#include <JSFML/JNI/sfml_graphics_SFMLNativeDrawer.h>

#include <JSFML/Intercom/Intercom.hpp>
#include <JSFML/Intercom/NativeObject.hpp>

#include <JSFML/JNI/sfml_internal_ExPtr.h>

#include <SFML/Graphics/Drawable.hpp>
#include <SFML/Graphics/RenderTarget.hpp>

/*
 * Class:     sfml_graphics_SFMLNativeDrawer
 * Method:    nativeDrawVertices
 * Signature: (ILjava/nio/Buffer;ILorg/jsfml/graphics/RenderTarget;ILjava/nio/Buffer;Lorg/jsfml/graphics/ConstTexture;Lorg/jsfml/graphics/ConstShader;)V
 */
JNIEXPORT void JNICALL Java_sfml_graphics_SFMLNativeDrawer_nativeDrawVertices
    (JNIEnv *env, jclass cls, jint num, jobject vbuf, jint type, jobject jtarget,
        jint blendMode, jobject xform, jobject texture, jobject shader) {

    sf::RenderTarget *target = JSFML::NativeObject::GetExPointer<sf::RenderTarget>(
        env, jtarget, sfml_internal_ExPtr_RENDER_TARGET);
    sf::Vertex *verts = (sf::Vertex*)env->GetDirectBufferAddress(vbuf);

    target->draw(verts, num, (sf::PrimitiveType)type,
        sf::RenderStates((sf::BlendMode)blendMode,
                        JSFML::Intercom::decodeTransform(env, xform),
                        JSFML::NativeObject::GetPointer<sf::Texture>(env, texture),
                        JSFML::NativeObject::GetPointer<sf::Shader>(env, shader)));
}

/*
 * Class:     sfml_graphics_SFMLNativeDrawer
 * Method:    nativeDrawDrawable
 * Signature: (Lorg/jsfml/graphics/Drawable;Lorg/jsfml/graphics/RenderTarget;ILjava/nio/Buffer;Lorg/jsfml/graphics/ConstTexture;Lorg/jsfml/graphics/ConstShader;)V
 */
JNIEXPORT void JNICALL Java_sfml_graphics_SFMLNativeDrawer_nativeDrawDrawable
    (JNIEnv *env, jclass cls, jobject drawable, jobject target,
        jint blendMode, jobject xform, jobject texture, jobject shader) {

    JSFML::NativeObject::GetExPointer<sf::RenderTarget>(env, target, sfml_internal_ExPtr_RENDER_TARGET)->draw(
        *JSFML::NativeObject::GetExPointer<sf::Drawable>(env, drawable, sfml_internal_ExPtr_DRAWABLE),
        sf::RenderStates((sf::BlendMode)blendMode,
                JSFML::Intercom::decodeTransform(env, xform),
                JSFML::NativeObject::GetPointer<sf::Texture>(env, texture),
                JSFML::NativeObject::GetPointer<sf::Shader>(env, shader)));
}
