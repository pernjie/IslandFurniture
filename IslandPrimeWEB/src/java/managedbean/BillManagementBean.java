/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedbean;

import entity.Bill;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import session.stateless.ScmBean;

/**
 *
 * @author AdminNUS
 */
@Named(value = "billManagementBean")
@javax.enterprise.context.RequestScoped
public class BillManagementBean {

    @EJB
    private ScmBean sb;
    private List<Bill> unpaidBills;
    
    public BillManagementBean() {
    }
    
    @PostConstruct
    public void init() {
        System.out.println("init");
        unpaidBills = sb.getUnpaidBills();
    }

    public List<Bill> getUnpaidBills() {
        return unpaidBills;
    }

    public void setUnpaidBills(List<Bill> unpaidBills) {
        this.unpaidBills = unpaidBills;
    }
    
    
}