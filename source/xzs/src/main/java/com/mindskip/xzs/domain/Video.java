package com.mindskip.xzs.domain;

import java.io.Serializable;

/**
 * 视频
 */
public class Video implements Serializable {
    private static final long serialVersionUID = -7797183521298123117L;

    //id
    private Integer id;

    //标题
    private String title;

    //年级
    private Integer subjectId;

    //视频地址
    private String url;

    //封面地址
    private String picUrl;

    //是否删除
    private Boolean deleted;

    //描述
    private String remark;

    /**
     * 年级
     */
    private Integer level;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
