View Purchase
<template>
  <html lang="en">
    <Taskbar />
    <div id="account">
      <h2>Purchase: {{ this.purchaseID }}</h2>
      <div class="info">
        <p class="header">Total Price : ${{ this.totalPrice}}</p>
        <p class="header">Purchased at : {{ this.time }}</p>
      </div>
      <p class="listHeader">Postings</p>
    </div>
    <div ref="section2" style="margin-top: 50px">
      <PostingList v-bind:postingList="postingList" />
    </div>
  </html>
</template>

<script>
import Taskbar from "./Taskbar";
import PostingList from "./PostingList";
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
  name: "ViewPurchase",
  data() {
    return {
      purchaseID: null,
      totalPrice: null,
      time: "",
      postingList: [],
      error: "",
    };
  },
  components: {
    Taskbar,
    PostingList,
  },
  created: function () {
    this.purchaseID = this.$store.getters.getActivePurchase;
      AXIOS.get("/purchases/".concat(this.purchaseID))
        .then((response) => {
          this.totalPrice = response.data.totalPrice;
          this.postingList = response.data.postings;
          this.time = response.data.time;
        })
        .catch((e) => {
          this.error = e;
        });
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
