import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import SmartArt from '@/components/SmartArt'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/app',
      name: 'SmartArt',
      component: SmartArt
    }
  ]
})