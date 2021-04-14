package ewhamenu.com.demo.DB;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB_Test {
    @Test
    public void test() throws Exception {
        Class.forName("org.mariadb.jdbc.Driver"); // 마리아DB

        Connection con = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/test", "root", "0909");// 마리아DB

        System.out.println(con);
    }
}
