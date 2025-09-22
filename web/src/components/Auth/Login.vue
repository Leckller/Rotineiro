<template>
  <TheLayout>
    <article>
      <span>
        <FontAwesomeIcon />
      </span>

      <h2>Rotineiro</h2>
      <p>Organize sua vida, alcance seus objetivos</p>
    </article>
    <form class="form" @submit="handleSubmit($event)">
      <h3>Entrar</h3>
      <p>Digite suas credenciais para acessar sua conta</p>

      <TheInput v-model="email" label="Email" />
      <TheInput v-model="password" label="Senha" />

      <button type="submit">Entrar</button>
      <button type="button" @click="handleToggleForm">
        Não possui uma conta? Cadastre-se
      </button>
    </form>
    <div>
      <article>
        <span>
          <FontAwesomeIcon />
        </span>
        <small>Metas Claras</small>
      </article>
      <article>
        <span>
          <FontAwesomeIcon />
        </span>
        <small>Rotinas Organizadas</small>
      </article>
      <article>
        <span>
          <FontAwesomeIcon />
        </span>
        <small>Progresso Visual</small>
      </article>
    </div>
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
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { defineComponent } from "vue";

export default defineComponent({
  name: "LoginView",
  data() {
    return {
      useNotification: useNotificationStore(),
      login: false,
      email: "",
      password: "",
    };
  },
  components: {
    TheLayout,
    TheInput,
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
        }
        router.push("/home");
      } catch (error: any) {
        if (!error.response) return;
        this.showMessage({
          type: NotificationEnum.error,
          title: error.response?.data?.message,
          time: 3000,
        });
        if (error.response.data.response) {
          for (const [index, message] of Object.values(
            error.response?.data?.response
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