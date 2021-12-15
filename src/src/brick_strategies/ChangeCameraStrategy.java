package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;
import src.gameobjects.Puck;

public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator{

    private static final int NUM_BALL_COLLISIONS_TO_TURN_OFF = 4;
    private WindowController windowController;
    private BrickerGameManager gameManager;
    private BallCollisionCountdownAgent agent;

    /**
     * Constructs ChangeCameraStrategy game object.
     * @param toBeDecorated The object that will be decorated.
     * @param windowController The window controller.
     * @param gameManager Game manager object .
     */
    public ChangeCameraStrategy(CollisionStrategy toBeDecorated, WindowController windowController,
                                BrickerGameManager gameManager){
        super(toBeDecorated);
        this.windowController = windowController;
        this.gameManager = gameManager;
    } //end of constructor

    /**
     * The action that will happen on collision between two objects.
     * @param thisObj The given object.
     * @param otherObj The other object.
     * @param counter Counts how many bricks have been hit.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        if(!(otherObj instanceof Puck) && this.gameManager.getCamera() == null) {
            gameManager.setCamera(
                    new Camera(
                            otherObj,            //object to follow
                            Vector2.ZERO,    //follow the center of the object
                            windowController.getWindowDimensions().mult(1.2f),  //widen the frame a bit
                            windowController.getWindowDimensions()   //share the window dimensions
                    )
            );
            agent = new BallCollisionCountdownAgent((Ball) otherObj, this, NUM_BALL_COLLISIONS_TO_TURN_OFF);
            getGameObjectCollection().addGameObject(agent);
        } // end of if
    } //end of override method onCollision

    /**
     * Turns off the camera
     */
    public void turnOffCameraChange(){
        gameManager.setCamera(null);
        getGameObjectCollection().removeGameObject(agent);
    } //end of method turnOffCameraChange
} //end of ChangeCameraStrategy class

