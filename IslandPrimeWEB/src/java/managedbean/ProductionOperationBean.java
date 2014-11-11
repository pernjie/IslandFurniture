package managedbean;

import classes.WeekHelper;
import entity.Facility;
import entity.InventoryMaterial;
import entity.Item;
import entity.MrpRecord;
import entity.ProductionRecord;
import entity.PurchasePlanningOrder;
import entity.PurchasePlanningRecord;
import entity.ShelfSlot;
import entity.ShelfType;
import enumerator.InvenLoc;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import session.stateless.ScmBean;

@ManagedBean(name = "productionOperationBean")
@ViewScoped
public class ProductionOperationBean implements Serializable {

    private String loggedInEmail;
    private Date currDate;
    private Facility fac;
    private Item item;
    private Integer week;
    private Integer year;
    private WeekHelper wh = new WeekHelper();
    
    private Double furnLengthRes;
    private Double furnBreadthRes;
    private Double furnHeightRes;
    private Integer resUpperThres;
    private Integer resLowerThres;
    private boolean disable;
    
    @EJB
    private ScmBean sb;

    @PostConstruct
    public void init() {
        System.err.println("function: init()");
        loggedInEmail = new String();
        loggedInEmail = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("email");
        fac = sb.getFac(loggedInEmail);
        System.err.println("fac: " + fac);
        currDate = wh.getCurrDate();
        week = wh.getWeek();
        year = wh.getYear();
        this.disable= false;
    }

    public void startProduction() {
        System.out.println("Inventory Update for raw materials on: " + new Date());
        currDate = wh.getCurrDate();
        List<MrpRecord> mrprec = new ArrayList<MrpRecord>();
        mrprec = sb.getMrpRecord(fac, week, year);

        if (!mrprec.isEmpty()) {
            for (MrpRecord mr : mrprec) {
                System.err.println("function: iterate production record: " + mr.getMat());
                InventoryMaterial im = new InventoryMaterial();
                im = sb.getInventoryMat(mr);
                System.err.println("function: inventorymaterial " + im.getMat() + " at fac " + im.getFac() + " with qty " + im.getQuantity());

                Integer qty = im.getQuantity() + wh.getDailyDemand(mr.getRequirement(), wh.getDay());
                System.err.println("qty: " + qty);
                if (qty > 0) {
                    im.setQuantity(qty);
                    sb.persistInventoryMaterial(im);
                }
                if (qty < im.getLowThreshold()) {
                    System.err.println("Qty below lower threshold");
                    this.disable = true;
                } else {
                    System.err.println("Insufficient Inventory");
                }
            }
        } else {
            System.out.println("No mrp record found");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Production Available", ""));
        }
    }

    public void endProduction() {
        System.out.println("Inventory Update for completed goods on: " + new Date() + " -- " + wh.getPeriod(0) + wh.getYear());
        currDate = wh.getCurrDate();
        Integer period = wh.getPeriod(0);
        List<ProductionRecord> prodrec = new ArrayList<ProductionRecord>();
        prodrec = sb.getProductionRecord(fac, period, year);

        if (!prodrec.isEmpty()) {
            for (ProductionRecord pr : prodrec) {
                System.err.println("function: iterate production record: " + pr.getMat());
                InventoryMaterial im = new InventoryMaterial();
                im = sb.getInventoryMat(pr);
                System.err.println("function: inventorymaterial " + im.getMat() + " at fac " + im.getFac() + " with qty " + im.getQuantity());

                Integer qty = im.getQuantity();
                Integer week = wh.getWeekOfPeriod();
                if (week == 1) {
                    qty += wh.getDailyDemand(pr.getQuantityW1(), wh.getDay());
                } else if (week == 2) {
                    qty += wh.getDailyDemand(pr.getQuantityW2(), wh.getDay());
                } else if (week == 3) {
                    qty += wh.getDailyDemand(pr.getQuantityW3(), wh.getDay());
                } else if (week == 4) {
                    qty += wh.getDailyDemand(pr.getQuantityW4(), wh.getDay());
                } else {
                    qty += wh.getDailyDemand(pr.getQuantityW5(), wh.getDay());
                }
                System.err.println("new qty: " + qty);
                if (qty > im.getUppThreshold()) {
                    System.err.println("Qty exceeds upper threshold");
                    InventoryMaterial mat = new InventoryMaterial();
                    Item furn = im.getMat();
                    System.err.println("mat id:" + furn);
                    try {
                        mat = sb.getInventoryMat(furn, fac, InvenLoc.MF);
                        System.err.println("found invmat id: " + mat);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    if (mat == null) {
                        System.err.println("new mat:" + furn);
                        ShelfSlot shelfSlot = new ShelfSlot();
                        shelfSlot = sb.getAvailableShelfSlot(fac);
                        if (shelfSlot == null) {
                            System.err.println("no shelf slot in current mf");
                        } else {
                            mat.setShelfSlot(shelfSlot);
                            shelfSlot.setOccupied(Boolean.TRUE);
                            sb.persistShelfSlot(shelfSlot);
                            mat.setShelf(shelfSlot.getShelf());
                            mat.setZone(shelfSlot.getShelf().getZone());
                            mat.setLocation(InvenLoc.MF);
                            ShelfType shelfType = shelfSlot.getShelf().getShelfType();
                            Double slotLength = shelfType.getLength();
                            Double slotBreadth = shelfType.getBreadth();
                            Double slotHeight = shelfType.getHeight();
                            try {
                                Double furnLength = furn.getLength();
                                Double furnBreadth = furn.getBreadth();
                                Double furnHeight = furn.getHeight();
                                Map<String, Double> finalvalues = new HashMap<String, Double>();

                                finalvalues = sb.calcThresValues(slotLength, slotBreadth, slotHeight, furnLength, furnBreadth, furnHeight);
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
                            mat.setLowThreshold(resUpperThres);
                            mat.setUppThreshold(resLowerThres);
                            mat.setMatBreadth(furnBreadthRes);
                            mat.setMatHeight(furnHeightRes);
                            mat.setMatLength(furnLengthRes);
                            sb.persistInventoryMaterial(mat);
                        }
                    }
                } else {
                    im.setQuantity(qty);
                    sb.updateInventoryMat(im);
                }
            }
        } else {
            System.out.println("No production record");
        }
    }
    
    public boolean isDisable() {
       return disable;
    }
    public void setDisable(boolean disable) {
       this.disable = disable;
    }
}
