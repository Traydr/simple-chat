package dev.traydr.simplechat.entities;

public class MessageData {
    private long gid;
    private String data;

    public MessageData(long gid, String message) {
        this.gid = gid;
        this.data = message;
    }

    public long getGid() {
        return gid;
    }

    public void setGid(long gid) {
        this.gid = gid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
