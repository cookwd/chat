<template>
  <div class="login-page">
    <div class="login-container">
<!--      <div class="login-logo">-->
<!--        &lt;!&ndash; 请替换为你的 logo 图片路径 &ndash;&gt;-->
<!--        <img src="/images/qq-logo.png" alt="Logo" />-->
<!--      </div>-->
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
        <button type="submit" class="login-button">登录</button>
      </form>
      <div class="login-options">
        <!-- 使用 router-link 进行 SPA 内部路由跳转 -->
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
        // 假设后端返回的用户信息在 response.data 中
        this.$store.dispatch('login', response.data); // 登录后存储用户信息到 Vuex
        this.$router.push('/home');  // 登录成功后跳转到主页
      } catch (error) {
        console.error('Login failed:', error);
        alert('Login failed!');
      }
    },
    forgotPassword() {
      // 此处可实现忘记密码的逻辑
      alert("Forgot password functionality is not implemented yet.");
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
  /* 请替换为你自己的背景图片路径
  background: url('/images/qq-background.jpg') no-repeat center center;*/
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

/* Logo 样式 */
.login-logo img {
  width: 80px;
  margin-bottom: 20px;
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
