package com.tun.live.models;

public class trailer {
    String ttitle;
    String tposter;
    String tvid;

    public trailer(String ttitle, String tposter, String tvid) {
        this.ttitle = ttitle;
        this.tposter = tposter;
        this.tvid = tvid;
    }

    public trailer() {

    }

    public String getTtitle() {
        return ttitle;
    }

    public void setTtitle(String ttitle) {
        this.ttitle = ttitle;
    }

    public String getTposter() {
        return tposter;
    }

    public void setTposter(String tposter) {
        this.tposter = tposter;
    }

    public String getTvid() {
        return tvid;
    }

    public void setTvid(String tvid) {
        this.tvid = tvid;
    }
}
