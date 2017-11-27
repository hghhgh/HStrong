package ir.abrain.games.gameworld;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import java.util.AbstractMap;

import ir.abrain.games.gameobjects.Bird;
import ir.abrain.games.helpers.AssetLoader;

/**
 * Created by ghiassi on 12/4/15.
 */
public class WorldContactListener implements ContactListener {

    GameWorld TheWord;

    public WorldContactListener(GameWorld gameWorld) {
        TheWord = gameWorld;
    }

    @Override
    public void beginContact(Contact contact) {

        try {

            AbstractMap.SimpleEntry<String, Object> nameA = (AbstractMap.SimpleEntry<String, Object>) contact.getFixtureA().getBody().getUserData();
            AbstractMap.SimpleEntry<String, Object> nameB = (AbstractMap.SimpleEntry<String, Object>) contact.getFixtureB().getBody().getUserData();

            if (nameB != null && nameB.getKey().equals("stone")) {
                if (nameA != null && nameA.getKey().equals("bird")) {
                    if(((Bird) nameA.getValue()).birdState != Bird.BirdState.DMAGED) {
                        ((Bird) nameA.getValue()).birdState = Bird.BirdState.DMAGED;
                        TheWord.addFireScore(.5f);
                        Body stone = contact.getFixtureB().getBody();
                        float sc = .3f;
                        stone.applyLinearImpulse(TheWord.TheWorld.getGravity().scl(-sc * stone.getMass() * stone.getGravityScale())
                                , stone.getWorldCenter(), true);
                        playBirdStoneSound();
                    }
                }
            }
            if (nameB != null && nameB.getKey().equals("bird"))
                if (nameA != null && nameA.getKey().equals("stone")) {
                    if(((Bird) nameB.getValue()).birdState != Bird.BirdState.DMAGED) {
                        ((Bird) nameB.getValue()).birdState = Bird.BirdState.DMAGED;
                        TheWord.addFireScore(.5f);
                        Body stone = contact.getFixtureA().getBody();
                        float sc = .3f;
                        stone.applyLinearImpulse(TheWord.TheWorld.getGravity().scl(-sc * stone.getMass() * stone.getGravityScale())
                                , stone.getWorldCenter(), true);
                        playBirdStoneSound();
                    }
                }

                if (nameA != null && nameA.getKey().equals("stone"))
                    if (nameB != null && nameB.getKey().equals("wbfloor"))
                    {
                        playStoneSound(contact.getFixtureA().getBody().getAngularVelocity());
                    TheWord.setTheStoneState(GameWorld.StoneState.IDLE);
                    TheWord.resetFireScore();
                }
                if (nameB != null && nameB.getKey().equals("stone"))
                    if (nameA != null && nameA.getKey().equals("wbfloor"))
                {
                    playStoneSound(contact.getFixtureB().getBody().getAngularVelocity());
                    TheWord.setTheStoneState(GameWorld.StoneState.IDLE);
                    TheWord.resetFireScore();
                }
        } catch (Exception e)
        {
        }
    }

    private void playBirdStoneSound() {
        AssetLoader.snd_birdstoneCollision.play();
        AssetLoader.snd_birdcrash.play();
    }

    private void playStoneSound(float vel) {
//        if(Math.abs(body.getAngularVelocity()) > 0.7)
        if ((vel) < -0.7) { // 0.1 < Math.abs(vel) - .6 < 10
            float vol = Math.min(Math.abs(vel) / 10 - 0.06f, 1.0f);
            AssetLoader.snd_stoneCollision.play(vol);
        }
        AssetLoader.snd_jet.stop();

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
