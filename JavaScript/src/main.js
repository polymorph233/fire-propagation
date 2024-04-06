import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import Config from "@/Config.vue";
import GameDisplay from "@/GameDisplay.vue";
import {createMemoryHistory, createRouter} from "vue-router";


// Set up the routes
const routes = [
    { name: 'config', path: '/', component: Config },
    { name: 'game-page', path: '/gamepage', component: GameDisplay }
]

// Build the router
const router = createRouter({
    history: createMemoryHistory(),
    routes
})

createApp(App)
    .use(router)
    .mount('#app')
