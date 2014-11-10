package entity;

import entity.Facility;
import entity.Material;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-11-10T19:15:58")
@StaticMetamodel(MrpRecordMaterial.class)
public class MrpRecordMaterial_ { 

    public static volatile SingularAttribute<MrpRecordMaterial, Long> id;
    public static volatile SingularAttribute<MrpRecordMaterial, Integer> requirement;
    public static volatile SingularAttribute<MrpRecordMaterial, Material> mat;
    public static volatile SingularAttribute<MrpRecordMaterial, Facility> fac;
    public static volatile SingularAttribute<MrpRecordMaterial, Integer> receipt;
    public static volatile SingularAttribute<MrpRecordMaterial, Integer> yearId;
    public static volatile SingularAttribute<MrpRecordMaterial, Integer> onHand;
    public static volatile SingularAttribute<MrpRecordMaterial, Integer> planned;
    public static volatile SingularAttribute<MrpRecordMaterial, Integer> week;

}