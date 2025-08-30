<template>
  <TheLayout>
    <form @submit="handleSubmit($event)">

      <label>
        Email
        <input type="text" v-model="email">
      </label>

      <label>
        Senha
        <input type="text" v-model="password">
      </label>

      <label v-if="!login">
        Nome
        <input type="text" v-model="name">
      </label>

      <label v-if="!login">
        Nome de Usuário
        <input type="text" v-model="username">
      </label>


      <button type="submit">{{ login ? "Logar!" : "Cadastrar!" }}</button>
      <button type="button" @click="handleToggleForm">
        {{ login ? "Não possui uma conta? Crie agora!" : "Já possui uma conta? Faça o login!" }}
      </button>

    </form>
  </TheLayout>
</template>

<script lang="ts">
import TheLayout from '@/components/TheLayout.vue';
import router from '@/router';
import { UserService } from '@/services/userService';
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'LoginView',
  data() {
    return {
      login: false,
      email: "",
      password: "",
      name: "",
      username: ""
    }
  },
  components: {
    TheLayout
  },
  mounted() {
    const url = router.currentRoute.value.path
    this.login = url == "login"
  },
  methods: {
    async handleSubmit(e: Event) {
      e.preventDefault();

      try {

        if (this.login) {
          await UserService.login({ email: this.email, password: this.password });
        } else {
          await UserService.register({ email: this.email, password: this.password, name: this.name, username: this.username });
        }

      } catch {

      }
      router.push("/home");
    },
    handleToggleForm() {
      this.login = !this.login;
    }
  }
})
</script>
