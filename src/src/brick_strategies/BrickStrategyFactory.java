package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import src.BrickerGameManager;

import java.util.Random;

public class BrickStrategyFactory {

    private final int NUM_OF_EFFECTS = 5;
    private final int NUM_OF_SPECIAL_EFFECTS = 4;
    private final GameObjectCollection gameObjects;
    private final Random rand;
    private int counter;
    private BrickerGameManager gameManager;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private WindowController windowController;
    private Vector2 windowDimensions;
    private RemoveBrickStrategy toBeDecorated;

    /**
     * constructs BrickStrategy factory that will decide which collision will be chosed.
     * @param gameObjects
     */
    public BrickStrategyFactory(GameObjectCollection gameObjects, BrickerGameManager gameManager,
                                ImageReader imageReader, SoundReader soundReader,
                                UserInputListener inputListener, WindowController windowController,
                                Vector2 windowDimensions) {
        this.gameObjects = gameObjects;
        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;
        this.toBeDecorated = new RemoveBrickStrategy(gameObjects);
        this.counter = 0;
        this.rand = new Random();
    } //end of constructor

    /**
     * chooses which strategy will be implemented.
     * @return The strategy that will be used.
     */
    public CollisionStrategy getStrategy(){
        //  choose randomly between the possible brick strategies
        this.counter = 0;
        int randNum = rand.nextInt(NUM_OF_EFFECTS + 1 ) + 1; // we want to start from 1 until 6
        switch (randNum) {
            case 1:
                return this.toBeDecorated;
            default:
                return Strategy(randNum, this.toBeDecorated);
        }//end of switch
    } // end of method getStrategy


    private CollisionStrategy Strategy(int randNum, CollisionStrategy toDecorate) {
        switch (randNum) {
            case 2:
                return new AddPaddleStrategy(toDecorate, this.imageReader, this.inputListener, this.windowDimensions);
            case 3:
                return new PuckStrategy(toDecorate, this.imageReader, this.soundReader);
            case 4:
                return new ChangeCameraStrategy(toDecorate, this.windowController, this.gameManager);
            case 5:
                return new ExpandOrShrinkStrategy(toDecorate, this.imageReader, this.windowDimensions);
            case 6:
                return Double();
            default:
                return null;
        } // end of switch
    } // end of createDoubleStrategy method

        private CollisionStrategy Double() {
            if (this.counter < 2) {
                this.counter++;
                CollisionStrategy strategy = Strategy(this.rand.nextInt(NUM_OF_EFFECTS)+2, this.toBeDecorated);
                return Strategy(this.rand.nextInt(NUM_OF_SPECIAL_EFFECTS)+2, strategy);
        } // end of if
        else{
                System.out.println("3!");
            return Strategy(rand.nextInt(NUM_OF_SPECIAL_EFFECTS)+2, this.toBeDecorated);
        } //end of else
        }//end of createTripleStrategy method
    } // end of BrickStrategyFactory class