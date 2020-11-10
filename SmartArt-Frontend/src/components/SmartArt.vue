<!--dynamic template for list of person object-->
<template>
  <div id="smartart">
    <h2>Artists</h2>
    <table>
      <tr v-for="gallery in galleries" :key="gallery.name">
        <td>{{ gallery.name }}</td>
      </tr>
      <!-- ... -->
    </table>
    <table>
      <!-- ... -->
      <tr>
        <td>
          <input type="text" v-model="newGallery" placeholder="Gallery Name" />
        </td>
        <td>
          <button v-bind:enabled="!newGallery" @click="createGallery(newGallery)">
            Create Gallery
          </button>
        </td>
      </tr>
    </table>
    <span v-if="errorGallery" style="color:red">Error: {{errorGallery}} </span>
  </div>
</template>

<script src="./purchasing.js">
</script>

<style>
#smartart {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: #f2ece8;
}
</style>
