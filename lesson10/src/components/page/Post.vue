<template>
  <div>
    <ViewPost :post="post"
              :cntComment="numberOfComments(post)"
              :user="findUser(post)"
              :key="post.id"/>

    <div class="comment" v-for="comment in comments" :key="comment.id">
      {{findUser(comment).name}}: {{comment.text}}
    </div>

  </div>
</template>

<script>
import ViewPost from "./ViewPost";

export default {
  name: "Post",
  components: {ViewPost},
  props: ["post", "comments", "findUser"],
  methods: {
    numberOfComments: function () {
      return this.comments.length
    },
  },
}

</script>

<style scoped>

</style>