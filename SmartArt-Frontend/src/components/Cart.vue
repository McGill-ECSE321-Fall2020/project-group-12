Cart
<!DOCTYPE html>
<template>
  <html style="color: white">
    <Taskbar />
    <body style="align-items: center; text-align: center">
      <div style="padding-top: 30px"></div>
      <section id="cart">
        <div class="cartTitle">
          <h1>My Cart</h1>
        </div>
        <div class="top divider">
          <hr />
        </div>
        <div class="row cartText">
          <div class="columns;" style="width: 14%"></div>
          <div class="columns;" style="width: 20%">
            Image
            <table>
              <li
                v-for="posting in cartPostings"
                v-bind:key="posting.postingID"
              >
                <tr>
                  <img
                    v-bind:src="posting.image"
                    style="max-height: 220px; width: auto"
                  />
                </tr>
              </li>
            </table>
          </div>
          <div class="columns;" style="width: 20%">
            Title
            <table>
              <li
                v-for="posting in cartPostings"
                v-bind:key="posting.postingID"
              >
                <tr>
                  {{
                    posting.title
                  }}
                </tr>
              </li>
            </table>
          </div>
          <div class="columns;" style="width: 15%"></div>
          <div class="columns;" style="width: 12%">
            Price
            <table>
              <li
                v-for="posting in cartPostings"
                v-bind:key="posting.postingID"
              >
                <tr>
                  {{
                    posting.price
                  }}
                </tr>
              </li>
            </table>
          </div>
          <div class="columns;" style="width: 5%">
            <hr />
            <table>
              <li
                v-for="posting in cartPostings"
                v-bind:key="posting.postingID"
              >
                <tr>
                  <div class="purchaseButton">
                    <a href="http://127.0.0.1:8087/#/cart">
                      <button
                        type="button"
                        class="btn btn-danger"
                        @click="removePosting(posting)"
                      >
                        Remove
                      </button>
                    </a>
                  </div>
                </tr>
              </li>
            </table>
          </div>
          <div class="columns;" style="width: 14%"></div>
        </div>
        <div class="bot divider">
          <hr />
        </div>
        <div class="row">
          <div class="columns;" style="width: 70%"></div>
          <div class="columns;" style="width: 5%; text-align: left">Total</div>
          <div class="columns;" style="width: 5%"></div>
          <div class="columns;" style="width: 5%; text-align: right">
            {{ this.totalPrice }}
          </div>
        </div>
      </section>
      <section id="buttons">
        <div class="row">
          <div class="columns;" style="width: 53%"></div>
          <div class="columns;" style="width: 17%">
            <b-dropdown
              id="dropdown-1"
              text="Select your delivery type"
              class="m-md-2"
            >
              <b-dropdown-item-btn @click="setPickUp"
                >Pick-Up</b-dropdown-item-btn
              >
              <b-dropdown-item-btn @click="setShipped"
                >Shipped</b-dropdown-item-btn
              >
            </b-dropdown>
          </div>
          <div class="columns;" style="width: 17%">
            <div class="purchaseButton">
              <a href="http://127.0.0.1:8087/#/orderconfirmation">
                <button type="button" class="btn btn-danger" @click="toConf">
                  Confirm Purchase
                </button>
              </a>
            </div>
          </div>
        </div>
      </section>
    </body>
  </html>
</template>

<script>
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
  components: {
    PostingList,
    Taskbar,
  },
  data() {
    return {
      cart: null,
      cartPostings: [],
      totalPrice: 0.00,
      email: null,
      deliveryType: null,
      purchaseID: null,
      response: [],
    };
  },
  created: function () {
    this.email = this.$store.getters.getActiveUser;
    AXIOS.get("/purchases/cart/".concat(this.email))
      .then((response) => {
        this.cart = response.data;
        if (cart != null) {
          this.cartPostings = response.data.postings;
          document.getElementById("cartPostings").innerHTML = this.cartPostings;
        }
      })
      .catch((e) => {
        this.errorPosting = e;
      });
    this.purchaseID = this.$store.getters.getActivePurchase;
    AXIOS.get("/purchases/".concat(this.purchaseID))
        .then((response) => {
          this.totalPrice = response.data.totalPrice;
        })
        .catch((e) => {
          this.error = e;
        });
  },
  methods: {
    removePosting: function (posting) {
      this.cartPostings.splice(this.cartPostings.indexOf(posting), 1);
      AXIOS.delete("/purchase/cart/remove/".concat(posting.postingID)
      ).catch((e) => {
        this.errorPosting = e;
      });
    },
    setPickUp: function () {
      this.$store.dispatch("setActiveDeliveryType", "PickUp");
    },
    setShipped: function () {
      this.$store.dispatch("setActiveDeliveryType", "Shipped");
    },
    toConf: function (){
      this.$router.push({ name: "OrderConfirmation" });
    }
  },
};
</script>


<style>
hr {
  width: 75%;
  background: black;
}

#cart {
  border-top: 1px solid #c1c1c1;
  border-bottom: 1px solid #c1c1c1;
  padding-top: 0.9375rem;
  padding-bottom: 0.9375rem;
  background-color: rgba(216, 216, 216, 0.2);
  text-align: center;
  margin-top: 1rem;
  margin-bottom: 1rem;
}
#buttons {
  padding-top: 0.9375rem;
  padding-bottom: 0.9375rem;
  text-align: center;
  margin-top: 1rem;
  margin-bottom: 1rem;
  margin-left: 6rem;
  margin-right: 6rem;
}
ul.ba {
    list-style-type: none;
}
</style>
