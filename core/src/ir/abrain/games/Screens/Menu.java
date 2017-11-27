package ir.abrain.games.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import ir.abrain.games.gameworld.GameWorld;
import ir.abrain.games.helpers.AssetLoader;

/**
 * Created by ghiassi on 12/9/15.
 */
public class Menu implements Screen{

    private ir.abrain.games.HStrong theGame;
    private Stage menuStage;

    // Menu
    private Table mcontainer, mtable;
    private TextButton start, uploadscore, exit;
    private Label title, highScore;
    // CoreBoard
    private Table sbcontainer,sbtable;
    private Label sbtitle;
    private TextButton sbmenu;

    private SpriteBatch batcher;

    public Menu (ir.abrain.games.HStrong game) {
        this.theGame = game;

        OrthographicCamera cam = new OrthographicCamera();
        float Width = Gdx.graphics.getWidth();
        float Height = Gdx.graphics.getHeight();
        cam.setToOrtho(true, Width, Height);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        menuStage = new Stage(new FitViewport(Width*2, Height*2));

        Skin skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"));
        // Menu
        makeMenu(Width, Height, skin);

        // Score Board
        makeScoreBoard(Width, Height, skin);

        menuStage.clear();
        menuStage.addActor(mcontainer);
    }

    private void makeScoreBoard(float width, float height, Skin skin) {
        sbtitle = new Label("Score Board", skin);
        sbtitle.setColor(Color.BLACK);
        sbmenu = new TextButton("Menu", skin);

        sbtable = new Table();
        sbcontainer = new Table();
        sbtable.add(sbtitle).maxSize(width / 2f, height / 5f).pad(50).expand();

        sbtable.row().colspan(2);
        sbtable.add(sbmenu).maxSize(width / 2f, height / 5f).pad(50).expand().fill();
        sbtable.background(new TextureRegionDrawable(AssetLoader.tx_frame));
        sbtable.pad(100);
        sbtable.setDebug(true); // This is optional, but enables debug lines for tables.
        sbcontainer.setFillParent(true);
        sbcontainer.pad(height / 3f, width / 2, height / 3f, width / 2);
        sbcontainer.add(sbtable).expand().fill();
    }

    private void makeMenu(float width, float height, Skin skin) {
        title = new Label("HStrong", skin);
        title.setColor(Color.BLACK);
//        title.sizeBy(2f);
        highScore = new Label("High Score : " + AssetLoader.getHighScore(), skin);
        highScore.setColor(Color.BLACK);
        start = new TextButton("Start", skin);
        uploadscore = new TextButton("Upload Score", skin);
        exit = new TextButton("Exit", skin);

        mtable = new Table();
        mcontainer = new Table();
//        mtable.setBounds(0,0,Width/2f, Height/2f);

        mtable.add(title).maxSize(width / 2f, height / 5f).pad(50).expand();
        mtable.add(highScore).maxSize(width / 2f, height / 5f).pad(50).expand();
        mtable.row().colspan(2);
        mtable.add(start).maxSize(width / 2f, height / 5f).pad(50).expand().fill();
        mtable.row().colspan(2);
        mtable.add(uploadscore).maxSize(width / 2f, height / 5f).pad(50).expand().fill();
        mtable.row().colspan(2);
        mtable.add(exit).maxSize(width / 2f, height / 5f).pad(50).expand().fill();
        mtable.background(new TextureRegionDrawable(AssetLoader.tx_frame));
        mtable.pad(100);
        mtable.setDebug(true); // This is optional, but enables debug lines for tables.
        mcontainer.setFillParent(true);
        mcontainer.pad(height / 3f, width / 2, height / 3f, width / 2);
        mcontainer.add(mtable).expand().fill();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(menuStage);
        registerListeners();
        AssetLoader.msc_pradise.play();
    }

