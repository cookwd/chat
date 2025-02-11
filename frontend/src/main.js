import { createApp } from 'vue';
import App from './App.vue';
import store from './store';
import router from './router';

const app = createApp(App);

// 在应用启动时检查并加载用户信息
async function loadUserInfo() {
    const user = localStorage.getItem('user');
    const token = localStorage.getItem('token');

    if (user && token) {
        try {
            // 如果用户信息存在，则通过 Vuex 加载
            await store.dispatch('login', {
                user: JSON.parse(user),  // 将 user 从 JSON 字符串转为对象
                token: token,
            });
        } catch (error) {
            console.error('Failed to load user info from localStorage:', error);
        }
    }
}

loadUserInfo().then(() => {
    app.use(store).use(router).mount('#app');  // 等待加载完用户信息后再挂载应用
});
