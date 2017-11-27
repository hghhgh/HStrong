package ir.abrain.games.gameobjects;

/**
 * Created by ghiassi on 12/3/15.
 */
public class Dirt extends Scrollable {

    public Dirt(float x, float y, float width, float height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
    }
}