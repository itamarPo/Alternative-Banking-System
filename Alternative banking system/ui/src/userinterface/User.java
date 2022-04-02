
package userinterface;

import database.EngineInterface;
import database.Engine;
import exceptions.accountexception.LoansDoesNotReachSumOfInvestment;
import exceptions.accountexception.NoLoanSelectedForInlay;
import exceptions.accountexception.NoMatchesFound;
import exceptions.accountexception.WithDrawMoneyException;
import exceptions.filesexepctions.*;
import objects.DisplayCustomerName;
import objects.categories.CategoriesListDTO;
import objects.customers.CustomerInfoDTO;
import objects.loans.*;

import javax.swing.text.StyledEditorKit;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.*;

public class User implements UserInterface {
    private Engine data;
    private Scanner scanner;
    private Boolean FileLoaded;


    public User(){
        this.data = new Engine();
        this.scanner = new Scanner(System.in);
        this.FileLoaded = false;
    }

    public void start(){
        //boolean start = true;
       menu();
    }

    public void menu()  {
        int userIntegerInput;
        System.out.println("Welcome!");
        printMenu();
        do {
            userIntegerInput = getValidInput();
            if(!FileLoaded && userIntegerInput > 1 && userIntegerInput < 8) {
                System.out.println("A file was not loaded! please load a file before choosing any other option.");
            }
            else {
                switch (userIntegerInput) {
                    case 1: {
                        loadFile();
                        break;
                    }
                    case 2: {
                        getLoansInfo();
                        break;
                    }
                    case 3:{
                        getCustomersInfo();
                        break;
                    }
                    case 4:{
                        addMoneyToAccount();
                        break;
                    }
                    case 5:{
                        getMoneyFromAccount();
                        break;
                    }
                    case 6:{
                        activationOfInlay();
                        break;
                    }
                    default: {
                        System.out.println("Goodbye!");
                    }
                }
                if(userIntegerInput != 8){
                    printMenu();
                }

            }
        } while (userIntegerInput != 8) ;

    }

        public int getValidInput() {
            int userIntegerInput = 0;
            Boolean validInput = false;
            while (!validInput) {
                try {
                    userIntegerInput = scanner.nextInt();
                    scanner.nextLine(); // clear buffer
                    validInput = true;
                }
                catch (InputMismatchException exception) {
                    System.out.println("This is not a natural number, please enter a natural number between 1 to 8:");
                    validInput = false;
                    System.out.println(scanner.nextLine());
                }
                if ((userIntegerInput < 1 || userIntegerInput > 8) && validInput)
                    System.out.println("Invalid number input! /r/n Please try again. Make sure that you enter a natural number between 1 to 8: ");
            }
            return userIntegerInput;
        }
    @Override
    public void printMenu() {
        System.out.println("\r\nPlease select one fo the following options:" );
        System.out.println("1. Load file ");
        System.out.println("2. Show loans information and their status  ");
        System.out.println("3. Show clients information ");
        System.out.println("4. Add money to an account ");
        System.out.println("5. Draw money from an account");
        System.out.println("6. Activation of inlay ");
        System.out.println("7. Advance To next time period and provide payment");
        System.out.println("8. Exit program");
    }

