package knight.arkham.objects.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.objects.box2D.PlayerAnimationState;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

//La clase sprite nos hereda un conjunto de funcionalidades encargadas de manejar sprites.
public class Mario extends Sprite {

    private boolean timeToRedefineMario;

    //    Con estas variables manejaré el estado del jugador, ya sea que este parado o corriendo
//    Y necesitaré una variable para almacenar el estado actual y el anterior
    private PlayerAnimationState currentState;
    private PlayerAnimationState previousState;

    //    Aqui almacenaremos el tiempo que hay en cada estado en específico, para llevar un record.
    private float stateTimer;

    //    En esta variable almacenaré las animaciones de mario
    private Animation<TextureRegion> playerRunning;
    private TextureRegion playerJumping;
    private boolean isPlayerRunningRight;
    private Body body;

    private final TextureRegion playerStand;

    private TextureRegion dyingPlayer;

    private boolean isMarioDead;

    private final World world;


    public Mario(World world, TextureRegion textureRegion) {

//        Debido a que heredamos de la clase sprite podemos implementar el constructor, al que le mandaremos un texture
//        region, y este texture region lo podremos referenciar más abajo mediante la función getTexture.
//        Esto nos dara la region que le pertenece a los sprite llamados little_mario
//        Esto puedo removerlo pues no tiene mucho proposito, ya que utilizo aqui varios getTextureAtlas
        super(textureRegion);

        this.world = world;


//        Standing debe de ser el estado inicial, tanto para el current como el previous state
        currentState = PlayerAnimationState.STANDING;
        previousState = PlayerAnimationState.STANDING;
        stateTimer = 0;

        isPlayerRunningRight = true;

        body = Box2DHelper.createPlayerBody(

                new Box2DBody(new Rectangle(450, 75, 16, 16), world, this)
        );

//Utilizamos getTexture para obtener el texture region que indicamos en el constructor súper y luego indicamos
// las coordenadas donde está la imagen inicial que deseamos, como es la primera imagen Le indicamos 0 0, aunque aqui
// tuve que hacer par de ajustes, debido a la existencia de las bolas de fuego El origen del textureRegion empieza en
// la esquina superior izquierda, el 10 en Y funciona para bajar 10px y asi tomar la imagen inicial de mario.
// Un texture region es un conjunto de imágenes juntas.
        playerStand = new TextureRegion(getTexture(), 0, 10, 16, 16);


        //    Funciones heredadas de la clase Sprite
        setRegion(playerStand);
        setBounds(0, 0, 32 / PIXELS_PER_METER, 32 / PIXELS_PER_METER);

        makePlayerAnimations();
    }

    private void makePlayerAnimations() {

        // Preparando mis animaciones
        Array<TextureRegion> animationFrames = new Array<>();

//        La animación de correr se encuentra en los sprite 1 hasta el 3, lo texture region empiezan también en 0
        for (int i = 1; i < 4; i++) {

//  Multiplico por 16 i, para asi escoger del segundo sprite de mario en adelante, pues aqui es que empieza
//  la animación, en conclusion la posición en X al principio será 16, en la siguiente iteración 32, etc..
            animationFrames.add(new TextureRegion(getTexture(), i * 16, 10, 16, 16));
        }

// De esta forma defino una animación, el primer parametro es la duración de cada frame
//        y el segundo el arreglo de textureRegion
        playerRunning = new Animation<>(0.1f, animationFrames);

//        Luego limpiamos el arreglo pues ya no necesitamos los elementos dentro de este y necesitamos
//        llenar el arreglo con nuevos elementos
        animationFrames.clear();


        // Como el salto es de solo 1 frame tanto para little_mario como para big_mario no hay necesidad de guardar
        // esto en un tipo de dato animation y hacer el loop. Si no guardar directamente el textureRegion
        playerJumping = new TextureRegion(getTexture(), 80, 10, 16, 16);
        dyingPlayer = new TextureRegion(getTexture(), 96, 0, 16, 32);

    }

