<template>
  <TheLayout>
    <div class="conteudo">
      <article class="logo">
        <span class="icone-principal">
          <FontAwesomeIcon icon="calendar" />
        </span>

        <h2>Rotineiro</h2>
        <p>Organize sua vida, alcance seus objetivos</p>
      </article>
      <form class="form" @submit="handleSubmit($event)">
        <h3>Entrar</h3>
        <p>Digite suas credenciais para acessar sua conta</p>

        <TheInput placeholder="seu@email.com" v-model="email" label="Email" />
        <TheInput
          placeholder="Sua Senha"
          type="Password"
          v-model="password"
          label="Senha"
        />

        <button
          class="entrar"
          :style="{
            backgroundColor:
              email.length > 6 && password.length > 8 ? '#1A65FE' : '#8BB0FE',
          }"
          type="submit"
        >
          Entrar
        </button>
        <button :style="{color: '#2B89FD'}" type="button" @click="handleToggleForm">
          Não possui uma conta? Cadastre-se
        </button>
      </form>
      <div class="lista-de-propaganda">
        <article class="propaganda">
          <span :style="{ backgroundColor: '#DBEAFE', color: '#155DFC' }">
            <FontAwesomeIcon icon="bullseye" />
          </span>
          <small>Metas Claras</small>
        </article>
        <article class="propaganda">
          <span :style="{ backgroundColor: '#DBFCE7', color: '#12AD4C' }">
            <FontAwesomeIcon icon="calendar" />
          </span>
          <small>Rotinas Organizadas</small>
        </article>
        <article class="propaganda">
          <span :style="{ backgroundColor: '#F3E8FF', color: '#9810FA' }">
            <FontAwesomeIcon icon="arrow-trend-up" />
          </span>
          <small>Progresso Visual</small>
        </article>
      </div>
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
    };
  },
  components: {
    TheLayout,
    TheInput,
    FontAwesomeIcon,
  },
  methods: {
    showMessage(notification: NotificationType) {
      this.useNotification.createNotification(notification);
    },
    async handleSubmit(e: Event) {
      e.preventDefault();

      try {
        await UserService.login({
          email: this.email,
          password: this.password,
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
      this.$router.push("/register");
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

.propaganda {
  display: flex;
  flex-direction: column;
  width: 33%;
  gap: 8px;
  align-items: center;
}

.propaganda span {
  width: 40px;
  height: 40px;
  display: flex;
  font-size: 24px;
  border-radius: 8px;
  justify-content: center;
  align-items: center;
}

.lista-de-propaganda {
  display: flex;
  gap: 16px;
  font-size: 14px;
}

.entrar {
  width: 100%;
  max-width: 300px;
  color: white;
  padding: 8px;
  border-radius: 8px;
}
</style>