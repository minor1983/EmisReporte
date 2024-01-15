import java.util.Date;

public class Meter {
    private final String id;
    private String city;
    private String serial;
    private String street;
    private String build_num;
    private String build_letter;
    private String apart_num;
    private Date dateStamp;
    private String value_Sum;
    private String value_T1;
    private String value_T2;
    private String value_T3;
    private String tariffCount;
    private String district;
    private String owner;
    private String ls;
    private String pointType;
    private String type;
    private String comments;
    private String Ktt;

    public Meter(String id){
        this.id=id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuild_num() {
        return build_num;
    }

    public void setBuild_num(String build_num) {
        this.build_num = build_num;
    }

    public String getBuild_letter() {
        return build_letter;
    }

    public void setBuild_letter(String build_letter) {
        this.build_letter = build_letter;
    }

    public String getApart_num() {
        return apart_num;
    }

    public void setApart_num(String apart_num) {
        this.apart_num = apart_num;
    }

    public Date getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(Date dateStamp) {
        this.dateStamp = dateStamp;
    }

    public void setValue_Sum(String value_Sum) {
        this.value_Sum = value_Sum;
    }

    public String getValue_T1() {
        return value_T1;
    }

    public void setValue_T1(String value_T1) {
        this.value_T1 = value_T1;
    }

    public String getValue_T2() {
        return value_T2;
    }

    public void setValue_T2(String value_T2) {
        this.value_T2 = value_T2;
    }

    public String getValue_T3() {
        return value_T3;
    }

    public void setValue_T3(String value_T3) {
        this.value_T3 = value_T3;
    }

    public String getTariffCount() {
        return tariffCount;
    }

    public void setTariffCount(String tariffCount) {
        this.tariffCount = tariffCount;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLs() {
        return ls;
    }

    public void setLs(String ls) {
        this.ls = ls;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKtt() {
        return Ktt;
    }

    public void setKtt(String ktt) {
        this.Ktt = ktt;
    }
}
