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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dyihoon90
 */
@Entity
@Table(name = "TRANSACTIONFURNITURE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionFurniture.findAll", query = "SELECT t FROM TransactionFurniture t"),
    @NamedQuery(name = "TransactionFurniture.findById", query = "SELECT t FROM TransactionFurniture t WHERE t.id = :id"),
    @NamedQuery(name = "TransactionFurniture.findByQuantity", query = "SELECT t FROM TransactionFurniture t WHERE t.quantity = :quantity"),
    @NamedQuery(name = "TransactionFurniture.findByPrice", query = "SELECT t FROM TransactionFurniture t WHERE t.price = :price"),
    @NamedQuery(name = "TransactionFurniture.findByReturnedQty", query = "SELECT t FROM TransactionFurniture t WHERE t.returnedQty = :returnedQty")})
public class TransactionFurniture implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "QUANTITY")
    private Integer quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRICE")
    private Double price;
    @Column(name = "RETURNED_QTY")
    private Integer returnedQty;
    @JoinColumn(name = "TRANSACT_ID", referencedColumnName = "ID")
    @ManyToOne
    private TransactionRecord transactId;
    @JoinColumn(name = "MAT_ID", referencedColumnName = "ID")
    @ManyToOne
    private Material matId;

    public TransactionFurniture() {
    }

    public TransactionFurniture(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getReturnedQty() {
        return returnedQty;
    }

    public void setReturnedQty(Integer returnedQty) {
        this.returnedQty = returnedQty;
    }

    public TransactionRecord getTransactId() {
        return transactId;
    }

    public void setTransactId(TransactionRecord transactId) {
        this.transactId = transactId;
    }

    public Material getMatId() {
        return matId;
    }

    public void setMatId(Material matId) {
        this.matId = matId;
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
        if (!(object instanceof TransactionFurniture)) {
            return false;
        }
        TransactionFurniture other = (TransactionFurniture) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TransactionFurniture[ id=" + id + " ]";
    }
    
}
