/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import classes.InventoryLocation;
import entity.Facility;
import entity.InventoryMaterial;
import entity.InventoryProduct;
import entity.Item;
import java.util.ArrayList;
import java.util.List;
import entity.Shelf;
import entity.ShelfSlot;
import entity.ShelfType;
import enumerator.InvenLoc;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.RowEditEvent;

import org.primefaces.event.SelectEvent;
import session.stateless.InventoryBean;
import util.exception.DetailsConflictException;
import util.exception.ReferenceConstraintException;

/**
 *
 * @author Anna
 */
@ManagedBean(name = "replenishInventoryBean")
@ViewScoped
public class ReplenishInventoryBean implements Serializable {

    private Item furn;
    private Item tempFurn;
    private List<Shelf> zoneShelfEntities;
    private Shelf zoneShelfEntity;

    private List<Shelf> shelfEntities;
    private Shelf shelfEntity;

    private List<ShelfSlot> shelfSlots;
    private ShelfSlot shelfSlot;

    private ShelfType shelfTypeSelected;

    private String zon;
    private String shelfValue;
    private String shelfSlotPos;
    private String statusMessage;
    private Double furnLengthRes;
    private Double furnBreadthRes;
    private Double furnHeightRes;
    private Integer resUpperThres;
    private Integer resLowerThres;
    private Integer upperThresValue;
    private Integer upperLowerThresValue;
    private Long newInvenFurnId;
    private final static String[] location;
    private String loc;
    private InvenLoc invenLoc;

    private InventoryMaterial selectedInvenFurn;
    private InventoryMaterial filteredInvenFurn;
    private List<InventoryMaterial> invenFurns;
    private List<InventoryProduct> invenProds;
    private InventoryMaterial restockMat;
    private InventoryProduct restockProd;
    private List<InventoryLocation> ilList;
    private List<InventoryLocation> restockList;
    private InventoryLocation il;
    private InventoryLocation rl;

    private String loggedInEmail;
    private Facility fac;

    @EJB
    private InventoryBean ib;

    static {
        location = new String[3];
        location[0] = "MarketPlace";
        location[1] = "Self Service Warehouse";
        location[2] = "Retail Store";
    }

    @PostConstruct
    public void init() {
        System.err.println("replenishinventorybean: init()");
        loggedInEmail = new String();
        loggedInEmail = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("email");
        fac = ib.getFac(loggedInEmail);
        zoneShelfEntities = ib.getZoneShelfEntitiesFromFac();
        shelfEntities = new ArrayList<Shelf>();
        shelfSlots = new ArrayList<ShelfSlot>();

        resUpperThres = null;

        for (Shelf s : zoneShelfEntities) {
            System.out.println(s.getZone());
        }

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("zoneShelfEntities", zoneShelfEntities);

    }

