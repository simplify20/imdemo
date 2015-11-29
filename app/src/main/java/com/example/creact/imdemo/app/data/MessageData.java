package com.example.creact.imdemo.app.data;

/**
 * Created by Administrator on 2015/11/28.
 */
public class MessageData {

    private long id;
    private String message;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageData{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
