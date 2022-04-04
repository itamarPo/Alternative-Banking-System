package database;

import database.client.Customer;
import database.fileresource.generated.*;
import exceptions.accountexception.NameException;
import objects.DisplayCustomerName;
import objects.customers.CustomerInfoDTO;
import objects.loans.NewLoanDTO;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public interface EngineInterface{
    Boolean loadFile(String filePath) throws FileNotFoundException, JAXBException , Exception;
    void checkCustomerInfo(AbsCustomers newCustomers) throws Exception;
    void organizeInformation(AbsDescriptor descriptor) throws Exception;
    void checkLoansInfo(List<AbsCustomer> newCustomers, List<String> newCategories, AbsLoans newLoans) throws Exception;
    void copyDataToEngineFields(AbsCustomers newCustomers, AbsLoans newLoans, AbsCategories newCategories);
    void resetTime();
    List<NewLoanDTO> getLoansInfo();
    List<CustomerInfoDTO> getCustomerInfo();
    Customer getCustomerByName(String name) throws Exception;
    void addMoneyToAccount(int userChoice, double moneyToAdd);
    DisplayCustomerName namesForDisplay();
    void drawMoneyFromAccount(int userChoice, double moneyToDraw) throws Exception;
}
