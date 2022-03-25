package database;

import database.fileresource.generated.AbsDescriptor;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Map;

public interface EngineInterface {
    void loadFile(String filePath) throws FileNotFoundException, JAXBException , Exception;
    void checkCustomerInfo(AbsCustomers customers) throws Exception;
    void  organizeInformation(AbsDescriptor descriptor) throws Exception;
}
