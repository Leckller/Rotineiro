import { defineStore } from "pinia";

export enum NotificationEnum {
    default, error, important
}

export type NotificationType = {
    id?: number, time?: number, title: string, description?: string, type?: NotificationEnum,
}

export const useNotificationStore = defineStore("notification", {
    state: (): { notifications: NotificationType[] } => (
        // { title: "", description: "", open: false }
        { notifications: [] }
    ),
    actions: {
        // types: default, important, error
        createNotification({time = 1000, title = "", description = "", type = NotificationEnum.default}: NotificationType) {
            const notificationId = Math.floor(Math.random() * 100000)
            this.notifications.push({ id: notificationId, title, description, type })
            setTimeout(() => {
                this.notifications = this.notifications.filter((n) => n.id != notificationId)
            }, time);
        },
        closeNotification(notificationId: number) {
            this.notifications = this.notifications.filter((n) => n.id != notificationId)
        }
    },
})