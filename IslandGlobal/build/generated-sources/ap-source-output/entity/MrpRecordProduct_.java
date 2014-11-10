package entity;

import entity.Facility;
import entity.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-10T19:15:58")
@StaticMetamodel(MrpRecordProduct.class)
public class MrpRecordProduct_ { 

    public static volatile SingularAttribute<MrpRecordProduct, Long> id;
    public static volatile SingularAttribute<MrpRecordProduct, Integer> requirement;
    public static volatile SingularAttribute<MrpRecordProduct, Facility> fac;
    public static volatile SingularAttribute<MrpRecordProduct, Integer> receipt;
    public static volatile SingularAttribute<MrpRecordProduct, Integer> year;
    public static volatile SingularAttribute<MrpRecordProduct, Integer> onHand;
    public static volatile SingularAttribute<MrpRecordProduct, Integer> planned;
    public static volatile SingularAttribute<MrpRecordProduct, Integer> week;
    public static volatile SingularAttribute<MrpRecordProduct, Product> prod;

}