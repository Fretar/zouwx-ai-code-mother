<template>
  <div class="user-profile-page">
    <!-- 页面头部 -->
    <div class="profile-header">
      <div class="profile-header-content">
        <!-- 头像和返回按钮容器 -->
        <div class="avatar-wrapper">
          <!-- 返回按钮 -->
          <a-button 
            type="text" 
            class="back-button"
            @click="handleGoBack"
          >
            <template #icon>
              <ArrowLeftOutlined />
            </template>
          </a-button>
          <a-avatar :src="formState.userAvatar || undefined" :size="72" class="profile-avatar">
            <template #icon>
              <UserOutlined />
            </template>
          </a-avatar>
        </div>
        <div class="profile-header-info">
          <h2 class="profile-name">{{ formState.userName || '未设置用户名' }}</h2>
          <p class="profile-account">@{{ userAccount }}</p>
          <p class="profile-role">
            <a-tag :color="loginUserStore.loginUser.userRole === 'admin' ? 'green' : 'blue'">
              {{ loginUserStore.loginUser.userRole === 'admin' ? '管理员' : '普通用户' }}
            </a-tag>
          </p>
        </div>
      </div>
    </div>

    <!-- 编辑表单 -->
    <div class="profile-card">
      <a-card title="编辑个人信息" :bordered="false">
        <a-form
          :model="formState"
          name="userProfileForm"
          :rules="rules"
          ref="formRef"
          layout="vertical"
        >
          <a-form-item label="用户名" name="userName">
            <a-input
              v-model:value="formState.userName"
              placeholder="请输入用户名"
              size="large"
              :maxlength="20"
              show-count
            >
              <template #prefix>
                <UserOutlined class="input-icon" />
              </template>
            </a-input>
          </a-form-item>

          <a-form-item label="头像URL" name="userAvatar">
            <a-input
              v-model:value="formState.userAvatar"
              placeholder="请输入头像URL地址"
              size="large"
            >
              <template #prefix>
                <PictureOutlined class="input-icon" />
              </template>
            </a-input>
          </a-form-item>

          <!-- 头像预览 -->
          <a-form-item label="头像预览">
            <div class="avatar-preview">
              <a-avatar
                :src="formState.userAvatar || undefined"
                :size="80"
                shape="square"
              >
                <template #icon>
                  <UserOutlined />
                </template>
              </a-avatar>
              <span class="avatar-preview-tip">
                {{ formState.userAvatar ? '预览效果' : '请先输入头像URL' }}
              </span>
            </div>
          </a-form-item>

          <a-form-item label="个人简介" name="userProfile">
            <a-textarea
              v-model:value="formState.userProfile"
              placeholder="介绍一下你自己吧..."
              :rows="4"
              :maxlength="200"
              show-count
            />
          </a-form-item>

          <a-divider />

          <a-form-item>
            <a-space size="middle">
              <a-button
                type="primary"
                size="large"
                :loading="submitting"
                @click="handleSubmit"
              >
                <template #icon>
                  <SaveOutlined />
                </template>
                {{ submitting ? '保存中...' : '保存修改' }}
              </a-button>
              <a-button size="large" @click="handleReset">
                <template #icon>
                  <UndoOutlined />
                </template>
                重置
              </a-button>
            </a-space>
          </a-form-item>
        </a-form>
      </a-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { message } from 'ant-design-vue'
import type { FormInstance } from 'ant-design-vue'
import { UserOutlined, PictureOutlined, SaveOutlined, UndoOutlined, ArrowLeftOutlined } from '@ant-design/icons-vue'
import { useLoginUserStore } from '@/stores/loginUser'
import { updateUser, getLoginUser } from '@/api/userController'
import { useRouter } from 'vue-router'
import './UserProfilePage.css'

const loginUserStore = useLoginUserStore()
const router = useRouter()
const formRef = ref<FormInstance>()
const submitting = ref(false)
const loading = ref(true)
const userAccount = ref('')

// 表单数据（使用 ref 以便 reset）
const formState = reactive<API.UserUpdateRequest & { _original?: API.LoginUserVO }>({
  id: undefined,
  userName: '',
  userAvatar: '',
  userProfile: '',
})

// 保存原始数据用于重置
let originalData: API.LoginUserVO | null = null

// 表单验证规则
const rules = {
  userName: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 1, max: 20, message: '用户名长度应在1-20个字符之间', trigger: 'blur' },
    { pattern: /^[\u4e00-\u9fa5a-zA-Z0-9_]+$/, message: '用户名只能包含中英文、数字和下划线', trigger: 'blur' },
  ],
  userAvatar: [
    {
      validator: (_rule: unknown, value: string) => {
        if (!value || value.trim() === '') return Promise.resolve()
        try {
          const url = new URL(value)
          if (url.protocol === 'http:' || url.protocol === 'https:') {
            return Promise.resolve()
          }
        } catch {
          // not a valid URL
        }
        return Promise.reject('请输入有效的URL地址（以 http:// 或 https:// 开头）')
      },
      trigger: 'blur',
    },
  ],
}

// 初始化表单数据
onMounted(async () => {
  try {
    const res = await getLoginUser()
    if (res.data.code === 0 && res.data.data) {
      const userData = res.data.data
      originalData = { ...userData }
      formState.id = userData.id
      formState.userName = userData.userName || ''
      formState.userAvatar = userData.userAvatar || ''
      formState.userProfile = userData.userProfile || ''
      userAccount.value = userData.userAccount || ''
    } else {
      message.error('获取用户信息失败')
    }
  } catch (error) {
    console.error('获取用户信息异常:', error)
    message.error('获取用户信息失败，请检查网络连接')
  } finally {
    loading.value = false
  }
})

// 重置表单
const handleReset = () => {
  if (originalData) {
    formState.userName = originalData.userName || ''
    formState.userAvatar = originalData.userAvatar || ''
    formState.userProfile = originalData.userProfile || ''
    formRef.value?.clearValidate()
    message.info('已恢复为原始数据')
  }
}

// 返回上一页
const handleGoBack = () => {
  router.back()
}

// 提交表单
const handleSubmit = async () => {
  // 手动验证
  try {
    await formRef.value?.validate()
  } catch {
    message.warning('请先修正表单中的错误')
    return
  }

  submitting.value = true
  try {
    const res = await updateUser({
      id: formState.id,
      userName: formState.userName,
      userAvatar: formState.userAvatar,
      userProfile: formState.userProfile,
    })
    if (res.data.code === 0) {
      message.success('个人信息更新成功')
      // 同步更新 store 中的用户信息
      await loginUserStore.fetchLoginUser()
      // 更新原始数据引用
      if (res.data.data != null && originalData) {
        originalData.userName = formState.userName
        originalData.userAvatar = formState.userAvatar
        originalData.userProfile = formState.userProfile
      }
      // 延迟后返回上一页
      setTimeout(() => {
        router.back()
      }, 1000)
    } else {
      message.error('更新失败：' + (res.data.message || '未知错误'))
    }
  } catch (error) {
    console.error('提交更新异常:', error)
    message.error('更新失败，请检查网络连接后重试')
  } finally {
    submitting.value = false
  }
}
</script>
