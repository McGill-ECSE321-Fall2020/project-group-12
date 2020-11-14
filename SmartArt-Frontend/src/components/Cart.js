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

function BuyerDto (email, name, password, gallery) {
  this.email = email
  this.name = name
  this.password = password
  this.gallery = gallery
}

export default {
  name: "Cart",

  components: {
      PostingList,
      Taskbar
  },
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
};
