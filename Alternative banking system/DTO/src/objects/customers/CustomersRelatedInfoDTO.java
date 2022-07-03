package objects.customers;

import objects.admin.LoanAndCustomerInfoDTO;
import objects.loans.*;
import objects.loans.payments.PaymentNotificationDTO;

import java.util.List;

public class CustomersRelatedInfoDTO {
    List<NewLoanDTO> newLoans;
    List<PendingLoanDTO> pendingLoans;
    List<ActiveRiskLoanDTO> activeLoans;
    List<ActiveRiskLoanDTO> riskLoans;
    List<FinishedLoanDTO> finishedLoans;
    CustomerInfoDTO customerInfo;
//    List<String> categories;
//    List<PaymentNotificationDTO> paymentsNotificationList;
//    List<LoansForSaleDTO> loansForSaleList;

    public CustomersRelatedInfoDTO(List<NewLoanDTO> newLoans, List<PendingLoanDTO> pendingLoans, List<ActiveRiskLoanDTO> activeLoans, List<ActiveRiskLoanDTO> riskLoans, List<FinishedLoanDTO> finishedLoans, CustomerInfoDTO customerInfo) {
        this.newLoans = newLoans;
        this.pendingLoans = pendingLoans;
        this.activeLoans = activeLoans;
        this.riskLoans = riskLoans;
        this.finishedLoans = finishedLoans;
        this.customerInfo = customerInfo;
//        this.categories = categories;
//        this.paymentsNotificationList = paymentsNotificationList;
//        this.loansForSaleList = loansForSaleList;
    }

    //Getters
    public List<NewLoanDTO> getNewLoans() {return newLoans;}
    public List<PendingLoanDTO> getPendingLoans() {return pendingLoans;}
    public List<ActiveRiskLoanDTO> getActiveLoans() {return activeLoans;}
    public List<ActiveRiskLoanDTO> getRiskLoans() {return riskLoans;}
    public List<FinishedLoanDTO> getFinishedLoans() {return finishedLoans;}
    public CustomerInfoDTO getCustomerInfo() {
        return customerInfo;
    }
//    public List<String> getCategories() {
//        return categories;
//    }
//    public List<PaymentNotificationDTO> getPaymentsNotificationList() {
//        return paymentsNotificationList;
//    }
//    public List<LoansForSaleDTO> getLoansForSaleList() {
//        return loansForSaleList;
//    }
}
