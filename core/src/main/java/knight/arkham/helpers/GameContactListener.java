package knight.arkham.helpers;

import com.badlogic.gdx.physics.box2d.*;
import knight.arkham.screens.Box2DScreen;

public class GameContactListener implements ContactListener {

    private final Box2DScreen gameScreen;

    public GameContactListener(Box2DScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void beginContact(Contact contact) {

       Fixture fixtureA = contact.getFixtureA();
       Fixture fixtureB = contact.getFixtureB();

        if ( fixtureA.getUserData() == null || fixtureB.getUserData() == null)
            return;


//        Trabajar contacto con las paredes izquierda y derecha, esto solo funciona
//        bien con las paredes inferiores y superiores
        //comprobacion de elementos, en esta comparacion no me importaria el orden de los elementos
        if (fixtureA.getUserData() == ContactType.BALL || fixtureB.getUserData() == ContactType.BALL){

            if (fixtureA.getUserData() == ContactType.WALL || fixtureB.getUserData() == ContactType.WALL){

                gameScreen.getPhysicsBall().reverseVelocityY();
                gameScreen.getPhysicsBall().incrementSpeed();
            }
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
