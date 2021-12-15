package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public abstract class RemoveBrickStrategyDecorator implements CollisionStrategy {

    private final CollisionStrategy toBeDecorated;

    /**
     * Constructs RemoveBrickStrategyDecorator game object.
     * @param toBeDecorated Collision strategy object to be decorated.
     */
    public RemoveBrickStrategyDecorator(CollisionStrategy toBeDecorated){
        this.toBeDecorated = toBeDecorated;
    } //end of constructor

    /**
     * The action that happens when two objects collide.
     * @param thisObj The given object.
     * @param otherObj The other object.
     * @param counter Counts how many bricks have been hit.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        toBeDecorated.onCollision(thisObj, otherObj, counter);
    } // end of method onCollision

    /**
     * return held reference to global game object. Delegate to held object to be decorated
     * @return held reference to global game object. Delegate to held object to be decorated
     */
    public GameObjectCollection getGameObjectCollection(){
        return this.toBeDecorated.getGameObjectCollection();
    } //end of method getGameObjectCollection
} // end of RemoveBrickStrategyDecorator class
