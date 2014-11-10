/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import entity.Campaign;
import entity.Country;
import entity.Customer;
import entity.CustomerCampaignMetric;
import entity.Facility;
import entity.Item;
import entity.Region;
import entity.RegionItemPrice;
import entity.RegionServicePrice;
import entity.Service;
import entity.TransactionItem;
import entity.TransactionRecord;
import entity.TransactionService;
import enumerator.BusinessArea;
import enumerator.TenderType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author pern
 */
@Stateless
@WebService
public class PosBean {

    public int login(String email, String password) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        try {
            password = encryptPassword(email, password);
            Query q;
            System.out.println("Password entered = " + password);
            q = em.createQuery("SELECT s.password FROM Staff s where s.email= :param");
            q.setParameter("param", email);
            String pwd = (String) q.getSingleResult();
            System.out.println("Password found = " + pwd);
            if (pwd == null) {
                System.out.println("PASSWORD NULL");
                return 0;
            } else {
                System.err.println("SURVIVED");
                if (pwd.equals(password)) {
                    return 1;
                } else {
                    return -1;
                }
            }

        } catch (NoResultException ec) {
            System.err.println("ERROR");
            return 0;
        }
    }

    private String encryptPassword(String email, String password) {
        String encrypted = null;
        if (password == null) {
            return null;
        } else {
            try {
                password = password.concat(email);
                System.err.println("encrypt Password: password = " + password);
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(password.getBytes(), 0, password.length());
                encrypted = new BigInteger(1, md.digest()).toString(16);
                System.err.println("encrypt Password: encrypted = " + encrypted);
            } catch (NoSuchAlgorithmException ex) {
            }
        }
        return encrypted;
    }

    public Facility getStore(String email) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q;
        q = em.createQuery("SELECT s.fac FROM Staff s WHERE s.email= :param");
        q.setParameter("param", email);
        Facility store = (Facility) q.getSingleResult();
        return store;
    }

    public Item getItem(String id) {
        System.out.println("ID IS " + id);
        try {
            EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
            EntityManager em = emf.createEntityManager();
            Query query = em.createNamedQuery("Item.findById");
            query.setParameter("id", Long.parseLong(id, 10));
            Item item = (Item) query.getSingleResult();
            System.out.println("item is: " + item.getName());
            return item;
        } catch (Exception e) {
            return null;
        }
    }

    public Service getService(String id) {
        System.out.println("ID IS " + id);
        try {
            EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
            EntityManager em = emf.createEntityManager();
            Query query = em.createNamedQuery("Service.findById");
            query.setParameter("id", Long.parseLong(id, 10));
            Service service = (Service) query.getSingleResult();
            return service;
        } catch (Exception e) {
            return null;
        }
    }

    public Customer getCustomer(String card) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q;
        System.out.println("card:" + card);
        q = em.createQuery("SELECT c FROM " + Customer.class.getName() + " c WHERE c.loyaltyCard = :card");
        q.setParameter("card", card);
        try {
            Customer cust = (Customer) q.getSingleResult();
            return cust;
        } catch (NoResultException e) {
            return null;
        }
    }

    public Region getRegion(Facility store) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q;
        q = em.createQuery("SELECT f.region FROM " + Facility.class.getName() + " f WHERE f = :store");
        q.setParameter("store", store);
        Region region = (Region) q.getSingleResult();
        return region;
    }

    public Double getItemPrice(Item item, Region region) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q;
        q = em.createQuery("SELECT r.price FROM " + RegionItemPrice.class.getName() + " r WHERE r.item = :item AND r.region = :region");
        q.setParameter("region", region);
        q.setParameter("item", item);
        Double price = (Double) q.getSingleResult();
        return price;
    }

    public Double getServicePrice(Service service, Region region) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q;
        q = em.createQuery("SELECT r.price FROM " + RegionServicePrice.class.getName() + " r WHERE r.svc = :service AND r.region = :region");
        q.setParameter("region", region);
        q.setParameter("service", service);
        Double price = (Double) q.getSingleResult();
        return price;
    }

    public boolean redeemCake(Customer cust) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q;
        q = em.createQuery("SELECT c FROM " + Customer.class.getName() + " c WHERE c = :cust");
        q.setParameter("cust", cust);
        Customer cust2 = (Customer) q.getSingleResult();
        cust2.setRedeemedCake(true);
        try {
            em.merge(cust2);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyPromo(String code) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q;
        q = em.createQuery("SELECT 1 FROM " + Campaign.class.getName() + " c WHERE c.promocode = :code");
        q.setParameter("code", code);
        return (!q.getResultList().isEmpty());
    }

    private Campaign getCampaignFromPromo(String code) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q;
        q = em.createQuery("SELECT c FROM " + Campaign.class.getName() + " c WHERE c.promocode = :code");
        q.setParameter("code", code);
        try {
            return ((Campaign) q.getSingleResult());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addTransactionRecord(Facility store, List<TransactionItem> transitems, List<TransactionService> transservices,
            List<Item> items, List<Service> services,
            Double amount, String promo, int tender, Customer cust, boolean redeemed) {
        TransactionRecord tr = new TransactionRecord();
        tr.setGrossTotalAmount(amount);
        Double discount = 0.0;
        Double tax = 0.0;
        System.out.println("amt: " + amount);
        if (promo != null) {
            discount = getPromoDiscount(amount, promo);
            tr.setCampaign(getCampaignFromPromo(promo));

        } else if (redeemed) {
            discount = getRedemptionDiscount(amount);
            tr.setRedemption(true);
        }
        tr.setDiscountAmount(round(discount, 2));
        amount -= discount;
        tax = getTaxAmount(amount, store);
        tr.setTaxAmount(round(tax, 2));
        amount += tax;
        for (TransactionItem ti : transitems) {
            ti.setTransact(tr);
        }
        for (TransactionService ts : transservices) {
            ts.setTransact(tr);
        }
        for (int i = 0; i < transitems.size(); i++) {
            transitems.get(i).setItem(items.get(i));
        }
        for (int i = 0; i < transservices.size(); i++) {
            transservices.get(i).setService(services.get(i));
        }
        tr.setNetTotalAmount(round(amount, 2));
        Calendar cal = Calendar.getInstance();
        tr.setTransactTime(cal.getTime());
        tr.setFac(store);
        if (cust != null) {
            tr.setCust(cust);
            editCustomerPoints(cust, tr.getGrossTotalAmount());
            if (redeemed) {
                editCustomerPoints(cust, -500.0);
            }
        }

        switch (tender) {
            case 0:
                tr.setTenderType(TenderType.CASH);
                break;
            case 1:
                tr.setTenderType(TenderType.CREDIT_CARD);
                break;
            case 2:
                tr.setTenderType(TenderType.NETS);
                break;
        }
        tr.setCollected(false);
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.persist(tr);
            for (TransactionItem ti : transitems) {
                em.persist(ti);
            }
            for (TransactionService ts : transservices) {
                em.persist(ts);
            }
            addTransactionToCampaignCustomerMetric(tr);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            em.close();
            return true;
        }
    }
    
    private void addTransactionToCampaignCustomerMetric(TransactionRecord tr) {
        Region transactRegion = tr.getFac().getRegion();
        Customer customer = tr.getCust();
        Campaign campaign = tr.getCampaign();
        String itemType = getTransactionRecordItemType(tr);
        BusinessArea bizArea = getBusinessAreaFromItemType(itemType);
        if (campaign == null && customer != null) { //Case 1: TR has customer, no campaign. We want to see if customer is inside any ongoing campaign. if he is, add 1 to hit.
            List<Campaign> onGoingCampaigns = getOngoingCampaignsInRegionAndBusinessArea(transactRegion, bizArea);
            if (!onGoingCampaigns.isEmpty()) {
                for (Campaign camp : onGoingCampaigns) {
                    //System.out.println("1A onging campaign: " + camp.getId());
                    if (campaignTargetsCustomer(camp, customer)) {
                        addToTargetCustomerCampaignHit(camp, customer);
                    }
                }
            }
        } else if (campaign != null && customer == null) { //Case 2: TR has campaign, no customer. add 1 to nonTargetCustomerPromo and hit.
            addToNonTargetCustomerCampaignPromoHit(campaign);

        } else if (campaign != null && customer != null) { //Case 3: TR has campaign, has customer. Check customer in campaign? yes, add 1 to TargetCustomerPromo and hit. no, add to nontarget
            if (campaignTargetsCustomer(campaign, customer)) {
                addToTargetCustomerCampaignPromoHit(campaign, customer);
            } else {
                addToNonTargetCustomerCampaignPromoHit(campaign);
            }
        }

    }

    private void addToNonTargetCustomerCampaignPromoHit(Campaign camp) {
        System.out.println("addToNonTarget initialized");
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q;
        q = em.createQuery("SELECT ccm FROM CustomerCampaignMetric ccm WHERE ccm.campaign = :camp");
        q.setParameter("camp", camp);
        List<CustomerCampaignMetric> ccms = q.getResultList();
        CustomerCampaignMetric selectedCcm = null;
        for (CustomerCampaignMetric ccm : ccms) {
            if (ccm.getCustomer() == null) {
                selectedCcm = ccm;
            }
        }
        try {
            if (selectedCcm != null) {
                System.out.println("case 3A should come here.");
                Integer newNumHits = selectedCcm.getNumHits() + 1;
                selectedCcm.setNumHits(newNumHits);
                em.merge(selectedCcm);
                em.flush();
            } else {
                CustomerCampaignMetric ccm = new CustomerCampaignMetric();
                ccm.setCampaign(camp);
                ccm.setNumHits(1);
                ccm.setNumPromoCodeUsed(1);
                em.persist(ccm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private BusinessArea getBusinessAreaFromItemType(String itemType) {
        if (itemType.equals("Material")) {
            return BusinessArea.FURNITURE;
        } else if (itemType.equals("Product")) {
            return BusinessArea.PRODUCT;
        } else if (itemType.equals("MenuItem") || itemType.equals("SetItem")) {
            return BusinessArea.KITCHEN;
        }
        return null;
    }

    private Boolean campaignTargetsCustomer(Campaign camp, Customer customer) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT 1 FROM CustomerCampaignMetric ccm WHERE ccm.campaign = :camp AND ccm.customer = :customer");
        q.setParameter("camp", camp);
        q.setParameter("customer", customer);
        return (!q.getResultList().isEmpty());
    }

    private void addToTargetCustomerCampaignPromoHit(Campaign camp, Customer customer) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        System.out.println("addToTargetPromo initialized");
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT ccm FROM CustomerCampaignMetric ccm WHERE ccm.campaign = :camp AND ccm.customer = :customer");
        q.setParameter("camp", camp);
        q.setParameter("customer", customer);
        try {
            CustomerCampaignMetric ccm = (CustomerCampaignMetric) q.getSingleResult();
            System.out.print(ccm.getCampaign() + "=====" + ccm.getCustomer());
            Integer newNumHits = ccm.getNumHits() + 1;
            Integer newNumPromo = ccm.getNumPromoCodeUsed() + 1;
            ccm.setNumHits(newNumHits);
            ccm.setNumPromoCodeUsed(newNumPromo);
            em.merge(ccm);
            em.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private void addToTargetCustomerCampaignHit(Campaign camp, Customer customer) {
        System.out.println("addToTargetCustomerCampaignHit initialized");
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT ccm FROM CustomerCampaignMetric ccm WHERE ccm.campaign = :camp AND ccm.customer = :customer");
        q.setParameter("camp", camp);
        q.setParameter("customer", customer);
        try {
            CustomerCampaignMetric ccm = (CustomerCampaignMetric) q.getSingleResult();
            System.out.print(ccm.getCampaign() + "=====" + ccm.getCustomer());
            Integer newNumHits = ccm.getNumHits() + 1;
            ccm.setNumHits(newNumHits);
            em.merge(ccm);
            em.flush();
        } catch (Exception e) {
            System.out.println("PROBLEM");
            e.printStackTrace();

        } finally {
            em.close();
        }
    }

    private List<Campaign> getOngoingCampaignsInRegionAndBusinessArea(Region region, BusinessArea bizArea) {
        Calendar cal = Calendar.getInstance();
        Date currDate = cal.getTime();
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT c FROM Campaign c WHERE c.region = :region AND c.businessArea = :bizArea AND :currDate BETWEEN c.startDate AND c.endDate");
        q.setParameter("region", region);
        q.setParameter("currDate", currDate);
        q.setParameter("bizArea", bizArea);
        return (List<Campaign>) q.getResultList();
    }

    private String getTransactionRecordItemType(TransactionRecord tr) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        System.out.println("getTransactionRecordItemType initialized. TR ID IS: " + tr.getId());
        EntityManager em = emf.createEntityManager();
        Query q = em.createNamedQuery("TransactionItem.findByTransact");
        q.setParameter("transact", tr);
        List<TransactionItem> transactItems = q.getResultList();
        return transactItems.get(0).getItem().getItemType();
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void editCustomerPoints(Customer cust, double points) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q;
        q = em.createQuery("SELECT c FROM " + Customer.class.getName() + " c WHERE c = :cust");
        q.setParameter("cust", cust);
        Customer cust2 = (Customer) q.getSingleResult();
        cust2.setPoints(cust2.getPoints() + (int) (Math.floor(points)));
        if (cust2.getPoints() >= 150 && !cust2.getPlus()) {
            cust2.setPlus(true);
        }
        em.merge(cust2);
    }

    private Double getPromoDiscount(Double amount, String promo) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q;
        q = em.createQuery("SELECT c.percentDisc FROM " + Campaign.class.getName() + " c "
                + "WHERE c.promocode = :promo");
        q.setParameter("promo", promo);
        Double disc = (Double) q.getSingleResult();
        return amount * (disc / 100);
    }

    private Double getRedemptionDiscount(Double amount) {
        return (amount > 5.0) ? 5 : amount;
    }

    private Double getTaxAmount(Double amount, Facility store) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q;
        q = em.createQuery("SELECT c.taxPercent FROM " + Country.class.getName() + " c, " + Facility.class.getName() + " f "
                + "WHERE f.country = c AND f = :store");
        q.setParameter("store", store);
        Double tax = (Double) q.getSingleResult();
        return amount * (tax / 100);
    }
}
