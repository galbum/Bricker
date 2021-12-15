package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;


public class GraphicLifeCounter extends GameObject {

    private static final int SPACE_BETWEEN_WIDGETS = 40;
    private final Counter livesCounter;
    private final GameObjectCollection gameObjectCollection;
    private int currentLives;
    private GameObject[] livesLeft;
    private int xCoordinate = 25;
    private int yCoordinate = 20;

    /**
     * Construct a new GrahicLifeCounter instance.
     * @param widgetTopLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param widgetDimensions    Width and height of the widget that will be added.
     * @param livesCounter    The amount of lives left for the player in the game.
     * @param widgetRenderable The widget that we will render.
     * @param gameObjectsCollection All the objects related to this game.
     * @param numOfLives - The number of lives in the game.
     */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner, Vector2 widgetDimensions, Counter livesCounter,
                              Renderable widgetRenderable, GameObjectCollection gameObjectsCollection,
                              int numOfLives){
        super(widgetTopLeftCorner, widgetDimensions, null);
        this.livesCounter = livesCounter;
        this.currentLives = numOfLives;
        this.gameObjectCollection = gameObjectsCollection;
        this.livesLeft = new GameObject[numOfLives];
        for (int i = 0; i < numOfLives; i++) {
            livesLeft[i] = new GameObject(Vector2.ZERO, widgetDimensions, widgetRenderable);
            livesLeft[i].setCenter(new Vector2( xCoordinate,yCoordinate ));
            xCoordinate = xCoordinate + SPACE_BETWEEN_WIDGETS;
            gameObjectsCollection.addGameObject(this.livesLeft[i], Layer.BACKGROUND);
        } //end of for loop
    }//end of constructor

    /**
     * overrides the update method.
     * @param deltaTime Time of the current game.
     */
    public void update(float deltaTime){
        super.update(deltaTime);
        if(this.livesCounter.value() < this.currentLives){
            this.currentLives --;
            gameObjectCollection.removeGameObject(this.livesLeft[currentLives], Layer.BACKGROUND);
        } // end of if
    } // end of update method
} //end of class

