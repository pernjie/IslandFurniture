/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Owner
 */
@Entity
@Table(name = "facility")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Facility.findAll", query = "SELECT f FROM Facility f"),
    @NamedQuery(name = "Facility.findAllGhq", query = "SELECT f FROM Facility f WHERE f.type = 'Global HQ'"),
    @NamedQuery(name = "Facility.findAllMf", query = "SELECT f FROM Facility f WHERE f.type = 'Manufacturing'"),
    @NamedQuery(name = "Facility.findAllStore", query = "SELECT f FROM Facility f WHERE f.type = 'Store'"),
    @NamedQuery(name = "Facility.findAllOnline", query = "SELECT f FROM Facility f WHERE f.type = 'Online'"),
    @NamedQuery(name = "Facility.findById", query = "SELECT f FROM Facility f WHERE f.id = :id"),
    @NamedQuery(name = "Facility.findByName", query = "SELECT f FROM Facility f WHERE f.name = :name"),
    @NamedQuery(name = "Facility.findByPostalCode", query = "SELECT f FROM Facility f WHERE f.postalCode = :postalCode"),
    @NamedQuery(name = "Facility.findByCity", query = "SELECT f FROM Facility f WHERE f.city = :city"),
    @NamedQuery(name = "Facility.findByCountry", query = "SELECT f FROM Facility f WHERE f.country = :country"),
    @NamedQuery(name = "Facility.findByType", query = "SELECT f FROM Facility f WHERE f.type = :type")})
public class Facility implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "type")
    private String type;
    
    public Facility() {
    }

    public Facility(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Facility)) {
            return false;
        }
        Facility other = (Facility) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Facility[ id=" + id + " ]";
    }
    
}
