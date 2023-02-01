package com.mindskip.xzs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mindskip.xzs.domain.Video;
import com.mindskip.xzs.repository.VideoMapper;
import com.mindskip.xzs.service.VideoService;
import com.mindskip.xzs.utility.OssUtils;
import com.mindskip.xzs.viewmodel.admin.video.VideoPageRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class VideoServiceImpl extends BaseServiceImpl<Video> implements VideoService {

    final String tmpName = "/tmp/";
    private final VideoMapper videoMapper;

    @Autowired
    public VideoServiceImpl(VideoMapper videoMapper) {
        super(videoMapper);
        this.videoMapper = videoMapper;
    }

    @Override
    public Video insertVideo(Video video, MultipartFile[] file) {
        try {
            //获取文件后缀，因此此后端代码可接收一切文件，上传格式前端限定
            String fileExt1 = file[0].getOriginalFilename().substring(file[0].getOriginalFilename().lastIndexOf(".") + 1)
                    .toLowerCase();
            String fileExt2 = file[1].getOriginalFilename().substring(file[1].getOriginalFilename().lastIndexOf(".") + 1)
                    .toLowerCase();
            // 重构文件名称
            String pikId = UUID.randomUUID().toString().replaceAll("-", "");
            String newVidoeName1 = pikId + "." + fileExt1;
            String newVidoeName2 = pikId + "." + fileExt2;
            System.out.println("重构文件名防止上传同名文件："+newVidoeName1);
            System.out.println("重构文件名防止上传同名文件："+newVidoeName2);

            //上传图片
            File newFile1 = new File(tmpName+newVidoeName1);
            if(!newFile1.getParentFile().exists()){
                System.out.println(newFile1.getAbsolutePath());
                //如果目标文件所在的目录不存在，则创建父目录
                newFile1.getParentFile().mkdirs();
            }
            file[0].transferTo(newFile1);
            OssUtils.upload(newFile1, "/"+pikId, newVidoeName1);
            newFile1.delete();

            //上传文件
            File newFile2 = new File(tmpName+newVidoeName2);
            if(!newFile2.getParentFile().exists()){
                System.out.println(newFile2.getAbsolutePath());
                //如果目标文件所在的目录不存在，则创建父目录
                newFile2.getParentFile().mkdirs();
            }
            file[1].transferTo(newFile2);
            OssUtils.upload(newFile2, "/"+pikId, newVidoeName2);
            newFile2.delete();


            video.setPicUrl("/"+pikId+"/"+newVidoeName1);
            video.setUrl("/"+pikId+"/"+newVidoeName2);
            video.setDeleted(Boolean.FALSE);
            videoMapper.insertVideo(video);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return video;
    }

    @Override
    public void updateSrcVideo(Video video, MultipartFile file) {
        try {
            String fileName = video.getUrl().substring(video.getUrl().lastIndexOf("//") + 2);
            System.out.println("重构文件名防止上传同名文件："+fileName);
            File newFile = new File(tmpName+fileName);
            if(!newFile.getParentFile().exists()){
                System.out.println(newFile.getAbsolutePath());
                //如果目标文件所在的目录不存在，则创建父目录
                newFile.getParentFile().mkdirs();
            }
            file.transferTo(newFile);
            OssUtils.upload(newFile, "", fileName);
            newFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateSrcPic(Video video, MultipartFile file) {
        try {
            String fileName = video.getPicUrl().substring(video.getPicUrl().lastIndexOf("//") + 2);
            System.out.println("重构文件名防止上传同名文件："+fileName);
            File newFile = new File(tmpName+fileName);
            if(!newFile.getParentFile().exists()){
                System.out.println(newFile.getAbsolutePath());
                //如果目标文件所在的目录不存在，则创建父目录
                newFile.getParentFile().mkdirs();
            }
            file.transferTo(newFile);
            OssUtils.upload(newFile, "", fileName);
            newFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateVideo(Video video) {
        videoMapper.updateVideo(video);
    }

    @Override
    public List<Video> videoPageList(String title, Integer pageIndex, Integer pageSize) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("title", title);
        map.put("offset", ((int) pageIndex) * pageSize);
        map.put("limit", pageSize);
        return videoMapper.videoPageList(map);
    }

    @Override
    public Integer videoPageCount(String title) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("title", title);
        return videoMapper.videoPageCount(map);
    }

    @Override
    public PageInfo<Video> videoPage(VideoPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                videoMapper.videoPage(requestVM)
        );
    }

    @Override
    public Video getVideoById(Integer id) {
        return videoMapper.getVideoById(id);
    }



    @Override
    public void deleteVideoById(Integer id) {
        videoMapper.deleteVideoById(id);
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    @Override
    public int insert(Video record) {
        return 0;
    }

    @Override
    public int insertByFilter(Video record) {
        return 0;
    }

    @Override
    public Video selectById(Integer id) {
        return null;
    }

    @Override
    public int updateByIdFilter(Video record) {
        return 0;
    }

    @Override
    public int updateById(Video record) {
        return 0;
    }




}
