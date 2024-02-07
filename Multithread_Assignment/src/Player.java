import java.util.List;

public abstract class Player {
    protected  String name;
    protected  List<Card> hand;

    public Player(String name, List<Card> hand) {
        this.hand = hand;
        this.name = name;
    }

    public String getPlayerName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public abstract void play();

    public abstract boolean isLoser();
}
