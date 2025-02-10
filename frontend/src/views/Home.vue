<template>
  <div class="chat-dashboard">
    <!-- 左侧边栏：私聊用户和聊天室列表 -->
    <div class="sidebar">
      <div class="section">
        <h3>私聊</h3>
        <ul>
          <li
              v-for="user in users"
              :key="user.id"
              :class="{ active: selectedChat && selectedChat.type === 'private' && selectedChat.id === user.id }"
              @click="selectPrivateChat(user)"
          >
            {{ user.name }}
          </li>
        </ul>
      </div>
      <div class="section">
        <h3>聊天室</h3>
        <ul>
          <li
              v-for="room in chatrooms"
              :key="room.id"
              :class="{ active: selectedChat && selectedChat.type === 'room' && selectedChat.id === room.id }"
              @click="selectChatRoom(room)"
          >
            {{ room.name }}
          </li>
        </ul>
      </div>
    </div>

    <!-- 右侧聊天窗口 -->
    <div class="chat-window">
      <div class="chat-header">
        <h2 v-if="selectedChat">
          <!-- 根据会话类型显示标题 -->
          {{ selectedChat.type === 'private' ? '与 ' + selectedChat.name + ' 的私聊' : '聊天室: ' + selectedChat.name }}
        </h2>
        <h2 v-else>请选择聊天对象或聊天室</h2>
      </div>

      <div class="chat-messages">
        <div v-for="(msg, index) in messages" :key="index" class="message">
          <span class="sender">{{ msg.sender }}:</span>
          <span class="text">{{ msg.text }}</span>
        </div>
      </div>

      <div class="chat-input" v-if="selectedChat">
        <input
            type="text"
            v-model="newMessage"
            @keyup.enter="sendMessage"
            placeholder="输入消息..."
        />
        <button @click="sendMessage">发送</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ChatDashboard',
  data() {
    return {
      // 模拟在线用户数据，实际数据可通过 API/WebSocket 获取
      users: [
        { id: 1, name: 'Alice' },
        { id: 2, name: 'Bob' },
        { id: 3, name: 'Charlie' }
      ],
      // 模拟聊天室数据
      chatrooms: [
        { id: 101, name: '综合聊天室' },
        { id: 102, name: '体育聊天室' },
        { id: 103, name: '科技聊天室' }
      ],
      // 当前选中的聊天会话：{ type: 'private' 或 'room', id, name }
      selectedChat: null,
      // 当前会话中的消息（示例数据）
      messages: [],
      // 输入框中的新消息
      newMessage: ''
    };
  },
  methods: {
    // 选择私聊用户
    selectPrivateChat(user) {
      this.selectedChat = { type: 'private', id: user.id, name: user.name };
      // 清空聊天记录（实际应用中应加载对应聊天记录）
      this.messages = [
        { sender: '系统', text: '开始与 ' + user.name + ' 的私聊。' }
      ];
    },
    // 选择聊天室
    selectChatRoom(room) {
      this.selectedChat = { type: 'room', id: room.id, name: room.name };
      // 清空聊天记录（实际应用中应加载聊天室历史消息）
      this.messages = [
        { sender: '系统', text: '您已加入 ' + room.name + ' 聊天室。' }
      ];
    },
    // 发送消息
    sendMessage() {
      if (!this.newMessage.trim()) return;
      // 添加自己的消息
      this.messages.push({
        sender: '我',
        text: this.newMessage
      });
      this.newMessage = '';
      // 模拟滚动到底部
      this.$nextTick(() => {
        const chatMessages = this.$el.querySelector('.chat-messages');
        chatMessages.scrollTop = chatMessages.scrollHeight;
      });
      // 在实际应用中，此处应通过 WebSocket 或 API 将消息发送给后端
    }
  }
};
</script>

<style scoped>
.chat-dashboard {
  display: flex;
  height: 100vh;
  font-family: Arial, sans-serif;
}

/* 左侧边栏 */
.sidebar {
  width: 250px;
  background-color: #f5f5f5;
  padding: 20px;
  overflow-y: auto;
  border-right: 1px solid #ddd;
}

.sidebar .section {
  margin-bottom: 30px;
}

.sidebar h3 {
  margin-top: 0;
  margin-bottom: 10px;
  font-size: 18px;
  color: #333;
}

.sidebar ul {
  list-style: none;
  padding: 0;
}

.sidebar li {
  padding: 10px;
  cursor: pointer;
  border-radius: 4px;
  margin-bottom: 5px;
}

.sidebar li:hover {
  background-color: #e0e0e0;
}

.sidebar li.active {
  background-color: #007aff;
  color: #fff;
}

/* 右侧聊天窗口 */
.chat-window {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.chat-header {
  padding: 20px;
  border-bottom: 1px solid #ddd;
  background-color: #f9f9f9;
}

.chat-messages {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background-color: #fff;
}

.message {
  margin-bottom: 10px;
}

.message .sender {
  font-weight: bold;
  margin-right: 5px;
}

.chat-input {
  padding: 20px;
  border-top: 1px solid #ddd;
  background-color: #f9f9f9;
  display: flex;
}

.chat-input input {
  flex: 1;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.chat-input button {
  margin-left: 10px;
  padding: 10px 20px;
  background-color: #007aff;
  border: none;
  color: #fff;
  font-size: 16px;
  border-radius: 4px;
  cursor: pointer;
}

.chat-input button:hover {
  background-color: #005bb5;
}
</style>
