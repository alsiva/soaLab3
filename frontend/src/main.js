import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { UrlService } from './scripts/urlService'

const app = createApp(App)
const base_url = "http://localhost:8080"
const first_service = "http://localhost:81/firstService"
const second_service = "http://localhost:82/secondService/api/killer"
//const first_service = "/firstService"
//const second_service = "/secondService/api/killer"
const urlService = new UrlService(base_url)
const urlServiceFirst = new UrlService(first_service)
const urlServiceSecond = new UrlService(second_service)

app.use(router)

app.mount('#app')

export {urlService, urlServiceFirst, urlServiceSecond};
