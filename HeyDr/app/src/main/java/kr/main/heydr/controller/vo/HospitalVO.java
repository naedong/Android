package kr.main.heydr.controller.vo;

public class HospitalVO {
    private int  cnum,r_num;
    private String hname;
    private String hloc;
    private String otime;
    private String ctime;

    public int getCnum() {
        return cnum;
    }

    public void setCnum(int cnum) {
        this.cnum = cnum;
    }

    public int getR_num() {
        return r_num;
    }

    public void setR_num(int r_num) {
        this.r_num = r_num;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public String getHloc() {
        return hloc;
    }

    public void setHloc(String hloc) {
        this.hloc = hloc;
    }

    public String getOtime() {
        return otime;
    }

    public void setOtime(String otime) {
        this.otime = otime;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getHcate() {
        return hcate;
    }

    public void setHcate(String hcate) {
        this.hcate = hcate;
    }

    public String getHurl() {
        return hurl;
    }

    public void setHurl(String hurl) {
        this.hurl = hurl;
    }

    public String getHtel() {
        return htel;
    }

    public void setHtel(String htel) {
        this.htel = htel;
    }

    private String hcate;
    private String hurl;
    private String htel;

}
