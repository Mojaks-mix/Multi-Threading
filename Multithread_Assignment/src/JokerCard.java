public class JokerCard extends Card{
    private final String type;
    public JokerCard(String type, String value) {
        super("Joker");
        this.type = type;
    }

    public String toString() {
        return this.value + " of " + type;
    }
}
