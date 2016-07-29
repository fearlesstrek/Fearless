package com.example.fearless.meet.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/8/31 0031.
 */
public class MeetBean {

    private String courseName;
    private List<MeetBean> courseList;


    public MeetBean() {
    }

    public List<MeetBean> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<MeetBean> courseList) {
        this.courseList = courseList;
    }

    public String getCateName() {
        return courseName;
    }

    public void setCateName(String courseName) {
        this.courseName = courseName;
    }
}
