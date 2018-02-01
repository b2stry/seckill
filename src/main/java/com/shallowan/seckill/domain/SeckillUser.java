package com.shallowan.seckill.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author ShallowAn
 */
@Data
public class SeckillUser {
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;

    @Override
    public String toString() {
        return "SeckillUser{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", head='" + head + '\'' +
                ", tegisterDate=" + registerDate +
                ", lastLoginDate=" + lastLoginDate +
                ", loginCount=" + loginCount +
                '}';
    }
}
