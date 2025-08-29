// api.ts
import axios, { AxiosError } from "axios";
import { UserService } from "./userService";

const api = axios.create({
  baseURL: "http://localhost:8095/",
  timeout: 5000,
  headers: { "Content-Type": "application/json" },
});

export type DefaultResponse<T> = {
  message: string,
  response: T
}

// Mensagens centralizadas
const errorMessages: Record<number, string> = {
  400: "Bad Request",
  401: "Unauthorized",
  403: "Forbidden",
  404: "Not Found",
  500: "Internal Server Error",
};

// Interceptor para adicionar token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("rotineiro_access_token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Interceptor de resposta
api.interceptors.response.use(
  (response) => response,
  async (error: AxiosError) => {
    const status = error.response?.status;

    if (status) {
      const message = errorMessages[status] || "Unexpected Error";

      // Exibir de forma padronizada (pode trocar alert por toast, logger, etc.)
      console.error(`API Error [${status}]: ${message}`);
      alert(message);

      // Caso seja 401, tenta renovar o token e refazer a request
      if (status === 401 && error.config) {
        try {
          await UserService.refreshToken();
          return api(error.config); // Reenvia a requisição original
        } catch (refreshError) {
          console.error("Token refresh failed", refreshError);
          throw refreshError;
        }
      }
    }

    return Promise.reject(error);
  }
);

export default api;
