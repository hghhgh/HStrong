package ir.abrain.games.gameworld;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import ir.abrain.games.gameobjects.Bird;
import ir.abrain.games.gameobjects.WorldBox;
import ir.abrain.games.gameobjects.ScrollHandler;
import ir.abrain.games.gameobjects.Stone;
import ir.abrain.games.helpers.AssetLoader;
import ir.abrain.games.HStrong;
import ir.abrain.games.helpers.Consts;

public class GameWorld {


    public final World TheWorld;
    public Stone TheStone;
    public WorldBox TheWorldBox;
    public ArrayList<Bird> TheBirds;
    public ScrollHandler scroller;
    private float Width;
    private float Height;
    public Random rnd;

    float gravityX = 0, gravityY = 0, gravityZ = 0;
    float linear_accelX = 0, linear_accelY = 0, linear_accelZ = 0;
    final float alpha = 0.8f;
    float maxPeak = 0, sumPeak = 0;
    float bPeak = 0, Peak = 0, aPeak = 0;
    int numOfPeak = 0;
    float throwTime = 0.0f;
    private float score = 0;
    int thr = 2, timeThr = 5;
    boolean isThrown;

    public GameState CurrentGameState;

    private StoneState TheStoneState;
    private float FireScore;
    public float FireTimeLen;

    private static float slowMotionRate = 1.0f;
    private float slowMotionLen = .1f;

    public void SlowMotion() {
        slowMotionRate = .5f;
        slowMotionLen = .5f;
    }

    public void NormalMotion() {
        slowMotionRate = 1.0f;
    }

    public static float getSlowMotionRate() {
        return slowMotionRate;
    }

    public enum StoneState {
        IDLE, NORMAL, preFireStoneLVL1, FireStoneLVL1, preFireStoneLVL2, FireStoneLVL2, preFireStoneLVL3, FireStoneLVL3
    }


    public enum GameState {
        MENU, RUNNING, TimesUp, HIGHSCORE
    }

    public int getThrowTime() {
        int r = (int) (timeThr - throwTime);
        return r < 0 ? 0 : r;
    }

    public GameWorld() {
        Width = Gdx.graphics.getWidth();
        Height = Gdx.graphics.getHeight();
        rnd = new Random(100);
        // passed in is gravity
        TheWorld = new World(Consts.Gravity, true);

        TheStone = new Stone(TheWorld, Width - Width / 5, Height / 2, HStrong.scaleW * 150, HStrong.scaleH * 150);
        TheWorldBox = new WorldBox(TheWorld, -HStrong.scaleW * 400, Height - HStrong.scaleH * 75 / 2, 4 * Width + HStrong.scaleW * 802, HStrong.scaleH * 75);
        scroller = new ScrollHandler(TheWorld);
        scroller.setScrollSpeed(0, 0);

        TheBirds = new ArrayList<Bird>();

        numOfPeak = 0;
        throwTime = 0.0f;

        CurrentGameState = GameState.MENU;
        TheStoneState = StoneState.NORMAL;
        FireTimeLen = 0.0f;
        FireScore = 0.0f;

        TheWorld.setContactListener(new WorldContactListener(this));
    }

    public void update(float delta) {

        slowMotionLen -= delta;
        if (slowMotionLen < 0)
            NormalMotion();

        switch (CurrentGameState) {
            case RUNNING:

                makeBirds();
                updateRunning(delta);
                TheWorld.step(delta, 6, 2);

                break;
            default:
                break;
        }

    }

    private void makeBirds() {
        int len = (int) (Math.log(getScore())/2);

        float lane1 = getHeight()/3f;
        float lane1c = (0+lane1)/2f;
        float lane2 = lane1 + getHeight()/3f;
        float lane2c = (lane1+lane2)/2f;
        float lane3 = lane2 + getHeight()/3f;
        float lane3c = (lane2+lane3)/2f;


        if ((TheBirds.size() ) < len)
            {
                float offset = 100;
                float x = -HStrong.scaleW * 150;
                float y = Height - HStrong.scaleH * 200;
                float rx = (float) (1 * rnd.nextGaussian() * getScore() * len + offset);
                if(TheBirds.size() >0) {
                    Bird lastone = TheBirds.get(TheBirds.size() - 1);
                    offset = lastone.onScoreCome;
                    rx = (float) (10 * rnd.nextGaussian() * getScore() * len + offset);
//                    Gdx.app.log("lastone", String.valueOf(lastone.getPosition().x));
                    y = lastone.getPosition().y - HStrong.scaleH * 200;
                    if (y < HStrong.scaleH * 20)
                        y = lastone.getPosition().y + HStrong.scaleH * 200;

                    if (lastone.getPosition().x <= -HStrong.scaleW * 150)
                        x = lastone.getPosition().x - HStrong.scaleW * 150;
                    if(lastone.getPosition().x > 0)
                    {
                        TheBirds.add(new Bird(String.valueOf(TheBirds.size()), TheWorld, (int) (rx)
                                , x, y
                                , 0 + HStrong.scaleW * 200, HStrong.scaleH * 200));
                    }
                }
                else {
                    TheBirds.add(new Bird(String.valueOf(TheBirds.size()), TheWorld, (int) (rx)
                            , x, y
                            , 0 + HStrong.scaleW * 200, HStrong.scaleH * 200));
                }
            }

        for (Iterator<Bird> it = TheBirds.iterator(); it.hasNext();) {
            Bird b = it.next();
            if (b.getPosition().x > (getWidth() + HStrong.scaleW * 200))
                    it.remove();
        }

    }


