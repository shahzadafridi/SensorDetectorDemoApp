package official.com.sensordetector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity implements Animation.AnimationListener {
    TextView logo, title;
    RelativeLayout parent_view;
    String TAG = "SplashActivity";
    Animation right2left, left2right, top2bottom, bottom2top;
    boolean isFirstAnim = false, isSecondAnim = false;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        handler = new Handler();
        logo = (TextView) findViewById(R.id.splash_s_r3Logo);
        title = (TextView) findViewById(R.id.splash_s_title);
        parent_view = (RelativeLayout) findViewById(R.id.splash_parrent_view);

        left2right = AnimationUtils.loadAnimation(this, R.anim.left2right);
        logo.setAnimation(left2right);

        right2left = AnimationUtils.loadAnimation(this, R.anim.right2left);
        title.setAnimation(right2left);
        right2left.setAnimationListener(this);

        logo.startAnimation(left2right);
        title.startAnimation(right2left);

    }


    @Override
    public void onAnimationStart(Animation animation) {
        Log.e(TAG, "onAnimationStart");
        if (isFirstAnim) {
            isSecondAnim = true;
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Log.e(TAG, "onAnimationEnd");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                title.setVisibility(View.VISIBLE);
                logo.setVisibility(View.VISIBLE);
                Intent intent = new Intent(SplashScreen.this, LoginScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }, 500);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

}
