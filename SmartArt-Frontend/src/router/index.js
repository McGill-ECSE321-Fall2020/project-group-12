import Vue from 'vue'
import Router from 'vue-router'
import Login from "../components/Login";
import CreateAccount from "../components/CreateAccount";
import Home from "../components/Home";
import Cart from "../components/Cart"
import Account from "../components/Account";
import ViewPosting from "../components/ViewPosting";
import CreatePosting from "../components/CreatePosting"

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/home'
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
    },
    {
      path: '/account',
      name: 'Account',
      component: Account
    },
    {
      path: '/viewPosting',
      name: 'ViewPosting',
      component: ViewPosting
    },
    {
      path: '/createPosting',
      name: 'CreatePosting',
      component: CreatePosting
    }
  ]
})
