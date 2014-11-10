/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dyihoon90
 */
@Entity
@Table(name = "PROMOTIONALCODE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PromotionalCode.findAll", query = "SELECT p FROM PromotionalCode p"),
    @NamedQuery(name = "PromotionalCode.findByCode", query = "SELECT p FROM PromotionalCode p WHERE p.code = :code"),
    @NamedQuery(name = "PromotionalCode.findByDescription", query = "SELECT p FROM PromotionalCode p WHERE p.description = :description"),
    @NamedQuery(name = "PromotionalCode.findByPercentDisc", query = "SELECT p FROM PromotionalCode p WHERE p.percentDisc = :percentDisc"),
    @NamedQuery(name = "PromotionalCode.findByAbsDisc", query = "SELECT p FROM PromotionalCode p WHERE p.absDisc = :absDisc"),
    @NamedQuery(name = "PromotionalCode.findByStartDate", query = "SELECT p FROM PromotionalCode p WHERE p.startDate = :startDate"),
    @NamedQuery(name = "PromotionalCode.findByEndDate", query = "SELECT p FROM PromotionalCode p WHERE p.endDate = :endDate")})
public class PromotionalCode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Size(min = 1, max = 255)
    @Column(name = "CODE")
    private String code;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PERCENT_DISC")
    private Double percentDisc;
    @Column(name = "ABS_DISC")
    private Double absDisc;
    @Basic(optional = false)
    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @OneToMany(mappedBy = "promoCode")
    private Collection<TransactionRecord> transactionRecordCollection;

    public PromotionalCode() {
    }

    public PromotionalCode(String code) {
        this.code = code;
    }

    public PromotionalCode(String code, Date startDate, Date endDate) {
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPercentDisc() {
        return percentDisc;
    }

    public void setPercentDisc(Double percentDisc) {
        this.percentDisc = percentDisc;
    }

    public Double getAbsDisc() {
        return absDisc;
    }

    public void setAbsDisc(Double absDisc) {
        this.absDisc = absDisc;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @XmlTransient
    public Collection<TransactionRecord> getTransactionrecordCollection() {
        return transactionRecordCollection;
    }

    public void setTransactionrecordCollection(Collection<TransactionRecord> transactionRecordCollection) {
        this.transactionRecordCollection = transactionRecordCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PromotionalCode)) {
            return false;
        }
        PromotionalCode other = (PromotionalCode) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PromotionalCode[ code=" + code + " ]";
    }
    
}
