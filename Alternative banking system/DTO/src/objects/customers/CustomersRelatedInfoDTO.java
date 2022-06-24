package objects.customers;

import objects.admin.LoanAndCustomerInfoDTO;
import objects.loans.LoansForSaleDTO;
import objects.loans.NewLoanDTO;
import objects.loans.payments.PaymentNotificationDTO;

import java.util.List;

public class CustomersRelatedInfoDTO {
    List<NewLoanDTO> relatedLoans;
    CustomerInfoDTO customerInfo;
    List<String> categories;
    List<PaymentNotificationDTO> paymentsNotificationList;
    List<LoansForSaleDTO> loansForSaleList;

    public CustomersRelatedInfoDTO(List<NewLoanDTO> relatedLoans, CustomerInfoDTO customerInfo, List<String> categories, List<PaymentNotificationDTO> paymentsNotificationList, List<LoansForSaleDTO> loansForSaleList) {
        this.relatedLoans = relatedLoans;
        this.customerInfo = customerInfo;
        this.categories = categories;
        this.paymentsNotificationList = paymentsNotificationList;
        this.loansForSaleList = loansForSaleList;
    }

    public List<NewLoanDTO> getRelatedLoans() {
        return relatedLoans;
    }

    public CustomerInfoDTO getCustomerInfo() {
        return customerInfo;
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<PaymentNotificationDTO> getPaymentsNotificationList() {
        return paymentsNotificationList;
    }

    public List<LoansForSaleDTO> getLoansForSaleList() {
        return loansForSaleList;
    }
}
