<template>
  <div>
    <h2>Chat Room: {{ roomId }}</h2>
    <div v-if="user">
      <p>Chat as {{ user.username }}</p>
      <!-- 显示聊天内容和输入框 -->
    </div>
    <div v-else>
      <p>Please login to join the chat.</p>
    </div>
  </div>
</template>

<script>
export default {
  props: ['roomId'],
  computed: {
    user() {
      return this.$store.state.user;
    }
  }
};
</script>
