package com.example.administrator.paintandcanvasdemo.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.administrator.paintandcanvasdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 * 自定义一个view，这个View包括一些简单的绘制操作
 */

public class CanvasView extends View {
    private static final String TAG = "CanvasView";
    private float textHeight;
    private float fontSize = getResources().getDimensionPixelSize(R.dimen.default_font_size);
    private TextPaint paint;
    private DrawMode drawMode = DrawMode.UNKNOWN;
    //这个得到屏幕像素密度的那个
    private float density = getResources().getDisplayMetrics().density;
    private Bitmap bitmap;
    private Bitmap bitmap1;

    public CanvasView(Context context) {
        super(context);
        init(null, 0);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    //    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)  //api 21
//    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }
    //一个枚举类 之所以static应该是和那个单例一样的效果
    public static enum DrawMode {
        //有下面这几种模式的对象
        UNKNOWN(0),
        AXIS(1),
        ARGB(2),
        TEXT(3),
        POINT(4),
        LINE(5),
        RECT(6),
        CIRCLE(7),
        OVAL(8),
        ARC(9),
        PATH(10),
        BITMAP(11);
        private int value = 0;

        private DrawMode(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }

        public static DrawMode valueOf(int value) {
            switch (value) {
                case 0:
                    return UNKNOWN;
                case 1:
                    return AXIS;
                case 2:
                    return ARGB;
                case 3:
                    return TEXT;
                case 4:
                    return POINT;
                case 5:
                    return LINE;
                case 6:
                    return RECT;
                case 7:
                    return CIRCLE;
                case 8:
                    return OVAL;
                case 9:
                    return ARC;
                case 10:
                    return PATH;
                case 11:
                    return BITMAP;
                default:
                    return UNKNOWN;
            }
        }
    }

    private void init(AttributeSet attrs, int defStyle) {
        //初始化TextPaint
        paint = new TextPaint();

        //paint的默认字体大小
        Log.e(TAG, "默认字体大小: " + paint.getTextSize() + "px");

        //paint的默认颜色
        Log.e(TAG, "默认颜色: " + Integer.toString(paint.getColor(), 16));

        //paint的默认style是FILL，即填充模式
        Log.e(TAG, "默认style: " + paint.getStyle().toString());

        //paint的默认cap是
        Log.e(TAG, "默认cap: " + paint.getStrokeCap().toString());

        //paint默认的strokeWidth
        Log.e(TAG, "默认strokeWidth: " + paint.getStrokeWidth() + "");
        //设置一些标志，比如抗锯齿，下划线等等。
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);//设置为抗锯齿
        paint.setTextSize(fontSize);//设置字体大小

        //初始化textHeight
        textHeight = fontSize;
        //Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        //textHeight = Math.abs(fontMetrics.top) + fontMetrics.bottom;
    }

    //--------------------------------------提供给外部的方法-----------------------------------
    public void setDrawMode(DrawMode mode) {
        this.drawMode = mode;
        //Android中实现view的更新有两组方法，一组是invalidate，
        // 另一组是postInvalidate，其中前者是在UI线程自身中使用，而后者在非UI线程中使用。
        postInvalidate();
    }

    public void setBitmap(Bitmap bm) {
        releaseBitmap();
        bitmap = bm;
    }

    private void releaseBitmap() {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        bitmap = null;
    }

