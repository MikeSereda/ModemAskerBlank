import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Modem {
    private final int id;
    private final String ip;
    private int rsl;
    private int temperature;
    private float ebNo;
    private float ebNoRemote;
    private float txPowerLevelIncrease;
    private float ber;
    private String unitAlarm;
    private String txAlarm;
    private String rxAlarm;
    private String oduAlarm;
    private LocalDateTime timestampWotz;

    public Modem(int id, String ip){
        this.id = id;
        this.ip = "cdm html/Modem Status"+ip+".html";
    }

    private void getValuesFromPage() throws IOException {
        System.out.println(ip+" modem gets values from page");
        File file = new File(ip);
        Document document = Jsoup.parse(file, "UTF-8", "192.168.100.111");

        this.rsl = normalizeInteger(document.select("td").get(37).text());
        this.ber = normalizeFloat(document.select("td").get(25).text());
        this.temperature = normalizeInteger(document.select("td").get(27).text());
        this.ebNo = normalizeFloat(document.select("td").get(29).text());
        this.ebNoRemote = normalizeFloat(document.select("td").get(48).text());
        this.txPowerLevelIncrease = normalizeFloat(document.select("td").get(52).text());
        this.unitAlarm = document.select("td").get(6).text();
        this.txAlarm = document.select("td").get(10).text();
        this.rxAlarm = document.select("td").get(14).text();
        this.oduAlarm = document.select("td").get(18).text();


/*      HashMap<String,String> valuesMap = new HashMap<>();
        valuesMap.put("rsl",document.select("td").get(37).text());
        valuesMap.put("temperature",document.select("td").get(27).text());
        valuesMap.put("ebNo",document.select("td").get(29).text());
        valuesMap.put("ebNoRemote",document.select("td").get(48).text());
        valuesMap.put("txPowerLevelIncrease",document.select("td").get(52).text());
        valuesMap.put("unitAlarm", document.select("td").get(6).text());
        valuesMap.put("txAlarm",document.select("td").get(10).text());
        valuesMap.put("rxAlarm",document.select("td").get(14).text());
        valuesMap.put("oduAlarm",document.select("td").get(18).text());
        normalizeValues(valuesMap);*/
    }
/*    private void normalizeValues(HashMap<String, String> map){
        this.rsl = Integer.parseInt(map.get("rsl").replaceAll("[^0-9]",""));
        this.temperature = Integer.parseInt(map.get("temperature").replaceAll("[^0-9]",""));
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
    }*/

    private int normalizeInteger(String inputValue){
        return Integer.parseInt(inputValue.replaceAll("[^0-9]",""));
    }

    private float normalizeFloat(String inputValue){
        if (inputValue.contains("Demod") || inputValue.contains("AUPC") || inputValue.contains("EDMAC") ){
            return -404;
        }
        else {
            //System.out.println(inputValue);
            return Float.parseFloat(inputValue.replaceAll("[^0-9.,]",""));
        }
    }

    public void refreshValues() throws IOException {
        getValuesFromPage();
        this.timestampWotz = LocalDateTime.now();
        //somthing else
        System.out.println(ip+" modem added common values from System");
    }

    public HashMap<String,Object> getValues(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("rsl",rsl);
        map.put("temperature",temperature);
        map.put("ebNo",ebNo);
        map.put("ebNoRemote",ebNoRemote);
        map.put("ber", ber);
        map.put("txPowerLevelIncrease",txPowerLevelIncrease);
        map.put("unitAlarm",unitAlarm);
        map.put("txAlarm",txAlarm);
        map.put("rxAlarm",rxAlarm);
        map.put("oduAlarm",oduAlarm);
        map.put("timestampWotz",timestampWotz);
        return map;
    }

    public String getIp() {
        return ip;
    }

    public int getId() {
        return id;
    }
}
