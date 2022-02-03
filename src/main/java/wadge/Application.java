package wadge;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class Application {
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_ORANGE = "\u001B[33m";
    private static final String	HIGH_INTENSITY = "\u001B[1m";
    private static final String ANSI_RESET = "\u001B[0m";
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
        System.out.println(HIGH_INTENSITY +ANSI_GREEN  +
                " _          _\n" +
                "( )   ()   ( )\n" +
                "(  )  ()  (  )\n" +
                " (  ) () (  )\n" +
                "  (        )\n" +
                "   (      ) "+ANSI_ORANGE+"___      ___      ___ \n" +ANSI_GREEN+
                "    (____)"+ANSI_ORANGE+"  \\  \\    /   \\    /  /_____       __\n" +ANSI_GREEN+
                "   /      \\ "+ANSI_ORANGE+" \\  \\  /     \\  /  /|____ \\  ___|  | ___ __  ____\n" +ANSI_GREEN+
                "  |        | "+ANSI_ORANGE+" \\  \\/   _   \\/  / /  _   |/  _   |/  _`  |/ __ \\\n" +ANSI_GREEN+
                "  |  |  |  |  "+ANSI_ORANGE+" \\     / \\     / |  (_|  |  (_|  |  (_|  |  ___/\n" +ANSI_GREEN+
                "  |  |  |  |  "+ANSI_ORANGE+"  \\___/   \\___/   \\____,_|\\____,_|\\___,  |\\____|\n" +ANSI_GREEN+
                "   \\/ \\/ \\/  "+ANSI_ORANGE+"                                    |____/\n" + ANSI_RESET);
    }
}
