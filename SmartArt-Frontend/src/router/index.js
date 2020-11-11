import Vue from 'vue'
import Router from 'vue-router'
import SmartArt from "../components/SmartArt";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/app',
      name: 'SmartArt',
      component: SmartArt
    }
  ]
})
