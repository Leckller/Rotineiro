<template>
  <label class="label">
    {{ label }}
    <input :required="required" :minlength="min" class="input" type="text" :value="modelValue"
      @input="$emit('update:modelValue', ($event.target as HTMLInputElement).value)" />
  </label>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "TheInput",
  props: {
    modelValue: {
      type: String,
      required: true,
    },
    label: {
      type: String,
      default: "",
    },
    min: {
      type: Number,
      default: 1,
    },
    required: {
      type: Boolean,
      default: true,
    },
  },
  emits: {
    "update:modelValue": (value: string) => typeof value === "string",
  },
});
</script>


<style scoped>
.label {
  display: flex;
  flex-direction: column;
}
</style>