<template>
  <div class="hello-content">
    <div>
      <h3>Olá! 👋</h3>
      <p>
        <small>{{ actualDate }}</small>
      </p>
    </div>
    <div class="logout">
      <TheButton
      @click="() => $router.push('/')"
      variant="tertiary"
    >
      <FontAwesomeIcon icon="right-from-bracket" />
    </TheButton>
    </div>
  </div>
</template>

<script lang="ts">
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { defineComponent } from "vue";
import TheButton from "../Forms/TheButton.vue";

export default defineComponent({
  name: "HomeRoutineHello",
  components: {
    FontAwesomeIcon,
    TheButton,
  },
  data() {
    return {
      actualDate: "",
    };
  },
  created() {
    this.getActualDate();
  },
  methods: {
    getActualDate() {
      const hoje = new Date();
      const opcoes = {
        weekday: "long" as const,
        year: "numeric" as const,
        month: "long" as const,
        day: "numeric" as const,
      };

      this.actualDate = hoje
        .toLocaleDateString("pt-BR", opcoes)
        .split(" ")
        .map((word) => word.charAt(0).toUpperCase() + word.slice(1))
        .join(" ");
    },
  },
});
</script>

<style scoped>
.hello-content {
  display: flex;
  justify-content: space-between;
  width: 100%;
  align-items: center;
}

.hello-content div {
  display: flex;
  align-items: start;
  flex-direction: column;
}

@media (min-width: 900px) {
  .logout {
    display: none !important;
  }
}

.hello-content h3 {
  font-size: var(--x-large);
  font-weight: 700;
}

.hello-content p {
  font-size: var(--medium);
}
</style>
