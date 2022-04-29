import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ArrayList;
import java.util.List;

public class Main {

    static boolean startFlag = true;

    public static void main(String[] args){


        List<ModemTimerTask> modemTimerTasks = new ArrayList<>();
        List<ModemTimer> modemTimers = new ArrayList<>();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        final JdbcTemplate jdbcTemplate;

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/newcdmtest");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");
        jdbcTemplate = new JdbcTemplate(dataSource);

        for (int i=0;i<8;i++){
            modemTimerTasks.add(new ModemTimerTask(i+1,"http://192.168.100.11"+(i+1)+"/status.htm",jdbcTemplate));
            modemTimers.add(new ModemTimer("CDM 11"+(i+1)));
            modemTimers.get(i).schedule(modemTimerTasks.get(i),i*1000,15000);

        }

//        ConfiguringThread configuringThread = new ConfiguringThread();
//        configuringThread.start();
    }
}
