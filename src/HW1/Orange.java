package HW1;

public class Orange extends Fruit{
    private final double defaultWeight = 1.5;
    private double weight;

    public Orange(double weight) {
        this.weight = weight;
    }

    public Orange() {
        this.weight=defaultWeight;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public String getType() {
        return "Orange";
    }
}