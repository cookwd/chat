import { createStore } from 'vuex';

export default createStore({
    state: {
        user: null,  // 默认用户为空
    },
    mutations: {
        setUser(state, user) {
            state.user = user;  // 更新用户信息
        },
        clearUser(state) {
            state.user = null;  // 清除用户信息
        }
    },
    actions: {
        login({ commit }, user) {
            commit('setUser', user);  // 用户登录，存储信息
        },
        logout({ commit }) {
            commit('clearUser');  // 用户登出，清除信息
        }
    },
    getters: {
        user: (state) => state.user,  // 获取当前用户
    }
});
