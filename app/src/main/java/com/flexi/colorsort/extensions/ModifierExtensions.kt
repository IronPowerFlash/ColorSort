package com.flexi.colorsort.extensions

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.unit.dp


fun Modifier.glowBorder(
    color:Color,
    blurRadius:Dp,
    borderWidth:Dp
) = if(borderWidth > 0.dp){
    this.drawBehind{
        val borderWidthPx = borderWidth.toPx()
        val paint = Paint().apply{
            this.color = color
            this.style = PaintingStyle.Stroke
            this.strokeWidth = borderWidthPx
        }

        paint.asFrameworkPaint()
            .apply { maskFilter = BlurMaskFilter(blurRadius.toPx(),
                BlurMaskFilter.Blur.NORMAL) }

        val shadowSize = Size(
            width = size.width + borderWidthPx,
            height = size.height + borderWidthPx
        )

        val shadowOutline = RectangleShape
            .createOutline(shadowSize, layoutDirection, this)
        drawIntoCanvas { canvas ->
            canvas.save()
            canvas.translate(0 - borderWidthPx / 2,
                0 - borderWidthPx / 2)
            canvas.drawOutline(shadowOutline, paint)
            canvas.restore()
            drawRect(
                size = size,
                color = Color.Transparent,
                blendMode = BlendMode.Clear
            )
        }
    }
}
else {
    this
}