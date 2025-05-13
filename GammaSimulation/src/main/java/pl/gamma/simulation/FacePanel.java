package pl.gamma.simulation;

import javax.swing.*;
import java.awt.*;

public class FacePanel extends JPanel {
    private int totalSteps = 10;
    private int currentStep = 0;
    private int initialSize = 80;
    private int finalSize = 20;
    private double scale = 1.0;

    public void updateStep(int step, double scale) {
        this.currentStep = step;
        this.scale = scale;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
       
        Graphics2D g2d = (Graphics2D) g;

        // Tło
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Wymiary i pozycje
        int padding = 30;
        int lineY = getHeight() - 60;
        int stepWidth = (getWidth() - 2 * padding) / (totalSteps);

        //Oś
        g2d.setColor(Color.BLACK);
        g2d.drawLine(padding, lineY, getWidth() - padding, lineY);

        for (int i = 0; i <= totalSteps; i++) {
            int x = padding + i * stepWidth;
            g2d.drawLine(x, lineY - 5, x, lineY + 5);
            g2d.drawString(Integer.toString(i), x - 5, lineY + 20);
        }

        // Emotikon
        int faceSize = (int) (initialSize * scale);
        faceSize = Math.max(finalSize, faceSize);

        int faceX = padding + currentStep * stepWidth - faceSize / 2;
        int faceY = lineY - 40 - faceSize;

        // Żółty 
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(faceX, faceY, faceSize, faceSize);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(faceX, faceY, faceSize, faceSize);

        // Kosmetyka
        int eyeSize = faceSize / 8;
        g2d.fillOval(faceX + faceSize / 4, faceY + faceSize / 3, eyeSize, eyeSize);
        g2d.fillOval(faceX + 3 * faceSize / 4 - eyeSize, faceY + faceSize / 3, eyeSize, eyeSize);
        int smileWidth = faceSize / 2;
        int smileHeight = faceSize / 6;
        g2d.drawArc(faceX + faceSize / 4, faceY + faceSize / 2, smileWidth, smileHeight, 0, -180);
    }
}
