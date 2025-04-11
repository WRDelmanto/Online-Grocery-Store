import http from "../http-common";

class AdminOrders {

    getAllOrders() {
        return http.get('/orders');
    }

    getOrdersByStatus(statusOrder) {
        return http.get(`/orders/status/${statusOrder}`)
    }

    getOrdersByDeliveryType(deliveryType) {
        return http.get(`/orders/delivery-type/${deliveryType}`)
    }
    
    getOrderById(id){
        return http.get(`/orders/${id}`)
    }

    updateOrder(id, order){
        return http.put(`/orders/${id}`, order)
    }

createOrder(orderData) {
    return http.post('/orders', orderData);
  }
}
export default new AdminOrders(); // Export object