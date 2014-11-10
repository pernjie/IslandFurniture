package entity;

import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-10T19:15:58")
@StaticMetamodel(Sellsfurniture.class)
public class Sellsfurniture_ { 

    public static volatile SingularAttribute<Sellsfurniture, BigInteger> facilityID;
    public static volatile SingularAttribute<Sellsfurniture, BigInteger> matID;
    public static volatile SingularAttribute<Sellsfurniture, Integer> lowerthreshold;
    public static volatile SingularAttribute<Sellsfurniture, String> location;
    public static volatile SingularAttribute<Sellsfurniture, Integer> inventory;
    public static volatile SingularAttribute<Sellsfurniture, Integer> upperthreshold;
    public static volatile SingularAttribute<Sellsfurniture, String> matName;
    public static volatile SingularAttribute<Sellsfurniture, String> zone;
    public static volatile SingularAttribute<Sellsfurniture, Long> sellID;
    public static volatile SingularAttribute<Sellsfurniture, String> found;

}