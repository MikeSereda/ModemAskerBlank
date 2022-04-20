import java.util.Arrays;
import java.util.Scanner;

public class ConsoleInteraction {
    boolean stop_server_command_flag;
    String stop_server_command = "stop server";
    Scanner scanner = new Scanner(System.in);

    public void run(){
        System.out.println("server commands:");
        System.out.println(stop_server_command + " //stop server");
        System.out.println("add cdm modem [ip] [name] [db_name] //creates new thread for CDM570L");

        while (!stop_server_command_flag){

            String command = scanner.nextLine();
            if (command.equalsIgnoreCase(stop_server_command)){
//                for (Thread thread:modemThreads){
//                    System.out.println(thread.toString());
//                    //thread.stop();
//                }
                stop_server_command_flag = true;
            }
            else {
                if (command.indexOf("add cdm modem")==0){
                    String[] parts = command.substring(14).split(" ");
                    System.out.println(Arrays.toString(parts));
                    //modemThreads.add(new ModemThread(modemThreads.size(), parts[0],jdbcTemplate));
                    //System.out.println(Arrays.toString(modemThreads.toArray()));
                }
                else{
                    System.out.println("server commands:");
                    System.out.println(stop_server_command + " //stop server");
                    System.out.println("add cdm modem [ip] [name] [db_name] //creates new thread for CDM570L");
                }
            }
        }
    }
}
