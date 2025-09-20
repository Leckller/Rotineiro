/* eslint-disable */
declare module '*.vue' {
  import type { DefineComponent } from 'vue'
  const component: DefineComponent<{}, {}, any>
  export default component
}

/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_API_URL: string
  // pode adicionar mais variáveis aqui
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}
