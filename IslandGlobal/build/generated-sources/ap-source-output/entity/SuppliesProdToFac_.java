package entity;

import entity.Facility;
import entity.Product;
import entity.Supplier;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-10T19:15:58")
@StaticMetamodel(SuppliesProdToFac.class)
public class SuppliesProdToFac_ { 

    public static volatile SingularAttribute<SuppliesProdToFac, Long> id;
    public static volatile SingularAttribute<SuppliesProdToFac, Facility> fac;
    public static volatile SingularAttribute<SuppliesProdToFac, Double> unitPrice;
    public static volatile SingularAttribute<SuppliesProdToFac, Supplier> sup;
    public static volatile SingularAttribute<SuppliesProdToFac, Integer> leadTime;
    public static volatile SingularAttribute<SuppliesProdToFac, Integer> lotSize;
    public static volatile SingularAttribute<SuppliesProdToFac, Product> prod;

}