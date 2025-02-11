<template>
  <div class="friend-request-list">
    <h3>Friend Requests</h3>
    <ul>
      <li v-for="request in requests" :key="request.id">
        <span>{{ request.sender }}</span>
        <button @click="acceptRequest(request.senderId)">Accept</button>
        <button @click="rejectRequest(request.senderId)">Reject</button>
      </li>
    </ul>
    <p v-if="requests.length === 0">You have no pending friend requests.</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      requests: []
    };
  },
  created() {
    this.fetchFriendRequests();
  },
  methods: {
    async fetchFriendRequests() {
      try {
        const response = await axios.get(`/api/friends/requests?userId=${this.$store.state.user.id}`);
        this.requests = response.data;
      } catch (error) {
        console.error('Failed to fetch friend requests:', error);
      }
    },
    async acceptRequest(senderId) {
      try {
        const response = await axios.post('/api/friends/accept', {
          userId: this.$store.state.user.id,
          friendId: senderId
        });
        if (response.data.success) {
          this.fetchFriendRequests();  // Refresh the request list
        } else {
          alert('Failed to accept friend request');
        }
      } catch (error) {
        console.error('Error accepting friend request:', error);
      }
    },
    async rejectRequest(senderId) {
      try {
        const response = await axios.post('/api/friends/reject', {
          userId: this.$store.state.user.id,
          friendId: senderId
        });
        if (response.data.success) {
          this.fetchFriendRequests();  // Refresh the request list
        } else {
          alert('Failed to reject friend request');
        }
      } catch (error) {
        console.error('Error rejecting friend request:', error);
      }
    }
  }
};
</script>

<style scoped>
.friend-request-list {
  margin-top: 20px;
}

button {
  padding: 6px 12px;
  background-color: #2ecc71;
  color: white;
  border: none;
  cursor: pointer;
  margin-right: 10px;
}

button:hover {
  background-color: #27ae60;
}

button:nth-child(2) {
  background-color: #e74c3c;
}

button:nth-child(2):hover {
  background-color: #c0392b;
}
</style>
