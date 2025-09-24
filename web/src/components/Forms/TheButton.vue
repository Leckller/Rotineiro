<template>
  <button
    :type="type"
    :disabled="disabled"
    :class="['btn', variant, `align-${align}`, sizeClass]"
    @click="$emit('click', $event)"
  >
    <slot />
  </button>
</template>

<script lang="ts">
import { defineComponent, computed } from "vue";

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
      default: "primary", // primary, secondary, tertiary, danger, etc
    },
    align: {
      type: String as () => "start" | "center" | "end",
      default: "center",
      validator: (value: string) => ["start", "center", "end"].includes(value),
    },
    size: {
      type: String as () => "full" | "medium" | "",
      default: "",
      validator: (value: string) => ["full", "medium", ""].includes(value),
    },
  },
  setup(props) {
    const sizeClass = computed(() => {
      return props.size ? `btn-${props.size}` : "";
    });
    return { sizeClass };
  },
});
</script>

<style scoped>
.btn {
  padding: var(--padding-small);
  border-radius: var(--padding-small);
  width: auto;
  max-width: 300px;
  gap: var(--gap-small);
  color: var(--secondary-text-color);
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

/* Tamanhos */
.btn-full {
  width: 100%;
}
.btn-medium {
  width: 40%;
}

/* Variantes */
.btn.primary {
  background-color: var(--medium-2-blue);
  color: white;
}
.btn.primary:hover {
  background-color: var(--strong-blue);
}

.btn.secondary {
  background-color: #e5e7eb;
  color: #111827;
}
.btn.secondary:hover {
  background-color: #d1d5db;
}

.btn.tertiary {
  background-color: transparent;
  color: #111827;
}
.btn.tertiary:hover {
  background-color: var(--light-gray);
}

.btn.danger {
  background-color: #ef4444;
  color: white;
}
.btn.danger:hover {
  background-color: #b91c1c;
}

.btn:hover:disabled {
  background-color: transparent;
}

.btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
</style>
