package entity;

import entity.Facility;
import entity.Material;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-10T19:15:58")
@StaticMetamodel(ProductionOrder.class)
public class ProductionOrder_ { 

    public static volatile SingularAttribute<ProductionOrder, Long> id;
    public static volatile SingularAttribute<ProductionOrder, String> status;
    public static volatile SingularAttribute<ProductionOrder, Integer> month;
    public static volatile SingularAttribute<ProductionOrder, Integer> quantity;
    public static volatile SingularAttribute<ProductionOrder, Integer> yearId;
    public static volatile SingularAttribute<ProductionOrder, Facility> storeId;
    public static volatile SingularAttribute<ProductionOrder, Material> matId;

}