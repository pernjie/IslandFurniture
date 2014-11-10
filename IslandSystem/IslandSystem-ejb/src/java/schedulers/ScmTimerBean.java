/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulers;

import classes.WeekHelper;
import entity.DeliverySchedule;
import entity.DistributionMFtoStore;
import entity.InventoryMaterial;
import entity.MrpRecord;
import entity.ProductionRecord;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import session.stateless.ScmBean;

@Stateless
public class ScmTimerBean {

    @EJB
    private ScmBean sb;
    private WeekHelper wh = new WeekHelper();
    private Date currDate;
    private Integer week;
    private Integer year;

    @Schedule(second = "0", minute = "0", hour = "9", dayOfWeek = "Mon, Tue, Wed, Thu, Fri")//second="0", minute="0", dayOfWeek="Mon")
    public void autoDeductRawMat() {
        System.out.println("Inventory Update for raw materials on: " + new Date());
        currDate = wh.getCurrDate();
        List<MrpRecord> mrprec = new ArrayList<MrpRecord>();
        mrprec = getMrpRecord();

        if (!mrprec.isEmpty()) {
            for (MrpRecord mr : mrprec) {
                System.err.println("function: iterate production record: " + mr.getMat());
                InventoryMaterial im = new InventoryMaterial();
                im = getInventoryMat(mr);
                System.err.println("function: inventorymaterial " + im.getMat() + " at fac " + im.getFac() + " with qty " + im.getQuantity());

                Integer qty = im.getQuantity() + wh.getDailyDemand(mr.getRequirement(), wh.getDay());
                System.err.println("qty: " + qty);
                if (qty > 0) {
                    im.setQuantity(qty);
                    persistInventoryMat(im);
                }
                if (qty < im.getLowThreshold()) {
                    System.err.println("Qty below lower threshold");
                } else {
                    System.err.println("Insufficient Inventory");
                }
            }
        } else {
            System.out.println("No mrp record found");
        }
    }

    @Schedule(second = "0", minute = "0", hour = "17", dayOfWeek = "Mon, Tue, Wed, Thu, Fri")//second="0", minute="0", dayOfWeek="Mon")
    private void autoIncreaseComGood() {
        System.out.println("Inventory Update for completed goods on: " + new Date() + " -- " + wh.getPeriod(0) + wh.getYear());
        currDate = wh.getCurrDate();
        List<ProductionRecord> prodrec = new ArrayList<ProductionRecord>();
        prodrec = getProductionRecord();

        if (!prodrec.isEmpty()) {
            for (ProductionRecord pr : prodrec) {
                System.err.println("function: iterate production record: " + pr.getMat());
                InventoryMaterial im = new InventoryMaterial();
                im = getInventoryMat(pr);
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
                } else {
                    im.setQuantity(qty);
                    persistInventoryMat(im);
                }
            }
        } else {
            System.out.println("No production record");
        }
    }

    @Schedule(second = "0", minute = "0", hour = "12", dayOfWeek = "Mon, Tue, Wed, Thu, Fri")//second="0", minute="0", dayOfWeek="Mon")
    private void autoDeductDelivGood() {
        System.out.println("Inventory Update for delivered goods on: " + new Date());
        currDate = wh.getCurrDate();
        List<DeliverySchedule> delsch = new ArrayList<DeliverySchedule>();
        delsch = getDeliverySchedule();

        if (!delsch.isEmpty()) {
            for (DeliverySchedule ds : delsch) {
                System.err.println("function: iterate delivery schedule: " + ds.getMat());
                InventoryMaterial im = new InventoryMaterial();
                im = getInventoryMat(ds);
                System.err.println("function: inventorymaterial " + im.getMat() + " at fac " + im.getFac() + " with qty " + im.getQuantity());

                Integer qty = im.getQuantity() - ds.getQuantity();

                System.err.println("new qty: " + qty);
                if (qty > 0) {
                    im.setQuantity(qty);
                    persistInventoryMat(im);
                }
                if (qty < im.getLowThreshold()) {
                    System.err.println("Qty below lower threshold");
                } else {
                    System.err.println("Insufficient Inventory");
                }
            }
        } else {
            System.out.println("No delivery schedule found");
        }
    }

    private List<MrpRecord> getMrpRecord() {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT m FROM " + MrpRecord.class.getName() + " m WHERE m.week = '" + wh.getWeek() + "' AND m.year = '" + wh.getYear() + "'");
        return query.getResultList();
    }

    private InventoryMaterial getInventoryMat(MrpRecord mr) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT i FROM " + InventoryMaterial.class.getName() + " i, " + MrpRecord.class.getName() + " m WHERE i.mat.id = '" + mr.getMat().getId() + "' AND i.fac.id = '" + mr.getFac().getId() + "' AND m.week = '" + wh.getWeek() + "' AND m.year = '" + wh.getYear() + "'");
        return (InventoryMaterial) query.getSingleResult();
    }

    private List<ProductionRecord> getProductionRecord() {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT p FROM " + ProductionRecord.class.getName() + " p WHERE p.period = '" + wh.getPeriod(0) + "' AND p.year = '" + wh.getYear() + "'");
        return query.getResultList();
    }

    private InventoryMaterial getInventoryMat(ProductionRecord pr) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT i FROM " + InventoryMaterial.class.getName() + " i, " + DistributionMFtoStore.class.getName() + " d WHERE i.mat.id = '" + pr.getMat().getId() + "' AND d.mat.id = '" + pr.getMat().getId() + "' AND d.store.id = '" + pr.getStore().getId() + "' AND i.fac.id = d.mf.id");
        return (InventoryMaterial) query.getSingleResult();
    }

    private List<DeliverySchedule> getDeliverySchedule() {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT d FROM " + DeliverySchedule.class.getName() + " d WHERE d.deliveryDate = '" + wh.getDateString(wh.getCurrDate()) + "'");
        return query.getResultList();
    }

    private InventoryMaterial getInventoryMat(DeliverySchedule ds) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT i FROM " + InventoryMaterial.class.getName() + " i, " + DeliverySchedule.class.getName() + " d WHERE i.mat.id = '" + ds.getMat().getId() + "' AND d.mat.id = '" + ds.getMat().getId() + "' AND d.store.id = '" + ds.getStore().getId() + "' AND i.fac.id = d.mf.id");
        return (InventoryMaterial) query.getSingleResult();
    }

    private boolean persistInventoryMat(InventoryMaterial im) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        try {
            em.merge(im);
            em.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
            System.err.println("inventorymaterial persisted!");
            return true;
        }
    }
}
