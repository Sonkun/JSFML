package sfml.graphics;

import sfml.internal.Const;

/**
 * Interface for read-only textures.
 * <p/>
 * It provides methods to can gain information from a shader, but none to modify it
 * in any way.
 * <p/>
 * Note that this interface is expected to be implemented by a {@link Shader}.
 * It is not recommended to be implemented outside of the JSFML API.
 *
 * @see sfml.internal.Const
 */
public interface ConstShader extends Const {
}
