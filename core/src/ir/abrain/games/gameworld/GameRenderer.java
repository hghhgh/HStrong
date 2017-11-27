package ir.abrain.games.gameworld;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
//import aurelienribon.tweenengine.TweenManager;
import ir.abrain.games.HStrong;
//import ir.abrain.games.TweenAccessors.Value;
import ir.abrain.games.TweenAccessors.ValueAccessor;
import ir.abrain.games.gameobjects.Bird;
import ir.abrain.games.helpers.AssetLoader;
import ir.abrain.games.helpers.Consts;

public class GameRenderer {
    private final GameWorld TheGameWorld;
    private OrthographicCamera cam;
    private SpriteBatch batcher;
    private float Width;
    private float Height;

    Box2DDebugRenderer debugRenderer;

    // Tween stuff
//    private TweenManager manager;
//    private Value alpha = new Value();
    private Color transitionColor;

    public GameRenderer(GameWorld world) {
        this.TheGameWorld = world;
        cam = new OrthographicCamera();
        Width = Gdx.graphics.getWidth();
        Height = Gdx.graphics.getHeight();
        cam.setToOrtho(true, Width, Height);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        debugRenderer = new Box2DDebugRenderer();

        transitionColor = new Color();
        switchTo();
    }


    public void switchTo() {
        prepareTransition(255, 255, 255, .5f);
    }

    public void render(float runTime) {
// Sets a Color to Fill the Screen with (RGB = 10, 15, 230), Opacity of 1 (100%)
        Gdx.gl.glClearColor(144 / 255.0f, 195 / 255.0f, 212 / 255.0f, 1f);
        // Fills the screen with the selected color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin SpriteBatch
        batcher.begin();
        // Disable transparency
//        batcher.disableBlending();
        drawBG();
        drawObjects(runTime);
        // The bird needs transparency, so we enable that again.
//        batcher.enableBlending();

            drawBirds(runTime);
//            drawFireBtn();
            drawScore();
            drawTime();

        drawFG();

        // End SpriteBatch
        batcher.end();

        debugRenderer.render(TheGameWorld.TheWorld, cam.combined.cpy().scl(Consts.BTW));

//        TheGameWorld.TheWorld.step(1 / 60f, 6, 2);
    }

    private void drawBirds(float runTime) {

        for (Bird b:TheGameWorld.TheBirds) {
            draw1Bird(runTime , b);
        }

    }

    private void draw1Bird(float runTime, Bird theBird) {
        TextureRegion keyframe;
        if(theBird.birdState == Bird.BirdState.DMAGED)
            keyframe = (TextureRegion) AssetLoader.anim_bird1_damage.getKeyFrame(runTime, true);
        else
            keyframe = (TextureRegion) AssetLoader.anim_bird1_idle.getKeyFrame(runTime, true);

        float redfireoffsetx =HStrong.scaleW *  0;
        float scale = 1f;
        float x = theBird.getCenter().x - (theBird.width / 2f + redfireoffsetx) * scale;
        float y = theBird.getCenter().y - (theBird.height / 2f) * scale;
        float w = theBird.width * scale;
        float h = theBird.height * scale;
        batcher.draw(keyframe, x, y, 0, 0, w, h, 1, 1, 0);
    }


    private void drawTime() {
        String score = TheGameWorld.getThrowTime() + "";
        AssetLoader.fnt_font.setColor(Color.RED);
        AssetLoader.fnt_font.draw(batcher, score, Width - HStrong.scaleW * 200 - (3 * score.length() - 1),HStrong.scaleH *  11);
        AssetLoader.fnt_font.setColor(Color.WHITE);
    }

    private void drawScore() {
        String score = TheGameWorld.getScore() + "";
        AssetLoader.fnt_font.draw(batcher, score, HStrong.scaleW * 100 + (3 * score.length() - 1),HStrong.scaleH *  11);
    }

    private void drawObjects(float runTime) {
        // pre Fire Ball
        switch (TheGameWorld.getTheStoneState()) {
            case IDLE:
            case NORMAL:
                drawTheStone();
                break;

            case FireStoneLVL1:
            case preFireStoneLVL2:
                drawWithRedLvl1FireStone(runTime);
                break;
            case FireStoneLVL2:
            case preFireStoneLVL3:
                drawWithRedLvl2FireStone(runTime);
                break;
            case FireStoneLVL3:
                drawWithRedLvl3FireStone(runTime);
                break;
        }
    }

