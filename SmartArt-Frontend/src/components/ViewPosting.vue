ViewPosting
<template>
  <html lang="en">
    <Taskbar />
    <div id="posting">
      <h1>{{this.title}}</h1>
      <h2>{{this.image}}</h2>
      <div class="info">
        <p class="header">postingID : {{ this.postingID }}</p>
        <p class="header">Artist : {{ this.artist }}</p>
        <p class="header">Gallery : {{ this.gallery }}</p>
        <p class="header">Price : {{ this.price }}$</p>
        <p class="header">Dimensions : {{ this.xDim }}x{{ this.yDim}}x{{ this.zDim }}</p>
        <p class="header">Description : {{ this.description }}</p>
        <p class="header">{{ this.artStatus }}</p>
        <p class="header">Date : {{ this.date }}</p>
      </div>
    </div>
    <div>
    <b-button @click="addToCart" pill variant="outline-secondary"
     style="margin: 2px 2px 2px 2px;">Add to Cart</b-button
    >
    </div>
    <p></p>
  </html>
</template>


<script>
import Taskbar from "./Taskbar";
import axios from "axios";
import PostingList from "./PostingList";
var config = require("../../config");

var frontendUrl = "http://" + config.dev.host + ":" + config.dev.port;
var backendUrl =
  "http://" + config.dev.backendHost + ":" + config.dev.backendPort;

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
});

export default {
  name: 'ViewPosting',
  data () {
    return {
      email: "",
      postingID: "",
      artist: "",
      gallery: "",
      price: "",
      xDim: "",
      yDim: "",
      zDim: "",
      title: "",
      description: "",
      artStatus: "",
      date: "",
      image: "",
    };
  },
  components: {
    Taskbar,
  },
  created: function () {
      this.email = this.$store.getters.getActiveUser;
      this.postingID = 1234;
      this.artist = "Michelangelo";
      this.gallery = "Louvre";
      this.price = "50";
      this.xDim = "5";
      this.yDim = "10";
      this.zDim = "20";
      this.title = "Golden Man";
      this.description = "Man made of gold";
      this.artStatus = "Available";
      this.date = "11/14/2020";
      this.image = "yes";
  },
  methods: {
    addToCart: function () {
       AXIOS({
          method: "post",
          url: "/".concat(this.userType).concat("/").concat("login"),
          data: {
            email: this.email,
            password: this.password,
          },
        })
          .then((response) => {
            this.$store.dispatch("setActiveUser", this.email);
            this.$store.dispatch("setActiveUserType", this.userType);
            this.email = "";
            this.password = "";
            this.error = "";
            this.userType = "";
            this.$router.push({ name: "Home" });
          })
          .catch((e) => {
            var errorMsg = e.message;
            console.log(e);
            this.error = errorMsg;
          });
    },
  },
};
</script>


<style>
.btn {
    border-radius: 0
}
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
