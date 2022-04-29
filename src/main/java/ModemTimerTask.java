import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.TimerTask;

public class ModemTimerTask extends TimerTask {

    private final ModemDAO modemDAO;

    public ModemTimerTask(int modemId, String modemIp, JdbcTemplate jdbcTemplate) {
        modemDAO = new ModemDAO(new Modem(modemId,modemIp),jdbcTemplate);
    }

    @Override
    public void run() {
        try {
            modemDAO.saveValuesToDB();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
