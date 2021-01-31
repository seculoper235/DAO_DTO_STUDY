package DAO;

import DTO.UserDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static DAO.DaoSqls.*;

@Repository
public class UserDao {
    private NamedParameterJdbcTemplate namedTemplate;
    private SimpleJdbcInsert simpleInsert;
    private RowMapper<UserDto> userMapper = BeanPropertyRowMapper.newInstance(UserDto.class);

    public UserDao(DataSource dataSource) {
        this.namedTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("user")
                .usingGeneratedKeyColumns("id");
    }

    public List<UserDto> selectAllUser() {
        return namedTemplate.query(SELECT_All, userMapper);
    }

    public UserDto selectUserById(int userId) {
        Map<String, Integer> userMap = new HashMap<>();
        userMap.put("userId", userId);
        return namedTemplate.queryForObject(SELECT_BY_ID, userMap, userMapper);
    }

    public int insertUser(UserDto user) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);
        // 단순히 실행하는 것만이 아닌, 입력한 객체의 키값을마도 반환함
        return (int) simpleInsert.executeAndReturnKey(params);
    }

    // 다수의 유저 insert 가능
    public int[] insertUserList(List<UserDto> userList) {
        // createBatch를 사용하여 SqlParameterSource 배열을 만듦
        SqlParameterSource[] paramList = SqlParameterSourceUtils.createBatch(userList.toArray());
        return simpleInsert.executeBatch(paramList);
    }

    public int updateUser(UserDto user) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);
        return namedTemplate.update(UPDATE, params);
    }

    // 다수의 유저 update도 가능(insertUserList와 비슷)
    public int[] updateUserList(List<UserDto> userList) {
        // createBatch를 사용하여 SqlParameterSource 배열을 만듦
        SqlParameterSource[] paramList = SqlParameterSourceUtils.createBatch(userList.toArray());
        return namedTemplate.batchUpdate(UPDATE, paramList);
    }

    public int deleteUserById(int userId) {
        Map<String, Integer> userMap = new HashMap<>();
        userMap.put("userId", userId);
        return namedTemplate.update(DELETE, userMap);
    }
}