    private void updateRunning(float delta) {
        TheStone.update(delta);
        TheWorldBox.update(delta);
        scroller.update(delta);
        updateSensor(delta);

        if (isThrown) {
            TheStone.setAwake_Active(true);
            TheWorldBox.getFloor().setAwake(true);
            TheWorldBox.getFloor().setActive(true);
            if (TheStone.getVelocity() < 0) {
                float speed = -(TheStone.getVelocity()) * 50;
                scroller.setScrollSpeed((int) (speed), -10);
                addScore(delta * speed);

                for (Bird b : TheBirds) {
                    if (getScore() > b.onScoreCome)
                        b.setSpeed(TheStone.getVelocity());
                }

                if (FireTimeLen > 0.0f)
                    FireTimeLen -= delta;
                else if (TheStoneState == StoneState.FireStoneLVL1 || TheStoneState == StoneState.FireStoneLVL2 || TheStoneState == StoneState.FireStoneLVL3) {
                    TheStone.resetGravity();
                    TheStoneState = StoneState.NORMAL;
                    AssetLoader.snd_jet.stop();
                }
            }

        }
        if (isThrown && Math.abs(TheStone.getVelocity()) < 0.05) {
            CurrentGameState = GameState.HIGHSCORE;
            restart();
        }
        if (!isThrown && throwTime > timeThr) {
            CurrentGameState = GameState.TimesUp;
            restart();
        }

        for (Bird b : TheBirds) {
            b.update(delta);
        }


    }

    private void updateSensor(float delta) {
        throwTime += delta;
        if (numOfPeak < thr & !isThrown) { //!!!!!!!! check this
            bPeak = Peak;
            Peak = aPeak;
            aPeak = getAccelLen();

            if (bPeak < Peak && Peak < aPeak && Peak > 10) // this is Peak
            {
                if (Peak > maxPeak) {
                    maxPeak = Peak;
                    sumPeak += Peak;
                }
                numOfPeak++;
            }
            if (numOfPeak >= thr) {
                TheStone.applyVelocityImpulse(maxPeak / 4f);
                TheStone.applyInvGravityImpulse(-.5f);
                AssetLoader.snd_throw.play();
                isThrown = true;
                throwTime = timeThr;
                addFireScore(maxPeak / 30);
                maxPeak = 0.0f;
                TheStone.thrown = true;
            }
        }
    }

    public void applyFireBtn(float w, StoneState ss) {
        if (TheStone.getVelocity() < 0) {
            TheStone.applyVelocityImpulse(w);
            TheStone.floatObject();
            TheStone.applyInvGravityImpulse(-.09f);
            FireScore = 0.0f;
            AssetLoader.snd_jet.play();
            TheStoneState = ss;
        }
    }


    private float getAccelLen() {
        float accelX = Gdx.input.getAccelerometerX();
        float accelY = Gdx.input.getAccelerometerY();
        float accelZ = Gdx.input.getAccelerometerZ();
        // Isolate the force of gravity with the low-pass filter.
        gravityX = alpha * gravityX + (1 - alpha) * accelX;
        gravityY = alpha * gravityY + (1 - alpha) * accelY;
        gravityZ = alpha * gravityZ + (1 - alpha) * accelZ;


        // Remove the gravity contribution with the high-pass filter.
        linear_accelX = Math.max(gravityX - accelX, 0);
        linear_accelY = Math.min(gravityY - accelY, 0);
        linear_accelZ = gravityZ - accelZ;

//        Gdx.app.log("linear_accelX", String.valueOf(linear_accelX));
//        Gdx.app.log("linear_accelY", String.valueOf(linear_accelY));

        return (float) Math.sqrt(linear_accelX * linear_accelX + linear_accelY * linear_accelY);
    }

    public int getScore() {
        return (int) (score / 100);
    }

    public void addScore(float increment) {
        score += increment;
    }

    public void restart() {
        if (getScore() > AssetLoader.getHighScore()) {
            AssetLoader.setHighScore(getScore());
        }
        score = 0.0f;
        bPeak = 0.0f;
        Peak = 0.0f;
        aPeak = 0.0f;
        maxPeak = 0.0f;
        sumPeak = 0.0f;
        throwTime = 0.0f;
        numOfPeak = 0;
        gravityX = 0;
        gravityY = 0;
        gravityZ = 0;
        linear_accelX = 0;
        linear_accelY = 0;
        linear_accelZ = 0;
        if (isThrown)
            isThrown = false;

        TheStone.onRestart();
        TheBirds.clear();
        scroller.onRestart();
        TheWorldBox.onRestart();

        TheStoneState = StoneState.NORMAL;
        FireScore = 0.0f;
        FireTimeLen = 0.0f;

        slowMotionRate = 1.0f;
//        CurrentGameState = GameState.MENU;
    }

    public boolean isTimesUp() {
        return CurrentGameState == GameState.TimesUp;
    }

    public boolean isHighScore() {
        return CurrentGameState == GameState.HIGHSCORE;
    }

    public boolean isMenu() {
        return CurrentGameState == GameState.MENU;
    }

    public boolean isRunning() {
        return CurrentGameState == GameState.RUNNING;
    }

    public StoneState getTheStoneState() {
        return TheStoneState;
    }

    public void setTheStoneState(StoneState ss) {
        TheStoneState = ss;
    }

    public float getWidth() {
        return Width;
    }

    public float getHeight() {
        return Height;
    }

    public void addFireScore(float val){
        FireScore+=val;
//    Gdx.app.log("FireScore", String.valueOf(FireScore));
//    Gdx.app.log("val", String.valueOf(val));
    }
    public float getFireScore(){return FireScore;}
    public void resetFireScore(){ FireScore=0;}
}
