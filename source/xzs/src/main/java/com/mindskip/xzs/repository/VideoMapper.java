package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.User;
import com.mindskip.xzs.domain.Video;
import com.mindskip.xzs.viewmodel.admin.user.UserPageRequestVM;
import com.mindskip.xzs.viewmodel.admin.video.VideoPageRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface VideoMapper extends BaseMapper<Video> {

    void deleteVideoById(Integer id);

    void insertVideo(Video video);

    void updateVideo(Video video);

    List<Video> videoPageList(Map<String, Object> map);

    Integer videoPageCount(Map<String, Object> map);

    List<Video> videoPage(VideoPageRequestVM requestVM);

    Video getVideoById(Integer id);
}
