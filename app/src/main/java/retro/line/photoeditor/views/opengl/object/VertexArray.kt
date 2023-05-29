package retro.line.photoeditor.views.opengl.`object`

import android.opengl.GLES20.*
import retro.line.photoeditor.utils.ShaderHelper

class VertexArray(vertexData: FloatArray) {
    private val floatBuffer = ShaderHelper.toFloatBuffer(vertexData)

    fun setVertexAttribPointer(dataOffset: Int, attributeLocation: Int, componentCount: Int, stride: Int){
        floatBuffer.position(dataOffset)
        glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT, false, stride, floatBuffer)
        glEnableVertexAttribArray(attributeLocation)

        floatBuffer.position(0)
    }
}