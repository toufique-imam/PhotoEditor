package retro.line.photoeditor.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.opengl.GLES20.*
import android.opengl.GLUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder


class TextureHelper {
    companion object {
        private const val TAG = "TextureHelper"
        fun loadTexture(context: Context, resourceId: Int) : Int {
            //  generate one texture object
            val textureObjectIds = IntArray(1)
            glGenTextures(1, textureObjectIds, 0)
            if (textureObjectIds[0] == 0){
                LoggerConfig.w(TAG, "Couldn't generate a new openGL texture object")
                return 0
            }
            val options : BitmapFactory.Options = BitmapFactory.Options()
            options.inScaled = false // original image data instead of a scaled version of the data
            val bitmap = BitmapFactory.decodeResource(context.resources, resourceId, options) //decode the image into bitmap
            if (bitmap == null){
                LoggerConfig.w(TAG, "Resource ID $resourceId couldn't be decoded")
                glDeleteTextures(1, textureObjectIds, 0)
                return 0
            }

            glBindTexture(GL_TEXTURE_2D, textureObjectIds[0]) //future texture calls should be applied to this texture object
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR) // trilinear filtering
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR) // bilinear filtering

            GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0) // reads in the bitmap data defined by bitmap & copy it over into the texture object that is currently bound

            bitmap.recycle() //release the bitmap data
            glGenerateMipmap(GL_TEXTURE_2D) //generates mipmap
            glBindTexture(GL_TEXTURE_2D, 0) //unbinds the texture so no further changes to this texture
            return textureObjectIds[0]
        }
        fun deleteTexture(textureId: Int){
            val textureIds = ShaderHelper.toIntBuffer(intArrayOf(textureId))
            glDeleteTextures(1,textureIds)
        }
        fun loadTexture(context: Context, uri: Uri?): Int {
            if(uri == null){
                return 0;
            }
            val textureObjectIds = IntArray(1)
            glGenTextures(1, textureObjectIds, 0)
            if (textureObjectIds[0] == 0){
                LoggerConfig.e(TAG, "Couldn't generate a new openGL texture object")
                return 0
            }
            val options : BitmapFactory.Options = BitmapFactory.Options()
            options.inScaled = false // original image data instead of a scaled version of the data
            val data = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(data)
            data?.close()
            //decode the image into bitmap
            if (bitmap == null){
                LoggerConfig.e(TAG, "$uri couldn't be decoded")
                glDeleteTextures(1, textureObjectIds, 0)
                return 0
            }
            glBindTexture(GL_TEXTURE_2D, textureObjectIds[0]) //future texture calls should be applied to this texture object
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR) // trilinear filtering
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR) // bilinear filtering

            GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0) // reads in the bitmap data defined by bitmap & copy it over into the texture object that is currently bound

            bitmap.recycle() //release the bitmap data
            glGenerateMipmap(GL_TEXTURE_2D) //generates mipmap
            glBindTexture(GL_TEXTURE_2D, 0) //unbinds the texture so no further changes to this texture
            return textureObjectIds[0]
        }
        fun getUriInfo(context: Context, uri: Uri?): Pair<Int,Int> {
            if(uri == null){
                return Pair(0,0);
            }

            val options : BitmapFactory.Options = BitmapFactory.Options()
            options.inScaled = false // original image data instead of a scaled version of the data
            val data = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(data)
            data?.close()
            if (bitmap == null){
                LoggerConfig.e(TAG, "$uri couldn't be decoded")
                return Pair(0,0)
            }
            val ans = Pair(bitmap.width, bitmap.height)
            bitmap.recycle()
            return ans
        }
        fun saveTexture(width: Int, height: Int, textureId: Int): Bitmap? {
            val frame = intArrayOf(1)
            glGenFramebuffers(1,frame,0)
            checkGlError("glGenFramebuffers");
            glBindFramebuffer(GL_FRAMEBUFFER, frame[0])
            checkGlError("glBindFramebuffer");
            glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureId, 0)
            checkGlError("glFramebufferTexture2D");
            val buffer = ByteBuffer.allocateDirect(width * height * 4)
            buffer.order(ByteOrder.nativeOrder())

            glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer)
            checkGlError("glReadPixels");

            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            bitmap.copyPixelsFromBuffer(buffer)

            glBindFramebuffer(GL_FRAMEBUFFER, 0)
            checkGlError("glBindFramebuffer");
            glDeleteFramebuffers(1,frame,0)
            checkGlError("glDeleteFramebuffer");
            return bitmap
        }
        private fun checkGlError(op: String) {
            var error: Int
            while (glGetError().also { error = it } != GL_NO_ERROR) {
                throw RuntimeException("$op: glError $error")
            }
        }
    }
}