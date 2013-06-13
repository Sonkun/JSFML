import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.ShaderSourceException;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.Shader;
import org.jsfml.graphics.TextureCreationException;
import org.jsfml.system.Vector2f;


////////////////////////////////////////////////////////////
//"Edge" post-effect fragment shader
////////////////////////////////////////////////////////////
public class Edge extends Effect {
	
	protected Edge() {
		super("edge post-effect");
	}

	@Override
	protected boolean onLoad() {
		// Create the off-screen surface
		try {
			surface.create(800, 600);
		} catch (TextureCreationException e) {
			e.printStackTrace();
		}
		surface.setSmooth(true);

		// Load the textures
		try {
			backgroundTexture.loadFromFile(Paths.get("resources/sfml.png"));
			entityTexture.loadFromFile(Paths.get("resources/devices.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		backgroundTexture.setSmooth(true);
		entityTexture.setSmooth(true);

		// Initialize the background sprite
		backgroundSprite.setTexture(backgroundTexture);
		backgroundSprite.setPosition(135, 100);

		// Load the moving entities
		for (int i = 0; i < 6; i++)
		{
			Sprite entity = new Sprite(entityTexture, new IntRect(96 * i, 0, 96, 96));
			entities.add(entity);
		}

		// Load the shader
		try {
			shader.loadFromFile(Paths.get("resources/edge.frag"), Shader.Type.FRAGMENT);
		} catch (IOException | ShaderSourceException e) {
			e.printStackTrace();
			return false;
		}
		shader.setParameter("texture", Shader.CURRENT_TEXTURE);

		return true;
	}

	@Override
	protected void onUpdate(float time, float x, float y) {
		shader.setParameter("edge_threshold", 1 - (x + y) / 2);

		// Update the position of the moving entities

		for (int i = 0; i < entities.size(); i++)
		{
			float _x = (float)Math.cos(0.25f * (time * i + (entities.size() - i))) * 300 + 350;
			float _y = (float)Math.sin(0.25f * (time * (entities.size() - i) + i)) * 200 + 250;
			entities.get(i).setPosition(_x, _y);
		}

		// Render the updated scene to the off-screen surface
		surface.clear(Color.WHITE);
		surface.draw(backgroundSprite);
		for (int i = 0; i < entities.size(); i++)
		{
			surface.draw(entities.get(i));
		}
		
		surface.display();		
	}

	@Override
	protected void onDraw(RenderTarget target, RenderStates states) {
		states.shader = shader;
		target.draw(new Sprite(surface.getTexture()), states);
	}

	private RenderTexture surface;
	private Texture       backgroundTexture;
	private Texture       entityTexture;
	private Sprite        backgroundSprite;
	ArrayList<Sprite>     entities;
	Shader                shader;
}