    public void destroy() {
        releaseBitmap();
    }
    //----------------------------------------绘制的方法---------------------------------


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (drawMode) {
            case AXIS:
                drawAxis(canvas);
                break;
            case ARGB:
                drawARGB(canvas);
                break;
            case TEXT:
                drawText(canvas);
                break;
            case POINT:
                drawPoint(canvas);
                break;
            case LINE:
                drawLine(canvas);
                break;
            case RECT:
                drawRect(canvas);
                break;
            case CIRCLE:
                drawCircle(canvas);
                break;
            case OVAL:
                drawOval(canvas);
                break;
            case ARC:
                drawArc(canvas);
                break;
            case PATH:
                drawPath(canvas);
                break;
            case BITMAP:
                drawBitmap(canvas);
                break;
        }
    }

    //绘制坐标系
    private void drawAxis(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        paint.setStyle(Paint.Style.STROKE);//设置画笔画边的
        paint.setStrokeCap(Paint.Cap.ROUND);//就是边花那种圆形的那个
        paint.setStrokeWidth(8 * density);//Stroke那个边的宽度
        //用canvas试着画一个xy坐标轴
        paint.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawLine(0, 0, canvasWidth, 0, paint);//绘制x轴
        canvas.drawLine(0, 0, 0, canvasHeight, paint);//绘制y轴

        //对坐标系平移后，第二次绘制坐标轴
        canvas.translate(canvasWidth / 4, canvasWidth / 4);//把坐标系向右下角平移
        canvas.drawLine(0, 0, canvasWidth, 0, paint);//绘制x轴
        canvas.drawLine(0, 0, 0, canvasHeight, paint);//绘制y轴

        //对坐标在进行平移及旋转
        canvas.translate(canvasWidth / 6, canvasWidth / 6);//这个平移是真的平移我们的坐标系
        canvas.rotate(-60);
        canvas.drawLine(0, 0, canvasWidth, 0, paint);//绘制x轴
        canvas.drawLine(0, 0, 0, canvasHeight, paint);//绘制y轴

        paint.setColor(getResources().getColor(R.color.bule_overlay));
        canvas.drawLine(100, 300, 400, 500, paint);
        //上面这些平移及效果对后面的操作有直接影响

    }

    //这个是整个背景就画了
    private void drawARGB(Canvas canvas) {
        //随便画一个颜色
        canvas.drawARGB(255, 123, 123, 123);
    }

    private void drawText(Canvas canvas) {
        //这个dx，dy跟你手机像素分辨率有关
        int canvasWidth = canvas.getWidth();
        int halfCanvasWidth = canvasWidth / 2;//屏幕宽度的一半
        //定义一个向y轴的平移量
        float translateY = 100;
//        save：用来保存Canvas的状态。save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作。
//        restore：用来恢复Canvas之前保存的状态。防止save后对Canvas执行的操作对后续的绘制有影响。
//        save和restore要配对使用（restore可以比save少，但不能多），如果restore调用次数比save多，会引发Error。

        //随变绘制一个
        canvas.save();
        paint.setTextSize(fontSize);
        paint.setColor(getResources().getColor(R.color.blue));
        canvas.translate(300, translateY);
        canvas.drawText("海贼王", 0, 0, paint);
        canvas.restore();
        translateY += 400;
        //绘制一个垂直居中的文字
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize((float) (fontSize * 1.2));
        paint.setColor(getResources().getColor(R.color.sy_color1));
        canvas.save();
        canvas.translate(halfCanvasWidth, translateY);
        canvas.drawText("大海贼时代", 0, 0, paint);
        canvas.restore();
        translateY += 600;

        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setUnderlineText(true);//设置下划线
        paint.setTextSize((float) (fontSize * 1.4));
        paint.setFakeBoldText(true);//设置加粗的字体
        paint.setStrikeThruText(true);//设置文本的删除线
        canvas.save();
        canvas.translate(halfCanvasWidth, translateY);
        canvas.rotate(10);//顺时针旋转10度
        canvas.drawText("海贼王剧场版", 0, 0, paint);
        canvas.restore();
        //把paint重置   paint.reset();
        paint.setUnderlineText(false);
        paint.setFakeBoldText(false);
        paint.setStrikeThruText(false);


    }

    private void drawPoint(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int x = canvasWidth / 2;
        int deltaY = canvasHeight / 3;
        int y = deltaY / 2;
        paint.setColor(getResources().getColor(R.color.umeng_blue));//设置颜色
        paint.setStrokeWidth(100 * density);//设置线宽，如果不设置线宽，无法绘制点

        //绘制Cap为BUTT的点
        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoint(x, y, paint);

        //绘制Cap为ROUND的点
        canvas.translate(0, deltaY);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(x, y, paint);

        //绘制Cap为SQUARE的点
        canvas.translate(0, deltaY);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(x, y, paint);

    }

    private void drawLine(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setStrokeWidth(5 * density);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int halfWidth = canvasWidth / 2;
        int halfHeight = canvasHeight / 2;
        int deltaY = canvas.getHeight() / 5;
        int halfDeltaY = deltaY / 2;

        canvas.drawLine(50, 50, canvasWidth - 100, deltaY, paint);

        float[] pts = {
                50, 0, halfWidth, halfDeltaY,
                halfWidth, halfDeltaY, canvasWidth - 50, 0
        };
        //绘制折线
        canvas.save();
        canvas.translate(0, deltaY);
        canvas.drawLines(pts, paint);
        canvas.restore();

        //绘制一个T
        paint.setStrokeWidth(10 * density);
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.save();
        canvas.translate(0, halfHeight);
        canvas.drawLine(halfWidth, 0, halfWidth, 200 * density, paint);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(0, 0, 350 * density, 0, paint);
        canvas.restore();
    }

    //画矩形
    private void drawRect(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int halfWidth = canvasWidth / 2;
        int halfHeight = canvasHeight / 2;
        paint.setColor(getResources().getColor(R.color.colorAccent));
        canvas.save();
        canvas.translate(halfWidth, 0);//平移到屏幕宽度的一半
        //左上右下
        canvas.drawRect(0, 80, 100 * density, canvasHeight / 3, paint);
        canvas.restore();

        paint.setColor(getResources().getColor(R.color.umeng_blue));
        canvas.save();
        canvas.translate(150 * density, canvasHeight / 3);
        //左上右下
        canvas.drawRect(0, 50 * density, 100 * density, canvasHeight / 3, paint);
        canvas.restore();
    }

    //画圆
    private void drawCircle(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int halfCanvasWidth = canvasWidth / 2;
        int halfCanvasHeight = canvasHeight / 2;
        int radius = canvasWidth / 4;
        //paint.setColor(0xff8bc5ba);//设置颜色
        paint.setColor(getResources().getColor(R.color.bule_overlay));
        paint.setStyle(Paint.Style.FILL);
//        cx：圆心的x坐标。
//        cy：圆心的y坐标。
//        radius：圆的半径。
//        paint：绘制时所使用的画笔。
        canvas.drawCircle(halfCanvasWidth, halfCanvasHeight / 3, radius, paint);

        //下面两种画圆环的方式

        canvas.translate(0, halfCanvasHeight / 3);
        canvas.drawCircle(halfCanvasWidth, halfCanvasHeight / 2, radius, paint);

        canvas.translate(0, (float) (halfCanvasHeight * 0.7));
        canvas.drawCircle((float) (radius * 0.7), halfCanvasHeight / 2, (float) (radius * 0.7), paint);
        //画一个内环圆并且设置为白色达到看起来像环状效果
        paint.setColor(getResources().getColor(R.color.white));
        canvas.drawCircle((float) (radius * 0.7), halfCanvasHeight / 2, (float) (radius * 0.5), paint);

        //画一个圆环
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10 * density);//设置圆环的宽度
        paint.setColor(getResources().getColor(R.color.sy_color1));
        canvas.drawCircle(canvasWidth - (float) (radius * 0.7), halfCanvasHeight / 2, (float) (radius * 0.7), paint);


    }

    /*  画椭圆
    * 和画圆一个道理
    */
    private void drawOval(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        float quarter = canvasHeight / 4;
        float left = 10 * density;
        float top = 0;
        float right = canvasWidth - left;
        float bottom = quarter;
        RectF rectF = new RectF(left, top, right, bottom);

        //绘制椭圆形轮廓线
        paint.setStyle(Paint.Style.STROKE);//设置画笔为画线条模式
        paint.setStrokeWidth(2 * density);//设置线宽
        paint.setColor(getResources().getColor(R.color.bule_overlay));//设置线条颜色
        canvas.translate(0, quarter / 4);
        canvas.drawOval(rectF, paint);

        //绘制椭圆形填充面
        paint.setStyle(Paint.Style.FILL);//设置画笔为填充模式
        canvas.translate(0, (quarter + quarter / 4));
        canvas.drawOval(rectF, paint);

        //画两个椭圆，形成轮廓线和填充色不同的效果
        canvas.translate(0, (quarter + quarter / 4));
        //1. 首先绘制填充色
        paint.setStyle(Paint.Style.FILL);//设置画笔为填充模式
        canvas.drawOval(rectF, paint);//绘制椭圆形的填充效果
        //2. 将线条颜色设置为蓝色，绘制轮廓线
        paint.setStyle(Paint.Style.STROKE);//设置画笔为线条模式
        paint.setColor(getResources().getColor(R.color.sy_color4));
        ;//设置填充色为蓝色
        canvas.drawOval(rectF, paint);//设置椭圆的轮廓线
    }

    /*  oval :指定圆弧的外轮廓矩形区域。
        startAngle: 圆弧起始角度，单位为度。
        sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度。
        useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。
        paint: 绘制圆弧的画板属性，如颜色，是否填充等。
        */
    private void drawArc(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int count = 5;
        float ovalHeight = canvasHeight / (count + 1);
        float left = 10 * density;
        float top = 0;
        float right = canvasWidth - left;
        float bottom = ovalHeight;
        RectF rectF = new RectF(left, top, right, bottom);

        paint.setStrokeWidth(2 * density);//设置线宽
        paint.setColor(getResources().getColor(R.color.bule_overlay));//设置颜色
        paint.setStyle(Paint.Style.FILL);//默认设置画笔为填充模式

        //绘制用drawArc绘制完整的椭圆
        canvas.translate(0, ovalHeight / count);
        canvas.drawArc(rectF, 0, 360, true, paint);

        //绘制椭圆的四分之一,起点是钟表的3点位置，从3点绘制到6点的位置
        canvas.translate(0, (ovalHeight + ovalHeight / count));
        canvas.drawArc(rectF, 0, 90, true, paint);

        //绘制椭圆的四分之一,将useCenter设置为false
        canvas.translate(0, (ovalHeight + ovalHeight / count));
        canvas.drawArc(rectF, 0, 90, false, paint);

        //绘制椭圆的四分之一，只绘制轮廓线
        paint.setStyle(Paint.Style.STROKE);//设置画笔为线条模式
        canvas.translate(0, (ovalHeight + ovalHeight / count));
        canvas.drawArc(rectF, 0, 90, true, paint);

        //绘制带有轮廓线的椭圆的四分之一
        //1. 先绘制椭圆的填充部分
        paint.setStyle(Paint.Style.FILL);//设置画笔为填充模式
        canvas.translate(0, (ovalHeight + ovalHeight / count));
        canvas.drawArc(rectF, 0, 90, true, paint);
        //2. 再绘制椭圆的轮廓线部分
        paint.setStyle(Paint.Style.STROKE);//设置画笔为线条模式
        paint.setColor(getResources().getColor(R.color.blue));//设置轮廓线条为蓝色
        canvas.drawArc(rectF, 0, 90, true, paint);

    }

    /*   lineTo(float x, float y)方法：
         lineTo(float x, float y)方法用于从当前轮廓点绘制一条线段到x，y点：
         moveTo(float x, float y)方法：
         path的moveTo方法将起始轮廓点移至x，y坐标点，默认情况为0,0点
         close()方法:回到初始点形成封闭的曲线(感觉就像起点和终点的连接闭合)
         arcTo和addArc的区别:
         1. addArc可以直接加入一段椭圆弧。使用arcTo还需要使用moveTo指定当前点的坐标。
         2. arcTo如果当前点坐标和曲线的起始点不是同一个点的话，还会自动添加一条直线补齐路径。
         addCircle(float x, float y, float radius, Direction dir)方法：
         使用path绘制圆形，xy为圆的圆心 radius为圆的半径，Direction 为绘制元的方向
         Diection.CCW 逆时针方向
         Diection.CW 顺时针方向
         addPath(Path src, float dx, float dy)方法：
         在已有的Path上通过平移创建新的path
      */
    private void drawPath(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int deltaX = canvasWidth / 4;
        int deltaY = (int) (deltaX * 0.75);

        paint.setColor(0xff8bc5ba);//设置画笔颜色
        paint.setStrokeWidth(4);//设置线宽

        /*--------------------------用Path画填充面-----------------------------*/
        paint.setStyle(Paint.Style.FILL);//设置画笔为填充模式
        Path path = new Path();
        //向Path中加入Arc
        RectF arcRecF = new RectF(0, 0, deltaX, deltaY);
        path.addArc(arcRecF, 0, 135);
        //向Path中加入Oval
        RectF ovalRecF = new RectF(deltaX, 0, deltaX * 2, deltaY);
        path.addOval(ovalRecF, Path.Direction.CCW);
        //向Path中添加Circle
        path.addCircle((float) (deltaX * 2.5), deltaY / 2, deltaY / 2, Path.Direction.CCW);
        //向Path中添加Rect
        RectF rectF = new RectF(deltaX * 3, 0, deltaX * 4, deltaY);
        path.addRect(rectF, Path.Direction.CCW);
        canvas.drawPath(path, paint);

        /*--------------------------用Path画线--------------------------------*/
        paint.setStyle(Paint.Style.STROKE);//设置画笔为线条模式
        canvas.translate(0, deltaY * 2);
        Path path2 = path;
        canvas.drawPath(path2, paint);

        /*-----------------使用lineTo、arcTo、quadTo、cubicTo画线--------------*/
        paint.setStyle(Paint.Style.STROKE);//设置画笔为线条模式
        canvas.translate(0, deltaY * 2);
        Path path3 = new Path();
        //用pointList记录不同的path的各处的连接点
        List<Point> pointList = new ArrayList<Point>();
        //1. 第一部分，绘制线段
        path3.moveTo(0, 0);
        path3.lineTo(deltaX / 2, 0);//绘制线段
        pointList.add(new Point(0, 0));
        pointList.add(new Point(deltaX / 2, 0));
        //2. 第二部分，绘制椭圆右上角的四分之一的弧线
        RectF arcRecF1 = new RectF(0, 0, deltaX, deltaY);
        path3.arcTo(arcRecF1, 270, 90);//绘制圆弧
        pointList.add(new Point(deltaX, deltaY / 2));
        //3. 第三部分，绘制椭圆左下角的四分之一的弧线
        //注意，我们此处调用了path的moveTo方法，将画笔的移动到我们下一处要绘制arc的起点上
        path3.moveTo(deltaX * 1.5f, deltaY);
        RectF arcRecF2 = new RectF(deltaX, 0, deltaX * 2, deltaY);
        path3.arcTo(arcRecF2, 90, 90);//绘制圆弧
        pointList.add(new Point((int) (deltaX * 1.5), deltaY));
        //4. 第四部分，绘制二阶贝塞尔曲线
        //二阶贝塞尔曲线的起点就是当前画笔的位置，然后需要添加一个控制点，以及一个终点
        //再次通过调用path的moveTo方法，移动画笔
        path3.moveTo(deltaX * 1.5f, deltaY);
        //绘制二阶贝塞尔曲线
        path3.quadTo(deltaX * 2, 0, deltaX * 2.5f, deltaY / 2);
        pointList.add(new Point((int) (deltaX * 2.5), deltaY / 2));
        //5. 第五部分，绘制三阶贝塞尔曲线，三阶贝塞尔曲线的起点也是当前画笔的位置
        //其需要两个控制点，即比二阶贝赛尔曲线多一个控制点，最后也需要一个终点
        //再次通过调用path的moveTo方法，移动画笔
        path3.moveTo(deltaX * 2.5f, deltaY / 2);
        //绘制三阶贝塞尔曲线
        path3.cubicTo(deltaX * 3, 0, deltaX * 3.5f, 0, deltaX * 4, deltaY);
        pointList.add(new Point(deltaX * 4, deltaY));

        //Path准备就绪后，真正将Path绘制到Canvas上
        canvas.drawPath(path3, paint);

        //最后绘制Path的连接点，方便我们大家对比观察
        paint.setStrokeWidth(10);//将点的strokeWidth要设置的比画path时要大
        paint.setStrokeCap(Paint.Cap.ROUND);//将点设置为圆点状
        paint.setColor(0xff0000ff);//设置圆点为蓝色
        for (Point p : pointList) {
            //遍历pointList，绘制连接点
            canvas.drawPoint(p.x, p.y, paint);
        }
    }

    /*  Android Rect和RectF的区别
    *   精度不一样，Rect是使用int类型作为数值，RectF是使用float类型作为数值
    */
    private void drawBitmap(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int halfCanvasWidth = canvasWidth / 2;
        int halfCanvasHeight = canvasHeight / 2;
        //如果bitmap不存在，那么就不执行下面的绘制代码
        if (bitmap == null) {
            return;
        }

        //直接完全绘制Bitmap
        canvas.drawBitmap(bitmap, 50 * density, 50 * density, paint);
        //绘制Bitmap的一部分，并对其拉伸
        //srcRect定义了要绘制Bitmap的哪一部分
        Rect srcRect = new Rect();
        srcRect.left = 0;
        srcRect.right = bitmap.getWidth();
        srcRect.top = 0;
        srcRect.bottom = (int) (0.5 * bitmap.getHeight());//绘制图片高度的一半

        //这个方法就是要绘制到屏幕的位置
        Rect dstRect = new Rect();
        dstRect.left = (int) (150 * density);
        dstRect.right = (int) (150 * density) + bitmap.getWidth();
        dstRect.top = (int) (150 * density);
        dstRect.bottom = (int) ((int) (0.5 * bitmap.getHeight()) + 150 * density);

        canvas.drawBitmap(bitmap, srcRect, dstRect, paint);

        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.banner1);
        Rect srcRect1 = new Rect();
        srcRect1.left = (int) (150 * density);
        srcRect1.right = bitmap1.getWidth() / 8;
        srcRect1.top = (int) (150 * density);
        srcRect1.bottom = bitmap1.getHeight() / 8;

        //这个方法就是要绘制到屏幕的位置
        Rect dstRect1 = new Rect();
        dstRect1.left = halfCanvasWidth - srcRect1.right;
        dstRect1.right = halfCanvasWidth + srcRect1.right;
        dstRect1.top = halfCanvasHeight - srcRect1.bottom;
        dstRect1.bottom = halfCanvasHeight + srcRect1.bottom;

        canvas.drawBitmap(bitmap1, srcRect1, dstRect1, paint);
        bitmap1.recycle();
    }


}
