<template>
  <TheLayout>
    <div class="conteudo">
      <article class="logo">
        <span class="icone-principal">
          <FontAwesomeIcon icon="calendar" />
        </span>

        <h2>Criar Conta</h2>
        <p>Comece sua jornada de produtividade</p>
      </article>
      <form class="form" @submit="handleSubmit($event)">
        <TheInput placeholder="seu@email.com" v-model="email" label="Email" />
        <TheInput
          placeholder="Sua Senha"
          v-model="password"
          type="Password"
          label="Senha"
        />
        <TheInput
          placeholder="Nome Único"
          v-model="username"
          label="Nome de Usuário"
        />
        <TheInput placeholder="Seu Nome" v-model="name" label="Nome" />

        <button
          class="entrar"
          :disabled="
            !(
              email.length > 6 &&
              password.length > 7 &&
              username.length > 4 &&
              name.length > 4
            )
          "
          type="submit"
        >
          Criar Conta
        </button>
        <button
          :style="{ color: '#00A63E' }"
          type="button"
          @click="handleToggleForm"
        >
          Já tem uma conta? Faça login
        </button>
      </form>

      <article class="lista-propagandas">
        <h4>Com o Rotineiro você pode:</h4>
        <p class="propaganda">
          <FontAwesomeIcon icon="check" /> Criar rotinas personalizadas
        </p>
        <p class="propaganda">
          <FontAwesomeIcon icon="check" /> Acompanhar seu progresso diário
        </p>
        <p class="propaganda">
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
.conteudo {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 40px;
  width: 85%;
  height: 100%;
}

.lista-propagandas {
  display: flex;
  flex-direction: column;
  gap: 16px;
  max-width: 350px;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.12);
  width: 100%;
  padding: 16px 8px;
  border-radius: 8px;
}

.propaganda {
  display: flex;
  align-items: center;
  font-size: 14px;
  justify-content: start;
  width: 100%;
  gap: 16px;
}

.propaganda svg {
    color: #7FD7A1;
    border: solid 1px #7FD7A1;
    border-radius: 999px;
    width: 10px;
    height: 10px;
    padding: 4px
}

.logo {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
}

.icone-principal {
  background: linear-gradient(to right, #60b5ff, #3bff79);
  width: 70px;
  height: 70px;
  display: flex;
  font-size: 40px;
  color: white;
  border-radius: 8px;
  justify-content: center;
  align-items: center;
}

.form {
  display: flex;
  width: 100%;
  flex-direction: column;
  gap: 16px;
  align-items: center;
  max-width: 350px;
  border-radius: 8px;
  justify-content: center;
  padding: 16px 8px;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.12);
}

.entrar {
  width: 100%;
  max-width: 300px;
  color: white;
  padding: 8px;
  border-radius: 8px;
  background-color: #00b143;
}

.entrar:disabled {
  background-color: #7fd7a1;
  cursor: not-allowed;
}
</style>