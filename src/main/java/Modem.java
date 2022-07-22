import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

public class Modem {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private boolean isDevice;

    private final String name;
    private final String ip;
    private String type;
    private String id;
    private int port;
    private String description;
    private String location;

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

    public Modem(String name, String ip, boolean isDevice){
        this.name = name;
        this.isDevice = isDevice;
        //this.ip = "cdm html/Modem Status"+id+".html";
        this.ip = ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private void getValuesFromPage() throws IOException {
        Document document;
        if (isDevice){
            System.out.println(ip+" modem gets values from page");
            document = Jsoup.connect(ip).get();
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
        }
        else {
            System.out.println("Modem gets values from static page");
            this.rsl = 65;
            this.ber = 7;
            this.temperature = 23;
            this.ebNo = BigDecimal.valueOf(7 + 1*(0.5-Math.random()))
                    .setScale(1, BigDecimal.ROUND_HALF_DOWN)
                    .floatValue();
            this.ebNoRemote = BigDecimal.valueOf(6.1 + 1*(0.5-Math.random()))
                    .setScale(1, BigDecimal.ROUND_HALF_DOWN)
                    .floatValue();
            this.txPowerLevelIncrease = (float) 2.1;
            this.unitAlarm = "Unit from file";
            this.txAlarm = "Tx from file";
            this.rxAlarm = "Rx from file";
            this.oduAlarm = "Odu from file";
        }

    }

    private int normalizeInteger(String inputValue){
        return Integer.parseInt(inputValue.replaceAll("[^0-9]",""));
    }

    private float normalizeFloat(String inputValue){
        if (inputValue.contains("Demod") || inputValue.contains("AUPC") || inputValue.contains("EDMAC") ){
            return -404;
        }
        else {
            if (inputValue.contains("E-")){
                float value = Float.parseFloat(inputValue);
                if (value<Float.parseFloat("1E-9"))
                    return 0;
                else
                    return ((float)(10+Math.log10(value)));
            }
            else {
                //System.out.println(inputValue);
                return Float.parseFloat(inputValue.replaceAll("[^0-9.,]",""));
            }
        }
    }

    public void refreshValues() throws IOException {
        getValuesFromPage();
        this.timestampWotz = (LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
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

    public String getName() {
        return name;
    }
}
