package com.mindskip.xzs.controller.admin;

import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.domain.Video;
import com.mindskip.xzs.service.VideoService;

import com.mindskip.xzs.viewmodel.admin.video.VideoPageRequestVM;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;


@RestController("AdminVideoController")
@RequestMapping(value = "/api/admin/video")
public class VideoController {

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo<Video>> pageList(@RequestBody VideoPageRequestVM model) {
        PageInfo<Video> pageInfo = videoService.videoPage(model);
        return RestResponse.ok(pageInfo);
    }

    /**
     * 新增视频，file [0]封面， [1]视频
     * @param file
     * @param title
     * @param subjectId
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public RestResponse<Video> edit(@RequestParam("file") MultipartFile[] file,@RequestParam String title, @RequestParam Integer subjectId, @RequestParam String remark, @RequestParam Integer level) throws IOException {
        Video video = new Video();
        video.setTitle(title);
        video.setSubjectId(subjectId);
        video.setRemark(remark);
        video.setLevel(level);
        video = videoService.insertVideo(video, file);
        return RestResponse.ok(video);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResponse update(@RequestBody  Video model) {
        videoService.updateVideo(model);
        return RestResponse.ok();
    }

    @RequestMapping(value = "/updateSrcVideo", method = RequestMethod.POST)
    public RestResponse updateSrcVideo(@RequestParam("file") MultipartFile file, @RequestParam Integer id) {
        Video video = videoService.getVideoById(id);
        videoService.updateSrcVideo(video, file);
        return RestResponse.ok();
    }

    @RequestMapping(value = "/updateSrcPic", method = RequestMethod.POST)
    public RestResponse updateSrcPic(@RequestParam("file") MultipartFile file, @RequestParam Integer id) {
        Video video = videoService.getVideoById(id);
        videoService.updateSrcPic(video, file);
        return RestResponse.ok();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Integer id) {
        videoService.deleteVideoById(id);
        return RestResponse.ok();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.POST)
    public RestResponse get(@PathVariable Integer id) {
        Video video = videoService.getVideoById(id);
        return RestResponse.ok(video);
    }
}
