<template>
  <article class="most-used-box">
    <p class="most-used-title">
      <FontAwesomeIcon :style="{color: 'goldenrod'}" icon="trophy" />
      Rotina mais Repetida
    </p>

    <div class="most-used-text">
      <div>
        <h4>{{ routine }}</h4>
        <small
          >{{ quantity }} {{ quantity > 1 ? "vezes" : "vez" }} entre
          {{ start }} e {{ end }}</small
        >
      </div>
      <FontAwesomeIcon icon="book" />
    </div>
  </article>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { defineComponent } from "vue";

export default defineComponent({
  name: "MostUsedRoutine",
  components: { FontAwesomeIcon },
  props: {
    routine: {
      type: String,
      required: true,
      default: "",
    },
    start: {
      type: Date,
      default: () => {
        const date = new Date();
        date.setMonth(date.getMonth() - 1); // subtrai 1 mês
        return date.toLocaleDateString();
      },
      required: true,
    },
    end: {
      type: Date,
      default: () => {
        const date = new Date();
        return date.toLocaleDateString();
      },
      required: true,
    },
    quantity: {
      type: Number,
      required: true,
      default: 0,
    },
  },
});
</script>

<style>

.most-used-box {
  display: flex;
  gap: 16px;
  flex-direction: column;
  width: 100%;
  border: solid 1px lightgray;
  padding: 16px;
  border-radius: 8px;
  max-width: 320px;
}

.most-used-title {
  display: flex;
  width: 100%;
  gap: 8px;
}

.most-used-text {
  display: flex;
  align-items: end;
  justify-content: space-between;
}

.most-used-text svg {
  font-size: 20px;
}

.most-used-text div {
  display: flex;
  flex-direction: column;
  justify-content: start;
  text-align: start;
}

</style>