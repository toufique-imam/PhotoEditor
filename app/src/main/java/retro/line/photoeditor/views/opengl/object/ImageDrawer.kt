package retro.line.photoeditor.views.opengl.`object`

import android.opengl.GLES20
import retro.line.photoeditor.utils.ShaderHelper
import retro.line.photoeditor.views.opengl.programs.TextureShaderProgram

class ImageDrawer {
    private val vertexArray = VertexArray(ShaderHelper.getStandardVertexCoordinates())
    private val textureArray = VertexArray(ShaderHelper.getStandardTextureCoordinates())
    fun bindData(textureProgram: TextureShaderProgram) {
        vertexArray.setVertexAttribPointer(
            0,
            textureProgram.getPositionAttributeLocation(),
            positionComponentCount,
            0
        )
        textureArray.setVertexAttribPointer(
            0,
            textureProgram.getTextureCoordinatesAttributeLocation(),
            textureCoordinatesComponentCount,
            0
        )
    }

    fun draw() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
    }

    companion object {
        const val positionComponentCount = 2
        const val textureCoordinatesComponentCount = 2
//        const val stride =
//            (positionComponentCount + textureCoordinatesComponentCount) * ShaderHelper.bytesPerFloat

        /*
          val vertexData = floatArrayOf(
            0f, 0f,
            -1f, -1f,
            1f, -1f,
            1f, 1f,
            -1f, 1f,
            -1f, -1f
        )

        val textureData = floatArrayOf(
            0.5f, 0.5f,
            0f, 1f,
            1f, 1f,
            1f, 0f,
            0f, 0f,
            0f, 1f
        )
         */
    }
}