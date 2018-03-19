package com.mindata.blockchain.core.bean;

import java.util.List;

/**
 * @author wuweifeng wrote on 2018/3/19.
 */
public class MemberData {
    private int code;
    private String message;
    private List<Member> members;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
