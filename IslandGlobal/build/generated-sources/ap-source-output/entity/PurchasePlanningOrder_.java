package entity;

import entity.Facility;
import entity.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-10T19:15:58")
@StaticMetamodel(PurchasePlanningOrder.class)
public class PurchasePlanningOrder_ { 

    public static volatile SingularAttribute<PurchasePlanningOrder, Long> id;
    public static volatile SingularAttribute<PurchasePlanningOrder, Facility> store;
    public static volatile SingularAttribute<PurchasePlanningOrder, String> status;
    public static volatile SingularAttribute<PurchasePlanningOrder, Integer> year;
    public static volatile SingularAttribute<PurchasePlanningOrder, Integer> quantity;
    public static volatile SingularAttribute<PurchasePlanningOrder, Integer> period;
    public static volatile SingularAttribute<PurchasePlanningOrder, Product> prod;

}