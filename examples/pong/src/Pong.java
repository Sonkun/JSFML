import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;


public class Pong {
	
	private final static float pi = 3.1415f;
	private final static int gameWidth = 800;
	private final static int gameHeight = 600;
	private final static Vector2f paddleSize = new Vector2f(25, 100);
	private final static float ballRadius = 10;

	public static void main(String[] args) {
		
		Random numberGenerator = new Random();

		// Create the window of the application
        RenderWindow window = new RenderWindow(new VideoMode(gameWidth, gameHeight, 32), "JSFML Pong");
        window.setVerticalSyncEnabled(true);

        // Load the sounds used in the game
		SoundBuffer ballSoundBuffer = new SoundBuffer();
		try {
			ballSoundBuffer.loadFromFile(Paths.get("resources/ball.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Sound ballSound = new Sound(ballSoundBuffer);
		
		// Create the left paddle
		RectangleShape leftPaddle = new RectangleShape();
		leftPaddle.setSize(new Vector2f(paddleSize.x - 3, paddleSize.y - 3));
		leftPaddle.setOutlineThickness(3);
		leftPaddle.setOutlineColor(Color.BLACK);
		leftPaddle.setFillColor(new Color(100, 100, 200));
		leftPaddle.setOrigin(new Vector2f(paddleSize.x / 2, paddleSize.y / 2));

	    // Create the right paddle
		RectangleShape rightPaddle = new RectangleShape();
		rightPaddle.setSize(new Vector2f(paddleSize.x - 3, paddleSize.y - 3));
		rightPaddle.setOutlineThickness(3);
		rightPaddle.setOutlineColor(Color.BLACK);
		rightPaddle.setFillColor(new Color(200, 100, 200));
		rightPaddle.setOrigin(new Vector2f(paddleSize.x / 2, paddleSize.y / 2));
		
	    // Create the ball
		CircleShape ball = new CircleShape();
		ball.setRadius(ballRadius - 3);
	    ball.setOutlineThickness(3);
		ball.setOutlineColor(Color.BLACK);
		ball.setFillColor(Color.WHITE);
		ball.setOrigin(ballRadius / 2, ballRadius / 2);

	    // Load the text font
		Font font = new Font();
	    try {
			font.loadFromFile(Paths.get("resources/sansation.ttf"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	    // Initialize the pause message
	    Text pauseMessage = new Text();
	    pauseMessage.setFont(font);
	    pauseMessage.setCharacterSize(40);
	    pauseMessage.setPosition(170, 150);
	    pauseMessage.setColor(Color.WHITE);
	    pauseMessage.setString("Welcome to SFML pong!\nPress space to start the game");

	    // Define the paddles properties
	    Clock AITimer = new Clock();
	    Time AITime = Time.getSeconds(0.1f);
	    
	    float paddleSpeed = 400.f;
	    float rightPaddleSpeed  = 0.f;
	    float ballSpeed = 400.f;
	    float ballAngle = 0.f; // to be changed later

	    Clock clock = new Clock();
	    boolean isPlaying = false;
		
        while (window.isOpen()) {
        	
            // Handle events
        	for (Event event : window.pollEvents())
        	{
                // Window closed or escape key pressed: exit
        		if (event.type == Event.Type.CLOSED || (event.type == Event.Type.KEY_PRESSED && event.asKeyEvent().key == Keyboard.Key.ESCAPE)) {
        			window.close();
        			break;
        		}
        		
                // Space key pressed: play
                if ((event.type == Event.Type.KEY_PRESSED) && (event.asKeyEvent().key == Keyboard.Key.SPACE)) {
                	
                    if (!isPlaying) {
						// (re)start the game
						isPlaying = true;
						clock.restart();
						
						// Reset the position of the paddles and ball
						leftPaddle.setPosition(10 + paddleSize.x / 2, gameHeight / 2);
						rightPaddle.setPosition(gameWidth - 10 - paddleSize.x / 2, gameHeight / 2);
						ball.setPosition(gameWidth / 2, gameHeight / 2);
						
						// Reset the ball angle
						do
						{
						    // Make sure the ball initial angle is not too much vertical
						    ballAngle = (numberGenerator.nextInt(360)) * 2 * pi / 360;
						}
						while (Math.abs(Math.cos(ballAngle)) < 0.7f);						
						
                    }
                }
            }
        	
            if (isPlaying)
            {
                float deltaTime = clock.restart().asSeconds();

                // Move the player's paddle
                if (Keyboard.isKeyPressed(Keyboard.Key.UP) && (leftPaddle.getPosition().y - paddleSize.y / 2 > 5.f))
                {
                    leftPaddle.move(0.f, -paddleSpeed * deltaTime);
                }
                if (Keyboard.isKeyPressed(Keyboard.Key.DOWN) && (leftPaddle.getPosition().y + paddleSize.y / 2 < gameHeight - 5.f))
                {
                    leftPaddle.move(0.f, paddleSpeed * deltaTime);
                }

                // Move the computer's paddle
                if (((rightPaddleSpeed < 0.f) && (rightPaddle.getPosition().y - paddleSize.y / 2 > 5.f)) ||
                    ((rightPaddleSpeed > 0.f) && (rightPaddle.getPosition().y + paddleSize.y / 2 < gameHeight - 5.f)))
                {
                    rightPaddle.move(0.f, rightPaddleSpeed * deltaTime);
                }


                // Update the computer's paddle direction according to the ball position
                if (AITimer.getElapsedTime().asMicroseconds() > AITime.asMicroseconds())
                {
                    AITimer.restart();
                    if (ball.getPosition().y + ballRadius > rightPaddle.getPosition().y + paddleSize.y / 2)
                        rightPaddleSpeed = paddleSpeed;
                    else if (ball.getPosition().y - ballRadius < rightPaddle.getPosition().y - paddleSize.y / 2)
                        rightPaddleSpeed = -paddleSpeed;
                    else
                        rightPaddleSpeed = 0.f;
                }
                
                // Move the ball
                float factor = ballSpeed * deltaTime;
                ball.move((float)Math.cos(ballAngle) * factor, (float)Math.sin(ballAngle) * factor);

                // Check collisions between the ball and the screen
                if (ball.getPosition().x - ballRadius < 0.f)
                {
                    isPlaying = false;
                    pauseMessage.setString("You lost !\nPress space to restart or\nescape to exit");
                }
                if (ball.getPosition().x + ballRadius > gameWidth)
                {
                    isPlaying = false;
                    pauseMessage.setString("You won !\nPress space to restart or\nescape to exit");
                }
                if (ball.getPosition().y - ballRadius < 0.f)
                {
                    ballSound.play();
                    ballAngle = -ballAngle;
                    ball.setPosition(ball.getPosition().x, ballRadius + 0.1f);
                }
                if (ball.getPosition().y + ballRadius > gameHeight)
                {
                    ballSound.play();
                    ballAngle = -ballAngle;
                    ball.setPosition(ball.getPosition().x, gameHeight - ballRadius - 0.1f);
                }
                
                // Check the collisions between the ball and the paddles
                // Left Paddle
                if (ball.getPosition().x - ballRadius < leftPaddle.getPosition().x + paddleSize.x / 2 && 
                    ball.getPosition().x - ballRadius > leftPaddle.getPosition().x &&
                    ball.getPosition().y + ballRadius >= leftPaddle.getPosition().y - paddleSize.y / 2 &&
                    ball.getPosition().y - ballRadius <= leftPaddle.getPosition().y + paddleSize.y / 2)
                {
                    if (ball.getPosition().y > leftPaddle.getPosition().y)
                        ballAngle = pi - ballAngle + (numberGenerator.nextInt(20)) * pi / 180;
                    else
                        ballAngle = pi - ballAngle - (numberGenerator.nextInt(20)) * pi / 180;

                    ballSound.play();
                    ball.setPosition(leftPaddle.getPosition().x + ballRadius + paddleSize.x / 2 + 0.1f, ball.getPosition().y);
                }

                // Right Paddle
                if (ball.getPosition().x + ballRadius > rightPaddle.getPosition().x - paddleSize.x / 2 &&
                    ball.getPosition().x + ballRadius < rightPaddle.getPosition().x &&
                    ball.getPosition().y + ballRadius >= rightPaddle.getPosition().y - paddleSize.y / 2 &&
                    ball.getPosition().y - ballRadius <= rightPaddle.getPosition().y + paddleSize.y / 2)
                {
                    if (ball.getPosition().y > rightPaddle.getPosition().y)
                        ballAngle = pi - ballAngle + (numberGenerator.nextInt(20)) * pi / 180;
                    else
                        ballAngle = pi - ballAngle - (numberGenerator.nextInt(20)) * pi / 180;

                    ballSound.play();
                    ball.setPosition(rightPaddle.getPosition().x - ballRadius - paddleSize.x / 2 - 0.1f, ball.getPosition().y);
                }
            }
  	
	        // Clear the window
	        window.clear(new Color(50, 200, 50));

	        if (isPlaying)
	        {
	            // Draw the paddles and the ball
	            window.draw(leftPaddle);
	            window.draw(rightPaddle);
	            window.draw(ball);
	        }
	        else
	        {
	            // Draw the pause message
	            window.draw(pauseMessage);
	        }

	        // Display things on screen
	        window.display();
	        
        	}
    }
}