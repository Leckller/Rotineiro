<template>
  <TheLayout>
    <article>
      <span>
        <FontAwesomeIcon icon="" />
      </span>

      <h2>Criar Conta</h2>
      <p>Comece sua jornada de produtividade</p>
    </article>
    <form class="form" @submit="handleSubmit($event)">
      <TheInput v-model="email" label="Email" />
      <TheInput v-model="password" label="Senha" />
      <TheInput v-model="name" label="Nome" />
      <TheInput v-model="username" label="Nome de Usuário" />

      <button type="submit">Criar Conta</button>
      <button type="button" @click="handleToggleForm">
        Já tem uma conta? Faça login
      </button>
    </form>
    <div>
      <article>
        <h4>Com o Rotineiro você pode:</h4>
        <p><FontAwesomeIcon icon="check" /> Criar rotinas personalizadas</p>
        <p>Acompanhar <FontAwesomeIcon icon="check" /> seu progresso diário</p>
        <p>
          <FontAwesomeIcon icon="check" /> Visualizar estatísticas motivacionais
        </p>
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
      name: "",
      username: "",
    };
  },
  components: {
    TheLayout,
    FontAwesomeIcon,
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
        await UserService.register({
          email: this.email,
          password: this.password,
          name: this.name,
          username: this.username,
        });
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
      this.$router.push("/");
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