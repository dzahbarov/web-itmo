<template>
  <div>
    <ViewPost v-for="post in sortedPosts"
              :post="post"
              :cntComment="numberOfComments(post)"
              :user="findUser(post)"
              :key="post.id"/>
  </div>
</template>

<script>
import ViewPost from "./ViewPost";


export default {
  name: "Index",
  components: {ViewPost},
  props: ["posts", "findUser", "numberOfComments"],
  computed: {
    sortedPosts: function () {
      return Object.values(this.posts).sort((a, b) => b.id - a.id);
    }
  },
}
</script>

<style scoped>

</style>
