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
    <div ref="section2" style="margin-top: 50px">
      <PostingList v-bind:postingList="postingList" />
    </div>
    <div ref="section2" style="margin-top: 50px">
      <PurchaseList v-bind:purchaseList="purchaseList" />
    </div>
    <div>
      <b-button @click="logOut" pill variant="outline-secondary"
        >Logout</b-button
      >
    </div>
    <Footer/>
  </html>
</template>

<script>
import Taskbar from "./Taskbar";
import PostingList from "./PostingList";
import PurchaseList from "./PurchaseList";
import axios from "axios";
import Footer from "./Footer";
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
      purchaseList: [],
      postingList: [],
      password: "",
      error: "",
      userType: "",
    };
  },
  components: {
    Taskbar,
    PostingList,
    PurchaseList,
    Footer,
  },
  methods: {
    logOut() {
      this.$store.dispatch("setActiveUser", "");
      this.$store.dispatch("setActiveUserType", "");
      this.$router.push({ name: "Home" });
    },
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
          } else if (this.userType == "buyer") {
            this.listType = "My Purchases";
          }
        })
        .catch((e) => {
          this.error = e;
        });
    }
    if (this.userType == "artist") {
      AXIOS.get("/postings/artist/".concat(this.email))
        .then((response) => {
          this.postingList = response.data;
        })
        .catch((e) => {
          this.error = e;
        });
    } else if (this.userType == "buyer") {
      AXIOS.get("/purchases/buyer/".concat(this.email))
        .then((response) => {
          this.purchaseList = response.data;
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
