<template>
  <div id="loginPage">
    <h3>Welcome</h3>
    <div class="input">
      <div class="inputbox">
        <input
          type="email"
          class="form-control input-style"
          v-model="email"
          placeholder="Email"
        />
      </div>
      <div class="inputbox">
        <input
          type="password"
          class="form-control input-style"
          v-model="password"
          placeholder="Password"
        />
      </div>
    </div>
    <div>
      <b-dropdown id="dropdown-1" text="Select your user type" class="m-md-2">
        <b-dropdown-item-btn @click="setBuyer">Buyer</b-dropdown-item-btn>
        <b-dropdown-item-btn @click="setArtist">Artist</b-dropdown-item-btn>
        <b-dropdown-item-btn @click="setAdmin"
          >Administrator</b-dropdown-item-btn
        >
      </b-dropdown>
    </div>
    <b-button @click="tryLogin" pill variant="outline-secondary"
      >Login</b-button
    >
    <b-button @click="toCreate" pill variant="outline-secondary"
      >Create Account</b-button
    >
    <p>{{ error }}</p>
  </div>
</template>

<script>
import axios from "axios";
var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});
function LoginDto (email, password){
  this.e = email
  this.p = password
}
export default {
  name: "Login",
  data() {
    return {
      email: '',
      password: '',
      error: '',
      userType: '',
      types: ["email", "password"],
    };
  },
  methods: {
    tryLogin: function () {
      AXIOS.get("/".concat(this.userType).concat("/").concat("login"), {"email": this.email, "password": this.password})
      .then(response => {
        this.email=''
        this.password=''
        this.error=''
      })
      .catch(e => {
        var errorMsg = e.message;
        console.log(errorMsg);
        this.error = errorMsg;
      })
    },
    toCreate: function () {},
    setBuyer: function () {
      this.userType = "buyer";
    },
    setArtist: function () {
      this.userType = "artist";
    },
    setAdmin: function () {
      this.userType = "administrator";
    },
  },
};
</script>

<style scoped>
.input {
  top: 20px;
  padding: 30px 30px;
  width: 450px;
}
.inputbox {
  position: relative;
  text-align: center;
  align-self: center;
  padding: 5px 5px;
}
</style>

