package com.example.creact.imdemo.core.callback;

/**
 * Created by Administrator on 2015/11/28.
 */
public class SendResponse<T> {
    private boolean success;
    private T obj;
    private String message;
    private Object error;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
}