    private void drawWithRedLvl1FireStone(float runTime) {
        TextureRegion keyframe = (TextureRegion) AssetLoader.anim_lvl1fireball.getKeyFrame(runTime, true);
        float redfireoffsetx =HStrong.scaleW *  50;
        float scale = 1.7f;
        float x = TheGameWorld.TheStone.getCenter().x - (TheGameWorld.TheStone.width / 2f + redfireoffsetx) * scale;
        float y = TheGameWorld.TheStone.getCenter().y - (HStrong.scaleH * keyframe.getRegionHeight() / 2f) * scale;
        float w = keyframe.getRegionWidth() * scale;
        float h = keyframe.getRegionHeight() * scale;
        batcher.draw(keyframe, x, y, w, h);

        drawTheStone();

        // post Fire Ball
        batcher.setColor(1.0f, 1.0f, 1.0f, .3f);
        batcher.draw(keyframe, x, y, w, h);
        batcher.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void drawWithRedLvl2FireStone(float runTime) {
        TextureRegion keyframe = (TextureRegion) AssetLoader.anim_lvl2fireball.getKeyFrame(runTime, true);
        float redfireoffsetx = HStrong.scaleW * -10;
        float scale = 1.9f;
        float x = TheGameWorld.TheStone.getCenter().x - (TheGameWorld.TheStone.width / 2f + redfireoffsetx) * scale;
        float y = TheGameWorld.TheStone.getCenter().y - (HStrong.scaleH * keyframe.getRegionHeight() / 2f + HStrong.scaleH * 10) * scale;
        float w = keyframe.getRegionWidth() * scale;
        float h = keyframe.getRegionHeight() * scale;
        batcher.draw(keyframe, x, y + HStrong.scaleH * 590, 0, 0, w, h, 1f, 1f, -90f);

        drawTheStone();

        // post Fire Ball
        batcher.setColor(1.0f, 1.0f, 1.0f, .3f);
        batcher.draw(keyframe, x, y + HStrong.scaleH * 590, 0, 0, w, h, 1f, 1f, -90f);
        batcher.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void drawWithRedLvl3FireStone(float runTime) {
        TextureRegion keyframe = (TextureRegion) AssetLoader.anim_lvl3fireball.getKeyFrame(runTime, true);
        float redfireoffsetx = HStrong.scaleW * 30;
        float scale = .75f;
        float x = TheGameWorld.TheStone.getCenter().x - (TheGameWorld.TheStone.width / 2f + redfireoffsetx) * scale;
        float y = TheGameWorld.TheStone.getCenter().y - (HStrong.scaleH * keyframe.getRegionHeight() / 2f + HStrong.scaleH * 10) * scale;
        float w = keyframe.getRegionWidth() * scale;
        float h = keyframe.getRegionHeight() * scale;
        batcher.draw(keyframe, x, y + HStrong.scaleH * 260, 0, 0, w, h, 1f, 1f, -90f);

        drawTheStone();

        // post Fire Ball
        batcher.setColor(1.0f, 1.0f, 1.0f, .3f);
        batcher.draw(keyframe, x, y + HStrong.scaleH * 260, 0, 0, w, h, 1f, 1f, -90f);
        batcher.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void drawTheStone() {
        // The stone
        float a = Math.min(Math.abs(TheGameWorld.TheStone.getVelocity()) / 35f, 1)*GameWorld.getSlowMotionRate();
//        Gdx.app.log("a", String.valueOf(a));
        batcher.setColor(1.0f, 1.0f, 1.0f, a);
        batcher.draw(AssetLoader.tx_stone_b,
                TheGameWorld.TheStone.getPosition().x, TheGameWorld.TheStone.getPosition().y, 0, 0
                , TheGameWorld.TheStone.width, TheGameWorld.TheStone.height
                , 1, 1, TheGameWorld.TheStone.getAngle() * 57.2958f);
        batcher.setColor(1.0f, 1.0f, 1.0f, 1f - a);
        batcher.draw(AssetLoader.tx_stone,
                TheGameWorld.TheStone.getPosition().x, TheGameWorld.TheStone.getPosition().y, 0, 0
                , TheGameWorld.TheStone.width, TheGameWorld.TheStone.height
                , 1, 1, TheGameWorld.TheStone.getAngle() * 57.2958f);
        batcher.setColor(1.0f, 1.0f, 1.0f, 1f);
    }

    private void drawBG() {

        // Draw trees
        float a = Math.min(Math.abs(TheGameWorld.TheStone.getVelocity()) / 60f, 1);
        batcher.setColor(1.0f, 1.0f, 1.0f, a);
        batcher.draw(AssetLoader.tx_trees_b, TheGameWorld.scroller.TreesL.getX(), TheGameWorld.scroller.TreesL.getY(),
                TheGameWorld.scroller.TreesL.getWidth(), TheGameWorld.scroller.TreesL.getHeight());
        batcher.draw(AssetLoader.tx_trees_b, TheGameWorld.scroller.TreesR.getX(), TheGameWorld.scroller.TreesR.getY(),
                TheGameWorld.scroller.TreesR.getWidth(), TheGameWorld.scroller.TreesR.getHeight());
        batcher.setColor(1.0f, 1.0f, 1.0f, 1f - a);
        batcher.draw(AssetLoader.tx_trees, TheGameWorld.scroller.TreesL.getX(), TheGameWorld.scroller.TreesL.getY(),
                TheGameWorld.scroller.TreesL.getWidth(), TheGameWorld.scroller.TreesL.getHeight());
        batcher.draw(AssetLoader.tx_trees, TheGameWorld.scroller.TreesR.getX(), TheGameWorld.scroller.TreesR.getY(),
                TheGameWorld.scroller.TreesR.getWidth(), TheGameWorld.scroller.TreesR.getHeight());
        batcher.setColor(1.0f, 1.0f, 1.0f, 1);

        // Draw Flower
        a = Math.min(Math.abs(TheGameWorld.TheStone.getVelocity()) / 30f, 1);
        batcher.setColor(1.0f, 1.0f, 1.0f, a);
        batcher.draw(AssetLoader.tx_flower_b, TheGameWorld.scroller.flowerL.getX(), TheGameWorld.scroller.flowerL.getY(),
                TheGameWorld.scroller.flowerL.getWidth(), TheGameWorld.scroller.flowerL.getHeight());
        batcher.draw(AssetLoader.tx_flower_b, TheGameWorld.scroller.flowerR.getX(), TheGameWorld.scroller.flowerR.getY(),
                TheGameWorld.scroller.flowerR.getWidth(), TheGameWorld.scroller.flowerR.getHeight());
        batcher.setColor(1.0f, 1.0f, 1.0f, 1f - a);
        batcher.draw(AssetLoader.tx_flower, TheGameWorld.scroller.flowerL.getX(), TheGameWorld.scroller.flowerL.getY(),
                TheGameWorld.scroller.flowerL.getWidth(), TheGameWorld.scroller.flowerL.getHeight());
        batcher.draw(AssetLoader.tx_flower, TheGameWorld.scroller.flowerR.getX(), TheGameWorld.scroller.flowerR.getY(),
                TheGameWorld.scroller.flowerR.getWidth(), TheGameWorld.scroller.flowerR.getHeight());
        batcher.setColor(1.0f, 1.0f, 1.0f, 1f);

        // Draw Dirt
        batcher.draw(AssetLoader.tx_dirt.getRegion(), TheGameWorld.scroller.dirtL.getX(), TheGameWorld.scroller.dirtL.getY(),
                TheGameWorld.scroller.dirtL.getWidth(), TheGameWorld.scroller.dirtL.getHeight());
        batcher.draw(AssetLoader.tx_dirt.getRegion(), TheGameWorld.scroller.dirtR.getX(), TheGameWorld.scroller.dirtR.getY(),
                TheGameWorld.scroller.dirtR.getWidth(), TheGameWorld.scroller.dirtR.getHeight());

        // Draw Cielling
        batcher.draw(AssetLoader.tx_dirt.getRegion(), TheGameWorld.scroller.cielL.getX(), TheGameWorld.scroller.cielL.getY(),
                TheGameWorld.scroller.cielL.getWidth(), TheGameWorld.scroller.cielL.getHeight());
        batcher.draw(AssetLoader.tx_dirt.getRegion(), TheGameWorld.scroller.cielR.getX(), TheGameWorld.scroller.cielR.getY(),
                TheGameWorld.scroller.cielR.getWidth(), TheGameWorld.scroller.cielR.getHeight());


        // Draw Cloud
//        batcher.draw(AssetLoader.tx_cloud,200, 100,
//                AssetLoader.tx_cloud.getRegionWidth(), AssetLoader.tx_cloud.getRegionHeight());
//

    }

    private void drawFG() {

        float a = TheGameWorld.getSlowMotionRate();
        batcher.setColor(1.0f, 1.0f, 1.0f, 1 - a);
        batcher.draw(AssetLoader.tx_zoomblur, 0, 0,
                TheGameWorld.getWidth(), TheGameWorld.getHeight());
        batcher.setColor(1.0f, 1.0f, 1.0f, 1f);

    }



    public void prepareTransition(int r, int g, int b, float duration) {
//        transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
//        alpha.setValue(1);
//        Tween.registerAccessor(Value.class, new ValueAccessor());
//        manager = new TweenManager();
//        Tween.to(alpha, -1, duration).target(0)
//                .ease(TweenEquations.easeOutQuad).start(manager);
    }

    public SpriteBatch getBatcher() {
        return batcher;
    }

//    private void drawTransition(float delta) {
//        if (alpha.getValue() > 0) {
//            manager.update(delta);
//            Gdx.gl.glEnable(GL20.GL_BLEND);
//            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//            shapeRenderer.setColor(transitionColor.r, transitionColor.g,
//                    transitionColor.b, alpha.getValue());
//            shapeRenderer.rect(0, 0, Width, 300);
//            shapeRenderer.end();
//            Gdx.gl.glDisable(GL20.GL_BLEND);
//
//        }
//    }
}
