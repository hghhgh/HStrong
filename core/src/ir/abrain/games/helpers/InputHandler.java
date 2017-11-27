package ir.abrain.games.helpers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import ir.abrain.games.HStrong;
import ir.abrain.games.gameworld.GameWorld;
import ir.abrain.games.ui.FireLvlBtn;
import ir.abrain.games.ui.HSBtn;

import static ir.abrain.games.gameworld.GameWorld.*;

public class InputHandler extends GestureDetector {
    private GameWorld TheGameWorld;
    private List<HSBtn> menuButtons;
//    private HSBtn playButton;
    public FireLvlBtn FireLvlButton;

    private int Width, Height;

    static GListener gl = new GListener() ;

    public InputHandler() {
        super(gl);
        TheGameWorld = null;
        Width = Gdx.graphics.getWidth();
        Height = Gdx.graphics.getHeight();

        menuButtons = new ArrayList<HSBtn>();
        float pbw = Height / 3f, pbh = Height / 3f;
//        playButton = new HSBtn(Width / 2 - (pbw / 2), Height / 2 - pbh / 2, pbw, pbh
//                , AssetLoader.tx_buttonUp, AssetLoader.tx_buttonDown);
        FireLvlButton = new FireLvlBtn(pbw / 4, Height - HStrong.scaleH * AssetLoader.tx_fireBtnLvls[0].getRegionHeight() - HStrong.scaleH * 100
                , HStrong.scaleW * AssetLoader.tx_fireBtnLvls[0].getRegionWidth() , HStrong.scaleH * AssetLoader.tx_fireBtnLvls[0].getRegionHeight() );

//        menuButtons.add(playButton);
    }

    public void setWorld(GameWorld world) {
        TheGameWorld = world;
        gl.TheGameWorld = world;
    }

    @Override
    public boolean keyDown(int keycode) {
        super.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        super.keyUp(keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        super.keyTyped(character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);

//        screenX = (int) (HStrong.scaleW * (screenX));
//        screenY = (int) (HStrong.scaleH * (screenY));

//        if (TheGameWorld.isMenu()) {
//            playButton.isTouchDown(screenX, screenY);
//        }
        boolean canclick =  FireLvlButton.getState() == StoneState.FireStoneLVL1 ||
                FireLvlButton.getState() == StoneState.preFireStoneLVL2 ||
                FireLvlButton.getState() == StoneState.FireStoneLVL2 ||
                FireLvlButton.getState() == StoneState.preFireStoneLVL3 ||
                FireLvlButton.getState() == StoneState.FireStoneLVL3;
        if (TheGameWorld.isRunning() && canclick) {
            FireLvlButton.isTouchDown(screenX, screenY);
            if (FireLvlButton.isTouchUp(screenX, screenY)) {
                TheGameWorld.setTheStoneState(FireLvlButton.getPressedState());
                FireLvlButton.setState(StoneState.NORMAL);
                return true;
            }
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX, screenY, pointer, button);
//        screenX = (int) (HStrong.scaleW * screenX);
//        screenY = (int) (HStrong.scaleH * screenY);


        boolean canclick =  FireLvlButton.getState() == StoneState.FireStoneLVL1 ||
                FireLvlButton.getState() == StoneState.preFireStoneLVL2 ||
                FireLvlButton.getState() == StoneState.FireStoneLVL2 ||
                FireLvlButton.getState() == StoneState.preFireStoneLVL3 ||
                FireLvlButton.getState() == StoneState.FireStoneLVL3;

        if (TheGameWorld.isRunning() && canclick) {

        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        super.touchDragged(screenX, screenY, pointer);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        super.mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        super.scrolled(amount);
        return false;
    }

    public List<HSBtn> getMenuButtons() {
        return menuButtons;
    }

    public static class GListener implements GestureListener{
        private GameWorld TheGameWorld;

        @Override
        public boolean touchDown(float x, float y, int pointer, int button) {

//                Gdx.app.log("touchDown", "touchDown");
            return false;
        }

        @Override
        public boolean tap(float x, float y, int count, int button) {
//                Gdx.app.log("tap", "tap");
            return false;
        }

        @Override
        public boolean longPress(float x, float y) {
            return false;
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            if (Math.abs(velocityX) > Math.abs(velocityY)) {
                if (velocityX > 1000) {
                    TheGameWorld.SlowMotion();
                } else {
//                    directionListener.onLeft();
                }
            } else {
                float v = Math.min(Math.abs(velocityY) / 30000f, 1f);
                float ssv=1f;
                if(TheGameWorld.getTheStoneState() ==StoneState.FireStoneLVL1
                        ||TheGameWorld.getTheStoneState() ==StoneState.FireStoneLVL2
                        ||TheGameWorld.getTheStoneState() ==StoneState.FireStoneLVL3)
                    ssv = .001f;
                TheGameWorld.addFireScore(v/5);
                if (velocityY > 0) {
//                    onDown();
                    float av = Math.min(Math.abs(TheGameWorld.TheStone.getVelocity()) / 50f, 1f);
                    float a = Math.abs((TheGameWorld.TheStone.getPosition().y - TheGameWorld.TheStone.height) / TheGameWorld.getHeight());
                    float w=av*(1f-v)*a*ssv;
                    TheGameWorld.TheStone.applyInvGravityImpulse(w);
            } else {
//                    onUp
                    float av = Math.min(Math.abs(TheGameWorld.TheStone.getVelocity()) / 50f, 1f);
                    float a = Math.abs((TheGameWorld.TheStone.getPosition().y - TheGameWorld.TheStone.height) / TheGameWorld.getHeight());
                    float w = -av * (1f - v) * a * ssv;
                    w = Math.min(w, -.1f);
                    TheGameWorld.TheStone.applyInvGravityImpulse(w);

//                    Gdx.app.log("av", String.valueOf(av));
//                    Gdx.app.log("a", String.valueOf(a));
                    Gdx.app.log("w", String.valueOf(w));
                }
            }
//                return super.fling(velocityX, velocityY, button);
            return true;
        }

        @Override
        public boolean pan(float x, float y, float deltaX, float deltaY) {
            return false;
        }

        @Override
        public boolean panStop(float x, float y, int pointer, int button) {
            return false;
        }

        @Override
        public boolean zoom(float initialDistance, float distance) {
            return false;
        }

        @Override
        public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
            return false;
        }

        @Override
        public void pinchStop() {

        }

    };
}