    @Override
    public void loadFile(){
        Boolean FileLoadedSuccessfully = false;
        System.out.println("Please enter a file name that you wish to load:");
        try {
            FileLoadedSuccessfully = data.loadFile(scanner.nextLine());
        }
        //JAXB error
        catch (JAXBException e) {
            System.out.println("JAXB related error");
        } catch (FileNotFoundException e) {
            System.out.println("The file's path was incorrect. please make sure that the path is correct.");
        } catch (TwoClientsWithSameNameException e) {
            e.printMessage();
        } catch (NotXmlExcpetion e) {
            e.printMessage();
        } catch (OwnerLoanNotExistException e) {
            e.printMessage();
        } catch (LoanCategoryNotExistException e){
            e.printMessage();
        } catch (TimeOfPaymentNotDivideEqualyException e){
            e.printMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(FileLoadedSuccessfully){
            FileLoaded = true;
            System.out.println("File loaded successfully!");
        }


    }

    @Override
    public void getLoansInfo() {
        List<NewLoanDTO> loans = data.getLoansInfo();
        for(NewLoanDTO loan : loans){
            loan.print();
            System.out.println("");
        }
    }

    @Override
    public void getCustomersInfo() {
        List<CustomerInfoDTO> customersInfo = data.getCustomerInfo();
        for(CustomerInfoDTO customerInfo : customersInfo)
            customerInfo.print();
    }


    @Override
    public void moveTimeForward() {

    }



    @Override
    public void addMoneyToAccount() {
        System.out.println("Please select the number of the desired customer:");
        DisplayCustomerName customersList = data.namesForDisplay();
        customersList.printNamesAndBalance();
        int numOfCustomers = customersList.getCustomerList().size();
        int userChoice = validUserCustomerChoice(numOfCustomers);
        double moneyToAdd = -1;
        System.out.println("Please enter the amount you wish to add. make sure that you enter a suitable number:");
        moneyToAdd = validTransactionChoice();
        data.addMoneyToAccount(userChoice,moneyToAdd);
        System.out.println("The money was successfully added. ");
    }
    @Override
    public void getMoneyFromAccount() {
        System.out.println("Please select the number of the desired customer:");
        DisplayCustomerName customersList = data.namesForDisplay();
        customersList.printNamesAndBalance();
        int numOfCustomers = customersList.getCustomerList().size();
        int userChoice = validUserCustomerChoice(numOfCustomers);
        double moneyToDraw = -1;
        System.out.println("Please enter the amount you wish to draw. make sure that you enter a suitable number:");
        moneyToDraw = validTransactionChoice();
        try {
            data.drawMoneyFromAccount(userChoice,moneyToDraw);
            System.out.println("The money was successfully withdrawn. ");
        }
        catch (WithDrawMoneyException e){
            e.printMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activationOfInlay() {
        DisplayCustomerName customerNames = data.namesForDisplay();
        int numOfCustomers = customerNames.getCustomerList().size();
        System.out.println("Please select the number of the desired customer for the inlay:");
        customerNames.printNamesAndBalance();
        int userChoice = validUserCustomerChoice(numOfCustomers);

        try {
            System.out.println("Please enter a positive number for the customer to invest: (This option is mandatory!)");
            double moneyToInvest = validTransactionChoice();
            String customerSelected = null;
            data.checkAmountOfInvestment(userChoice, moneyToInvest);
            int i = 1,j = 0;
            for (Map.Entry<String,Double> entry : customerNames.getCustomerList().entrySet()){
                if(j == userChoice - 1){
                    customerSelected = entry.getKey();
                }
                j++;
            }
            List<NewLoanDTO> possibleLoans = getInlayDetails(customerSelected);
            if(possibleLoans.size() == 0)
                throw new NoMatchesFound();
            for(NewLoanDTO loan : possibleLoans){
                System.out.println(i + ":");
                loan.print();
                i++;
            }
            System.out.println("\r\nPlease enter the loan numbers that you wish to invest: \r\n(The loan's details are above this statement. Please enter the numbers seperated by spaces or ENTER if you're not interested to invest in any of them.\r\nPlease note that entering the same number twice will make no difference.)");
            possibleLoans = filterLoansAccordingToUser(possibleLoans);
            data.splitMoneyBetweenLoans(possibleLoans, moneyToInvest, customerSelected);
            System.out.println("Inlay has completed successfully!");
        }
        catch(WithDrawMoneyException e){
            e.printInvestMessage();
            return;
        }catch(NoLoanSelectedForInlay e){
            e.print();
        } catch(NoMatchesFound e){
            e.printMessage();
        }catch (LoansDoesNotReachSumOfInvestment e) {
            e.print();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<NewLoanDTO> filterLoansAccordingToUser(List<NewLoanDTO> possibleLoans) throws Exception{
        Boolean validInput = false;
        List<NewLoanDTO> LoansSelected = new ArrayList<>();
        while (!validInput) {
            String input = scanner.nextLine();

            if (input.equals("")) {
                throw new NoLoanSelectedForInlay();
            }
            String[] optionalNumbers = input.split(" ");
            for (String possibleNumber : optionalNumbers) {
                validInput = true;
                try {
                    int number = Integer.parseInt(possibleNumber);
                    if (number > possibleLoans.size() || number < 1) {

                        throw new NumberFormatException();
                    }
                    if(!LoansSelected.contains(possibleLoans.get(number - 1))){
                        LoansSelected.add(possibleLoans.get(number - 1));
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! please make sure to enter loans numbers seperated by spaces or ENTER. \r\nPlease try again: ");
                    validInput = false;
                    LoansSelected.clear();
                    break;
                }
            }
        }
        return LoansSelected;
    }

    public List<NewLoanDTO> getInlayDetails(String userName) throws Exception{
        System.out.println("Please select the desired categories from the list: \r\n(Enter categories numbers seperated by spaces. This option isn't mandatory! if not interested just press ENTER.\r\nPlease note that entering the same number twice will make no difference)");
        CategoriesListDTO systemCategories = data.getCategoriesList();
        systemCategories.print();
        List<String> categoriesAfterFilter = new ArrayList<>();
        categoriesAfterFilter = getFilteredCategories(categoriesAfterFilter,systemCategories.getCategoriesList());
        System.out.println("Please select the minimum interest you're willing to accept: \r\n(This option isn't mandatory! If you aren't interested in this option please press ENTER)");
        int interest = getPositiveInt(true);
        System.out.println("Please select the minimum time of loan you're willing to accept: \r\n(This option isn't mandatory! If you aren't interested in this option please press ENTER)");
        int minTime = getPositiveInt(false);
        return data.getFilteredLoans(categoriesAfterFilter, interest,minTime,userName);
    }

    public List<String> getFilteredCategories(List<String> categoriesAfterFilter, List<String> categoriesBeforeFilter) {
        Boolean validInput = false;
        while (!validInput) {
            String input = scanner.nextLine();
            if (input.equals("")) {
                categoriesAfterFilter = categoriesBeforeFilter;
                validInput = true;
            }
            else {
                String[] choices = input.split(" ");
                for (String choice : choices) {
                    validInput = true;
                    try {
                        int number = Integer.parseInt(choice);
                        if (number > categoriesBeforeFilter.size() || number < 1) {
                            throw new NumberFormatException();
                        }
                        if(!categoriesAfterFilter.contains(categoriesBeforeFilter.get(number-1))) {
                            categoriesAfterFilter.add(categoriesBeforeFilter.get(number - 1));
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! please make sure to enter categories numbers seperated by spaces or ENTER. \r\nPlease try again: ");
                        categoriesAfterFilter.clear();
                        validInput = false;
                        break;
                    }
                }
            }
        }
        return categoriesAfterFilter;
    }

    public int getPositiveInt(Boolean IsInterest){
        Boolean validInput = false;
        int number = 0;
        while (!validInput) {
            String input = scanner.nextLine();
            if (input.equals("")) {
                return number;
            } else {
                validInput = true;
                try {
                    number = Integer.parseInt(input);
                    if(IsInterest) {
                        if (number > 100 || number < 1) {
                            throw new NumberFormatException();
                        }
                    }else{
                        if(number < 1){
                            throw new NumberFormatException();
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please make sure you enter a valid number. \r\nPlease try again:");
                    validInput = false;
                }
            }
        }
        return number;
    }
    public int validUserCustomerChoice(int numOfCustomers) {
        int userChoice = 0;
        do{
            try {
                userChoice = scanner.nextInt();
                if (userChoice < 1 || userChoice > numOfCustomers) {
                    System.out.println("Invalid input. Please enter a suitable number:");
                }
            } catch (InputMismatchException exception) {
                System.out.println("This isn't a number! please enter a suitable number:");
            }
            scanner.nextLine(); //Buffer
        } while (userChoice < 1 || userChoice > numOfCustomers);
        return userChoice;
    }
    public double validTransactionChoice(){
        double moneyToDraw = 0;
        do {
            try {
                moneyToDraw = scanner.nextDouble();
                scanner.nextLine(); //buffer
                if (moneyToDraw <= 0)
                    System.out.println("Invalid input. Please enter a positive number!");
                else{

                }

            } catch (InputMismatchException exception) {
                System.out.println("Incorrect Input. Please make sure that you enter a positive number.");
                scanner.nextLine();
            }
        } while (moneyToDraw <= 0);
        return moneyToDraw;
    }

}

