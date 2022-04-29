import java.util.Scanner;

public class ConfiguringThread extends Thread{

    public void run(){
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        if (str.equals("stop")){
            Main.startFlag=false;
            System.out.println(str+" confirmed");
        }
    }
}
