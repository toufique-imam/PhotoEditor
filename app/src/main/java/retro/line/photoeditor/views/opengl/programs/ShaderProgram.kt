package retro.line.photoeditor.views.opengl.programs

import android.content.Context
import android.opengl.GLES20
import retro.line.photoeditor.utils.ShaderHelper
import retro.line.photoeditor.utils.TextResourceReader


open class ShaderProgram(context: Context, vertexShaderResourceId: Int, fragmentShaderResourceId: Int) {
    // Compile the shaders and link the program.
    protected val program: Int = ShaderHelper.buildProgram(TextResourceReader.readTextFileFromResource(context, vertexShaderResourceId), TextResourceReader.readTextFileFromResource(context, fragmentShaderResourceId))

    fun useProgram(){
        // Set the current OpenGL shader program to this program.
        GLES20.glUseProgram(program)
    }
    companion object {
        // Uniform constants
        const val uMatrix = "u_Matrix"
        const val uTextureUnit = "u_TextureUnit"

        // Attribute constants
        const val aPosition = "a_Position"
        const val aColor = "a_Color"
        const val uColor = "u_Color"
        const val aTextureCoordinates = "a_TextureCoordinates"
    }
}