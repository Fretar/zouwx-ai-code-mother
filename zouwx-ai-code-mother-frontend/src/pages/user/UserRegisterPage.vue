<script setup lang="ts">
import {ref} from 'vue'
import {useRouter} from 'vue-router'
import {message} from 'ant-design-vue'
import {UserOutlined, LockOutlined} from '@ant-design/icons-vue'
import {register} from '@/api/userController'
import './UserRegisterPage.css'

const router = useRouter()

const formState = ref({
  userAccount: '',
  userPassword: '',
  checkPassword: ''
})

const loading = ref(false)

const handleRegister = async () => {
  if (!formState.value.userAccount || !formState.value.userPassword || !formState.value.checkPassword) {
    message.error('请填写完整的注册信息')
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

  if (formState.value.userPassword !== formState.value.checkPassword) {
    message.error('两次输入的密码不一致')
    return
  }

  loading.value = true
  try {
    const res = await register({
      userAccount: formState.value.userAccount,
      userPassword: formState.value.userPassword,
      checkPassword: formState.value.checkPassword
    })

    if (res.data.code === 0 && res.data.data) {
      message.success('注册成功')
      setTimeout(() => {
        router.push('/user/login')
      }, 500)
    } else {
      message.error(res.data.message || '注册失败')
    }
  } catch (error) {
    console.error('注册错误:', error)
    message.error('注册失败，请检查网络连接')
  } finally {
    loading.value = false
  }
}

const goToLogin = () => {
  router.push('/user/login')
}
</script>

<template>
  <div class="register-container">
    <!-- 背景装饰 -->
    <div class="register-background">
      <div class="bg-circle circle-1"></div>
      <div class="bg-circle circle-2"></div>
      <div class="bg-circle circle-3"></div>
    </div>

    <!-- 注册卡片 -->
    <div class="register-card">
      <!-- 标题区域 -->
      <div class="card-header">
        <div class="brand-logo-wrapper">
          <img src="@/assets/logo.png" alt="Logo" class="card-brand-logo"/>
          <h1 class="card-title">灵搭 AI 应用生成</h1>
        </div>
        <p class="card-subtitle">创建您的账户开始使用</p>
      </div>

      <!-- 注册表单 -->
      <a-form
        :model="formState"
        class="register-form"
        @finish="handleRegister"
      >
        <!-- 账号输入框 -->
        <a-form-item name="userAccount">
          <a-input
            v-model:value="formState.userAccount"
            placeholder="请输入账号（至少4个字符）"
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
            placeholder="请输入密码（至少8个字符）"
            size="large"
            :bordered="false"
            class="custom-input"
          >
            <template #prefix>
              <LockOutlined class="input-icon"/>
            </template>
          </a-input-password>
        </a-form-item>

        <!-- 确认密码输入框 -->
        <a-form-item name="checkPassword">
          <a-input-password
            v-model:value="formState.checkPassword"
            placeholder="请再次输入密码"
            size="large"
            :bordered="false"
            class="custom-input"
          >
            <template #prefix>
              <LockOutlined class="input-icon"/>
            </template>
          </a-input-password>
        </a-form-item>

        <!-- 注册按钮 -->
        <a-form-item>
          <a-button
            type="primary"
            html-type="submit"
            size="large"
            block
            :loading="loading"
            class="register-button"
          >
            {{ loading ? '注册中...' : '注 册' }}
          </a-button>
        </a-form-item>

        <!-- 登录链接 -->
        <div class="register-footer">
          <span>已有账号？</span>
          <a @click="goToLogin" class="login-link">立即登录</a>
        </div>
      </a-form>
    </div>
  </div>
</template>
