import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import Login from '../views/Login.vue';
import Register from '../views/Register.vue';
import ChatRoom from '../views/ChatRoom.vue';

const routes = [
    { path: '/home', component: Home },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/chatroom/:id', component: ChatRoom, props: true },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

// 导航守卫：访问 `/` 时自动重定向到 `/login`
router.beforeEach((to, from, next) => {
    // 如果目标路径是 `/`，则重定向到 `/login`
    if (to.path === '/') {
        next('/login');
    } else {
        next();  // 否则继续导航
    }
});

export default router;
