import { createWebHistory, createRouter } from "vue-router"
import AdminLogin from "../components/AdminLogin.vue"
import AdminDashboard from "../components/AdminDashboard.vue"
import AdminOrders from "../components/AdminOrders.vue"
import AdminInvoices from "../components/AdminInvoices.vue"
import HomePage from "../components/HomePage.vue"
import CartPage from "../components/CartPage.vue"
import OrderSummary from "../components/OrderSummary.vue";

const routes = [
  {
    path: "/",
    name: "home",
    component: HomePage
  },
  {
    path: "/cart",
    name: "cart",
    component: CartPage
  },
  {
    path: "/order-summary",
    name: "orderSummary",
    component: OrderSummary,
    
  },
  {
    path: "/admin",
    alias: "/admin",
    name: "adminLogin",
    component: AdminLogin
  },
  {
    path: "/admin-dashboard",
    name: "adminDashboard",
    component: AdminDashboard
  },
  {
    path: "/admin-orders",
    name: "adminOrders",
    component: AdminOrders
  },
  {
    path: "/admin-invoices",
    name: "adminInvoices",
    component: AdminInvoices
  }
];

export default createRouter({
  history: createWebHistory(),
  routes
});
