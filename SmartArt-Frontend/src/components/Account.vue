Account
<template>
  <html lang="en">
    <Taskbar />
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
import Taskbar from "./Taskbar";
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
  name: "Account",
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
  components: {
    Taskbar,
  },
  created: function () {
    this.email = this.$store.getters.getActiveUser;
    this.userType = this.$store.getters.getActiveUserType;
    if (this.email != "") {
      AXIOS.get("/".concat(this.userType).concat("s/").concat(this.email))
        .then((response) => {
          this.name = response.data.name;
          if (this.userType == "artist") {
            this.listType = "My Postings";
            this.postings = response.data.postings;
          } else if (this.userType == "buyer") {
            this.listType = "My Purchases";
            this.purchases = response.data.purchases;
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
