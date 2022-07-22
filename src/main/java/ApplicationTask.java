import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

public class ApplicationTask extends TimerTask {
    private final JdbcTemplate template;
    private final boolean isRealDevices;
    private HashMap<String,ModemDAO> modemDAOHashMap;
    private HashMap<String,ModemTimerTask> modemTimerTaskHashMap;
    private Timer timer;

    public ApplicationTask(JdbcTemplate template, boolean isRealDevices) {
        modemDAOHashMap = new HashMap<>();
        modemTimerTaskHashMap = new HashMap<>();
        timer = new Timer(false);
        this.template = template;
        this.isRealDevices = isRealDevices;
    }

    @Override
    public void run() {
        ArrayList<String> refreshedModemKeyList = new ArrayList<>();
        ArrayList<String> differModemKeyList = new ArrayList<>();

        List<Modem> modems = template.query("SELECT * FROM devices",new ModemRowMapper(isRealDevices));
        for (Modem modem : modems){
            refreshedModemKeyList.add(modem.getId());
            if (!modemDAOHashMap.containsKey(modem.getId())){
                modemDAOHashMap.put(modem.getId(), new ModemDAO(modem,template));
                modemTimerTaskHashMap.put(modem.getId(), new ModemTimerTask(modem,this.template));
                timer.scheduleAtFixedRate(modemTimerTaskHashMap.get(modem.getId()),0,1000);
                modemDAOHashMap.get(modem.getId()).setModemTimerTask(modemTimerTaskHashMap.get(modem.getId()));

            }
        }

        modemDAOHashMap.forEach((key,value)->{
            if (!refreshedModemKeyList.contains(key)){
                differModemKeyList.add(key);
            }
        });
        differModemKeyList.forEach((key)->{
            modemDAOHashMap.get(key).stopTimerTask();
            modemTimerTaskHashMap.remove(key);
            modemDAOHashMap.remove(key);
        });
    }
}
