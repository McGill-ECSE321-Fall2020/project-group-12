<template>
  <div id="app">
    <img src="./assets/SmartArt.png">
<!--    v-bind will map a variable postingList to our postingList in the data function
        essentially displays the data the is put into our data function-->
    <PostingList v-bind:postingList="postingList"/>
    <router-view></router-view>
  </div>
</template>

<script>
import PostingList from "./components/PostingList"

export default {
  name: 'app',
  components: {
    PostingList
  },
  data () {
    return {
      postingList: [
        {
          id: 1,
          title: "Posting 1"
        },
        {
          id: 2,
          title: "Posting 2"
        }
      ]
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
