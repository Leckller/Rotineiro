import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import RotinaView from '@/views/RotinaView.vue'
import CalendarioView from '@/views/CalendarioView.vue'
import DashboardView from '@/views/DashboardView.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/',
    name: 'rotina',
    component: RotinaView
  },
  {
    path: '/',
    name: 'calendario',
    component: CalendarioView
  },
  {
    path: '/',
    name: 'dashboard',
    component: DashboardView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
