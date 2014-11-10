package entity;

import entity.TransactionRecord;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-10T19:15:58")
@StaticMetamodel(PromotionalCode.class)
public class PromotionalCode_ { 

    public static volatile SingularAttribute<PromotionalCode, Double> absDisc;
    public static volatile SingularAttribute<PromotionalCode, Date> startDate;
    public static volatile SingularAttribute<PromotionalCode, String> description;
    public static volatile SingularAttribute<PromotionalCode, Double> percentDisc;
    public static volatile CollectionAttribute<PromotionalCode, TransactionRecord> transactionRecordCollection;
    public static volatile SingularAttribute<PromotionalCode, Date> endDate;
    public static volatile SingularAttribute<PromotionalCode, String> code;

}