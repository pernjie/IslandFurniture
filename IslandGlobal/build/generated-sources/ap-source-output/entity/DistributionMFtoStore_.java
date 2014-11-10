package entity;

import entity.Facility;
import entity.Material;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-10T19:15:58")
@StaticMetamodel(DistributionMFtoStore.class)
public class DistributionMFtoStore_ { 

    public static volatile SingularAttribute<DistributionMFtoStore, Long> id;
    public static volatile SingularAttribute<DistributionMFtoStore, Facility> store;
    public static volatile SingularAttribute<DistributionMFtoStore, Material> mat;
    public static volatile SingularAttribute<DistributionMFtoStore, Facility> mf;

}