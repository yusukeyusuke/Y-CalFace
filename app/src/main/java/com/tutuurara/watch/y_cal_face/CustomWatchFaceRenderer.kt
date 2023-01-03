package com.tutuurara.watch.y_cal_face

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import androidx.wear.watchface.CanvasType
import androidx.wear.watchface.Renderer
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.style.CurrentUserStyleRepository
import androidx.wear.watchface.style.WatchFaceLayer
import com.tutuurara.watch.y_cal_face.databinding.CustomWatchFaceBinding
import java.time.ZonedDateTime

class CustomWatchFaceRenderer(
    context: Context,
    surfaceHolder: SurfaceHolder,
    currentUserStyleRepository: CurrentUserStyleRepository,
    watchState: WatchState
) : Renderer.CanvasRenderer2<Renderer.SharedAssets>(
    surfaceHolder,
    currentUserStyleRepository,
    watchState,
    CanvasType.HARDWARE,
    16L,
    false
) {

    private val binding = CustomWatchFaceBinding.inflate(LayoutInflater.from(context))

    override suspend fun createSharedAssets() = object : SharedAssets {
        override fun onDestroy() {}
    }

    override fun renderHighlightLayer(canvas: Canvas, bounds: Rect, zonedDateTime: ZonedDateTime, sharedAssets: SharedAssets) {}

    override fun render(canvas: Canvas, bounds: Rect, zonedDateTime: ZonedDateTime, sharedAssets: SharedAssets) {
        var paint : Paint = Paint()

        if (renderParameters.watchFaceLayers.contains(WatchFaceLayer.COMPLICATIONS_OVERLAY)) {
            binding.root.measure(
                View.MeasureSpec.makeMeasureSpec(bounds.width(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(bounds.height(), View.MeasureSpec.EXACTLY)
            )
            binding.root.layout(0, 0, bounds.width(), bounds.height())
            binding.time.text = String.format("%02d:%02d", zonedDateTime.hour, zonedDateTime.minute)
            binding.root.draw(canvas)

            paint.color = Color.WHITE
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 2F
            paint.isAntiAlias = true

            var calwidth = 250F
            var calheight = 150F
            var calcenterx = bounds.exactCenterX()
            var calcentery = bounds.exactCenterY()+50F

            for (cnt in 0..6){
                canvas.drawLine(calcenterx-(calwidth/2),calcentery-(calheight/2) + (calheight/6)*cnt,calcenterx+(calwidth/2),calcentery-(calheight/2)+(calheight/6)*cnt ,paint)
            }
            for (cnt in 0..7){
                canvas.drawLine(calcenterx-(calwidth/2) + (calwidth/7)*cnt,calcentery-(calheight/2) ,calcenterx-(calwidth/2) + (calwidth/7)*cnt,calcentery+(calheight/2),paint)
            }
        }
    }
}
