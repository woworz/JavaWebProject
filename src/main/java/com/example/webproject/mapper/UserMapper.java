package com.example.webproject.mapper;
import com.example.webproject.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}
