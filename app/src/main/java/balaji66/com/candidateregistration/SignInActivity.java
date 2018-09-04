package balaji66.com.candidateregistration;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
private AppCompatButton mButtonLogin;
private AppCompatButton mButtonRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initiateViews();
        initiateListeners();
    }
    public void initiateViews()
    {
        mButtonLogin =(AppCompatButton)findViewById(R.id.appCompactButtonLogIn);
        mButtonRegister=(AppCompatButton)findViewById(R.id.appCompactButtonRegister);
    }

public void initiateListeners()
{
    mButtonLogin.setOnClickListener(this);
    mButtonRegister.setOnClickListener(this);
}
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.appCompactButtonLogIn:
                Intent mIntentLogin =new Intent(SignInActivity.this,CandidateLoginActivity.class);
                startActivity(mIntentLogin);
                break;
            case R.id.appCompactButtonRegister:
                Intent mIntentRegister =new Intent(SignInActivity.this,RegisterWithImage.class);
               startActivity(mIntentRegister);
               break;
               default:
                   break;
        }

    }
    @Override
    protected void onStop() {
        super.onStop();
    }
}
