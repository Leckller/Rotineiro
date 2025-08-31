import { defineStore } from "pinia";

export const useModalStore = defineStore("modals", {
    state: () => ({ actualModalName: "", open: false }),
    actions: {
        closeModal() {
            this.open = false
        },
        openAndSetModal(name: string) {
            this.open = true
            this.actualModalName = name
        },
        toggleOpenModal() {
            this.open = !this.open
        },
        setActualModalName(name: string) {
            this.actualModalName = name
        }
    },
})