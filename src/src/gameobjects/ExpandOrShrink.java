package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class ExpandOrShrink extends GameObject {
    private GameObjectCollection gameObjectCollection;
    private float mult;
    private Vector2 windowDimensions;
    private final int MIN_DISTANCE = 10;

    /**
     * Construct a new GameObject instance.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param windowDimensions
     */
    public ExpandOrShrink(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                          GameObjectCollection gameObjectCollection, float mult, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjectCollection = gameObjectCollection;
        this.mult = mult;
        this.windowDimensions = windowDimensions;
    } //end of constructor

    /**
     * defines what happens on Collision between ball and brick.
     * @param other The other Game Object.
     * @param collision    The sort of collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if(other instanceof Paddle){
            super.onCollisionEnter(other, collision);
            if(other.getDimensions().x()*this.mult >= this.windowDimensions.x() )
                other.setDimensions(new Vector2(this.windowDimensions.x() -40,other.getDimensions().y()));
            else if(other.getDimensions().x()*this.mult < MIN_DISTANCE)
                other.setDimensions(new Vector2(MIN_DISTANCE,other.getDimensions().y()));
            else
                other.setDimensions(new Vector2(other.getDimensions().x()*mult,other.getDimensions().y()));
            this.gameObjectCollection.removeGameObject(this);
        } //end of if
    } //end of onCollisionEnter method

    /**
     * @param other The other object
     * @return True if other is instance of paddle
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return (other instanceof Paddle);
    } //end of shouldCollideWith
} //end of class ExpandOrShrink class
