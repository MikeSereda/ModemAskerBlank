import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        List<ModemThread> modemThreads = new ArrayList<>();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/newcdmtest");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");

        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        boolean stop_server_command_flag = false;
        Scanner scanner = new Scanner(System.in);
        String stop_server_command = "stop server";
        System.out.println("server commands:");
        System.out.println(stop_server_command + " //stop server");
        System.out.println("add cdm modem [ip] [name] [db_name] //creates new thread for CDM570L");
        while (!stop_server_command_flag){

            String command = scanner.nextLine();
            if (command.equalsIgnoreCase(stop_server_command)){
                for (Thread thread:modemThreads){
                    System.out.println(thread.toString());
                    //thread.stop();
                }
                stop_server_command_flag = true;
            }
            else {
                if (command.indexOf("add cdm modem")==0){
                    String[] parts = command.substring(14).split(" ");
                    System.out.println(Arrays.toString(parts));
                    modemThreads.add(new ModemThread(modemThreads.size(), parts[0],jdbcTemplate));
                    System.out.println(Arrays.toString(modemThreads.toArray()));
                }
                else{
                    System.out.println("server commands:");
                    System.out.println(stop_server_command + " //stop server");
                    System.out.println("add cdm modem [ip] [name] [db_name] //creates new thread for CDM570L");
                }
            }
        }
//        for (int i=0; i<4; i++){
//            long millis = System.currentTimeMillis();
//            threads.add(new ModemThread(i+1, jdbcTemplate));
//            threads.get(i).start();
//            while (System.currentTimeMillis() - millis < 1000) {
//                try {
//                    TimeUnit.MILLISECONDS.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        System.out.println("All threads runned");
    }
}
