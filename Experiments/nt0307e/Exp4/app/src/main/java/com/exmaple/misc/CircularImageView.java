package com.exmaple.misc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

// This is just so I can crop the image properly.
public class CircularImageView extends androidx.appcompat.widget.AppCompatImageView {

    private final Path clipPath = new Path();
    private final RectF rect = new RectF();

    public CircularImageView(Context context) {
        super(context);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float radius = getWidth() / 2f;
        rect.set(0, 0, getWidth(), getHeight());
        clipPath.reset();
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
