import sfml.graphics.Drawable;
import sfml.graphics.Font;
import sfml.graphics.RenderStates;
import sfml.graphics.RenderTarget;
import sfml.graphics.Shader;
import sfml.graphics.Text;


//////////////////////////////////////////////////////////////
//// Base class for effects
//////////////////////////////////////////////////////////////
public abstract class Effect implements Drawable {

	public static Font font;

	String getName() {
		return name;
	}

	void load() {
		isLoaded = Shader.isAvailable() && onLoad();
	}

	void update(float time, float x, float y)
	{
		if (isLoaded)
			onUpdate(time, x, y);
	}

	@Override
	public void draw(RenderTarget target, RenderStates states) {
		if (isLoaded)
		{
			onDraw(target, states);
		}
		else
		{
			Text error = new Text("Shader not\nsupported", font);
			error.setPosition(320.f, 200.f);
			error.setCharacterSize(36);
			target.draw(error, states);
		}
	}

	protected Effect(String name) {
		this.name = name;
		this.isLoaded = false;
	}

	// Abstract functions to be implemented in derived effects
	protected abstract boolean onLoad();
	protected abstract void    onUpdate(float time, float x, float y);
	protected abstract void    onDraw(RenderTarget target, RenderStates states);

	private String  name;
	private boolean isLoaded;
}
