public class ModemThread extends Thread{
    private int id;
    public ModemThread(int id){
        this.id = id;
    }
    public void run(){
        Modem modem = new Modem(id+"");
        while (true)
        {
            long millis = System.currentTimeMillis();
            ModemDAO modemDAO = new ModemDAO();
            modemDAO.saveValuesToDB(modem);
            while (System.currentTimeMillis() - millis < 8000) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
