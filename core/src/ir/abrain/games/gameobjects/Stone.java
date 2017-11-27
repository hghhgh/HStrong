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

public class Stone {

    private World world;
    private Body body;

    public Vector2 orgPosition;

    public float width;
    public float height;
    public boolean thrown;

    public Stone(World world, float x, float y, float width, float height) {
        this.width = width;
        this.height = height;
        orgPosition = new Vector2(x, y).scl(Consts.WTB);

        this.world=world;

        thrown = false;
        setupObject(x, y, width, height);
    }

    private void setupObject(float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(x, y).scl(Consts.WTB));
        bodyDef.angularDamping = .05f;
        body = world.createBody(bodyDef);
        body.setAwake(false);
        // Now define the dimensions of the physics shape
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("data/stone/stoneBody"));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 2.5f;//25f;
        fixtureDef.friction = .3f;

        // 4. Create the body fixture automatically by using the loader.
        loader.attachFixture(body, "stoneBody", fixtureDef, width*Consts.WTB);
//        bottleModelOrigin = loader.getOrigin("test01", BOTTLE_WIDTH).cpy();

//        Fixture fixture = body.createFixture(fixtureDef);

        // Shape is the only disposable of the lot, so get rid of it
//        shape.dispose();

        body.setUserData(new AbstractMap.SimpleEntry<String, Object>("stone", this));
        resetGravity();
    }

    public void update(float delta) {
        if(thrown) {
            float v = Math.min(Math.abs(getVelocity()) / 3000f, .1f);
            body.applyForceToCenter(world.getGravity().scl(-v* body.getMass() * body.getGravityScale()), true);
//            Gdx.app.log("v", String.valueOf(v));
            float al=.05f;
            float dx = body.getPosition().x - orgPosition.x;
            float vy = body.getLinearVelocity().y;
            float vx = al*-dx/(width*Consts.WTB) + (1f-al)*body.getLinearVelocity().x;
            body.setLinearVelocity(vx, vy);
//            body.applyForceToCenter(-1*dx, 0, true);
        }
        // Now update the spritee position accordingly to it's now updated Physics body

//        velocity.add(acceleration.cpy().scl(delta));
//
//        if (velocity.y > 200) {
//            velocity.y = 200;
//        }

//        position.add(velocity.cpy().scl(delta));
//
//        // Rotate counterclockwise
//        if (velocity.y < 0) {
//            rotation -= 600 * delta;
//
//            if (rotation < -20) {
//                rotation = -20;
//            }
//        }

        // Rotate clockwise
//        if (isFalling()) {
//            rotation -= rotationSpeed * delta;
//            if (rotation > 90) {
//                rotation = 90;
//            }

//        }

    }

    public Vector2 getPosition(){
        return body.getPosition().cpy().scl(Consts.BTW);
    }

    public void onRestart() {
        body.setAwake(false);
        body.setActive(false);
        body.setTransform(orgPosition, 0);
        thrown = false;
    }

    public void setAwake_Active(boolean awake_Active) {
        body.setActive(awake_Active);
        body.setAwake(awake_Active);
    }

    public float getVelocity() {
        return body.getAngularVelocity();
    }

//    public void setGravityScale(float gsc) {
//        body.setGravityScale(gsc);
//    }

    public void applyInvGravityImpulse(float w) {
        body.applyLinearImpulse(world.getGravity().scl(w * body.getMass() * body.getGravityScale()),
                body.getWorldCenter(), true);
    }

    public void applyVelocityImpulse(float w) {
        float impulse = body.getInertia() * -w / 1f;

        body.applyAngularImpulse(impulse, true);
    }


    public Vector2 getCenter() {
        return body.getWorldCenter().cpy().scl(Consts.BTW);
    }

    public float getAngle() {
        return body.getAngle();
    }

    public void resetGravity() {
        body.setGravityScale(1f);
    }

    public void floatObject() {
//        body.setGravityScale(0.1f);
    }
}
