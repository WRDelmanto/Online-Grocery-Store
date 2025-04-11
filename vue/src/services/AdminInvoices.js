import http from "../http-common";

class AdminInvoices {

    getAllInvoices() {
        return http.get('/invoices');
    }

    getInvoiceByID(id) {
        return http.get(`/invoices/${id}`);
    }

    getInvoicesByPaymentStatus(paymentStatus){
        return http.get(`/invoices/payment-status/${paymentStatus}`);
    }

    getInvoicesByPaymentMethod(paymentMethod){
        return http.get(`/invoices/payment-method/${paymentMethod}`);
    }
    addInvoice(invoice) {
        return http.post('/invoices', invoice);
      }
}

export default new AdminInvoices(); // Export object