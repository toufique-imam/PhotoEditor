package retro.line.photoeditor.views.opengl

import android.content.Context
import android.net.Uri
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import android.opengl.GLES20.*
import android.opengl.Matrix
import retro.line.photoeditor.R
import retro.line.photoeditor.utils.LoggerConfig
import retro.line.photoeditor.utils.TextureHelper
import retro.line.photoeditor.views.opengl.`object`.ImageDrawer
import retro.line.photoeditor.views.opengl.programs.TextureShaderProgram

class OpenGLRenderer(private val context: Context): GLSurfaceView.Renderer {
    private lateinit var textureShaderProgram: TextureShaderProgram
    private var texture = 0
    private val projectionMatrix = FloatArray(16)
//    private var uMatrixLocation = 0
    private var imageUri: Uri? = null
    private var changeTexture = false
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(0f,0f,0f,0f)
        textureShaderProgram = TextureShaderProgram(context)

        LoggerConfig.e("called", "onSurfaceCreated")
        texture = TextureHelper.loadTexture(context, R.drawable.air_hockey_surface)
    }


    fun setImageUri(uri: Uri){
        if(imageUri != uri) {
            imageUri = uri
            changeTexture = true
        }
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        LoggerConfig.e("called", "onSurfaceChanged")
        glViewport(0,0,width,height)
    }

    override fun onDrawFrame(gl: GL10?) {
        LoggerConfig.e("called", "onDrawFrame")
        glClear(GL_COLOR_BUFFER_BIT)
//        glUniformMatrix4fv(uMatrixLocation, 1 , false , projectionMatrix, 0)
        if(changeTexture){
            changeTexture = false
            TextureHelper.deleteTexture(texture)
            texture = TextureHelper.loadTexture(context, imageUri)
            LoggerConfig.e("textureId", texture.toString())
        }
        textureShaderProgram.useProgram()
        textureShaderProgram.setUniforms(texture)
        val imageDrawer = ImageDrawer()
        imageDrawer.bindData(textureShaderProgram)
        imageDrawer.draw()
    }

}