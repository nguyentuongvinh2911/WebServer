
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.concurrent.*;

public class GUIServer extends JFrame {

    public static JTextArea textArea;
    private static JButton stopButton;
    private static JButton startButton;
    private JPanel chatInputContainer;
    static ExecutorService resp;

    public GUIServer() {
        //set title bar
        super("LOG");
        //set dimensions
        setSize(500, 500);
        //determines the layout
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        //add some controls
        textArea = new JTextArea();
        textArea = new JTextArea(3, 20);
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        //add a input containers
        chatInputContainer = new JPanel();
        chatInputContainer.add(textArea);
        chatInputContainer.add(startButton);
        chatInputContainer.add(stopButton);
        stopButton.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        }
        );
        startButton.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    WebServer ws = new WebServer();
                    ws.start();
                } catch (IOException ex) {
                }
            }
        }
        );
        //add component to the layout
        add(chatInputContainer, BorderLayout.SOUTH);
        add(textArea, BorderLayout.CENTER);

        //to make the GUI appear
        setVisible(true);
    }
    // Setup the server

    public static void main(String[] args) {
        GUIServer server = new GUIServer();
        Listener listener = new Listener(textArea); //create instance of TextAreaPopulate method

        //create thread pool for this GUI with size of 100
        resp = Executors.newFixedThreadPool(100);

        //execute the GUI
        resp.execute(listener);
    }
}
