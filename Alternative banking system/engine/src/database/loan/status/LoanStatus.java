package database.loan.status;

import com.sun.xml.internal.bind.v2.TODO;
import database.Engine;
import database.loan.Payment;

import java.util.ArrayList;
import java.util.List;

public class LoanStatus implements LoanStatusInterface {
 //TODO: changes to status according to the word file.
    private String status;
    private List<Payment> payments;
    private int startingActiveTime;
    private int finishTime;
    private int nextPaymentTime;

    public void setStatus(String status) {
        this.status = status;
    }

    public void addPay(Payment payment) {
        this.payments.add(payment) ;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public LoanStatus() {
        this.status = "Active";
        this.payments = new ArrayList<>();
        this.startingActiveTime = Engine.getTime();
        this.finishTime = 0;
        this.nextPaymentTime = startingActiveTime;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public void print() {

    }
}
