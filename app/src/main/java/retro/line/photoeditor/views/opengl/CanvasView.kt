package retro.line.photoeditor.views.opengl

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.MotionEvent

class CanvasView : GLSurfaceView {
    private val renderer: OpenGLRenderer
    constructor(context: Context): super(context) {
        setEGLContextClientVersion(2)
        renderer = OpenGLRenderer(context)

        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY
    }
    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        setEGLContextClientVersion(2)
        renderer = OpenGLRenderer(context)

        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY
    }
    fun setImageUri(uri: Uri){

        //TODO use this.queueEvent
        renderer.setImageUri(uri)
        requestRender()
    }
    fun saveImage(callback: ((bitmap: Bitmap?)->Unit)) {
        this.queueEvent {
            val x = renderer.getBitmap()
            callback(x)
        }
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.
        if (event == null) return false
        val x: Float = event.x
        val y: Float = event.y

        val normalizedX = (event.x / width) * 2 - 1
        val normalizedY = -((event.y / height) * 2 - 1)

        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                this.queueEvent {
//                    renderer.handleTouchDrag(normalizedX, normalizedY)
                }
            }
            MotionEvent.ACTION_DOWN -> {
                this.queueEvent {
//                    renderer.handleTouchPress(normalizedX, normalizedY)
                }
            }
        }
//        requestRender()

        return true
    }
}