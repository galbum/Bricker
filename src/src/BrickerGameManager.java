package src;

import src.brick_strategies.BrickStrategyFactory;
import src.brick_strategies.CollisionStrategy;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.*;

import java.awt.*;
import java.util.Random;

public class BrickerGameManager extends GameManager {

    private static final int BORDER_WIDTH = 3;
    private static final int BRICKS_AMOUNT_OF_COL = 8;
    private static final int BRICKS_AMOUNT_OF_ROWS = 5;
    private static final int INIT_X_COORDINATE = 50;
    private static final int INIT_Y_COORDINATE = 45;
    private static final int BRICK_HEIGHT = 15;
    private static final int PADDLE_HEIGHT = 15;
    private static final int PADDLE_WIDTH = 100;
    private static final int BALL_RADIUS = 20;
    private static final float BALL_SPEED = 150;
    private static final Renderable BORDER_RENDERABLE =
            new RectangleRenderable(new Color(232, 255, 250));
    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = 10;
    private Counter livesCounter = new Counter(4);
    private static int GAME_AMOUNT_OF_LIVES = 4;
    private Counter brickCounter = new Counter();
    private Vector2 windowDimensions;
    private Renderable ballImage;
    private Renderable paddleImage;
    private ImageRenderable brickImage;
    private Ball ball;
    private WindowController windowController;
    private ImageRenderable heartImage;
    private BrickStrategyFactory brickStrategyFactory;

    /**
     * Construct a new BrickerGameManager game.
     * @param windowTitle The title of the game.
     * @param windowDimensions    Width and height of the window of the game.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    } // end of BrickerGameManager constructor

    /**
     * Overrides the initializeGame..
     * @param imageReader - an ImageReader instance for reading images from files for rendering of objects.
     * @param soundReader - a SoundReader instance for reading soundclips from files for rendering event sounds.
     * @param inputListener - an InputListener instance for reading user input.
     * @param windowController - controls visual rendering of the game window and object renderables.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        this.windowController = windowController;
        //initialization
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowDimensions = windowController.getWindowDimensions();  // gets windows dimension

        //create ball
        createBall(imageReader, soundReader, windowDimensions);
        //create user paddles
        createPaddle(imageReader, inputListener, windowDimensions);
        //create collision factory
        brickStrategyFactory = new BrickStrategyFactory(gameObjects(), this, imageReader,
                soundReader,inputListener, windowController,windowDimensions);
        //create bricks
        createBricks(imageReader,windowDimensions,brickStrategyFactory);
        //create borders
        createBorders(windowDimensions);

        //create background
        GameObject background = new GameObject(Vector2.ZERO, windowController.getWindowDimensions(), imageReader.readImage("assets/DARK_BG2_small.jpeg",true ));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);

        //create heart
        heartImage = imageReader.readImage("assets/heart.png",true);
         new GraphicLifeCounter( Vector2.ZERO, new Vector2(25,20), livesCounter, heartImage,gameObjects(),GAME_AMOUNT_OF_LIVES);
         new NumericLifeCounter(livesCounter, Vector2.ZERO, this.windowDimensions, gameObjects());
        // slow game speed
       // windowController.setTimeScale((float) 0.1);

    } // end of override method initializeGame


    /**
     * Overrides the update method.
     * @param deltaTime The time of the game.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
    } // end of method update


    /**
     * Private methods.
     */
    private void checkForGameEnd() {
        float ballHeight = this.ball.getCenter().y();
        String prompt = "";
        if(ballHeight > this.windowDimensions.y()) {   //we lose
            livesCounter.decrement();
            if(livesCounter.value() == 0)
                prompt = "You Lose!";
            this.windowController.resetGame();
            GAME_AMOUNT_OF_LIVES --;
        } // end of if(ballHeight > this.windowDimensions.y()
        if (brickCounter.value()==0) {
            prompt="You Win!";
        }
        if(!prompt.isEmpty()) {
            prompt += " Play again?";
            if(this.windowController.openYesNoDialog(prompt)) {
                this.windowController.resetGame();
            }
            else
                this.windowController.closeWindow();
        } //end of if !prompt.isEmpty()
    } //end of private method checkForGameEnd

