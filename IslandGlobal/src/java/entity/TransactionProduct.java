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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dyihoon90
 */
@Entity
@Table(name = "TRANSACTIONPRODUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionProduct.findAll", query = "SELECT t FROM TransactionProduct t"),
    @NamedQuery(name = "TransactionProduct.findById", query = "SELECT t FROM TransactionProduct t WHERE t.id = :id"),
    @NamedQuery(name = "TransactionProduct.findByQuantity", query = "SELECT t FROM TransactionProduct t WHERE t.quantity = :quantity"),
    @NamedQuery(name = "TransactionProduct.findByPrice", query = "SELECT t FROM TransactionProduct t WHERE t.price = :price")})
public class TransactionProduct implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "QUANTITY")
    private Integer quantity;
    @Column(name = "PRICE")
    private Double price;
    @JoinColumn(name = "TRANSACT", referencedColumnName = "ID")
    @ManyToOne
    private TransactionRecord transact;
    @JoinColumn(name = "PROD", referencedColumnName = "ID")
    @ManyToOne
    private Product prod;

    public TransactionProduct() {
    }

    public TransactionProduct(Long id) {
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

    public TransactionRecord getTransact() {
        return transact;
    }

    public void setTransact(TransactionRecord transact) {
        this.transact = transact;
    }

    public Product getProd() {
        return prod;
    }

    public void setProd(Product prod) {
        this.prod = prod;
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
        if (!(object instanceof TransactionProduct)) {
            return false;
        }
        TransactionProduct other = (TransactionProduct) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TransactionProduct[ id=" + id + " ]";
    }
    
}
