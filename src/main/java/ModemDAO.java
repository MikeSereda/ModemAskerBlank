import java.io.IOException;
import java.util.HashMap;

public class ModemDAO {

    Modem modem;
    public ModemDAO(Modem modem){
        this.modem = modem;
    }
    public void saveValuesToDB() throws IOException {
        modem.refreshValues();
        HashMap<String, Object> map = modem.getValues();
        for (String item:map.keySet()){
            System.out.println(modem.getIp()+": "+item+": "+map.get(item));
        }
        System.out.println();
        System.out.println("------------------------------------------------------");
        System.out.println();
    }
}
