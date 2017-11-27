package ir.abrain.games.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;

import ir.abrain.games.HStrong;
import ir.abrain.games.helpers.AssetLoader;

/**
 * Created by ghiassi on 12/3/15.
 */
public class ScrollHandler {
    // ScrollHandler will create all five objects that we need.
    public Grass TreesL, TreesR;
    public Flower flowerL, flowerR;
    public Dirt dirtL, dirtR;
    public Dirt cielL, cielR;

    private int Width, Height;

    // ScrollHandler will use the constants below to determine
    // how fast we need to scroll and also determine
    // the size of the gap between each pair of pipes.

    // Capital letters are used by convention when naming constants.
    public static float SCROLL_SPEED = 0;
    public static float SCROLL_SPEED_Front_Diff = 0;
    public float Acceleration=0;

    // Constructor receives a float that tells us where we need to create our
    // Grass and Pipe objects.
    public ScrollHandler(World world) {
        Width = Gdx.graphics.getWidth();
        Height = Gdx.graphics.getHeight();

        int sc = 1;
        float r = sc*Width / (HStrong.scaleW * AssetLoader.tx_trees.getRegionWidth());
        TreesL = new Grass(0, Height - HStrong.scaleH * AssetLoader.tx_trees.getRegionHeight() * r,
                sc*Width, (int)(HStrong.scaleH * AssetLoader.tx_trees.getRegionHeight() * r), SCROLL_SPEED);
        TreesR = new Grass(TreesL.getTailXLeft(), Height - HStrong.scaleH * AssetLoader.tx_trees.getRegionHeight() * r,
                sc*Width, (int)(HStrong.scaleH * AssetLoader.tx_trees.getRegionHeight() * r), SCROLL_SPEED);

        r = Width / (HStrong.scaleW *  AssetLoader.tx_flower.getRegionWidth());
        flowerL = new Flower(0, Height - HStrong.scaleH * AssetLoader.tx_flower.getRegionHeight() * r-HStrong.scaleH * 50,
                Width, (int)(HStrong.scaleH * AssetLoader.tx_flower.getRegionHeight() * r), SCROLL_SPEED+SCROLL_SPEED_Front_Diff);
        flowerR = new Flower(flowerL.getTailXLeft(), Height - HStrong.scaleH*AssetLoader.tx_flower.getRegionHeight() * r-HStrong.scaleH*50,
                Width, (int)(HStrong.scaleH*AssetLoader.tx_flower.getRegionHeight() * r), SCROLL_SPEED+SCROLL_SPEED_Front_Diff);

        dirtL = new Dirt(0, Height - HStrong.scaleH*75,Width, HStrong.scaleH*75, SCROLL_SPEED+SCROLL_SPEED_Front_Diff);
        dirtR = new Dirt(dirtL.getTailXLeft(), Height - HStrong.scaleH*75,Width, HStrong.scaleH*75, SCROLL_SPEED+SCROLL_SPEED_Front_Diff);

        cielL = new Dirt(0, 0,Width, HStrong.scaleH*25, SCROLL_SPEED+SCROLL_SPEED_Front_Diff);
        cielR = new Dirt(cielL.getTailXLeft(), 0,Width, HStrong.scaleH*25, SCROLL_SPEED+SCROLL_SPEED_Front_Diff);
    }

    public void update(float delta) {

        // Update our objects
        TreesL.update(delta);
        TreesR.update(delta);
        flowerL.update(delta);
        flowerR.update(delta);
        dirtL.update(delta);
        dirtR.update(delta);
        cielL.update(delta);
        cielR.update(delta);

        updateVelocity();

        // Check if any of the pipes are scrolled left,
        // and reset accordingly
//        if (flowerL.isScrolledLeft()) {
//            flowerL.reset(flowerR.getTailXLeft());
//        } else if (flowerR.isScrolledLeft()) {
//            flowerR.reset(flowerL.getTailXLeft());
//        }
        if (flowerL.isScrolledRight()) {
            flowerL.reset(flowerR.getTailXRight());
        } else if (flowerR.isScrolledRight()) {
            flowerR.reset(flowerL.getTailXRight());
        }

        // Same with grass
//        if (TreesL.isScrolledLeft()) {
//            TreesL.reset(TreesR.getTailXLeft());
//        } else if (TreesR.isScrolledLeft()) {
//            TreesR.reset(TreesL.getTailXLeft());
//        }
        if (TreesL.isScrolledRight()) {
            TreesL.reset(TreesR.getTailXRight());
        } else if (TreesR.isScrolledRight()) {
            TreesR.reset(TreesL.getTailXRight());
        }

         if (dirtL.isScrolledRight()) {
             dirtL.reset(dirtR.getTailXRight());
        } else if (dirtR.isScrolledRight()) {
             dirtR.reset(dirtL.getTailXRight());
        }
         if (cielL.isScrolledRight()) {
             cielL.reset(cielR.getTailXRight());
        } else if (cielR.isScrolledRight()) {
             cielR.reset(cielL.getTailXRight());
        }
    }

    private void updateVelocity() {
        SCROLL_SPEED += Acceleration;
        SCROLL_SPEED = Math.max(0,SCROLL_SPEED);
        SCROLL_SPEED_Front_Diff += Acceleration;
        SCROLL_SPEED_Front_Diff = Math.max(0,SCROLL_SPEED_Front_Diff);

        setScrollSpeed();
    }

    private void setScrollSpeed() {
        TreesL.setScrollSpeed(SCROLL_SPEED);
        TreesR.setScrollSpeed(SCROLL_SPEED);
        dirtL.setScrollSpeed(SCROLL_SPEED + SCROLL_SPEED_Front_Diff);
        dirtR.setScrollSpeed(SCROLL_SPEED + SCROLL_SPEED_Front_Diff);
        cielL.setScrollSpeed(SCROLL_SPEED + SCROLL_SPEED_Front_Diff);
        cielR.setScrollSpeed(SCROLL_SPEED + SCROLL_SPEED_Front_Diff);
        flowerL.setScrollSpeed(SCROLL_SPEED + SCROLL_SPEED_Front_Diff);
        flowerR.setScrollSpeed(SCROLL_SPEED + SCROLL_SPEED_Front_Diff);
    }

    public void setScrollSpeed(float v, float a) {
        Acceleration=a;
        SCROLL_SPEED = v;
        SCROLL_SPEED_Front_Diff=0;
        if(v>10)
            SCROLL_SPEED_Front_Diff = v+.1f*v;

        setScrollSpeed();
    }


    public void onRestart() {
        setScrollSpeed(0.0f,0.0f);
    }
}
