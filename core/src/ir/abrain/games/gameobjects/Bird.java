package ir.abrain.games.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.AbstractMap;

import ir.abrain.games.helpers.BodyEditorLoader;
import ir.abrain.games.helpers.Consts;

/**
 * Created by ghiassi on 12/9/15.
 */
public class Bird {

    private World world;
    private Body body;
    public BirdState birdState;

    public Vector2 orgPosition;

    public float width;
    public float height;
//    private Vector2 velocity;
//    private Vector2 acceleration;
    private float vX;
    private float vY;

    public String Name;

    public int onScoreCome=0;

    private float vXoffset = 10;


    public enum BirdState {
        NORMAL, DMAGED
    }

    public Bird(String name, World world, int osc, float x, float y, float width, float height) {
        this.width = width;
        this.height = height;
        orgPosition = new Vector2(x, y).scl(Consts.WTB);
        this.Name = name;
        this.onScoreCome = osc;

        this.world = world;

        setupObject(x, y, width, height);

        vX=0;
//        velocity = new Vector2(0, 0);
//        acceleration = new Vector2(0,0);
        birdState = BirdState.NORMAL;
    }

    private void setupObject(float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(x, y).scl(Consts.WTB));
//        bodyDef.angle = (float) (Math.PI);
        bodyDef.angle = (float) (-Math.PI);
//        bodyDef.angle = 0;
        body = world.createBody(bodyDef);
        body.setAwake(true);
        body.setActive(true);
        // Now define the dimensions of the physics shape
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("data/birds/bird1_ph"));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 2.f;//25f;
        fixtureDef.friction = .0f;

        loader.attachFixture(body, "bird1_ph", fixtureDef, width*Consts.WTB);

        body.setUserData(new AbstractMap.SimpleEntry<String, Object>("bird", this));
        body.setGravityScale(1);
        vY = world.getGravity().cpy().scl( -body.getMass()  * body.getGravityScale()).y;
    }

    public void update(float delta) {
//        body.setLinearVelocity(vX, 0);

        if(birdState == BirdState.DMAGED)
            vY=0;
            body.applyForceToCenter(new Vector2((vXoffset + vX), vY), true);
//        body.setLinearVelocity(vXoffset + vX, body.getLinearVelocity().y);
//        if(body.getLinearVelocity().x < 5)
////            isTurnedOn = false;
//        Gdx.app.log("vX", String.valueOf(vX));
//        Gdx.app.log("velocity", body.getLinearVelocity().toString());
    }

    public void setSpeed(float speed) {
        vX = (int) (-Math.min(speed, 3000)/1f);
    }

    public Vector2 getPosition(){
        return body.getPosition().cpy().scl(Consts.BTW);
    }

//    public void onRestart() {
//        setSpeed(0, 0);
//        body.setLinearVelocity(0,0);
//        body.setAngularVelocity(0);
//        birdState = BirdState.NORMAL;
//        body.setTransform(orgPosition, 0);
//    }
//    public void setAwake_Active(boolean awake_Active) {
//        body.setActive(awake_Active);
//        body.setAwake(awake_Active);
//    }
//
    public float getVelocity() {
        return body.getLinearVelocity().x;
    }
//
//    public void setGravityScale(float gsc) {
//        body.setGravityScale(gsc);
//    }
//
//    public void applyInvGravityImpulse(float w) {
//        body.applyLinearImpulse(world.getGravity().scl(w * body.getMass()),
//                body.getWorldCenter(), true);
//    }
//
//    public void applyVelocityImpulse(float w) {
//        float inertia = body.getInertia();
//        float impulse = inertia * -w / 10f;
//
//        body.applyAngularImpulse(Math.min(-.1f * inertia, impulse), true);
//    }
//    public float getAngle() {
//        return body.getAngle();
//    }
//

    public Vector2 getCenter() {
        return body.getWorldCenter().cpy().scl(Consts.BTW);
    }



}
