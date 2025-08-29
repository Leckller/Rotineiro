<template>
  <TheLayout>
    <form @submit.prevent="handleSubmit">

      <label>
        Email
        <input type="text" v-model="email">
      </label>

      <label>
        Senha
        <input type="text" v-model="password">
      </label>


      <button type="submit">{{ login ? "Logar!" : "Cadastrar!" }}</button>
      <button @click="handleToggleForm">Não possui uma conta? Crie agora!</button>

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
      password: ""
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
    async handleSubmit() {
      await UserService.login({ email: this.email, password: this.password });
      router.push("/home");
    },
    handleToggleForm() {
      this.login = !this.login;
    }
  }
})
</script>
