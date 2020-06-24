
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("why");

        // Initialize the interface
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    StartWindow window = new StartWindow(/*db_session*/);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
