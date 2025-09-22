import { defineStore } from "pinia";

export const useDateStore = defineStore("date", {
    state: (): { startDate: Date, endDate: Date } => {
        const startDate = new Date();
        startDate.setMonth(startDate.getMonth() - 1); // mês passado
        const endDate = new Date();
        return { startDate, endDate };
    },
    actions: {
        setStartDate(newDate: Date) {
            this.startDate = newDate;
        },
        setEndDate(newDate: Date) {
            this.endDate = newDate;
        },
    }
})