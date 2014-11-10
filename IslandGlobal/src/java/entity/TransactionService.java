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
@Table(name = "TRANSACTIONSERVICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionService.findAll", query = "SELECT t FROM TransactionService t"),
    @NamedQuery(name = "TransactionService.findById", query = "SELECT t FROM TransactionService t WHERE t.id = :id"),
    @NamedQuery(name = "TransactionService.findByQuantity", query = "SELECT t FROM TransactionService t WHERE t.quantity = :quantity"),
    @NamedQuery(name = "TransactionService.findByPrice", query = "SELECT t FROM TransactionService t WHERE t.price = :price")})
public class TransactionService implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "QUANTITY")
    private BigInteger quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRICE")
    private Double price;
    @JoinColumn(name = "TRANSACT_ID", referencedColumnName = "ID")
    @ManyToOne
    private TransactionRecord transactId;
    @JoinColumn(name = "SERVICE_ID", referencedColumnName = "ID")
    @ManyToOne
    private Product serviceId;

    public TransactionService() {
    }

    public TransactionService(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public TransactionRecord getTransactId() {
        return transactId;
    }

    public void setTransactId(TransactionRecord transactId) {
        this.transactId = transactId;
    }

    public Product getServiceId() {
        return serviceId;
    }

    public void setServiceId(Product serviceId) {
        this.serviceId = serviceId;
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
        if (!(object instanceof TransactionService)) {
            return false;
        }
        TransactionService other = (TransactionService) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TransactionService[ id=" + id + " ]";
    }
    
}
