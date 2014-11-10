package entity;

import entity.Facility;
import entity.PromotionalCode;
import entity.TransactionFurniture;
import entity.TransactionProduct;
import entity.TransactionService;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-10T19:15:58")
@StaticMetamodel(TransactionRecord.class)
public class TransactionRecord_ { 

    public static volatile SingularAttribute<TransactionRecord, Long> id;
    public static volatile SingularAttribute<TransactionRecord, BigInteger> custId;
    public static volatile CollectionAttribute<TransactionRecord, TransactionFurniture> transactionfurnitureCollection;
    public static volatile SingularAttribute<TransactionRecord, Boolean> collected;
    public static volatile CollectionAttribute<TransactionRecord, TransactionService> transactionserviceCollection;
    public static volatile SingularAttribute<TransactionRecord, Date> transactTime;
    public static volatile SingularAttribute<TransactionRecord, Double> totalAmount;
    public static volatile CollectionAttribute<TransactionRecord, TransactionProduct> transactionproductCollection;
    public static volatile SingularAttribute<TransactionRecord, Long> posId;
    public static volatile SingularAttribute<TransactionRecord, Facility> facilityId;
    public static volatile SingularAttribute<TransactionRecord, PromotionalCode> promoCode;
    public static volatile SingularAttribute<TransactionRecord, Integer> redemption;

}