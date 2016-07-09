package itac.together;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {


    RelativeLayout Main_RelaLay, OutestRelLay;
    View androidSupDsignCooLay;
    View side;
    ViewGroup Parent;
    boolean sideHasShown = false;
    LayoutInflater LayInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Proclaim_view();//MVC模式的宣告函式

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//很重要 要讓2.X的使用就要處理好



        //每當Relative Layout 被點下去的時候就會進行偵測
        Main_RelaLay.setOnTouchListener(new View.OnTouchListener() {
            // 建立GestureDetector物件，並傳入自己定義的手勢物件（繼承自SimpleOnGestureListener）
            GestureDetector gd = new GestureDetector(MainActivity.this, new SideGestureDetectorListener());

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                // 呼叫GestureDetector的onTouchEvent()方法，傳入收到的MotionEvent物件
                //判斷是不是已經顯示側邊滑動欄(side)了
                if(sideHasShown)
                {
                }
                else
                {
                    //還沒出現側邊滑動欄(side)的話就用手勢偵測去判斷要不要顯示側邊滑動欄(side)
                    Log.d("get into ontouch event", "");

                    gd.onTouchEvent(event);
                }
                return true; // 必須回傳true,因為已經了
            }
        });










    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void  Proclaim_view()
    {
        Main_RelaLay = (RelativeLayout) findViewById(R.id.Main_RelaLay);
        androidSupDsignCooLay = findViewById(R.id.androidSupDsignCooLay);
        OutestRelLay = (RelativeLayout)findViewById(R.id.OutestRelLay);
        //Parent 是比看的到的元件還要外面的那一層  加View 在這底下就可以蓋過Action Bar 了~~(查了好久Q_Q)




        Parent = (ViewGroup) OutestRelLay.getParent();
        //把滑動的側邊直接轉成View 等待呼叫  不然不知道使用著會呼叫到多少次 = =


        LayInflater =  (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        side = LayInflater.inflate(R.layout.side_menu, null);
        Parent.addView(side);
        side.setVisibility(View.GONE);
        Parent.bringToFront();
        Log.d("Layout Inflater 已經做好了", "side已經放進去了");
    }




    private View.OnTouchListener sideGoBack = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getX() >120)
            {
                sideHasShown = false;

                side.setVisibility(View.GONE);

                return true;
            }
            else
                return false;
        }
    };


    class SideGestureDetectorListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            Log.d("In OnFling Mode", "~~");
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);//取得螢幕大小

            if(e1.getX() < metrics.widthPixels / 2  && velocityX >= 1)
            {

                sideHasShown = true;
                side.setVisibility(View.VISIBLE);
                side.setOnTouchListener(sideGoBack);


                Log.d("In On Fling IFFFF", "return True");
                return super.onFling(e1, e2, velocityX, velocityY);
            }
            else{
                Log.d("In On Fling IFFFF", "return False");
                return false;
            }
        }
    }




}


