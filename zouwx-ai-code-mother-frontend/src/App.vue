<script setup lang="ts">
import {computed} from 'vue'
import {useRoute} from 'vue-router'
import BasicLayout from '@/layouts/BasicLayout.vue'
import {useLoginUserStore} from '@/stores/loginUser.ts'

const route = useRoute()
const loginUserStore = useLoginUserStore()
loginUserStore.fetchLoginUser()

// 判断是否需要使用布局（登录、注册页面不需要布局）
const needLayout = computed(() => {
  return !route.path.startsWith('/user/')
})
</script>

<template>
  <!-- 需要布局的页面 -->
  <BasicLayout v-if="needLayout"/>

  <!-- 不需要布局的页面（直接渲染当前路由组件） -->
  <router-view v-else/>
</template>

<style></style>
