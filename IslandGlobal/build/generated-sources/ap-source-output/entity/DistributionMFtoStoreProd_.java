package entity;

import entity.Facility;
import entity.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-10T19:15:58")
@StaticMetamodel(DistributionMFtoStoreProd.class)
public class DistributionMFtoStoreProd_ { 

    public static volatile SingularAttribute<DistributionMFtoStoreProd, Long> id;
    public static volatile SingularAttribute<DistributionMFtoStoreProd, Facility> store;
    public static volatile SingularAttribute<DistributionMFtoStoreProd, Facility> mf;
    public static volatile SingularAttribute<DistributionMFtoStoreProd, Product> prod;

}