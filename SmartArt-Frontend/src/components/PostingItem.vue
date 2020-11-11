<template>
<div>
  <h1>
    {{posting.title}}
  </h1>
</div>
</template>

<script>

export default {
  name: "PostingItem",
  props: [
    "posting"
  ]
}
</script>

<style scoped>

</style>
