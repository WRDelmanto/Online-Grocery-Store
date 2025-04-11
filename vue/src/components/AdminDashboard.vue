<template>
    <div class="dashboard-container">
        <div class="logout-link">
            <router-link to="/admin" class="logout-button">Logout</router-link>
        </div>
        <h1 class="dashboard-title">Dashboard</h1>
        <div class="button-container">
            <!-- When click the button, it will call the adminOrders function from methods below-->
            <button type="submit" class="dashboard-button" @click="adminOrders">Orders Dashboard</button>
        </div>
        <br />
        <br />
        <div class="button-container">
            <!-- When click the button, it will call the adminInvoices function from methods below-->
            <button type="submit" class="dashboard-button" @click="adminInvoices">Invoices Dashboard</button>
        </div>
    </div>
</template>

<script>
export default {
    name: "adminDashboard",
    data () {
        return { 

        }
    },

    methods: {
        adminOrders(event) {
            event.preventDefault();
            this.$router.push({ name: "adminOrders" }); // Redirect to adminOrders component
        },

        adminInvoices(event) {
            event.preventDefault();
            this.$router.push({ name: "adminInvoices" }); // Redirect to adminInvoices component
        }
    },

    mounted() {
        
    }
}
</script>

<style>

.dashboard-container {
    max-width: 500px;
    margin: 50px auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
    background-color: #f9f9f9;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    font-family: Arial, sans-serif;
}

.logout-link {
    margin-bottom: 20px;
}

.logout-button {
    display: inline-block;
    padding: 8px 16px;
    font-size: 1em;
    background-color: #f1f1f1;
    color: #007bff;
    border: 2px solid #007bff;
    border-radius: 5px;
    text-decoration: none;
    cursor: pointer;
    transition: background-color 0.3s ease, color 0.3s ease;
}

.logout-button:hover {
    background-color: #007bff;
    color: white;
}

.logout-button:focus {
    outline: none;
    box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}

.dashboard-title {
    text-align: center;
    font-size: 2em;
    color: #333;
    margin-bottom: 20px;
}

.button-container {
    text-align: center;
    margin-top: 20px;
}

.dashboard-button {
    padding: 12px 25px;
    font-size: 1.1em;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
    width: 100%;
    max-width: 350px;
}

.dashboard-button:hover {
    background-color: #0056b3;
}

</style>