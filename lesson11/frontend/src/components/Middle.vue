<template>
  <div class="middle">
    <Sidebar :posts="viewPosts"/>
    <main>
      <Index v-if="page === 'Index'" :posts="posts" :findUser="findUser"/>
      <Enter v-if="page === 'Enter'"/>
      <Register v-if="page === 'Register'"/>
      <Users v-if="page === 'Users'" :users="users"/>
      <WritePost v-if="page === 'WritePost'" :users="users" :user="user"/>
      <Post v-if="page === 'Post'"
            :post="post"
            :findUser="findUser"/>
    </main>
  </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./main/Index";
import Enter from "./main/Enter";
import Register from "./main/Register";
import Users from "./main/Users";
import WritePost from "./main/WritePost";
import Post from "@/components/main/Post";

export default {
  name: "Middle",
  data: function () {
    return {
      page: "Index"
    }
  },
  components: {
    Register,
    Enter,
    Index,
    Sidebar,
    Users,
    WritePost,
    Post
  },
  props: ["posts", "users", "post", "user"],
  methods: {
    findUser: function (post) {
      return Object.values(this.users).find((user) => user.id === post.user.id);
    },
    // numberOfComments: function (post) {
    //   return Object.values(this.comments).filter(comment => comment.postId === post.id).length
    // },
  },
  computed: {
    viewPosts: function () {
      return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
    }
  },
  beforeCreate() {
    this.$root.$on("onChangePage", (page) => this.page = page)
    this.$root.$on("onPostPage", (post) => { this.page = "Post"; this.post=post})
  }
}
</script>

<style scoped>

</style>
