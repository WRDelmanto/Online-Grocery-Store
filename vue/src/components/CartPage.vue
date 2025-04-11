<template>
  <div class="cart-page">
    <div class="cart-header">
      <button @click="goBack" class="back-btn">← Back</button>
      <h2>Your Cart</h2>
    </div>
    <div v-if="cartItems.length === 0" class="empty-cart">Nothing here <i class="fas fa-sad-tear"></i></div>
    <div v-else class="cart-content">
      <div class="cart-items">
        <div v-for="item in cartItems" :key="item.id" class="cart-item">
          <img :src="item.imageURL" :alt="item.name" class="cart-item-image">
          <div class="cart-item-details">
            <h3>{{ item.name }}</h3>
            <div class="cart-item-price-control">
              <span class="item-price">${{ (item.price * item.cartQuantity).toFixed(2) }}</span>
              <div class="quantity-control">
                <button @click="decreaseQuantity(item)" class="quantity-btn">-</button>
                <span class="quantity-display">{{ item.cartQuantity }}</span>
                <button @click="increaseQuantity(item)" :disabled="item.cartQuantity >= item.stockLevel" 
                  class="quantity-btn">+</button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="cart-summary">
        <div class="delivery-option">
          <h3>Delivery Option</h3>
          <select v-model="deliveryMethod" class="delivery-select">
            <option value="pickup">Pick-up (Free)</option>
            <option value="delivery">Delivery (+$5.00)</option>
          </select>
        </div>
        <div class="cart-totals">
          <div class="total-line">
            <span>Subtotal</span>
            <span>${{ subtotal.toFixed(2) }}</span>
          </div>
          <div class="total-line">
            <span>Delivery fee</span>
            <span>{{ deliveryMethod === 'delivery' ? '$5.00' : 'Free' }}</span>
          </div>
          <div class="total-line total-final">
            <span>Total</span>
            <span>${{ total.toFixed(2) }}</span>
          </div>
        </div>
        <form @submit.prevent="checkout">
          <div class="user-form" :class="{ 'loading': isProcessing }">
            <h3>Enter Your Details</h3>
            <input v-model="user.name" type="text" placeholder="Name" required 
              :disabled="isProcessing"/>
            <input v-model="user.email" type="email" placeholder="Email" required 
              :disabled="isProcessing"/>
            <input v-model="user.address" type="text" placeholder="Address" required 
              :disabled="isProcessing"/>
            <input v-model="user.phone" type="tel" required :disabled="isProcessing"/>
            <div class="payment-methods">
              <label>
                <input type="radio" value="cash" v-model="user.paymentMethod" required 
                  :disabled="isProcessing"/>Cash</label>
              <label>
                <input type="radio" value="credit card" v-model="user.paymentMethod" 
                  required :disabled="isProcessing"/>Credit Card</label>
            </div>
          </div>
          <button type="submit" class="checkout-btn" :disabled="isProcessing">
            {{ isProcessing ? 'Processing...' : 'Checkout' }}
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import CartService from '@/services/CartService';
import CustomerData from '@/services/CustomerData';
import InvoiceService from '@/services/InvoiceService';
import OrderService from '@/services/OrderService';

