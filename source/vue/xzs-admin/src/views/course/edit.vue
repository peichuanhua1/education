<template>
  <div class="app-container">
    <el-form :model="form" ref="form" label-width="100px" v-loading="formLoading">
      <el-form-item label="年级：" required>
        <el-select v-model="form.level" placeholder="年级">
          <el-option v-for="item in levelEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="学科：" required>
        <el-select v-model="form.level" placeholder="学科">
          <el-option v-for="item in levelEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="课程名称：" required>
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="封面：" required>
        <el-upload
          action="https://jsonplaceholder.typicode.com/posts/"
          list-type="picture-card"
          accept=".jpg, .jpeg, .png, .JPG, .JPEG"
          :limit="1"
          :on-success="handleSuccess"
          :on-remove="handleRemove">
            <i class="el-icon-plus"></i>
        </el-upload>
      </el-form-item>
      <el-form-item label="描述：" required>
        <el-input v-model="form.name" type="textarea" :autosize="{ minRows: 2, maxRows: 4}"></el-input>
      </el-form-item>
      <el-form-item label="课程视频：" required>
        <el-upload
          action="https://jsonplaceholder.typicode.com/posts/"
          accept=".mp4, .ogg, .flv, .avi, .wmv, .rmvb"
          :limit="1"
          :on-success="handleSuccess"
          :on-remove="handleRemove">
            <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapGetters, mapState, mapActions } from 'vuex'
import subjectApi from '@/api/subject'

export default {
  data () {
    return {
      form: {
        id: null,
        name: '',
        level: 1,
        levelName: ''
      },
      formLoading: false
    }
  },
  created () {
    let id = this.$route.query.id
    let _this = this
    if (id && parseInt(id) !== 0) {
      _this.formLoading = true
      subjectApi.select(id).then(re => {
        _this.form = re.response
        _this.formLoading = false
      })
    }
  },
  methods: {
    submitForm () {
      let _this = this
      this.formLoading = true
      this.form.levelName = this.enumFormat(this.levelEnum, this.form.level)
      subjectApi.edit(this.form).then(data => {
        if (data.code === 1) {
          _this.$message.success(data.message)
          _this.delCurrentView(_this).then(() => {
            _this.$router.push('/education/subject/list')
          })
        } else {
          _this.$message.error(data.message)
          _this.formLoading = false
        }
      }).catch(e => {
        _this.formLoading = false
      })
    },
    resetForm () {
      let lastId = this.form.id
      this.$refs['form'].resetFields()
      this.form = {
        id: null,
        name: '',
        level: 1,
        levelName: ''
      }
      this.form.id = lastId
    },
    ...mapActions('tagsView', { delCurrentView: 'delCurrentView' })
  },
  computed: {
    ...mapGetters('enumItem', [
      'enumFormat'
    ]),
    ...mapState('enumItem', {
      levelEnum: state => state.user.levelEnum
    })
  }
}
</script>
<style lang="scss" scoped>

.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
