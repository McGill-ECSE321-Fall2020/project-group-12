<template>
  <div>
    <b-card-group columns id="postingCardColumn">
      <b-card id="postingCardClass" v-bind:key="posting.id" v-for="posting in postingList"
        v-bind:img-src="posting.image"
        img-alt="Image"
        img-top
        tag="article"
        style="max-width: 20rem;"
        class="mb-2"
      >
        <b-card-title>
          {{posting.title}}
        </b-card-title>
        <b-card-text>
          <div>
            ${{posting.price}}
          </div>
          {{ posting.description }}
        </b-card-text>

        <b-button href="#" variant="primary">View Post</b-button>
        <template #footer>
          <small class="text-muted">Posted {{posting.date}}</small>
        </template>
      </b-card>
    </b-card-group>
  </div>
</template>

<script>

export default {
  name: "PostingList",
  // props "exports" what's inside so that whatever imports PostingList has access to data inside
  props: ["postingList"]
}
</script>

<style scoped>
  #postingCardClass {
    box-shadow: 6px 6px #999999;
  }
  #postingCardColumn {
    padding-bottom: 20px;
  }
</style>
