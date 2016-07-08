package itac.together;

import android.content.Context;
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


    RelativeLayout Main_RL;
    View androidSupDsignCooLay;
    View side;
    ViewGroup Parent;
    boolean sideHasShown = false;
    LayoutInflater LayInflater;

    ImageButton []sideButtons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Proclaim_view();//MVC模式的宣告函式



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//很重要 要讓2.X的使用就要處理好

        /*  目前不需要用吧? 不知道為什麼有這個?? Can anyone answer me this question?
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/



        //每當Relative Layout 被點下去的時候就會進行偵測
        Main_RL.setOnTouchListener(new View.OnTouchListener() {
            // 建立GestureDetector物件，並傳入自己定義的手勢物件（繼承自SimpleOnGestureListener）
            GestureDetector gd = new GestureDetector(MainActivity.this, new SideGestureDetectorListener());

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                // 呼叫GestureDetector的onTouchEvent()方法，傳入收到的MotionEvent物件
                //判斷是不是已經顯示側邊滑動欄(side)了
                if(sideHasShown)
                {
                    Parent.addView(side);

                    side.setOnClickListener(sideGoBack);
                }
                else
                {
                    //還沒出現側邊滑動欄(side)的話就用手勢偵測去判斷要不要顯示側邊滑動欄(side)
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
        Main_RL = (RelativeLayout) findViewById(R.id.Main_RL);
        androidSupDsignCooLay = findViewById(R.id.androidSupDsignCooLay);
        //Parent 是比看的到的元件還要外面的那一層  加View 在這底下就可以蓋過Action Bar 了~~(查了好久Q_Q)
        Parent = (ViewGroup) androidSupDsignCooLay.getParent();
        //把滑動的側邊直接轉成View 等待呼叫  不然不知道使用著會呼叫到多少次 = =
        LayInflater = getLayoutInflater();
        side = LayInflater.inflate(R.layout.side_mode_select, null);
    }


    private View.OnClickListener sideGoBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getX() >240)
            {
                sideHasShown = false;
                Parent.removeView(side);
            }
        }
    };



    class SideGestureDetectorListener extends GestureDetector.SimpleOnGestureListener
    {

        @Override
        public void onLongPress(MotionEvent e)
        {
            Log.d("MyGestureDetector", "onLongPress");
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                float distanceY)
        {
            /*Log.d("MyGestureDetector", "onScroll");

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);//取得螢幕大小



            if(e1.getX() < metrics.widthPixels / 2 && distanceX >= metrics.widthPixels/3)
            {

                LayoutInflater LInflater = getLayoutInflater();
                                LayoutInflater LIf = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View v = LIf.inflate(R.layout.side_mode_select, Main_RL, true);
                //v.setVisibility(View.VISIBLE);


                Log.d("In On Scroll IFFFF", "return True");
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
            else {

                Log.d("In On Scroll IFFFF", "return False");
                return false;
            }*/
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            Log.d("In OnFling Mode", "~~");
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);//取得螢幕大小

            if(e1.getX() < metrics.widthPixels / 2  && velocityX >= 1)
            {
                /*LayoutInflater LIf = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View v = LayoutInflater.inflate(R.layout.side_mode_select, Main_RL);*/

                sideHasShown = true;
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


