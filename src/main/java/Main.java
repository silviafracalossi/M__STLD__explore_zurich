
import java.awt.*;

public class Main {

    public static void main(String[] args) throws Exception {

        // DATA LOADER
        final DataLoader dl = new DataLoader();

        // INTERFACE
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                     StartWindow window = new StartWindow(dl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
