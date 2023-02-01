package com.mindskip.xzs.controller.student;

import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.domain.Video;
import com.mindskip.xzs.service.VideoService;
import com.mindskip.xzs.viewmodel.admin.video.VideoPageRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController("StudentVideoController")
@RequestMapping(value = "/api/student/video")
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

}