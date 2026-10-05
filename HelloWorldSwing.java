import javax.swing.*;
//javac --release 8 HelloWorldSwing.java
public class HelloWorldSwing {
    public static void main(String[] args) {
        // Run UI update on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hello World (Swing)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // Hide the title bar and window borders
            //frame.setUndecorated(true);
            JLabel label = new JLabel("Hello from Java Swing running in WebAssembly!", SwingConstants.CENTER);
            frame.add(label);
            
            frame.setSize(500, 400);
            frame.setVisible(true);
        });
    }
}