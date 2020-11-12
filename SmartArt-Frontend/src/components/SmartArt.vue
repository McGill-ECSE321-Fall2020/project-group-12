<template id="smartart">
  <div>
    <img src="../assets/SmartArt.png" style="padding-bottom: 100px; padding-top: 50px">
    <div class="carousel-style">
      <b-carousel
        id="carousel-1"
        v-model="slide"
        :interval="4000"
        controls
        indicators
        background="#ababab"
        img-width="1080"
        img-height="720"
        style="text-shadow: 1px 1px 2px #333;"
        @sliding-start="onSlideStart"
        @sliding-end="onSlideEnd"
      >
        <!-- Text slides with image -->
        <b-carousel-slide
          img-src="https://picsum.photos/1024/480/?image=52"
        ></b-carousel-slide>

        <!-- Slides with custom text -->
        <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=54">
        </b-carousel-slide>

        <!-- Slides with image only -->
        <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=58"></b-carousel-slide>

        <!-- Slides with img slot -->
        <!-- Note the classes .d-block and .img-fluid to prevent browser default image alignment -->
        <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=55">
        </b-carousel-slide>
      </b-carousel>
    </div>
    <div style="padding-top: 50px">
      <div id="postingCard">
        <PostingList v-bind:postingList="postingList"/>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import PostingList from './PostingList'

var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: {'Access-Control-Allow-Origin': frontendUrl}
})

export default {
  name: "SmartArt",
  city: "Montreal",
  commission: '',
  data() {
    return {
      slide: 0,
      sliding: null,
      artists: [],
      buyers: [],
      admins: [],
      postingList: [],
      errorArtist: '',
      errorBuyer: '',
      errorPosting: '',
      errorAdmin: '',
      response: []
    }
  },
  components: {
    PostingList
  },
  created: function () {
    AXIOS.get('/artists').then(response => {
      this.artists = response.data
    })
      .catch(e => {
        this.errorArtist = e
      })
    AXIOS.get('/buyers').then(response => {
      this.buyers = response.data
    })
      .catch(e => {
        this.errorBuyer = e
      })
    AXIOS.get('/administrators').then(response => {
      this.admins = response.data
    })
      .catch(e => {
        this.errorAdmin = e
      })
    AXIOS.get('/postings').then(response => {
      this.postingList = response.data
    })
      .catch(e => {
        this.errorPosting = e
      })
  },
  methods: {
    onSlideStart(slide) {
      this.sliding = true
    },
    onSlideEnd(slide) {
      this.sliding = false
    }
  }
}
</script>

<style>
#smartart {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #f2ece8;
}

.carousel-style {
  padding-left: 200px;
  padding-right: 200px;
  padding-bottom: 100px;
  color: #2c3e50;
  background: #f2ece8;
}

#postingCard {
  padding-left: 100px;
  padding-right: 100px;
  padding-bottom: 50px;
}
</style>
