package com.kapware.spinkit;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

class SpinAlgorithms {
    private static final PointF TRANSLATION_BUFFER = new PointF();

    static void drawWanderingCube(Canvas canvas, int viewWidth, int viewHeight, float progress,
                                          Paint paint) {
        final int boxSize = 10 * Math.min(viewWidth, viewHeight) / 32;

        final float scale = Math.abs(Math.abs(2 * progress - 1) - 0.5f) + 0.5f;
        calculateTranslation(TRANSLATION_BUFFER, progress, viewWidth, viewHeight, 0);

        canvas.save();
        canvas.translate(TRANSLATION_BUFFER.x, TRANSLATION_BUFFER.y);
        canvas.scale(scale, scale);
        canvas.rotate(progress * 360);
        canvas.drawRect(0, 0, boxSize, boxSize, paint);
        canvas.restore();
    }

    static void drawWanderingCircle(Canvas canvas, int viewWidth, int viewHeight, float progress,
                                            Paint paint) {
        final int boxSize = 10 * Math.min(viewWidth, viewHeight) / 32;

        final float scale = Math.abs(Math.abs(2 * progress - 1) - 0.5f) + 0.5f;
        calculateTranslation(TRANSLATION_BUFFER, progress, viewWidth - boxSize,
                viewHeight - boxSize, boxSize);

        canvas.save();
        canvas.translate(TRANSLATION_BUFFER.x, TRANSLATION_BUFFER.y);
        canvas.scale(scale, scale);
        canvas.drawCircle(0, 0, boxSize / 2, paint);
        canvas.restore();
    }

    private static void calculateTranslation(PointF translation, float progress,
                                             int pathWidth, int pathHeight, float boxSize) {
        // TODO: optimize
        float x0 = boxSize / 2;
        float y0 = boxSize / 2;
        final int quarter = (int) (progress * 4);
        switch (quarter) {
            case 0:
                translation.set(x0 + 4 * progress * pathWidth, y0);
                return;
            case 1:
                translation.set(x0 + pathWidth, y0 + 4 * (progress - 0.25f) * pathHeight);
                return;
            case 2:
                translation.set(x0 + pathWidth - 4 * (progress - 0.5f) * pathWidth, y0 + pathHeight);
                return;
            default:
            case 3:
                translation.set(x0, y0 + pathHeight - 4 * (progress - 0.75f) * pathHeight);
        }
    }
}
