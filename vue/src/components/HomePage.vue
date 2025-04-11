<template>
    <div class="home-container">
      <MainNavbar :cartUpdateTrigger="cartQuantity" @search="handleSearch" />
      <h1 class="page-title">Welcome</h1>
      <div v-if="error" class="error-message">
        {{ error }}
      </div>
      <div class="items-grid">
        <ItemCard v-for="item in filteredItems" :key="item.id" :item="item" @cart-updated="updateCartQuantity" />
      </div>
    </div>
  </template>
  
  <script>
  import ItemService from '../services/ItemService';
  import ItemCard from './ItemCard.vue';
  import MainNavbar from './MainNavbar.vue';
  
  export default {
    name: 'HomePage',
    components: {
      ItemCard,
      MainNavbar,
    },
    data() {
      return {
        items: [],
        error: null,
        searchQuery: '',
        cartQuantity: 0,
      };
    },
    computed: {
      filteredItems() {
        if (!this.searchQuery) return this.items;
        const query = this.searchQuery.toLowerCase();
        return this.items.filter(item =>
          item.name.toLowerCase().includes(query) ||
          item.category.toLowerCase().includes(query)
        );
      },
    },
    methods: {
      handleSearch(query) {
        this.searchQuery = query;
      },
      fetchItems() {
        ItemService.getAllItems()
          .then(response => {
            this.items = response.data;
          })
          .catch(error => {
            console.error(error);
          });
      },
      updateCartQuantity() {
        const savedCart = localStorage.getItem('cart');
        const cartData = savedCart
          ? savedCart.split(',').reduce((cart, item) => {
              const [id, quantity] = item.split(':');
              cart[id] = parseInt(quantity, 10);
              return cart;
            }, {})
          : {};
  
        this.cartQuantity = Object.values(cartData).reduce((sum, quantity) => sum + quantity, 0);
      },
    },
    mounted() {
      this.fetchItems();
      this.updateCartQuantity();
    },
  };
  </script>
  
  <style scoped>
  .home-container {
    padding: 2rem;
  }
   .page-title {
    text-align: center;
     font-size: 2rem;
    font-weight: bold;
    margin-bottom: 1.5rem;
  }
  
  .error-message {
    color: red;
    text-align: center;
  }
  
  .items-grid {
    display: grid;
    grid-template-columns: repeat(1, 1fr);
    gap: 1.5rem;
  }
  
  @media (min-width: 640px) {
    .items-grid {
      grid-template-columns: repeat(2, 1fr);
    }
  }
  
  @media (min-width: 768px) {
    .items-grid {
      grid-template-columns: repeat(3, 1fr);
    }
  }
  
  @media (min-width: 1024px) {
    .items-grid {
      grid-template-columns: repeat(4, 1fr);
    }
  }
  </style>
  