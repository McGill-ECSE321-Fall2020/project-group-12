import Vue from 'vue'
import Router from 'vue-router'
import Login from "../components/Login";
import CreateAccount from "../components/CreateAccount"
import Home from "../components/Home";


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
    }
  ]
})
