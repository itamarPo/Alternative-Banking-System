
package userinterface;

import database.DataBase;
import database.client.Client;
import database.client.ClientImp;
import database.loan.Loans;

public class UserInterfaceImpl implements UserInterface {


    public void start(){
       if(menu()==1)
        DataBase data;
    }

    public int menu() {

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
