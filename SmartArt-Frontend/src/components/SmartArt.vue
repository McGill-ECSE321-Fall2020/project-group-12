<template>
  <div id="smartart">
    <img src="../assets/SmartArt.png">
    <PostingList v-bind:postingList="postingList"/>
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
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: "SmartArt",
  city: "Montreal",
  commission: '',
  data () {
    return {
      artists: [],
      buyers: [],
      admins: [],
      postingList: [],
      errorArtist: '',
      errorBuyer: '',
      errorPosting: '',
      errorAdmin: '',
      response: []
    }},
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
    }

}
</script>

<style>
#smartart {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #f2ece8;
}
</style>
