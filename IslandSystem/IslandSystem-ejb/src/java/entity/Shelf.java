/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import enumerator.InvenLoc;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "shelf")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Shelf.findAll", query = "SELECT s FROM Shelf s"),
    @NamedQuery(name = "Shelf.findById", query = "SELECT s FROM Shelf s WHERE s.id = :id"),
    @NamedQuery(name = "Shelf.findByFac", query = "SELECT s FROM Shelf s WHERE s.fac = :fac"),
    @NamedQuery(name = "Shelf.findByZone", query = "SELECT s FROM Shelf s WHERE s.zone = :zone"),
    @NamedQuery(name = "Shelf.findByShelf", query = "SELECT s FROM Shelf s WHERE s.shelf = :shelf"),
    @NamedQuery(name = "Shelf.findByShelfType", query = "SELECT s FROM Shelf s WHERE s.shelfType = :shelfType"),
    @NamedQuery(name = "Shelf.getZone", query = "SELECT  s FROM Shelf s WHERE s.fac = :fac GROUP BY s.zone")})

public class Shelf implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @JoinColumn(name = "FAC", referencedColumnName = "ID")
    @ManyToOne
    private Facility fac;
    @Size(max = 2)
    @Column(name = "location")
    private InvenLoc location;
    @Column(name = "zone")
    private String zone;
    @Column(name = "shelf")
    private Integer shelf;
    @JoinColumn(name = "SHELFTYPE", referencedColumnName = "ID")
    @ManyToOne
    private ShelfType shelfType;
    

    public Shelf() {
    }

    public Shelf(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Facility getFac() {
        return fac;
    }

    public void setFac(Facility fac) {
        this.fac = fac;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Integer getShelf() {
        return shelf;
    }

    public void setShelf(Integer shelf) {
        this.shelf = shelf;
    }

    public ShelfType getShelfType() {
        return shelfType;
    }

    public void setShelfType(ShelfType shelfType) {
        this.shelfType = shelfType;
    }

    public InvenLoc getLocation() {
        return location;
    }

    public void setLocation(InvenLoc location) {
        this.location = location;
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
        if (!(object instanceof Shelf)) {
            return false;
        }
        Shelf other = (Shelf) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Shelf[ id=" + id + " ]";
    }
    
}
