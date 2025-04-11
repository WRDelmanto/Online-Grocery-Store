import http from "../http-common.js"; // http here is the axios object

class LoginService {
    login(adminLogin, adminPassword) {
        return http.get(`/admin/${adminLogin}/${adminPassword}`); // Create the GET request
    }
}

export default new LoginService();