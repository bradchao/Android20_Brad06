package tw.org.iii.brad.brad06;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.LinkedList;

public class MyView extends View {
    private Paint paint;
    private LinkedList<LinkedList<HashMap<String, Float>>> lines;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        lines = new LinkedList<>();
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(LinkedList<HashMap<String,Float>> line : lines){
            for (int i = 1; i<line.size(); i++){
                HashMap<String,Float> p0 = line.get(i-1);
                HashMap<String,Float> p1 = line.get(i);
                canvas.drawLine(p0.get("x"), p0.get("y"), p1.get("x"), p1.get("y"),paint);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() ==  MotionEvent.ACTION_DOWN){
            LinkedList<HashMap<String,Float>> line = new LinkedList<>();
            lines.add(line);
        }

        float ex = event.getX(), ey = event.getY();
        HashMap<String,Float> point = new HashMap<>();
        point.put("x", ex); point.put("y", ey);
        lines.getLast().add(point);

        invalidate();   // repaint()

        return true;
    }
}
