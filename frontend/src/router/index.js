import { createRouter, createWebHistory } from 'vue-router'
import BaseView from '@/views/BaseView.vue'
//import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: BaseView
    }
  ]
})

export default router