    private void registerListeners() {
        start.clearListeners();
        start.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                theGame.TheGameWorld.CurrentGameState = GameWorld.GameState.RUNNING;
                theGame.switchToThrowScreen();
//                Gdx.app.log("btn", "start");
            }
        });
        uploadscore.clearListeners();
        uploadscore.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                theGame.TheGameWorld.CurrentGameState = GameWorld.GameState.HIGHSCORE;
                menuStage.clear();
                menuStage.addActor(sbcontainer);
            }
        });
        exit.clearListeners();
        exit.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }
        });

        sbmenu.clearListeners();
        sbmenu.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                theGame.TheGameWorld.CurrentGameState = GameWorld.GameState.MENU;
                menuStage.clear();
                menuStage.addActor(mcontainer);

            }
        });
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(144 / 255.0f, 195 / 255.0f, 212 / 255.0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batcher.begin();
        drawBG();
        batcher.end();

        if (theGame.TheGameWorld.isMenu()) {
            drawMenuUI(delta);
        } else if (theGame.TheGameWorld.isTimesUp()) {
            drawMenuUI(delta);
        } else if (theGame.TheGameWorld.isHighScore()) {
            drawMenuUI(delta);
        }

    }

    public void resize (int width, int height) {
//        menuStage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        AssetLoader.msc_pradise.stop();

        Gdx.input.setInputProcessor(null);
    }

    public void dispose() {
        menuStage.dispose();
    }

    private void drawMenuUI(float delta) {

        menuStage.act(delta);
        menuStage.draw();

    }

    private void drawBG() {

        // Draw trees
        float a = Math.min(Math.abs(theGame.TheGameWorld.TheStone.getVelocity()) / 60f, 1);
        batcher.setColor(1.0f, 1.0f, 1.0f, a);
        batcher.draw(AssetLoader.tx_trees_b, theGame.TheGameWorld.scroller.TreesL.getX(), theGame.TheGameWorld.scroller.TreesL.getY(),
                theGame.TheGameWorld.scroller.TreesL.getWidth(), theGame.TheGameWorld.scroller.TreesL.getHeight());
        batcher.draw(AssetLoader.tx_trees_b, theGame.TheGameWorld.scroller.TreesR.getX(), theGame.TheGameWorld.scroller.TreesR.getY(),
                theGame.TheGameWorld.scroller.TreesR.getWidth(), theGame.TheGameWorld.scroller.TreesR.getHeight());
        batcher.setColor(1.0f, 1.0f, 1.0f, 1f - a);
        batcher.draw(AssetLoader.tx_trees, theGame.TheGameWorld.scroller.TreesL.getX(), theGame.TheGameWorld.scroller.TreesL.getY(),
                theGame.TheGameWorld.scroller.TreesL.getWidth(), theGame.TheGameWorld.scroller.TreesL.getHeight());
        batcher.draw(AssetLoader.tx_trees, theGame.TheGameWorld.scroller.TreesR.getX(), theGame.TheGameWorld.scroller.TreesR.getY(),
                theGame.TheGameWorld.scroller.TreesR.getWidth(), theGame.TheGameWorld.scroller.TreesR.getHeight());
        batcher.setColor(1.0f, 1.0f, 1.0f, 1);

        // Draw Flower
        a = Math.min(Math.abs(theGame.TheGameWorld.TheStone.getVelocity()) / 30f, 1);
        batcher.setColor(1.0f, 1.0f, 1.0f, a);
        batcher.draw(AssetLoader.tx_flower_b, theGame.TheGameWorld.scroller.flowerL.getX(), theGame.TheGameWorld.scroller.flowerL.getY(),
                theGame.TheGameWorld.scroller.flowerL.getWidth(), theGame.TheGameWorld.scroller.flowerL.getHeight());
        batcher.draw(AssetLoader.tx_flower_b, theGame.TheGameWorld.scroller.flowerR.getX(), theGame.TheGameWorld.scroller.flowerR.getY(),
                theGame.TheGameWorld.scroller.flowerR.getWidth(), theGame.TheGameWorld.scroller.flowerR.getHeight());
        batcher.setColor(1.0f, 1.0f, 1.0f, 1f - a);
        batcher.draw(AssetLoader.tx_flower, theGame.TheGameWorld.scroller.flowerL.getX(), theGame.TheGameWorld.scroller.flowerL.getY(),
                theGame.TheGameWorld.scroller.flowerL.getWidth(), theGame.TheGameWorld.scroller.flowerL.getHeight());
        batcher.draw(AssetLoader.tx_flower, theGame.TheGameWorld.scroller.flowerR.getX(), theGame.TheGameWorld.scroller.flowerR.getY(),
                theGame.TheGameWorld.scroller.flowerR.getWidth(), theGame.TheGameWorld.scroller.flowerR.getHeight());
        batcher.setColor(1.0f, 1.0f, 1.0f, 1f);


        batcher.draw(AssetLoader.tx_dirt.getRegion(), theGame.TheGameWorld.scroller.dirtL.getX(), theGame.TheGameWorld.scroller.dirtL.getY(),
                theGame.TheGameWorld.scroller.dirtL.getWidth(), theGame.TheGameWorld.scroller.dirtL.getHeight());
        batcher.draw(AssetLoader.tx_dirt.getRegion(), theGame.TheGameWorld.scroller.dirtR.getX(), theGame.TheGameWorld.scroller.dirtR.getY(),
                theGame.TheGameWorld.scroller.dirtR.getWidth(), theGame.TheGameWorld.scroller.dirtR.getHeight());


        // Draw Cloud
//        batcher.draw(AssetLoader.tx_cloud,200, 100,
//                AssetLoader.tx_cloud.getRegionWidth(), AssetLoader.tx_cloud.getRegionHeight());
//

    }

}
