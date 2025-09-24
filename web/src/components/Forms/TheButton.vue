<template>
  <button
    :type="type"
    :disabled="disabled"
    :class="['btn', variant, `align-${align}`]"
    @click="$emit('click', $event)"
  >
    <slot />
  </button>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "TheButton",
  props: {
    type: {
      type: String as () => "button" | "submit" | "reset",
      default: "button",
      validator: (value: string) =>
        ["button", "submit", "reset"].includes(value),
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    variant: {
      type: String,
      default: "primary", // primary, secondary, danger, etc
    },
    align: {
      type: String as () => "start" | "center" | "end",
      default: "center",
      validator: (value: string) => ["start", "center", "end"].includes(value),
    },
  },
});
</script>

<style scoped>
.btn {
  padding: var(--padding-small);
  border-radius: var(--padding-small);
  width: 100%;
  gap: var(--gap-small);
  color: var(--secondary-text-color);
  max-width: 300px;
  border: none;
  cursor: pointer;
  font-weight: 500;
  transition: background-color 0.2s;
  display: flex;
  align-items: center;
}

/* Alinhamento */
.align-start {
  justify-content: flex-start;
}
.align-center {
  justify-content: center;
}
.align-end {
  justify-content: flex-end;
}

.btn.primary {
  background-color: #3b82f6;
  color: white;
}
.btn.primary:hover {
  background-color: #2563eb;
}

.btn.secondary {
  background-color: #e5e7eb;
  color: #111827;
}
.btn.secondary:hover {
  background-color: #d1d5db;
}

.btn.danger {
  background-color: #ef4444;
  color: white;
}
.btn.danger:hover {
  background-color: #b91c1c;
}

.btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
</style>
