import { createStore } from 'vuex';

export default createStore({
    state: {
        user: JSON.parse(localStorage.getItem('user')) || null,
        token: localStorage.getItem('token') || null,
    },
    mutations: {
        setUser(state, { user, token }) {
            state.user = user;
            state.token = token;
            // 存储用户信息和 token 到 localStorage
            localStorage.setItem('user', JSON.stringify(user));
            localStorage.setItem('token', token);
        },
        clearUser(state) {
            state.user = null;
            state.token = null;
            // 清除 localStorage 中的用户信息
            localStorage.removeItem('user');
            localStorage.removeItem('token');
        }
    },
    actions: {
        login({ commit }, { user, token }) {
            commit('setUser', { user, token });
        },
        logout({ commit }) {
            commit('clearUser');
        }
    },
    getters: {
        user: (state) => state.user,
        token: (state) => state.token,
    }
});
