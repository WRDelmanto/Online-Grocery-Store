import http from "../http-common";

class ItemService {
  getAllItems() {
    return http.get('/items');
  }
  getItemById(id) {
    return http.get(`/items/${id}`); 
  }
  getItemsByCategory(category) {
    return http.get(`/items/category/${category}`);
  }

  createItem(item) {
    return http.post('/items', item);
  }
  updateStockLevel(id, newStockLevel) {
    return http.patch(`/items/${id}/stock`, { stockLevel: newStockLevel });
  }
  updateItem(id, item) {
    return http.put(`/items/${id}`, item);
  }

}
export default new ItemService();



