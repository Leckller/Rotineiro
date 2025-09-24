<script lang="ts">
import { defineComponent } from "vue";
import TheButton from "../Forms/TheButton.vue";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

export default defineComponent({
  name: "HomeRoutineEmpty",
  components: {
    TheButton,
    FontAwesomeIcon,
  },
});
</script>

<template>
  <section id="routine-empty">
    <div class="empty-section">
      <img class="img" src="../../assets/book.jpg" alt="livro" />
      <p :style="{ fontSize: 'var(--large)' }">
        Pronto para organizar seu dia?
      </p>
      <p>
        <small
          >Comece criando sua rotina personalizada para aumentar sua
          produtividade!</small
        >
      </p>
      <article class="empty-help">
        <FontAwesomeIcon class="help-icon" icon="bullseye" />
        <div class="empty-help-text">
          <p><strong>Crie sua primeira rotina</strong></p>
          <p>Organize suas atividades do dia</p>
        </div>
      </article>

      <TheButton @click="() => $router.push('/routine')">
        <FontAwesomeIcon icon="plus" />
        Escolher Rotina
      </TheButton>

      <span :style="{ width: '100%', borderTop: 'solid 1px var(--light-gray)' }" />

      <small
        :style="{ fontSize: 'var(--small)' }"
      >
        "O primeiro passo para organizar sua vida é organizar seu dia."
      </small>
    </div>
    <div class="img-desktop-content">
      <img class="img-desktop" src="../../assets/book.jpg" alt="livro" />
    </div>
  </section>
</template>

<style scoped>
#routine-empty {
  display: flex;
  width: 100%;
  gap: var(--gap-giant);
  align-items: start;
}

@media (min-width: 900px) {
  .img {
    display: none !important;
  }
}

@media (max-width: 900px) {
  .img-desktop {
    display: none !important;
  }
}
.img-desktop {
  width: 300px !important;
  object-position: -60px center !important;
}

.img,
.img-desktop {
  width: 150px;
  aspect-ratio: 1 / 1;
  object-position: -30px center;
  object-fit: cover;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.5);
}

.img-desktop-content {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  max-width: 400px;
}

.empty-help {
  display: flex;
  font-size: small;
  align-items: center;
  padding: 16px;
  border: solid 1px oklch(0.809 0.105 251.813);
  gap: 8px;
  border-radius: 8px;
  background-color: oklch(0.97 0.014 254.604);
}

.help-icon {
  background-color: oklch(0.546 0.245 262.881);
  color: white;
  padding: 8px;
  height: 15px;
  width: 15px;
  border-radius: 999px;
}

.empty-help-text {
  display: flex;
  flex-direction: column;
  align-items: start;
  gap: 4px;
}

.empty-section {
  display: flex;
  flex-direction: column;
  align-items: start;
  text-align: start;
  width: 100%;
  max-width: 400px;
  gap: var(--gap-large);
}
</style>