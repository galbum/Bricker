package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public interface CollisionStrategy {
    /**
     * An abstract method that chooses which action will be chosen on collision between two objects.
     * @param thisObj The given object.
     * @param otherObj The other object.
     * @param counter Counts how many bricks have been hit.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) ;

    /**
     * All collision strategy objects should hold a reference to the global game object collection and be able to return it.
     * @return global game object collection whose reference is held in object.
     */
    public GameObjectCollection getGameObjectCollection();

} // end of CollisionStrategy class
