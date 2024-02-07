public class PlayingCard extends Card{
    private final String shape;
    private final Color color;
    public PlayingCard(String shape, String value, Color color) {
        super(value);
        this.color = color;
        this.shape = shape;
    }
    public String getShape() {
        return shape;
    }

    public Color getColor(){
        return color;
    }

    public String toString() {
        return this.value + " of " + shape;
    }
}