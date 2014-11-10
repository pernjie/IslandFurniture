/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dyihoon90
 */
@Entity
@Table(name = "MRPRECORDPRODUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MrpRecordProduct.findAll", query = "SELECT m FROM MrpRecordProduct m"),
    @NamedQuery(name = "MrpRecordProduct.findById", query = "SELECT m FROM MrpRecordProduct m WHERE m.id = :id"),
    @NamedQuery(name = "MrpRecordProduct.findByRequirement", query = "SELECT m FROM MrpRecordProduct m WHERE m.requirement = :requirement"),
    @NamedQuery(name = "MrpRecordProduct.findByReceipt", query = "SELECT m FROM MrpRecordProduct m WHERE m.receipt = :receipt"),
    @NamedQuery(name = "MrpRecordProduct.findByOnHand", query = "SELECT m FROM MrpRecordProduct m WHERE m.onHand = :onHand"),
    @NamedQuery(name = "MrpRecordProduct.findByPlanned", query = "SELECT m FROM MrpRecordProduct m WHERE m.planned = :planned"),
    @NamedQuery(name = "MrpRecordProduct.findByWeek", query = "SELECT m FROM MrpRecordProduct m WHERE m.week = :week"),
    @NamedQuery(name = "MrpRecordProduct.findByYear", query = "SELECT m FROM MrpRecordProduct m WHERE m.year = :year")})
public class MrpRecordProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "REQUIREMENT")
    private Integer requirement;
    @Column(name = "RECEIPT")
    private Integer receipt;
    @Column(name = "ON_HAND")
    private Integer onHand;
    @Column(name = "PLANNED")
    private Integer planned;
    @Column(name = "WEEK")
    private Integer week;
    @Column(name = "YEAR")
    private Integer year;
    @JoinColumn(name = "PROD", referencedColumnName = "ID")
    @ManyToOne
    private Product prod;
    @JoinColumn(name = "FAC", referencedColumnName = "ID")
    @ManyToOne
    private Facility fac;

    public MrpRecordProduct() {
    }

    public MrpRecordProduct(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRequirement() {
        return requirement;
    }

    public void setRequirement(Integer requirement) {
        this.requirement = requirement;
    }

    public Integer getReceipt() {
        return receipt;
    }

    public void setReceipt(Integer receipt) {
        this.receipt = receipt;
    }

    public Integer getOnHand() {
        return onHand;
    }

    public void setOnHand(Integer onHand) {
        this.onHand = onHand;
    }

    public Integer getPlanned() {
        return planned;
    }

    public void setPlanned(Integer planned) {
        this.planned = planned;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Product getProd() {
        return prod;
    }

    public void setProd(Product prod) {
        this.prod = prod;
    }

    public Facility getFac() {
        return fac;
    }

    public void setFac(Facility fac) {
        this.fac = fac;
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
        if (!(object instanceof MrpRecordProduct)) {
            return false;
        }
        MrpRecordProduct other = (MrpRecordProduct) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MrpRecordProduct[ id=" + id + " ]";
    }

}
