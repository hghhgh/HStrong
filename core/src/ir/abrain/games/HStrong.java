package ir.abrain.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;

import ir.abrain.games.Screens.Menu;
import ir.abrain.games.Screens.SplashScreen;
import ir.abrain.games.Screens.ThrowStoneScreen;
import ir.abrain.games.gameworld.GameRenderer;
import ir.abrain.games.gameworld.GameWorld;
import ir.abrain.games.helpers.AssetLoader;

public class HStrong extends Game {

	public static float scaleW, scaleH;
    public GameWorld TheGameWorld;

    private Menu TheMenuScreen;
    private ThrowStoneScreen TheThrowScreen;
    private SplashScreen TheSplashScreen;

	@Override
	public void create() {
		boolean available = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

		float ratio = Gdx.graphics.getWidth()/(float)Gdx.graphics.getHeight();
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = screenWidth/ratio;

		scaleW = screenWidth/2560f;
		scaleH = screenHeight/1440f;


		if(available) {
			Gdx.app.log("HStrong", "created");
			AssetLoader.load();
            TheGameWorld = new GameWorld(); // initialize TheWorld
            TheMenuScreen = new ir.abrain.games.Screens.Menu(this);
            TheThrowScreen = new ir.abrain.games.Screens.ThrowStoneScreen(this);
            TheSplashScreen = new ir.abrain.games.Screens.SplashScreen(this);

            setScreen(TheSplashScreen);
		}
		Gdx.app.log("HStrong", "Accelerometer not avilable !");
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

    public void switchToThrowScreen() {
        setScreen(TheThrowScreen);
    }

    public void switchToMenuScreen() {
        setScreen(TheMenuScreen);
    }

	public void switchToScoreBoardScreen() {

	}
}
