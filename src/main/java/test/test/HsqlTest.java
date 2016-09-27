package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    @Test
    public void test() {
        List<Map<String, Object>> userList = jdbcTemplate.queryForList("select * from swf.users");
        for(Map<String, Object> user : userList) {
            System.out.println(user.get("id"));
            System.out.println(user.get("username"));
            System.out.println(user.get("password"));
        }
    }
}
