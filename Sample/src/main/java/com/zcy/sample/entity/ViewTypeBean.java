package com.zcy.sample.entity;

/**
 * Created by zcy on 16/10/3.
 */

public class ViewTypeBean {

    private String content;
    private String title;
    private int viewType;

    public ViewTypeBean(String content, int viewType) {
        this.content = content;
        this.viewType = viewType;
    }

    public ViewTypeBean() {

    }

    public ViewTypeBean(String content, int viewType, String title) {
        this.content = content;
        this.title = title;
        this.viewType = viewType;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
