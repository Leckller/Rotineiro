import { RoutineEntity } from "@/services/routineService";
import { defineStore } from "pinia";

export const useRoutineStore = defineStore("routine", {
  state: (): { routines: RoutineEntity[] } => ({ routines: [] }),
  actions: {
  },
})