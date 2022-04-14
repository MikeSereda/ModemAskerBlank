public class ModemDAO {
    public ModemDAO(){

    }
    public void saveValuesToDB(Modem modem){
        modem.refreshValues();
        //saving values to DB
        System.out.println("From "+modem.getIp()+": Imitation of saving data to DB. Inserted values "+modem.getEbNo()+" "
                +modem.getRsl()+" "+modem.getEbNoRemote()+" "+modem.getTxPowerLevelIncrease()+" "+modem.getTemperature()
                +" "+modem.getRxAlarm()+" "+modem.getTxAlarm()+" "+modem.getOduAlarm()+" "+modem.getUnitAlarm());
    }
}
