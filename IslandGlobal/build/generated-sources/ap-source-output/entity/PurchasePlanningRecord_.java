package entity;

import entity.Facility;
import entity.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-10T19:15:58")
@StaticMetamodel(PurchasePlanningRecord.class)
public class PurchasePlanningRecord_ { 

    public static volatile SingularAttribute<PurchasePlanningRecord, Long> id;
    public static volatile SingularAttribute<PurchasePlanningRecord, Integer> quantityW4;
    public static volatile SingularAttribute<PurchasePlanningRecord, Integer> quantityW5;
    public static volatile SingularAttribute<PurchasePlanningRecord, Facility> store;
    public static volatile SingularAttribute<PurchasePlanningRecord, Integer> year;
    public static volatile SingularAttribute<PurchasePlanningRecord, Integer> period;
    public static volatile SingularAttribute<PurchasePlanningRecord, Integer> quantityW1;
    public static volatile SingularAttribute<PurchasePlanningRecord, Integer> quantityW2;
    public static volatile SingularAttribute<PurchasePlanningRecord, Integer> quantityW3;
    public static volatile SingularAttribute<PurchasePlanningRecord, Product> prod;

}