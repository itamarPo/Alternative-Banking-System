
package userinterface;

import database.DataBase;
import database.DataBaseImpl;
import database.client.Client;
import database.client.ClientImp;
import database.loan.Loans;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterfaceImpl implements UserInterface {
    private DataBase data;
    private Scanner scanner;


    public UserInterfaceImpl(){
        this.data = new DataBaseImpl();
        this.scanner = new Scanner(System.in);
    }

    public void start(){

        boolean start = true;
       menu();

    }

    public void menu()  {
        int userIntegerInput;
        Boolean FileLoaded = false;
        printMenu();
        do {
            userIntegerInput = getValidInput();
            if(!FileLoaded && userIntegerInput != 1) {
                System.out.println("A file was not loaded! please load a file before choosing any other option.");
            }
            else {
                switch (userIntegerInput) {
                    case 1: {
                        System.out.println("Please enter the full file's path:");
                        //String string = scanner.nextLine();
                        try {
                            data.loadFile(scanner.nextLine());
                        } catch (JAXBException e) {
                            e.printStackTrace();
                            System.out.println("JAXB related error");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            System.out.println("The file's path was incorrect. please make sure that the path is correct.");
                        }
                        break;
                    }
                    case 2: {

                    }
                    case 3: {

                    }
                    case 4: {

                    }
                    case 5: {

                    }
                    case 6: {

                    }
                    default: {

                    }

                }
                printMenu();
            }
        }
            while (userIntegerInput != 8) ;
    }

        public int getValidInput() {

            int userIntegerInput = 0;
            Boolean validInput = false;
            while (!validInput) {
                try {
                    userIntegerInput = scanner.nextInt();
                    validInput = true;
                } catch (InputMismatchException exception) {
                    System.out.println("This is not a natural number, please enter a natural number between 1 to 8:");
                    validInput = false;
                }
                if ((userIntegerInput < 1 || userIntegerInput > 8) && validInput)
                    System.out.println("Invalid number input! /r/n Please try again. Make sure that you enter a natural number between 1 to 8: ");
            }
            return userIntegerInput;
        }
    @Override
    public void printMenu() {
        System.out.println("1. ");
    }

    @Override
    public Client getClientInfo() {
        return null;
    }

    @Override
    public Loans getLoanInfo() {
        return null;
    }

    @Override
    public void moveTimeForward() {

    }

    @Override
    public void loadFile() {

    }

    @Override
    public void addMoneyToAccount(Client client) {

    }

    @Override
    public void getMoneyFromAccount(Client client) {

    }
}
