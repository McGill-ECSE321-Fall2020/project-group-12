Order Confirmation
<template>
  <html lang="en">
    <Taskbar></Taskbar>
    <div id="orderConfirmation">
      <h3>Your purchase is complete, {{ this.buyer }}.</h3>
      <h3>Order Confirmation: #{{ this.purchaseID }}</h3>
      <h3>Thank you for supporting local artists!</h3>
      <br>
        <button type="button" class="btn btn-danger btn-lg" @click="toHome">Browse Other Art</button>
      <p>{{ error }}</p>
    </div>
    
  </html>
</template>

<script>
import axios from "axios";
import Taskbar from "./Taskbar";
var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});
export default {
  name: "CreateAccount",
  components: {
    Taskbar,
  },
  data() {
    return {
      email: "",
      name: "",
      password: "",
      confirmPassword: "",
      gallery: "SmartArt",
      userType: "",
      error: "",
    };
  },
  created: function () {
      this.email = this.$store.getters.getActiveUser;
      
  },
  methods: {
      toHome() {
        this.$router.push({name: 'Home'})
      },
  },
};
</script>

<style scoped>
#orderConfirmation {
  padding-top: 20vh;
}
.container-fluid {
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: auto;
  white-space: nowrap;
}
</style>

