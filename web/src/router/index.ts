import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import DashboardView from '@/views/DashboardView.vue'
import LoginView from '@/views/LoginView.vue'
import EditRoutineView from '@/views/EditRoutineView.vue'
import RoutineView from '@/views/RoutineView.vue'
import RegisterView from '@/views/RegisterView.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'login',
    component: LoginView
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/home',
    name: 'home',
    component: HomeView
  },
  {
    path: '/routine',
    name: 'rotina',
    component: RoutineView
  },
  {
    path: '/routine/:id',
    name: 'editRotina',
    component: EditRoutineView
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: DashboardView
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach(async (to) => {
  const isAuthenticated = localStorage.getItem("rotineiro_access_token");
  if (
    !isAuthenticated &&
    to.name !== 'login' && to.name !== 'register'
  ) {
    return { name: 'login' }
  }
})

export default router
