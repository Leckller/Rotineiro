<template>
  <label class="label">
    {{ label }}
    <div class="campos">
      <input
        :required="required"
        :placeholder="placeholder"
        :minlength="min"
        class="input"
        :type="currentType"
        :value="modelValue"
        @input="$emit('update:modelValue', ($event.target as HTMLInputElement).value)"
      />
      <button v-if="type === 'Password'" type="button" @click="toggleType">
        <FontAwesomeIcon
          :icon="currentType === 'Password' ? 'eye' : 'eye-slash'"
        />
      </button>
    </div>
  </label>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

export default defineComponent({
  name: "TheInput",
  components: { FontAwesomeIcon },
  props: {
    modelValue: {
      type: String,
      required: true,
    },
    placeholder: {
      type: String,
      default: "",
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
    type: {
      type: String,
      default: "text", // minúsculo
    },
  },
  emits: {
    "update:modelValue": (value: string) => typeof value === "string",
  },
  setup(props) {
    const currentType = ref(props.type);

    function toggleType() {
      currentType.value =
        currentType.value === "Password" ? "text" : "Password";
    }

    return { currentType, toggleType };
  },
});
</script>

<style scoped>
.label {
  display: flex;
  flex-direction: column;
  text-align: start;
  gap: 4px;
  width: 100%;
  max-width: 300px;
}

.input {
  outline: none;
  border: none;
  width: 100%;
  background-color: transparent;
}

.campos {
  display: flex;
  padding: 8px;
  border-radius: 8px;
  background-color: #f3f3f5;
  display: flex;
  justify-content: space-between;
  width: 100%;
}

</style>
