package com.shallowan.seckill.service;

import com.shallowan.seckill.dao.UserDao;
import com.shallowan.seckill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ShallowAn
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getById(int id) {
        return userDao.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean tx() {
        User u1 = new User();
        u1.setId(2);
        u1.setName("22222");
        userDao.insert(u1);

        return true;
    }
}
