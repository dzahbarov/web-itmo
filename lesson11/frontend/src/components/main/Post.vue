<template>
  <div>
    <ViewPost :post="post"
              :numberOfComments="post.comments.length"
              :key="post.id"/>

    <div class="comment" v-for="comment in post.comments" :key="comment.id">
      {{ comment.user.name }}: {{ comment.text }}
    </div>

  </div>
</template>

<script>
import ViewPost from "./ViewPost";

export default {
  name: "Post",
  components: {ViewPost},
  props: ["post"],
}

</script>

<style scoped>

</style>