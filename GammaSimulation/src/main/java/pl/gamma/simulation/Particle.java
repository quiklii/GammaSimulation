package pl.gamma.simulation;

public class Particle {
    private double count;

    public Particle(int count) {
        this.count = count;
    }

    public double getCount() {
        return count;
    }

    public void absorb(double absorptionCoefficient, double fraction) {
        count -= (count * absorptionCoefficient) * fraction;
    }
}