    private void createBricks(ImageReader imageReader,Vector2 windowDimensions, BrickStrategyFactory brickStrategyFactory){
        brickImage = imageReader.readImage("assets/brick.png", true);
        GameObject[][] bricks =new GameObject[BRICKS_AMOUNT_OF_ROWS][BRICKS_AMOUNT_OF_COL];
        int xCoordinatePlacement = INIT_X_COORDINATE,  yCoordinatePlacement = INIT_Y_COORDINATE;
        // add the bricks
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                CollisionStrategy collisionStrategy = brickStrategyFactory.getStrategy();
                bricks[i][j] = new Brick(Vector2.ZERO, new Vector2(windowDimensions.x()/10,BRICK_HEIGHT), brickImage, collisionStrategy, brickCounter);
                bricks[i][j].setCenter(new Vector2( xCoordinatePlacement, yCoordinatePlacement));
                brickCounter.increment();
                xCoordinatePlacement +=  windowDimensions.x()/10 + 15;
                gameObjects().addGameObject(bricks[i][j], Layer.STATIC_OBJECTS);
            } //end of inner for loop
            xCoordinatePlacement = 50;
            yCoordinatePlacement += 40;
        } //end of outer for loop
    } //end of private method createBricks

    private void createPaddle(ImageReader imageReader, UserInputListener inputListener, Vector2 windowDimensions){
        paddleImage = imageReader.readImage("assets/paddle.png", true);
        GameObject Paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH ,PADDLE_HEIGHT ), paddleImage, inputListener, windowDimensions,  MIN_DISTANCE_FROM_SCREEN_EDGE);
        Paddle.setCenter(new Vector2(windowDimensions.x()/2 , (int)windowDimensions.y()-30));
        gameObjects().addGameObject(Paddle);
    } //end of createUserPaddle method

    private void createBall(ImageReader imageReader, SoundReader soundReader, Vector2 windowDimensions){
        ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS,BALL_RADIUS),ballImage, collisionSound);
        float ballVelY = BALL_SPEED, ballVelX = BALL_SPEED;
        Random rand = new Random();
        if(rand.nextBoolean())
            ballVelX *= -1;
        if(rand.nextBoolean())
            ballVelY *= -1;
        ball.setVelocity(new Vector2(ballVelX, ballVelY)); // change the ball's speed
        ball.setCenter(windowDimensions.mult(0.5f)); // set ball to the middle of the screen
        gameObjects().addGameObject(ball); // add object ball to the gameObjects
    }//end of createBall method

   private void createBorders(Vector2 windowDimensions){
             // left border
            gameObjects().addGameObject(
                    new GameObject(
                            Vector2.ZERO,
                            new Vector2(BORDER_WIDTH, windowDimensions.y()),
                            BORDER_RENDERABLE) );
            // right border
            gameObjects().addGameObject(
                    new GameObject(
                            new Vector2(windowDimensions.x() - BORDER_WIDTH, 0),
                            new Vector2(BORDER_WIDTH, windowDimensions.y()),
                            BORDER_RENDERABLE) );
            // top border
            gameObjects().addGameObject(
                    new GameObject(
                            Vector2.ZERO,
                            new Vector2( windowDimensions.x(), BORDER_WIDTH),
                            BORDER_RENDERABLE)  );
        }// end of private method createBorders

    private void createPaddleStrategy(ImageReader imageReader, SoundReader soundReader, Vector2 windowDimensions){

    }//end of createBall method

    /**
     * The main method that runs the game.
     */
    public static void main(String[] args) {
        BrickerGameManager game =  new BrickerGameManager("Bouncing Ball",  new Vector2(700, 500));
        game.run();
    } // end of main
} //end of class BrickerGameManager
