package ru.pyply.funs.mydraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MyDraw extends View {

    private Paint paint = new Paint(); //Кисть


    public MyDraw(Context context) {
        super(context);
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        /*
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(200,200,100, paint);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(300,300,100, paint);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(400,400,100, paint);
        paint.setTextSize(70);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText("Hi everyone!", 0, 600, paint);
        canvas.rotate(45, getWidth() / 2, getHeight() / 2);
        paint.setTextSize(70);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText("Hi everyone!",getWidth() / 2, getHeight() / 2, paint);
        canvas.rotate(-45, getWidth() / 2, getHeight() / 2);
        paint.setTextSize(70);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText("Hi everyone! - 1",0, 100, paint);
        */

        Bitmap pic = BitmapFactory.decodeResource(getResources(), R.mipmap.pic1);
        canvas.scale(.5f, .5f);
        canvas.drawBitmap(pic, 0,0, paint);

    }

}