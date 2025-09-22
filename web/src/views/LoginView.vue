<template>
  <TheLayout>
    <form class="form" @submit="handleSubmit($event)">
      <TheInput v-model="email" label="Email" />
      <TheInput v-model="password" label="Senha" />
      <TheInput v-if="!login" v-model="name" label="Nome" />
      <TheInput v-if="!login" v-model="username" label="Nome de Usuário" />

      <button type="submit">{{ login ? "Logar!" : "Cadastrar!" }}</button>
      <button type="button" @click="handleToggleForm">
        {{
          login
            ? "Não possui uma conta? Crie agora!"
            : "Já possui uma conta? Faça o login!"
        }}
      </button>
    </form>
  </TheLayout>
</template>

<script lang="ts">
import TheInput from "@/components/TheInput.vue";
import TheLayout from "@/components/TheLayout.vue";
import router from "@/router";
import { UserService } from "@/services/userService";
import {
  NotificationEnum,
  NotificationType,
  useNotificationStore,
} from "@/stores/notification";
import { defineComponent } from "vue";

export default defineComponent({
  name: "LoginView",
  data() {
    return {
      useNotification: useNotificationStore(),
      login: false,
      email: "",
      password: "",
      name: "",
      username: "",
    };
  },
  components: {
    TheLayout,
    TheInput,
  },
  mounted() {
    const url = router.currentRoute.value.path;
    this.login = url == "login";
  },
  methods: {
    showMessage(notification: NotificationType) {
      this.useNotification.createNotification(notification);
    },
    async handleSubmit(e: Event) {
      e.preventDefault();

      try {
        if (this.login) {
          await UserService.login({
            email: this.email,
            password: this.password,
          });
        } else {
          await UserService.register({
            email: this.email,
            password: this.password,
            name: this.name,
            username: this.username,
          });
        }
        router.push("/home");
      } catch (error: any) {
        this.showMessage({
          type: NotificationEnum.error,
          title: error.response.data.message,
          time: 3000,
        });
        if (error.response.data.response) {
          for (const [index, message] of Object.values(
            error.response.data.response
          ).entries()) {
            this.showMessage({
              type: NotificationEnum.important,
              title: String(message),
              time: (index + 4) * 1000,
            });
          }
        }
      }
    },
    handleToggleForm() {
      this.login = !this.login;
    },
  },
});
</script>

<style scoped>
.form {
  display: flex;
  height: 100%;
  width: 100%;
  flex-direction: column;
  gap: 16px;
  align-items: center;
  justify-content: center;
}

.form label {
  display: flex;
  flex-direction: column;
  text-align: start;
}
</style>