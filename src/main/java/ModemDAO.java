public class ModemDAO {
    public ModemDAO(){

    }
    public void saveValuesToDB(Modem modem){
        modem.prepareValues();
        //saving values to DB
        System.out.println(modem.getIp()+" modem fulfill all and data saved to DB");
    }
}
