
package database;

import database.client.AccountTransaction;
import database.client.Customer;
import database.loan.Loans;
import database.fileresource.generated.*;
import database.loan.Payment;
import exceptions.filesexepctions.*;
import objects.Customers.AccountTransactionDTO;
import objects.Customers.CustomerInfoDTO;
import objects.Customers.LoanInfoDTO;
import objects.Loans.ActiveRiskLoanDTO;
import objects.Loans.FinishedLoanDTO;
import objects.Loans.NewLoanDTO;
import objects.Loans.Payments.PaymentsDTO;
import objects.Loans.PendingLoanDTO;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class Engine implements EngineInterface {
   private List<Customer> customers;
   private List<Loans> loans;
   private Map<String, List<Loans>> loansByCategories; //saves all the loans which has the same category
   private static int time = 0;

   public Engine() {
      customers = new ArrayList<>();
      loans = new ArrayList<>();
      loansByCategories = new TreeMap<>();
   }

   public static int getTime() {
      return time;
   }

   public void resetTime(){
      time = 1;
   }

   public Boolean loadFile(String filePath) throws FileNotFoundException, JAXBException, Exception {
      String[] list = filePath.split("\\.");
      if (!list[list.length - 1].equals("xml")) {
         throw new NotXmlExcpetion();
      }
      InputStream XMLFile = new FileInputStream(filePath);
      JAXBContext jc = JAXBContext.newInstance("database.fileresource.generated");
      Unmarshaller u = jc.createUnmarshaller();
      AbsDescriptor descriptor = (AbsDescriptor) u.unmarshal(XMLFile);
      organizeInformation(descriptor);
      resetTime();
      return true;
   }

   @Override
   public void organizeInformation(AbsDescriptor descriptor) throws Exception {
      AbsCustomers newCustomers = descriptor.getAbsCustomers();
      checkCustomerInfo(newCustomers);
      AbsCategories newCategories = descriptor.getAbsCategories();
      AbsLoans newLoans = descriptor.getAbsLoans();
      checkLoansInfo(newCustomers.getAbsCustomer(), newCategories.getAbsCategory(), newLoans);
      //TODO: override existing file! meaning clean up the current data before adding new data.
      copyDataToEngineFields(newCustomers, newLoans, newCategories);
   }

   @Override
   public void checkCustomerInfo(AbsCustomers newCustomers) throws TwoClientsWithSameNameException {

      List<String> customerInfo = new ArrayList<>();
//      Map <String, Double> customerInfo = new HashMap<String, Double>();
      for (AbsCustomer customer : newCustomers.getAbsCustomer()) {
         if (customerInfo.contains(customer.getName().toLowerCase())) {
            throw new TwoClientsWithSameNameException(customer.getName());
         }
         customerInfo.add(customer.getName().toLowerCase());
      }
   }

   @Override
   public void checkLoansInfo(List<AbsCustomer> newCustomers, List<String> newCategories, AbsLoans newLoans) throws Exception {
      boolean customerFound = false;
      for (AbsLoan loan : newLoans.getAbsLoan()) {
         if (!newCategories.contains(loan.getAbsCategory())) {
            throw new LoanCategoryNotExistException(loan.getAbsCategory(), loan.getId());
         }
         for (AbsCustomer customer : newCustomers) {
            customerFound = loan.getAbsOwner().equals(customer.getName());
            if (customerFound)
               break;
         }
         if (!customerFound)
            throw new OwnerLoanNotExistException(loan.getAbsOwner(), loan.getId());
         if (loan.getAbsTotalYazTime() % loan.getAbsPaysEveryYaz() != 0) {
            throw new TimeOfPaymentNotDivideEqualyException(loan.getAbsTotalYazTime(), loan.getAbsPaysEveryYaz());
         }
      }
   }
   @Override
   public void copyDataToEngineFields(AbsCustomers newCustomers, AbsLoans newLoans, AbsCategories newCategories) {
      //customers copy

      for(AbsCustomer newCustomer : newCustomers.getAbsCustomer()){
         customers.add(new Customer(newCustomer.getName(), newCustomer.getAbsBalance()));
      }
      //categories copy
      for (String newCategory : newCategories.getAbsCategory()){
         loansByCategories.put(newCategory, new ArrayList<>());
      }
      //loans copy
      for(AbsLoan newLoan : newLoans.getAbsLoan()){
         Loans loan = new Loans(newLoan.getAbsOwner(),newLoan.getId(),newLoan.getAbsCategory(),
                 newLoan.getAbsTotalYazTime(), newLoan.getAbsIntristPerPayment(),
                 newLoan.getAbsPaysEveryYaz(), newLoan.getAbsCapital());
         loans.add(loan);
         loansByCategories.get(loan.getLoanCategory()).add(loan);
         for(Customer neededCustomer: customers){
            if(neededCustomer.getName().equals(loan.getBorrowerName())){
               neededCustomer.getBorrowerList().add(loan);
               break;
            }
         }
      }
   }

   @Override
   public List<NewLoanDTO> getLoansInfo() {
      List<NewLoanDTO> DTOloans = new ArrayList<>();
      for(Loans loan : loans){
         List<PaymentsDTO> paymentList = null;
         if(loan.getStatus().getPayments() != null)
            paymentList = copyPaymentList(loan);
         switch (loan.getStatus().toString()){
            case "NEW": {
               DTOloans.add(new NewLoanDTO(loan.getLOANID(),loan.getBorrowerName(), loan.getLoanCategory(),
                       loan.getLoanSizeNoInterest(),loan.getTimeLimitOfLoan(),loan.getInterestPerPayment(),
                       loan.getTimePerPayment(),loan.getStatus().toString()));
               break;
            }
            case "PENDING": {
               DTOloans.add(new PendingLoanDTO(loan.getLOANID(),loan.getBorrowerName(), loan.getLoanCategory(),
                       loan.getLoanSizeNoInterest(),loan.getTimeLimitOfLoan(),loan.getInterestPerPayment(),
                       loan.getTimePerPayment(),loan.getStatus().toString(),loan.getListOflenders()
                       ,loan.getCollectedSoFar(),loan.getLoanSize() - loan.getCollectedSoFar()));
               break;
            }
            case "FINISHED": {
               DTOloans.add(new FinishedLoanDTO(loan.getLOANID(),loan.getBorrowerName(), loan.getLoanCategory(),
                       loan.getLoanSizeNoInterest(),loan.getTimeLimitOfLoan(),loan.getInterestPerPayment(),
                       loan.getTimePerPayment(),loan.getStatus().toString(),loan.getStatus().getStartingActiveTime(),
                       paymentList,loan.getStatus().getFinishTime()));
               break;
            }
            default: //ACTIVE OR RISK{
               DTOloans.add(new ActiveRiskLoanDTO(loan.getLOANID(),loan.getBorrowerName(), loan.getLoanCategory(),
                       loan.getLoanSizeNoInterest(),loan.getTimeLimitOfLoan(),loan.getInterestPerPayment(),
                       loan.getTimePerPayment(),loan.getStatus().toString(),loan.getStatus().getStartingActiveTime(),
                       loan.getStatus().getNextPaymentTime(),paymentList,loan.getStatus().getInterestPayed(),
                       loan.getStatus().getInitialPayed(),loan.getStatus().getInterestLeftToPay(),loan.getStatus().getInitialLeftToPay()));
               break;
         }

      }
      return DTOloans;
//
//         //Either NEW or PENDING
//         if(loan.getStatus() == null){
//            if(loan.getCollectedSoFar() == 0) {  //NEW
//               DTOloans.add(new NewLoanDTO(loan.getLOANID(),loan.getBorrowerName(), loan.getLoanCategory(),
//                       loan.getLoanSizeNoInterest(),loan.getTimeLimitOfLoan(),loan.getInterestPerPayment(),
//                       loan.getTimePerPayment(),"New"));
//            } else { // PENDING
//               DTOloans.add(new PendingLoanDTO(loan.getLOANID(),loan.getBorrowerName(), loan.getLoanCategory(),
//                       loan.getLoanSizeNoInterest(),loan.getTimeLimitOfLoan(),loan.getInterestPerPayment(),
//                       loan.getTimePerPayment(),"Pending", loan.getListOflenders(),
//                       loan.getCollectedSoFar(),loan.getLoanSize()-loan.getCollectedSoFar()));
//            }
//         } else{

   }

   @Override
   public List<CustomerInfoDTO> getCustomerInfo() {
     List<AccountTransactionDTO> accountTransactionDTOList = new ArrayList<>();
     List<LoanInfoDTO> lenderList = new ArrayList<>();
     List<LoanInfoDTO> borrowerList = new ArrayList<>();
     List<CustomerInfoDTO> customersInfo = new ArrayList<>();
     for(Customer customer: customers)
     {
        for(AccountTransaction accountTransaction: customer.getTransactions()){
           accountTransactionDTOList.add(new AccountTransactionDTO(accountTransaction.getTimeOfTransaction(),accountTransaction.getTransactionAmount(), accountTransaction.getIncomeOrExpense(), accountTransaction.getBalanceBefore(), accountTransaction.getBalanceAfter()));
        }
        for(Loans lenderLoan : customer.getLenderList())
        {

           lenderList.add(new LoanInfoDTO(lenderLoan.getLoanSize(),lenderLoan.getLOANID(),lenderLoan.getLoanCategory(),lenderLoan.getLoanSizeNoInterest(),lenderLoan.getInterestPerPayment(),lenderLoan.getTimePerPayment(),lenderLoan.getStatus().toString()));
        }
        for(Loans borrowerLoan: customer.getBorrowerList()){
           borrowerList.add(new LoanInfoDTO(borrowerLoan.getLoanSize(),borrowerLoan.getLOANID(),borrowerLoan.getLoanCategory(),borrowerLoan.getLoanSizeNoInterest(),borrowerLoan.getInterestPerPayment(),borrowerLoan.getTimePerPayment(),borrowerLoan.getStatus().toString()));
        }
        customersInfo.add(new CustomerInfoDTO(accountTransactionDTOList, lenderList, borrowerList, customer.getName(), customer.getBalance()));
        accountTransactionDTOList.clear();
        lenderList.clear();
        borrowerList.clear();
     }
     return customersInfo;
   }


   public List<PaymentsDTO> copyPaymentList(Loans loan){
      List<PaymentsDTO> list = new ArrayList<>();
      for(Payment payment: loan.getStatus().getPayments()){
         list.add(new PaymentsDTO(payment.getTimeOfPayment(), payment.getInterestComponent(),
                 payment.getSumOfPayment(), payment.getInitialComponent(), payment.isPayedSuccesfully()));
      }
      return list;
   }
}





