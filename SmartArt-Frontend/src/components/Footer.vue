<template>
            <div class = "footer">
                    <div class = "inner_footer">
                    <div class = "logo_container">
                        <img src="../assets/SmartArt.png">
                    </div>

                    <div class="footer_third">
                        <h1>Need Help?</h1>
                        <a href="#">Terms &amp; Conditions</a>
                        <a href="#">Privacy Policy</a>
                        <a href="#/ContactUs">Contact Us</a>
                    </div>

                    <div class="footer_third">
                        <h1>Quick Navigation</h1>
                        <a href="#/home">Home</a>
                        <a href="#">Browse Artwork</a>
                        <a href="#/CreateAccount">Create Account</a>
                        <button @click="toAcc">Login</button>
                    </div>

                    <div class="footer_third">
                        <h1>Our Links</h1>
                        <a href="https://github.com/McGill-ECSE321-Fall2020/project-group-12">Github</a>

                        <span>
                        &copy; SmartArt Ltd.<br>
                        McGill University<br>
                        845 Sherbrooke St W<br>
                        Montreal, Quebec<br>
                        H3A0G4
                        </span>
                    </div>
                </div>
            </div>
</template>

<script>
export default {
  name: "Footer",
  data() {
    return {
      email: "",
      error: "",
      userType: "",
      buttonType: "",
    };
  },

  created: function () {
    this.email = this.$store.getters.getActiveUser;
    this.userType = this.$store.getters.getActiveUserType;
  },
  methods: {
    toAcc() {
      console.log(this.$store.getters.getActiveUser);
      if (this.$store.getters.getActiveUser == "") {
        this.$router.push({ name: "Login" });
      } else {
        this.$router.push({ name: "Account" });
      }
    },
    toHome() {
      this.$router.push({ name: "Home" });
    },
  },
};
</script>

<style scoped>
button{
    background:none!important;
    border: none;
    padding:0!important;
    font-family: 'arial';
    font-size: 16px;
    color:white;
    display: block;
    text-align: left;
    font-weight: 200;
    cursor: pointer;
    width: 100%;
    padding-bottom: 5px;
}

button:hover {
    text-decoration:underline;
}

*{
    margin:0;
    padding:0;
    list-style: none;
    text-decoration: none;
}

.footer{
    width: 100vw;
    display: block;
    overflow: hidden;
    padding: 70px 0;
    box-sizing: border-box;
    background-color: #3C1874;
    
}

.inner_footer{
    display: block;
    margin: 0 auto;
    width: 1100px;
    height: 100%;
}

.inner_footer .logo_container{
    width: 35%;
    float: left;
    height: 100%;
    display: block;
}

.inner_footer .logo_container img{
    width: 120px;
    height: auto;
}

.inner_footer .footer_third{
    width: calc(21.6666666667% - 20px);
    margin-right: 10px;
    float: left;
    height: 100%;
}

.inner_footer .footer_third:last-child{
    margin-right: 0;

}
.inner_footer .footer_third h1{
    font-family: 'arial';
    font-size: 20px;
    color:white;
    display: block;
    text-align: left;
    width: 100%;
    margin-bottom: 20px;
}
.inner_footer .footer_third a{
    font-family: 'arial';
    font-size: 16px;
    color:white;
    display: block;
    text-align: left;
    font-weight: 200;
    width: 100%;
    padding-bottom: 5px;
}
.inner_footer .footer_third li{
    display: inline-block;
    padding: 0 5px;
    font-size: 20px;
}

.inner_footer .footer_third span{
    color: white;
    font-family: 'arial';
    font-size: 14px;
    font-weight: 200;
    display: block;
    text-align: left;
    width: 100%;
    padding-top: 20px;
}

@media(max-width: 800px){
    .footer .inner_footer{
        width: 90%;
    }

    .inner_footer .logo_container,
    .inner_footer .footer_third{
        width: 100%;
        margin-bottom: 30px;
    }
}
</style>