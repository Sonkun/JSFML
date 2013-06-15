#include <JSFML/JNI/sfml_window_Keyboard.h>

#include <SFML/Window/Keyboard.hpp>

/*
 * Class:     sfml_window_Keyboard
 * Method:    nativeIsKeyPressed
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_sfml_window_Keyboard_nativeIsKeyPressed (JNIEnv* env, jclass cls, jint key) {
    sf::Keyboard::Key sfKey = (sf::Keyboard::Key)(key);
	return sf::Keyboard::isKeyPressed(sfKey);
}
