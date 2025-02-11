<template>
  <div class="friend-list">
    <h3>Your Friends</h3>
    <ul>
      <li v-for="friend in friends" :key="friend.id">
        {{ friend.username }}
        <button @click="removeFriend(friend.id)">Remove</button>
      </li>
    </ul>
    <p v-if="friends.length === 0">You have no friends yet.</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      friends: []
    };
  },
  created() {
    this.fetchFriends();
  },
  methods: {
    async fetchFriends() {
      try {
        const response = await axios.get(`/api/friends?userId=${this.$store.state.user.id}`);
        this.friends = response.data;
      } catch (error) {
        console.error('Failed to fetch friends:', error);
      }
    },
    async removeFriend(friendId) {
      try {
        const response = await axios.delete('/api/friends', {
          params: {
            userId: this.$store.state.user.id,
            friendId: friendId
          }
        });
        if (response.data.success) {
          this.fetchFriends();  // Refresh the friend list
        } else {
          alert('Failed to remove friend');
        }
      } catch (error) {
        console.error('Error removing friend:', error);
      }
    }
  }
};
</script>

<style scoped>
.friend-list {
  margin-top: 20px;
}

button {
  padding: 6px 12px;
  background-color: #e74c3c;
  color: white;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #c0392b;
}
</style>
