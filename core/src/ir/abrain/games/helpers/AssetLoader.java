package ir.abrain.games.helpers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.ArrayList;

import ir.abrain.games.HStrong;

public class AssetLoader {

    private static Json json = new Json();

    public static TextureRegion tx_stone  ,tx_stone_b  , tx_trees, tx_trees_b, tx_flower,tx_flower_b, tx_logo, tx_retry;
    public static TextureRegion tx_frame;
    public static TextureRegion tx_highscore, tx_buttonUp, tx_buttonDown;
    public static  TextureRegion[] tx_fireBtnLvls;
    public static TextureRegion tx_zoomblur;

    public static TiledDrawable tx_dirt;

    public static Animation anim_lvl3fireball,anim_lvl2fireball, anim_lvl1fireball;
    public static Animation anim_bird1_idle,anim_bird1_damage;

    public static Sound snd_throw, snd_select, snd_second, snd_stoneCollision, snd_birdstoneCollision, snd_jet;
    public static Sound snd_birdcrash;

    public static Music msc_bg,msc_pradise;

    public static BitmapFont fnt_font, fnt_small;

    private static Preferences prefs;

    public static void load(){
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setIgnoreUnknownFields(true);
        json.setOutputType(JsonWriter.OutputType.json);

        loadUI();
        loadBG();

        loadStone();

        loadBirds();


        loadSound();

        loadFonts();

        // Create (or retrieve existing) preferences file
        prefs = Gdx.app.getPreferences("HStrong");

        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }
    }

    private static void loadBirds() {
        int        FRAME_COLS = 8;
        int        FRAME_ROWS = 1;
        Texture sheet;
        TextureRegion[]                 frames;
        sheet = new Texture(Gdx.files.internal("data/birds/bird1_i.png"));
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/FRAME_COLS, sheet.getHeight()/FRAME_ROWS);              // #10
        frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                tmp[i][j].flip(false,true);
                frames[index++] = tmp[i][j];
            }
        }
        anim_bird1_idle = new Animation(.05f, frames);

        sheet = new Texture(Gdx.files.internal("data/birds/bird1_d.png"));
        tmp = TextureRegion.split(sheet, sheet.getWidth()/FRAME_COLS, sheet.getHeight()/FRAME_ROWS);
        frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                tmp[i][j].flip(false,true);
                frames[index++] = tmp[i][j];
            }
        }
        anim_bird1_damage = new Animation(.05f, frames);
    }

    private static void loadStone() {
        tx_stone = new TextureRegion(new Texture(Gdx.files.internal("data/stone/stone.png")));
        tx_stone_b = new TextureRegion(new Texture(Gdx.files.internal("data/stone/stone_b.png")));

        int        FRAME_COLS = 13;         // #1
        int        FRAME_ROWS = 1;         // #2
        Texture                         sheet;              // #4
        TextureRegion[]                 frames;             // #5
        sheet = new Texture(Gdx.files.internal("data/stone/fireballLvl2.png")); // #9
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/FRAME_COLS, sheet.getHeight()/FRAME_ROWS);              // #10
        frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        anim_lvl2fireball = new Animation(0.05f, frames);      // #11
//        anim_lvl2fireball.setFrameDuration(50 / 1000.0f);
        FRAME_COLS = 5;
        FRAME_ROWS = 1;
        sheet = new Texture(Gdx.files.internal("data/stone/fireballLvl1.png")); // #9
        tmp = TextureRegion.split(sheet, sheet.getWidth()/FRAME_COLS, sheet.getHeight()/FRAME_ROWS);              // #10
        frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        anim_lvl1fireball = new Animation(0.06f, frames);      // #11
//        anim_lvl1fireball.setFrameDuration(60/1000.0f);

        FRAME_COLS = 26;
        FRAME_ROWS = 1;
        sheet = new Texture(Gdx.files.internal("data/stone/fireballLvl3.png")); // #9
        tmp = TextureRegion.split(sheet, sheet.getWidth()/FRAME_COLS, sheet.getHeight()/FRAME_ROWS);              // #10
        frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        anim_lvl3fireball = new Animation(0.06f, frames);      // #11
