<template>
    <div class="order-summary-container">
      <h1 class="title">Order Summary</h1>
      <div class="order-details">
        <h3 class="order-date-time">{{ orderDate }}</h3>
        <ul class="order-items" v-if="orderDetails && orderDetails.items && orderDetails.items.length > 0">
          <li v-for="item in orderDetails.items" :key="item.id" class="order-item">
            <span class="item-name">{{ item.name }}</span>
            <span class="item-quantity">Qty: {{ item.cartQuantity }}</span>
            <span class="item-total">Total: ${{ (item.price * item.cartQuantity).toFixed(2) }}</span>
          </li>
        </ul>
        <div v-if="orderDetails && orderDetails.items && orderDetails.items.length > 0" class="order-summary">
          <div class="summary-line">
            <span class="summary-label">Subtotal</span>
            <span class="summary-value">${{ subtotal.toFixed(2) }}</span>
          </div>
          <div class="summary-line">
            <span class="summary-label">Delivery</span>
            <span class="summary-value">{{ orderDetails.deliveryFee === 0 ? 'Free' : '$5.00' }}</span>
          </div>
          <div class="summary-line total">
            <span class="summary-label total-label">Total</span>
            <span class="summary-value total-value">${{ total.toFixed(2) }}</span>
          </div>
        </div>
      </div>
        <h2 class="thank-you">Thank you for your order!</h2>
      <button class="home-btn" @click="goToHome">Back to Home</button>
    </div>
  </template>
  
  <script>
  export default {
    name: 'OrderSummary',
    data() {
      return {
        orderDetails: null,  
        orderDate: "",  
      };
    },
    computed: {
      subtotal() {
        return this.orderDetails.items.reduce((total, item) => total + item.price * item.cartQuantity, 0);
      },
      total() {
        return this.subtotal + (this.orderDetails.deliveryFee === 0 ? 0 : 5); 
      }
    },
    methods: {
      goToHome() {
        this.$router.push({ name: 'home' });
      },
      formatDate(date) {
        return new Date(date).toLocaleString();
      }
    },
    mounted() {
      const orderData = this.$route.query.order;
      if (orderData) {
        this.orderDetails = JSON.parse(orderData);
        this.orderDate = this.formatDate(new Date());
      } else {
        console.log('No data');
      }
    }
  };
  </script>
  
  <style scoped>
  .order-summary-container {
    padding: 2rem;
    max-width: 800px;
    margin: 0 auto;
    background-color: #f9fafb;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    text-align: center;
  } 
  .title {
    font-size: 2rem;
    font-weight: bold;
    color: #333;
    margin-bottom: 1rem;
  }
  
  .order-details {
    margin-bottom: 2rem;
  }
  
  .order-date-time {
    font-size: 1.1rem;
    color: #555;
    margin-bottom: 1rem;
  }
  
  .order-items {
    list-style: none;
    padding: 0;
    margin-bottom: 1rem;
  }
  
  .order-item {
    display: flex;
    justify-content: space-between;
    padding: 0.5rem 0;
    border-bottom: 1px solid #e5e7eb;
  }
  
  .item-name {
    font-weight: bold;
  }
  
  .item-quantity, .item-total {
    font-size: 1rem;
    color: #666;
  }
  
  .order-summary {
    margin-top: 1rem;
    padding: 1rem;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  } 
  .summary-line {
    display: flex;
    justify-content: space-between;
    margin-bottom: 0.75rem;
  }
  .summary-label {
    font-weight: bold;
    font-size: 1.1rem;
    color: #333;
  }
  
  .summary-value {
    font-size: 1.1rem;
    color: #333;
  }
  .total {
    margin-top: 1rem;
    font-weight: bold;
  }
  
  .total-label {
    font-size: 1.25rem;
    color: #333;
  }
  
  .total-value {
    font-size: 1.25rem;
    color: #10b981;
  }
  
  .thank-you {
    font-size: 1.5rem;
    font-weight: bold;
    color: #10b981;
    margin-bottom: 2rem;
  }
  .home-btn {
    padding: 0.75rem 1.5rem;
    background-color: #3b82f6;
    color: white;
    border: none;
    border-radius: 0.5rem;
    cursor: pointer;
    font-size: 1.1rem;
  } 
  .home-btn:hover {
    background-color: #2563eb;
  }
  </style>
  