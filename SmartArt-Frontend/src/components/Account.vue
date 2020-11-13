Account
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
            <li class="nav-item"><a class="nav-link" href="#">Account</a></li>
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
    <div id="account">
      <h2>My Account</h2>
      <div class="info">
        <p class="header">Name : {{ this.name }}</p>
        <p class="header">Email : {{ this.email }}</p>
        <p class="header">User Type : {{ this.userType }}</p>
      </div>
      <p class="listHeader">{{ this.listType }}</p>
    </div>
  </html>
</template>

<script>
import { mapGetters } from "vuex";
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
  name: "Login",
  data() {
    return {
      email: "",
      listType: "",
      name: "",
      purchases: [],
      postings: [],
      password: "",
      error: "",
      userType: "",
    };
  },
  created: function () {
    this.email = this.$store.getters.getActiveUser;
    this.userType = this.$store.getters.getActiveUserType;
    if (this.email != "") {
      AXIOS.get("/".concat(this.userType).concat("s/").concat(this.email))
        .then((response) => {
          this.name = response.data.name;
          if(this.userType == 'artist'){
              this.listType = "My Postings"
              this.postings = response.data.postings
          }else if (this.userType == 'buyer'){
              this.listType = "My Purchases"
              this.purchases = response.data.purchases
          }
        })
        .catch((e) => {
          this.error = e;
        });
    }
  },
};
</script>

<style scoped>
.info {
  position: relative;
  text-align: left;
  align-self: left;
  padding: 20px 200px;
}

.header {
  font-size: 20pt;
}
.listHeader {
  font-size: 22pt;
}
</style>
