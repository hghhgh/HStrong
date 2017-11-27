package ir.abrain.games.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.AbstractMap;

import ir.abrain.games.HStrong;
import ir.abrain.games.helpers.Consts;


public class WorldBox {

    private World world;
    private Body floor;
    private Body lWall;
    private Body rWall;
    private Body ceil;

    public WorldBox(World world, float fx, float fy, float fwidth, float fheight){
        this.world=world;
        setupFloor(fx, fy, fwidth, fheight);
//        setupWalls(fx, fy, fwidth, fheight);
        setupCeiling(fx, fy, fwidth, fheight);

    }

    private void setupWalls(float fx, float fy, float fwidth, float fheight) {
        //leftwall
        BodyDef lbodyDef = new BodyDef();
        lbodyDef.type = BodyDef.BodyType.StaticBody;
        lbodyDef.position.set(new Vector2(-HStrong.scaleW * 500, HStrong.scaleH * 5).scl(Consts.WTB));
        lWall = world.createBody(lbodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(HStrong.scaleW * 10 * Consts.WTB, fy * Consts.WTB);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 1f;//.35f;
        Fixture fixture = lWall.createFixture(fixtureDef);
        lWall.setUserData(new AbstractMap.SimpleEntry<String, Object>("lWall", this));
        shape.dispose();

//        //reftwall
//        BodyDef rbodyDef = new BodyDef();
//        rbodyDef.type = BodyDef.BodyType.StaticBody;
//        rbodyDef.position.set(new Vector2(fx+fwidth-10, 5).scl(Consts.WTB));
//        rWall = world.createBody(rbodyDef);
//        shape = new PolygonShape();
//        shape.setAsBox(10 * Consts.WTB, -fy * Consts.WTB);
//        fixtureDef = new FixtureDef();
//        fixtureDef.shape = shape;
//        fixtureDef.friction = 1f;//.35f;
//        fixture = rWall.createFixture(fixtureDef);
//        rWall.setUserData("rWall");
//        shape.dispose();

    }
//    public WorldBox(World TheWorld, int fx, int fy, int fwidth, int fheight, int cx, int cy, int cwidth, int cheight){
//        this.TheWorld=TheWorld;
//        setupFloor(fx, fy, fwidth, fheight);
//        setupCeiling(cx, cy, cwidth, cheight);
//
//    }

    private void setupFloor(float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2(x, y).scl(Consts.WTB));
        floor = world.createBody(bodyDef);
        // Now define the dimensions of the physics shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 * Consts.WTB, height / 2* Consts.WTB);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = .7f;//.35f;
        fixtureDef.restitution = .3f;

        Fixture fixture = floor.createFixture(fixtureDef);
        floor.setUserData(new AbstractMap.SimpleEntry<String, Object>("wbfloor", this));
        shape.dispose();
    }
   private void setupCeiling(float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
       bodyDef.position.set(new Vector2(0, 0).scl(Consts.WTB));
       ceil = world.createBody(bodyDef);
        // Now define the dimensions of the physics shape
        PolygonShape shape = new PolygonShape();
       shape.setAsBox(width / 2 * Consts.WTB, HStrong.scaleH*25f* Consts.WTB);

        FixtureDef fixtureDef = new FixtureDef();
       fixtureDef.shape = shape;
       fixtureDef.friction = 0f;//.35f;
       fixtureDef.restitution = 0f;

        Fixture fixture = ceil.createFixture(fixtureDef);
       ceil.setUserData(new AbstractMap.SimpleEntry<String, Object>("wbceil", this));
       shape.dispose();
    }

    public Body getFloor()
    {
        return floor;
    }

    public void update(float delta) {
        // Now update the spritee position accordingly to it's now updated Physics floor
//        position = floor.getPosition();

    }

    public void onRestart() {
        floor.setAwake(false);
        floor.setActive(false);
    }
}
