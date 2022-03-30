
package userinterface;

import database.EngineInterface;
import database.Engine;
import exceptions.accountexception.WithDrawMoneyException;
import exceptions.filesexepctions.*;
import objects.DisplayCustomerName;
import objects.customers.CustomerInfoDTO;
import objects.loans.*;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class User implements UserInterface {
    private EngineInterface data;
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
            if(!FileLoaded && userIntegerInput != 1 && userIntegerInput != 8) {
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
                    default: {
                        System.out.println("This option has not been implemented yet!");
                    }
                }
                printMenu();
            }
        } while (userIntegerInput != 8) ;
        System.out.println("Goodbye!");
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
        System.out.println("Please enter the amount you wish to draw. make sure that you enter a suitable number:");
        do { // positive amount of money to draw check
            try {
                moneyToAdd = scanner.nextDouble();
                scanner.nextLine(); //buffer
                if (moneyToAdd <= 0)
                    System.out.println("Invalid input. Please enter a positive number!");

            } catch (InputMismatchException exception) {
                System.out.println("Incorrect Input. Please make sure that you enter a positive number.");
                scanner.nextLine();
            }
        } while (moneyToAdd <= 0);
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
}
