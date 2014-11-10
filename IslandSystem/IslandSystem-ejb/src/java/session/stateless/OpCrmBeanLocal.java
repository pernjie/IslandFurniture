/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import entity.Campaign;
import entity.Country;
import entity.Customer;
import entity.Facility;
import entity.Material;
import entity.ProductionOrder;
import entity.Region;
import entity.Service;
import entity.ServiceRecord;
import entity.ServiceRecordItem;
import entity.TransactionItem;
import entity.TransactionRecord;
import entity.TransactionService;
import java.util.Date;
import java.util.List;
import util.exception.DetailsConflictException;
import util.exception.EntityDneException;
import util.exception.ReferenceConstraintException;

/**
 *
 * @author nataliegoh
 */
public interface OpCrmBeanLocal {
    public Boolean addCampaignCustomers(Campaign campaign);
    
    public void removeCampaign(Campaign campaign) throws ReferenceConstraintException;

    public Customer getCustomerDetails(String email);

    public void unsubscribe(long custId);

    public Long addNewCampaign(Campaign campaign) throws DetailsConflictException;

    public Facility getUserFacility(String email);

    public Region getUserRegion(Facility fac);

    public void updateProductionOrder(ProductionOrder productionOrder) throws EntityDneException, DetailsConflictException;

    public List<ProductionOrder> getAllProductionOrders();

    public Long addNewProductionOrder(Integer period, Integer year, Integer quantity, Long matId, Long storeId) throws EntityDneException, DetailsConflictException;

    public void removeProductionOrder(ProductionOrder productionOrder) throws ReferenceConstraintException;

    public void addOverallDelivery(Service delivery, ServiceRecord selectedServiceRecord);

    public Long addNewServiceRecordItem(Long svcId, Long matId, Long svcRecId, Integer svcRecItemQty) throws DetailsConflictException;

    public ServiceRecord addNewServiceRecord(String custName, String address, Date svcDate, Long storeId);

    public Service getDeliveryForServiceRecord(Region region, Double totalMatPrice);

    public Double getTotalMaterialPrice(ServiceRecord svcRec, Region region);

    public Boolean payServiceRecord(Long transactRecId, Long svcRecId);

    public Boolean fulfillServiceRecord(ServiceRecord svcRec);

    public void removeServiceRecord(ServiceRecord serviceRecord) throws ReferenceConstraintException;

    public void removeServiceRecordItem(ServiceRecordItem serviceRecordItem) throws ReferenceConstraintException;

    public void updateServiceRecord(ServiceRecord serviceRecord) throws EntityDneException, DetailsConflictException;

    public void updateServiceRecordItem(ServiceRecordItem serviceRecordItem) throws EntityDneException, DetailsConflictException;

    public void updateTransactionRecord(TransactionRecord tr);

    public void updateTransactionItem(TransactionItem transactionItem) throws EntityDneException, DetailsConflictException;

    public Double countryTaxRate(Country country);

    public Double getTwoDigitPrice(Double a);

    public void updateTransactionRecordWithReturns(Double returnedAmount, TransactionRecord tr);

    public TransactionRecord getSelectedTransactionRecord(Long id);

    public List<TransactionItem> getTransactionItems(TransactionRecord tr);

    public List<TransactionService> getTransactionServices(TransactionRecord tr);

    public List<TransactionRecord> getRegionTransactionRecords(Region region);

    public List<ServiceRecordItem> getServiceRecordItems(Long svcRecId);

    public List<ServiceRecord> getServiceRecords(Facility store);

    public List<Material> getMaterials(Region region);

    public List<Service> getNonDeliveryServices(Region region);

    public List<ServiceRecord> getAllServiceRecords();

    public List<ProductionOrder> getProductionOrders(Facility store);

    public List<Customer> getAllCustomers();

    public List<TransactionRecord> getAllTransactionRecords();

    public List<Facility> getAllMFs();

    public List<Material> getAllFurniture();

    public List<Material> getAllRawMat();

    public List<Facility> getAllStores();

    public List<Facility> getAllFacilities();

    public List<Campaign> getAllCampaigns();
    
    public Long addNewCustomer(Customer customer) throws DetailsConflictException;
    public List<Country> getAllCountries();

}
