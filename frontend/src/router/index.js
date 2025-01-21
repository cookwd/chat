import { createRouter, createWebHistory } from 'vue-router';
import ChatRoom from '../views/ChatRoom.vue'; // 导入 ChatRoom 组件

// 定义路由规则
const routes = [
    {
        path: '/',
        redirect: '/chatRoom',  // 默认重定向到 ChatRoom 页面
    },
    {
        path: '/chatRoom',
        name: 'ChatRoom',
        component: ChatRoom,  // 引入 ChatRoom 组件
    },
];

// 创建路由实例
const router = createRouter({
    history: createWebHistory(), // 使用 HTML5 History 模式
    routes, // 设置路由规则
});

export default router;
