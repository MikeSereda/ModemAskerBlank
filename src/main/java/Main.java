import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

public class Main {

    static boolean startFlag = true;

    public static void main(String[] args){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        final JdbcTemplate jdbcTemplate;
        String url;
        int port;
        String dbName;
        boolean isRealDevices;
        dataSource.setDriverClassName("org.postgresql.Driver");

        if (System.getenv("ASKER_DB_ADDR")==null){
            url = "localhost";
        }
        else url = System.getenv("ASKER_DB_ADDR");
        if (System.getenv("ASKER_DB_PORT")==null){
            port=5432;
        }
        else port = Integer.parseInt(System.getenv("ASKER_DB_PORT"));
        if (System.getenv("ASKER_DB_NAME")==null){
            dbName = "sau_rest";
        }
        else dbName = System.getenv("ASKER_DB_NAME");
        if (System.getenv("ASKER_REAL_DEVICES")==null){
            isRealDevices = false;
        }
        else isRealDevices = Boolean.parseBoolean(System.getenv("ASKER_REAL_DEVICES"));

        System.out.println("dataSource location is "+url+":"+port+"/"+dbName);
        dataSource.setUrl("jdbc:postgresql://"+url+":"+port+"/"+dbName);
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");
        jdbcTemplate = new JdbcTemplate(dataSource);
        Timer timer = new Timer();
        ApplicationTask applicationTask = new ApplicationTask(jdbcTemplate,isRealDevices);
        timer.scheduleAtFixedRate(applicationTask,0, 1000);
    }
}
