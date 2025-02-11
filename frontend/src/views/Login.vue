<template>
  <div class="login-page">
    <div class="login-container">
      <h2>用户登录</h2>
      <form @submit.prevent="login">
        <div class="input-group">
          <input
              v-model="username"
              type="text"
              placeholder="请输入用户名"
              required
          />
        </div>
        <div class="input-group">
          <input
              v-model="password"
              type="password"
              placeholder="请输入密码"
              required
          />
        </div>
        <button
            type="submit"
            class="login-button"
            :disabled="!username || !password">
          登录
        </button>
      </form>
      <div class="login-options">
        <router-link to="/register">注册</router-link>
        <a href="#" @click.prevent="forgotPassword">忘记密码（还没做）</a>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      username: '',
      password: '',
    };
  },
  methods: {
    async login() {
      try {
        const response = await axios.post('http://localhost:8080/api/users/login', {
          username: this.username,
          password: this.password
        });
        console.log('Login response:', response.data);
        if (response.data.status === 'success') {
          // 存储用户信息和 token 到 Vuex 和 localStorage
          this.$store.dispatch('login', response.data.user);  // 存储用户信息到 Vuex
          localStorage.setItem('token', response.data.token); // 将 token 存储在 localStorage
          console.log('User data:', response.data.user);
          console.log('JWT Token:', response.data.token);
          this.$router.push('/home');  // 登录成功后跳转到主页
        } else {
          alert('Invalid credentials!');
        }
      } catch (error) {
        console.error('Login failed:', error);
        if (error.response) {
          alert(`Error: ${error.response.data.message || 'Login failed'}`);
        } else {
          alert('Network error, please try again later.');
        }
      }
    },
    forgotPassword() {
      // 跳转到忘记密码页面或弹出提示
      this.$router.push('/forgot-password');  // 跳转到忘记密码页面
    }
  }
};
</script>

<style scoped>
/* 整个页面居中显示，带有背景图片 */
.login-page {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background-size: cover;
  padding: 20px;
}

/* 登录容器样式 */
.login-container {
  background: rgba(255, 255, 255, 0.95);
  padding: 30px;
  width: 100%;
  max-width: 400px;
  border-radius: 8px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
  text-align: center;
}

/* 标题样式 */
h2 {
  margin-bottom: 20px;
  color: #333;
}

/* 输入框容器 */
.input-group {
  margin-bottom: 15px;
}

/* 输入框样式 */
.input-group input {
  width: 100%;
  padding: 12px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

/* 登录按钮样式 */
.login-button {
  width: 100%;
  padding: 12px;
  background-color: #007aff;
  border: none;
  border-radius: 4px;
  color: #fff;
  font-size: 16px;
  cursor: pointer;
}

.login-button:hover {
  background-color: #005bb5;
  transition: background-color 0.3s ease;
}

/* 辅助链接样式 */
.login-options {
  margin-top: 15px;
}

.login-options a,
.login-options router-link {
  margin: 0 10px;
  color: #007aff;
  text-decoration: none;
  font-size: 14px;
}

.login-options a:hover,
.login-options router-link:hover {
  text-decoration: underline;
}

/* 响应式调整 */
@media (max-width: 768px) {
  .login-container {
    max-width: 90%;
  }
}
</style>
