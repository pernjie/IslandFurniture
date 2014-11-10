package islandglobal;

import classes.WeekHelper;
import entity.Component;
import entity.DistributionMFtoStore;
import entity.DistributionMFtoStoreProd;
import entity.Facility;
import entity.Material;
import entity.MrpRecordMaterial;
import entity.Product;
import entity.ProductionOrder;
import entity.ProductionRecord;
import entity.PurchasePlanningOrder;
import entity.PurchasePlanningRecord;
import entity.Staff;
import entity.Supplier;
import entity.SuppliesMatToFac;
import entity.SuppliesProdToFac;
import entity.TransactionFurniture;
import entity.TransactionProduct;
import entity.TransactionRecord;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class Main {

    public static void main(String[] args) {
        String choice = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (!choice.equals("0")) {
            System.out.println("Select an option");
            System.out.println("a: Initialise");
            System.out.println("b: Select");
            System.out.println("c: Add values");

            try {
                choice = br.readLine();
            } catch (Exception e) {
                System.out.println(e.toString());
            }

            if (choice.equals("a")) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost/islanddatabase", "root", "");
                    Statement sta = (Statement) con.createStatement();

                    String reset;
                    System.out.println("hi1");
                    reset = "DROP TABLE IF EXISTS ChatRecord";
                    sta.executeUpdate(reset);
                    reset = "DROP TABLE IF EXISTS Bill";
                    sta.executeUpdate(reset);
                    reset = "DROP TABLE IF EXISTS SuppliesMatToFac";
                    sta.executeUpdate(reset);
                    reset = "DROP TABLE IF EXISTS SuppliesProdToFac";
                    sta.executeUpdate(reset);
                    reset = "DROP TABLE IF EXISTS SuppliesMatToFacility";
                    sta.executeUpdate(reset);
                    reset = "DROP TABLE IF EXISTS SellsFurniture";
                    sta.executeUpdate(reset);
                    System.out.println("hi2");
                    reset = "DROP TABLE IF EXISTS MrpRecordMaterial";
                    sta.executeUpdate(reset);
                    System.out.println("hi3");
                    reset = "DROP TABLE IF EXISTS ProductionOrder";
                    sta.executeUpdate(reset);
                    System.out.println("hi4");
                    reset = "DROP TABLE IF EXISTS ProductionRecord";
                    sta.executeUpdate(reset);
                    System.out.println("hi5");
                    reset = "DROP TABLE IF EXISTS DistributionMFtoStore";
                    sta.executeUpdate(reset);
                    reset = "DROP TABLE IF EXISTS DistributionMFtoStoreProd";
                    sta.executeUpdate(reset);
                    System.out.println("hi6");
                    System.out.println("hi7");
                    reset = "DROP TABLE IF EXISTS TransactionFurniture";
                    sta.executeUpdate(reset);
                    reset = "DROP TABLE IF EXISTS TransactionService";
                    sta.executeUpdate(reset);
                    reset = "DROP TABLE IF EXISTS TransactionProduct";
                    sta.executeUpdate(reset);
                    System.out.println("hi8");
                    reset = "DROP TABLE IF EXISTS TransactionRecord";
                    sta.executeUpdate(reset);
                    System.out.println("hi9");
                    reset = "DROP TABLE IF EXISTS Component";
                    sta.executeUpdate(reset);
                    System.out.println("hi10");
                    reset = "DROP TABLE IF EXISTS PoProduct";
                    sta.executeUpdate(reset);
                    System.out.println("hi");
                    reset = "DROP TABLE IF EXISTS PoRecord";
                    sta.executeUpdate(reset);
                    System.out.println("hi");
                    reset = "DROP TABLE IF EXISTS MrpRecordProduct";
                    sta.executeUpdate(reset);
                    reset = "DROP TABLE IF EXISTS PurchasePlanningOrder";
                    sta.executeUpdate(reset);
                    reset = "DROP TABLE IF EXISTS PurchasePlanningRecord";
                    sta.executeUpdate(reset);
                    reset = "DROP TABLE IF EXISTS Product";
                    sta.executeUpdate(reset);
                    System.out.println("hi");
                    reset = "DROP TABLE IF EXISTS Material";
                    sta.executeUpdate(reset);
                    System.out.println("hi");
                    reset = "DROP TABLE IF EXISTS Supplier";
                    sta.executeUpdate(reset);
                    System.out.println("hi");
                    reset = "DROP TABLE IF EXISTS Facility";
                    sta.executeUpdate(reset);
                    reset = "DROP TABLE IF EXISTS Staff_Log";
                    sta.executeUpdate(reset);
                    reset = "DROP TABLE IF EXISTS Staff";
                    sta.executeUpdate(reset);
                    reset = "DROP TABLE IF EXISTS Log";
                    sta.executeUpdate(reset);

                    String create;
                    
                    create = "CREATE TABLE Log (\n"
                            + "id BIGINT PRIMARY KEY,\n"
                            + "email VARCHAR(64),\n"
                            + "logDate VARCHAR(64),\n"
                            + "logDetails VARCHAR(255)\n"
                            + ");";
                    sta.executeUpdate(create);

                    create = "CREATE TABLE Material (\n"
                            + "id BIGINT PRIMARY KEY,\n"
                            + "name VARCHAR(64),\n"
                            + "description VARCHAR(255),\n"
                            + "gen_category INT,\n"
                            + "mat_category VARCHAR(64)\n"
                            + ");";
                    sta.executeUpdate(create);

                    create = "CREATE TABLE Facility (\n"
                            + "id BIGINT PRIMARY KEY,\n"
                            + "name VARCHAR(50) UNIQUE,\n"
                            + "postal_code VARCHAR(50),\n"
                            + "city VARCHAR(50),\n"
                            + "country VARCHAR(50),\n"
                            + "type VARCHAR(16)\n"
                            + ");";
                    sta.executeUpdate(create);

                    create = "CREATE TABLE Component(\n"
                            + "	id BIGINT PRIMARY KEY,\n"
                            + "	mat_id BIGINT REFERENCES Material(id),\n"
                            + "	consist_of BIGINT REFERENCES Material(id),\n"
                            + "	quantity INT\n"
                            + ");";
                    sta.executeUpdate(create);

                    create = "CREATE TABLE SellsFurniture (\n"
                            + "	id BIGINT PRIMARY KEY,\n"
                            + "	store_id BIGINT REFERENCES Facility(id),\n"
                            + "	mat_id BIGINT REFERENCES Material(id),\n"
                            + "	price DOUBLE\n"
                            + ");";
                    sta.executeUpdate(create);

                    create = "CREATE TABLE Supplier (\n"
                            + "id BIGINT PRIMARY KEY,\n"
                            + "name VARCHAR(50) UNIQUE,\n"
                            + "address VARCHAR(50),\n"
                            + "contact_number VARCHAR(50),\n"
                            + "contact_email VARCHAR(50)\n"
                            + ")";
                    sta.executeUpdate(create);

                    create = "CREATE TABLE MrpRecordMaterial(\n"
                            + "	id BIGINT PRIMARY KEY,\n"
                            + "	fac_id BIGINT REFERENCES Facility(id),\n"
                            + "	mat_id BIGINT REFERENCES Material(id),\n"
                            + "	requirement BIGINT,\n"
                            + "	receipt BIGINT,\n"
                            + "	on_hand BIGINT,\n"
                            + "	planned BIGINT,\n"
                            + "	week INT,\n"
                            + "	yearId INT\n"
                            + ");";
                    sta.executeUpdate(create);

                    create = "CREATE TABLE DistributionMFtoStore (\n"
                            + "id BIGINT PRIMARY KEY,\n"
                            + "mf_id BIGINT REFERENCES facility(id),\n"
                            + "store_id BIGINT REFERENCES facility(id),\n"
                            + "mat_id BIGINT REFERENCES Material(id)\n"
                            + ");";
                    sta.executeUpdate(create);
                    
                    create = "CREATE TABLE DistributionMFtoStoreProd (\n"
                            + "id BIGINT PRIMARY KEY,\n"
                            + "mf BIGINT REFERENCES facility(id),\n"
                            + "store BIGINT REFERENCES facility(id),\n"
                            + "prod BIGINT REFERENCES Product(id)\n"
                            + ");";
                    sta.executeUpdate(create);

                    create = "CREATE TABLE PoProduct (\n"
                            + "delivery_date DATE  ,\n"
                            + "quantity BIGINT  ,\n"
                            + "total_price DOUBLE  ,\n"
                            + "status VARCHAR(50)  ,\n"
                            + "po_id BIGINT REFERENCES PoRecord (id),\n"
                            + "product_id BIGINT REFERENCES Product (id),\n"
                            + "PRIMARY KEY (po_id, product_id)\n"
                            + ");";
                    sta.executeUpdate(create);

                    create = "CREATE TABLE PoRecord (\n"
                            + "id BIGINT UNSIGNED ZEROFILL   AUTO_INCREMENT,\n"
                            + "order_date DATE  ,\n"
                            + "total_price DOUBLE  ,\n"
                            + "status VARCHAR(50)  ,\n"
                            + "facility_id BIGINT REFERENCES Facility (id),\n"
                            + "supplier_id BIGINT REFERENCES Supplier (id),\n"
                            + "PRIMARY KEY (id)\n"
                            + ");";
                    sta.executeUpdate(create);

                    create = "CREATE TABLE TransactionRecord (\n"
                            + "id BIGINT   PRIMARY KEY,\n"
                            + "transact_time DATE  ,\n"
                            + "facility_id BIGINT REFERENCES facility(id),\n"
                            + "pos_id BIGINT  ,\n"
                            + "cust_id BIGINT,\n"
                            + "collected BOOLEAN   ,\n"
                            + "promo_code VARCHAR(255) REFERENCES PromotionalCode(code),\n"
                            + "redemption INT,\n"
                            + "total_amount DOUBLE  \n"
                            + ");";
                    sta.executeUpdate(create);

                    create = "CREATE TABLE TransactionFurniture (\n"
                            + "id BIGINT   PRIMARY KEY,\n"
                            + "transact_id BIGINT REFERENCES TransactionRecord(id),\n"
                            + "mat_id BIGINT REFERENCES Material(id),\n"
                            + "quantity BIGINT,\n"
                            + "price DOUBLE,\n"
                            + "returned_qty INT\n"
                            + ");";
                    sta.executeUpdate(create);

                    create = "CREATE TABLE Product (\n"
                            + "id BIGINT   PRIMARY KEY,\n"
                            + "name VARCHAR(64) UNIQUE  ,\n"
                            + "description VARCHAR(255)  ,\n"
                            + "shelf_life INT  ,\n"
                            + "category VARCHAR(64)  ,\n"
                            + "halal BOOLEAN  \n"
                            + ");";
                    sta.executeUpdate(create);

                    create = "CREATE TABLE ProductionOrder(\n"
                            + "	id BIGINT PRIMARY KEY,\n"
                            + "	store_id BIGINT REFERENCES Facility(id),\n"
                            + "	mat_id BIGINT REFERENCES Material(id),\n"
                            + "	month INT,\n"
                            + "	yearId INT,\n"
                            + "	quantity BIGINT,\n"
                            + "	status VARCHAR(64)\n"
                            + ");";
                    sta.executeUpdate(create);
                    
                    create = "CREATE TABLE PurchasePlanningOrder(\n"
                            + "	id BIGINT PRIMARY KEY,\n"
                            + "	store BIGINT REFERENCES Facility(id),\n"
                            + "	prod BIGINT REFERENCES Product(id),\n"
                            + "	period INT,\n"
                            + "	year INT,\n"
                            + "	quantity BIGINT,\n"
                            + "	status VARCHAR(64)\n"
                            + ");";
                    sta.executeUpdate(create);
                    
                    create = "CREATE TABLE ProductionRecord(\n"
                            + "	id BIGINT PRIMARY KEY,\n"
                            + "	store BIGINT REFERENCES Facility(id),\n"
                            + "	mat BIGINT REFERENCES Material(id),\n"
                            + "	period INT,\n"
                            + "	year INT,\n"
                            + "	quantity_W1 INT,\n"
                            + "	quantity_W2 INT,\n"
                            + "	quantity_W3 INT,\n"
                            + "	quantity_W4 INT,\n"
                            + "	quantity_W5 INT\n"
                            + ");";
                    sta.executeUpdate(create);
                    
                    create = "CREATE TABLE PurchasePlanningRecord(\n"
                            + "	id BIGINT PRIMARY KEY,\n"
                            + "	store BIGINT REFERENCES Facility(id),\n"
                            + "	prod BIGINT REFERENCES Product(id),\n"
                            + "	period INT,\n"
                            + "	year INT,\n"
                            + "	quantity_W1 INT,\n"
                            + "	quantity_W2 INT,\n"
                            + "	quantity_W3 INT,\n"
                            + "	quantity_W4 INT,\n"
                            + "	quantity_W5 INT\n"
                            + ");";
                    sta.executeUpdate(create);
                    
                    create = "CREATE TABLE SuppliesMatToFac(\n"
                            + "	id BIGINT PRIMARY KEY,\n"
                            + "	lot_size INT,"
                            + " unit_price DOUBLE,"
                            + " lead_time INT,"
                            + " sup BIGINT REFERENCES Supplier(id),"
                            + " mat BIGINT REFERENCES Material(id),"
                            + " fac BIGINT REFERENCES Facility(id)"
                            + ");";
                    sta.executeUpdate(create);
                    
                    create = "CREATE TABLE SuppliesProdToFac(\n"
                            + "	id BIGINT PRIMARY KEY,\n"
                            + "	lot_size INT,"
                            + " unit_price DOUBLE,"
                            + " lead_time INT,"
                            + " sup BIGINT REFERENCES Supplier(id),"
                            + " prod BIGINT REFERENCES Product(id),"
                            + " fac BIGINT REFERENCES Facility(id)"
                            + ");";
                    sta.executeUpdate(create);
                    
                    create = "CREATE TABLE Bill (\n"
                            + "	id BIGINT PRIMARY KEY,\n"
                            + "	amount DOUBLE,"
                            + " payment_date DATE,"
                            + " status VARCHAR(64),"
                            + " sup BIGINT REFERENCES Supplier(id),"
                            + " po BIGINT"
                            + ");";
                    sta.executeUpdate(create);

                    create = "CREATE TABLE Staff (\n"
                            + "	id BIGINT PRIMARY KEY,\n"
                            + "	email VARCHAR(255),\n"
                            + "	contact VARCHAR(255),\n"
                            + "	name VARCHAR(255) ,\n"
                            + "	password VARCHAR(255) ,\n"
                            + "	fac BIGINT REFERENCES Facility(id),\n"
                            + "	role_1 VARCHAR(64) ,\n"
                            + "	role_2 VARCHAR(64),\n"
                            + "	role_3 VARCHAR(64)\n"
                            + ");";
                    sta.executeUpdate(create);
                    
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            } else if (choice.equals("c")) {
                try {
                    EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("IslandGlobalPU");
                    EntityManager em = emf.createEntityManager();

                    //Insert Goods
                    ArrayList<String> goods = new ArrayList<String>();
                    String filename = "C:\\Users\\nataliegoh\\Downloads\\IS3102\\Project\\files\\goods.txt";
                    BufferedReader reader = new BufferedReader(new FileReader(filename));

                    try {
                        String line;
                        //as long as there are lines in the file, print them
                        while ((line = reader.readLine()) != null) {
                            String[] parts = line.split(",");
                            for (int i = 0; i < 4; i++) {
                                goods.add(parts[i]);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    List<Material> mats = new ArrayList<>();
                    em.getTransaction().begin();
                    try {
                        for (int mat = 0; mat < goods.size() / 4; mat++) {
                            Material m = new Material();
                            m.setName(goods.get(mat * 4));
                            m.setDescription(goods.get(mat * 4 + 1));
                            m.setGenCategory(Integer.valueOf(goods.get(mat * 4 + 2)));
                            m.setMatCategory(goods.get(mat * 4 + 3));
                            mats.add(m);
                            em.persist(m);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        em.getTransaction().rollback();
                    } finally {
                        //em.close();
                    }
                    
                    Product p1 = new Product();
                    p1.setName("Lays Potato Chips");
                    p1.setDescription("Chips");
                    p1.setCategory("Snacks");
                    Product p2 = new Product();
                    p2.setName("Wang Wang Biscuits");
                    p2.setDescription("Children's Biscuits");
                    p2.setCategory("Snacks");
                    Product p3 = new Product();
                    p3.setName("Dou Sha Bao");
                    p3.setDescription("Red Bean Paste Buns");
                    p3.setCategory("Snacks");

                    Facility f_jurong = new Facility();
                    f_jurong.setName("Jurong MF");
                    f_jurong.setPostalCode("S33433");
                    f_jurong.setCity("Singapore");
                    f_jurong.setCountry("Singapore");
                    f_jurong.setType("Manufacturing");

                    Facility f_tampines = new Facility();
                    f_tampines.setName("Tampines Store");
                    f_tampines.setPostalCode("S123292");
                    f_tampines.setCity("Singapore");
                    f_tampines.setCountry("Singapore");
                    f_tampines.setType("Store");

                    Facility f_kallang = new Facility();
                    f_kallang.setName("Kallang Store");
                    f_kallang.setPostalCode("S322323");
                    f_kallang.setCity("Singapore");
                    f_kallang.setCountry("Singapore");
                    f_kallang.setType("Store");
                    
                    Facility f_hougang = new Facility();
                    f_hougang.setName("Global HQ");
                    f_hougang.setPostalCode("S333323");
                    f_hougang.setCity("Singapore");
                    f_hougang.setCountry("Singapore");
                    f_hougang.setType("Global HQ");

                    DistributionMFtoStore dist1 = new DistributionMFtoStore();
                    dist1.setMf(f_jurong);
                    dist1.setStore(f_tampines);
                    dist1.setMat(mats.get(1));
                    
                    DistributionMFtoStore dist2 = new DistributionMFtoStore();
                    dist2.setMf(f_jurong);
                    dist2.setStore(f_tampines);
                    dist2.setMat(mats.get(3));
                    
                    DistributionMFtoStore dist3 = new DistributionMFtoStore();
                    dist3.setMf(f_jurong);
                    dist3.setStore(f_kallang);
                    dist3.setMat(mats.get(1));
                    
                    DistributionMFtoStore dist4 = new DistributionMFtoStore();
                    dist4.setMf(f_jurong);
                    dist4.setStore(f_kallang);
                    dist4.setMat(mats.get(27));
                    
                    DistributionMFtoStoreProd dist5 = new DistributionMFtoStoreProd();
                    dist5.setMf(f_jurong);
                    dist5.setStore(f_kallang);
                    dist5.setProd(p1);
                    
                    DistributionMFtoStoreProd dist6 = new DistributionMFtoStoreProd();
                    dist6.setMf(f_jurong);
                    dist6.setStore(f_kallang);
                    dist6.setProd(p2);
                    
                    try {
                        em.persist(p1);
                        em.persist(p2);
                        em.persist(p3);
                        em.persist(f_tampines);
                        em.persist(f_kallang);
                        em.persist(f_jurong);
                        em.persist(f_hougang);
                        em.persist(dist1);
                        em.persist(dist2);
                        em.persist(dist3);
                        em.persist(dist4);
                        em.persist(dist5);
                        em.persist(dist6);
//                        em.persist(po);
                    } catch (Exception e) {
                        e.printStackTrace();
                        em.getTransaction().rollback();
                    } finally {
                        //em.close();
                    }

                    //Insert Component
                    ArrayList<String> comps = new ArrayList<String>();
                    filename = "C:\\Users\\nataliegoh\\Downloads\\IS3102\\Project\\files\\component.txt";
                    reader = new BufferedReader(new FileReader(filename));
                    try {
                        String line;
                        //as long as there are lines in the file, print them
                        while ((line = reader.readLine()) != null) {
                            String[] parts = line.split(",");
                            for (int i = 0; i < 3; i++) {
                                comps.add(parts[i]);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    
                    try {
                        for (int i = 0; i < comps.size() / 3; i++) {
                            Component c = new Component();
                            c.setMatId(mats.get(Integer.valueOf(comps.get(i * 3)) - 1));
                            c.setConsistOf(mats.get(Integer.valueOf(comps.get(i * 3 + 1)) - 1));
                            c.setQuantity(Integer.valueOf(comps.get(i * 3 + 2)));
                            em.persist(c);
                        }
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                        em.getTransaction().rollback();
                    } finally {
                        //em.close();
                    }

                    //Insert Suppliers
                    ArrayList<String> supps = new ArrayList<String>();
                    filename = "C:\\Users\\nataliegoh\\Downloads\\IS3102\\Project\\files\\suppliers.txt";
                    reader = new BufferedReader(new FileReader(filename));
                    try {
                        String line;
                        //as long as there are lines in the file, print them
                        while ((line = reader.readLine()) != null) {
                            String[] parts = line.split(",");
                            for (int i = 0; i < 4; i++) {
                                supps.add(parts[i]);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //Insert Suppliers
                    List<Supplier> suppliers = new ArrayList<Supplier>();
                    try {
                        for (int s = 0; s < supps.size() / 4; s++) {
                            Supplier sup = new Supplier();
                            sup.setName(supps.get(s * 4));
                            sup.setAddress(supps.get(s * 4 + 1));
                            sup.setContactNumber(supps.get(s * 4 + 2));
                            sup.setContactEmail(supps.get(s * 4 + 3));
                            suppliers.add(sup);
                            em.persist(sup);
                        }
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                        em.getTransaction().rollback();
                    } finally {
                        //em.close();
                    }
                    
                    try {
                        //mat: 44, 40, 43, 42, 
                        for (int i = 0; i < mats.size(); i++) {
                            SuppliesMatToFac sm = new SuppliesMatToFac();
                            sm.setMat(mats.get(i));
                            sm.setSup(suppliers.get(1));
                            sm.setLeadTime(2);
                            sm.setLotSize(100);
                            sm.setFac(f_jurong);
                            em.persist(sm);
                        }

                        ProductionOrder po1 = new ProductionOrder();
                        po1.setMonth(10);
                        po1.setYearid(2015);
                        po1.setStoreId(f_kallang);
                        po1.setQuantity(300);
                        po1.setMatId(mats.get(1));
                        po1.setStatus("pending");
                        
                        ProductionOrder po2 = new ProductionOrder();
                        po2.setMonth(10);
                        po2.setYearid(2015);
                        po2.setStoreId(f_kallang);
                        po2.setQuantity(200);
                        po2.setMatId(mats.get(3));
                        po2.setStatus("pending");
                        
                        PurchasePlanningOrder po3 = new PurchasePlanningOrder();
                        po3.setPeriod(10);
                        po3.setYear(2015);
                        po3.setStore(f_kallang);
                        po3.setQuantity(300);
                        po3.setProd(p1);
                        po3.setStatus("pending");
                        
                        PurchasePlanningOrder po4 = new PurchasePlanningOrder();
                        po4.setPeriod(10);
                        po4.setYear(2015);
                        po4.setStore(f_kallang);
                        po4.setQuantity(300);
                        po4.setProd(p2);
                        po4.setStatus("pending");
                        
                        SuppliesProdToFac sp1 = new SuppliesProdToFac();
                        sp1.setFac(f_jurong);
                        sp1.setLeadTime(1);
                        sp1.setLotSize(80);
                        sp1.setSup(suppliers.get(0));
                        sp1.setProd(p1);

                        SuppliesProdToFac sp2 = new SuppliesProdToFac();
                        sp2.setFac(f_jurong);
                        sp2.setLeadTime(1);
                        sp2.setLotSize(50);
                        sp2.setSup(suppliers.get(0));
                        sp2.setProd(p2);
                        em.persist(sp1);
                        em.persist(sp2);
                        em.persist(po1);
                        em.persist(po2);
                        em.persist(po3);
                        em.persist(po4);
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        //em.close();
                    }
                    
                    WeekHelper wh = new WeekHelper();
                    for (int k = -1; k<5;k++) {
                            //1, 3, 27
                            ProductionRecord pr = new ProductionRecord();
                            pr.setMat(mats.get(1));
                            pr.setPeriod(wh.getPeriod(k));
                            pr.setYear(wh.getYear(k));
                            pr.setQuantityW1(100);
                            pr.setQuantityW2(100);
                            pr.setQuantityW3(100);
                            pr.setQuantityW4(100);
                            pr.setStore(f_kallang);
                            
                            ProductionRecord pr2 = new ProductionRecord();
                            pr2.setMat(mats.get(3));
                            pr2.setPeriod(wh.getPeriod(k));
                            pr2.setYear(wh.getYear(k));
                            pr2.setQuantityW1(100);
                            pr2.setQuantityW2(100);
                            pr2.setQuantityW3(100);
                            pr2.setQuantityW4(100);
                            pr2.setStore(f_kallang);
                            
                            ProductionRecord pr3 = new ProductionRecord();
                            pr3.setMat(mats.get(27));
                            pr3.setPeriod(wh.getPeriod(k));
                            pr3.setYear(wh.getYear(k));
                            pr3.setQuantityW1(100);
                            pr3.setQuantityW2(100);
                            pr3.setQuantityW3(100);
                            pr3.setQuantityW4(100);
                            pr3.setStore(f_kallang);
                            
                            PurchasePlanningRecord pp = new PurchasePlanningRecord();
                            pp.setProd(p1);
                            pp.setPeriod(wh.getPeriod(k));
                            pp.setYear(wh.getYear(k));
                            pp.setQuantityW1(100);
                            pp.setQuantityW2(100);
                            pp.setQuantityW3(100);
                            pp.setQuantityW4(100);
                            pp.setStore(f_kallang);
                            
                            PurchasePlanningRecord pp2 = new PurchasePlanningRecord();
                            pp2.setProd(p2);
                            pp2.setPeriod(wh.getPeriod(k));
                            pp2.setYear(wh.getYear(k));
                            pp2.setQuantityW1(100);
                            pp2.setQuantityW2(100);
                            pp2.setQuantityW3(100);
                            pp2.setQuantityW4(100);
                            pp2.setStore(f_kallang);
                            
                            em.persist(pr);
                            em.persist(pr2);
                            em.persist(pr3);
                            em.persist(pp);
                            em.persist(pp2);
                        }
                    try {
                        for (int j = 0; j < mats.size(); j++) {
                            for (int i = 0; i < 4; i++) {
                                MrpRecordMaterial m = new MrpRecordMaterial();
                                m.setFac(f_jurong);
                                m.setMat(mats.get(j));
                                m.setOnHand(100);
                                m.setPlanned(100);
                                m.setRequirement(100);
                                m.setReceipt(100);
                                m.setWeek(wh.getPeriodFirstWeek(4)+i);
                                m.setYearid(wh.getYear(4));
                                em.persist(m);
                            }
                        }
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        //em.close();
                    }

                    try {
                        for (int k = 0; k < 7; k++) {
                            TransactionRecord tr = new TransactionRecord();
                            tr.setFacilityId(f_kallang);
                            tr.setCollected(true);
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.MONTH, k + 2);
                            cal.set(Calendar.DATE, 13);
                            cal.set(Calendar.YEAR, 2014);
                            tr.setTransactTime(cal.getTime());
                            em.persist(tr);

                            for (int j = 0; j < 39; j++) {
                                TransactionFurniture tf = new TransactionFurniture();
                                tf.setMatId(mats.get(k));
                                tf.setQuantity((int) (Math.floor((Math.random() * 100)) + 100));
                                tf.setTransactId(tr);
                                em.persist(tf);
                            }
                            TransactionProduct tp = new TransactionProduct();
                            tp.setProd(p1);
                            tp.setQuantity((int) (Math.floor((Math.random() * 50)) + 200));
                            tp.setTransact(tr);
                            em.persist(tp);
                            TransactionProduct tp2 = new TransactionProduct();
                            tp2.setProd(p2);
                            tp2.setQuantity((int) (Math.floor((Math.random() * 50)) + 200));
                            tp2.setTransact(tr);
                            em.persist(tp2);
                        }
                        
                        Staff globalstaff = new Staff();
                        globalstaff.setContact("9312323");
                        globalstaff.setEmail("dingyi@if.com");
                        globalstaff.setFac(f_hougang);
                        globalstaff.setName("Ding Yi");
                        globalstaff.setPassword("d6bb11d15203babea04994f1f076ade5");
                        globalstaff.setRole1("Global HQ");
                        globalstaff.setRole2("System Admin");
                        em.persist(globalstaff);

                        for (int k = 0; k < 7; k++) {
                            TransactionRecord tr = new TransactionRecord();
                            tr.setFacilityId(f_kallang);
                            tr.setCollected(true);
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.MONTH, k + 2);
                            cal.set(Calendar.DATE, 26);
                            cal.set(Calendar.YEAR, 2014);
                            tr.setTransactTime(cal.getTime());
                            em.persist(tr);

                            for (int j = 0; j < 39; j++) {
                                TransactionFurniture tf = new TransactionFurniture();
                                tf.setMatId(mats.get(j));
                                tf.setQuantity((int) (Math.floor((Math.random() * 100)) + 100));
                                tf.setTransactId(tr);
                                em.persist(tf);
                            }
                            TransactionProduct tp = new TransactionProduct();
                            tp.setProd(p1);
                            tp.setQuantity((int) (Math.floor((Math.random() * 50)) + 200));
                            tp.setTransact(tr);
                            em.persist(tp);
                            TransactionProduct tp2 = new TransactionProduct();
                            tp2.setProd(p2);
                            tp2.setQuantity((int) (Math.floor((Math.random() * 50)) + 200));
                            tp2.setTransact(tr);
                            em.persist(tp2);
                        }
                        em.getTransaction().commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                        em.getTransaction().rollback();
                    } finally {
                        em.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (choice.equals("b")) {
            }

        }
    }
}
