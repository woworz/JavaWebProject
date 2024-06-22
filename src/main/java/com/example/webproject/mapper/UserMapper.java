package com.example.webproject.mapper;
import com.example.webproject.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * UserMapper存放数据库操作指令
 */

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE id = #{id}")
    User getUserById(Long id);

    @Insert("INSERT INTO users(username, password) VALUES(#{username}, #{password})")
    void insertUser(User user);

    @Select("SELECT * FROM users WHERE username = #{username}")
    User getUserByUsername(String username);

    @Select("SELECT * FROM users")
    List<User> findAll();

    @Update("UPDATE users\n" +
            "        SET username = #{username}, password = #{password}\n" +
            "        WHERE id = #{id}")
    void updateUser(User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    void deleteUser(Long id);
}