export default {
  name: 'CartPage',
  data() {
    return {
      cartItems: [],
      deliveryMethod: 'pickup',
      isProcessing: false,
      user: {
        name: "",
        email: "",
        address: "",
        phone: "",
        paymentMethod: ""
      }
    };
  },
  computed: {
    subtotal() {
      return this.cartItems.reduce((total, item) => total + item.price * item.cartQuantity, 0);
    },
    total() {
      return this.subtotal + this.calculateDeliveryFee();
    }
  },
  methods: {
    calculateDeliveryFee() {
      return this.deliveryMethod === 'delivery' ? 5 : 0;
    },
    formatDate(date) {
      const d = new Date(date);
      const year = d.getFullYear();
      const month = String(d.getMonth() + 1).padStart(2, '0');
      const day = String(d.getDate()).padStart(2, '0');
      const hours = String(d.getHours()).padStart(2, '0');
      const minutes = String(d.getMinutes()).padStart(2, '0');
      return `${year}-${month}-${day} ${hours}:${minutes}`;
    },
    goBack() {
      this.$router.push('/');
    },
    async fetchCartItems() {
      try {
        this.cartItems = await CartService.fetchCartItemDetails();
      } catch (error) {
        console.error(error);
        this.$toast.error('Something went wrong');
      }
    },
    increaseQuantity(item) {
      if (item.cartQuantity < item.stockLevel) {
        item.cartQuantity++;
        this.updateLocalStorage();
      }
    },
    decreaseQuantity(item) {
      if (item.cartQuantity > 1) {
        item.cartQuantity--;
        this.updateLocalStorage();
      } else {
        this.cartItems = this.cartItems.filter(i => i.id !== item.id);
        this.updateLocalStorage();
      }
    },
    updateLocalStorage() {
      const cartData = this.cartItems.map(item => ({
        id: item.id,
        quantity: item.cartQuantity
      }));
      CartService.updateCart(cartData);
    },
    async checkout() {
      if (this.isProcessing) return;  
      this.isProcessing = true;  
      try {
        const stockValid = await CartService.validateStock(this.cartItems);
        if (!stockValid) {
          throw new Error('STOCK_INVALID');
        }
        const currentDate = this.formatDate(new Date())
        //customer
        const userApiDetails = {
          name: this.user.name,
          address: this.user.address,
          email: this.user.email,
          phone: this.user.phone,
        };
        const { data: newUser } = await CustomerData.createCustomer(userApiDetails);
        await CartService.checkout(this.cartItems);
        //order
        const orderApiDetails = {
          customer: newUser,
          items: [...this.cartItems],
          orderDateTime: currentDate,
          status: "Pending",
          deliveryType: this.deliveryMethod,
          deliveryDateTime: currentDate,
          deliveryFee: this.calculateDeliveryFee()
        };
        const { data: newOrder } = await OrderService.createOrder(orderApiDetails);
        //invoice
        const invoiceApiDetails = {
          order: newOrder,
          invoiceDateTime: currentDate,
          paymentStatus: "Pending",
          paymentMethod: this.user.paymentMethod,
          dueDate: currentDate,
        };
        await InvoiceService.addInvoice(invoiceApiDetails);
        //order summary
        const orderDetails = {
          user: newUser,
          items: [...this.cartItems],
          subtotal: this.subtotal,
          deliveryFee: this.calculateDeliveryFee(),
          total: this.total,
          deliveryMethod: this.deliveryMethod
        };
        await CartService.clearCart();
        await this.$router.push({
          name: 'orderSummary',
          query: { order: JSON.stringify(orderDetails) }
        });
      } catch (error) {
        console.error(error);        
        switch(error.message) {
          case 'STOCK_INVALID':
            await this.fetchCartItems(); //refresh to get stock
            this.$toast.error('Sorry, but these items are out of stock');
            break;
          default:
            this.$toast.error('Sorry, something went wrong');
        }
      } finally {
        this.isProcessing = false;
      }
    }
  },

  mounted() {
    this.fetchCartItems();
  }
};
</script>

<style scoped>
.cart-page {
  max-width: 800px;
  margin: 0 auto;
  padding: 1rem;
  font-family: Arial, sans-serif;
}
.cart-header {
  display: flex;
  align-items: center;
  margin-bottom: 1.5rem;
}

.back-btn {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  margin-right: 1rem;
  color: #007bff;
}

.cart-item {
  display: flex;
  align-items: center;
  border-bottom: 1px solid #e5e7eb;
  padding: 1rem 0;
}

.cart-item-image {
  width: 100px;
  height: 100px;
  object-fit: cover;
  margin-right: 1rem;
  border-radius: 0.25rem;
}

.cart-item-details {
  flex-grow: 1;
}

.cart-item-price-control {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 0.5rem;
}

.quantity-control {
  display: flex;
  align-items: center;
}

.quantity-btn {
  background-color: #e5e7eb;
  padding: 0.25rem 0.75rem;
  border: none;
  cursor: pointer;
  border-radius: 0.25rem;
  transition: background-color 0.2s;
}

.quantity-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.quantity-btn:hover:not(:disabled) {
  background-color: #d1d5db;
}

.quantity-display {
  padding: 0.25rem 0.75rem;
  background-color: #f3f4f6;
  min-width: 2.5rem;
  text-align: center;
}

.user-form {
  margin-bottom: 1.5rem;
}

.user-form.loading {
  opacity: 0.7;
  pointer-events: none;
}

.user-form input,
.user-form select {
  display: block;
  width: 100%;
  padding: 0.75rem;
  margin: 0.5rem 0;
  border: 1px solid #ddd;
  border-radius: 0.25rem;
  transition: border-color 0.2s;
}

.user-form input:focus,
.user-form select:focus {
  border-color: #3b82f6;
  outline: none;
  box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.1);
}

.payment-methods {
  display: flex;
  justify-content: center;
  margin-top: 1rem;
  gap: 1.5rem;
}

.payment-methods label {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  cursor: pointer;
}

.cart-summary {
  background-color: #f9fafb;
  padding: 1.5rem;
  border-radius: 0.5rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.delivery-option {
  margin-bottom: 1.5rem;
}

.delivery-select {
  width: 100%;
  padding: 0.75rem;
  margin-top: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 0.25rem;
  background-color: white;
}

.cart-totals {
  margin-bottom: 1.5rem;
  border-top: 1px solid #e5e7eb;
  padding-top: 1rem;
}

.total-line {
  display: flex;
  justify-content: space-between;
  padding: 0.5rem 0;
}

.total-final {
  font-weight: bold;
  font-size: 1.2rem;
  border-top: 2px solid #e5e7eb;
  margin-top: 0.5rem;
  padding-top: 0.5rem;
}

.checkout-btn {
  width: 100%;
  background-color: #3b82f6;
  color: white;
  padding: 1rem;
  border-radius: 0.25rem;
  border: none;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.2s;
}

.checkout-btn:hover:not(:disabled) {
  background-color: #2563eb;
}

.checkout-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.empty-cart {
  text-align: center;
  padding: 2rem;
  color: #6b7280;
  font-size: 1.2rem;
}
</style>