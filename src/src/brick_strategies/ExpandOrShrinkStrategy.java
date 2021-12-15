package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.Brick;
import src.gameobjects.ExpandOrShrink;

import java.util.Random;

public class ExpandOrShrinkStrategy extends RemoveBrickStrategyDecorator{

    private static final float SPEED = 150;
    private final ImageRenderable narrowImage;
    private final ImageRenderable widenImage;
    private Vector2 windowDimensions;
    private CollisionStrategy toBeDecorated;
    private ImageReader imageReader;

    /**
     * Strategy that when is chosen decides randomly between an expand or shrink objects.
     * @param toBeDecorated The Collision Strategy chosen
     * @param imageReader The image reader
     * @param windowDimensions The dimensions of the window
     */
        public ExpandOrShrinkStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader, Vector2 windowDimensions){
            super(toBeDecorated);
            this.toBeDecorated = toBeDecorated;
            this.imageReader = imageReader;
            this.narrowImage = imageReader.readImage("assets/buffNarrow.png", true);
            this.widenImage = imageReader.readImage("assets/buffWiden.png", true);
            this.windowDimensions = windowDimensions;
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
            Random rand = new Random();
            ImageRenderable [] image = {this.narrowImage, this.widenImage};
            float[] size = {0.5f ,2};
            int i = rand.nextInt(2);
            GameObject expandShrink = new ExpandOrShrink(Vector2.ZERO, new Vector2(25, 20), image[i], getGameObjectCollection(), size[i], this.windowDimensions);
            if(thisObj instanceof Brick && otherObj instanceof Ball) {
                expandShrink.setCenter(thisObj.getCenter());
                float VelY = SPEED;
                expandShrink.setVelocity(new Vector2(0, VelY));
                getGameObjectCollection().addGameObject(expandShrink);
            } // end of if
        } //end of override method onCollision
    } //end of class