    public void updateTable() {
        Integer qty = 0;
        ilList = new ArrayList<InventoryLocation>();
        restockList = new ArrayList<InventoryLocation>();
        if (loc.equals("MarketPlace") || loc.equals("Self Service Warehouse")) {
            invenLoc = InvenLoc.FRONTEND_STORE;
            invenFurns = ib.getFurns(fac, invenLoc);
            for (InventoryMaterial mat : invenFurns) {
                il = new InventoryLocation();
                rl = new InventoryLocation();
                furn = mat.getMat();
                if (mat.getQuantity() < mat.getUppThreshold()) {
                    il.setInvItem(furn.getId());
                    rl.setInvItem(furn.getId());
                    il.setItemType(1);
                    rl.setItemType(1);
                    qty = mat.getUppThreshold() - mat.getQuantity();
                    restockMat = new InventoryMaterial();
                    restockMat = ib.getInventoryMat(furn, fac, InvenLoc.BACKEND_WAREHOUSE);
                    if (restockMat.getQuantity() > qty) {
                        mat.setQuantity(mat.getUppThreshold());
                        restockMat.setQuantity(restockMat.getQuantity() - qty);
                        il.setItem(furn);
                        il.setZone(mat.getZone());
                        il.setShelve(mat.getShelf());
                        il.setShelfSlot(mat.getShelfSlot());
                        il.setQty(qty);
                        il.setMove(true);
                        rl.setItem(furn);
                        rl.setZone(restockMat.getZone());
                        rl.setShelve(restockMat.getShelf());
                        rl.setShelfSlot(restockMat.getShelfSlot());
                        rl.setQty(qty);
                        rl.setMove(true);
                    } else if (restockMat.getQuantity() > 0) {
                        mat.setQuantity(mat.getQuantity() + restockMat.getQuantity());
                        restockMat.setQuantity(0);
                        il.setItem(furn);
                        il.setZone(mat.getZone());
                        il.setShelve(mat.getShelf());
                        il.setShelfSlot(mat.getShelfSlot());
                        il.setQty(restockMat.getQuantity());
                        il.setMove(true);
                        rl.setItem(furn);
                        rl.setZone(restockMat.getZone());
                        rl.setShelve(restockMat.getShelf());
                        rl.setShelfSlot(restockMat.getShelfSlot());
                        rl.setQty(restockMat.getQuantity());
                        rl.setMove(true);
                    } else {
                    // ad hoc production planning
                        sendAdHocProdOrder(fac,mat.getMat());
                    }
                    ib.persistInventoryMaterial(mat);
                    ib.persistInventoryMaterial(restockMat);
                    ilList.add(il);
                }
            }
        } else {
            invenProds = ib.getProds(fac, invenLoc);
            for (InventoryProduct prod : invenProds) {
                il = new InventoryLocation();
                furn = prod.getProd();
                if (prod.getQuantity() < prod.getUppThreshold()) {
                    il.setInvItem(furn.getId());
                    rl.setInvItem(furn.getId());
                    il.setItemType(2);
                    rl.setItemType(2);
                    qty = prod.getUppThreshold() - prod.getQuantity();
                    restockProd = new InventoryProduct();
                    restockProd = ib.getInventoryProd(furn, fac, InvenLoc.BACKEND_WAREHOUSE);
                    if (restockProd.getQuantity() > qty) {
                        prod.setQuantity(prod.getUppThreshold());
                        restockProd.setQuantity(restockProd.getQuantity() - qty);                      
                        il.setItem(furn);
                        il.setZone(prod.getZone());
                        il.setShelve(prod.getShelf());
                        il.setShelfSlot(prod.getShelfSlot());il.setQty(qty);
                        il.setMove(true);
                        rl.setZone(restockProd.getZone());
                        rl.setShelve(restockProd.getShelf());
                        rl.setShelfSlot(restockProd.getShelfSlot());
                        rl.setQty(qty);
                        rl.setMove(true);
                    } else if (restockProd.getQuantity() > 0) {
                        prod.setQuantity(prod.getQuantity() + restockProd.getQuantity());
                        restockProd.setQuantity(0);
                        il.setItem(furn);
                        il.setZone(prod.getZone());
                        il.setShelve(prod.getShelf());
                        il.setShelfSlot(prod.getShelfSlot());
                        il.setQty(restockProd.getQuantity());
                        il.setMove(true);
                        rl.setItem(furn);
                        rl.setZone(restockProd.getZone());
                        rl.setShelve(restockProd.getShelf());
                        rl.setShelfSlot(restockProd.getShelfSlot());
                        rl.setQty(restockProd.getQuantity());
                        rl.setMove(true);
                    } else {
                    // ad hoc production planning
                        sendAdHocProdOrder(fac,prod.getProd());
                    }
                    ib.persistInventoryProduct(prod);
                    ib.persistInventoryProduct(restockProd);
                    ilList.add(il);
                }
            }
        }
    }

