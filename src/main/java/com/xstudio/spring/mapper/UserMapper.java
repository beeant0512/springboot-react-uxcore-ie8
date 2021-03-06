package com.xstudio.spring.mapper;

import com.xstudio.common.AppUserDetails;
import com.xstudio.common.IBaseDao;
import com.xstudio.spring.model.Role;
import com.xstudio.spring.model.User;
import com.xstudio.spring.vo.MenuVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends IBaseDao<User> {
    AppUserDetails selectByUsername(String username);
}