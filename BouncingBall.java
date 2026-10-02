import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BouncingBall extends JPanel implements ActionListener {
    // Ball position and speed
    private int x = 50;
    private int y = 50;
    private int dx = 4;
    private int dy = 4;
    private final int DIAMETER = 30;

    // Track click count
    private int score = 0;

    public BouncingBall() {
        setBackground(Color.BLACK);

        // Add mouse listener to detect clicks on the ball
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Find center point of the ball
                int centerX = x + DIAMETER / 2;
                int centerY = y + DIAMETER / 2;

                // Calculate Euclidean distance between click point and ball center
                double distance = Math.hypot(e.getX() - centerX, e.getY() - centerY);

                // If click is within the ball's radius, increment score & move ball
                if (distance <= DIAMETER / 2.0) {
                    score++;
                    teleportToRandomSpot();
                }
            }
        });

        // Timer triggers roughly every 16ms (~60 FPS)
        Timer timer = new Timer(16, this);
        timer.start();
    }

    private void teleportToRandomSpot() {
        int maxX = Math.max(0, getWidth() - DIAMETER);
        int maxY = Math.max(0, getHeight() - DIAMETER);

        x = (int) (Math.random() * maxX);
        y = (int) (Math.random() * maxY);

        dx *= (int)(Math.pow(-1,(int)Math.random()*2));
        dy *= (int)(Math.pow(-1,(int)Math.random()*2));

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the bouncing ball
        g2d.setColor(Color.CYAN);
        g2d.fillOval(x, y, DIAMETER, DIAMETER);

        // Draw the click count text
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Score: " + score, 20, 35);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Bounce off left and right boundaries
        if (x + dx < 0 || x + dx + DIAMETER > getWidth()) {
            dx = -dx;
        }

        // Bounce off top and bottom boundaries
        if (y + dy < 0 || y + dy + DIAMETER > getHeight()) {
            dy = -dy;
        }

        x += dx;
        y += dy;

        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bouncing Ball Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new BouncingBall());
            frame.setSize(500, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}