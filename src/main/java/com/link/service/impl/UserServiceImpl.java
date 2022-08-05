package com.link.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.link.common.Constants;
import com.link.controller.dto.UserDTO;
import com.link.entity.User;
import com.link.exception.ServiceException;
import com.link.mapper.UserMapper;
import com.link.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Link
 * @since 2022-08-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer deleteBatch(List<Integer> ids) {
        return userMapper.deleteBatchIds(ids);
    }

    @Override
    public UserDTO login(UserDTO user) {
        String username = user.getUsername();
        String password = user.getPassword();
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", username);
        qw.eq("password", password);
        User u = null;
        try {
            u = userMapper.selectOne(qw);
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        if (u != null) {
            BeanUtil.copyProperties(u, user, true);
            return user;
        } else {
            throw new ServiceException(Constants.CODE_500, "用户名或错误");
        }

    }
}