package kr.main.heydr.controller.vo;

public class ReserveVO {
    private int reservNum;
    private int num;
    private int r_num;
    private int dnum;
    private int contectCheck;
    private String symptom;
    private String symptomComm;
    private String rdate;
    private String rtime;

    private MemberVO memberVO;
    private DoctorVO doctorVO;

    public int getReservNum() {
        return reservNum;
    }

    public void setReservNum(int reservNum) {
        this.reservNum = reservNum;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getR_num() {
        return r_num;
    }

    public void setR_num(int r_num) {
        this.r_num = r_num;
    }

    public int getDnum() {
        return dnum;
    }

    public void setDnum(int dnum) {
        this.dnum = dnum;
    }

    public int getContectCheck() {
        return contectCheck;
    }

    public void setContectCheck(int contectCheck) {
        this.contectCheck = contectCheck;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getSymptomComm() {
        return symptomComm;
    }

    public void setSymptomComm(String symptomComm) {
        this.symptomComm = symptomComm;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getRtime() {
        return rtime;
    }

    public void setRtime(String rtime) {
        this.rtime = rtime;
    }

    public MemberVO getMemberVO() {
        return memberVO;
    }

    public void setMemberVO(MemberVO memberVO) {
        this.memberVO = memberVO;
    }

    public DoctorVO getDoctorVO() {
        return doctorVO;
    }

    public void setDoctorVO(DoctorVO doctorVO) {
        this.doctorVO = doctorVO;
    }
}
