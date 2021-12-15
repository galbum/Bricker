package src.brick_strategies;

import danogl.GameObject;
import danogl.util.Counter;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;

public class RemoveBrickStrategy implements CollisionStrategy {

    private GameObjectCollection gameObjects;

    /**
     *  Construct a new RemoveBrickStrategy game object.
     * @param gameObjects The given game objects
     */
    public RemoveBrickStrategy(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
    } //end of RemoveBrickStrategy

    /**
     * On collision what happens next.
     * @param thisObj The given object
     * @param otherObj The other object
     * @param counter Counts the amounts of bricks that were hit.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter){
        if(this.gameObjects.removeGameObject(thisObj,Layer.STATIC_OBJECTS))
            counter.decrement();
    } //end of onCollision method

    /**
     * All the objects that are in the game
     * @return All the objects
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return this.gameObjects;
    } // end of method getGameObjectCollection
} // end of CollisionStrategy class


