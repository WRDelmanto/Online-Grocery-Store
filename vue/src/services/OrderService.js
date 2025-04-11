import http from "../http-common";

class OrderService {
    createOrder(order) {
        return http.post('/orders', order);
    }
}

export default new OrderService();