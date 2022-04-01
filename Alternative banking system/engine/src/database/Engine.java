
package database;

import database.client.AccountTransaction;
import database.client.Customer;
import database.loan.Loans;
import database.fileresource.generated.*;
import database.loan.Payment;
import exceptions.accountexception.NameException;
import exceptions.accountexception.WithDrawMoneyException;
import exceptions.filesexepctions.*;
import objects.DisplayCustomerName;
import objects.categories.CategoriesListDTO;
import objects.customers.*;
import objects.customers.loanInfo.*;
import objects.loans.ActiveRiskLoanDTO;
import objects.loans.FinishedLoanDTO;
import objects.loans.NewLoanDTO;
import objects.loans.payments.PaymentsDTO;
import objects.loans.PendingLoanDTO;


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
      loansByCategories = new LinkedHashMap<>();
   }

   public static int getTime() {
      return time;
   }

   public void resetTime() { time = 1;}

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

      for (AbsCustomer newCustomer : newCustomers.getAbsCustomer()) {
         customers.add(new Customer(newCustomer.getName(), newCustomer.getAbsBalance()));
      }
      //categories copy
      for (String newCategory : newCategories.getAbsCategory()) {
         loansByCategories.put(newCategory, new ArrayList<>());
      }
      //loans copy
      for (AbsLoan newLoan : newLoans.getAbsLoan()) {
         Loans loan = new Loans(newLoan.getAbsOwner(), newLoan.getId(), newLoan.getAbsCategory(),
                 newLoan.getAbsTotalYazTime(), newLoan.getAbsIntristPerPayment(),
                 newLoan.getAbsPaysEveryYaz(), newLoan.getAbsCapital());
         loans.add(loan);
         loansByCategories.get(loan.getLoanCategory()).add(loan);
         for (Customer neededCustomer : customers) {
            if (neededCustomer.getName().equals(loan.getBorrowerName())) {
               neededCustomer.getBorrowerList().add(loan);
               break;
            }
         }
      }
   }

   @Override
   public List<NewLoanDTO> getLoansInfo() {
      List<NewLoanDTO> DTOloans = new ArrayList<>();
      for (Loans loan : loans) {
         List<PaymentsDTO> paymentList = null;
         if (loan.getStatus().getPayments() != null)
            paymentList = copyPaymentList(loan);
         switch (loan.getStatus().getStatus()) {
            case "New": {
               DTOloans.add(new NewLoanDTO(loan.getLOANID(), loan.getBorrowerName(), loan.getLoanCategory(),
                       loan.getLoanSizeNoInterest(), loan.getTimeLimitOfLoan(), loan.getInterestPerPayment(),
                       loan.getTimePerPayment(), loan.getStatus().getStatus()));
               break;
            }
            case "Pending": {
               DTOloans.add(new PendingLoanDTO(loan.getLOANID(), loan.getBorrowerName(), loan.getLoanCategory(),
                       loan.getLoanSizeNoInterest(), loan.getTimeLimitOfLoan(), loan.getInterestPerPayment(),
                       loan.getTimePerPayment(), loan.getStatus().getStatus(), loan.getListOflenders()
                       , loan.getCollectedSoFar(), loan.getLoanSize() - loan.getCollectedSoFar()));
               break;
            }
            case "Finished": {
               DTOloans.add(new FinishedLoanDTO(loan.getLOANID(), loan.getBorrowerName(), loan.getLoanCategory(),
                       loan.getLoanSizeNoInterest(), loan.getTimeLimitOfLoan(), loan.getInterestPerPayment(),
                       loan.getTimePerPayment(), loan.getStatus().getStatus(), loan.getStatus().getStartingActiveTime(),
                       paymentList, loan.getStatus().getFinishTime()));
               break;
            }
            default: //ACTIVE OR RISK{
               DTOloans.add(new ActiveRiskLoanDTO(loan.getLOANID(), loan.getBorrowerName(), loan.getLoanCategory(),
                       loan.getLoanSizeNoInterest(), loan.getTimeLimitOfLoan(), loan.getInterestPerPayment(),
                       loan.getTimePerPayment(), loan.getStatus().getStatus(), loan.getStatus().getStartingActiveTime(),
                       loan.getStatus().getNextPaymentTime(), paymentList, loan.getStatus().getInterestPayed(),
                       loan.getStatus().getInitialPayed(), loan.getStatus().getInterestLeftToPay(), loan.getStatus().getInitialLeftToPay()));
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
//      List<AccountTransactionDTO> accountTransactionDTOList = new ArrayList<>();
//      List<LoanInfoDTO> lenderList = new ArrayList<>();
//      List<LoanInfoDTO> borrowerList = new ArrayList<>();
      List<CustomerInfoDTO> customersInfo = new ArrayList<>();
      LoanInfoDTO newLoan;
      for (Customer customer : customers) {
         customersInfo.add(new CustomerInfoDTO(customer.getName(),customer.getBalance()));
         for (AccountTransaction accountTransaction : customer.getTransactions()) {
            customersInfo.get(customersInfo.size()-1).getTransactionDTOS().add(new AccountTransactionDTO(accountTransaction.getTimeOfTransaction(),
             accountTransaction.getTransactionAmount(), accountTransaction.getIncomeOrExpense(), accountTransaction.getBalanceBefore(), accountTransaction.getBalanceAfter()));
//           accountTransactionDTOList.add(new AccountTransactionDTO(accountTransaction.getTimeOfTransaction(), accountTransaction.getTransactionAmount(),
//                    accountTransaction.getIncomeOrExpense(), accountTransaction.getBalanceBefore(), accountTransaction.getBalanceAfter()));
         }
         for (Loans lenderLoan : customer.getLenderList()) {
            newLoan = customerDTOClassArrange(lenderLoan);
           customersInfo.get(customersInfo.size()-1).getLenderList().add(newLoan);
         }
         for (Loans borrowerLoan : customer.getBorrowerList()) {
            newLoan = customerDTOClassArrange(borrowerLoan);
            customersInfo.get(customersInfo.size()-1).getBorrowerList().add(newLoan);
         }
//         accountTransactionDTOList.clear();
//         lenderList.clear();
//         borrowerList.clear();
      }
      return customersInfo;
   }

   @Override
   public Customer getCustomerByName(String name) throws Exception{
      for(Customer customer: customers){
         if(name.equalsIgnoreCase(customer.getName())){
            return customer;
         }
      }
      throw new NameException(name);
   }


   public List<PaymentsDTO> copyPaymentList(Loans loan) {
      List<PaymentsDTO> list = new ArrayList<>();
      for (Payment payment : loan.getStatus().getPayments()) {
         list.add(new PaymentsDTO(payment.getTimeOfPayment(), payment.getInterestComponent(),
                 payment.getSumOfPayment(), payment.getInitialComponent(), payment.isPayedSuccesfully()));
      }
      return list;
   }

   public LoanInfoDTO customerDTOClassArrange(Loans loan){
      switch(loan.getStatus().getStatus()){
         case "Pending":{
            return new PendingLoanInfoDTO(loan.getLOANID(), loan.getLoanCategory(),loan.getLoanSizeNoInterest(), loan.getInterestPerPayment(),
                    loan.getTimePerPayment(), loan.getStatus().getStatus(),loan.getCollectedSoFar());
         }
         case "Active":{ //TODO: add the expected next payment
           return new ActiveLoanInfoDTO(loan.getLOANID(), loan.getLoanCategory(),loan.getLoanSizeNoInterest(), loan.getInterestPerPayment(),
                   loan.getTimePerPayment(), loan.getStatus().getStatus(),loan.getStatus().getNextPaymentTime(), 2.0/*need to add next payment*/);
         }
         case "Risk":{ //TODO: changes accordingly to the changes at the class
           return new RiskLoanInfoDTO(loan.getLOANID(), loan.getLoanCategory(),loan.getLoanSizeNoInterest(), loan.getInterestPerPayment(),
                   loan.getTimePerPayment(), loan.getStatus().getStatus());
         }
         case "Finished": {
            return new FinishedLoanInfoDTO(loan.getLOANID(), loan.getLoanCategory(),loan.getLoanSizeNoInterest(), loan.getInterestPerPayment(),
                    loan.getTimePerPayment(), loan.getStatus().getStatus(), loan.getStatus().getStartingActiveTime(), loan.getStatus().getFinishTime());
         }
         default: //probably only new loan
            return new LoanInfoDTO(loan.getLOANID(), loan.getLoanCategory(),loan.getLoanSizeNoInterest(), loan.getInterestPerPayment(),
                    loan.getTimePerPayment(), loan.getStatus().getStatus());
      }

   }

   public void addMoneyToAccount(int userChoice, double moneyToAdd) {
      customers.get(userChoice - 1).addMoney(moneyToAdd);
//      customer.changeBalance(moneyToAdd);
   }

   public DisplayCustomerName namesForDisplay(){
      DisplayCustomerName names = new DisplayCustomerName();
      for(Customer customer: customers)
      {
         names.getCustomerList().put(customer.getName(),customer.getBalance());
      }
      return names;
   }

   public void drawMoneyFromAccount(int userChoice, double moneyToDraw) throws Exception{
      if(customers.get(userChoice - 1).getBalance() - moneyToDraw < 0){
         throw new WithDrawMoneyException(customers.get(userChoice - 1).getBalance(), moneyToDraw);
      }
      customers.get(userChoice - 1).drawMoney(moneyToDraw);
   }

   public Customer findCustomerDueToName(String name){
      for(Customer customer: customers){
         if(customer.getName().equalsIgnoreCase(name))
            return customer;
      }
      return null;
   }

   public CategoriesListDTO getCategoriesList(){
      List<String> categories = new ArrayList<>();
      for(Map.Entry<String,List<Loans>> entry : loansByCategories.entrySet()){
         categories.add(entry.getKey());
      }
      return new CategoriesListDTO(categories);
   }

   public List<NewLoanDTO> getFilteredLoans(double moneyToInvest, List<String> categories,int interest,int minTime) {
      List<NewLoanDTO> validLoans = new ArrayList<>();
      for (Map.Entry<String, List<Loans>> entry : loansByCategories.entrySet()) {
         if (categories.contains(entry.getKey())) {
            for (Loans candidateLoan : entry.getValue()) {
               if (candidateLoan.getStatus().getStatus() == "New" || candidateLoan.getStatus().getStatus() == "Pending") {
                  if (interest == 0 || (candidateLoan.getInterestPerPayment() - interest >= 0)) {
                     if (minTime == 0 || (candidateLoan.getTimeLimitOfLoan() - minTime >= 0)) {
                        if (candidateLoan.getStatus().getStatus() == "New") {
                           validLoans.add(new NewLoanDTO(candidateLoan.getLOANID(), candidateLoan.getBorrowerName(), candidateLoan.getLoanCategory(),
                                   candidateLoan.getLoanSizeNoInterest(), candidateLoan.getTimeLimitOfLoan(), candidateLoan.getInterestPerPayment(),
                                   candidateLoan.getTimePerPayment(), candidateLoan.getStatus().getStatus()));
                        } else {
                           validLoans.add(new PendingLoanDTO(candidateLoan.getLOANID(), candidateLoan.getBorrowerName(), candidateLoan.getLoanCategory(),
                                   candidateLoan.getLoanSizeNoInterest(), candidateLoan.getTimeLimitOfLoan(), candidateLoan.getInterestPerPayment(),
                                   candidateLoan.getTimePerPayment(), candidateLoan.getStatus().getStatus(), candidateLoan.getListOflenders(), candidateLoan.getCollectedSoFar(),
                                   candidateLoan.getLoanSize() - candidateLoan.getCollectedSoFar()));
                        }
                     }
                  }
               }
            }
         }
      }
      return validLoans;
   }

   public void checkAmountOfInvestment(int userChoice, double moneyToInvest)throws Exception {
      if(customers.get(userChoice - 1).getBalance() - moneyToInvest < 0){
         throw new WithDrawMoneyException(customers.get(userChoice - 1).getBalance(), moneyToInvest);
      }
   }
}







