ViewPosting
<template>
  <html lang="en">
    <Taskbar />
    <div>
      <p class="title">{{ this.title }}</p>
    </div>
    <div id="posting">
      <img v-bind:src="this.image" />
      <div class="info">
        <p class="header">Posting ID : {{ this.postingID }}</p>
        <p class="header">Artist : {{ this.artistName }}</p>
        <p class="header">Price : ${{ this.price }}</p>
        <p class="header">
          Dimensions : {{ this.xDim }}x{{ this.yDim }}x{{ this.zDim }}cm
        </p>
        <p class="header">Description : {{ this.description }}</p>
        <p class="header">Availability : {{ this.artStatus }}</p>
        <p class="header">Date : {{ this.date }}</p>
      </div>
    </div>
    <div>
      <b-button
        @click="addToCart"
        pill
        variant="outline-secondary"
        style="margin: 2px 2px 2px 2px"
        >Add to Cart</b-button
      >
      <p>{{ this.message }}</p>
    </div>
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
  name: "ViewPosting",
  data() {
    return {
      email: "",
      postingID: 0,
      artist: "",
      gallery: "SmartArt",
      artistName: "",
      price: "",
      xDim: "",
      yDim: "",
      zDim: "",
      title: "",
      description: "",
      artStatus: "",
      date: "",
      image: "",
      message: "",
      userType: ""
    };
  },
  components: {
    Taskbar,
  },
  created: function () {
    this.email = this.$store.getters.getActiveUser;
    this.postingID = this.$store.getters.getActivePosting;
    this.userType = this.$store.getters.getActiveUserType;
    if(this.userType == 'buyer'){
          AXIOS.get("/buyers/".concat(this.email))
      .then((response) => {
      })
      .catch((e) => {
        this.message =
          "You must be logged in as a buyer to add items to your cart";
      });
    }
    if (this.postingID != 0) {
      AXIOS.get("/postings/".concat(this.postingID))
        .then((response) => {
          this.artistName = response.data.artist.name;
          this.artist = response.data.artist;
          this.title = response.data.title;
          this.price = response.data.price;
          this.xDim = response.data.xdim;
          this.yDim = response.data.ydim;
          this.zDim = response.data.zdim;
          this.description = response.data.description;
          this.artStatus = response.data.artStatus;
          this.date = response.data.date;
          this.image = response.data.image;
        })
        .catch((e) => {
          this.message = e;
        });
    }
  },
  methods: {
    addToCart: function () {
      if(this.email != ''){
              AXIOS({
        method: "post",
        url: "/purchase/cart/add/".concat(this.postingID),
        data: {
            email: this.email,
            gallery: this.gallery
        },
      })
        .then((response) => {
          this.$router.push({ name: "Home" });
        })
        .catch((e) => {
          var errorMsg = e.message;
          console.log(e);
          this.message = errorMsg;
        });
      }else {
        this.message = "You must be logged in as a buyer to add items to your cart";
      }
    },
  },
};
</script>


<style>
.btn {
  border-radius: 0;
}
.info {
  position: relative;
  text-align: center;
  align-self: center;
  padding: 20px 200px;
}
.title {
  font-size: 40pt;
}
.header {
  font-size: 22pt;
}
</style>
