Cart
<!DOCTYPE html>
<template>
   <html style="color: white">
      <div class="taskbar"
          <Taskbar/>
      </div>
      <body style="align-items: center; text-align: center">
         <div style="padding-top: 30px">
         </div>
         <section id="cart">
            <div class = "cartTitle">
               <h1> My Cart </h1>
            </div>
            <div class="top divider">
            		<hr>
            </div>
            <div class = "row cartText">
               <div class="cartText1 columns">
         					&nbsp;
            	 </div>
               <div class="cartText2 columns">
        					Product
        		   </div>
            	 <div class="cartText2 columns">
            			Details
               </div>
            	 <div class="cartText1 columns">
          				Size
          		 </div>
      		     <div class="small-2 columns">
            			Quantity
            	 </div>
            	 <div class="cartText1 columns">
            			Price
            	 </div>
            	 <div class="cartText1 columns">
            					&nbsp;
            	 </div>
            </div>
            <!-- insert table here -->
            <div class="bot divider">
               <hr>
            </div>
            <div class = row>
               <div class = columns; style = "width: 70%">
               </div>
               <div class = columns; style = "width: 5%; text-align: left">
                                   Total
               </div>
               <div class = columns; style = "width: 5%">
               </div>
               <div class = columns; style = "width: 5%; text-align: right">
                    0.00
               </div>
            </div>
         </section>
         <section id="buttons">
            <div class = row>
                <div class = columns; style = "width: 70%">
                </div>
                <div class = columns; style = "width: 17%">
                   <div class="purchaseButton">
                      <a href="https://smartart-frontend-000.herokuapp.com">
                         <button type="button" class="btn btn-danger">
                            Confirm Purchase
                         </button>
                      </a>
                   </div>
                </div>
            </div>
         </section>
      </body>
    </html>
</template>

<script>
import Taskbar from "./Taskbar";
export default {
  name: "Cart",

  components: {
      Taskbar
  },
};

</script>


<style>

hr {
  width: 75%;
  background: black;
}

#cart {
    border-top: 1px solid #c1c1c1;
    border-bottom: 1px solid #c1c1c1;
    padding-top: .9375rem;
    padding-bottom: .9375rem;
    background-color: rgba(216,216,216,0.2);
    text-align: center;
    margin-top: 1rem;
    margin-bottom: 1rem;
    margin-left: 6rem;
    margin-right: 6rem;
}
#buttons{
    padding-top: .9375rem;
    padding-bottom: .9375rem;
    text-align: center;
    margin-top: 1rem;
    margin-bottom: 1rem;
    margin-left: 6rem;
    margin-right: 6rem;
}
.cartText1 {
    width: 8.33333%;
}
.cartText2 {
    width: 25%;
}
</style>
