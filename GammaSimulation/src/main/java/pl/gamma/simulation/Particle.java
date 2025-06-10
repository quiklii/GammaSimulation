package pl.gamma.simulation;

import java.util.Random;

public class Particle {
    private double count;
    Random rng = new Random();

    public Particle(int count) {
        this.count = count;
    }

    public double getCount() {
        return count;
    }

    public void absorb(double absorptionCoefficient) {
    
        int survivors = 0;
        double p = Math.exp(-absorptionCoefficient);
    
        for (int i = 0; i < count; i++) {    
            if (rng.nextDouble() < p) {
                survivors++;
            }
        }
    
        count = survivors;
    }
}
