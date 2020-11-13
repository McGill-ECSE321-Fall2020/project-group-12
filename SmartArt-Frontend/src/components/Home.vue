<!DOCTYPE html>
<template>
  <html style="color: white">
      <Taskbar/>
      <body style="align-items: center; text-align: center">
      <div style="height: 100vh">
        <h1 id=quote>
          <b>Your Gallery,<br> Reimagined.</b>
        </h1>
      </div>
      <div style="padding-top: 50px">
        <div id="postingCard">
          <PostingList v-bind:postingList="postingList" />
        </div>
      </div>
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
  name: "SmartArt",
  city: "Montreal",
  commission: "",
  data() {
    return {
      slide: 0,
      sliding: null,
      artists: [],
      buyers: [],
      admins: [],
      postingList: [],
      errorArtist: "",
      errorBuyer: "",
      errorPosting: "",
      errorAdmin: "",
      response: [],
    };
  },
  components: {
    PostingList,
    Taskbar
  },
  created: function () {
    AXIOS.get("/artists")
      .then((response) => {
        this.artists = response.data;
      })
      .catch((e) => {
        this.errorArtist = e;
      });
    AXIOS.get("/buyers")
      .then((response) => {
        this.buyers = response.data;
      })
      .catch((e) => {
        this.errorBuyer = e;
      });
    AXIOS.get("/administrators")
      .then((response) => {
        this.admins = response.data;
      })
      .catch((e) => {
        this.errorAdmin = e;
      });
    AXIOS.get("/postings")
      .then((response) => {
        this.postingList = response.data;
      })
      .catch((e) => {
        this.errorPosting = e;
      });
  },
  methods: {
    onSlideStart(slide) {
      this.sliding = true;
    },
    onSlideEnd(slide) {
      this.sliding = false;
    },

  },
};
</script>

<style>
#top-container {
  margin-bottom: 0;
  margin-top: 0;
  background-color: #d21f3c;
  color: #ffffff;
}
#welcome h2 {
  text-align: center;
  margin-top: 15px;
  margin-bottom: 10px;
  font-size: 37px;
}
#welcome h3 {
  text-align: center;
  margin-top: 5px;
  margin-bottom: 0px;
  font-size: 30px;
  font-family: Lucida;
  font-style: oblique;
  color: #d21f3c;
}


#postingCard {
  padding-left: 100px;
  padding-right: 100px;
  padding-bottom: 50px;
}

#quote {
  background-image: url("../assets/stairsAcrossCanal.jpg");
  height: 100%;
  background-repeat: no-repeat;
  background-size: cover;
  text-align: center;
  padding-top: 15vh;
  font-family: Palatino;
  font-variant: small-caps;
  font-size: 100px
}
</style>
