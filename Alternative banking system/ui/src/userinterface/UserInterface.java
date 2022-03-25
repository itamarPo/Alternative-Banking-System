package userinterface;



import database.client.CustomerInterface;
import database.loan.LoansInterface;


public interface UserInterface {
    void printMenu();
    CustomerInterface getClientInfo();
    LoansInterface getLoanInfo();
    void moveTimeForward();
    void loadFile();
    void addMoneyToAccount(CustomerInterface client);
    void getMoneyFromAccount(CustomerInterface client);
}
