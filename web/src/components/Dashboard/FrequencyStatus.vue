<template>
  <article class="frq-status">
    <FontAwesomeIcon icon="rocket" />
    <p>Frequência de uso</p>
    <p>Entre {{ useDate.startDate.toLocaleDateString() }} e {{ useDate.endDate.toLocaleDateString() }}</p>
  </article>
</template>

<script>
import { useDateStore } from "@/stores/date";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { defineComponent } from "vue";

export default defineComponent({
  name: "FrequencyStatus",
  components: {
    FontAwesomeIcon,
  },
  data() {
    return {
      useDate: useDateStore(),
    };
  },
});
</script>

<style scoped>
.frq-status {
  display: flex;
  gap: 16px;
  flex-direction: column;
  width: 100%;
  border: solid 1px lightgray;
  padding: 16px;
  border-radius: 8px;
  align-items: center;
  max-width: 320px;
}
</style>
