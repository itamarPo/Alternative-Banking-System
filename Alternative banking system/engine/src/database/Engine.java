package database;

import database.client.AccountTransaction;
import database.client.Customer;
import database.loan.Loans;
import database.fileresource.generated.*;
import database.loan.Payment;
import exceptions.accountexception.notAllAmountSuccessfullyInvested;
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
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.min;

public class Engine implements EngineInterface , Serializable {
   private List<Customer> customers;
   private List<Loans> loans;
   private Map<String, List<Loans>> loansByCategories; //saves all the loans which has the same category
   private static int time = 1;
   private int timeToSave; // A field which we use in the bonus, to save the current time.

   public Engine() {
      customers = new ArrayList<>();
      loans = new ArrayList<>();
      loansByCategories = new LinkedHashMap<>();
      timeToSave = 1;
   }

   public static int getTime() {
      return time;
   }


   public void resetTime() {
      timeToSave = 1;
      time = timeToSave;
   }

   public Boolean loadFile(String filePath) throws FileNotFoundException, JAXBException, Exception {
//      String[] list = filePath.split("\\.");
////      if (!list[list.length - 1].equals("xml")) {
////         throw new NotXmlExcpetion();
////      }
      InputStream XMLFile = new FileInputStream(filePath);
      JAXBContext jc = JAXBContext.newInstance("database.fileresource.generated");
      Unmarshaller u = jc.createUnmarshaller();
      AbsDescriptor descriptor = (AbsDescriptor) u.unmarshal(XMLFile);
      organizeInformation(descriptor);
      return true;
   }

   public void saveState(String filePath, Engine data) throws IOException{
      filePath += ".xtxt";
      File file = new File(filePath);
      file.createNewFile();
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
      out.writeObject(data);
      out.flush();
   }

   public Engine loadLastFile(String filePath) throws FileNotFoundException, Exception{
      filePath += ".xtxt";
      File lastFile = new File(filePath);
      if(!lastFile.exists())
         throw new FileNotFoundException();
      ObjectInputStream in = new ObjectInputStream(new FileInputStream(lastFile.getAbsolutePath()));

      Engine loadedInfo = (Engine)in.readObject();
      loadedInfo.time = loadedInfo.timeToSave;
      return loadedInfo;
   }


   @Override
   public void organizeInformation(AbsDescriptor descriptor) throws Exception {
      AbsCustomers newCustomers = descriptor.getAbsCustomers();
      checkCustomerInfo(newCustomers);
      AbsCategories newCategories = descriptor.getAbsCategories();
      AbsLoans newLoans = descriptor.getAbsLoans();
      checkLoansInfo(newCustomers.getAbsCustomer(), newCategories.getAbsCategory(), newLoans);
      resetEngine();
      copyDataToEngineFields(newCustomers, newLoans, newCategories);
   }

