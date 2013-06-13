import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;


public class Shader {

	public static void main(String[] args) {
	    // Create the main window
	    RenderWindow window = new RenderWindow(new VideoMode(800, 600), "JSFML Shader");
	    window.setVerticalSyncEnabled(true);

	    // Load the application font and pass it to the Effect class
	    Font font = new Font();
	    try {
			font.loadFromFile(Paths.get("resources/sansation.ttf"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	 
	    Effect.font = font;
	    
	    // Create the effects
	    ArrayList<Effect> effects = new ArrayList<Effect>();
	    effects.add(new Pixelate());
	    effects.add(new WaveBlur());
	    effects.add(new StormBlink());
	    effects.add(new Edge());
	    int current = 0;

	    // Initialize them
	    for (Effect effect : effects)
	        effect.load();
	        
	    // Create the messages background
	    Texture textBackgroundTexture = new Texture();
	    try {
			textBackgroundTexture.loadFromFile(Paths.get("resources/text-background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	    Sprite textBackground = new Sprite(textBackgroundTexture);
	    textBackground.setPosition(0, 520);
	    textBackground.setColor(new Color(255, 255, 255, 200));

	    // Create the description text
	    Text description = new Text("Current effect: " + effects.get(current).getName(), font, 20);
	    description.setPosition(10, 530);
	    description.setColor(new Color(80, 80, 80));

	    // Create the instructions text
	    Text instructions = new Text("Press left and right arrows to change the current shader", font, 20);
	    instructions.setPosition(280, 555);
	    instructions.setColor(new Color(80, 80, 80));
	    
	    // Start the game loop
	    Clock clock = new Clock();
	    while (window.isOpen())
	    {
            // Process events
        	for (Event event : window.pollEvents())
        	{
                // Close window: exit
                if (event.type == Event.Type.CLOSED)
                    window.close();
                
                if (event.type == Event.Type.KEY_PRESSED)
                {
                    switch (event.asKeyEvent().key)
                    {
                    // Escape key: exit
                    case ESCAPE:
                        window.close();
                        break;

                    // Left arrow key: previous shader
                    case LEFT:
                        if (current == 0)
                            current = effects.size() - 1;
                        else
                            current--;
                        description.setString("Current effect: " + effects.get(current).getName());
                        break;

                    // Right arrow key: next shader
                    case RIGHT:
                        if (current == effects.size() - 1)
                            current = 0;
                        else
                            current++;
                        description.setString("Current effect: " + effects.get(current).getName());
                        break;

                    default:
                        break;
                    }
                }
        	}
        	
            // Update the current example
        	float x = Mouse.getPosition(window).x / window.getSize().x;
        	float y = Mouse.getPosition(window).y / window.getSize().y;
            effects.get(current).update(clock.getElapsedTime().asSeconds(), x, y);

            // Clear the window
            window.clear(new Color(255, 128, 0));

            // Draw the current example
            window.draw(effects.get(current));

            // Draw the text
            window.draw(textBackground);
            window.draw(instructions);
            window.draw(description);

            // Finally, display the rendered frame on screen
            window.display();
        	
	    }
	}
}
