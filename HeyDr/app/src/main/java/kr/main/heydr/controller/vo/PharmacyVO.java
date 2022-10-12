package kr.main.heydr.controller.vo;

public class PharmacyVO {
    private int pnum, cnum;
    private String pname, ploc;
    private double plat, plong;

    public int getPnum() {
        return pnum;
    }

    public void setPnum(int pnum) {
        this.pnum = pnum;
    }

    public int getCnum() {
        return cnum;
    }

    public void setCnum(int cnum) {
        this.cnum = cnum;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPloc() {
        return ploc;
    }

    public void setPloc(String ploc) {
        this.ploc = ploc;
    }

    public double getPlat() {
        return plat;
    }

    public void setPlat(double plat) {
        this.plat = plat;
    }

    public double getPlong() {
        return plong;
    }

    public void setPlong(double plong) {
        this.plong = plong;
    }
}
