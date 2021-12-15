package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Ball;
import src.gameobjects.Brick;
import src.gameobjects.Puck;
import java.util.Random;

public class PuckStrategy extends RemoveBrickStrategyDecorator{

    private static final int AMOUNT_OF_HITS_ALLOWED = 3;
    private static final float BALL_RADIUS = 20;
    private static final float BALL_SPEED = 150;
    private final ImageRenderable ballImage;
    private final Sound collisionSound;

    /**
     * Constructs PuckStrategy object.
     * @param toBeDecorated The collision that will be decorated.
     * @param imageReader The image reader that will be used.
     * @param soundReader The sound reader that will be used.
     */
    public PuckStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader, SoundReader soundReader){
        super(toBeDecorated);
        this.ballImage = imageReader.readImage("assets/mockBall.png", true);
        this.collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
    } // end of constructor

    /**
     * The action that will happen during collision between two objects.
     * @param thisObj The given object.
     * @param otherObj The other object.
     * @param counter Counts how many bricks have been hit.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        if(thisObj instanceof Brick && otherObj instanceof Ball && !(otherObj instanceof Puck)) {
            for (int i = 0; i < AMOUNT_OF_HITS_ALLOWED; i++) {
                GameObject puck = new Puck(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);
                Random rand = new Random();
                float ballVelY = BALL_SPEED, ballVelX = BALL_SPEED;
                if (rand.nextBoolean())
                    ballVelX *= -1;
                if (rand.nextBoolean())
                    ballVelY *= -1;
                puck.setVelocity(new Vector2(ballVelX, ballVelY)); // change the ball's speed
                puck.setCenter(otherObj.getCenter()); // set ball to the middle of the brick
                getGameObjectCollection().addGameObject(puck);
            } // end of for loop
        }// end of if
    } // end of onCollision method
} //end of PuckStrategy class
