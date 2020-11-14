Create account
<template>
  <html lang="en">
    <Taskbar></Taskbar>
    <div id="createAccount">
      <h3>Create your new posting</h3>
      <div class="container-fluid">
        <div class="input">
          <div class="inputbox">
            <p>Title:</p>
            <input
              type="text"
              class="form-control input-style"
              v-model="title"
              placeholder="Title"
            />
          </div>
          <div class="inputbox">
            <p>Description:</p>
            <input
              type="text"
              class="form-control input-style"
              v-model="description"
              placeholder="Description of your posting"
            />
          </div>
          <div class="inputbox">
            <p>Price ($):</p>
            <input
              type="number"
              class="form-control input-style"
              v-model="price"
              placeholder="Price ($)"
            />
          </div>
          <div class="inputbox">
            <p>X-Dimmension (cm):</p>
            <input
              type="number"
              class="form-control input-style"
              v-model="xDim"
              placeholder="X-Dimmension (cm)"
            />
          </div>
          <div class="inputbox">
            <p>Y-Dimmension (cm):</p>
            <input
              type="number"
              class="form-control input-style"
              v-model="yDim"
              placeholder="Y-Dimmension (cm)"
            />
          </div>
          <div class="inputbox">
            <p>Z-Dimmension (cm):</p>
            <input
              type="number"
              class="form-control input-style"
              v-model="zDim"
              placeholder="Z-Dimmension (cm)"
            />
          </div>
          <div class="inputbox">
            <p>Date:</p>
            <input
              type="date"
              class="form-control input-style"
              v-model="date"
              placeholder="Date created"
            />
          </div>
          <div class="inputbox">
            <p>Image URL</p>
            <input
              type="url"
              class="form-control input-style"
              v-model="image"
              placeholder="Image URL"
            />
          </div>
        </div>
      </div>
      <b-button @click="createPost" pill variant="outline-secondary"
        >Create Posting</b-button
      >
      <p>{{ error }}</p>
    </div>
  </html>
</template>

<script>
import axios from "axios";
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
  name: "CreatePosting",
  components: {
    Taskbar,
  },
  data() {
    return {
      email: "",
      title: "",
      description: "",
      image: "",
      price: null,
      xDim: null,
      yDim: null,
      zDim: null,
      date: "",
      gallery: "SmartArt",
      userType: "",
      error: "",
    };
  },
  created: function () {
    this.email = this.$store.getters.getActiveUser;
    this.userType = this.$store.getters.getActiveUserType;
  },
  methods: {
    createPost: function () {
      AXIOS({
        method: "post",
        url: "/posting/create",
        data: {
          artist: {
              email: this.email,
              gallery: this.gallery
          },
          title: this.title,
          price: this.price,
          xdim: this.xDim,
          ydim: this.yDim,
          zdim: this.zDim,
          image: this.image,
          date: this.date,
          description: this.description
        },
      })
        .then((response) => {
          this.email = "";
          this.title = "";
          this.description = "";
          this.xDim = null;
          this.yDim = null;
          this.zDim = null;
          this.date = "";
          this.image = "";
          this.error = "";

          this.$router.push({ name: "Account" });
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
