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
 * @author dyihoon90
 */
@Entity
@Table(name = "PRODUCTIONORDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductionOrder.findAll", query = "SELECT p FROM ProductionOrder p"),
    @NamedQuery(name = "ProductionOrder.findById", query = "SELECT p FROM ProductionOrder p WHERE p.id = :id"),
    @NamedQuery(name = "ProductionOrder.findByMonth", query = "SELECT p FROM ProductionOrder p WHERE p.month = :month"),
    @NamedQuery(name = "ProductionOrder.findByYearId", query = "SELECT p FROM ProductionOrder p WHERE p.yearId = :yearId"),
    @NamedQuery(name = "ProductionOrder.findByQuantity", query = "SELECT p FROM ProductionOrder p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "ProductionOrder.findByStatus", query = "SELECT p FROM ProductionOrder p WHERE p.status = :status")})
public class ProductionOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "MONTH")
    private Integer month;
    @Column(name = "YEARID")
    private Integer yearId;
    @Column(name = "QUANTITY")
    private Integer quantity;
    @Size(max = 64)
    @Column(name = "STATUS")
    private String status;
    @JoinColumn(name = "MAT_ID", referencedColumnName = "ID")
    @ManyToOne
    private Material matId;
    @JoinColumn(name = "STORE_ID", referencedColumnName = "ID")
    @ManyToOne
    private Facility storeId;

    public ProductionOrder() {
    }

    public ProductionOrder(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYearId() {
        return yearId;
    }

    public void setYearid(Integer yearId) {
        this.yearId = yearId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Material getMatId() {
        return matId;
    }

    public void setMatId(Material matId) {
        this.matId = matId;
    }

    public Facility getStoreId() {
        return storeId;
    }

    public void setStoreId(Facility storeId) {
        this.storeId = storeId;
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
        if (!(object instanceof ProductionOrder)) {
            return false;
        }
        ProductionOrder other = (ProductionOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProductionOrder[ id=" + id + " ]";
    }
    
}
