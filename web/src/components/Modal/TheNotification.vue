<template>
  <div class="notifications">
    <TransitionGroup name="notif" tag="div">
      <div class="card-notification" :class="getNotificationColorClass(not.type)" v-for="not in getNotifications()"
        :key="not.id">
        <div class="card-header">
          <p>{{ not.title }}</p>
          <button @click="closeNotificiation(not.id)">
            <FontAwesomeIcon icon="xmark" />
          </button>
        </div>
        <p class="description">{{ not.description }}</p>
      </div>
    </TransitionGroup>
  </div>
</template>

<script>
import { useNotificationStore } from "@/stores/notification";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

export default {
  name: "TheNotification",
  components: {
    FontAwesomeIcon,
  },
  data() {
    return {
      notificationStore: useNotificationStore(),
    };
  },
  methods: {
    getNotificationColorClass(type) {
      switch (type) {
        case "important":
          return "important-color";
        case "error":
          return "error-color";
        default:
          return "default-color";
      }
    },
    getNotifications() {
      return this.notificationStore.notifications;
    },
    closeNotificiation(notificationId) {
      this.notificationStore.closeNotification(notificationId);
    },
  },
};
</script>

<style scoped>
.default-color {
  background-color: rgb(164, 211, 255);
}

.error-color {
  background-color: rgb(255, 230, 207);
}

.important-color {
  background-color: rgb(255, 255, 198);
}

.notifications p {
  padding: 0;
  margin: 0;
}

.notifications {
  position: fixed;
  top: 50px;
  right: auto;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1002;
  flex-direction: column;
}

@media (max-width: 400px) {
  .card-notification {
    max-width: 300px !important;
  }
}

.card-notification {
  display: flex;
  margin: 8px 0;
  min-width: 300px;
  max-width: 400px;
  padding: 16px;
  border-radius: 8px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.15);
}

.card-header {
  display: flex;
  gap: 16px;
  justify-content: space-between;
  width: 100%;
  font-size: large;
}

.description {
  font-size: medium;
}

.card-header button {
  background-color: transparent;
  border: none;
  cursor: pointer;
  display: flex;
  height: 25px;
  align-items: center;
}

/* ANIMAÇÕES */
.notif-enter-active,
.notif-leave-active {
  transition: all 0.4s ease;
}

.notif-enter-from {
  opacity: 0;
  transform: translateY(-20px) scale(0.9);
}

.notif-enter-to {
  opacity: 1;
  transform: translateY(0) scale(1);
}

.notif-leave-from {
  opacity: 1;
  transform: translateY(0) scale(1);
}

.notif-leave-to {
  opacity: 0;
  transform: translateY(-20px) scale(0.9);
}
</style>
