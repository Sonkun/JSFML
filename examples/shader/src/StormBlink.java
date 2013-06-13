import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.ShaderSourceException;
import org.jsfml.graphics.Vertex;
import org.jsfml.graphics.VertexArray;
import org.jsfml.graphics.Shader;
import org.jsfml.system.Vector2f;


////////////////////////////////////////////////////////////
// "Storm" vertex shader + "blink" fragment shader
////////////////////////////////////////////////////////////
public class StormBlink extends Effect {
	
	protected StormBlink() {
		super("storm + blink");
	}

	@Override
	protected boolean onLoad() {
        // Create the points
		Random numberGenerator = new Random();
		
        points.setPrimitiveType(PrimitiveType.POINTS);
        for (int i = 0; i < 40000; ++i)
        {
            float x = numberGenerator.nextInt(800);
            float y = numberGenerator.nextInt(600);
            int r = numberGenerator.nextInt(255);
            int g = numberGenerator.nextInt(255);
            int b = numberGenerator.nextInt(255);
            points.add(new Vertex(new Vector2f(x, y), new Color(r, g, b)));
        }

        // Load the shader
        shader = new Shader();
        try {
			shader.loadFromFile(Paths.get("resources/storm.vert"), Paths.get("resources/blink.frag"));
		} catch (IOException | ShaderSourceException e) {
			e.printStackTrace();
			return true;
		}
        
        return true;
	}

	@Override
	protected void onUpdate(float time, float x, float y) {
        float radius = 200 + (float)Math.cos(time) * 150;
        shader.setParameter("storm_position", x * 800, y * 600);
        shader.setParameter("storm_inner_radius", radius / 3);
        shader.setParameter("storm_total_radius", radius);
        shader.setParameter("blink_alpha", 0.5f + (float)Math.cos(time * 3) * 0.25f);
	}

	@Override
	protected void onDraw(RenderTarget target, RenderStates states) {
		states.shader = shader;
		target.draw(points, states);
	}
	
	private VertexArray points;
	private Shader      shader;
}