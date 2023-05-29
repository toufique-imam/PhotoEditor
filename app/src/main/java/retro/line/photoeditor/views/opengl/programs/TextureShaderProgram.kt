package retro.line.photoeditor.views.opengl.programs

import android.content.Context
import android.opengl.GLES20.*
import retro.line.photoeditor.R

class TextureShaderProgram(context: Context) :
    ShaderProgram(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader) {
    // Uniform locations
    private val uMatrixLocation = glGetUniformLocation(program, uMatrix)
    private val uTextureUnitLocation = glGetUniformLocation(program, uTextureUnit)

    // Attribute locations
    private val aPositionLocation = glGetAttribLocation(program, aPosition)
    private val aTextureCoordinatesLocation = glGetAttribLocation(program, aTextureCoordinates)

    fun setUniforms(textureId: Int) {
        // Pass the matrix into the shader program.
//        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)

        // Set the active texture unit to texture unit 0.
        glActiveTexture(GL_TEXTURE0)

        // Bind the texture to this unit.
        glBindTexture(GL_TEXTURE_2D, textureId)

        // Tell the texture uniform sampler to use this texture in the shader by
        // telling it to read from texture unit 0.
        glUniform1i(uTextureUnitLocation, 0)
    }

    fun getPositionAttributeLocation(): Int {
        return aPositionLocation
    }

    fun getTextureCoordinatesAttributeLocation(): Int {
        return aTextureCoordinatesLocation
    }
}