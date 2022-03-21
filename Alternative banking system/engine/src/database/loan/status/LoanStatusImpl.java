package database.loan.status;

import database.DataBase;
import database.loan.Payment;

import java.util.ArrayList;
import java.util.List;

public class LoanStatusImpl implements LoanStatus{

    private String status;
    private List<Payment> pay;
    private int startingActiveTime;
    private int finishTime;

    public void setStatus(String status) {
        this.status = status;
    }

    public void addPay(Payment pay) {
        this.pay.add(pay) ;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public LoanStatusImpl() {
        this.status = "Active";
        this.pay = new ArrayList<Payment>();
        this.startingActiveTime = DataBase.time;
        this.finishTime = 0;
    }
}
