import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.HashMap;

public class ModemDAO {

    private final JdbcTemplate jdbcTemplate;
    Modem modem;

    public ModemDAO(Modem modem, JdbcTemplate jdbcTemplate){
        this.modem = modem;
        this.jdbcTemplate = jdbcTemplate;
    }
    public void saveValuesToDB() throws IOException {
        modem.refreshValues();
        HashMap<String, Object> map = modem.getValues();
        jdbcTemplate.update("INSERT INTO cdm11"+modem.getId()+" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                , map.get("ebNo"), map.get("unitAlarm"), map.get("txAlarm"), map.get("temperature"), map.get("rxAlarm"), map.get("rsl")
                , map.get("ber"), map.get("txPowerLevelIncrease"), map.get("oduAlarm"), map.get("ebNoRemote"), map.get("timestampWotz"));
        for (String item:map.keySet()){
            System.out.println(modem.getIp()+": "+item+": "+map.get(item));
        }
        System.out.println();
        System.out.println("------------------------------------------------------");
        System.out.println();
    }
}
