import java.util.ArrayList;
import java.util.Collections;

public abstract class CardGame {
    protected final int numPlayers;
    public static int currentPlayerIndex;
    public static ArrayList<Player> players;
    protected  ArrayList<Card> deck;

    public CardGame(int numPlayers, ArrayList<Player> players, ArrayList<Card> deck) {
        this.numPlayers = numPlayers;
        this.players = players;
        this.deck = deck;
        this.currentPlayerIndex = 0;
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public abstract void distributeCards();

    public abstract void startGame();

    public abstract void printResult();

}