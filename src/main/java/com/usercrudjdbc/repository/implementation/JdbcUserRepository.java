package com.usercrudjdbc.repository.implementation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.usercrudjdbc.model.entity.User;
import com.usercrudjdbc.repository.interfaces.UserRepository;

@Repository
public class JdbcUserRepository implements UserRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
    public User save(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            return ps;
        }, keyHolder);
        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    @Override
    public Optional<User> findOne(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), id);
        if(users.isEmpty())
            return Optional.empty();
        else
            return Optional.of(users.get(0));
    }


    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(), user.getId());
        return user;
    }
    
    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.query(sql, (ResultSet rs) -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }, new Object[] { email } );
        return count > 0;
    }


    private final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getLong("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
        }
    }


	@Override
	public boolean existsByEmailWithNotThisId(String email, Long id) {
		String sql = "SELECT COUNT(*) FROM users WHERE email = ? and id != ?";
        Integer count = jdbcTemplate.query(sql, (ResultSet rs) -> {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }, new Object[] { email, id } );
        return count > 0;
	}
}
