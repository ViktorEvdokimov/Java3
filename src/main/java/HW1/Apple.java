package HW1;

public class Apple extends Fruit{
    private final double defaultWeight = 1.0;
    private double weight;

    public Apple(double weight) {
        this.weight = weight;
    }

    public Apple() {
        this.weight=defaultWeight;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public String getType() {
        return "Apple";
    }
}
