/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import classes.CustomerFreq;
import classes.CustomerMonetary;
import classes.CustomerRecency;
import classes.CustomerRfm;
import entity.Campaign;
import entity.Customer;
import entity.CustomerCampaignMetric;
import entity.CustomerMetric;
import entity.Region;
import entity.TransactionItem;
import entity.TransactionRecord;
import enumerator.BusinessArea;
import enumerator.Gender;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author DY
 */
@Stateless
@LocalBean
public class AnalCrmBean {

    List<CustomerRfm> custRfmScores = new ArrayList<>();

//    public void testAddTransactToCampaign() {
//        System.out.println("testAddTransactToCampaign @ SESSION BEAN initialized");
//        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
//        EntityManager em = emf.createEntityManager();
//        Query q;
//        System.out.println("CASE 1A");
//        q = em.createNamedQuery("TransactionRecord.findById");
//        q.setParameter("id", 1L);
//        addTransactionToCampaignCustomerMetric((TransactionRecord) q.getSingleResult());
//        printAllCCMs();
//        System.out.println("CASE 1B");
//        q = em.createNamedQuery("TransactionRecord.findById");
//        q.setParameter("id", 2L);
//        addTransactionToCampaignCustomerMetric((TransactionRecord) q.getSingleResult());
//        printAllCCMs();
//        System.out.println("CASE 2");
//        q = em.createNamedQuery("TransactionRecord.findById");
//        q.setParameter("id", 3L);
//        addTransactionToCampaignCustomerMetric((TransactionRecord) q.getSingleResult());
//        System.out.println("CASE 3A");
//        q = em.createNamedQuery("TransactionRecord.findById");
//        q.setParameter("id", 4L);
//        addTransactionToCampaignCustomerMetric((TransactionRecord) q.getSingleResult());
//        System.out.println("CASE 3B");
//        q = em.createNamedQuery("TransactionRecord.findById");
//        q.setParameter("id", 5L);
//        addTransactionToCampaignCustomerMetric((TransactionRecord) q.getSingleResult());
//    }

    public void addCampaignNewCustomer(Region region, Customer customer) {
        System.out.println("Initialize checkCampaignNewCustomer");
        Integer custAge = translateDobToAge(customer.getDob());
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT c FROM Campaign c WHERE c.region = :region AND c.upperAge>= :custAge AND c.lowerAge <= :custAge AND c.targetNew = :true");
        q.setParameter("region", region);
        q.setParameter("custAge", custAge);
        q.setParameter("true", true);
        List<Campaign> campaigns = q.getResultList();
        if (!campaigns.isEmpty()) {
            for (Campaign camp : campaigns) {
                if (camp.getTargetGender().equals(Gender.ALL) || camp.getTargetGender().equals(customer.getGender())) {
                    CustomerCampaignMetric ccm = new CustomerCampaignMetric();
                    ccm.setCampaign(camp);
                    ccm.setCustomer(customer);
                    ccm.setNumHits(0);
                    ccm.setNumPromoCodeUsed(0);
                    em.persist(ccm);
                }
            }
        }
    }

    public Integer translateDobToAge(Date date) {
        Calendar currDate = Calendar.getInstance();
        Integer currYear = currDate.get(Calendar.YEAR);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Integer bornYear = cal.get(Calendar.YEAR);
        return currYear - bornYear;
    }


    public void calculateInactivity() {
        System.out.println("Initialize calculateInactivity");
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Calendar cal = Calendar.getInstance();
        System.out.println("current date: " + cal);
        Date currDate = cal.getTime();
        cal.add(Calendar.YEAR, -1);
        System.out.println("last year date: " + cal);
        Date lastYear = cal.getTime();
        List<String> itemTypes = new ArrayList<>();
        itemTypes.add("Material");
        itemTypes.add("Product");
        itemTypes.add("Kitchen");

        Query q = em.createQuery("SELECT DISTINCT tr FROM TransactionRecord tr WHERE tr.transactTime BETWEEN :lastYear AND :currDate");
        q.setParameter("lastYear", lastYear, TemporalType.TIMESTAMP);
        q.setParameter("currDate", currDate, TemporalType.TIMESTAMP);
        Query qq = em.createNamedQuery("CustomerMetric.findAll");
        if (!q.getResultList().isEmpty()) {
            List<TransactionRecord> transactionRecords = q.getResultList();
            List<CustomerMetric> customerMetrics = qq.getResultList();
            setInactivty(transactionRecords, customerMetrics);
        }
    }

