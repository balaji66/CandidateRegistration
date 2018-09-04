package squareandcube.com.candidateregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import static java.lang.Thread.sleep;
import static squareandcube.com.candidateregistration.R.anim.abc_fade_out;

public class SplashActivity extends AppCompatActivity {

    private ImageView mImageRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mImageRegister = findViewById(R.id.imageViewCandidateRegister);
        final Animation animationAntiRotate = AnimationUtils.loadAnimation(getBaseContext(), R.anim.antirotate);
        final Animation animationBlink = AnimationUtils.loadAnimation(getBaseContext(), R.anim.blink);

        mImageRegister.startAnimation(animationAntiRotate);
        animationAntiRotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                mImageRegister.startAnimation(animationBlink);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        animationBlink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                Intent mIntentSignIn = new Intent(SplashActivity.this, SignInActivity.class);
                startActivity(mIntentSignIn);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
