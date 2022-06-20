package objects.customers;

import objects.admin.LoanAndCustomerInfoDTO;
import objects.loans.LoansForSaleDTO;
import objects.loans.NewLoanDTO;
import objects.loans.payments.PaymentNotificationDTO;

import java.util.List;

public class CustomersRelatedInfoDTO extends LoanAndCustomerInfoDTO {
    List<PaymentNotificationDTO> paymentsNotificationList;
    List<LoansForSaleDTO> loansForSaleList;

    public CustomersRelatedInfoDTO(List<CustomerInfoDTO> customerList, List<NewLoanDTO> loanList, List<PaymentNotificationDTO> paymentsList, List<LoansForSaleDTO> loansForSaleList) {
        super(customerList, loanList);
        this.paymentsNotificationList = paymentsList;
        this.loansForSaleList = loansForSaleList;
    }

    public List<PaymentNotificationDTO> getPaymentsNotificationList() {
        return paymentsNotificationList;
    }

    public List<LoansForSaleDTO> getLoansForSaleList() {
        return loansForSaleList;
    }
}
