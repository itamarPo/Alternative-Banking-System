package database.loan;

import java.util.List;

public interface Loans {
    String getLoanName();
    String getBorrowerName();
    String getLoanCategory();
    int getStartingTime();
    int getTimeLimitOfLoan();
    int getLOANID();
    double getLoanSize();
    double getLoanInterest();
    int getTimePerPayment();
    List<String> getListOflenders();

}
