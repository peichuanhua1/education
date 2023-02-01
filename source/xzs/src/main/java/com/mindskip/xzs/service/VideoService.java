package com.mindskip.xzs.service;


import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.domain.Video;
import com.mindskip.xzs.viewmodel.admin.video.VideoPageRequestVM;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService extends BaseService<Video>{

    void deleteVideoById(Integer id);

    Video insertVideo(Video video, MultipartFile[] file) throws IOException;

    void updateVideo(Video video);

    List<Video> videoPageList(String title, Integer pageIndex, Integer pageSize);

    Integer videoPageCount(String title);

    PageInfo<Video> videoPage(VideoPageRequestVM requestVM);

    Video getVideoById(Integer id);

    void updateSrcVideo(Video video, MultipartFile file);

    void updateSrcPic(Video video, MultipartFile file);
}
