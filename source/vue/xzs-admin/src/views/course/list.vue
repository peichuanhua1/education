<template>
	<div class="app-container">
		<el-form :model="queryParam" ref="queryForm" :inline="true">
			<el-form-item label="年级：">
				<el-select v-model="queryParam.level" placeholder="年级" @change="levelChange" clearable>
					<el-option v-for="item in levelEnum" :key="item.key" :value="item.key" :label="item.value"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="学科：">
				<el-select v-model="queryParam.subjectId" clearable>
					<el-option v-for="item in subjectFilter" :key="item.id" :value="item.id"
						:label="item.name + ' ( ' + item.levelName + ' )'"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="名称：">
				<el-input v-model="queryParam.title" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="submitForm">查询</el-button>
			</el-form-item>
		</el-form>
		<el-table v-loading="listLoading" :data="tableData" border fit highlight-current-row style="width: 100%">
			<el-table-column prop="id" label="Id" width="90px" />
			<el-table-column prop="subjectId" label="封面" width="160px">
				<template slot-scope="{row}">
					<el-image style="width: 100px; height: 100px" :src="picBase + row.picUrl"
						:preview-src-list="[picBase + row.picUrl]">
					</el-image>
				</template>
			</el-table-column>
			<el-table-column prop="title" label="课程名称" />
			<el-table-column prop="subjectId" :formatter="subjectFormatter" label="分类" />
			<el-table-column prop="remark" label="描述" />
			<el-table-column label="操作" align="center" width="160px">
				<template slot-scope="{row}">
					<!-- <el-button size="mini" @click="$router.push({ path: '/exam/paper/edit', query: { data: row } })">编辑</el-button> -->
					<el-button size="mini" type="danger" @click="deletePaper(row)" class="link-left">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
		<pagination v-show="total > 0" :total="total" :page.sync="queryParam.pageIndex" :limit.sync="queryParam.pageSize"
			@pagination="search" />
	</div>
</template>

<script>
import { mapGetters, mapState, mapActions } from 'vuex'
import Pagination from '@/components/Pagination'
import examPaperApi from '@/api/course'

export default {
	components: { Pagination },
	data() {
		return {
			picBase: 'http://pch-photo.oss-cn-shanghai.aliyuncs.com/education',
			queryParam: {
				title: null,
				level: null,
				subjectId: null,
				pageIndex: 1,
				pageSize: 10
			},
			subjectFilter: null,
			listLoading: true,
			tableData: [],
			total: 0
		}
	},
	created() {
		this.initSubject()
		this.search()
	},
	methods: {
		submitForm() {
			this.queryParam.pageIndex = 1
			this.search()
		},
		search() {
			this.listLoading = false
			examPaperApi.pageList(this.queryParam).then(data => {
				const re = data.response
				this.tableData = re.list
				this.total = re.total
				this.queryParam.pageIndex = re.pageNum
				this.listLoading = false
			})
		},
		deletePaper(row) {
			let _this = this
			examPaperApi.deleteCourse(row.id).then(re => {
				if (re.code === 1) {
					_this.search()
					_this.$message.success(re.message)
				} else {
					_this.$message.error(re.message)
				}
			})
		},
		levelChange() {
			this.queryParam.subjectId = null
			this.subjectFilter = this.subjects.filter(data => data.level === this.queryParam.level)
		},
		subjectFormatter(row, column, cellValue, index) {
			return this.subjectEnumFormat(cellValue)
		},
		...mapActions('exam', { initSubject: 'initSubject' })
	},
	computed: {
		...mapGetters('enumItem', ['enumFormat']),
		...mapState('enumItem', {
			levelEnum: state => state.user.levelEnum
		}),
		...mapGetters('exam', ['subjectEnumFormat']),
		...mapState('exam', { subjects: state => state.subjects })
	}
}
</script>
