package com.example.creact.imdemo.core.communication;

/**
 * Created by Administrator on 2015/11/28.
 */
public class Connection {
    private boolean ok;
    private String message;
    private Object obj;

    public Connection(boolean ok, Object obj) {
        this.ok = ok;
        this.obj = obj;
    }

    public Connection(boolean ok) {
        this.ok = ok;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
