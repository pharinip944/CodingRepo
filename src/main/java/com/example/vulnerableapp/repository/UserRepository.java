package com.example.vulnerableapp.repository;
import com.example.vulnerableapp.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class UserRepository {
private final JdbcTemplate jdbcTemplate;
public UserRepository(JdbcTemplate jdbcTemplate){this.jdbcTemplate=jdbcTemplate;}
public List<User> findUserByUsername(String username){
String sql="SELECT * FROM users WHERE username=?";
return jdbcTemplate.query(sql, new Object[]{username}, (rs,rowNum)->{
User u=new User();
u.setId(rs.getInt("id"));
u.setUsername(rs.getString("username"));
u.setPassword(rs.getString("password"));
return u;
});
}
}