    public void setInactivty(List<TransactionRecord> transactionRecords, List<CustomerMetric> customerMetrics) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q;
        //for every single customer metric (one customer has 3, furniture, product and kitchen), check thru all transactions. if there is a match, set the customer metric
        //to active for that business area. then, break and go to the next customer metric.
        for (CustomerMetric cm : customerMetrics) {
            for (TransactionRecord tr : transactionRecords) {
                q = em.createQuery("SELECT ti FROM TransactionItem ti WHERE ti.transact= :transact");
                q.setParameter("transact", tr);
                List<TransactionItem> transactItems = q.getResultList();
                TransactionItem ti = transactItems.get(0);
                //System.out.println("Item Type: "+ti.getItem().getItemType());
                if (tr.getCust().equals(cm.getCustomer())) {
                    if (ti.getItem().getItemType().equals("Material") && cm.getBusinessArea().equals(BusinessArea.FURNITURE)) {
                        //System.out.println("CM number: "+cm.getId());
                        cm.setInactive(false);
                        em.merge(cm);
                        break;
                    } else if (ti.getItem().getItemType().equals("Product") && cm.getBusinessArea().equals(BusinessArea.PRODUCT)) {
                        cm.setInactive(false);
                        em.merge(cm);
                        break;
                    } else if ((ti.getItem().getItemType().equals("MenuItem") || ti.getItem().getItemType().equals("SetItem")) && cm.getBusinessArea().equals(BusinessArea.KITCHEN)) {
                        cm.setInactive(false);
                        em.merge(cm);
                        break;
                    }
                    cm.setInactive(true);
                    em.merge(cm);

                }
            }
        }
    }

    public void calculateCustomerRfmScoreByRegion() {
        System.out.println("Initialize calculateCustomerRfmScoreByRegion");
        List<Region> regions = getAllRegions();
        Calendar cal = Calendar.getInstance();
        System.out.println("current date: " + cal);
        Date currDate = cal.getTime();
        cal.add(Calendar.YEAR, -1);
        System.out.println("last year date: " + cal);
        Date lastYear = cal.getTime();
        List<String> itemTypes = new ArrayList<>();
        itemTypes.add("Material");
        itemTypes.add("Product");
        itemTypes.add("Kitchen");

//        List<BusinessArea> bizAreas = new ArrayList<BusinessArea>(BusinessArea.FURNITURE,BusinessArea.KITCHEN,BusinessArea.PRODUCT);
        for (String itemType : itemTypes) {
            for (Region r : regions) {
                EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
                EntityManager em = emf.createEntityManager();
                //get all transaction records in the region between 
                System.out.println("getting transaction records for region: " + r.getName() + " item type: " + itemType);
                Query q;
                if (!itemType.equals("Kitchen")) {
                    q = em.createQuery("SELECT DISTINCT ti.transact FROM TransactionItem ti WHERE ti.item.ItemType = :itemType "
                            + "AND ti.transact.fac.region = :region AND ti.transact.transactTime BETWEEN :lastYear AND :currDate");
                    q.setParameter("itemType", itemType);
                    q.setParameter("region", r);
                    q.setParameter("lastYear", lastYear, TemporalType.TIMESTAMP);
                    q.setParameter("currDate", currDate, TemporalType.TIMESTAMP);
                } else {
                    q = em.createQuery("SELECT DISTINCT ti.transact FROM TransactionItem ti WHERE ti.item.ItemType = :itemType1 OR ti.item.ItemType = :itemType2 "
                            + "AND ti.transact.fac.region = :region AND ti.transact.transactTime BETWEEN :lastYear AND :currDate");
                    q.setParameter("itemType1", "MenuItem");
                    q.setParameter("itemType2", "SetItem");
                    q.setParameter("region", r);
                    q.setParameter("lastYear", lastYear, TemporalType.TIMESTAMP);
                    q.setParameter("currDate", currDate, TemporalType.TIMESTAMP);
                }

                System.out.println("finished getting transaction records");
                if (!q.getResultList().isEmpty()) {
                    System.out.println("success in getting records, moving on to calculate RFM");
                    List<TransactionRecord> transactionRecords = q.getResultList();
                    calculateFrequency(transactionRecords);
                    calculateRecency(transactionRecords);
                    calculateMonetary(transactionRecords);
                    persistRfmScores(r, itemType);
                }
                custRfmScores = new ArrayList<>();
            }
        }
    }

    public void persistRfmScores(Region r, String itemType) {

        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT cm FROM CustomerMetric cm WHERE cm.customer.region = :region AND cm.businessArea = :bizArea");
        q.setParameter("region", r);
        if (itemType.equals("Material")) {
            q.setParameter("bizArea", BusinessArea.FURNITURE);
        } else if (itemType.equals("Product")) {
            q.setParameter("bizArea", BusinessArea.PRODUCT);
        } else if (itemType.equals("Kitchen")) {
            q.setParameter("bizArea", BusinessArea.KITCHEN);
        }
        List<CustomerMetric> custMetrics = q.getResultList();
        for (CustomerMetric cm : custMetrics) {
            for (CustomerRfm custRfm : custRfmScores) {
                if (cm.getCustomer().equals(custRfm.getCust())) {
                    cm.setCurrentRfm(custRfm.getRfmScore());
                    if (custRfm.getRfmScore() > cm.getHighestRfm()) {
                        cm.setHighestRfm(custRfm.getRfmScore());
                        em.merge(cm);
                        break;
                    } else {
                        em.merge(cm);
                        break;
                    }
                }

            }
        }
    }

    public List<Region> getAllRegions() {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Region.findAll");
        return query.getResultList();
    }

    public void calculateRecency(List<TransactionRecord> transactionRecords) {
        Collections.sort(transactionRecords, new Comparator<TransactionRecord>() {
            @Override
            public int compare(TransactionRecord o1, TransactionRecord o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });

        Boolean foundCustRecencyRecord = false;
        List<CustomerRecency> custRecencys = new ArrayList<>();
        //for loop to tag each customer with the latest transactionId. If a customer has a new transaction, it overwrites his older transactionid.
        for (TransactionRecord tr : transactionRecords) {
            for (CustomerRecency custRec : custRecencys) {
                if (tr.getCust().equals(custRec.getCust())) {
                    custRec.setTransactId(tr.getId());
                    foundCustRecencyRecord = true;
                    break;
                }
            }
            if (foundCustRecencyRecord == false) {
                custRecencys.add(new CustomerRecency(tr.getCust(), tr.getId()));
            } else {
                foundCustRecencyRecord = false;
            }
        }
        //sort customer recency from small transactionId to large transactionId. Behind one will be more recent.
        Collections.sort(custRecencys, new Comparator<CustomerRecency>() {
            @Override
            public int compare(CustomerRecency o1, CustomerRecency o2) {
                return o1.getTransactId().compareTo(o2.getTransactId());
            }
        });
//If the customer transactin id is smaller, means he is 

        Integer numOfScores = custRecencys.size();
        for (int i = 0; i < numOfScores; i++) {
            //sort small to large, 1point
            Customer customer = custRecencys.get(i).getCust();
            if (i < (numOfScores / 5)) {
                addToCustRfmScores(customer, 1);
            } //2points
            else if (i >= (numOfScores / 5) && i < (2 * numOfScores / 5)) {
                addToCustRfmScores(customer, 2);
            }//3points
            else if (i >= (2 * numOfScores / 5) && (i < 3 * numOfScores / 5)) {
                addToCustRfmScores(customer, 3);
            } //4points
            else if (i >= (3 * numOfScores / 5) && (i < 4 * numOfScores / 5)) {
                addToCustRfmScores(customer, 4);
            } //5points
            else if (i >= (4 * numOfScores / 5) && (i < numOfScores)) {
                addToCustRfmScores(customer, 5);
            }
            //System.out.println(custRfmScores.get(i).getCust().getName() + " " + custRfmScores.get(i).getCust().getRegion() + " " + custRfmScores.get(i).getRfmScore());
        }

    }

    public void calculateFrequency(List<TransactionRecord> transactionRecords) {
        List<CustomerFreq> custFreqScores = new ArrayList<>();
        Boolean foundCustFreqRecord = false;

        //for loop to find out the frequency of transactions made by customers in the furniture and then product business
        for (TransactionRecord tr : transactionRecords) {
            for (CustomerFreq custFreq : custFreqScores) {
                if (tr.getCust().equals(custFreq.getCust())) {
                    custFreq.setFreq(custFreq.getFreq() + 1);
                    foundCustFreqRecord = true;
                    break;
                }
            }
            //if within the output list, we did not find a record of the customer's scores yet, we add the customer with frequency 1.
            if (foundCustFreqRecord == false) {
                custFreqScores.add(new CustomerFreq(tr.getCust(), 1));
            } else {
                foundCustFreqRecord = false;
            }
        }
        //sort by comparing the frequency of each customerFreq item in custFreqScores
        Collections.sort(custFreqScores, new Comparator<CustomerFreq>() {
            @Override
            public int compare(CustomerFreq c1, CustomerFreq c2) {
                return c1.getFreq().compareTo(c2.getFreq());
            }
        });

        Integer numOfScores = custFreqScores.size();
        for (int i = 0; i < numOfScores; i++) {
            //sort small to large, 1point
            Customer customer = custFreqScores.get(i).getCust();
            if (i < (numOfScores / 5)) {
                custRfmScores.add(new CustomerRfm(customer, 1));
            } //2points
            else if (i >= (numOfScores / 5) && i < (2 * numOfScores / 5)) {
                custRfmScores.add(new CustomerRfm(customer, 2));
            }//3points
            else if (i >= (2 * numOfScores / 5) && (i < 3 * numOfScores / 5)) {
                custRfmScores.add(new CustomerRfm(customer, 3));
            } //4points
            else if (i >= (3 * numOfScores / 5) && (i < 4 * numOfScores / 5)) {
                custRfmScores.add(new CustomerRfm(customer, 4));
            } //5points
            else if (i >= (4 * numOfScores / 5) && (i < numOfScores)) {
                custRfmScores.add(new CustomerRfm(customer, 5));
            }
            // System.out.println(custRfmScores.get(i).getCust().getName() + " " + custRfmScores.get(i).getCust().getRegion() + " " + custRfmScores.get(i).getRfmScore());
        }

    }

    public void calculateMonetary(List<TransactionRecord> transactionRecords) {
        Collections.sort(transactionRecords, new Comparator<TransactionRecord>() {
            @Override
            public int compare(TransactionRecord o1, TransactionRecord o2) {
                return o1.getGrossTotalAmount().compareTo(o2.getGrossTotalAmount());
            }
        });

        Boolean foundCustMonetaryRecord = false;
        List<CustomerMonetary> custMonetarys = new ArrayList<>();
        //for loop to tag each customer with the latest transactionId. If a customer has a new transaction, it overwrites his older transactionid.
        for (TransactionRecord tr : transactionRecords) {
            for (CustomerMonetary custMon : custMonetarys) {
                if (tr.getCust().equals(custMon.getCust())) {
                    custMon.setMoney(custMon.getMoney() + tr.getGrossTotalAmount());
                    foundCustMonetaryRecord = true;
                    break;
                }
            }
            if (foundCustMonetaryRecord == false) {
                custMonetarys.add(new CustomerMonetary(tr.getCust(), tr.getGrossTotalAmount()));
            } else {
                foundCustMonetaryRecord = false;
            }
        }
        //sort customer recency from small transactionId to large transactionId. Behind one will be more recent.
        Collections.sort(custMonetarys, new Comparator<CustomerMonetary>() {
            @Override
            public int compare(CustomerMonetary o1, CustomerMonetary o2) {
                return o1.getMoney().compareTo(o2.getMoney());
            }
        });
//If the customer transactin id is smaller, means he is 

        Integer numOfScores = custMonetarys.size();
        for (int i = 0; i < numOfScores; i++) {
            //sort small to large, 1point
            Customer customer = custMonetarys.get(i).getCust();
            if (i < (numOfScores / 5)) {
                addToCustRfmScores(customer, 1);
            } //2points
            else if (i >= (numOfScores / 5) && i < (2 * numOfScores / 5)) {
                addToCustRfmScores(customer, 2);
            }//3points
            else if (i >= (2 * numOfScores / 5) && (i < 3 * numOfScores / 5)) {
                addToCustRfmScores(customer, 3);
            } //4points
            else if (i >= (3 * numOfScores / 5) && (i < 4 * numOfScores / 5)) {
                addToCustRfmScores(customer, 4);
            } //5points
            else if (i >= (4 * numOfScores / 5) && (i < numOfScores)) {
                addToCustRfmScores(customer, 5);
            }
            System.out.println("CUSTOMER: " + custRfmScores.get(i).getCust().getName() + " REGION: " + custRfmScores.get(i).getCust().getRegion().getName() + " SCORE: " + custRfmScores.get(i).getRfmScore());
        }

    }

    public Boolean noRecordInCustRfmScore(Customer customer) {
        for (CustomerRfm custRfm : custRfmScores) {
            if (custRfm.getCust().equals(customer)) {
                return false;
            }
        }
        return true;
    }

    public void addToCustRfmScores(Customer customer, Integer addToScore) {

        for (CustomerRfm custRfm : custRfmScores) {
            if (custRfm.getCust().equals(customer)) {
                custRfm.setRfmScore(custRfm.getRfmScore() + addToScore);
            }
        }

    }

    public void generateCustomerMetrics() {
        int custId = 1;
        int i = 1;
        for (int b = 0; b < 3; b++) {

            for (custId = 1; custId < 201; custId++) {
                System.out.println("INSERT INTO CUSTOMERMETRIC (ID,INACTIVE,CURRENT_RFM,HIGHEST_RFM,BUSINESS_AREA,CUSTOMER)"
                        + "VALUES (" + i + "," + 0 + "," + 0 + "," + 0 + "," + b + "," + custId + ");");
                i++;
            }
        }
    }
}
