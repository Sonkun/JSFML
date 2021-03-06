#include <JSFML/JNI/sfml_audio_Sound.h>

#include <JSFML/Intercom/NativeObject.hpp>

#include <JSFML/JNI/sfml_internal_ExPtr.h>

#include <SFML/Audio/Sound.hpp>

/*
 * Class:     sfml_audio_Sound
 * Method:    nativeCreate
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_sfml_audio_Sound_nativeCreate (JNIEnv *env, jobject obj) {
	return (jlong)new sf::Sound();
}

/*
 * Class:     sfml_audio_Sound
 * Method:    nativeSetExPtr
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_sfml_audio_Sound_nativeSetExPtr (JNIEnv *env, jobject obj) {
    JSFML::NativeObject::SetExPointer(env, obj, sfml_internal_ExPtr_SOUND_SOURCE,
        dynamic_cast<sf::SoundSource*>(THIS(sf::Sound)));
}

/*
 * Class:     sfml_audio_Sound
 * Method:    nativeDelete
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_sfml_audio_Sound_nativeDelete (JNIEnv *env, jobject obj) {
    delete THIS(sf::Sound);
}

/*
 * Class:     sfml_audio_Sound
 * Method:    nativeCopy
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_sfml_audio_Sound_nativeCopy (JNIEnv *env, jobject obj) {
	return (jlong)(new sf::Sound(*THIS(sf::Sound)));
}

/*
 * Class:     sfml_audio_SoundSource
 * Method:    nativeGetData
 * Signature: (Ljava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_sfml_audio_Sound_nativeGetData
    (JNIEnv *env, jobject obj, jobject buffer) {

    sf::Sound *sound = THIS(sf::Sound);
    void *data = env->GetDirectBufferAddress(buffer);

    ((jbyte*)data)[0] = sound->getLoop() ? 1 : 0;
    ((jlong*)data)[1] = (jlong)sound->getPlayingOffset().asMicroseconds();
}

/*
 * Class:     sfml_audio_Sound
 * Method:    play
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_sfml_audio_Sound_play (JNIEnv *env, jobject obj) {
	THIS(sf::Sound)->play();
}

/*
 * Class:     sfml_audio_Sound
 * Method:    pause
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_sfml_audio_Sound_pause (JNIEnv *env, jobject obj) {
	THIS(sf::Sound)->pause();
}

/*
 * Class:     sfml_audio_Sound
 * Method:    stop
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_sfml_audio_Sound_stop (JNIEnv *env, jobject obj) {
	THIS(sf::Sound)->stop();
}

/*
 * Class:     sfml_audio_Sound
 * Method:    nativeSetBuffer
 * Signature: (Lorg/jsfml/audio/SoundBuffer;)V
 */
JNIEXPORT void JNICALL Java_sfml_audio_Sound_nativeSetBuffer
    (JNIEnv *env, jobject obj, jobject buffer) {

	THIS(sf::Sound)->setBuffer(*JSFML::NativeObject::GetPointer<sf::SoundBuffer>(env, buffer));
}

/*
 * Class:     sfml_audio_Sound
 * Method:    nativeSetLoop
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_sfml_audio_Sound_nativeSetLoop(JNIEnv *env, jobject obj, jboolean b) {
	THIS(sf::Sound)->setLoop(b);
}

/*
 * Class:     sfml_audio_Sound
 * Method:    nativeSetPlayingOffset
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_sfml_audio_Sound_nativeSetPlayingOffset
    (JNIEnv *env, jobject obj, jlong time) {

	THIS(sf::Sound)->setPlayingOffset(sf::microseconds((sf::Int64)time));
}

/*
 * Class:     sfml_audio_Sound
 * Method:    nativeGetStatus
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_sfml_audio_Sound_nativeGetStatus (JNIEnv *env, jobject obj) {
	return (jint)THIS(sf::Sound)->getStatus();
}
