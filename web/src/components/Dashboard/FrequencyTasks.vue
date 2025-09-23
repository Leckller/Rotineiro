<template>
  <article class="frequency-tasks">
    <h3>Tarefas por Dia</h3>
    <p class="empty" v-if="frequency.length <= 0">
      Você ainda não concluiu uma Tarefa 😕
    </p>
    <Bar
      v-if="frequency.length > 0"
      :options="chartOptions"
      :data="chartData"
    />
  </article>
</template>

<script lang="ts">
import { Bar } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from "chart.js";
import { defineComponent, PropType, computed } from "vue";
import { DayUse } from "@/services/historyService";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
);

export default defineComponent({
  name: "FrequencyTasks",
  components: { Bar },
  props: {
    frequency: {
      type: Array as PropType<DayUse[]>,
      required: true,
    },
  },
  setup(props) {
    const chartData = computed(() => ({
      labels: props.frequency.map((f) =>
        new Date(f.date).toLocaleDateString("pt-BR", { weekday: "short" })
      ),
      datasets: [
        {
          label: "Tarefas Feitas",
          data: props.frequency.map((f) => f.entities.length),
          backgroundColor: "rgba(66, 165, 245, 0.8)", // azul suave
          borderRadius: 6, // arredondado
          barThickness: 30, // largura da barra
          maxBarThickness: 40, // limite máximo
        },
      ],
    }));

    const chartOptions = {
      responsive: true,
      maintainAspectRatio: false, // permite controlar altura pelo CSS
      plugins: {
        legend: { display: false },
      },
      scales: {
        x: {
          grid: { display: false },
        },
        y: {
          beginAtZero: true,
          ticks: { stepSize: 2 },
          grid: { color: "#eee" },
        },
      },
    };

    return { chartData, chartOptions };
  },
});
</script>

<style scoped>
.empty {
  padding: 8px 0;
  white-space: nowrap;
}
.frequency-tasks {
  width: 100%;
  max-width: 320px;
  max-height: 350px;
  border: solid 1px lightgray;
  border-radius: 8px;
  padding: 16px;
  text-align: start;
}
</style>