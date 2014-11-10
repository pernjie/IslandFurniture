package entity;

import entity.Material;
import entity.TransactionRecord;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-10T19:15:58")
@StaticMetamodel(TransactionFurniture.class)
public class TransactionFurniture_ { 

    public static volatile SingularAttribute<TransactionFurniture, Long> id;
    public static volatile SingularAttribute<TransactionFurniture, Double> price;
    public static volatile SingularAttribute<TransactionFurniture, TransactionRecord> transactId;
    public static volatile SingularAttribute<TransactionFurniture, Integer> quantity;
    public static volatile SingularAttribute<TransactionFurniture, Integer> returnedQty;
    public static volatile SingularAttribute<TransactionFurniture, Material> matId;

}