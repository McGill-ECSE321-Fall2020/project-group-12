import Vue from 'vue'
import Router from 'vue-router'
import SmartArt from "../components/SmartArt";
import Home from "../components/Home";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/app'
    },
    {
      path: '/app',
      name: 'SmartArt',
      component: SmartArt
    },
    {
      path: '/home',
      name: 'Home',
      component: Home
    },

  ]
})
