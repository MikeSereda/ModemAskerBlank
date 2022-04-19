import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ModemThread extends Thread{
    private final int id;
    private final String ip;
    private final JdbcTemplate jdbcTemplate;
    boolean stopFlag = false;

    public ModemThread(int id, String ip, JdbcTemplate jdbcTemplate){
        this.id = id;
        this.ip = ip;
        this.jdbcTemplate = jdbcTemplate;
    }
    public void run(){
        Modem modem = new Modem(id, ip);
        while (!stopFlag)
        {
            long millis = System.currentTimeMillis();
            ModemDAO modemDAO = new ModemDAO(modem, jdbcTemplate);
            try {
                modemDAO.saveValuesToDB();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            while (System.currentTimeMillis() - millis < 8000) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
