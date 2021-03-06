// #include <jni.h>
// #include <string>
#include <GLES3/gl3.h>
#include <android/log.h>

#define LOG_TAG "TRIANGLE"
#define ALOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#include <jni.h>
#include <stdlib.h>

static const char VERTEX_SHADER [] =
    "#version 300 es\n"
    "layout(location = 0) in vec4 vPosition;\n"
    "void main(){\n"
    "gl_Position = vPosition;\n"
    "}\n";
static const char FRAGMENT_SHADER [] =
    "#version 300 es\n"
    "precision mediump float;\n"
    "out vec4 fragColor;\n"
    "void main(){\n"
    "fragColor = vec4(1.0,0.0,0.0,1.0);\n"
    "}\n";
static const GLfloat VERTEX [] = {
    0.0f,0.5f,0.0f,
    -0.5f,-0.5f,0.0f,
    0.5f,-0.5f,0.0f
};

bool checkGlError(const char* funcName) {
    GLint err = glGetError();
    if (err != GL_NO_ERROR) {
        ALOGE("GL error after %s(): 0x%08x\n", funcName, err);
        return true;
    }
    return false;
}

GLuint createShader(GLenum shaderType, const char* src) {
    GLuint shader = glCreateShader(shaderType);
    if (!shader) {
        checkGlError("glCreateShader");
        return 0;
    }
    glShaderSource(shader, 1, &src, NULL);
    GLint compiled = GL_FALSE;
    glCompileShader(shader);
    glGetShaderiv(shader, GL_COMPILE_STATUS, &compiled);
    if (!compiled) {
        GLint infoLogLen = 0;
        glGetShaderiv(shader, GL_INFO_LOG_LENGTH, &infoLogLen);
        if (infoLogLen > 0) {
            GLchar* infoLog = (GLchar*)malloc(infoLogLen);
            if (infoLog) {
                glGetShaderInfoLog(shader, infoLogLen, NULL, infoLog);
                ALOGE("Could not compile %s shader:\n%s\n",
                      shaderType == GL_VERTEX_SHADER ? "vertex" : "fragment",
                      infoLog);
                free(infoLog);
            }
        }
        glDeleteShader(shader);
        return 0;
    }
    return shader;
}

GLuint createProgram(const char* vtxSrc, const char* fragSrc) {
    GLuint vtxShader = 0;
    GLuint fragShader = 0;
    GLuint program = 0;
    GLint linked = GL_FALSE;
    vtxShader = createShader(GL_VERTEX_SHADER, vtxSrc);
    if (!vtxShader)
        goto exit;
    fragShader = createShader(GL_FRAGMENT_SHADER, fragSrc);
    if (!fragShader)
        goto exit;
    program = glCreateProgram();
    if (!program) {
        checkGlError("glCreateProgram");
        goto exit;
    }
    glAttachShader(program, vtxShader);
    glAttachShader(program, fragShader);
    glLinkProgram(program);
    glGetProgramiv(program, GL_LINK_STATUS, &linked);
    if (!linked) {
        ALOGE("Could not link program");
        GLint infoLogLen = 0;
        glGetProgramiv(program, GL_INFO_LOG_LENGTH, &infoLogLen);
        if (infoLogLen) {
            GLchar* infoLog = (GLchar*)malloc(infoLogLen);
            if (infoLog) {
                glGetProgramInfoLog(program, infoLogLen, NULL, infoLog);
                ALOGE("Could not link program:\n%s\n", infoLog);
                free(infoLog);
            }
        }
        glDeleteProgram(program);
        program = 0;
    }
 exit:
    glDeleteShader(vtxShader);
    glDeleteShader(fragShader);
    return program;
}

GLuint program;

extern "C" JNIEXPORT jboolean JNICALL
Java_com_me_opengl_Triangle_init(JNIEnv* env, jobject obj){
    program = createProgram(VERTEX_SHADER, FRAGMENT_SHADER);
    if (!program){
        ALOGE("??????????????????");
        return JNI_FALSE;
    }
    glClearColor(0,0,0,0);
    return JNI_TRUE;
}

extern "C" JNIEXPORT void JNICALL
Java_com_me_opengl_Triangle_resize(JNIEnv* env, jobject obj, jint width, jint height){
    glViewport(0, 0, width, height);
    glClear(GL_COLOR_BUFFER_BIT);
}

extern "C" JNIEXPORT void JNICALL
Java_com_me_opengl_Triangle_step(JNIEnv* env, jobject obj){
    glClear(GL_COLOR_BUFFER_BIT);
    glUseProgram(program);
    glVertexAttribPointer(0,3,GL_FLOAT,GL_FALSE,0,VERTEX);
    glEnableVertexAttribArray(0);
    glDrawArrays(GL_TRIANGLES,0,3);
}

// extern "C" JNIEXPORT jstring JNICALL
// Java_com_me_opengl_MainActivity_stringFromJNI(
//         JNIEnv* env,
//         jobject /* this */) {
//     std::string hello = "Hello from C++";
//     return env->NewStringUTF(hello.c_str());
// }