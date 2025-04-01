package pl.gamma.simulation;

import java.awt.BorderLayout;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.DefaultXYDataset;

public class GammaSimulationGUI extends JFrame {
    private JMenu fileMenu;
    private JMenu viewMenu;
    private JMenuItem saveItem;
    private JMenuItem loadItem;
    private JMenuItem newSimItem;
    private JCheckBoxMenuItem animItem;
    private JCheckBoxMenuItem graphItem;
    
    private JTextField thicknessField;
    private JTextField materialField;
    private JTextField absorptionField;

    private JButton startButton;
    private JButton stopButton;
    // private JButton saveButton;
    // private JButton newSimButton;

    public GammaSimulationGUI() {
        setTitle("Symulacja Gamma");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(new BorderLayout());

        //Wykres po lewej stronie
        ChartPanel chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.CENTER);

        //Panel sterowania po prawej stronie
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.EAST);

        //Menu
        JMenuBar menuBar = createJMenuBar();
        setJMenuBar(menuBar);
    }

    private JMenuBar createJMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        fileMenu = new JMenu("Plik");
        viewMenu = new JMenu("Widok");

        //plik
        saveItem = new JMenuItem("Zapisz");
        loadItem = new JMenuItem("Wczytaj");
        newSimItem = new JMenuItem("Nowa symulacja");

        //widok
        animItem = new JCheckBoxMenuItem("Animacja");
        graphItem = new JCheckBoxMenuItem("Wykres", true);

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(newSimItem);
        viewMenu.add(animItem);
        viewMenu.add(graphItem);
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);

        setJMenuBar(menuBar);
        return menuBar;
    }

    private ChartPanel createChartPanel() {
        XYDataset dataset = createDataset();
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Wykres zależności kwantów od grubości",
            "Grubość [cm]",
            "Liczba kwantów",
            dataset
        );

        return new ChartPanel(chart);
    }

    //docelowo dataset bedzie generowany (realtime) w zaleznosci od symulacji
    //na razie jest to tylko przykladowy dataset
    //zeby wykres byl widoczny
    private XYDataset createDataset() {
        DefaultXYDataset dataset = new DefaultXYDataset();
        double data[][] = {
            {0, 1, 2, 3, 4, 5},
            {25, 16, 9, 4, 1, 0}
        };
        dataset.addSeries("Symulacja", data);
        return dataset;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //odpowiednie ustawienia celu wyskalowania
        //...

        JLabel paramsLabel = new JLabel("Parametry symulacji:");
        
        JLabel thicknessLabel = new JLabel("Grubość x:");
        thicknessField = new JTextField(10);

        JLabel materialLabel = new JLabel("Materiał:");
        materialField = new JTextField(10);

        JLabel absorptionLabel = new JLabel("Absorpcja μ:");
        absorptionField = new JTextField(10);

        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        // saveButton = new JButton("Zapisz");
        // newSimButton = new JButton("Nowa symulacja");

        //dodanie listenerow
        //...

        panel.add(paramsLabel);
        panel.add(thicknessLabel);
        panel.add(thicknessField);
        panel.add(materialLabel);
        panel.add(materialField);
        panel.add(absorptionLabel);
        panel.add(absorptionField);

        panel.add(startButton);
        panel.add(stopButton);
        // panel.add(saveButton);
        // panel.add(newSimButton);

        return panel;
    }

    public static void main(String[] args) {
        GammaSimulationGUI gui = new GammaSimulationGUI();
        gui.setVisible(true);
    }
}