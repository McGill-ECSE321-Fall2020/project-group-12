import Vue from 'vue'
import Router from 'vue-router'
import SmartArt from "../components/SmartArt";
import Login from "../components/Login";
import CreateAccount from "../components/CreateAccount"

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/app',
      name: 'SmartArt',
      component: SmartArt
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/createAccount',
      name: 'CreateAccount',
      component: CreateAccount
    }
  ]
})
