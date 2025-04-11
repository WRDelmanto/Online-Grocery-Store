<template>
  <div class="item-card">
    <div class="item-image-container">
      <img :src="item.imageURL" :alt="item.name" class="item-image" />
      <div v-if="item.stockLevel === 0" class="out-of-stock-overlay">
        Out of Stock
      </div>
    </div>
    <div class="item-details">
      <h3 class="item-name">{{ item.name }}</h3>
      <p class="item-description">{{ item.description }}</p>
      <div class="item-price-stock">
        <span class="item-price">${{ item.price.toFixed(2) }}</span>
        <span class="item-stock" :class="{ 'out-of-stock': item.stockLevel === 0 }">
          Stock: {{ item.stockLevel }}
        </span>
      </div>
      <div class="cart-action">
        <button v-if="item.stockLevel === 0" class="out-of-stock-btn" disabled>Out of Stock </button>
        <template v-else>
          <template v-if="cartQuantity === 0">
            <button @click="addToCart" class="add-to-cart-btn">Add to Cart</button>
          </template>
          <template v-else>
            <div class="quantity-control">
              <button @click="decreaseQuantity" class="quantity-btn">-</button>
              <span class="quantity-display">{{ cartQuantity }}</span>
              <button @click="increaseQuantity" :disabled="cartQuantity >= item.stockLevel" 
                class="quantity-btn"> + </button>
            </div>
          </template>
        </template>
      </div>
    </div>
  </div>
</template>

  
  <script>
  export default {
    name: 'ItemCard',
    props: {
      item: {
        type: Object,
        required: true,
      },
    },
    data() {
      return {
        cartQuantity: 0,
      };
    },
    mounted() {
      const savedCart = localStorage.getItem('cart');
      let cartData = {};  
      if (savedCart) {
        const items = savedCart.split(',');
        items.forEach(item => {
          const [id, quantity] = item.split(':');
          cartData[id] = parseInt(quantity, 10);
        });
      }
      this.cartQuantity = cartData[this.item.id] || 0;
    },
    methods: {
      addToCart() {
        this.cartQuantity = 1;
        this.updateLocalStorage();
        this.$emit('cart-updated');
 },
      increaseQuantity() {
        if (this.cartQuantity < this.item.stockLevel) {
          this.cartQuantity++;
          this.updateLocalStorage();
          this.$emit('cart-updated');
        }
      },
      decreaseQuantity() {
        if (this.cartQuantity > 0) {
          this.cartQuantity--;
          this.updateLocalStorage();
          this.$emit('cart-updated');
     }
 },
      updateLocalStorage() {
        const savedCart = localStorage.getItem('cart');
        let cartData = {};  
        if (savedCart) {
          const items = savedCart.split(',');
          items.forEach(item => {
            const [id, quantity] = item.split(':');
            cartData[id] = parseInt(quantity, 10);
     });
     }
        if (this.cartQuantity === 0) {
          delete cartData[this.item.id];
        } else {
          cartData[this.item.id] = this.cartQuantity;
     }
        const serializedCart = Object.entries(cartData)
          .map(([id, quantity]) => `${id}:${quantity}`)
          .join(',');
        localStorage.setItem('cart', serializedCart);
      },
    },
  };
  </script>
  <style scoped>
  .item-card {
    background-color: white;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    border-radius: 0.5rem;
    overflow: hidden;
    transition: transform 0.3s ease;
  }
  .item-card:hover {
    transform: scale(1.05);
  }
  .item-image-container {
    position: relative;
  }
  
  .item-image {
    width: 100%;
    height: 12rem;
    object-fit: cover;
  }
  .item-details {
    padding: 1rem;
  }
  .item-name {
    font-size: 1.125rem;
    font-weight: bold;
    margin-bottom: 0.5rem;
  }
  .item-description {
    color: #6b7280;
    margin-bottom: 0.5rem;
    font-size: 0.875rem;
  }
  .item-price-stock {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 1rem;
  }
  
  .item-price {
    font-size: 1.25rem;
    font-weight: 600;
    color: #10b981;
  }
  .item-stock {
    color: #6b7280;
    font-size: 0.875rem;
  }
  
  .cart-action {
    width: 100%;
  }
  
  .add-to-cart-btn {
    width: 100%;
    background-color: #3b82f6;
    color: white;
    padding: 0.5rem;
    border-radius: 0.25rem;
    transition: background-color 0.3s;
  }
  
  .add-to-cart-btn:hover {
    background-color: #2563eb;
  }
  
  .add-to-cart-btn:disabled {
    background-color: #9ca3af;
    cursor: not-allowed;
  }  
  .quantity-control {
    display: flex;
    width: 100%;
  }
  
  .quantity-btn {
    background-color: #e5e7eb;
    padding: 0.5rem 1rem;
  }
  
  .quantity-display {
    background-color: #f3f4f6;
    padding: 0.5rem 1rem;
    flex-grow: 1;
    text-align: center;
  }
.out-of-stock-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.6);
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.5rem;
  font-weight: bold;
}

.out-of-stock-btn {
  width: 100%;
  padding: 0.5rem;
  background-color: #9ca3af;
  color: white;
  border: none;
  border-radius: 0.25rem;
  cursor: not-allowed;
}

.item-stock.out-of-stock {
  color: #ef4444;
  font-weight: bold;
}
  </style>
  