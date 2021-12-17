<template>
  <div id="app">
    <Header :user="user"/>
    <Middle :posts="posts" :users="users" :user="user"/>
    <Footer/>
  </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";
import axios from "axios"

export default {
  name: 'App',
  components: {
    Footer,
    Middle,
    Header
  },
  data: function () {
    return {
      user: null,
      posts: [],
      users: []
    }
  },
  beforeMount() {
    if (localStorage.getItem("jwt") && !this.user) {
      this.$root.$emit("onJwt", localStorage.getItem("jwt"));
    }

    axios.get("/api/1/posts").then(response => {
      this.posts = response.data;
    });
    axios.get("/api/1/users").then(response => {
      this.users = response.data;
    });
  },
  beforeCreate() {
    this.$root.$on("onEnter", (login, password) => {
      if (password === "") {
        this.$root.$emit("onEnterValidationError", "Password is required");
        return;
      }

      axios.post("/api/1/jwt", {
        login, password
      }).then(response => {
        localStorage.setItem("jwt", response.data);
        this.$root.$emit("onJwt", response.data);
      }).catch(error => {
        this.$root.$emit("onEnterValidationError", error.response.data);
      });
    });

    this.$root.$on("onRegister", (login, name, password) => {

      if (name === "") {
        this.$root.$emit("onRegisterValidationError", "Name is required");
        return;
      }

      if (password === "") {
        this.$root.$emit("onRegisterValidationError", "Password is required");
        return;
      }

      if (login === "") {
        this.$root.$emit("onRegisterValidationError", "Login is required");
        return;
      }

      axios.post("/api/1/users", {
        login, name, password
      }).then(response => {
        this.users.push(response.data);
        this.$root.$emit("onEnter", login, password);
      }).catch(error => {
        this.$root.$emit("onRegisterValidationError", error.response.data);
      });
    });

    this.$root.$on("onJwt", (jwt) => {
      localStorage.setItem("jwt", jwt);

      axios.get("/api/1/users/auth", {
        params: {
          jwt
        }
      }).then(response => {
        this.user = response.data;
        this.$root.$emit("onChangePage", "Index");
      }).catch(() => this.$root.$emit("onLogout"))
    });

    this.$root.$on("onLogout", () => {
      localStorage.removeItem("jwt");
      this.user = null;
    });

    this.$root.$on("onWritePost", (title, text, user) => {
      if (this.user) {
        if (!title || title.length < 5) {
          this.$root.$emit("onWritePostValidationError", "Title is too short");
        } else if (!text || text.length < 10) {
          this.$root.$emit("onWritePostValidationError", "Text is too short");
        } else {
          axios.post("/api/1/posts", {
            title, text, user
          }).then(() => {
            // this.posts.push(response.data);
            axios.get("/api/1/posts").then(response => {
              this.posts = response.data;
            });
            this.$root.$emit("onChangePage", "Index");
          }).catch(error => {
            this.$root.$emit("onPostValidationError", error.response.data);
          });
        }
      } else {
        this.$root.$emit("onWritePostValidationError", "No access");
      }
    });

  }
}
</script>

<style>
#app {

}
</style>
