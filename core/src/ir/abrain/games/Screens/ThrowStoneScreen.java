package ir.abrain.games.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;

import ir.abrain.games.HStrong;
import ir.abrain.games.gameworld.GameRenderer;
import ir.abrain.games.gameworld.GameWorld;
import ir.abrain.games.helpers.AssetLoader;
import ir.abrain.games.helpers.InputHandler;

import static ir.abrain.games.gameworld.GameWorld.StoneState;

public class ThrowStoneScreen implements Screen {

    private ir.abrain.games.HStrong TheGame;
    public GameRenderer ThrowScreenRenderer;
    private InputHandler TheInputHandler;
    private float runTime = 0;

    public ThrowStoneScreen(HStrong game){
        TheGame = game;
        TheInputHandler  = new InputHandler();

        TheInputHandler.setWorld(TheGame.TheGameWorld);
        ThrowScreenRenderer = new GameRenderer(TheGame.TheGameWorld); // initialize ThrowScreenRenderer
    }

    @Override
    public void show() {
        TheGame.TheGameWorld.restart();
        ThrowScreenRenderer.switchTo();
        Gdx.input.setInputProcessor(TheInputHandler);
        TheInputHandler.FireLvlButton.setState(StoneState.NORMAL);
    }

    @Override
    public void render(float delta) {
        // Covert Frame rate to String, print it
//        Gdx.app.log("GameScreen FPS", (1/delta) + "");

        runTime += delta * GameWorld.getSlowMotionRate();
        float d = delta * GameWorld.getSlowMotionRate();

        if (TheGame.TheGameWorld.isMenu() || TheGame.TheGameWorld.isTimesUp()|| TheGame.TheGameWorld.isHighScore()) {
            TheGame.switchToMenuScreen();
        } else if (TheGame.TheGameWorld.isRunning()) {
            TheGame.TheGameWorld.update(d);
            ThrowScreenRenderer.render(runTime);
            drawFireBtn();
            calcFireLevel();
            applyFireBtn(d);
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {

    }

    private void calcFireLevel() {
        if(TheGame.TheGameWorld.getTheStoneState() == StoneState.IDLE) return;

        float FireScore = TheGame.TheGameWorld.getFireScore();
        TheInputHandler.FireLvlButton.setState(StoneState.NORMAL);
        if (FireScore >= 1f)
            TheInputHandler.FireLvlButton.setState(StoneState.FireStoneLVL1);
        else if(FireScore >= .5f)
            TheInputHandler.FireLvlButton.setState(StoneState.preFireStoneLVL1);
        if (FireScore >= 2f)
            TheInputHandler.FireLvlButton.setState(StoneState.FireStoneLVL2);
        else if(FireScore >= 1.5f)
            TheInputHandler.FireLvlButton.setState(StoneState.preFireStoneLVL2);
        if (FireScore >= 4f)
            TheInputHandler.FireLvlButton.setState(StoneState.FireStoneLVL3);
        else if(FireScore >= 3f)
            TheInputHandler.FireLvlButton.setState(StoneState.preFireStoneLVL3);

    }

    private void applyFireBtn(float delta) {
        float w;
        switch (TheInputHandler.FireLvlButton.getPressedState()) {
            case IDLE:
            case NORMAL:
                return;

            case FireStoneLVL1:
            case preFireStoneLVL2:
                TheGame.TheGameWorld.FireTimeLen = 1.0f;
                w = 10;
                TheGame.TheGameWorld.applyFireBtn(w, StoneState.FireStoneLVL1);
                TheInputHandler.FireLvlButton.setNormal();
                break;
            case FireStoneLVL2:
            case preFireStoneLVL3:
                TheGame.TheGameWorld.FireTimeLen = 2.0f;
                w = 20;
                TheGame.TheGameWorld.applyFireBtn( w, StoneState.FireStoneLVL2);
                TheInputHandler.FireLvlButton.setNormal();
                AssetLoader.snd_jet.play();
                break;
            case FireStoneLVL3:
                TheGame.TheGameWorld.FireTimeLen = 4.0f;
                w = 40;
                TheGame.TheGameWorld.applyFireBtn(w, StoneState.FireStoneLVL3);
                TheInputHandler.FireLvlButton.setNormal();
                AssetLoader.snd_jet.play();
                break;
        }

    }

    private void drawFireBtn() {
        ThrowScreenRenderer.getBatcher().begin();
        TheInputHandler.FireLvlButton.draw(ThrowScreenRenderer.getBatcher());
        ThrowScreenRenderer.getBatcher().end();
    }


}
