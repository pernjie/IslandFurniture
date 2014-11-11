package managedbean;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 







import entity.Facility;
import session.stateless.CIBeanLocal;
import entity.Staff;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import session.stateless.GlobalHqBean;
import util.exception.ReferenceConstraintException;

/**
 *
 * @author nataliegoh
 */
@Named(value = "viewAllStaffManagedBean")
@Dependent  
public class ViewAllStaffBean implements Serializable {

    /**
     * Creates a new instance of viewAllStaffManagedBean
     */
    public ViewAllStaffBean() {
    }
    
    @EJB
    private CIBeanLocal fmsbean;
    private Long id;
    private String email;
    private String contact;
    private String name;
    private String password;
    private String role1;
    private String role2;
    private String role3;
    private String statusMessage;
    private Staff selectedStaff;
    private List<Staff> filteredStaffs;
    private List<Staff> staffs;
    private Staff newStaff = new Staff();

    @PostConstruct
    public void init() {
        staffs = fmsbean.getAllAcounts();
    }

    public void deleteStaff() {
        try {
            fmsbean.remove(selectedStaff);
            staffs = fmsbean.getAllAcounts();
            FacesMessage msg = new FacesMessage("Staff Deleted");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (ReferenceConstraintException ex) {
            staffs = fmsbean.getAllAcounts();
            statusMessage = ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Changes not saved: "
                    + statusMessage, ""));
        }
    }

    public void onRowEdit(RowEditEvent event) {

        try {
            fmsbean.updateStaff((Staff) event.getObject());
            staffs = fmsbean.getAllAcounts();
            FacesMessage msg = new FacesMessage("Staff Edited", ((Staff) event.getObject()).getEmail());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {

            ex.printStackTrace();
        }

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Staff) event.getObject()).getEmail());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public Staff getNewStaff() {
        return newStaff;
    }

    public void setNewStaff(Staff newStaff) {
        this.newStaff = newStaff;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Staff> getFilteredStaffs() {
        return filteredStaffs;
    }

    public void setFilteredStaffs(List<Staff> filteredStaffs) {
        this.filteredStaffs = filteredStaffs;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<Staff> getStaffs() {
        return staffs;
    }

    public Staff getSelectedStaff() {
        return selectedStaff;
    }

    public void setSelectedStaff(Staff selectedStaff) {
        this.selectedStaff = selectedStaff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole1() {
        return role1;
    }

    public void setRole1(String role1) {
        this.role1 = role1;
    }

    public String getRole2() {
        return role2;
    }

    public void setRole2(String role2) {
        this.role2 = role2;
    }

    public String getRole3() {
        return role3;
    }

    public void setRole3(String role3) {
        this.role3 = role3;
    }
    
    
}



