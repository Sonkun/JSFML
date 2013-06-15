import java.io.IOException;
import java.nio.file.Paths;

import sfml.graphics.RenderStates;
import sfml.graphics.RenderTarget;
import sfml.graphics.ShaderSourceException;
import sfml.graphics.Text;
import sfml.graphics.Shader;


////////////////////////////////////////////////////////////
//"Wave" vertex shader + "blur" fragment shader
////////////////////////////////////////////////////////////
public class WaveBlur extends Effect {

	protected WaveBlur() {
		super("wave + blur");
	}

	@Override
	protected boolean onLoad() {
		// Create the text
		text.setString("Praesent suscipit augue in velit pulvinar hendrerit varius purus aliquam.\n" +
		"Mauris mi odio, bibendum quis fringilla a, laoreet vel orci. Proin vitae vulputate tortor.\n" +
		"Praesent cursus ultrices justo, ut feugiat ante vehicula quis.\n" +
		"Donec fringilla scelerisque mauris et viverra.\n" +
		"Maecenas adipiscing ornare scelerisque. Nullam at libero elit.\n" +
		"Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.\n" +
		"Nullam leo urna, tincidunt id semper eget, ultricies sed mi.\n" +
		"Morbi mauris massa, commodo id dignissim vel, lobortis et elit.\n" +
		"Fusce vel libero sed neque scelerisque venenatis.\n" +
		"Integer mattis tincidunt quam vitae iaculis.\n" +
		"Vivamus fringilla sem non velit venenatis fermentum.\n" +
		"Vivamus varius tincidunt nisi id vehicula.\n" +
		"Integer ullamcorper, enim vitae euismod rutrum, massa nisl semper ipsum,\n" +
		"vestibulum sodales sem ante in massa.\n" +
		"Vestibulum in augue non felis convallis viverra.\n" +
		"Mauris ultricies dolor sed massa convallis sed aliquet augue fringilla.\n" +
		"Duis erat eros, porta in accumsan in, blandit quis sem.\n" +
		"In hac habitasse platea dictumst. Etiam fringilla est id odio dapibus sit amet semper dui laoreet.\n");
		text.setFont(this.font);
		text.setCharacterSize(22);
		text.setPosition(30, 20);

		// Load the shader
		shader = new Shader();
		try {
			shader.loadFromFile(Paths.get("resources/wave.vert"), Paths.get("resources/blur.frag"));
		} catch (IOException | ShaderSourceException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	protected void onUpdate(float time, float x, float y) {
		shader.setParameter("wave_phase", time);
		shader.setParameter("wave_amplitude", x * 40, y * 40);
		shader.setParameter("blur_radius", (x + y) * 0.008f);
	}

	@Override
	protected void onDraw(RenderTarget target, RenderStates states) {
		states.shader = shader;
		target.draw(text, states);
	}

	private Text   text;
	private Shader shader;
}

//
//void onUpdate(float time, float x, float y)
//{
//m_shader.setParameter("wave_phase", time);
//m_shader.setParameter("wave_amplitude", x * 40, y * 40);
//m_shader.setParameter("blur_radius", (x + y) * 0.008f);
//}
