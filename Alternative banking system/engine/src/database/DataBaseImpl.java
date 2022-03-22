
package database;

import database.client.ClientImp;
import database.loan.LoansImpl;
import database.fileresource.generated.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class DataBaseImpl implements DataBase{
   private List<ClientImp> clients;
   private List<LoansImpl> loans;
   private Map<String, LoansImpl> loansByCategories;
   private static int time=0;

   public DataBaseImpl() {
      clients = new ArrayList<ClientImp>();
      loans = new ArrayList<LoansImpl>();
      loansByCategories = new Hashtable<>();
   }

   public static int getTime(){return time;}

   public void loadFile() throws FileNotFoundException, JAXBException {
      /* yesh aod kama dvarim lifney she zarih lasot behetem la mismah.
      kaha ze amur lehiraot. neshapets at ze aim funkziot shonot
      * ani lo mevin ma ze ha class descriptor she ha java yazar
      * kanal gam al ha objectFactory.
      *  */
      InputStream file = new FileInputStream(new File("src/database/fileresource/ex1-big.xml"));
      JAXBContext jc = JAXBContext.newInstance("database.fileresource.generated");
      Unmarshaller u = jc.createUnmarshaller();
      //AbsCategories categories = (AbsCategories) u.unmarshal(file);
      AbsCustomers customers = (AbsCustomers) u.unmarshal(file);
     // AbsLoans generatedLoans = (AbsLoans) u.unmarshal(file);
      AbsDescriptor descriptor = (AbsDescriptor) u.unmarshal(file);
      Map <String, Double> customerInfo = organizeInformation(descriptor);
      organizeClientInformation(customerInfo);
      //... zarih lirot ma ha peulot she anu osim kshe anahnu toanim at ha kovetz
      //loansBycategorizes.add(...)
      //loans.add(...)
      //clients.add(...)
      //etc

   }

   @Override
   public void organizeClientInformation(Map<String, Double> customerInfo) {
      for (Map.Entry<String, Double> entry: customerInfo.entrySet()) {
         clients.add(new ClientImp(entry.getKey(), entry.getValue()));
      }
   }

   @Override
   public Map<String, Double> organizeInformation(AbsDescriptor descriptor) {
      AbsCustomers names = descriptor.getAbsCustomers();
      Map <String, Double> customerInfo = new HashMap<String, Double>();
      AbsCategories categories = descriptor.getAbsCategories();
      AbsLoans absLoans = descriptor.getAbsLoans();
      /*questions to ask aviad: can we assume that the placements of the nods in the lists are parallel?*/
      for (AbsCustomer itr : names.getAbsCustomer()) {
        customerInfo.put(itr.getName(), (double) itr.getAbsBalance());
      }

      /*handels the loans list and loans by category. */
      for(AbsLoan itr : absLoans.getAbsLoan()){
         loans.add(new LoansImpl(itr.getAbsOwner(),itr.getId(),itr.getAbsCategory(),itr.getAbsTotalYazTime(),itr.getAbsIntristPerPayment(), itr.getAbsPaysEveryYaz(), itr.getAbsCapital()));
         loansByCategories.put(itr.getAbsCategory(),loans.get(loans.size()-1));
      }
      return customerInfo;
   }


}
