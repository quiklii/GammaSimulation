package pl.gamma.simulation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;

public class GammaSimulation extends JPanel {
    private Particle particle;
    private Timer timer;
    private double absorptionCoefficient;
    private int maxSteps;
    private int subSteps = 20; // liczba mikrokroków do płnnej animacji
    private int currentSubStep = 0;
    private int totalInitialParticles; // do skalowania

    private JTextField inputParticles;
    private JTextField inputCoefficient;
    private JTextField inputSteps;
    private JLabel statusLabel;

    private JButton startButton;
    private JButton stopButton;
    private JButton saveButton;

    private DefaultCategoryDataset dataset;
    private int step;
    private int initialCount = 1;
    
    private FacePanel facePanel;

    public GammaSimulation() {
        setLayout(new BorderLayout());

        // Panel sterujący
        JPanel controlPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        inputParticles = new JTextField("100");
        inputCoefficient = new JTextField("0.2");
        inputSteps = new JTextField("10");

        startButton = new JButton("Rozpocznij animację");
        stopButton = new JButton("Zatrzymaj animację");
        saveButton = new JButton("Zapisz do pliku");
        stopButton.setEnabled(false);
        saveButton.setEnabled(false);

        controlPanel.add(new JLabel("Liczba cząstek:"));
        controlPanel.add(inputParticles);
        controlPanel.add(new JLabel("Współczynnik pochłaniania (od 0 do 1):"));
        controlPanel.add(inputCoefficient);
        controlPanel.add(new JLabel("Liczba kroków:"));
        controlPanel.add(inputSteps);
        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        controlPanel.add(saveButton);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(controlPanel, BorderLayout.NORTH);
        add(rightPanel, BorderLayout.EAST);

        // Status i rysunek
        statusLabel = new JLabel("Symulacja nie została rozpoczęta.");
        add(statusLabel, BorderLayout.SOUTH);
        
        //Panel buzki
        facePanel = new FacePanel();
        facePanel.setPreferredSize(new Dimension(400,300));
        add(facePanel, BorderLayout.NORTH);

        // Wykres
        dataset = new DefaultCategoryDataset();
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Liczba cząstek w czasie",
                "Krok",
                "Cząstki",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        add(chartPanel, BorderLayout.CENTER);

        startButton.addActionListener(this::startSimulation);
        stopButton.addActionListener(this::stopSimulation);
        saveButton.addActionListener(this::saveToFile);
    }

    private void startSimulation(ActionEvent e) {
        try {
            int count = Integer.parseInt(inputParticles.getText());
            totalInitialParticles = count;
            absorptionCoefficient = Double.parseDouble(inputCoefficient.getText());
            maxSteps = Integer.parseInt(inputSteps.getText());
            
            if (absorptionCoefficient < 0 || absorptionCoefficient > 1 || count <= 0 || maxSteps <= 0) {
                JOptionPane.showMessageDialog(this, "Podaj poprawne dane wejściowe.");
                return;
            }

            particle = new Particle(count);
            statusLabel.setText("Symulacja trwa...");
            dataset.clear();
            step = 0;
            currentSubStep = 0;
            dataset.addValue(particle.getCount(), "Cząstki", Integer.toString(step));
            facePanel.updateStep(step, 1.0);
            
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }

           timer = new Timer(50, ev -> {
            if (step < maxSteps) {
                if (currentSubStep < subSteps) {
                    particle.absorb(absorptionCoefficient, 1.0 / subSteps);
                    currentSubStep++;

                    double scale = particle.getCount() / totalInitialParticles;
                    facePanel.updateStep(step + (double) currentSubStep / subSteps, scale);
                    statusLabel.setText(String.format("Pozostałe cząstki: %.2f", particle.getCount()));
                } else {
                    step++;
                    currentSubStep = 0;
                    dataset.addValue(particle.getCount(), "Cząstki", Integer.toString(step));
                }
            } else {
                timer.stop();
                statusLabel.setText("Symulacja zakończona. Pozostało: " + (int) particle.getCount());
                saveButton.setEnabled(true);
                stopButton.setEnabled(false);
            }
        });
            timer.start();
            saveButton.setEnabled(false);
            stopButton.setEnabled(true);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Wprowadź liczby.");
        }
    }
    
    private void stopSimulation (ActionEvent e) {
    	if (timer != null && timer.isRunning()) {
    		timer.stop();
    		statusLabel.setText("Symulacja przerwana na kroku:" + step);
    		stopButton.setEnabled(false);
    		saveButton.setEnabled(true);
    	}
    }

    private void saveToFile(ActionEvent e) {
        try (FileWriter writer = new FileWriter("symulacja_wyniki.txt")) {
            writer.write("Wyniki symulacji:\n");
            for (int i = 0; i <= step; i++) {
                Number value = dataset.getValue("Cząstki", Integer.toString(i));
                writer.write("Krok " + i + ": " + value + "\n");
            }
            writer.write("Współczynnik pochłaniania: " + absorptionCoefficient + "\n");
            statusLabel.setText("Wyniki zapisane do pliku symulacja_wyniki.txt.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Błąd zapisu do pliku.");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Symulacja Gamma 🙂");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setContentPane(new GammaSimulation());
        frame.setVisible(true);     
    }
    
}
