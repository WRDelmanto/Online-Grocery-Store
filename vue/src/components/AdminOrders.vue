<template>
    <div>
        <div class="button-back-container">
            <router-link to="/admin-dashboard" class="back-button">Back</router-link>
        </div>
        <h1>Admin Orders Dashboard</h1>
        <div>
            <label>Filter Order Status: </label>
            <select className="statusDropMenu" v-model="menuStatusSelection"> <!-- Store value in menuStatusSelection -->
                <option value="All">All</option>
                <option value="Completed">Completed</option>
                <option value="Pending">Pending</option>
            </select>
            <button type="submit" @click="filterStatus">Filter</button>
        </div>
        <div>
            <label>Filter Order Delivery Type: </label>
            <select className="deliveryTypeDropMenu" v-model="menuDeliveryTypeSelection"> <!-- Store value in menuDeliveryTypeSelection -->
                <option value="All">All</option>
                <option value="Delivery">Delivery</option>
                <option value="Pickup">Pickup</option>
            </select>
            <button type="submit" @click="filterDeliveryType">Filter</button>
        </div>
        <br />
        <table v-if="orders.length" class="order-table"> 
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Customer ID</th>
                    <th>Customer Name</th>
                    <th>Order Date Time</th>
                    <th>Order Status</th>
                    <th>Delivery Type</th>
                    <th>Delivery Date Time</th>
                    <th>Delivery Fee</th>
                    <th>Update Status</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="ord in orders" :key="ord.id"> <!-- Iterate over orders creating a row for each item -->
                    <td>{{ ord.id }}</td> <!-- ord is the current item in the orders array -->
                    <td>{{ ord.customer.id }}</td>
                    <td>{{ ord.customer.name }}</td>
                    <td>{{ ord.orderDateTime }}</td>
                    <td>{{ ord.status }}</td>
                    <td>{{ ord.deliveryType }}</td>
                    <td>{{ ord.deliveryDateTime }}</td>
                    <td>$ {{ ord.deliveryFee.toFixed(2) }}</td>
                    <td>
                        <select className="statusDropMenu" v-model="ord.updatedStatus"> <!-- Store value in ord.updatedStatus -->
                            <option value="Completed">Completed</option>
                            <option value="Pending">Pending</option>
                        </select>
                    </td>
                    <td>
                        <button type="submit" @click="update(ord.id, ord.updatedStatus)">Update</button> <!-- Send ord.id and ord.updatedStatus -->
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

</template>

<script>
import AdminOrders from "../services/AdminOrders"

export default {
    name: "adminOrders",
    data () {
        return { 
            orders: [],
            menuStatusSelection: "All",
            menuDeliveryTypeSelection: "All",
            orderToUpdate: null
        }
    },

    methods: {

        // Method to filter orders by status

        filterStatus(event){
            event.preventDefault();
            this.menuDeliveryTypeSelection = "All";
            if(this.menuStatusSelection === "All") {
                AdminOrders.getAllOrders()
                .then(response => {
                    this.orders = response.data;
                    console.log(this.orders);
                })
                .catch(error => {
                    console.log(error);
                });
            } else {
                AdminOrders.getOrdersByStatus(this.menuStatusSelection)
                .then(response => {
                    this.orders = response.data;
                    console.log(this.orders);
                })
                .catch(error => {
                    console.log(error);
                });
            }
        },


        // Method to filter orders by Delivery Type

        filterDeliveryType(event){
            event.preventDefault();
            this.menuStatusSelection = "All";
            if(this.menuDeliveryTypeSelection === "All") {
                AdminOrders.getAllOrders()
                .then(response => {
                    this.orders = response.data;
                    console.log(this.orders);
                })
                .catch(error => {
                    console.log(error);
                });
            } else {
                AdminOrders.getOrdersByDeliveryType(this.menuDeliveryTypeSelection)
                .then(response => {
                    this.orders = response.data;
                    console.log(this.orders);
                })
                .catch(error => {
                    console.log(error);
                });
            }
        },



        // Update status of an order

        update(orderId, updatedStatus) {
            const orderUpdate = this.orders.find(order => order.id === orderId);
            if (orderUpdate) {
                
                orderUpdate.status = updatedStatus; // update status order

                AdminOrders.updateOrder(orderId, orderUpdate) // Call method to put order
                    .then(response => {
                        console.log("Order updated successfully", response.data);
                    })
                    .catch(error => {
                        console.log("Error updating order", error);
                    });
            }
        }


    },

    mounted() {

        AdminOrders.getAllOrders()
                .then(response => {
                    this.orders = response.data;
                    console.log(this.orders);
                })
                .catch(error => {
                    console.log(error);
                });

    }
}

</script>

<style>

body {
    font-family: Arial, sans-serif;
}

h1 {
    text-align: center;
    margin-bottom: 20px;
    color: #333;
}

.order-table {
    width: 100%;
    border-collapse: collapse;
    margin: 20px 0;
    font-size: 16px;
    text-align: left;
}

.order-table th,
.order-table td {
    border: 1px solid #ddd;
    padding: 8px;
}

.order-table th {
    background-color: #f4f4f4;
    color: #333;
    font-weight: bold;
}

.order-table tr:nth-child(even) {
    background-color: #f9f9f9;
}

.order-table tr:hover {
    background-color: #f1f1f1;
}

.order-table td {
    vertical-align: middle;
}

.button-back-container {
    position: absolute;
    top: 20px;
    left: 20px;
}

.back-button {
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

.back-button:hover {
    background-color: #007bff;
    color: white;
}

.back-button:focus{
    outline: none;
    box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}

</style>