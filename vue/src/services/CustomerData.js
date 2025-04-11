import http from "../http-common";

class CustomerData {

    getAllCustomers() {
        return http.get('/customers');
    }
    getCustomerById(id) {
        return http.get(`/customers/${id}`);
    }
    getCustomersByName(name) {
        return http.get(`/customers/name/${name}`);
    }
    getCustomersByEmail(email) {
        return http.get(`/customers/email/${email}`);
    }
    getCustomersByPhone(phone) {
        return http.get(`/customers/phone/${phone}`);
    }

    createCustomer(customer) {
        return http.post('/customers', customer);
      }
    }
export default new CustomerData(); 
