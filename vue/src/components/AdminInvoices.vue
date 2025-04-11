<template>
    <div>
        <div class="button-back-container">
            <router-link to="/admin-dashboard" class="back-button">Back</router-link>
        </div>
        <h1>Admin Invoices Dashboard</h1>
        <div>
            <label>Filter Invoice Payment Status: </label>
            <select className="statusDropMenu" v-model="menuPaymentStatusSelection"> <!-- Store value in menuPaymentStatusSelection -->
                <option value="All">All</option>
                <option value="Paid">Paid</option>
                <option value="Unpaid">Unpaid</option>
            </select>
            <button type="submit" @click="filterPaymentStatus">Filter</button>
        </div>
        <div>
            <label>Filter Invoice Payment Method: </label>
            <select className="methodDropMenu" v-model="menuPaymentMethodSelection"> <!-- Store value in menuPaymentMethodSelection -->
                <option value="All">All</option>
                <option value="Credit Card">Credit Card</option>
                <option value="Cash">Cash</option>
            </select>
            <button type="submit" @click="filterPaymentMethod">Filter</button>
        </div>
        <br />
        <table v-if="invoices.length" class="invoice-table"> 
            <thead>
                <tr>
                    <th>Invoice ID</th>
                    <th>Order ID</th>
                    <th>Customer ID</th>
                    <th>Customer Name</th>
                    <th>Invoice Date Time</th>
                    <th>Total Amount</th>
                    <th>Payment Status</th>
                    <th>Payment Method</th>
                    <th>Due Date</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(inv, index) in invoices" :key="index"> <!-- Iterate over invoices creating a row for each item -->
                    <td>{{ inv.id }}</td> <!-- inv is the current item in the invoices array -->
                    <td>{{ inv.order.id }}</td>
                    <td>{{ inv.order.customer.id }}</td>
                    <td>{{ inv.order.customer.name }}</td>
                    <td>{{ inv.invoiceDateTime }}</td>
                    <td>$ {{ calculateTotalPrice(inv.order.items) }}</td> <!-- Pass array of items to this function -->
                    <td>{{ inv.paymentStatus }}</td>
                    <td>{{ inv.paymentMethod }}</td>
                    <td>{{ inv.dueDate }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import AdminInvoices from "../services/AdminInvoices"

export default {
    name: "adminInvoices",
    data () {
        return { 
            invoices: [],
            menuPaymentStatusSelection: "All",
            menuPaymentMethodSelection: "All",
        }
    },

    methods: {
        
        // Method to filter invoices by Payment Status

        filterPaymentStatus(event){
            event.preventDefault();
            this.menuPaymentMethodSelection = "All";
            if(this.menuPaymentStatusSelection === "All") {
                AdminInvoices.getAllInvoices()
                .then(response => {
                    this.invoices = response.data;
                    console.log(this.invoices);
                })
                .catch(error => {
                    console.log(error);
                });
            } else {
                AdminInvoices.getInvoicesByPaymentStatus(this.menuPaymentStatusSelection)
                .then(response => {
                    this.invoices = response.data;
                    console.log(this.invoices);
                })
                .catch(error => {
                    console.log(error);
                });
            }
        },

        // Method to filter invoices by Payment Method

        filterPaymentMethod(event){
            event.preventDefault();
            this.menuPaymentStatusSelection = "All";
            if(this.menuPaymentMethodSelection === "All") {
                AdminInvoices.getAllInvoices()
                .then(response => {
                    this.invoices = response.data;
                    console.log(this.invoices);
                })
                .catch(error => {
                    console.log(error);
                });
            } else {
                AdminInvoices.getInvoicesByPaymentMethod(this.menuPaymentMethodSelection)
                .then(response => {
                    this.invoices = response.data;
                    console.log(this.invoices);
                })
                .catch(error => {
                    console.log(error);
                });
            }
        },


        // Method to calculate the total amount of an invoice

        calculateTotalPrice(items) { // Receive array of items
            let total = 0;
            for(let element of items) { // Iterate every element in items array
                total += element.price;
            }
            return total.toFixed(2);
        }

    },


    mounted() {
        AdminInvoices.getAllInvoices()
                .then(response => {
                    this.invoices = response.data;
                    console.log(this.invoices);
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

.invoice-table {
    width: 100%;
    border-collapse: collapse;
    margin: 20px 0;
    font-size: 16px;
    text-align: left;
}

.invoice-table th,
.invoice-table td {
    border: 1px solid #ddd;
    padding: 8px;
}

.invoice-table th {
    background-color: #f4f4f4;
    color: #333;
    font-weight: bold;
}

.invoice-table tr:nth-child(even) {
    background-color: #f9f9f9;
}

.invoice-table tr:hover {
    background-color: #f1f1f1;
}

.invoice-table td {
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