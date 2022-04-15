import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ModemThread extends Thread{
    private final int id;
    public ModemThread(int id){
        this.id = id;
    }
    public void run(){
        Modem modem = new Modem("cdm html/Modem Status"+id+".html");
        while (true)
        {
            long millis = System.currentTimeMillis();
            ModemDAO modemDAO = new ModemDAO(modem);
            try {
                modemDAO.saveValuesToDB();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            while (System.currentTimeMillis() - millis < 8000) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
