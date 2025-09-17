<template>
  <article
    class="small-card"
    :style="{
      border: `rgba(${hexToRgb(color)}, 0.3) solid 1px`,
      backgroundColor: `rgba(${hexToRgb(color)}, 0.1)`,
      color: color,
    }"
  >
    <div class="icon-and-value">
      <FontAwesomeIcon :icon="icon" />
      <p>{{ value }}</p>
    </div>
    <p class="text">
      {{ text }}
    </p>
  </article>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { defineComponent } from "vue";

export default defineComponent({
  name: "SmallCard",
  components: {
    FontAwesomeIcon,
  },
  props: {
    icon: {
      type: String,
      required: true,
      default: "",
    },
    text: {
      type: String,
      required: true,
    },
    value: {
      type: String,
      required: true,
    },
    color: {
      type: String,
      required: false,
      default: "#0000FF",
    },
  },
  methods: {
    hexToRgb(hex) {
      hex = hex.replace(/^#/, "");
      if (hex.length === 3) {
        hex = hex
          .split("")
          .map((c) => c + c)
          .join("");
      }
      const bigint = parseInt(hex, 16);
      const r = (bigint >> 16) & 255;
      const g = (bigint >> 8) & 255;
      const b = bigint & 255;
      return `${r}, ${g}, ${b}`;
    },
  },
});
</script>

<style scoped>
.small-card {
  flex-grow: 1;
  padding: 16px;
  width: 100%;
  max-width: 150px;
  white-space: nowrap;
  border-radius: 8px;
}

.icon-and-value {
  display: flex;
  align-content: center;
  justify-content: space-between;
}

.text {
  font-size: 14px;
  color: black;
}
</style>