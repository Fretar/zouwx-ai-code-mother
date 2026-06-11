<script setup lang="ts">
import {ref} from 'vue'
import {useRouter} from 'vue-router'
import {message} from 'ant-design-vue'
import {UserOutlined, LockOutlined} from '@ant-design/icons-vue'
import {userLogin} from '@/api/userController'
import {useLoginUserStore} from '@/stores/loginUser'
import './UserLoginPage.css'

const router = useRouter()
const loginUserStore = useLoginUserStore()

const formState = ref({
  userAccount: '',
  userPassword: ''
})

const loading = ref(false)

const handleLogin = async () => {
  if (!formState.value.userAccount || !formState.value.userPassword) {
    message.error('请输入账号和密码')
    return
  }

  if (formState.value.userAccount.length < 4) {
    message.error('账号长度不能少于4个字符')
    return
  }

  if (formState.value.userPassword.length < 8) {
    message.error('密码长度不能少于8个字符')
    return
  }

  loading.value = true
  try {
    const res = await userLogin({
      userAccount: formState.value.userAccount,
      userPassword: formState.value.userPassword
    })

    if (res.data.code === 0 && res.data.data) {
      loginUserStore.setLoginUser(res.data.data)
      message.success('登录成功')

      const redirect = router.currentRoute.value.query.redirect as string
      router.push(redirect || '/')
    } else {
      message.error(res.data.message || '登录失败')
    }
  } catch (error) {
    console.error('登录错误:', error)
    message.error('登录失败，请检查网络连接')
  } finally {
    loading.value = false
  }
}

const goToRegister = () => {
  router.push('/user/register')
}
</script>

<template>
  <div class="login-container">
    <!-- 背景装饰 -->
    <div class="login-background">
      <div class="bg-circle circle-1"></div>
      <div class="bg-circle circle-2"></div>
      <div class="bg-circle circle-3"></div>
    </div>

    <!-- 登录卡片 -->
    <div class="login-card">
      <!-- 标题区域 -->
      <div class="card-header">
        <div class="brand-logo-wrapper">
          <img src="@/assets/logo.png" alt="Logo" class="card-brand-logo"/>
          <h1 class="card-title">灵搭 AI 应用生成</h1>
        </div>
        <p class="card-subtitle">一句话生成完整应用</p>
      </div>

      <!-- 登录表单 -->
      <a-form
        :model="formState"
        class="login-form"
        @finish="handleLogin"
      >
        <!-- 账号输入框 -->
        <a-form-item name="userAccount">
          <a-input
            v-model:value="formState.userAccount"
            placeholder="请输入账号"
            size="large"
            :bordered="false"
            class="custom-input"
          >
            <template #prefix>
              <UserOutlined class="input-icon"/>
            </template>
          </a-input>
        </a-form-item>

        <!-- 密码输入框 -->
        <a-form-item name="userPassword">
          <a-input-password
            v-model:value="formState.userPassword"
            placeholder="请输入密码"
            size="large"
            :bordered="false"
            class="custom-input"
          >
            <template #prefix>
              <LockOutlined class="input-icon"/>
            </template>
          </a-input-password>
        </a-form-item>

        <!-- 登录按钮 -->
        <a-form-item>
          <a-button
            type="primary"
            html-type="submit"
            size="large"
            block
            :loading="loading"
            class="login-button"
          >
            {{ loading ? '登录中...' : '登 录' }}
          </a-button>
        </a-form-item>

        <!-- 注册链接 -->
        <div class="login-footer">
          <span>还没有账号？</span>
          <a @click="goToRegister" class="register-link">立即注册</a>
        </div>
      </a-form>
    </div>
  </div>
</template>
