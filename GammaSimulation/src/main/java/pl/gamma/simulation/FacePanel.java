package pl.gamma.simulation;

import javax.swing.*;
import java.awt.*;

public class FacePanel extends JPanel {
    private int totalSteps = 10;
    private double currentStep = 0;
    private int initialSize = 80;
    private int finalSize = 20;
    private double scale = 1.0;


    public void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
        repaint();
    }    

    public void updateStep(double step, double scale) {
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

        // Parametry osi
        int padding = 30;
        int lineY = getHeight() - 60;
        int stepWidth = (getWidth() - 2 * padding) / totalSteps;

        // Oblicz odstęp etykiet (np. co 10 dla 100 kroków, co 100 dla 1000 itd.)
        int labelInterval = Math.max(1, totalSteps / 10);

        // Rysuj oś X
        g2d.setColor(Color.BLACK);
        g2d.drawLine(padding, lineY, getWidth() - padding, lineY);

        // Tiki i etykiety osi (tylko co labelInterval)
        for (int i = 0; i <= totalSteps; i++) {
            if (i % labelInterval == 0 || i == totalSteps) {
                int x = padding + i * stepWidth;
                // Tik na osi
                g2d.drawLine(x, lineY - 5, x, lineY + 5);
                // Etykieta osi
                String label = Integer.toString(i);
                FontMetrics fm = g2d.getFontMetrics();
                int labelWidth = fm.stringWidth(label);
                g2d.drawString(label, x - labelWidth / 2, lineY + 20);
            }
        }
    


        // Emotikon
        int faceSize = (int) (initialSize * scale);
        faceSize = Math.max(finalSize, faceSize);

        int faceX = padding + (int)(currentStep * stepWidth) - faceSize / 2;
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
        if(scale >= 0.5){
            g2d.drawArc(faceX + faceSize / 4, faceY + faceSize / 2, smileWidth, smileHeight, 0, -180);
        }
        else{
            g2d.drawArc(faceX + faceSize / 4, faceY+3 + faceSize / 2, smileWidth, smileHeight, 0, 180);
        }
    }
}