    public void updateInventory() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("../inventory/inventory_view_restock_location.xhtml");
    }
    
    public void sendAdHocProdOrder(Facility fac, Item mat) {
        System.err.println("send ad hoc prod order");
    }
    
    public List<Item> completeText(String query) {
        List<Item> allFurns = ib.getFurnitures();
        List<Item> filteredResults = new ArrayList<Item>();

        for (Item indiv : allFurns) {
            if (indiv.getName().toLowerCase().contains(query)) {
                filteredResults.add(indiv);
            }
        }

        return filteredResults;
    }

    public void onZoneChange() {
        System.out.println("ZONE: " + zon);
        if (zon != null && !zon.equals("")) {
            shelfEntities = ib.getShelfEntities(zon);
            System.out.println("SUCCESS");
        } else {
            shelfEntities = new ArrayList<Shelf>();
        }
    }

    public void onShelfChange() throws Exception {
        furn = (Item) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("furn");
      //furn.getClass().getSimpleName();
        //furn instanceof Material;

        //if(furn instanceof Material) {
        System.out.println("In FUNCTION");
        System.out.println("SHELF: " + shelfValue);
        if (shelfValue != null && !shelfValue.equals("")) {
            Long shelfNum = Long.valueOf(shelfValue);
            System.out.println(shelfNum);
            shelfSlots = ib.getShelfSlots(shelfNum);
            shelfTypeSelected = ib.getShelfType(shelfNum);
            Double slotLength = shelfTypeSelected.getLength();
            Double slotBreadth = shelfTypeSelected.getBreadth();
            Double slotHeight = shelfTypeSelected.getHeight();
            System.out.println("SUCCESS 2");
            System.out.println("SlotLength " + slotLength);
            System.out.println("SlotHeight " + slotHeight);
            System.out.println("SlotBreadth " + slotBreadth);

            System.out.println("FURN" + furn);
            try {
                Double furnLength = furn.getLength();
                Double furnBreadth = furn.getBreadth();
                Double furnHeight = furn.getHeight();
                Map<String, Double> finalvalues = new HashMap<String, Double>();

                finalvalues = ib.calcThresValues(slotLength, slotBreadth, slotHeight, furnLength, furnBreadth, furnHeight);
                furnLengthRes = finalvalues.get("lengthUsed");
                furnBreadthRes = finalvalues.get("breadthUsed");
                furnHeightRes = finalvalues.get("heightUsed");
                Double upperThreshold = finalvalues.get("upperThreshold");
                Double lowerThreshold = finalvalues.get("lowerThreshold");

                resUpperThres = upperThreshold.intValue();
                System.out.println("resUpperThreshold 2: " + resUpperThres);

                resLowerThres = lowerThreshold.intValue();
                System.out.println("resUpperThreshold 2: " + resLowerThres);

                System.out.println("Length to use 2: " + furnLengthRes);
                System.out.println("Breadth to use 2: " + furnBreadthRes);
                System.out.println("Height to use 2: " + furnHeightRes);
                System.out.println("Upper Threshold 2: " + resUpperThres);
                System.out.println("Lower Threshold 2: " + resLowerThres);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            shelfSlots = new ArrayList<ShelfSlot>();
        }
    }

    public void handleSelect(SelectEvent event) {
        tempFurn = (Item) event.getObject();
        System.out.println("HANDLE_SELECTED: " + tempFurn.getId());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("furn", tempFurn);
        System.out.println("HANDLE_SELECTED 2: " + furn);
    }

    public SelectItem[] getFurnLocValues() {
        SelectItem[] items = new SelectItem[3];
        int j = 0;
        int i;
        for (i = 0; i < (InvenLoc.values().length - 1); ++i) {
            InvenLoc il = InvenLoc.getIndex(i);
            items[j++] = new SelectItem(il, il.getLabel());
        }
        return items;
    }

    public void saveNewInventory(ActionEvent event) {
        System.out.println(furn);
        System.out.println(zon);
        System.out.println(shelfValue);
        System.out.println(shelfSlotPos);
        System.out.println(loc);

        Long shelfValueLong = Long.valueOf(shelfValue);
        Integer shelfSlotInt = Integer.valueOf(shelfSlotPos);

        try {
            newInvenFurnId = ib.addNewInventoryRawMat(furn, shelfValueLong, shelfSlotInt, invenLoc, zon, resUpperThres, resLowerThres, furnLengthRes, furnBreadthRes, furnHeightRes);
            statusMessage = "New Inventory Furniture Record Saved Successfully.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Add New Inventory Furniture Record Result: "
                    + statusMessage + " (New Inventory Furniture Record ID is " + newInvenFurnId + ")", ""));
        } catch (Exception ex) {
            newInvenFurnId = -1L;
            statusMessage = "New Inventory Furniture Failed.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Add New Inventory Furniture Record Result: "
                    + statusMessage, ""));
            ex.printStackTrace();
        }
    }

    public void deleteInvenFurn() {
        try {
            ib.removeFurn(selectedInvenFurn);
            invenFurns = ib.getAllInvenFurns();
            FacesMessage msg = new FacesMessage("Retail Inventory Record Deleted");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (ReferenceConstraintException ex) {
            invenFurns = ib.getAllInvenFurns();
            statusMessage = ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Changes not saved: "
                    + statusMessage, ""));
        }
    }

    public void onRowEdit(RowEditEvent event) {
        try {
            ib.updateInvenMat((InventoryMaterial) event.getObject());
            invenFurns = ib.getAllInvenFurns();
            FacesMessage msg = new FacesMessage("Retail Inventory Record Edited", ((InventoryMaterial) event.getObject()).getId().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (DetailsConflictException dcx) {
            invenFurns = ib.getAllInvenFurns();
            statusMessage = dcx.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Changes not saved: "
                    + statusMessage, ""));
        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((InventoryMaterial) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Creates a new instance of SuppliesMatToFacManagerBean
     */
    public ReplenishInventoryBean() {

    }

    public List<Shelf> getZoneShelfEntities() {
        return zoneShelfEntities;
    }

    public void setZoneShelfEntities(List<Shelf> zoneShelfEntities) {
        this.zoneShelfEntities = zoneShelfEntities;
    }

    public Shelf getZoneShelfEntity() {
        return zoneShelfEntity;
    }

    public void setZoneShelfEntity(Shelf zoneShelfEntity) {
        this.zoneShelfEntity = zoneShelfEntity;
    }

    public List<Shelf> getShelfEntities() {
        return shelfEntities;
    }

    public void setShelfEntities(List<Shelf> shelfEntities) {
        this.shelfEntities = shelfEntities;
    }

    public Shelf getShelfEntity() {
        return shelfEntity;
    }

    public void setShelfEntity(Shelf shelfEntity) {
        this.shelfEntity = shelfEntity;
    }

    public String getZon() {
        return zon;
    }

    public void setZon(String zon) {
        this.zon = zon;
    }

    public String getShelfValue() {
        return shelfValue;
    }

    public void setShelfValue(String shelfValue) {
        this.shelfValue = shelfValue;
    }

    public List<ShelfSlot> getShelfSlots() {
        return shelfSlots;
    }

    public void setShelfSlots(List<ShelfSlot> shelfSlots) {
        this.shelfSlots = shelfSlots;
    }

    public ShelfSlot getShelfSlot() {
        return shelfSlot;
    }

    public void setShelfSlot(ShelfSlot shelfSlot) {
        this.shelfSlot = shelfSlot;
    }

    public String getShelfSlotPos() {
        return shelfSlotPos;
    }

    public void setShelfSlotPos(String shelfSlotPos) {
        this.shelfSlotPos = shelfSlotPos;
    }

    public ShelfType getShelfTypeSelected() {
        return shelfTypeSelected;
    }

    public void setShelfTypeSelected(ShelfType shelfTypeSelected) {
        this.shelfTypeSelected = shelfTypeSelected;
    }

    public Double getFurnLengthRes() {
        return furnLengthRes;
    }

    public void setFurnLengthRes(Double furnLengthRes) {
        this.furnLengthRes = furnLengthRes;
    }

    public Double getFurnBreadthRes() {
        return furnBreadthRes;
    }

    public void setFurnBreadthRes(Double furnBreadthRes) {
        this.furnBreadthRes = furnBreadthRes;
    }

    public Double getFurnHeightRes() {
        return furnHeightRes;
    }

    public void setFurnHeightRes(Double furnHeightRes) {
        this.furnHeightRes = furnHeightRes;
    }

    public Integer getResUpperThres() {
        return resUpperThres;
    }

    public void setResUpperThres(Integer resUpperThres) {
        this.resUpperThres = resUpperThres;
    }

    public Integer getResLowerThres() {
        return resLowerThres;
    }

    public void setResLowerThres(Integer resLowerThres) {
        this.resLowerThres = resLowerThres;
    }

    public Integer getUpperThresValue() {
        return upperThresValue;
    }

    public void setUpperThresValue(Integer upperThresValue) {
        this.upperThresValue = upperThresValue;
    }

    public Integer getUpperLowerThresValue() {
        return upperLowerThresValue;
    }

    public void setUpperLowerThresValue(Integer upperLowerThresValue) {
        this.upperLowerThresValue = upperLowerThresValue;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public Long getNewInvenFurnId() {
        return newInvenFurnId;
    }

    public void setNewInvenFurnId(Long newInvenFurnId) {
        this.newInvenFurnId = newInvenFurnId;
    }

    public Item getFurn() {
        return furn;
    }

    public void setFurn(Item furn) {
        this.furn = furn;
    }

    public Item getTempFurn() {
        return tempFurn;
    }

    public void setTempFurn(Item tempFurn) {
        this.tempFurn = tempFurn;
    }

    public InventoryMaterial getSelectedInvenFurn() {
        return selectedInvenFurn;
    }

    public void setSelectedInvenFurn(InventoryMaterial selectedInvenFurn) {
        this.selectedInvenFurn = selectedInvenFurn;
    }

    public List<InventoryMaterial> getInvenFurns() {
        return invenFurns;
    }

    public void setInvenFurns(List<InventoryMaterial> invenFurns) {
        this.invenFurns = invenFurns;
    }

    public InventoryMaterial getFilteredInvenFurn() {
        return filteredInvenFurn;
    }

    public void setFilteredInvenFurn(InventoryMaterial filteredInvenFurn) {
        this.filteredInvenFurn = filteredInvenFurn;
    }
    
    public String[] getLocation() {
        return location;
    }

}
