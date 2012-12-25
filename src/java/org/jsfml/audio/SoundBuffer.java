package org.jsfml.audio;


import org.jsfml.NotNull;
import org.jsfml.SFMLNativeObject;
import org.jsfml.StreamUtil;
import org.jsfml.UnsafeOperations;
import org.jsfml.system.Time;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Buffer of audio samples, providing an audio data source for a {@code Sound}.
 */
public class SoundBuffer extends SFMLNativeObject implements ConstSoundBuffer {
    /**
     * Constructs a sound buffer.
     */
    public SoundBuffer() {
        super();
    }

    @SuppressWarnings("deprecation")
    SoundBuffer(long wrap) {
        super(wrap);
    }

    /**
     * Constructs a sound buffer by copying another sound buffer.
     *
     * @param other the sound buffer to copy.
     */
    @SuppressWarnings("deprecation")
    public SoundBuffer(ConstSoundBuffer other) {
        super(((SoundBuffer) other).nativeCopy());
        UnsafeOperations.manageSFMLObject(this, true);
    }

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected native long nativeCreate();

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected void nativeSetExPtr() {
    }

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected native void nativeDelete();

    private native long nativeCopy();

    private native boolean nativeLoadFromMemory(byte[] memory);

    /**
     * Fully loads all available bytes from the specified {@link java.io.InputStream}
     * and attempts to load the sound buffer from it.
     *
     * @param in the input stream to read from.
     * @throws java.io.IOException in case an I/O error occurs.
     */
    public void loadFromStream(InputStream in) throws IOException {
        if (!nativeLoadFromMemory(StreamUtil.readStream(in)))
            throw new IOException("Failed to load sound buffer from input stream.");
    }

    /**
     * Attempts to load the sound buffer from a file.
     *
     * @param file the file to load the sound buffer from.
     * @throws IOException in case an I/O error occurs.
     */
    public void loadFromFile(File file) throws IOException {
        if (!nativeLoadFromMemory(StreamUtil.readFile(file)))
            throw new IOException("Failed to load sound buffer from file: " + file);
    }

    private native boolean nativeLoadFromSamples(short[] samples, int channelCount, int sampleRate);

    /**
     * Attempts to load the sound buffer from an array of raw 16-bit audio samples.
     *
     * @param samples      the samples data.
     * @param channelCount the amount of audio channels.
     * @param sampleRate   the sample rate in samples per second.
     * @throws java.io.IOException in case an I/O error occurs.
     */
    public void loadFromSamples(@NotNull short[] samples, int channelCount, int sampleRate)
            throws IOException {
        if (samples == null)
            throw new NullPointerException("samples must not be null");

        if (!nativeLoadFromSamples(samples, channelCount, sampleRate))
            throw new IOException("Failed to load sound buffer from samples.");
    }

    private native boolean nativeSaveToFile(String fileName);

    @Override
    public void saveToFile(@NotNull File file) throws IOException {
        if (file == null)
            throw new NullPointerException("file must not be null");

        if (!nativeSaveToFile(file.getAbsolutePath()))
            throw new IOException("Failed to save sound buffer to file: " + file);
    }

    @Override
    public native short[] getSamples();

    @Override
    public native int getSampleCount();

    @Override
    public native int getSampleRate();

    @Override
    public native int getChannelCount();

    @Override
    public native Time getDuration();
}
