package test;

import com.ibatis.sqlmap.client.SqlMapClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by seongdonghun on 2016. 9. 27..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class HsqlTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SqlMapClient sqlMapClient;

    @Test
    public void test() throws SQLException {
        //List<Map<String, Object>> userList = jdbcTemplate.queryForList("select * from swf.users");

        List<Map<String, Object>> userList = sqlMapClient.queryForList("Common.selectList", null);

        for(Map<String, Object> user : userList) {
            System.out.println(user.get("ID"));
            System.out.println(user.get("USERNAME"));
            System.out.println(user.get("PASSWORD"));
        }
    }
}
