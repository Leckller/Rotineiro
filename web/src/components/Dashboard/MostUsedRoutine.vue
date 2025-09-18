<template>
  <article>
    <p>
      <FontAwesomeIcon icon="trophy" />
      Rotina mais Repetida
    </p>

    <div>chart</div>

    <div>
      <div>
        <span />
        <p>{{ routine }}</p>
        <small
          >{{ quantity }} {{ quantity > 1 ? "vezes" : "vez" }} entre
          {{ start }} e {{ end }}</small
        >
      </div>
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