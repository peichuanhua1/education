package com.mindskip.xzs.viewmodel.admin.video;

import com.mindskip.xzs.base.BasePage;

public class VideoPageRequestVM extends BasePage {
    //标题
    private String title;

    //科目
    private Integer subjectId;

    //年级
    private Integer level;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
