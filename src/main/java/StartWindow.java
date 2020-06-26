import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartWindow {
    private JFrame frame;
    private JTextField txtSomething;
    private DataLoader dl;

    /**
     * Create the application.
     */
    public StartWindow(DataLoader dl) {
        this.dl = dl;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 830, 449);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Panel panel = new Panel();
        panel.setBackground(SystemColor.control);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JLabel lblWelcomeToWheretofly = new JLabel("Explore Zurich");
        lblWelcomeToWheretofly.setBounds(10, 54, 566, 25);
        panel.add(lblWelcomeToWheretofly);
        lblWelcomeToWheretofly.setVerticalAlignment(SwingConstants.TOP);
        lblWelcomeToWheretofly.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblWelcomeToWheretofly.setHorizontalAlignment(SwingConstants.CENTER);

        // SHOW ANALYTICS BUTTON
        JButton btnShowAnalytics = new JButton("Button 1");
        btnShowAnalytics.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button 1");
                //PDI_AnalyticsServiceMysql asm = new PDI_AnalyticsServiceMysql(/*db_session*/);
                //asm.displayAnalytics();   //older
                //asm.generateVisualization();
            }
        });
        btnShowAnalytics.setBounds(364, 124, 139, 34);
        panel.add(btnShowAnalytics);

        // Explore button
        JButton btnExploreFlights = new JButton("Button 2");
        btnExploreFlights.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button 2");
                // frame.setVisible(false);
                // PDI_ExploreFlightsWindow sf = new PDI_ExploreFlightsWindow(/*db_session*/);
            }
        });
        btnExploreFlights.setBounds(364, 194, 139, 34);
        panel.add(btnExploreFlights);

        JLabel lblClickHereTo = new JLabel("Click here to click button 1:");
        lblClickHereTo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblClickHereTo.setBounds(124, 134, 193, 13);
        panel.add(lblClickHereTo);

        JLabel lblClickHereTo_1 = new JLabel("Click here to click button 2:");
        lblClickHereTo_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblClickHereTo_1.setBounds(124, 205, 178, 13);
        panel.add(lblClickHereTo_1);

        frame.setPreferredSize(new Dimension(600, 350));
        frame.pack();
        frame.setVisible(true);
    }
}
