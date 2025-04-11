import ItemService from './ItemService'; 

class CartService {
  getCartItems() {
    const cart = localStorage.getItem('cart');
    if (!cart) return [];
    return cart.split(',').map(item => {
      const [id, quantity] = item.split(':');
      return { id: parseInt(id), quantity: parseInt(quantity) };
    });
  }
  async checkout(cartItems) {
    try {
      const stockUpdatePromises = cartItems.map(item => {
        const newStockLevel = item.stockLevel - item.cartQuantity;
        return ItemService.updateItem(item.id, {
          ...item,
          stockLevel: newStockLevel
        });
      });
      await Promise.all(stockUpdatePromises);
      this.clearCart();
      return true;
    } catch (error) {
      console.error('Error updating stock levels:', error);
      throw error;
    }
  }

  async validateStock(cartItems) {
    try {
      const stockValidationPromises = cartItems.map(async cartItem => {
        const response = await ItemService.getItemById(cartItem.id);
        const currentStock = response.data.stockLevel;
        return {
          ...cartItem,
          isValid: currentStock >= cartItem.cartQuantity,
          currentStock
        };
      });
      const validatedItems = await Promise.all(stockValidationPromises);
      return validatedItems.every(item => item.isValid);
    } catch (error) {
      console.error('Error validating stock:', error);
      throw error;
    }
  }
  fetchCartItemDetails() {
    const cartData = this.getCartItems();
    if (cartData.length === 0) {
      console.log("Cart empty");
      return Promise.resolve([]); 
    }
    const promises = cartData.map(cartItem =>
      ItemService.getItemById(cartItem.id)
        .then(response => {
          return {
            ...response.data,
            cartQuantity: cartItem.quantity
          };
        })
        .catch(error => {
          console.error(error);
          return null; 
        })
    );
    return Promise.all(promises)
      .then(results => results.filter(item => item !== null))  
      .catch(error => {
        console.error(error);
        return [];
      });
  }
  updateCart(cartItems) {
    const serializedCart = cartItems.map(item => `${item.id}:${item.quantity}`).join(',');
    localStorage.setItem('cart', serializedCart);
  }
  clearCart() {
    localStorage.removeItem('cart');
  }
}

export default new CartService();
