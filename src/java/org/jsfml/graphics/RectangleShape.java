package org.jsfml.graphics;

import org.jsfml.NotNull;
import org.jsfml.system.Vector2f;

/**
 * A specialized shape representing a rectangle.
 */
public class RectangleShape extends Shape {
    /**
     * Constructs a new rectangle shape with no dimensions.
     */
    public RectangleShape() {
        super();
    }

    /**
     * Constructs a new rectangle shape with the specified dimensions.
     *
     * @param size the rectangle's dimensions.
     */
    public RectangleShape(Vector2f size) {
        this();
        setSize(size);
    }

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected native long nativeCreate();

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected native void nativeSetExPtr();

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected native void nativeDelete();

    private native void nativeSetSize(Vector2f size);

    /**
     * Sets the dimensions of the rectangle.
     *
     * @param size the new dimensions of the rectangle.
     */
    public void setSize(@NotNull Vector2f size) {
        if (size == null)
            throw new NullPointerException("size must not be null.");

        nativeSetSize(size);
    }

    /**
     * Gets the dimensions of the rectangle.
     *
     * @return the dimensions of the rectangle.
     */
    public native Vector2f getSize();
}
