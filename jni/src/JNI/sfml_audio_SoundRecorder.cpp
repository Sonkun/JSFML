#include <JSFML/JNI/sfml_audio_SoundRecorder.h>

#include <SFML/Audio/SoundRecorder.hpp>
#include <JSFML/JNI/sfml_internal_ExPtr.h>

#include <JSFML/Intercom/NativeObject.hpp>
#include <JSFML/Intercom/SoundRecorder.hpp>

#define SOUND_RECORDER JSFML::NativeObject::GetExPointer<sf::SoundRecorder>(env, obj, sfml_internal_ExPtr_SOUND_RECORDER)

/*
 * Class:     sfml_audio_SoundRecorder
 * Method:    isAvailable
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_sfml_audio_SoundRecorder_isAvailable (JNIEnv *env, jclass cls) {
    return sf::SoundRecorder::isAvailable();
}

/*
 * Class:     sfml_audio_SoundRecorder
 * Method:    nativeCreate
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_sfml_audio_SoundRecorder_nativeCreate (JNIEnv *env, jobject obj) {
	return (jlong)new JSFML::SoundRecorder(env, obj);
}

/*
 * Class:     sfml_audio_SoundRecorder
 * Method:    nativeSetExPtr
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_sfml_audio_SoundRecorder_nativeSetExPtr (JNIEnv *env, jobject obj) {
    JSFML::NativeObject::SetExPointer(env, obj, sfml_internal_ExPtr_SOUND_RECORDER,
        dynamic_cast<sf::SoundRecorder*>(THIS(JSFML::SoundRecorder)));
}

/*
 * Class:     sfml_audio_SoundRecorder
 * Method:    nativeDelete
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_sfml_audio_SoundRecorder_nativeDelete (JNIEnv *env, jobject obj) {
    delete THIS(JSFML::SoundRecorder);
}

/*
 * Class:     sfml_audio_SoundRecorder
 * Method:    start
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_sfml_audio_SoundRecorder_start
  (JNIEnv *env, jobject obj, jint sampleRate) {

    SOUND_RECORDER->start((unsigned int)sampleRate);
}

/*
 * Class:     sfml_audio_SoundRecorder
 * Method:    stop
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_sfml_audio_SoundRecorder_stop (JNIEnv *env, jobject obj) {
    SOUND_RECORDER->stop();
}

/*
 * Class:     sfml_audio_SoundRecorder
 * Method:    getSampleRate
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_sfml_audio_SoundRecorder_getSampleRate (JNIEnv *env, jobject obj) {
    return (jint)SOUND_RECORDER->getSampleRate();
}
