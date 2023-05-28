package com.KoreaIT.smw.demo.vo;

import lombok.Data;

@Data
public class member_club {
    private int id;
    private String regDate;
    private int clubId;
    private int memberId;
    private String purpose;
    private int authLevel;
    
    private String name;
}
