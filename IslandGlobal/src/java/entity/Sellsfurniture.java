/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nataliegoh
 */
@Entity
@Table(name = "sellsfurniture")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sellsfurniture.findAll", query = "SELECT s FROM Sellsfurniture s"),
    @NamedQuery(name = "Sellsfurniture.findBySellID", query = "SELECT s FROM Sellsfurniture s WHERE s.sellID = :sellID"),
    @NamedQuery(name = "Sellsfurniture.findByFacilityID", query = "SELECT s FROM Sellsfurniture s WHERE s.facilityID = :facilityID"),
    @NamedQuery(name = "Sellsfurniture.findByMatID", query = "SELECT s FROM Sellsfurniture s WHERE s.matID = :matID"),
    @NamedQuery(name = "Sellsfurniture.findByMatName", query = "SELECT s FROM Sellsfurniture s WHERE s.matName = :matName"),
    @NamedQuery(name = "Sellsfurniture.findByFound", query = "SELECT s FROM Sellsfurniture s WHERE s.found = :found"),
    @NamedQuery(name = "Sellsfurniture.findByZone", query = "SELECT s FROM Sellsfurniture s WHERE s.zone = :zone"),
    @NamedQuery(name = "Sellsfurniture.findByLocation", query = "SELECT s FROM Sellsfurniture s WHERE s.location = :location"),
    @NamedQuery(name = "Sellsfurniture.findByLowerthreshold", query = "SELECT s FROM Sellsfurniture s WHERE s.lowerthreshold = :lowerthreshold"),
    @NamedQuery(name = "Sellsfurniture.findByUpperthreshold", query = "SELECT s FROM Sellsfurniture s WHERE s.upperthreshold = :upperthreshold"),
    @NamedQuery(name = "Sellsfurniture.findByInventory", query = "SELECT s FROM Sellsfurniture s WHERE s.inventory = :inventory")})
public class Sellsfurniture implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sellID")
    private Long sellID;
    @Column(name = "facility_ID")
    private BigInteger facilityID;
    @Column(name = "mat_ID")
    private BigInteger matID;
    @Size(max = 255)
    @Column(name = "mat_name")
    private String matName;
    @Size(max = 255)
    @Column(name = "found")
    private String found;
    @Basic(optional = false)
    @Size(min = 1, max = 255)
    @Column(name = "zone")
    private String zone;
    @Basic(optional = false)
    @Size(min = 1, max = 255)
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @Column(name = "lowerthreshold")
    private int lowerthreshold;
    @Basic(optional = false)
    @Column(name = "upperthreshold")
    private int upperthreshold;
    @Basic(optional = false)
    @Column(name = "inventory")
    private int inventory;

    public Sellsfurniture() {
    }

    public Sellsfurniture(Long sellID) {
        this.sellID = sellID;
    }

    public Sellsfurniture(Long sellID, String zone, String location, int lowerthreshold, int upperthreshold, int inventory) {
        this.sellID = sellID;
        this.zone = zone;
        this.location = location;
        this.lowerthreshold = lowerthreshold;
        this.upperthreshold = upperthreshold;
        this.inventory = inventory;
    }

    public Long getSellID() {
        return sellID;
    }

    public void setSellID(Long sellID) {
        this.sellID = sellID;
    }

    public BigInteger getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(BigInteger facilityID) {
        this.facilityID = facilityID;
    }

    public BigInteger getMatID() {
        return matID;
    }

    public void setMatID(BigInteger matID) {
        this.matID = matID;
    }

    public String getMatName() {
        return matName;
    }

    public void setMatName(String matName) {
        this.matName = matName;
    }

    public String getFound() {
        return found;
    }

    public void setFound(String found) {
        this.found = found;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLowerthreshold() {
        return lowerthreshold;
    }

    public void setLowerthreshold(int lowerthreshold) {
        this.lowerthreshold = lowerthreshold;
    }

    public int getUpperthreshold() {
        return upperthreshold;
    }

    public void setUpperthreshold(int upperthreshold) {
        this.upperthreshold = upperthreshold;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sellID != null ? sellID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sellsfurniture)) {
            return false;
        }
        Sellsfurniture other = (Sellsfurniture) object;
        if ((this.sellID == null && other.sellID != null) || (this.sellID != null && !this.sellID.equals(other.sellID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Sellsfurniture[ sellID=" + sellID + " ]";
    }
    
}
