package userinterface;



import database.client.Client;
import database.loan.Loans;


public interface UserInterface {
    void printMenu();
    Client getClientInfo();
    Loans getLoanInfo();
    void moveTimeForward();
    void loadFile();
    void addMoneyToAccount(Client client);
    void getMoneyFromAccount(Client client);
}
