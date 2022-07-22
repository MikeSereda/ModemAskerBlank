import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.TimerTask;

public class ModemTimerTask extends TimerTask {

    private final ModemDAO modemDAO;

    public ModemTimerTask(Modem modem, JdbcTemplate jdbcTemplate) {
        modemDAO = new ModemDAO(modem,jdbcTemplate);
        modemDAO.setModemTimerTask(this);
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
