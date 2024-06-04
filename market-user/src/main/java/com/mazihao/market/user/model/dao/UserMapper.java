package com.mazihao.market.user.model.dao;

import com.mazihao.market.user.model.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    public int deleteByPrimaryKey(Integer id);

    public int insert(User record);

    public int insertSelective(User record);

    public User selectByPrimaryKey(Integer id);

    public int updateByPrimaryKeySelective(User record);

    public int updateByPrimaryKey(User record);

    public User selectByName(String username);

    public User selectLogin(@Param("username") String username, @Param("password") String password);
}