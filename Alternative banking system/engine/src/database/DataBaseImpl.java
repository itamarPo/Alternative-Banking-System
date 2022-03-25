
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
import java.nio.file.Path;
import java.nio.file.Paths;
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

   public void loadFile(String filePath) throws FileNotFoundException, JAXBException {
      InputStream XMLFile = new FileInputStream(new File(filePath));
      JAXBContext jc = JAXBContext.newInstance("database.fileresource.generated");
      Unmarshaller u = jc.createUnmarshaller();
      //all classes and data
      AbsDescriptor descriptor = (AbsDescriptor) u.unmarshal(XMLFile);

      organizeInformation(descriptor);
   }

   @Override
   public void organizeInformation(AbsDescriptor descriptor) throws Exception {
      AbsCustomers customers = descriptor.getAbsCustomers();
      Map <String, Double> customerInfo = new HashMap<String, Double>();
      for (AbsCustomer customer : customers.getAbsCustomer()){
         if(customerInfo.containsKey(customer.getName())){
            throw  new TwoClientsWithSameNameException(customer.getName());
         }
         customerInfo.put(customer.getName(), (double)customer.getAbsBalance());
      }

//      AbsCategories categories = descriptor.getAbsCategories();
//      AbsLoans absLoans = descriptor.getAbsLoans();
      /*questions to ask aviad: can we assume that the placements of the nods in the lists are parallel?*/
      for (AbsCustomer customer : customers.getAbsCustomer()) {
         for(ClientImp client : clients){
            if(customer.getName().equals(client.getName())){
               throw new TwoClientsWithSameNameException(client.getName());
            }
         }
         clients.add(new ClientImp(customer.getName(),customer.getAbsBalance()));
      }

//      /*handels the loans list and loans by category. */
//      for(AbsLoan itr : absLoans.getAbsLoan()){
//         loans.add(new LoansImpl(itr.getAbsOwner(),itr.getId(),itr.getAbsCategory(),itr.getAbsTotalYazTime(),itr.getAbsIntristPerPayment(), itr.getAbsPaysEveryYaz(), itr.getAbsCapital()));
//         loansByCategories.put(itr.getAbsCategory(),loans.get(loans.size()-1));
//      }
//      return customerInfo;
   }
//   @Override
//   public void organizeClientInformation(Map<String, Double> customerInfo) {
//
//      for (Map.Entry<String, Double> entry: customerInfo.entrySet()) {
//           clients.add(new ClientImp(entry.getKey(), entry.getValue()));
//      }
//   }




}
