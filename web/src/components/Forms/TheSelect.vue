<template>
  <label>
    {{ label }}
    <select :value="modelValue" @change="$emit('update:modelValue', ($event.target as HTMLInputElement).value)">
      <option v-for="(option, i) in options" :key="i" :value="option.value">
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
