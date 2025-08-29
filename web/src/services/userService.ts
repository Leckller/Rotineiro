import api, { DefaultResponse } from "./api"

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

export const UserService = {
  async refreshToken() {
    // api.post("", )
  },
  async login(loginRequest: LoginRequest): Promise<LoginResponse> {
    const { data } = await api.post("user", loginRequest);
    localStorage.setItem("rotineiro_access_token", (data as LoginResponse).response.token)
    return data as LoginResponse
  },
  async register(registerRequest: RegisterRequest): Promise<RegisterResponse> {
    const { data } = await api.post("auth/login", registerRequest)
    localStorage.setItem("rotineiro_access_token", (data as LoginResponse).response.token)
    return data as RegisterResponse
  },
}