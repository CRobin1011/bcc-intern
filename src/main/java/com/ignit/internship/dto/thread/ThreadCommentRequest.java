package com.ignit.internship.dto.thread;

public class ThreadCommentRequest {

    private String title;

    private String content;

    public ThreadCommentRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
