import java.io.IOException;
import java.nio.file.Paths;

import sfml.graphics.RenderStates;
import sfml.graphics.RenderTarget;
import sfml.graphics.Sprite;
import sfml.graphics.Texture;
import sfml.graphics.Shader;


////////////////////////////////////////////////////////////
//"Pixelate" fragment shader
////////////////////////////////////////////////////////////
public class Pixelate extends Effect {

	protected Pixelate() {
		super("pixelate");
	}

	@Override
	protected boolean onLoad() {
		// Load the texture and initialize the sprite
		texture = new Texture();
		try {
			texture.loadFromFile(Paths.get("resources/background.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		sprite = new Sprite(texture);

		// Load the shader
		shader = new Shader();
		shader.loadFromFile(Paths.get("resources/pixelate.frag", Shader.Type.FRAGMENT));
		shader.setParameter("texture", Shader.CURRENT_TEXTURE);

		return true;
	}

	@Override
	protected void onUpdate(float time, float x, float y) {
		shader.setParameter("pixel_threshold", (x + y / 30));
	}

	@Override
	protected void onDraw(RenderTarget target, RenderStates states) {
		states.shader = shader;
		target.draw(sprite, states);
	}

	private Texture texture;
	private Sprite  sprite;
	private Shader  shader;
}

