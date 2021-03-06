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
    private LinkedList<LinkedList<HashMap<String, Float>>> lines, recycler;
    private int color = Color.BLUE;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        lines = new LinkedList<>();
        recycler = new LinkedList<>();
        paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(LinkedList<HashMap<String,Float>> line : lines){
            HashMap<String,Float> color = line.get(0);
            // Float -> int?
            paint.setColor(color.get("color").intValue());
            for (int i = 2; i<line.size(); i++){
                HashMap<String,Float> p0 = line.get(i-1);
                HashMap<String,Float> p1 = line.get(i);
                canvas.drawLine(p0.get("x"), p0.get("y"), p1.get("x"), p1.get("y"),paint);
            }
        }

    }

    public void setColor(int newColor){
        color = newColor;
        invalidate();
    }

    public int getColor(){
        return color;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() ==  MotionEvent.ACTION_DOWN){
            recycler.clear();
            LinkedList<HashMap<String,Float>> line = new LinkedList<>();

            HashMap<String,Float> setting = new HashMap<>();
            setting.put("color", (float)color); // int -> float => OK; int -> float -> Float
            line.add(setting);

            lines.add(line);
        }

        float ex = event.getX(), ey = event.getY();
        HashMap<String,Float> point = new HashMap<>();
        point.put("x", ex); point.put("y", ey);
        lines.getLast().add(point);

        invalidate();   // repaint()

        return true;
    }

    public void clear(){
        lines.clear();
        invalidate();
    }

    public void undo(){
        if (lines.size()>0) {
            recycler.add(lines.removeLast());
            invalidate();
        }
    }

    public void redo(){
        if (recycler.size()>0) {
            lines.add(recycler.removeLast());
            invalidate();
        }
    }


}
