package userinterface;



import database.client.CustomerInterface;
import database.loan.LoansInterface;


public interface UserInterface {
    void printMenu();
    void getCustomersInfo();
    void getLoansInfo();
    void moveTimeForward();
    void loadFile();
    void addMoneyToAccount();
    void getMoneyFromAccount();
}
