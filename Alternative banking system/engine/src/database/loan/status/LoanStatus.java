package database.loan.status;

import database.Engine;
import database.loan.Payment;

import java.util.ArrayList;
import java.util.List;

public class LoanStatus implements LoanStatusInterface {

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

    public LoanStatus() {
        this.status = "Active";
        this.pay = new ArrayList<Payment>();
        this.startingActiveTime = Engine.getTime();
        this.finishTime = 0;
    }

    @Override
    public void print() {

    }
}
