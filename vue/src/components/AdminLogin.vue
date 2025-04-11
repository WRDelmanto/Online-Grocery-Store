<template>
    <div class="dashboard-container">
        <h4 class="dashboard-title">Login</h4>
        <form class="login-form">
            <div class="form-group">
                <label for="adminId" class="form-label">Username: </label>
                <!-- When click the button, information in the input will be saved in v-model= -->
                <input type="text" id="adminId" class="form-input" v-model="adminLoginRequest.adminId" />
            </div>
            <div class="form-group">
                <label for="password" class="form-label">Password: </label>
                <input type="password" id="password" class="form-input" v-model="adminLoginRequest.password" />
            </div>
        </form>
        <div class="button-container">
            <!-- When click the button, it will call the login function from methods below-->
            <button type="submit" class="submit-button" @click="login">Submit</button>
        </div>
        <p class="message">{{ message }}</p>
    </div>
</template>

<script>
import LoginService from "../services/LoginService";

export default {
    name: "adminLogin",
    data () { // Define all data we will use in the components
        return { // It will return the objects, the data we will use in the components
            adminLoginRequest: {adminId: "", password: ""}, // from the input label above
            message: ""
        }
    },

    methods: {
        login(event) {
            event.preventDefault();
            if(this.adminLoginRequest.adminId.trim() === "" || this.adminLoginRequest.password.trim() === ""){
                this.message = "Missing Credentials"
            }
            else{
                LoginService.login(this.adminLoginRequest.adminId, this.adminLoginRequest.password) // Call the method
                    .then(response => { // here will be save the response from the API
                        let isValid = response.data;
                        console.log(isValid); // To see the response from the API in the console
                        if (isValid) {
                            this.message = "Successful Access";
                            this.$router.push({ name: "adminDashboard" }); // Redirect to adminDashboard component
                        } else {
                            this.message = "Invalid Credentials";
                        }
                    })
                    .catch(error => {
                        this.adminLoginRequest.adminId = "";
                        this.adminLoginRequest.password = "";
                        this.error.response.data.message;
                        console.log(error.response.data);
                        console.log(this.isValid);  // To see the response from the API in the console
                    });
            }
        },

        handleKeyUp(event) {
            if(event.key === "Enter") { //Validate if the key pressed is "Enter"
                this.login(event);
            }
        }
    },

    mounted() {
        this.message = "";
        window.addEventListener('keyup', this.handleKeyUp); // Add listener for "keyup" (When key is released) 
    },

    beforeUnmount() {
        window.removeEventListener("keyup", this.handleKeyUp); // Remove listener for "keyup" (When change route) 
    }
}

</script>

<style>

.dashboard-container {
    max-width: 400px;
    margin: 50px auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 8px;
    background-color: #f9f9f9;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    font-family: Arial, sans-serif;
}

.dashboard-title {
    text-align: center;
    font-size: 1.5em;
    color: #333;
    margin-bottom: 20px;
}

.login-form {
    display: flex;
    flex-direction: column;
    gap: 15px;
}

.form-group {
    display: flex;
    flex-direction: column;
}

.form-label {
    font-size: 0.9em;
    color: #555;
    margin-bottom: 5px;
}

.form-input {
    padding: 10px;
    font-size: 1em;
    border: 1px solid #ccc;
    border-radius: 5px;
    outline: none;
    transition: border-color 0.3s ease;
}

.form-input:focus {
    border-color: #007bff;
}

.button-container {
    text-align: center;
    margin-top: 20px;
}

.submit-button {
    padding: 10px 20px;
    font-size: 1em;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.submit-button:hover {
    background-color: #0056b3;
}

.message {
    margin-top: 15px;
    text-align: center;
    font-size: 0.9em;
    color: #666;
}

</style>