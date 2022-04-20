import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args){
        List<ModemThread> modemThreads = new ArrayList<>();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/newcdmtest");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        boolean stop_server_command_flag = false;
//        Scanner scanner = new Scanner(System.in);
//        String stop_server_command = "stop server";


        for (int i=0; i<4; i++){
            long millis = System.currentTimeMillis();
            modemThreads.add(new ModemThread(i+1, "192.168.100.11"+(i+1)+"/status.htm",jdbcTemplate));
            modemThreads.get(i).start();
            while (System.currentTimeMillis() - millis < 1000) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("All threads runned");
    }
}
