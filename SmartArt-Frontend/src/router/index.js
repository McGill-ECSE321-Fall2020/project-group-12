import Vue from 'vue'
import Router from 'vue-router'
import SmartArt from "../components/SmartArt";
import Login from "../components/Login";
import CreateAccount from "../components/CreateAccount"
import Home from "../components/Home";
import Cart from "../components/Cart"

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/home'
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
    },
    {
      path: '/home',
      name: 'Home',
      component: Home
    },
    {
      path: '/cart',
      name: 'Cart',
      component: Cart
    }
  ]
})
