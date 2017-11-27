package ir.abrain.games.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import ir.abrain.games.gameworld.GameWorld;
import ir.abrain.games.helpers.AssetLoader;

import static ir.abrain.games.gameworld.GameWorld.*;

/**
 * Created by ghiassi on 12/4/15.
 */
public class FireLvlBtn {

    private float x, y, width, height;

    private TextureRegion[] Lvls;

    private StoneState TheState;
    private StoneState ThePressedState;

    private Rectangle bounds;

    private boolean isPressed = false;
    private float clickScale = -50f;

    public FireLvlBtn(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        bounds = new Rectangle(x, y, width, height);
        setNormal();
        Lvls = AssetLoader.tx_fireBtnLvls;

    }

    public void setState(StoneState ss)
    {
        TheState = ss;
    }
    public void setNormal()
    {
        TheState = StoneState.NORMAL;
        ThePressedState = StoneState.NORMAL;
    }
    public StoneState getPressedState()
    {
        return ThePressedState;
    }
    public StoneState getState()
    {
        return TheState;
    }

    public boolean isClicked(int screenX, int screenY) {
        return bounds.contains(screenX, screenY);
    }

    public void draw(SpriteBatch batcher) {

        if (!isPressed) {

            switch (TheState) {
                case IDLE:
                case NORMAL:
                    batcher.draw(Lvls[0], x, y, width, height);
                    break;
                case preFireStoneLVL1:
                    batcher.draw(Lvls[1], x, y, width, height);
                    break;
                case FireStoneLVL1:
                    batcher.draw(Lvls[2], x, y, width, height);
                    break;
                case preFireStoneLVL2:
                    batcher.draw(Lvls[3], x, y, width, height);
                    break;
                case FireStoneLVL2:
                    batcher.draw(Lvls[4], x, y, width, height);
                    break;
                case preFireStoneLVL3:
                    batcher.draw(Lvls[5], x, y, width, height);
                    break;
                case FireStoneLVL3:
                    batcher.draw(Lvls[6], x, y, width, height);
                    break;
            }
        } else {
            batcher.draw(Lvls[0], x, y, width, height);
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
            ThePressedState = TheState;
            TheState = StoneState.NORMAL;
            AssetLoader.snd_select.play();
            return true;
        }
        // Whenever a finger is released, we will cancel any presses.
        isPressed = false;
        return false;
    }

}