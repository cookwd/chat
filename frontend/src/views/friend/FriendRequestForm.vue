<template>
  <div class="friend-request-form">
    <h3>Send Friend Request</h3>
    <form @submit.prevent="sendFriendRequest">
      <input v-model="friendUsername" type="text" placeholder="Enter friend username" required />
      <button type="submit">Send Request</button>
    </form>
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      friendUsername: '',
      errorMessage: ''
    };
  },
  methods: {
    async sendFriendRequest() {
      try {
        const response = await axios.post('/api/friends/request', {
          userId: this.$store.state.user.id,
          friendUsername: this.friendUsername
        });
        if (response.data.success) {
          this.$emit('requestSent');
          this.friendUsername = '';
        } else {
          this.errorMessage = response.data.message;
        }
      } catch (error) {
        this.errorMessage = 'Failed to send friend request. Please try again.';
      }
    }
  }
};
</script>

<style scoped>
.friend-request-form {
  margin-top: 20px;
}

input {
  padding: 8px;
  margin-right: 10px;
}

button {
  padding: 8px 16px;
  background-color: #007aff;
  color: white;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #005bb5;
}

.error {
  color: red;
}
</style>
