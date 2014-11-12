/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import entity.Country;
import entity.Customer;
import entity.Item;
import entity.Material;
import entity.Region;
import entity.RegionItemPrice;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import util.exception.DetailsConflictException;

/**
 *
 * @author Anna
 */
@Stateless
@LocalBean
public class EComBean {//implements EComBeanLocal{
    //@Override

    public List<RegionItemPrice> getFurnitureDisplays(long regionId) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();

        Query q = em.createNamedQuery("Region.findById");
        q.setParameter("id", regionId);

        Region region = (Region) q.getSingleResult();

        System.out.println("IN GET FURNITURE!");
        System.out.print("REGION " + region);
        Query query = em.createQuery("SELECT r FROM " + RegionItemPrice.class.getName() + " r WHERE r.region =:region AND r.item IN (SELECT m FROM " + Material.class.getName() + " m WHERE NOT m.id = 64)");
        query.setParameter("region", region);

        System.out.println("IN GET FURNITURE 2!");

        return query.getResultList();
    }

    //@Override
    public Material getItem(Long itemId) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();

        System.out.println("itemid: !" + itemId);
        Query q = em.createNamedQuery("Material.findById");
        q.setParameter("id", itemId);

        Material result = (Material) q.getSingleResult();
        return result;
    }

    //@Override
    public Material getItembyString(String itemName) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();

        Query q = em.createNamedQuery("Material.findByName");
        q.setParameter("name", itemName);

        try {
            Material result = (Material) q.getSingleResult();
            System.out.println("IN GET ITEM!");
            System.out.println(result);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    //@Override
    public List<Region> getRegions() {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();

        Query q = em.createNamedQuery("Region.findAll");

        System.out.println("IN GET REGION!");
        System.out.println(q.getResultList().size());
        return q.getResultList();
    }

    //@Override
    public Region getRegion(long regionId) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();

        Query q = em.createNamedQuery("Region.findById");
        q.setParameter("id", regionId);

        return (Region) q.getSingleResult();
    }

    //@Override
    public RegionItemPrice getRegionPriceRecord(Item item, Region region) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();

        Query q = em.createNamedQuery("RegionItemPrice.findByRegionItem");
        q.setParameter("region", region);
        q.setParameter("item", item);

        return (RegionItemPrice) q.getSingleResult();
    }

    //@Override
    public List<Item> getItems(long regionId) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();

        System.out.println("IN GET ITEMS!");

        Integer genCategory = 0;
        List<Item> resItems = new ArrayList<Item>();

        Query q = em.createNamedQuery("Region.findById");
        q.setParameter("id", regionId);

        Region resultRegion = (Region) q.getSingleResult();

        Query query = em.createQuery("SELECT r FROM " + RegionItemPrice.class.getName() + " r WHERE r.region =:region AND r.item IN (SELECT m FROM " + Material.class.getName() + " m WHERE NOT m.genCategory = :genCategory)");
        query.setParameter("region", resultRegion);
        query.setParameter("genCategory", genCategory);
        List<RegionItemPrice> mats = query.getResultList();

        for (RegionItemPrice m : mats) {
            System.out.println(m.getId());
            resItems.add(m.getItem());
        }

        return resItems;
    }

    //@Override
    public void unsubscribe(long custId) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT c FROM Customer c where c.id= :param");
        q.setParameter("param", custId);
        Customer customer = (Customer) q.getSingleResult();
        customer.setUnsubscribed(true);
        em.persist(customer);
    }

    //@Override
    public Long addNewCustomer(Customer customer) throws DetailsConflictException {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        String email = customer.getEmail();
        if (!emailAlreadyExist(email)) {
            try {
                customer.setPassword(encryptPassword(customer.getEmail(), customer.getPassword()));
                em.persist(customer);

                return customer.getId();

            } catch (Exception e) {
                e.printStackTrace();

                return null;

            } finally {
                em.close();

            }
        } else {
            throw new DetailsConflictException("Email exists: " + customer.getEmail());
        }
    }

    public Customer getCustomerDetails(String email) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("SELECT c FROM Customer c where c.email= :param");
        q.setParameter("param", email);
        Customer customer = (Customer) q.getSingleResult();
        return customer;
    }

    private Boolean emailAlreadyExist(String email) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.email = :email");
        query.setParameter("email", email);
        List resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public String encryptPassword(String email, String password) {
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

    //@Override
    public List<Customer> getAllCustomers() {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Customer.findAll");
        return query.getResultList();
    }

    //@Override
    public List<Country> getAllCountries() {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandSystem-ejbPU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Country.findAll");
        return query.getResultList();
    }
}
