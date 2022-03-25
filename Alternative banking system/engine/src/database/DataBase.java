package database;

import database.fileresource.generated.AbsDescriptor;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Map;

public interface DataBase {
    void loadFile(String filePath) throws FileNotFoundException, JAXBException;
    //void organizeClientInformation(Map<String, Double> customerInfo);
    void  organizeInformation(AbsDescriptor descriptor);
}
