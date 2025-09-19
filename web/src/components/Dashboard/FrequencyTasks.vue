<template>
  <article class="frequency-tasks">
    <h3>Tarefas por Dia</h3>
    <Bar id="my-chart-id" :options="chartOptions" :data="chartData" />
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
.frequency-tasks {
  text-align: start;
  width: 100%;
  max-width: 1300px !important;
  height: 300px;
  display: flex;
  gap: 16px;
  flex-direction: column;
  width: 100%;
  border: solid 1px lightgray;
  padding: 16px 16px 46px 16px;
  border-radius: 8px;
  max-width: 320px;
}
</style>