package src.gameobjects;

import danogl.GameObject;
import danogl.util.Vector2;
import src.brick_strategies.ChangeCameraStrategy;

public class BallCollisionCountdownAgent extends GameObject {
    private final int numCurrentCollision;
    private Ball ball;
    private ChangeCameraStrategy owner;
    private int countDownValue;

    /**
     * Construct a new GameObject instance.
     *
     * @param ball The ball object used.
     * @param owner Reference to the change camera strategy.
     * @param countDownValue Counts down the hits.
     */
    public BallCollisionCountdownAgent(Ball ball, ChangeCameraStrategy owner, int countDownValue) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.numCurrentCollision = ball.getCollisionCount();
        this.owner = owner;
        this.ball = ball;
        this.countDownValue = countDownValue;
    } // end of constructor

    /**
     *  Overrides update method
     * @param deltaTime The current time
     */
    public void update(float deltaTime){
        super.update(deltaTime);
        if(this.ball.getCollisionCount() - this.numCurrentCollision > this.countDownValue)
            this.owner.turnOffCameraChange();
    }//end of update method

    /**
     * @param other The other object
     * @return True if other is instance of paddle
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return (other instanceof Ball && !(other instanceof Puck));
    } //end of shouldCollideWith
} // end of BallCollisionCountDownAgent class
