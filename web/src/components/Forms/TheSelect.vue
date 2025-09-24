<template>
  <label class="select-label">
    {{ label }}
    <select
      class="select"
      :value="modelValue"
      @change="$emit('update:modelValue', ($event.target as HTMLSelectElement).value)"
    >
      <option
        v-for="(option, i) in options"
        :key="i"
        :value="option.value"
      >
        {{ option.name }}
      </option>
    </select>
  </label>
</template>

<script lang="ts">
import { defineComponent, PropType } from 'vue'

interface Option {
  name: string
  value: string | number
}

export default defineComponent({
  name: "TheSelect",
  props: {
    options: {
      type: Array as PropType<Option[]>,
      required: true,
    },
    label: {
      type: String,
      default: "",
    },
    modelValue: {
      type: [String, Number],
      default: "",
    },
  },
  emits: ["update:modelValue"],
})
</script>

<style scoped>
.select-label {
  display: flex;
  flex-direction: column;
  font-size: 0.95rem;
  font-weight: 500;
  color: #333;
  margin-bottom: 0.75rem;
}

.select {
  padding: 0.5rem 0.75rem;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 0.95rem;
  outline: none;
  transition: all 0.2s ease;
  background: white;
  cursor: pointer;
  box-shadow: 0 1px 3px rgba(0,0,0,0.05);
}

.select:focus {
  border-color: #007bff;
  box-shadow: 0 0 0 3px rgba(0,123,255,0.2);
}

.select option {
  padding: 0.5rem;
}
</style>
