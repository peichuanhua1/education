import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/layout'

Vue.use(Router)

const constantRoutes = [
	{
		path: '/redirect',
		component: Layout,
		hidden: true,
		children: [
			{
				path: '/redirect/:path*',
				component: () => import('@/views/redirect/index')
			}
		]
	},
	{
		path: '/login',
		name: 'Login',
		hidden: true,
		component: () => import('@/views/login/index'),
		meta: { title: '登录' }
	},
	{
		path: '/',
		component: Layout,
		redirect: '/dashboard',
		children: [
			{
				path: 'dashboard',
				component: () => import('@/views/dashboard/index'),
				name: 'Dashboard',
				meta: { title: '主页', icon: 'home', affix: true }
			}
		]
	},
	{
		path: '/user',
		component: Layout,
		name: 'UserPage',
		meta: {
			title: '用户管理',
			icon: 'users'
		},
		children: [
			{
				path: 'student/list',
				component: () => import('@/views/user/student/list'),
				name: 'UserStudentPageList',
				meta: { title: '学生列表', noCache: true }
			},
			{
				path: 'student/edit',
				component: () => import('@/views/user/student/edit'),
				name: 'UserStudentEdit',
				meta: { title: '学生编辑', noCache: true, activeMenu: '/user/student/list' },
				hidden: true
			},
			{
				path: 'admin/list',
				component: () => import('@/views/user/admin/list'),
				name: 'UserAdminPageList',
				meta: { title: '管理员列表', noCache: true }
			},
			{
				path: 'admin/edit',
				component: () => import('@/views/user/admin/edit'),
				name: 'UserAdminEdit',
				meta: { title: '管理员编辑', noCache: true, activeMenu: '/user/admin/list' },
				hidden: true
			}
		]
	},
	{
		path: '/exam',
		component: Layout,
		name: 'ExamPage',
		meta: {
			title: '卷题管理',
			icon: 'exam'
		},
		children: [
			{
				path: 'paper/list',
				component: () => import('@/views/exam/paper/list'),
				name: 'ExamPaperPageList',
				meta: { title: '试卷列表', noCache: true }
			},
			{
				path: 'paper/edit',
				component: () => import('@/views/exam/paper/edit'),
				name: 'ExamPaperEdit',
				meta: { title: '试卷编辑', noCache: true, activeMenu: '/exam/paper/list' },
				hidden: true
			},
			{
				path: 'question/list',
				component: () => import('@/views/exam/question/list'),
				name: 'ExamQuestionPageList',
				meta: { title: '题目列表', noCache: true }
			},
			{
				path: 'question/edit/singleChoice',
				component: () => import('@/views/exam/question/edit/single-choice'),
				name: 'singleChoicePage',
				meta: { title: '单选题编辑', noCache: true, activeMenu: '/exam/question/list' },
				hidden: true
			}
		]
	},
	{
		path: '/course',
		component: Layout,
		name: 'CoursePage',
		meta: {
			title: '课程学习',
			icon: 'education'
		},
		alwaysShow: true,
		children: [
			{
				path: 'list',
				component: () => import('@/views/course/list'),
				name: 'CourseListPage',
				meta: { title: '课程列表', noCache: true }
			},
			{
				path: 'edit',
				component: () => import('@/views/course/edit'),
				name: 'CourseEditPage',
				meta: { title: '课程创建', noCache: true }
			}
		]
	},
	{
		path: '/task',
		component: Layout,
		name: 'TaskPage',
		meta: {
			title: '任务管理',
			icon: 'task'
		},
		alwaysShow: true,
		children: [
			{
				path: 'list',
				component: () => import('@/views/task/list'),
				name: 'TaskListPage',
				meta: { title: '任务列表', noCache: true }
			},
			{
				path: 'edit',
				component: () => import('@/views/task/edit'),
				name: 'TaskEditPage',
				meta: { title: '任务创建', noCache: true }
			}
		]
	},
	{
		path: '/education',
		component: Layout,
		name: 'EducationPage',
		meta: {
			title: '教育管理',
			icon: 'education'
		},
		alwaysShow: true,
		children: [
			{
				path: 'subject/list',
				component: () => import('@/views/education/subject/list'),
				name: 'EducationSubjectPage',
				meta: { title: '学科列表', noCache: true }
			},
			{
				path: 'subject/edit',
				component: () => import('@/views/education/subject/edit'),
				name: 'EducationSubjectEditPage',
				meta: { title: '学科编辑', noCache: true, activeMenu: '/education/subject/list' },
				hidden: true
			}
		]
	},
	{
		path: '/answer',
		component: Layout,
		name: 'AnswerPage',
		meta: {
			title: '成绩管理',
			icon: 'answer'
		},
		alwaysShow: true,
		children: [
			{
				path: 'list',
				component: () => import('@/views/answer/list'),
				name: 'AnswerPageList',
				meta: { title: '答卷列表', noCache: true }
			}
		]
	},
	{
		path: '/profile',
		component: Layout,
		hidden: true,
		children: [
			{
				path: 'index',
				component: () => import('@/views/profile/index'),
				name: 'Profile',
				meta: { title: '个人简介', icon: 'user', noCache: true }
			}
		]
	},
	{
		path: '*',
		hidden: true,
		component: () => import('@/views/error-page/404'),
		meta: { title: '404', noCache: true }
	}
]

const router = new Router({
	routes: constantRoutes
})

export {
	constantRoutes,
	router
}
