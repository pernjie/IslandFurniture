/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import entity.Item;
import entity.Material;
import entity.Region;
import entity.RegionItemPrice;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

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
}
