<template>
	<div class="app-container">
		<el-form :model="form" ref="form" :rules="rules" label-width="100px" v-loading="formLoading">
			<el-form-item label="年级：" required prop="level">
				<el-select v-model="form.level" placeholder="年级" @change="levelChange">
					<el-option v-for="item in levelEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="学科：" prop="subjectId">
				<el-select v-model="form.subjectId" placeholder="学科">
					<el-option v-for="item in subjectFilter" :key="item.id" :value="item.id"
						:label="item.name + ' ( ' + item.levelName + ' )'"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="课程名称：" prop="title">
				<el-input v-model="form.title"></el-input>
			</el-form-item>
			<el-form-item label="封面：" required prop="picUrl">
				<el-upload action="https://jsonplaceholder.typicode.com/posts/" list-type="picture-card"
					accept=".jpg, .jpeg, .png, .JPG, .JPEG" :limit="1" :on-change="handleImgChange" :auto-upload="this.form.id"
					:with-credentials="true" :data="{ 'id': this.form.id }" :on-exceed="handleExceed">
					<i class="el-icon-plus"></i>
				</el-upload>
			</el-form-item>
			<el-form-item label="描述：" required prop="remark">
				<el-input v-model="form.remark" type="textarea" :autosize="{ minRows: 2, maxRows: 4 }"></el-input>
			</el-form-item>
			<el-form-item label="课程视频：" required prop="videoUrl">
				<el-upload action="https://jsonplaceholder.typicode.com/posts/" accept=".mp4, .ogg, .flv, .avi, .wmv, .rmvb"
					:limit="1" :on-change="handleVideoChange" :auto-upload="this.form.id" :with-credentials="true"
					:data="{ 'id': this.form.id }" :on-exceed="handleExceed">
					<el-button size="small" type="primary">点击上传</el-button>
				</el-upload>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="submitForm">提交</el-button>
				<!-- <el-button @click="resetForm">重置</el-button> -->
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
import { mapGetters, mapState, mapActions } from 'vuex'
import subjectApi from '@/api/course'

export default {
	data() {
		return {
			form: {
				id: null,
				title: '',
				level: '',
				levelName: '',
				subjectId: '',
				remark: '',
				picUrl: null,
				videoUrl: null,
			},
			formLoading: false,
			subjectFilter: null,
			rules: {
				title: [
					{ required: true, message: '请输入名称', trigger: 'blur' },
				],
				remark: [
					{ required: true, message: '请输入描述', trigger: 'blur' },
				],
				subjectId: [
					{ required: true, message: '请选择学科', trigger: 'blur' },
				],
				level: [
					{ required: true, message: '请选择年级', trigger: 'blur' },
				],
				picUrl: [
					{ required: true, message: '选择图片', trigger: 'blur' },
				],
				videoUrl: [
					{ required: true, message: '选择视频，最大512Mb', trigger: 'blur' },
				],
			},
			picFile: null,
			videoFile: null,
		}
	},
	created() {
		this.initSubject()
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
		submitForm() {
			let _this = this
			this.$refs['form'].validate((valid) => {
				if (valid) {
					this.formLoading = true
					this.form.levelName = this.enumFormat(this.levelEnum, this.form.level)
					const formData = new FormData()
					formData.append('title', this.form.title)
					formData.append('subjectId', this.form.subjectId)
					formData.append('level', this.form.level)
					formData.append('remark', this.form.remark)
					if (!this.form.id) {
						formData.append('file', this.picFile)
						formData.append('file', this.videoFile)
					}
					subjectApi.insert(formData).then(data => {
						if (data.code === 1) {
							_this.$message.success(data.message)
							_this.delCurrentView(_this).then(() => {
								_this.$router.push('/course/list')
							})
						} else {
							_this.$message.error(data.message)
							_this.formLoading = false
						}
					}).catch(e => {
						_this.formLoading = false
					})
				} else {
					console.log('error submit!!');
					return false;
				}
			})
		},
		resetForm() {
			let lastId = this.form.id
			this.$refs['form'].resetFields()
			this.form = {
				id: null,
				title: '',
				level: '',
				levelName: '',
				videoUrl: '',
				picUrl: '',
			}
			this.form.id = lastId
		},
		levelChange() {
			this.form.subjectId = null
			this.subjectFilter = this.subjects.filter(data => data.level === this.form.level)
		},
		handleImgChange(file, fileList) {
			console.log('handleImgChange==', file)
			this.picFile = file.raw
			this.form.picUrl = file.raw
		},
		handleVideoChange(file, fileList) {
			const isLt2M = file.size / 1024 / 1024 < 512;
			if (!isLt2M) {
				this.$message.error('上传视频大小不能超过 512MB!');
				return
			}
			this.form.videoUrl = file.raw
			this.videoFile = file.raw
		},
		handleExceed() {
			this.$message.error('只能上传一个文件!');
		},
		...mapActions('tagsView', { delCurrentView: 'delCurrentView' }),
		...mapActions('exam', { initSubject: 'initSubject' })
	},
	computed: {
		...mapGetters('enumItem', [
			'enumFormat'
		]),
		...mapState('enumItem', {
			levelEnum: state => state.user.levelEnum
		}),
		...mapGetters('exam', ['subjectEnumFormat']),
		...mapState('exam', { subjects: state => state.subjects })
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
