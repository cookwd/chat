<template>
  <div class="friends-dashboard">
    <FriendRequestForm @requestSent="fetchFriendRequests" />
    <FriendRequestList />
    <FriendList />
  </div>
</template>

<script>
import FriendRequestForm from './FriendRequestForm.vue';
import FriendRequestList from './FriendRequestList.vue';
import FriendList from './FriendList.vue';

export default {
  components: {
    FriendRequestForm,
    FriendRequestList,
    FriendList
  },
  data() {
    return {};
  }
};
</script>

<style scoped>
.friends-dashboard {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}
</style>
