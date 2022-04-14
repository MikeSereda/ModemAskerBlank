import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Modem {
    private final String ip;
    private int rsl;
    private int temperature;
    private float ebNo;
    private float ebNoRemote;
    private float txPowerLevelIncrease;
    private String unitAlarm;
    private String txAlarm;
    private String rxAlarm;
    private String oduAlarm;

    public Modem(String ip){
        this.ip = ip;
    }

    private void getValuesFromPage(){
        System.out.println(ip+" modem gets values from page");
        HashMap<String,String> valuesMap = new HashMap<>();
        valuesMap.put("rsl",null);
        valuesMap.put("temperature",null);
        valuesMap.put("ebNo",null);
        valuesMap.put("ebNoRemote",null);
        valuesMap.put("txPowerLevelIncrease",null);
        valuesMap.put("unitAlarm",null);
        valuesMap.put("txAlarm",null);
        valuesMap.put("rxAlarm",null);
        valuesMap.put("oduAlarm",null);
        normalizeValues2(valuesMap);
    }
    private void normalizeValues2(HashMap<String, String> map){
        this.rsl = Integer.parseInt(map.get("rsl").replaceAll("[^0-9]",""));
        this.temperature = Integer.parseInt(map.get("").replaceAll("[^0-9]",""));
        //--------------------------------------------------------------------------------------------------------------
        if (map.get("ebNo").contains("Demod")){
            this.ebNo = -404;
        }
        else {
            this.ebNo = Float.parseFloat(map.get("ebNo").replaceAll("[^0-9.,]",""));
        }
        //--------------------------------------------------------------------------------------------------------------
        if (map.get("ebNoRemote").contains("Demod")){
            this.ebNoRemote = -404;
        }
        else {
            this.ebNoRemote = Float.parseFloat(map.get("ebNoRemote").replaceAll("[^0-9.,]",""));
        }
        //--------------------------------------------------------------------------------------------------------------
        if (map.get("txPowerLevelIncrease").contains("AUPC")){
            this.txPowerLevelIncrease = -404;
        }
        else {
            this.txPowerLevelIncrease = Float.parseFloat(map.get("txPowerLevelIncrease").replaceAll("[^0-9.,]",""));
        }
        //--------------------------------------------------------------------------------------------------------------
        this.oduAlarm = map.get("oduAlarm");
        this.txAlarm = map.get("txAlarm");
        this.rxAlarm = map.get("rxAlarm");
        this.unitAlarm = map.get("unitAlarm");

        System.out.println(map.size()+" values has been normalized and assigned");
    }

    private void normalizeValues(String rslString, String temperatureString, String ebNoString,
                                String ebNoRemoteString, String txPowerLevelIncreaseString, String unitAlarmString,
                                String txAlarmString, String rxAlarmString, String oduAlarmString){
        this.rsl = Integer.parseInt(rslString.replaceAll("[^0-9]",""));
        this.temperature = Integer.parseInt(temperatureString.replaceAll("[^0-9]",""));
        //--------------------------------------------------------------------------------------------------------------
        if (ebNoString.contains("Demod")){
            this.ebNo = -404;
        }
        else {
            this.ebNo = Float.parseFloat(ebNoString.replaceAll("[^0-9.,]",""));
        }
        //--------------------------------------------------------------------------------------------------------------
        if (ebNoRemoteString.contains("Demod")){
            this.ebNoRemote = -404;
        }
        else {
            this.ebNoRemote = Float.parseFloat(ebNoRemoteString.replaceAll("[^0-9.,]",""));
        }
        //--------------------------------------------------------------------------------------------------------------
        if (txPowerLevelIncreaseString.contains("AUPC")){
            this.txPowerLevelIncrease = -404;
        }
        else {
            this.txPowerLevelIncrease = Float.parseFloat(txPowerLevelIncreaseString.replaceAll("[^0-9.,]",""));
        }
        //--------------------------------------------------------------------------------------------------------------
        this.oduAlarm = oduAlarmString;
        this.txAlarm = txAlarmString;
        this.rxAlarm = rxAlarmString;
        this.unitAlarm = unitAlarmString;
    }

    public void refreshValues(){
        getValuesFromPage();
        //somthing else
        System.out.println(ip+" modem added common values from System");
    }

    public int getRsl() {
        return rsl;
    }

    public int getTemperature() {
        return temperature;
    }

    public float getEbNo() {
        return ebNo;
    }

    public float getEbNoRemote() {
        return ebNoRemote;
    }

    public float getTxPowerLevelIncrease() {
        return txPowerLevelIncrease;
    }

    public String getUnitAlarm() {
        return unitAlarm;
    }

    public String getTxAlarm() {
        return txAlarm;
    }

    public String getRxAlarm() {
        return rxAlarm;
    }

    public String getOduAlarm() {
        return oduAlarm;
    }

    public String getIp() {
        return ip;
    }
}
