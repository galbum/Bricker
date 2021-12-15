package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {

    private static final float MOVEMENT_SPEED = 300 ;
    private final float windowXDimensions;
    private final int minDistanceFromEdge;
    private UserInputListener inputListener;


    /**
     * Construct a new GameObject instance.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param inputListener The input listener.
     * @param windowDimensions The dimensions of the window
     * @param minDistanceFromEdge The minimum distance from edge.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener, Vector2 windowDimensions,int minDistanceFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowXDimensions = windowDimensions.x();
        this.minDistanceFromEdge = minDistanceFromEdge;
    } //end of constructor

    /**
     * Overrides the update method.
     * @param deltaTime Time of current moment.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){ //if left arrow key was pressed
            movementDir = movementDir.add(Vector2.LEFT);
        } // end of if
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){ //if right arrow key was pressed
            movementDir = movementDir.add(Vector2.RIGHT);
        } //end of if
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
        if(getTopLeftCorner().x() < minDistanceFromEdge )
            transform().setTopLeftCornerX(minDistanceFromEdge);
        if(getTopLeftCorner().x() > this.windowXDimensions - minDistanceFromEdge - getDimensions().x())
            transform().setTopLeftCornerX(this.windowXDimensions - minDistanceFromEdge - getDimensions().x());
    } //end of update method
} // end of user paddle class
