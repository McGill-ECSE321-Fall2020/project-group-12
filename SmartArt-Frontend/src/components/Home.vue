Home
<!DOCTYPE html>
<template>
  <html style="color: white">
      <nav class="navbar navbar-light navbar-expand-md">
        <div class="container-fluid">
          <a class="navbar-brand" href="#"
            ><img id="Logo" src="../assets/SmartArt.png" /></a
          ><button
            data-toggle="collapse"
            class="navbar-toggler"
            data-target="#navcol-1"
          >
            <span class="sr-only">Toggle navigation</span
            ><span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navcol-1">
            <ul class="nav navbar-nav ml-auto">
              <li class="nav-item">
                <a class="nav-link active" href="#">Artists</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Postings</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Sell Art</a>
              </li>
              <li class="nav-item" @click="toAcc"><a class="nav-link" href="#">Account</a></li>
              <form class="form-inline">
                <input
                  class="form-control mr-sm-2"
                  type="search"
                  placeholder="Search"
                  aria-label="Search"
                />
                <button class="btn btn-danger" type="submit">Search</button>
              </form>
              <li class="nav-item">
                <a href="#!" class="nav-link navbar-link-2 waves-effect">
                  <span class="badge badge-pill red">Cart</span>
                  <i class="fas fa-shopping-cart pl-0"></i>
                </a>
              </li>
            </ul>
          </div>
        </div>
      </nav>
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
        <div class="container-fluid">
          <div class="row">
            <div class="col-md">
              <a href="https://smartart-frontend-000.herokuapp.com">
                <button
                  type="button"
                  style="background-color: #d21f3c; color: white"
                  class="btn"
                >
                  <font size="5"><b>Buy Art</b></font>
                </button>
              </a>
            </div>
            <div class="col-md">
              <a href="https://smartart-frontend-000.herokuapp.com">
                <button
                  type="button"
                  style="background-color: #d21f3c; color: white"
                  class="btn"
                >
                  <font size="5"><b>Sell Art</b></font>
                </button>
              </a>
            </div>
          </div>
        </div>

      </div>
    </body>
  </html>
</template>

<script>
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
    //fix to check if current acc
    toAcc(){
      this.$router.push({name: 'Login'})
    }
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

.carousel-style {
  padding-left: 200px;
  padding-right: 200px;
  padding-bottom: 100px;
  color: #d21f3c;
  background: #ffffff;
}
.container-fluid {
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: auto;
  white-space: nowrap;
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

