Create account 
<template>
  <html lang="en">
    <nav class="navbar navbar-light navbar-expand-md">
      <div class="container-fluid">
        <a class="navbar-brand" href="#"
          ><img id="Logo" src="../assets/SmartArt.png" /></a
        ><button
          data-toggle="collapse"
          class="navbar-toggler"
          data-target="#navcol-1"
        >
          <span class="sr-only">Toggle navigation</span
          ><span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navcol-1">
          <ul class="nav navbar-nav ml-auto">
            <li class="nav-item">
              <a class="nav-link active" href="#">Artists</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Postings</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Sell Art</a>
            </li>
            <li class="nav-item"><a class="nav-link" href="#">Admin</a></li>
            <form class="form-inline">
              <input
                class="form-control mr-sm-2"
                type="search"
                placeholder="Search"
                aria-label="Search"
              />
              <button class="btn btn-danger" type="submit">Search</button>
            </form>
            <li class="nav-item">
              <a href="#!" class="nav-link navbar-link-2 waves-effect">
                <span class="badge badge-pill red">Cart</span>
                <i class="fas fa-shopping-cart pl-0"></i>
              </a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <div id="createAccount">
      <h3>Create your account</h3>
      <div class="container-fluid">
        <div class="input">
          <div class="inputbox">
            <input
              type="text"
              class="form-control input-style"
              v-model="name"
              placeholder="Full Name"
            />
          </div>
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
          <div class="inputbox">
            <input
              type="password"
              class="form-control input-style"
              v-model="confirmPassword"
              placeholder="Confirm Password"
            />
          </div>
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
      <b-button @click="createAcc" pill variant="outline-secondary"
        >Create Account</b-button
      >
      <b-button @click="toLogin" pill variant="outline-secondary"
        >Login</b-button
      >
      <p>{{ error }}</p>
    </div>
  </html>

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
export default {
  name: "CreateAccount",
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
  methods: {
    createAcc: function () {
      if (this.password != this.confirmPassword) {
        this.error = "Your passwords do not match";
      } else if (this.userType == "") {
        this.error = "Please select a user type";
      } else {
        AXIOS({

          method: "post",
          url: "/".concat(this.userType).concat("/").concat("create"),
          data: {
            email: this.email,
            name: this.name,
            password: this.password,
            gallery: this.gallery,
          },
        })
          .then((response) => {
            this.$store.dispatch("setActiveUser", this.email);
            this.$store.dispatch("setActiveUserType", this.userType);

            this.email = "";
            this.name = "";
            this.password = "";
            this.confirmPassword = "";
            this.userType = "";
            this.error = "";

            this.$router.push({ name: "Home" });

          })
          .catch((e) => {
            var errorMsg = e.message;
            console.log(e);
            this.error = errorMsg;
          });
      }
    },

    toLogin: function () {
      this.$router.push({ name: "Login" });
    },
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
.container-fluid {
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: auto;
  white-space: nowrap;
}
</style>

