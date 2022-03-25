package database.loan.status;

import com.sun.xml.internal.bind.v2.TODO;
import database.Engine;
import database.loan.Payment;

import java.util.ArrayList;
import java.util.List;

public class LoanStatus implements LoanStatusInterface {
 //TODO: changes to status according to the word file.
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
