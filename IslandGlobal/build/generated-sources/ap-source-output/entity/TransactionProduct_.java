package entity;

import entity.Product;
import entity.TransactionRecord;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-10T19:15:58")
@StaticMetamodel(TransactionProduct.class)
public class TransactionProduct_ { 

    public static volatile SingularAttribute<TransactionProduct, Long> id;
    public static volatile SingularAttribute<TransactionProduct, Double> price;
    public static volatile SingularAttribute<TransactionProduct, TransactionRecord> transact;
    public static volatile SingularAttribute<TransactionProduct, Integer> quantity;
    public static volatile SingularAttribute<TransactionProduct, Product> prod;

}