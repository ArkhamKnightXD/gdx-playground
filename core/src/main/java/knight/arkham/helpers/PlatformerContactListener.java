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

        if (fixtureA.getUserData() == ContactType.PLAYER || fixtureB.getUserData() == ContactType.PLAYER){

            if (fixtureA.getUserData() == ContactType.MOVINGFLOOR || fixtureB.getUserData() == ContactType.MOVINGFLOOR)
                gameScreen.getMovingFloor().isMovingRight = !gameScreen.getMovingFloor().isMovingRight;

            if (fixtureA.getUserData() == ContactType.HEAVYGRAVITYFLOOR || fixtureB.getUserData() == ContactType.HEAVYGRAVITYFLOOR)
                gameScreen.getPlayer().getBody().setGravityScale(0.5f);

            else
                gameScreen.getPlayer().getBody().setGravityScale(1);

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