//        anim_lvl1fireball.setFrameDuration(60/1000.0f);
    }

    private static void loadBG() {
        tx_trees = new TextureRegion(new Texture(Gdx.files.internal("data/bg/trees.png")));
        tx_trees.flip(false, true);
        tx_trees_b = new TextureRegion(new Texture(Gdx.files.internal("data/bg/trees_b.png")));
        tx_trees_b.flip(false, true);
//        tx_cloud = new TextureRegion(new Texture(Gdx.files.internal("data/bg/cloud.png")));
//        tx_cloud.flip(false, true);
        tx_flower = new TextureRegion(new Texture(Gdx.files.internal("data/bg/flowers.png")));
        tx_flower.flip(false, true);
        tx_flower_b = new TextureRegion(new Texture(Gdx.files.internal("data/bg/flowers_b.png")));
        tx_flower_b.flip(false, true);
        tx_dirt = new TiledDrawable(new TextureRegion(new Texture(Gdx.files.internal("data/bg/dirt.png"))));
        tx_dirt.getRegion().flip(false, true);
        tx_zoomblur = new TextureRegion(new Texture(Gdx.files.internal("data/bg/zoomBlur.png")));
    }

    private static void loadUI() {
        tx_logo = new TextureRegion(new Texture(Gdx.files.internal("data/ui/logo.png")));
        tx_frame = new TextureRegion(new Texture(Gdx.files.internal("data/ui/frame.png")));
        tx_retry = new TextureRegion(new Texture(Gdx.files.internal("data/ui/retry.png")));
//        tx_ready = new TextureRegion(new Texture(Gdx.files.internal("data/ui/retry.png")));
//        tx_gameover = new TextureRegion(new Texture(Gdx.files.internal("data/ui/retry.png")));
        tx_highscore = new TextureRegion(new Texture(Gdx.files.internal("data/ui/highscore.png")));
        tx_highscore.flip(false, true);
        tx_buttonUp = new TextureRegion(new Texture(Gdx.files.internal("data/ui/playUp.png")));
        tx_buttonDown = new TextureRegion(new Texture(Gdx.files.internal("data/ui/playDown.png")));

        Texture                         sheet;              // #4
        tx_fireBtnLvls = new TextureRegion[7];
        sheet = new Texture(Gdx.files.internal("data/ui/firebtn.png")); // #9
        ArrayList<JsonValue> list = json.fromJson(ArrayList.class,Gdx.files.internal("data/ui/firebtn.json"));
        for (JsonValue v : list) {
            TxData td = json.readValue(TxData.class, v);
            if (td.name.equals("fireBtn00")) {
                tx_fireBtnLvls[0] = new TextureRegion(sheet, td.x, td.y, td.width, td.height);
                tx_fireBtnLvls[0].flip(false, true);
            }else if (td.name.equals("fireBtn10")) {
                tx_fireBtnLvls[1] = new TextureRegion(sheet, td.x, td.y, td.width, td.height);
                tx_fireBtnLvls[1].flip(false, true);
            }else if (td.name.equals("fireBtn11")) {
                tx_fireBtnLvls[2] = new TextureRegion(sheet, td.x, td.y, td.width, td.height);
                tx_fireBtnLvls[2].flip(false, true);
            }else if (td.name.equals("fireBtn20")) {
                tx_fireBtnLvls[3] = new TextureRegion(sheet, td.x, td.y, td.width, td.height);
                tx_fireBtnLvls[3].flip(false, true);
            }else if (td.name.equals("fireBtn21")) {
                tx_fireBtnLvls[4] = new TextureRegion(sheet, td.x, td.y, td.width, td.height);
                tx_fireBtnLvls[4].flip(false, true);
            }else if (td.name.equals("fireBtn30")) {
                tx_fireBtnLvls[5] = new TextureRegion(sheet, td.x, td.y, td.width, td.height);
                tx_fireBtnLvls[5].flip(false, true);
            }else if (td.name.equals("fireBtn31")) {
                tx_fireBtnLvls[6] = new TextureRegion(sheet, td.x, td.y, td.width, td.height);
                tx_fireBtnLvls[6].flip(false, true);
            }
        }
    }

    private static void loadFonts() {
        fnt_font = new BitmapFont(Gdx.files.internal("font.fnt"));
        fnt_font.getData().setScale(HStrong.scaleW * 1f, HStrong.scaleH * -1f);
        fnt_small= new BitmapFont(Gdx.files.internal("font.fnt"));
        fnt_small.getData().setScale(HStrong.scaleW * .5f,HStrong.scaleH *  -.5f);
    }

    private static void loadSound() {
        snd_throw = Gdx.audio.newSound(Gdx.files.internal("sound/throw.ogg"));
        snd_select = Gdx.audio.newSound(Gdx.files.internal("sound/select.ogg"));
        snd_second = Gdx.audio.newSound(Gdx.files.internal("sound/second.ogg"));
        snd_stoneCollision = Gdx.audio.newSound(Gdx.files.internal("sound/collision.ogg"));
        snd_birdstoneCollision = Gdx.audio.newSound(Gdx.files.internal("sound/bsc.ogg"));
        snd_jet = Gdx.audio.newSound(Gdx.files.internal("sound/jet.ogg"));
        snd_birdcrash = Gdx.audio.newSound(Gdx.files.internal("sound/birdcrash.ogg"));

        msc_bg = Gdx.audio.newMusic(Gdx.files.internal("sound/bg.ogg"));
        msc_pradise = Gdx.audio.newMusic(Gdx.files.internal("sound/paradise.mp3"));
        msc_bg.setLooping(true);
        msc_bg.play();
        msc_pradise.setLooping(true);
    }

    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        tx_logo.getTexture().dispose();
        tx_retry.getTexture().dispose();
//        tx_ready.getTexture().dispose();
//        tx_gameover.getTexture().dispose();
        tx_highscore.getTexture().dispose();
        tx_stone.getTexture().dispose();
        tx_trees.getTexture().dispose();
//        tx_cloud.getTexture().dispose();
        tx_flower.getTexture().dispose();
        tx_dirt.getRegion().getTexture().dispose();

        snd_throw.dispose();
        snd_second.dispose();
        snd_select.dispose();
        snd_stoneCollision.dispose();
        msc_bg.dispose();

        fnt_font.dispose();
    }

    public static class TxData
    {
        String name;
        int x;
        int y;
        int width;
        int height;
    }

}
