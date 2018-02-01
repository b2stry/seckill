package com.shallowan.seckill.domain;

import lombok.Data;

/**
 * @author ShallowAn
 */
@Data
public class User {
    private int id;
    private String name;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
