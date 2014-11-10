package entity;

import entity.Facility;
import entity.Material;
import entity.Supplier;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-10T19:15:58")
@StaticMetamodel(SuppliesMatToFac.class)
public class SuppliesMatToFac_ { 

    public static volatile SingularAttribute<SuppliesMatToFac, Long> id;
    public static volatile SingularAttribute<SuppliesMatToFac, Material> mat;
    public static volatile SingularAttribute<SuppliesMatToFac, Facility> fac;
    public static volatile SingularAttribute<SuppliesMatToFac, Double> unitPrice;
    public static volatile SingularAttribute<SuppliesMatToFac, Supplier> sup;
    public static volatile SingularAttribute<SuppliesMatToFac, Integer> leadTime;
    public static volatile SingularAttribute<SuppliesMatToFac, Integer> lotSize;

}