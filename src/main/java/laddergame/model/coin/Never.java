package laddergame.model.coin;

public class Never implements Coin {
    @Override
    public boolean toss() {
        return false;
    }
}
