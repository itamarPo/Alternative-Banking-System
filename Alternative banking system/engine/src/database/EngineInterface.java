package database;

import database.fileresource.generated.*;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public interface EngineInterface {
    void loadFile(String filePath) throws FileNotFoundException, JAXBException , Exception;
    void checkCustomerInfo(AbsCustomers customers) throws Exception;
    void organizeInformation(AbsDescriptor descriptor) throws Exception;
    void checkLoansInfo(List<AbsCustomer> customers, List<String> categories, AbsLoans loans) throws Exception;
}
