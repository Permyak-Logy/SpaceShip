package ru.pyply.funs.mydraw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MyGame extends View implements View.OnTouchListener {
    private final boolean DEBUG_TOUCH_ACTION = true; // Флажок отладки действий касаний

    private List<Sprite> enemies;
    private Paint paint = new Paint(); //Кисть
    private int viewWidth;
    private int viewHeight;

    private Bitmap pic, pic_enemy;
    float touchX, touchY;

    float myShipX, myShipY;

    String myAction = "None";

    // Показатель выстрела. 1. Для обработки 2. Для отрисовки
    boolean[] fire = {false, false};

    private final int timerInterval = 1;
    private double speedAddEnemy = 0.5; // Скорость появления метеоров в секунду
    private double timeForAddEnemy = 0; // Готовность создания нового врага

    class Timer extends CountDownTimer {
        long last_time = Integer.MAX_VALUE;

        public Timer() {
            super(Integer.MAX_VALUE, timerInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            update(last_time - millisUntilFinished);
            last_time = millisUntilFinished;
        }

        @Override
        public void onFinish() {
        }
    }

    private void update(long ms) {
        // Добавляем врагов по таймеру
        timeForAddEnemy += speedAddEnemy * ms / 1000;
        if (timeForAddEnemy >= 1) {
            addEnemy();
            timeForAddEnemy = 0;
        }

        // Обновляем врагов и очищаем тех кто стал ниже экрана
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Sprite obj = enemies.get(i);
            obj.update((int) ms);

            if (obj.getY() > viewHeight) {
                enemies.remove(obj);
            }
        }

        // Обрабатываем выстрел
        if (fire[0]) {
            fire[0] = false;
            double fire_x = myShipX + (pic.getWidth() >> 1);  // Координата x по которой пальнули

            for (int i = enemies.size() - 1; i >= 0; i--) {
                Sprite obj = enemies.get(i);
                if (obj.getX() < fire_x && fire_x < obj.getX() + pic_enemy.getWidth() && obj.getY() < viewHeight - pic.getHeight()) {
                    enemies.remove(obj);
                }
            }
        }
    }

    public MyGame(Context context) {
        super(context);
        setOnTouchListener(this);
        pic = BitmapFactory.decodeResource(getResources(), R.mipmap.ship1);
        pic_enemy = BitmapFactory.decodeResource(getResources(), R.mipmap.pic2);

        myShipX = 0;
        paint.setColor(Color.RED);

        enemies = new ArrayList<Sprite>();

        Timer timer = new Timer();
        timer.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWidth = w;
        myShipY = viewHeight - pic.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(pic, myShipX, myShipY, paint);

        if (DEBUG_TOUCH_ACTION) {
            paint.setTextSize(20);
            canvas.drawText(
                    myAction + "=>X: " + String.valueOf(touchX) + ", Y:" + String.valueOf(touchY),
                    100, 100, paint);
        }

        if (fire[1]) {
            fire[1] = false;
            canvas.drawLine(
                    myShipX + pic.getWidth() / 2,
                    myShipY,
                    myShipX + pic.getWidth() / 2,
                    0, paint);
        }

        // Рисуем врагов
        for (Sprite obj : enemies) {
            obj.draw(canvas);
        }

        invalidate();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();

        if (touchX > viewWidth - pic.getWidth() / 2) {
            myShipX = viewWidth - pic.getWidth();
        } else if (touchX < pic.getWidth() / 2) {
            myShipX = 0;
        } else {
            myShipX = touchX - pic.getWidth() / 2;
        }

        fire[0] = true;
        fire[1] = true;

        if (DEBUG_TOUCH_ACTION) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    myAction = "ACTION_DOWN";
                    break;
                case MotionEvent.ACTION_UP:
                    myAction = "ACTION_UP";
                    break;
                case MotionEvent.ACTION_MOVE:
                    myAction = "ACTION_MOVE";
                    break;
            }
        }
        return false;
    }

    public void addEnemy() {
        Rect rect = new Rect(0, 0, pic_enemy.getWidth(), pic_enemy.getHeight());
        enemies.add(new Sprite(Math.random() * this.viewWidth, -rect.height(), 0, Math.random() * 150,
                rect, pic_enemy));
    }
}