package com.example.sql_academy_airport.dto.output;

public class SpendsOnFun {
    private String status;
    private String memberName;
    private Integer costs;

    public SpendsOnFun() {
    }

    public SpendsOnFun(String status, String memberName, Integer costs) {
        this.status = status;
        this.memberName = memberName;
        this.costs = costs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getCosts() {
        return costs;
    }

    public void setCosts(Integer costs) {
        this.costs = costs;
    }
}