   @Override
   public void checkCustomerInfo(AbsCustomers newCustomers) throws TwoClientsWithSameNameException {
      List<String> customerInfo = new ArrayList<>();
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
      for (Loans loan : loans){
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
               DTOloans.add(new FinishedLoanDTO(loan.getLOANID(), loan.getBorrowerName(), loan.getLoanCategory(), loan.getLoanSizeNoInterest(), loan.getTimeLimitOfLoan(),
                       loan.getInterestPerPayment(), loan.getTimePerPayment(), loan.getStatus().getStatus(), loan.getListOflenders(), loan.getCollectedSoFar(),
                       loan.getLeftToBeCollected(), loan.getStatus().getStartingActiveTime(), copyPaymentList(loan), loan.getStatus().getFinishTime()));
               break;
            }
            default: //ACTIVE OR RISK{
               DTOloans.add(new ActiveRiskLoanDTO(loan.getLOANID(), loan.getBorrowerName(), loan.getLoanCategory(),
                       loan.getLoanSizeNoInterest(), loan.getTimeLimitOfLoan(), loan.getInterestPerPayment(),
                       loan.getTimePerPayment(), loan.getStatus().getStatus(), loan.getListOflenders(), loan.getCollectedSoFar(),
                       loan.getLeftToBeCollected(), loan.getStatus().getStartingActiveTime(),
                       loan.getStatus().getNextPaymentTime(), copyPaymentList(loan), loan.getStatus().getInterestPayed(),
                       loan.getStatus().getInitialPayed(), loan.getStatus().getInterestLeftToPay(), loan.getStatus().getInitialLeftToPay()));
               break;
         }

      }
      return DTOloans;

   }

   @Override
   public List<CustomerInfoDTO> getCustomerInfo() {

      List<CustomerInfoDTO> customersInfo = new ArrayList<>();
      LoanInfoDTO newLoan;
      for (Customer customer : customers) {
         customersInfo.add(new CustomerInfoDTO(customer.getName(), customer.getBalance()));
         for (AccountTransaction accountTransaction : customer.getTransactions()) {
            customersInfo.get(customersInfo.size() - 1).getTransactionDTOS().add(new AccountTransactionDTO(accountTransaction.getTimeOfTransaction(),
                    accountTransaction.getTransactionAmount(), accountTransaction.getIncomeOrExpense(),
                    accountTransaction.getBalanceBefore(), accountTransaction.getBalanceAfter()));

         }
         for (Loans lenderLoan : customer.getLenderList()) {
            newLoan = customerDTOClassArrange(lenderLoan);
            customersInfo.get(customersInfo.size() - 1).getLenderList().add(newLoan);
         }
         for (Loans borrowerLoan : customer.getBorrowerList()) {
            newLoan = customerDTOClassArrange(borrowerLoan);
            customersInfo.get(customersInfo.size() - 1).getBorrowerList().add(newLoan);
         }
         customersInfo.get(customersInfo.size() - 1).setLoansAmounts();
      }

      return customersInfo;
   }

   @Override
   public Customer getCustomerByName(String name) {
      for (Customer customer : customers) {
         if (name.equalsIgnoreCase(customer.getName())) {
            return customer;
         }
      }
      return null;
   }


   public List<PaymentsDTO> copyPaymentList(Loans loan) {
      List<PaymentsDTO> list = new ArrayList<>();
      for (Payment payment : loan.getStatus().getPayments()) {
         list.add(new PaymentsDTO(payment.getTimeOfPayment(), payment.getInterestComponent(),
                 payment.getSumOfPayment(), payment.getInitialComponent(), payment.isPayedSuccesfully()));
      }
      return list;
   }

   public LoanInfoDTO customerDTOClassArrange(Loans loan) {
      switch (loan.getStatus().getStatus()) {
         case "Pending": {
            return new PendingLoanInfoDTO(loan.getLOANID(), loan.getLoanCategory(), loan.getLoanSizeNoInterest(), loan.getInterestPerPayment(),
                    loan.getTimePerPayment(), loan.getStatus().getStatus(), loan.getCollectedSoFar());
         }
         case "Active": {
            double sumNextPayment = loan.getLoanSizeNoInterest()/(loan.getTimeLimitOfLoan()/loan.getTimePerPayment());
            if(loan.getStatus().getPayments().size() !=0 )
               sumNextPayment = loan.getStatus().returnLastPayment().getSumOfPayment();
            return new ActiveLoanInfoDTO(loan.getLOANID(), loan.getLoanCategory(), loan.getLoanSizeNoInterest(), loan.getInterestPerPayment(),
                    loan.getTimePerPayment(), loan.getStatus().getStatus(), loan.getStatus().getNextPaymentTime(), sumNextPayment);
         }
         case "Risk": {
            int numberOfPaymentNotPayed = loan.getStatus().getPayments().stream().filter(T->!T.isPayedSuccesfully()).mapToInt(S-> 1).sum();
            double sumOfNotPayed = loan.getStatus().returnLastPayment().getSumOfPayment();
            return new RiskLoanInfoDTO(loan.getLOANID(), loan.getLoanCategory(), loan.getLoanSizeNoInterest(), loan.getInterestPerPayment(),
                    loan.getTimePerPayment(), loan.getStatus().getStatus(),  numberOfPaymentNotPayed , sumOfNotPayed);
         }
         case "Finished": {
            return new FinishedLoanInfoDTO(loan.getLOANID(), loan.getLoanCategory(), loan.getLoanSizeNoInterest(), loan.getInterestPerPayment(),
                    loan.getTimePerPayment(), loan.getStatus().getStatus(), loan.getStatus().getStartingActiveTime(), loan.getStatus().getFinishTime());
         }
         default: //probably only new loan
            return new LoanInfoDTO(loan.getLOANID(), loan.getLoanCategory(), loan.getLoanSizeNoInterest(), loan.getInterestPerPayment(),
                    loan.getTimePerPayment(), loan.getStatus().getStatus());
      }

   }

   public void addMoneyToAccount(int userChoice, double moneyToAdd) {
      customers.get(userChoice - 1).addMoney(moneyToAdd);
   }

   public DisplayCustomerName namesForDisplay() {
      DisplayCustomerName names = new DisplayCustomerName();
      for (Customer customer : customers) {
         names.getCustomerList().put(customer.getName(), customer.getBalance());
      }
      return names;
   }

   public void drawMoneyFromAccount(int userChoice, double moneyToDraw) throws Exception {
      if (customers.get(userChoice - 1).getBalance() - moneyToDraw < 0) {
         throw new WithDrawMoneyException(customers.get(userChoice - 1).getBalance(), moneyToDraw);
      }
      customers.get(userChoice - 1).drawMoney(moneyToDraw);
   }

   @Override
   public void resetEngine() {
      customers.clear();
      loans.clear();
      loansByCategories.clear();
      resetTime();
   }


   public CategoriesListDTO getCategoriesList() {
      List<String> categories = new ArrayList<>();
      for (Map.Entry<String, List<Loans>> entry : loansByCategories.entrySet()) {
         categories.add(entry.getKey());
      }
      return new CategoriesListDTO(categories);
   }

   public List<NewLoanDTO> getFilteredLoans(List<String> categories, double minInterest, int minTime, String userName, int maxOpenLoans) {
      List<NewLoanDTO> validLoans = new ArrayList<>();
      List<Loans> filteredLoans = new ArrayList<>(loans);
      filteredLoans = filteredLoans.stream()
              .filter(l-> l.getStatus().getStatus().equals("New") || l.getStatus().getStatus().equals("Pending"))
              .filter(l-> categories.contains(l.getLoanCategory()))
              .filter(l-> minInterest <= l.getInterestPerPayment())
              .filter(l-> minTime <= l.getTimeLimitOfLoan())
              .filter(l-> !l.getBorrowerName().equals(userName))
              .filter(l-> (getCustomerByName(l.getBorrowerName()).getBorrowerList().stream()
              .filter(x -> !(x.getStatus().getStatus().equals("Finished"))).count() <= maxOpenLoans))
              .collect(Collectors.toList());

      for(Loans candidateLoan: filteredLoans){
         if(candidateLoan.getStatus().getStatus().equals("New")){
            validLoans.add(new NewLoanDTO(candidateLoan.getLOANID(), candidateLoan.getBorrowerName(),
                    candidateLoan.getLoanCategory(), candidateLoan.getLoanSizeNoInterest(),
                    candidateLoan.getTimeLimitOfLoan(), candidateLoan.getInterestPerPayment(),
                    candidateLoan.getTimePerPayment(), candidateLoan.getStatus().getStatus()));
         }
         else{
            validLoans.add(new PendingLoanDTO(candidateLoan.getLOANID(), candidateLoan.getBorrowerName(),
                    candidateLoan.getLoanCategory(), candidateLoan.getLoanSizeNoInterest(),
                    candidateLoan.getTimeLimitOfLoan(), candidateLoan.getInterestPerPayment(),
                    candidateLoan.getTimePerPayment(), candidateLoan.getStatus().getStatus(),
                    candidateLoan.getListOflenders(), candidateLoan.getCollectedSoFar(),
                    candidateLoan.getLoanSize() - candidateLoan.getCollectedSoFar()));
         }
      }
      return validLoans;
   }

   public void checkAmountOfInvestment(String customerName , double moneyToInvest) throws Exception {
      if(moneyToInvest < 1){
         throw new NumberFormatException();
      }
      Customer customer = getCustomerByName(customerName);
      if (getCustomerByName(customerName).getBalance() - moneyToInvest < 0) {
         throw new WithDrawMoneyException(customer.getBalance(), moneyToInvest);
      }
   }

   public void splitMoneyBetweenLoans(List<String> desiredLoansID, int moneyToInvest, String customerSelected, int maxOwnershipPercentage) {
      double percentage = maxOwnershipPercentage/100.0;
      Map<Loans, Integer> maxAmountPerLoan = new LinkedHashMap(); // save possible loans and amount
      for (String ID : desiredLoansID) {
         for (Loans findLoan : loans) {
            if (ID.equals(findLoan.getLOANID())) {
               double possibleInvestment = findLoan.getLoanSizeNoInterest() * percentage;
               possibleInvestment = min(possibleInvestment,(int)findLoan.getLeftToBeCollected());
               maxAmountPerLoan.put(findLoan, (int)possibleInvestment);
            }
         }
      }
      //recursive function
      splitEquallyBetweenLoans(maxAmountPerLoan,moneyToInvest,getCustomerByName(customerSelected),0);
   }

   private void splitEquallyBetweenLoans(Map<Loans,Integer> maxAmountPerLoan, double moneyToInvest, Customer customerSelected, double remainingLoansMin) {
      int numOfLoans = maxAmountPerLoan.size();
      if (numOfLoans == 0) {
         return;
      }
      int min = getLoansWithMinSumToPay(maxAmountPerLoan);
      if ((min - remainingLoansMin) * numOfLoans > moneyToInvest) {
         int rest = (int)(moneyToInvest) % numOfLoans;
         int i = 0;
         int afterRestAdd = (int)(moneyToInvest / numOfLoans + remainingLoansMin + 1);
         for (Map.Entry<Loans,Integer> entry : maxAmountPerLoan.entrySet()) {
            if(i < rest){
               addCustomerToLoan(entry.getKey(), customerSelected,  (double)afterRestAdd);
               i++;
            }
            else{
               addCustomerToLoan(entry.getKey(), customerSelected,  (double)(afterRestAdd - 1));
            }
         }
         return;
      }
      else {
         for (Map.Entry<Loans,Integer> entry : maxAmountPerLoan.entrySet()) {
            if (entry.getValue() == min) {
               addCustomerToLoan(entry.getKey(),customerSelected, min);

//               maxAmountPerLoan.remove(entry);

            }
         }
         maxAmountPerLoan.values().removeIf(value-> value == min);
//         loansToInvest.removeIf(x -> x.getLeftToBeCollected() + min == min);
         splitEquallyBetweenLoans(maxAmountPerLoan, moneyToInvest - (min - remainingLoansMin) * numOfLoans, customerSelected, min);
      }
   }

   private int getLoansWithMinSumToPay(Map<Loans,Integer> maxAmountPerLoan) {
      int min = 0;
      boolean isFirst = true;
      for (Map.Entry<Loans,Integer> entry : maxAmountPerLoan.entrySet()) {
         if (isFirst) {
            min = entry.getValue();
            isFirst = false;
         } else {
            if (entry.getValue() < min) {
               min = entry.getValue();
            }
         }
      }
      return min;
   }

   private void investInAllLoans(List<Loans> loansToInvest, double moneyToInvest, String customerSelected) throws Exception {
      Customer lender = getCustomerByName(customerSelected);
      double moneyInvested;
      double totalInvested = 0;
      for (Loans investInLoan : loansToInvest) {
         moneyInvested = investInLoan.getLeftToBeCollected();
         totalInvested += moneyInvested;
         addCustomerToLoan(investInLoan, lender, moneyInvested);
      }
      if (totalInvested < moneyToInvest) {
         throw new notAllAmountSuccessfullyInvested(moneyToInvest - totalInvested, totalInvested);
      }
   }

   public void addCustomerToLoan(Loans loan, Customer investor, double moneyToInvest) {
      loan.getListOflenders().put(investor.getName(), moneyToInvest);
      investor.getLenderList().add(loan);
      loan.setCollectedSoFar(moneyToInvest);
      loan.setLeftToBeCollected(moneyToInvest);
      investor.drawMoney(moneyToInvest);

      loan.updateStatusBeforeActive();
      if (loan.getStatus().getStatus().equals("Active")) {
         getCustomerByName(loan.getBorrowerName()).addMoney(loan.getLoanSize());
      }
   }

   public void moveTimeForward() {
      List<Loans> listByTime = new ArrayList<>();

      for (Loans itr : loans) {
         if (itr.getStatus().getNextPaymentTime() == time) {
            listByTime.add(itr);
            double InitialComponent = itr.getLoanSizeNoInterest() / (itr.getTimeLimitOfLoan() / itr.getTimePerPayment());
            double InterestComponent = InitialComponent * ((double) itr.getInterestPerPayment() / 100);
            if (itr.getStatus().getStatus().equals("Risk")){
               if ((itr.getStatus().getPayments().size() >= itr.getTimeLimitOfLoan() / itr.getTimePerPayment())) {
                  itr.getStatus().addPayment(new Payment(time, itr.getStatus().returnLastPayment().getInterestComponent(),
                          itr.getStatus().returnLastPayment().getSumOfPayment(),
                          itr.getStatus().returnLastPayment().getInitialComponent(), false));
               } else {
                  itr.getStatus().addPayment(new Payment(time, itr.getStatus().returnLastPayment().getInterestComponent() + InterestComponent,
                          itr.getStatus().returnLastPayment().getSumOfPayment() + InterestComponent + InitialComponent,
                          itr.getStatus().returnLastPayment().getInitialComponent() + InitialComponent, false));
               }
            }
            else if (itr.getStatus().getStatus().equals("Active")) {
               itr.getStatus().addPayment(new Payment(time, InterestComponent,
                       InterestComponent + InitialComponent,
                       InitialComponent, false));
            }

         }
      }
      listByTime = listByTime.stream().sorted(Loans::compareTo).collect(Collectors.toList());
      for(Loans itr : listByTime)
      {
         if(!itr.getStatus().getStatus().equals("Finished"))
            paymentMethod(itr);
      }
      timeToSave++;
      time = timeToSave;
   }

   public void paymentMethod(Loans loan)
   {
      double money = loan.getStatus().returnLastPayment().getSumOfPayment();
      Customer customer = getCustomerByName(loan.getBorrowerName());
      if(money > customer.getBalance()){
         if(loan.getStatus().getStatus().equals("Active"))
            loan.getStatus().setStatus("Risk");
      }
      else {
         customer.drawMoney(money);
         for(Map.Entry<String,Double> entry : loan.getListOflenders().entrySet()){
            double ahuzYahasi = entry.getValue()/loan.getLoanSizeNoInterest();
            getCustomerByName(entry.getKey()).addMoney(ahuzYahasi*money);
         }
         loan.getStatus().returnLastPayment().setPayedSuccesfully(true);
         if(loan.getStatus().getStatus().equals("Risk")) {
            loan.getStatus().setStatus("Active");
         }
         updatePaymentComponents(loan);

      }
      loan.getStatus().setNextPaymentTime(loan.getTimePerPayment());
      if(loan.getStatus().getInitialLeftToPay() == 0) {
         loan.changeToFinish();
      }
   }

   public void updatePaymentComponents(Loans loan){
      loan.getStatus().setInitialLeftToPay(loan.getStatus().returnLastPayment().getInitialComponent());
      loan.getStatus().setInterestLeftToPay(loan.getStatus().returnLastPayment().getInterestComponent());
      loan.getStatus().setInterestPayed(loan.getStatus().returnLastPayment().getInterestComponent());
      loan.getStatus().setInitialPayed(loan.getStatus().returnLastPayment().getInitialComponent());
   }

   public ArrayList getCustomerNames(){
      return (ArrayList) customers.stream().map(user ->user.getName()).collect(Collectors.toList());
   }

   public Loans getLoanByName(String loanName){
      for (Loans loan : loans) {
         if (loan.getLOANID().equalsIgnoreCase(loanName)) {
            return loan;
         }
      }
      return null;
   }
   public void closeLoan(String customerName, String loanName, double amount)throws Exception{
      Customer customer = getCustomerByName(customerName);
      Loans loan = getLoanByName(loanName);

      if(amount>customer.getBalance())
         throw new WithDrawMoneyException(customer.getBalance(), amount);
      if(amount+loan.getCollectedSoFar()<loan.getLoanSize())
         throw new Exception(); //TODO: add the relevant exception.

      while(!loan.getStatus().getStatus().equals("Finished"))
      {
         paymentMethod(loan);
      }
   }

   public int getNumOfLoans(){
      return loans.size();
   }

   public List<PaymentNotificationDTO> getNotifications(String userName){
      List<PaymentNotificationDTO> notifications = new ArrayList<>();
      getCustomerByName(userName).getNotifications()
              .forEach(n-> notifications.add(new PaymentNotificationDTO(n.getLoanID(),n.getPaymentYaz(), n.getSumOfPayment())));
      return notifications;
   }

}