package wadge;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {
        
        SpringApplication.run(Application.class, args);
        System.out.println("" +
                " _          _\n" +
                "( )   ()   ( )\n" +
                "(  )  ()  (  )\n" +
                " (  ) () (  )\n" +
                "  (        )\n" +
                "   (      ) ___      ___      ___ \n" +
                "    (____)  \\  \\    /   \\    /  /_____       __\n" +
                "   /      \\  \\  \\  /     \\  /  /|____ \\  ___|  | ___ __  ____\n" +
                "  |        |  \\  \\/   _   \\/  / /  _   |/  _   |/  _`  |/ __ \\\n" +
                "  |  |  |  |   \\     / \\     / |  (_|  |  (_|  |  (_|  |  ___/\n" +
                "  |  |  |  |    \\___/   \\___/   \\____,_|\\____,_|\\___,  |\\____|\n" +
                "   \\/ \\/ \\/                                      |____/\n");
    }
}
