<template>
<html lang="en">
    <Taskbar />
    <div id="createPosting">
      <h3>Create your posting</h3>
      <div class="container-fluid">
        <div class="input">
          <div class="inputbox">
            <input
              type="text"
              class="form-control input-style"
              v-model="title"
              placeholder="Title of your artwork"
            />
          </div>
          <div class="inputbox">
            <input
              type="number"
              class="form-control input-style"
              v-model="price"
              placeholder="price"
            />
          </div>
          <div class="inputbox">
            <input
              type="text"
              class="form-control input-style"
              v-model="dimensions"
              placeholder="Enter the dimensions of your artwork"
            />
          </div>
          <div class="inputbox">
            <input
              type="text"
              class="form-control input-style"
              v-model="description"
              placeholder="Add a description"
            />
          </div>
           <div class="inputbox">
            <input
              type="url"
              class="form-control input-style"
              v-model="image"
              placeholder="Place the URL of your artwork"
            />
          </div>
        </div>
      </div>
      <b-button @click="createPos" pill variant="outline-secondary"
        >Create Artwork</b-button
      >
      <a href="#" class="btn btn-danger" @click="toPosting(this.postingID)">View Artwork</a>
      <p>{{ error }}</p>
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
  name: "createPosting",
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
      artStatus: "Available",
      date: "",
      image: "",
      message: "",
      userType: ""
    };
  },
  components: {
    Taskbar,
  },
  methods: {
    createPos: function () {
      this.email = this.$store.getters.getActiveUser;
      this.postingID = "help ben plz";
      this.userType = this.$store.getters.getActiveUserType;
      if(this.userType == 'artist'){
          AXIOS.get("/artists/".concat(this.email))
      .then((response) => {
      })
      .catch((e) => {
        this.message =
          "You must be logged in as an artist to create a posting.";
      });
    }
      
      if (this.title == "") {
        this.error = "Please enter a title";
      } else if (this.price == "") {
        this.error = "Please enter a price";
      } else if(this.image == "") {
        this.error = "Please enter a link to your artwork";
      } else {
        AXIOS({

          method: "post",
          url: "/".concat(postings).concat("/").concat("create"),
          data: {
            postingID: this.postingID,
            title: this.title,
            artist: this.email,
            gallery: this.gallery,
            price: this.name,
            xDim: this.xDim,
            yDim: this.yDim,
            zDim: this.zDim,
            dimensions: this.gallery,
            description: this.description,
            artStatus: this.artStatus,
            image: this.image,
          },
        })
          .then((response) => {
            this.$store.dispatch("setActivePosting", this.postingID)
            this.$router.push({ name: "ViewPosting" });

          })
          .catch((e) => {
            var errorMsg = e.message;
            console.log(e);
            this.error = errorMsg;
          });
      }
    },
    toPosting(id) {
      this.$store.dispatch("setActivePosting", id);
      this.$router.push({ name: "ViewPosting" });
    },
  },
};
</script>
<style scoped>
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