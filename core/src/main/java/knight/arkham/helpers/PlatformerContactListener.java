package knight.arkham.helpers;

import com.badlogic.gdx.physics.box2d.*;
import knight.arkham.screens.PlatformerBox2DScreen;

public class PlatformerContactListener implements ContactListener {

    private final PlatformerBox2DScreen gameScreen;

    public PlatformerContactListener(PlatformerBox2DScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void beginContact(Contact contact) {

       Fixture fixtureA = contact.getFixtureA();
       Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() == null || fixtureB.getUserData() == null)
            return;

        if (fixtureA.getUserData() == ContactType.PLAYER || fixtureB.getUserData() == ContactType.PLAYER){

            if (fixtureA.getUserData() == ContactType.TRAMPOLINE || fixtureB.getUserData() == ContactType.TRAMPOLINE)
                gameScreen.getPlayer().isTrampolineModeActive = true;

            else if (fixtureA.getUserData() == ContactType.FLOOR || fixtureB.getUserData() == ContactType.FLOOR)
                gameScreen.getPlayer().isTrampolineModeActive = false;
        }

        if (fixtureA.getUserData() == ContactType.ENEMY || fixtureB.getUserData() == ContactType.ENEMY){

            if (fixtureA.getUserData() == ContactType.PIPE || fixtureB.getUserData() == ContactType.PIPE)
                gameScreen.getEnemy().isMovingRight = !gameScreen.getEnemy().isMovingRight;
        }

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