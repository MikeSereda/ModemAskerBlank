import java.util.ArrayList;

import java.util.List;

public class Main {
    public static void main(String[] args){
        List<ModemThread> threads = new ArrayList<>();
        for (int i=0; i<4; i++){
            long millis = System.currentTimeMillis();
            threads.add(new ModemThread(i+1));
            threads.get(i).start();
            while (System.currentTimeMillis() - millis < 1000) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("All threads runned");
    }
}
