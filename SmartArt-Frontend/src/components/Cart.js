import axios from "axios";
import PostingList from "./PostingList";
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
  name: "Cart",

  components: {
      PostingList,
      Taskbar
  },
  data() {
    return {
      cart: null,
      cartPostings: [],
      cartTitles: [],
      cartPrices: [],
      totalPrice: 0.00,
      currentUser: null,
      response: [],
    };
  },
  created: function () {
      this.currentUser = this.$store.getter.getActiveUser;
      console.log("/purchases/cart/".concat(this.currentUser));
      AXIOS.get("/purchases/cart/".concat(this.currentUser))
        .then((response) => {
          this.cart = response.data;
        })
        .catch((e) => {
          this.errorPosting = e;
        });
      this.totalPrice = cart.totalPrice;
      document.getElementById("totalPrice").innerHTML = totalPrice;
      this.cartPostings = cart.postings;
      for (i = 0; i < cartPostings.length; i++) {
          this.cartTitles[i] = cartPostings[i].title;
          this.cartPrices[i] = cartPostings[i].price;
      }
  },
};
