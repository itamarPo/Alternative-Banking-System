
package database;

import database.client.Customer;
import database.loan.Loans;
import database.fileresource.generated.*;
import exceptions.filesexepctions.*;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class Engine implements EngineInterface {
   private List<Customer> clients;
   private List<Loans> loans;
   private Map<String, Loans> loansByCategories;
   private static int time=0;

   public Engine() {
      clients = new ArrayList<Customer>();
      loans = new ArrayList<Loans>();
      loansByCategories = new Hashtable<>();
   }

   public static int getTime(){return time;}

   public void loadFile(String filePath) throws FileNotFoundException, JAXBException, Exception {
      String[] list = filePath.split("\\.");

//      List <String> list = new ArrayList();
      //list = filePath.split("\\.");
      if(!list[list.length - 1].equals("xml")){
          throw new NotXmlExcpetion();
      }

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
      checkCustomerInfo(customers);
      AbsCategories categories = descriptor.getAbsCategories();
      AbsLoans loans = descriptor.getAbsLoans();
      checkLoansInfo(customers.getAbsCustomer(), categories.getAbsCategory(), loans);
     // checkLoansInfo(customers,categories,loans);

      /*questions to ask aviad: can we assume that the placements of the nods in the lists are parallel?*/
//      for (AbsCustomer customer : customers.getAbsCustomer()) {
//         for(Customer client : clients){
//            if(customer.getName().equals(client.getName())){
//               throw new TwoClientsWithSameNameException(client.getName());
//            }
//         }
//         clients.add(new Customer(customer.getName(),customer.getAbsBalance()));
//      }

//      for (Map.Entry<String, Double> entry: customerInfo.entrySet()) {
//         clients.add(new Customer(entry.getKey(), entry.getValue()));
//      }

//      /*handels the loans list and loans by category. */
//      for(AbsLoan itr : absLoans.getAbsLoan()){
//         loans.add(new LoansImpl(itr.getAbsOwner(),itr.getId(),itr.getAbsCategory(),itr.getAbsTotalYazTime(),itr.getAbsIntristPerPayment(), itr.getAbsPaysEveryYaz(), itr.getAbsCapital()));
//         loansByCategories.put(itr.getAbsCategory(),loans.get(loans.size()-1));
//      }
//      return customerInfo;
   }
   @Override
   public void checkCustomerInfo(AbsCustomers customers) throws TwoClientsWithSameNameException{

      List<String> customerInfo = new ArrayList<String>();
//      Map <String, Double> customerInfo = new HashMap<String, Double>();
      for (AbsCustomer customer : customers.getAbsCustomer()){
         if(customerInfo.contains(customer.getName().toLowerCase())){
            throw  new TwoClientsWithSameNameException(customer.getName());
         }
         customerInfo.add(customer.getName().toLowerCase());
      }
   }

   @Override
   public void checkLoansInfo(List<AbsCustomer> customers, List<String> categories, AbsLoans loans) throws Exception {
      boolean customerFound = false;
      for (AbsLoan loan: loans.getAbsLoan()){
         if(!categories.contains(loan.getAbsCategory())){
            throw new LoanCategoryNotExistException(loan.getAbsCategory(), loan.getId());
         }
         for (AbsCustomer customer : customers){
            customerFound = loan.getAbsOwner().equals(customer.getName());
            if(customerFound)
               break;
         }
         if (!customerFound)
            throw new OwnerLoanNotExistException(loan.getAbsOwner(),loan.getId());
         if(loan.getAbsTotalYazTime() % loan.getAbsPaysEveryYaz() != 0){
            throw new TimeOfPaymentNotDivideEqualyException(loan.getAbsTotalYazTime(), loan.getAbsPaysEveryYaz());
         }
      }
   }
}
