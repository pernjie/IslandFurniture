package entity;

import entity.Facility;
import entity.Material;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-10T19:15:58")
@StaticMetamodel(ProductionRecord.class)
public class ProductionRecord_ { 

    public static volatile SingularAttribute<ProductionRecord, Long> id;
    public static volatile SingularAttribute<ProductionRecord, Integer> quantityW4;
    public static volatile SingularAttribute<ProductionRecord, Integer> quantityW5;
    public static volatile SingularAttribute<ProductionRecord, Facility> store;
    public static volatile SingularAttribute<ProductionRecord, Material> mat;
    public static volatile SingularAttribute<ProductionRecord, Integer> year;
    public static volatile SingularAttribute<ProductionRecord, Integer> period;
    public static volatile SingularAttribute<ProductionRecord, Integer> quantityW1;
    public static volatile SingularAttribute<ProductionRecord, Integer> quantityW2;
    public static volatile SingularAttribute<ProductionRecord, Integer> quantityW3;

}