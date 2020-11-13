<template>
  <div>
  <nav class="navbar navbar-light navbar-expand-md">
    <div class="container-fluid">
      <a class="navbar-brand" href="#"
      ><img id="Logo" src="../assets/SmartArt.png" /></a
      ><button
      data-toggle="collapse"
      class="navbar-toggler"
      data-target="#navcol-1"
    >
            <span class="sr-only">Toggle navigation</span
            ><span class="navbar-toggler-icon"></span>
    </button>
      <div class="collapse navbar-collapse" id="navcol-1">
        <ul class="nav navbar-nav ml-auto">
          <li class="nav-item">
            <a class="nav-link active" href="#">Artists</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Postings</a>
          </li>
          <li class="nav-item" @click="toSell">
            <a class="nav-link" href="#">Sell Art</a>
          </li>
          <li class="nav-item" @click="toAcc"><a class="nav-link" href="#">Account</a></li>
          <form class="form-inline">
            <input
              class="form-control mr-sm-2"
              type="search"
              placeholder="Search"
              aria-label="Search"
            />
            <button
              class="btn btn-danger"
              type="submit"
            >
              Search
            </button>
          </form>
        </ul>
      </div>
    </div>
  </nav>
  </div>
</template>


<script>
export default {
  name: "Taskbar",
//fix to check if current acc
  methods:
    {
      toAcc() {
        this.$router.push({name: 'Login'})
      },
      toHome() {
        this.$router.push({name: 'Home'})
      },
      toSell() {
        this.$router.push({name: 'NewPost'})
      }
    }
}
</script>

<style scoped>
.container-fluid {
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: auto;
  white-space: nowrap;
}

</style>
