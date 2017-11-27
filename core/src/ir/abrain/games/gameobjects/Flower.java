package ir.abrain.games.gameobjects;

import java.util.Random;

/**
 * Created by ghiassi on 12/3/15.
 */
public class Flower extends Scrollable {

    public Flower(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
    }
}
