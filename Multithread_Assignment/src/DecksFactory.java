import java.util.ArrayList;

public class DecksFactory extends CardsFactory{
    private final Color[] COLORS = {Color.RED, Color.BLACK};
    private final String[] SHAPES = {"Spades", "Clubs", "Diamonds", "Hearts"};
    private final String[] VALUES = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    private int numberOfDecks = 1;

    public  ArrayList<Card> createDeck(int numberOfDecks) {
        this.numberOfDecks = numberOfDecks;
        return create();
    }

    @Override
    public ArrayList<Card> create() {
        ArrayList<Card> deck = new ArrayList<>();
        for(int i = 0; i < numberOfDecks; ++i){
            for (String shape : SHAPES) {
                Color color;
                if(shape.equals("Diamonds") || shape.equals("Hearts")){
                    color = COLORS[0];
                }
                else {
                    color = COLORS[1];
                }

                for (String value : VALUES) {
                    deck.add(new PlayingCard(shape, value, color));
                }
            }
        }
        deck.add(new JokerCard("Joker", "Joker"));

        return deck;
    }
}
