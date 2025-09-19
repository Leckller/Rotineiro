import axios, { AxiosError } from "axios";
import { NotificationEnum, useNotificationStore } from "@/stores/notification";

const api = axios.create({
  baseURL: "http://localhost:8095/",
  timeout: 5000,
  headers: { "Content-Type": "application/json" },
});

export type DefaultResponse<T> = {
  message: string;
  response: T;
};

// Adiciona token automaticamente
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("rotineiro_access_token");
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

// Trata respostas/erros
api.interceptors.response.use(
  (res) => res,
  async (err: AxiosError) => {

    if (!err.response) {
      console.error("Network Error:", err.message);
      useNotificationStore().createNotification({
        time: 2500,
        title: "Não foi possível conectar ao servidor. Verifique sua conexão.",
        type: NotificationEnum.error
      })
      return Promise.reject(err);
    }

    const status = err.response?.status;
    if (!status) return Promise.reject(err);

    if (status === 401 || status === 403) {
      localStorage.removeItem("rotineiro_access_token")
    }

    return Promise.reject(err);
  }
);

export default api;
