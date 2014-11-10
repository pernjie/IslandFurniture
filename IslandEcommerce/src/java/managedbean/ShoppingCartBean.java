/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedbean;

import classes.CartItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author pern
 */
@ManagedBean(name = "shoppingCartBean")
@RequestScoped
public class ShoppingCartBean {
    private List<CartItem> cart;
    private long itemId;
    private long editId;
    private String qr;

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public long getEditId() {
        return editId;
    }

    public void setEditId(long editId) {
        this.editId = editId;
    }
    private int editQty;

    public int getEditQty() {
        return editQty;
    }

    public void setEditQty(int editQty) {
        this.editQty = editQty;
    }
    
    public void saveItemId(long id) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("editId", id);
    }
    
    @PostConstruct
    public void init() {
        cart = (List)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cart");
        qr = generateQR();
        System.out.println("qr: " + qr);
    }

    public ShoppingCartBean() {
    }

    public List<CartItem> getCart() {
        return cart;
    }

    public void setCart(List<CartItem> cart) {
        this.cart = cart;
    }
    
    public void removeItem() {
        System.out.println("remove itemid: " + itemId);
        cart = (List)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cart");
        CartItem ci = null;
        for (int i=0;i<cart.size();i++) {
            if (cart.get(i).getItem().getId() == itemId)
                ci = cart.get(i);
        }
        cart.remove(ci);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("cart");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cart", cart);
        RequestContext.getCurrentInstance().update("form:carttable");
        RequestContext.getCurrentInstance().update("form:qrpanel");
    }
    
    public void editItem() {
        System.out.println("change itemid: " + FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("editId"));
        cart = (List)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cart");
        CartItem ci = null;
        for (int i=0;i<cart.size();i++) {
            if (Objects.equals(cart.get(i).getItem().getId(), FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("editId"))) {
                ci = cart.get(i);
                System.out.println("editqty: " + editQty);
                ci.setQuantity(editQty);
            }
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("cart");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cart", cart);
        qr = generateQR();
        RequestContext.getCurrentInstance().update("form:carttable");
        RequestContext.getCurrentInstance().update("form:qrpanel");
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
    
    public String generateQR() {
        String str = "";
        if (cart.isEmpty())
            return null;
        for (CartItem ci : cart) {
            str += ci.getItem().getId() + "-" + ci.getQuantity()+ ",";
        }
        return str.substring(0, str.length()-1);
    }
}
