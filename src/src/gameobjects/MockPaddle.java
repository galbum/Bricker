package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class MockPaddle extends Paddle {

    public static boolean isInstantiated = false;
    private GameObjectCollection gameObjectCollection;
    private final int numCollisionsToDisappear;
    private int numOfCollision;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner       Position of the object, in window coordinates (pixels).
     *                            Note that (0,0) is the top-left corner of the window.
     * @param dimensions          Width and height in window coordinates.
     * @param renderable          The renderable representing the object. Can be null, in which case
     * @param inputListener       The input listener.
     * @param windowDimensions    The dimensions of the window
     * @param minDistanceFromEdge The minimum distance from edge.
     */
    public MockPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener, Vector2 windowDimensions,
                       GameObjectCollection gameObjectCollection,int minDistanceFromEdge, int numCollisionsToDisappear) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistanceFromEdge);
        this.gameObjectCollection = gameObjectCollection;
        this.numCollisionsToDisappear = numCollisionsToDisappear;
        this.isInstantiated = true;
        this.numOfCollision = 0;
    } //end of constructor

    /**
     * Defines what happens when entering the collision between two objects.
     * @param other The other object
     * @param collision The type of collision
     */
    public void onCollisionEnter(GameObject other, Collision collision){
        this.numOfCollision++;
        if(numOfCollision >= this.numCollisionsToDisappear) {
            this.gameObjectCollection.removeGameObject(this);
            this.isInstantiated = false;
        }//end of if
    }// end of method onCollisionEnter

} // end of class MockPaddle
