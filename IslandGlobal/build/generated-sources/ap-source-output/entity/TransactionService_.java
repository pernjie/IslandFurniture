package entity;

import entity.Product;
import entity.TransactionRecord;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-10T19:15:58")
@StaticMetamodel(TransactionService.class)
public class TransactionService_ { 

    public static volatile SingularAttribute<TransactionService, Long> id;
    public static volatile SingularAttribute<TransactionService, Double> price;
    public static volatile SingularAttribute<TransactionService, Product> serviceId;
    public static volatile SingularAttribute<TransactionService, TransactionRecord> transactId;
    public static volatile SingularAttribute<TransactionService, BigInteger> quantity;

}