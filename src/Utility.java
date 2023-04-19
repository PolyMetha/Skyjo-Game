import java.util.Scanner;

public class Utility {

    static short controlInt(short borne1, short borne2, String message1, String message2)
    {
        short indice = 0;
        while (indice < borne1 || indice > borne2) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(message1);
            indice = scanner.nextShort();
            if(indice < borne1 || indice > borne2) {
                System.out.println(message2);
            }
        }
        return indice;
    }
}
