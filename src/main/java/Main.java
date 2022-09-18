import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Main {

    static boolean startFlag = true;

    public static void main(String[] args) throws InterruptedException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        final JdbcTemplate jdbcTemplate;
        String url;
        int port;
        int dockerDelay;
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

        if (args.length>0){
            isRealDevices = Boolean.parseBoolean(args[0]);
        }
        if (System.getenv("ASKER_WAIT_FOR_DB_DELAY")==null){
            dockerDelay=15000;
        }
        else dockerDelay = Integer.parseInt(System.getenv("ASKER_WAIT_FOR_DB_DELAY"));

        System.out.println("dataSource location is "+url+":"+port+"/"+dbName);
        System.out.println("Waiting for db...");
        TimeUnit.MILLISECONDS.sleep(dockerDelay);
        System.out.println("Time is over, connecting...");
        dataSource.setUrl("jdbc:postgresql://"+url+":"+port+"/"+dbName);
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");
        jdbcTemplate = new JdbcTemplate(dataSource);
        Timer timer = new Timer();
        ApplicationTask applicationTask = new ApplicationTask(jdbcTemplate,isRealDevices);
        timer.scheduleAtFixedRate(applicationTask,0, 1000);
    }
}
