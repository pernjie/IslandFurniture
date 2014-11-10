package entity;

import entity.Facility;
import entity.Log;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-10T19:15:58")
@StaticMetamodel(Staff.class)
public class Staff_ { 

    public static volatile SingularAttribute<Staff, Long> id;
    public static volatile SingularAttribute<Staff, String> email;
    public static volatile SingularAttribute<Staff, String> name;
    public static volatile SingularAttribute<Staff, Facility> fac;
    public static volatile SingularAttribute<Staff, String> role3;
    public static volatile SingularAttribute<Staff, String> role2;
    public static volatile SingularAttribute<Staff, String> role1;
    public static volatile SingularAttribute<Staff, String> password;
    public static volatile SingularAttribute<Staff, String> contact;
    public static volatile CollectionAttribute<Staff, Log> log;

}