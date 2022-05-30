package userinterface.customer.inlay;

import database.Engine;
import database.loan.Loans;
import javafx.concurrent.Task;
import objects.customers.loanInfo.LoanInfoDTO;
import objects.loans.NewLoanDTO;

import java.util.List;

public class inlayTask extends Task<List<NewLoanDTO>> {
    List<String> categories;
    double minInterest;
    int minTime;
    String userName;
    int maxOpenLoans;

    Engine engine;

    public inlayTask(List<String> categories, double minInterest, int minTime, String userName, int maxOpenLoans, Engine engine) {
        this.categories = categories;
        this.minInterest = minInterest;
        this.minTime = minTime;
        this.userName = userName;
        this.maxOpenLoans = maxOpenLoans;
        this.engine = engine;
    }

    @Override
    protected List<NewLoanDTO> call() throws Exception {
        List<NewLoanDTO> loans =  engine.getFilteredLoans(categories,minInterest,minTime,userName,maxOpenLoans);
        updateProgress(0.3,1);
        Thread.sleep(700);
        updateProgress(0.6,1);
        Thread.sleep(700);
        // List<NewLoanDTO> filteredLoans = engine.validLoansToInlay(loans);
        updateProgress(1,1);
        return loans;
    }
}
