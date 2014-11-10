package entity;

import entity.Material;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-10T19:15:58")
@StaticMetamodel(Component.class)
public class Component_ { 

    public static volatile SingularAttribute<Component, Long> id;
    public static volatile SingularAttribute<Component, Material> consistOf;
    public static volatile SingularAttribute<Component, Integer> quantity;
    public static volatile SingularAttribute<Component, Material> matId;

}