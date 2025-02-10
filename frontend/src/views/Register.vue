<template>
  <div class="register-page">
    <div class="register-container">
<!--      <div class="register-logo">-->
<!--        &lt;!&ndash; 请替换为你自己的 logo 图片路径 &ndash;&gt;-->
<!--        <img src="/images/qq-logo.png" alt="Logo" />-->
<!--      </div>-->
      <h2>用户注册</h2>
      <form @submit.prevent="register">
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
        <button type="submit" class="register-button">注册</button>
      </form>
      <div class="register-options">
        <router-link to="/login">已有注册账号？</router-link>
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
    async register() {
      try {
        const response = await axios.post('http://localhost:8080/api/users/register', {
          username: this.username,
          password: this.password
        });

        // 注册成功后跳转到登录页
        alert('Registration successful!');
        this.$router.push('/login');
      } catch (error) {
        console.error('Registration failed:', error);
        alert('Registration failed!');
      }
    }
  }
};
</script>

<style scoped>
/* 整个页面居中显示，带有背景图片 */
.register-page {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  /* 请替换为你自己的背景图片路径
  background: url('/images/qq-background.jpg') no-repeat center center;*/
  background-size: cover;
  padding: 20px;
}

/* 注册容器样式 */
.register-container {
  background: rgba(255, 255, 255, 0.95);
  padding: 30px;
  width: 100%;
  max-width: 400px;
  border-radius: 8px;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
  text-align: center;
}

/* Logo 样式 */
.register-logo img {
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

/* 注册按钮样式 */
.register-button {
  width: 100%;
  padding: 12px;
  background-color: #007aff;
  border: none;
  border-radius: 4px;
  color: #fff;
  font-size: 16px;
  cursor: pointer;
}

.register-button:hover {
  background-color: #005bb5;
}

/* 辅助链接样式 */
.register-options {
  margin-top: 15px;
}

.register-options a,
.register-options router-link {
  margin: 0 10px;
  color: #007aff;
  text-decoration: none;
  font-size: 14px;
}

.register-options a:hover,
.register-options router-link:hover {
  text-decoration: underline;
}

/* 响应式调整 */
@media (max-width: 768px) {
  .register-container {
    max-width: 90%;
  }
}
</style>
