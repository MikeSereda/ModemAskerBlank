public class Modem {
    private String ip;
    private int rsl;
    private float ebNo;
    private long timestamp;

    public Modem(String ip){
        this.ip = ip;
    }

    private void getValuesFromPage(){
        System.out.println(ip+" modem gets values from page");
        //connect to modem by IP
    }
    public void prepareValues(){
        getValuesFromPage();
        timestamp = System.currentTimeMillis();
        //somthing else
        System.out.println(ip+" modem added common values from System");
    }

    public String getIp() {
        return ip;
    }
}
