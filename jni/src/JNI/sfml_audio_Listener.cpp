#include <JSFML/JNI/sfml_audio_Listener.h>

#include <SFML/Audio/Listener.hpp>

/*
 * Class:     sfml_audio_Listener
 * Method:    nativeSetGlobalVolume
 * Signature: (F)V
 */
JNIEXPORT void JNICALL Java_sfml_audio_Listener_nativeSetGlobalVolume
    (JNIEnv *env, jclass cls, jfloat vol) {

    sf::Listener::setGlobalVolume(vol);
}

/*
 * Class:     sfml_audio_Listener
 * Method:    nativeSetPosition
 * Signature: (FFF)V
 */
JNIEXPORT void JNICALL Java_sfml_audio_Listener_nativeSetPosition
    (JNIEnv *env , jclass cls, jfloat x, jfloat y, jfloat z) {

    sf::Listener::setPosition(x, y, z);
}

/*
 * Class:     sfml_audio_Listener
 * Method:    nativeSetDirection
 * Signature: (FFF)V
 */
JNIEXPORT void JNICALL Java_sfml_audio_Listener_nativeSetDirection
    (JNIEnv *env , jclass cls, jfloat x, jfloat y, jfloat z) {

    sf::Listener::setDirection(x, y, z);
}
