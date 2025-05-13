package gamma;

public class Particle {
    private int count;

    public Particle(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void absorb(double absorptionCoefficient) {
        count -= (int) (count * absorptionCoefficient);
    }
}

