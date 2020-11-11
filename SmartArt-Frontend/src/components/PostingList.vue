<template>
  <div>
    <div v-bind:key="posting.id" v-for="posting in postingList">
      <PostingItem v-bind:posting="posting"/>
    </div>
  </div>
</template>

<script>
import PostingItem from "./PostingItem"

export default {
  name: "PostingList",
  // components are the data we need from imports, can be referenced in our data function
  components: {
    PostingItem
  },
  // props "exports" what's inside so that whatever imports PostingList has access to data inside
  props: ["postingList"]
}
</script>

<style scoped>

</style>
