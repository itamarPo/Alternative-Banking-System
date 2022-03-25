package userinterface;



import database.client.Client;
import database.loan.Loans;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;


public interface UserInterface {
    void printMenu();
    Client getClientInfo();
    Loans getLoanInfo();
    void moveTimeForward();
    void loadFile();
    void addMoneyToAccount(Client client);
    void getMoneyFromAccount(Client client);
}
