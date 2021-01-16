package Configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"DTO", "DAO"})
@EnableTransactionManagement
public class DBConfig {
    private String driverClassName = "com.mysql.cj.Driver";
    private String dbUrl = "jdbc:mysql://localhost:3306/testdb";
    private String userName = "test";
    private String password = "test";

    @Bean
    public DataSource dataSource() {
        BasicDataSource db = new BasicDataSource();
        db.setDriverClassName(driverClassName);
        db.setUrl(dbUrl);
        db.setUsername(userName);
        db.setPassword(password);

        return db;
    }
}
