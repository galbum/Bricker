package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

public class NumericLifeCounter extends GameObject {

    private static final Object SENTENCE = "  lives left  ";
    private Counter livesCounter;
    private int currLives;
    private TextRenderable textRenderable;

    /**
     * Construct a new NumericLifeCounter instance.
     * @param livesCounter - The amount of lives left for the player in the game.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height of the widget that will be added.
     * @param gameObjectCollection All the objects related to this game.

     */
    public NumericLifeCounter(Counter livesCounter, Vector2 topLeftCorner, Vector2 dimensions,
                            GameObjectCollection gameObjectCollection){
    super(topLeftCorner, dimensions, null);
    this.livesCounter = livesCounter;
    this.currLives = livesCounter.value();
    this.textRenderable = new TextRenderable(Integer.toString(this.livesCounter.value()) + SENTENCE,
            Font.SANS_SERIF, false, true);
    textRenderable.setColor(Color.WHITE);
    GameObject text = new GameObject(new Vector2(dimensions.x()-150,10),new Vector2 (dimensions.x(), 20),textRenderable);
    gameObjectCollection.addGameObject(text, Layer.BACKGROUND);
}

    /**
     * overrides the update method.
     * @param deltaTime Time of the current game.
     */
public void update(float deltaTime){
    if(this.livesCounter.value() != this.currLives){
        this.textRenderable.setString(Integer.toString(this.livesCounter.value()) + SENTENCE);
        this.currLives = this.livesCounter.value();
        } // end of if
        super.update(deltaTime);
    } //end of update method

}// end of NumericLifCounter class
