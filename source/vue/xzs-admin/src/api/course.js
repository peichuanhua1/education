import { post, form } from '@/utils/request'

export default {
	pageList: query => post('/api/admin/video/page/list', query),
	insert: query => form('/api/admin/video/insert', query),
	select: id => post('/api/admin/education/subject/select/' + id),
	deleteCourse: id => post('/api/admin/video/delete/' + id)
}