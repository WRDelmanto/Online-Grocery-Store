<template>
  <nav class="navbar">
    <div class="navbar-container">
      <router-link to="/" class="navbar-logo">Douglas Market</router-link>
      <div class="navbar-search">
        <input type="text" v-model="searchQuery" @input="performSearch" placeholder="Search by name / category"
          class="search-input"/>
      </div>
      <div class="navbar-actions">
        <router-link to="/admin" class="admin-login">Admin Login</router-link>
        <router-link to="/cart" class="cart-icon">
          <span class="cart-icon-container">
            <i class="fa fa-shopping-cart"></i>
            <span class="cart-count">{{ cartItemCount }}</span>
          </span>
        </router-link>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: "MainNavbar",
  props: {
    cartUpdateTrigger: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      searchQuery: "",
      cartItemCount: 0,
    };
  },
  methods: {
    performSearch() {
      this.$emit("search", this.searchQuery);
    },
    updateCartCount() {
      const savedCart = localStorage.getItem("cart");
      if (savedCart) {
        const cartData = savedCart.split(",");
        this.cartItemCount = cartData.reduce((total, item) => {
          const [, quantity] = item.split(":");
          return total + parseInt(quantity, 10);
        }, 0);
      } else {
        this.cartItemCount = 0;
      }
    },
  },
  watch: {
    cartUpdateTrigger() {
      this.updateCartCount();
    },
  },
  mounted() {
    this.updateCartCount();
  },
};
</script>


<style scoped>
.navbar {
  background-color: #f8f9fa;
  padding: 1rem;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.navbar-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto;
}

.navbar-logo {
  font-size: 1.5rem;
  font-weight: bold;
  text-decoration: none;
  color: #333;
}
.navbar-search {
  flex-grow: 1;
  max-width: 400px;
  margin: 0 2rem;
}

.search-input {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.navbar-actions {
  display: flex;
  align-items: center;
}

.admin-login {
  margin-right: 1rem;
  text-decoration: none;
  color: #333;
  padding: 0.5rem 1rem;
  border: 1px solid #333;
  border-radius: 4px;
}
.cart-icon {
  position: relative;
  color: #333;
  text-decoration: none;
}

.cart-icon-container {
  display: inline-block;
  position: relative;
}
.cart-count {
  position: absolute;
  top: -8px;
  right: -8px;
  background-color: red;
  color: white;
  border-radius: 50%;
  padding: 2px 6px;
  font-size: 0.7rem;
}
</style>
