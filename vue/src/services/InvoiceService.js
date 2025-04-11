import http from "../http-common";

class InvoiceService {
    addInvoice(invoice) {
        return http.post('/invoices', invoice);
      }
}

export default new InvoiceService();