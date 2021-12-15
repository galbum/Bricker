package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.MockPaddle;


public class AddPaddleStrategy extends RemoveBrickStrategyDecorator{

    private static final int PADDLE_HEIGHT = 15;
    private static final int PADDLE_WIDTH = 100;
    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = 10;
    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final int NUM_COLLISIONS_TO_DISAPPEAR = 3;

    /**
     * Constructs AddPaddleStrategy game object
     * @param toBeDecorated The collision strategy that will be decorated
     * @param imageReader The image that will appear.
     * @param inputListener The input listener that will be used
     * @param windowDimensions The dimensions of the window
     */
    public AddPaddleStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader,
                             UserInputListener inputListener, Vector2 windowDimensions){
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    } //end of constructor

    /**
     * The affect that will happen during a collision.
     * @param thisObj The given object.
     * @param otherObj The other object.
     * @param counter Counter that counts the blocks that have been hit.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj,otherObj,counter);
        if(!MockPaddle.isInstantiated) {
            ImageRenderable paddleImage = imageReader.readImage("assets/paddle.png", true);
            GameObject paddle = new MockPaddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH ,PADDLE_HEIGHT ), paddleImage, inputListener,
                    windowDimensions, getGameObjectCollection(), MIN_DISTANCE_FROM_SCREEN_EDGE, NUM_COLLISIONS_TO_DISAPPEAR) ;
            paddle.setCenter(new Vector2(thisObj.getCenter().x(), this.windowDimensions.mult(0.5f).y()));
            getGameObjectCollection().addGameObject(paddle);
        } //end of if
    } // end of onCollision method

} //end of class AddPaddleStrategy
