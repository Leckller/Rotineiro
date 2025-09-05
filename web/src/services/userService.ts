import api, { DefaultResponse } from "./api"
import { RoutineEntity } from "./routineService"

export type LoginRequest = {
  email: string,
  password: string
}

export type LoginResponse = DefaultResponse<{ token: string }>

export type RegisterRequest = {
  name: string,
  username: string,
  email: string,
  password: string
}

export type RegisterResponse = DefaultResponse<{ token: string }>

export type getActualRoutineResponse = DefaultResponse<RoutineEntity>

export const UserService = {
  async refreshToken() {
    // api.post("", )
  },
  async login(loginRequest: LoginRequest): Promise<LoginResponse> {
    const { data } = await api.post("auth/login", loginRequest);
    localStorage.setItem("rotineiro_access_token", (data as LoginResponse).response.token)
    return data as LoginResponse
  },
  async register(registerRequest: RegisterRequest): Promise<RegisterResponse> {
    const { data } = await api.post("user", registerRequest)
    localStorage.setItem("rotineiro_access_token", (data as LoginResponse).response.token)
    return data as RegisterResponse
  },
  async getActualRoutine(): Promise<getActualRoutineResponse> {
    const { data } = await api.get("user/actualRoutine")
    return data as getActualRoutineResponse
  }

}