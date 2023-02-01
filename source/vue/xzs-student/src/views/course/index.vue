<template>
	<div style="margin-top: 10px" class="app-contain">
		<el-tabs tab-position="left" v-model="tabId" @tab-click="subjectChange">
			<el-tab-pane :label="item.name" :key="item.id" :name="item.id" v-for="item in subjectList"
				style="margin-left: 20px;">
				<el-row style="float: right">
				</el-row>
				<el-table v-loading="listLoading" :data="tableData" fit highlight-current-row style="width: 100%">
					<el-table-column prop="id" label="序号" width="90px" />
					<el-table-column prop="title" label="名称" />
					<el-table-column prop="remark" label="描述" />
					<el-table-column align="right">
						<template slot-scope="{row}">
							<el-button type="text" size="small" @click="handleLearn(row)">立即学习</el-button>
						</template>
					</el-table-column>
				</el-table>
				<pagination v-show="total > 0" :total="total" :background="false" :page.sync="queryParam.pageIndex"
					:limit.sync="queryParam.pageSize" @pagination="search" style="margin-top: 20px" />
			</el-tab-pane>
		</el-tabs>

		<el-dialog :visible.sync="dialogTableVisible" @closed="handleDiagClose()" :close-on-press-escape="false"
			:close-on-click-modal="false">
			<div style="height: 500px">
				<vue-core-video-player ref="videoPlayer" :src="videoSource" :cover="cover" :title="title" :loop="false" />
			</div>
		</el-dialog>

	</div>
</template>

<script>
import { mapState } from 'vuex'
import Pagination from '@/components/Pagination'
import examPaperApi from '@/api/course'
import subjectApi from '@/api/subject'

export default {
	components: { Pagination },
	data() {
		return {
			queryParam: {
				paperType: 1,
				subjectId: 0,
				pageIndex: 1,
				pageSize: 10
			},
			tabId: '',
			listLoading: true,
			subjectList: [],
			tableData: [],
			total: 0,
			picBase: 'http://pch-photo.oss-cn-shanghai.aliyuncs.com/education',
			dialogTableVisible: false,
			videoSource: [],
			cover: null,
			title: null,
		}
	},
	created() {
		this.initSubject()
	},
	methods: {
		initSubject() {
			let _this = this
			subjectApi.list().then(re => {
				_this.subjectList = re.response
				let subjectId = _this.subjectList[0].id
				_this.queryParam.subjectId = subjectId
				_this.tabId = subjectId.toString()
				_this.search()
			})
		},
		search() {
			this.listLoading = true
			examPaperApi.pageList(this.queryParam).then(data => {
				const re = data.response
				this.tableData = re.list
				this.total = re.total
				this.queryParam.pageIndex = re.pageNum
				this.listLoading = false
			})
		},
		paperTypeChange(val) {
			this.search()
		},
		subjectChange(tab, event) {
			this.queryParam.subjectId = Number(this.tabId)
			this.search()
		},
		handleLearn(row) {
			console.log('handleLearn===', row)
			this.dialogTableVisible = true
			this.cover = `${this.picBase}${row.picUrl}`
			this.videoSource = [{ src: `${this.picBase}${row.url}` }]
			this.title = row.title
		},
		handleDiagClose() {
			console.log('handleDiagClose', this.$refs.videoPlayer)
			this.$refs.videoPlayer.pause()
		}
	},
	computed: {
		...mapState('enumItem', {
			paperTypeEnum: state => state.exam.examPaper.paperTypeEnum
		})
	}
}
</script>
