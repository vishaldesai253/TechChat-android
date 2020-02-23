package com.example.techchat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;

public class MyCanvas extends View {
    String []col={"#E91E63","#FF5722","#4CAF50","#009688","#4DD0E1","#f6546a","#ff7373","#5ac18e","#42A5F5","#794044","#EF5350","#FF80AB","#FFA726"};
    public static String ch="A";
    public MyCanvas(Context context) {

        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Paint pBackground = new Paint();
        pBackground.setColor(Color.TRANSPARENT);
        canvas.drawRect(0, 0, 70, 70, pBackground);
        Paint paint = new Paint();

        paint.setStyle(Paint.Style.FILL);

        ch=ch.toUpperCase();
        int index=ch.charAt(0)-'A';
        index=index%13;

        paint.setColor(Color.parseColor("#FF"+col[index].substring(1)));
        canvas.drawCircle(35, 30, 30, paint);

        paint.setColor(Color.parseColor("#FFFFFFFF"));
        paint.setTextSize(45f);

        canvas.drawText(ch+"",20,45,paint);

        //paint.setColor(Color.BLACK);
        //paint.setStyle(Paint.Style.STROKE);
        //paint.setStrokeWidth(0.5f);
       // canvas.drawCircle(35, 30, 29, paint);

    }
}