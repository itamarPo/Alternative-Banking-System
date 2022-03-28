package database;

import database.fileresource.generated.*;
import objects.Customers.CustomerInfoDTO;
import objects.Loans.NewLoanDTO;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public interface EngineInterface {
    Boolean loadFile(String filePath) throws FileNotFoundException, JAXBException , Exception;
    void checkCustomerInfo(AbsCustomers newCustomers) throws Exception;
    void organizeInformation(AbsDescriptor descriptor) throws Exception;
    void checkLoansInfo(List<AbsCustomer> newCustomers, List<String> newCategories, AbsLoans newLoans) throws Exception;
    void copyDataToEngineFields(AbsCustomers newCustomers, AbsLoans newLoans, AbsCategories newCategories);
    void resetTime();
    List<NewLoanDTO> getLoansInfo();
    List<CustomerInfoDTO> getCustomerInfo();
}
