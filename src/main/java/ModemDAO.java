import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

public class ModemDAO {

    private final JdbcTemplate jdbcTemplate;
    private Modem modem;
    private ModemTimerTask modemTimerTask;

    public ModemDAO(Modem modem, JdbcTemplate jdbcTemplate){
        this.modem = modem;
        this.jdbcTemplate = jdbcTemplate;
    }

    public ModemTimerTask getModemTimerTask() {
        return modemTimerTask;
    }

    public void setModemTimerTask(ModemTimerTask modemTimerTask) {
        this.modemTimerTask = modemTimerTask;
    }

    public Modem getModem() {
        return modem;
    }

    public void stopTimerTask(){
        modemTimerTask.cancel();
    }

    public void saveValuesToDB() throws IOException {
        modem.refreshValues();
        HashMap<String, Object> map = modem.getValues();
        jdbcTemplate.update("INSERT INTO parameters VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                modem.getId(),
                map.get("timestampWotz"),
                map.get("temperature"),
                map.get("rsl"),
                map.get("ebNo"),
                map.get("ber"),
                map.get("ebNoRemote"),
                map.get("txPowerLevelIncrease"),
                map.get("unitAlarm"),
                map.get("txAlarm"),
                map.get("rxAlarm"),
                map.get("oduAlarm"));
        for (String item:map.keySet()){
            System.out.println(modem.getIp()+": "+item+": "+map.get(item));
        }
        System.out.println();
        System.out.println("------------------------------------------------------");
        System.out.println();
    }
}
