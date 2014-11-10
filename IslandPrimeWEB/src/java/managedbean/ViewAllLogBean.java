package managedbean;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import session.stateless.CIBeanLocal;
import entity.Log;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author nataliegoh
 */
@Named(value = "viewLogManagedBean")
@Dependent
public class ViewAllLogBean {
    
    @EJB
    private CIBeanLocal fmsbean;
    private List<Log> allLog;
    private String loggedInEmail;

    /**
     * Creates a new instance of viewLogManagedBean
     */
    public ViewAllLogBean() {
    }
    
    public List<Log> getAllLog() {
        allLog = fmsbean.getAllLog();
        return allLog;
    }

    public void setAllLog(List<Log> allLog) {
        this.allLog = allLog;
    }
}
