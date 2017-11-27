package ir.abrain.games.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import ir.abrain.games.helpers.AssetLoader;

/**
 * Created by ghiassi on 12/4/15.
 */
public class HSBtn {

    private float x, y, width, height;

    private TextureRegion buttonUp;
    private TextureRegion buttonDown;

    private Rectangle bounds;

    private boolean isPressed = false;

    public HSBtn(float x, float y, float width, float height,
                        TextureRegion buttonUp, TextureRegion buttonDown) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;

        bounds = new Rectangle(x, y, width, height);

    }

    public boolean isClicked(int screenX, int screenY) {
        return bounds.contains(screenX, screenY);
    }

    public void draw(SpriteBatch batcher) {
        if (isPressed) {
            batcher.draw(buttonDown, x, y, width, height);
        } else {
            batcher.draw(buttonUp, x, y, width, height);
        }
    }

    public boolean isTouchDown(int screenX, int screenY) {

        if (bounds.contains(screenX, screenY)) {
            isPressed = true;
//            width += clickScale;
//            height += clickScale;
//            x -= clickScale/2f;
//            y -= clickScale/2f;
            return true;
        }

        return false;
    }

    public boolean isTouchUp(int screenX, int screenY) {

        // It only counts as a touchUp if the button is in a pressed state.
        if (bounds.contains(screenX, screenY) && isPressed) {
            isPressed = false;
            AssetLoader.snd_select.play();
            return true;
        }
//        if(isPressed)
//        {
//            width -= clickScale;
//            height -= clickScale;
//            x += clickScale/2f;
//            y += clickScale/2f;
//        }

        // Whenever a finger is released, we will cancel any presses.
        isPressed = false;
        return false;
    }

}