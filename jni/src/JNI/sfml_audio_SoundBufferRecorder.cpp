#include <JSFML/JNI/sfml_audio_SoundBufferRecorder.h>

#include <JSFML/Intercom/NativeObject.hpp>
#include <JSFML/JNI/sfml_internal_ExPtr.h>
#include <SFML/Audio/SoundBufferRecorder.hpp>

/*
 * Class:     sfml_audio_SoundBufferRecorder
 * Method:    nativeCreate
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_sfml_audio_SoundBufferRecorder_nativeCreate (JNIEnv *env, jobject obj) {
    return (jlong)new sf::SoundBufferRecorder();
}


/*
 * Class:     sfml_audio_SoundBufferRecorder
 * Method:    nativeSetExPtr
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_sfml_audio_SoundBufferRecorder_nativeSetExPtr (JNIEnv *env, jobject obj) {
    JSFML::NativeObject::SetExPointer(env, obj, sfml_internal_ExPtr_SOUND_RECORDER,
        dynamic_cast<sf::SoundRecorder*>(THIS(sf::SoundBufferRecorder)));
}

/*
 * Class:     sfml_audio_SoundBufferRecorder
 * Method:    nativeDelete
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_sfml_audio_SoundBufferRecorder_nativeDelete (JNIEnv *env, jobject obj) {
    delete THIS(sf::SoundBufferRecorder);
}

/*
 * Class:     sfml_audio_SoundBufferRecorder
 * Method:    nativeGetBuffer
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_sfml_audio_SoundBufferRecorder_nativeGetBuffer (JNIEnv *env, jobject obj) {
    return (jlong)&THIS(sf::SoundBufferRecorder)->getBuffer();
}
