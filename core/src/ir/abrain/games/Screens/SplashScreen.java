package ir.abrain.games.Screens;

//import ir.abrain.games.TweenAccessors.SpriteAccessor;
import ir.abrain.games.helpers.AssetLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//import aurelienribon.tweenengine.BaseTween;
//import aurelienribon.tweenengine.Tween;
//import aurelienribon.tweenengine.TweenCallback;
//import aurelienribon.tweenengine.TweenEquations;
//import aurelienribon.tweenengine.TweenManager;

/**
 * Created by ghiassi on 12/4/15.
 */
public class SplashScreen implements Screen {

//    private TweenManager manager;
//    private SpriteBatch batcher;
//    private Sprite sprite;
    private ir.abrain.games.HStrong game;

    public SplashScreen(ir.abrain.games.HStrong game) {
        this.game = game;
    }

    @Override
    public void show() {
//        sprite = new Sprite(AssetLoader.tx_logo);
//        sprite.setColor(1, 1, 1, 0);

//        float width = Gdx.graphics.getWidth();
//        float height = Gdx.graphics.getHeight();
//        float desiredWidth = width * .4f;
//        float scale = desiredWidth / sprite.getWidth();

//        sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
//        sprite.setPosition((width / 2) - (sprite.getWidth() / 2), (height / 2)
//                - (sprite.getHeight() / 2));
//        setupTween();
//        batcher = new SpriteBatch();
        game.switchToMenuScreen(); // orgiginally it should be in tween
    }

    private void setupTween() {
//        SpriteAccessor sp = new SpriteAccessor();
//        Tween.registerAccessor(Sprite.class, sp);
//        manager = new TweenManager();
//
//        TweenCallback cb = new TweenCallback() {
//            @Override
//            public void onEvent(int type, BaseTween<?> source) {
//                game.switchToMenuScreen();
//            }
//        };
//
//        Tween.to(sprite, SpriteAccessor.ALPHA, .8f).target(1)
//                .ease(TweenEquations.easeInOutQuad).repeatYoyo(1, .4f)
//                .setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
//                .start(manager);
    }

    @Override
    public void render(float delta) {
//        manager.update(delta);
//        Gdx.gl.glClearColor(1, 1, 1, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batcher.begin();
//        sprite.draw(batcher);
//        batcher.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}