    //    Esta función hace básicamente lo mismo que cuando mario crece, la diferencia es que aqui será para destruir
//    el cuerpo de big mario, para volver a crear el cuerpo de little mario
    private void redefineMarioBody() {

        Vector2 playerCurrentPosition = Box2DHelper.getSimplifiedCurrentPosition(body);

        world.destroyBody(body);

        body = Box2DHelper.createPlayerBody(

                new Box2DBody(new Rectangle(playerCurrentPosition.x, playerCurrentPosition.y, 16, 16) , world, this)
        );

        timeToRedefineMario = false;
    }


    public void update(float deltaTime) {

            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

        setRegion(getActualRegion(deltaTime));

        if (timeToRedefineMario)
            redefineMarioBody();

        playerMovement();
    }

    private void playerMovement() {

        //        Si mario esta muerto no se podrá mover
        if (currentState != PlayerAnimationState.DEAD) {

            // Todo salta varias veces
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                body.applyLinearImpulse(new Vector2(0, 4f), body.getWorldCenter(), true);

//        Si quiero reducir o aumentar la maxima velocidad de mario debo jugar con los valores al final del if
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && body.getLinearVelocity().x <= 1.3)
                body.applyLinearImpulse(new Vector2(1, 0), body.getWorldCenter(), true);

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && body.getLinearVelocity().x >= -1.3)
                body.applyLinearImpulse(new Vector2(-1, 0), body.getWorldCenter(), true);

        }
    }

    private TextureRegion getActualRegion(float deltaTime) {

        currentState = getPlayerCurrentState();

        TextureRegion region;

        switch (currentState) {

            case DEAD:
                region = dyingPlayer;
                break;

            case JUMPING:
// El stateTimer será lo que esta función tomara de referencia para decidir si cambiara de un sprite al siguiente.
                region = playerJumping;
                break;
            case RUNNING:
// Como deseamos que esta animación se repita de principio a fin siempre que estemos corriendo, le enviamos
// un segundo parametro a esta función, donde le indicamos que sea true a looping
                region = playerRunning.getKeyFrame(stateTimer, true);
                break;

            case FALLING:
            case STANDING:
            default:
                region = playerStand;
        }

        flipPlayerOnXAxis(region);

//Si el estado actual no es igual al anterior, entonces debemos pasar a otro estado, si no debemos reiniciar el timer.
        stateTimer = currentState == previousState ? stateTimer + deltaTime : 0;
        previousState = currentState;

        return region;
    }

    private void flipPlayerOnXAxis(TextureRegion region) {

        //Evaluación para determinar si las animaciones iran para la izquierda o la derecha isFlipX retorna true,
        // si la region ha sido volteada, en nuestro caso volteado a la izquierda La función flip requiere dos booleans
        // uno para X y otro para Y, en nuestro caso solo queremos voltear el eje X.
        if ((body.getLinearVelocity().x < 0 || !isPlayerRunningRight) && !region.isFlipX()) {

            region.flip(true, false);
            isPlayerRunningRight = false;
        } else if ((body.getLinearVelocity().x > 0 || isPlayerRunningRight) && region.isFlipX()) {

            region.flip(true, false);
            isPlayerRunningRight = true;
        }
    }

    private PlayerAnimationState getPlayerCurrentState() {

        //        Esta debe de ser la animation a la que se le dé prioridad sobre las demás. Pues si mario
        //        murió lo demás es irrelevante
        if (isMarioDead)
            return PlayerAnimationState.DEAD;

//        La segunda condición en el O lógico, se refiere a que si el player había saltado y en ese
//        salto cayó por un hueco, entonces continua con la animación de jumping
        else if (body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && previousState == PlayerAnimationState.JUMPING))
            return PlayerAnimationState.JUMPING;

//        Deseamos que la animación de falling sea diferente a la de jumping y por esta
//        razón establecimos este estado y esta condición
        else if (body.getLinearVelocity().y < 0)
            return PlayerAnimationState.FALLING;

        else if (body.getLinearVelocity().x != 0)
            return PlayerAnimationState.RUNNING;

        else
            return PlayerAnimationState.STANDING;
    }


    public Body getBody() {
        return body;
    }

    public float getStateTimer() {
        return stateTimer;
    }

    public PlayerAnimationState getCurrentState() {return currentState;}
}
