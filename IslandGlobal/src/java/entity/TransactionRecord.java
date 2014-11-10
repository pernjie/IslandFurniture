/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dyihoon90
 */
@Entity
@Table(name = "TRANSACTIONRECORD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionRecord.findAll", query = "SELECT t FROM TransactionRecord t"),
    @NamedQuery(name = "TransactionRecord.findById", query = "SELECT t FROM TransactionRecord t WHERE t.id = :id"),
    @NamedQuery(name = "TransactionRecord.findByTransactTime", query = "SELECT t FROM TransactionRecord t WHERE t.transactTime = :transactTime"),
    @NamedQuery(name = "TransactionRecord.findByPosId", query = "SELECT t FROM TransactionRecord t WHERE t.posId = :posId"),
    @NamedQuery(name = "TransactionRecord.findByCustId", query = "SELECT t FROM TransactionRecord t WHERE t.custId = :custId"),
    @NamedQuery(name = "TransactionRecord.findByCollected", query = "SELECT t FROM TransactionRecord t WHERE t.collected = :collected"),
    @NamedQuery(name = "TransactionRecord.findByRedemption", query = "SELECT t FROM TransactionRecord t WHERE t.redemption = :redemption"),
    @NamedQuery(name = "TransactionRecord.findByTotalAmount", query = "SELECT t FROM TransactionRecord t WHERE t.totalAmount = :totalAmount")})
public class TransactionRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Basic(optional = false)
    @Column(name = "TRANSACT_TIME")
    @Temporal(TemporalType.DATE)
    private Date transactTime;
    @Basic(optional = false)
    @Column(name = "POS_ID")
    private long posId;
    @Column(name = "CUST_ID")
    private BigInteger custId;
    @Basic(optional = false)
    @Column(name = "COLLECTED")
    private Boolean collected;
    @Column(name = "REDEMPTION")
    private Integer redemption;
    @Basic(optional = false)
    @Column(name = "TOTAL_AMOUNT")
    private double totalAmount;
    @OneToMany(mappedBy = "transactId")
    private Collection<TransactionFurniture> transactionfurnitureCollection;
    @OneToMany(mappedBy = "transact")
    private Collection<TransactionProduct> transactionproductCollection;
    @OneToMany(mappedBy = "transactId")
    private Collection<TransactionService> transactionserviceCollection;
    @JoinColumn(name = "PROMO_CODE", referencedColumnName = "CODE")
    @ManyToOne
    private PromotionalCode promoCode;
    @JoinColumn(name = "FACILITY_ID", referencedColumnName = "ID")
    @ManyToOne
    private Facility facilityId;

    public TransactionRecord() {
    }

    public TransactionRecord(Long id) {
        this.id = id;
    }

    public TransactionRecord(Long id, Date transactTime, long posId, Boolean collected, double totalAmount) {
        this.id = id;
        this.transactTime = transactTime;
        this.posId = posId;
        this.collected = collected;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTransactTime() {
        return transactTime;
    }

    public void setTransactTime(Date transactTime) {
        this.transactTime = transactTime;
    }

    public long getPosId() {
        return posId;
    }

    public void setPosId(long posId) {
        this.posId = posId;
    }

    public BigInteger getCustId() {
        return custId;
    }

    public void setCustId(BigInteger custId) {
        this.custId = custId;
    }

    public Boolean getCollected() {
        return collected;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    public Integer getRedemption() {
        return redemption;
    }

    public void setRedemption(Integer redemption) {
        this.redemption = redemption;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @XmlTransient
    public Collection<TransactionFurniture> getTransactionFurnitureCollection() {
        return transactionfurnitureCollection;
    }

    public void setTransactionFurnitureCollection(Collection<TransactionFurniture> transactionfurnitureCollection) {
        this.transactionfurnitureCollection = transactionfurnitureCollection;
    }

    @XmlTransient
    public Collection<TransactionProduct> getTransactionProductCollection() {
        return transactionproductCollection;
    }

    public void setTransactionProductCollection(Collection<TransactionProduct> transactionproductCollection) {
        this.transactionproductCollection = transactionproductCollection;
    }

    @XmlTransient
    public Collection<TransactionService> getTransactionServiceCollection() {
        return transactionserviceCollection;
    }

    public void setTransactionServiceCollection(Collection<TransactionService> transactionserviceCollection) {
        this.transactionserviceCollection = transactionserviceCollection;
    }

    public PromotionalCode getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(PromotionalCode promoCode) {
        this.promoCode = promoCode;
    }

    public Facility getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Facility facilityId) {
        this.facilityId = facilityId;
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
        if (!(object instanceof TransactionRecord)) {
            return false;
        }
        TransactionRecord other = (TransactionRecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TransactionRecord[ id=" + id + " ]";
    }
    
}
