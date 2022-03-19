package database;

import java.util.List;

public class ClientImp  implements Client{
    private String name;
    private final int ID;
    double money;
    private List<LoansImpl> lenderList;
    private List<LoansImpl> borrowerList;

    public ClientImp(String clientName, int clientId){
        name = clientName;
        ID = clientId;
        lenderList = new List<LoansImpl>(); //need to implemet List<LoansImpl>
        borrowerList = new List<LoansImpl>();
        money = 0;
    }

    @Override
    public void changeMoneyAmount(int amount) {
        if(money+amount < 0)
            money = 0;
        else
            money += amount;
    }

    @Override
    public double showMoney() { return money; }

    @Override
    public int getId() { return ID; }

    @Override
    public String getName() { return name; }

    @Override
    public void addLoanToClient(LoansImpl loan, boolean lenderOrBorrower) {
        if(lenderOrBorrower) // lender
           lenderList.add(loan);
        else
            borrowerList.add(loan);

    }


}
