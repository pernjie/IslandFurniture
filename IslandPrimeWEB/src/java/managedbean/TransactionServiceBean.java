/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Customer;
import entity.Facility;
import entity.Material;
import entity.Region;
import entity.Service;
import entity.TransactionRecord;
import entity.TransactionService;
import entity.TransactionRecord;
import enumerator.SvcCat;
import enumerator.SvcRecStatus;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.primefaces.event.RowEditEvent;
import services.SvcRecService;
import session.stateless.OpCrmBean;
import session.stateless.OpCrmBeanLocal;
import util.exception.DetailsConflictException;
import util.exception.ReferenceConstraintException;

/**
 *
 * @author dyihoon90
 */
@ManagedBean(name = "transactionServiceBean")
@SessionScoped
public class TransactionServiceBean implements Serializable {

    @EJB
    private OpCrmBeanLocal ocb;
    private Long id;
    private String userEmail;
    private Facility userFacility;
    private Region userRegion;
    private Long newTransactionRecordId;
    private Long newTransactionServiceId;
    private String statusMessage;
    private TransactionRecord selectedTransactionRecord;
    private TransactionService selectedTransactionService;
    private List<TransactionService> transactionServices;
    private List<TransactionService> filteredTransactionServices;
    private Boolean transantionServicesIsNotEmpty;

    @PostConstruct
    public void init() {
        userEmail = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("email");
        selectedTransactionRecord = (TransactionRecord) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedTransactionRecord");
        transactionServices = ocb.getTransactionServices(selectedTransactionRecord);
        transantionServicesIsNotEmpty = !transactionServices.isEmpty();
        userFacility = ocb.getUserFacility(userEmail);
        userRegion = ocb.getUserRegion(userFacility);

    }

    public boolean transantionServicesIsNotEmpty() {
        return !transactionServices.isEmpty();
    }

    public List<TransactionService> getTransactionServices() {
        return transactionServices;
    }

    public void setTransactionServices(List<TransactionService> transactionServices) {
        this.transactionServices = transactionServices;
    }

    public Long getNewTransactionServiceId() {
        return newTransactionServiceId;
    }

    public void setNewTransactionServiceId(Long newTransactionServiceId) {
        this.newTransactionServiceId = newTransactionServiceId;
    }

    public OpCrmBeanLocal getOcb() {
        return ocb;
    }

    public void setOcb(OpCrmBeanLocal ocb) {
        this.ocb = ocb;
    }

    public Boolean getTransantionServicesIsNotEmpty() {
        return transantionServicesIsNotEmpty;
    }

    public void setTransantionServicesIsNotEmpty(Boolean transantionServicesIsNotEmpty) {
        this.transantionServicesIsNotEmpty = transantionServicesIsNotEmpty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Facility getUserFacility() {
        return userFacility;
    }

    public void setUserFacility(Facility userFacility) {
        this.userFacility = userFacility;
    }

    public Long getNewTransactionRecordId() {
        return newTransactionRecordId;
    }

    public void setNewTransactionRecordId(Long newTransactionRecordId) {
        this.newTransactionRecordId = newTransactionRecordId;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public TransactionRecord getSelectedTransactionRecord() {
        return selectedTransactionRecord;
    }

    public void setSelectedTransactionRecord(TransactionRecord selectedTransactionRecord) {
        this.selectedTransactionRecord = selectedTransactionRecord;
    }

    public Region getUserRegion() {
        return userRegion;
    }

    public void setUserRegion(Region userRegion) {
        this.userRegion = userRegion;
    }

    public List<TransactionService> getFilteredTransactionServices() {
        return filteredTransactionServices;
    }

    public void setFilteredTransactionServices(List<TransactionService> filteredTransactionServices) {
        this.filteredTransactionServices = filteredTransactionServices;
    }

    public TransactionService getSelectedTransactionService() {
        return selectedTransactionService;
    }

    public void setSelectedTransactionService(TransactionService selectedTransactionService) {
        this.selectedTransactionService = selectedTransactionService;
    }

}
