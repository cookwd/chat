<!-- 修改文件名为 ChatRoom.vue -->
<template>
  <div>
    <h1>Welcome to the Chat Room</h1>
    <div id="chat-box"></div>
    <input v-model="message" placeholder="Type a message" />
    <button @click="sendMessage">Send</button>
  </div>
</template>

<script>
import axios from 'axios';
// 修改组件名称为 ChatRoom
export default {
  name: 'ChatRoom',
  data() {
    return {
      message: '',
    };
  },
  methods: {
    sendMessage() {
      // 发送请求时可以使用 axios
      axios.post('/sendMessage', { message: this.message })
          .then(response => {
            console.log('Message sent:', response);
          })
          .catch(error => {
            console.error('Error sending message:', error);
          });

      this.message = ''; // 发送后清空输入框
    },
  },
};
</script>

<style scoped>
#chat-box {
  border: 1px solid #ccc;
  height: 300px;
  overflow-y: auto;
  margin-bottom: 10px;
}
input {
  width: 80%;
  padding: 5px;
}
button {
  padding: 5px 10px;
}
</style>
