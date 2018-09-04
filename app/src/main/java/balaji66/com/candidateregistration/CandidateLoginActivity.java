package balaji66.com.candidateregistration;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.CheckBox;

public class CandidateLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = CandidateLoginActivity.this;
    private NestedScrollView mNestedScrollView;
    private TextInputLayout mTextInputLayoutEmail;
    private TextInputLayout mTextInputLayoutPassword;
    private TextInputEditText mTextInputEditTextEmail;
    private TextInputEditText mTextInputEditTextPassword;
    private AppCompatButton mAppCompatButtonLogin;
    private DbHelper1 mDatabaseHelper;
    private InputValidation mInputValidation;

    private CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_login);
        getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();
    }
        private void initViews() {
        checkBox =(CheckBox)findViewById(R.id.checkBox);
    mNestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
    mTextInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail1);
    mTextInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword1);
    mTextInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail1);
    mTextInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword1);
    mAppCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompactButtonLogIn);
}
        private void initListeners() {
        mAppCompatButtonLogin.setOnClickListener(this);
        }
        private void initObjects() {
        mDatabaseHelper = new DbHelper1(activity);
        mInputValidation = new InputValidation(activity);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompactButtonLogIn:
                verifyFromSQLite();
                break;
            case R.id.checkBox:

        }
    }
    private void verifyFromSQLite() {
        if (mInputValidation.isInputEditTextFilled(mTextInputEditTextEmail, mTextInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!mInputValidation.isInputEditTextEmail(mTextInputEditTextEmail, mTextInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (mInputValidation.isInputEditTextFilled(mTextInputEditTextPassword, mTextInputLayoutPassword, getString(R.string.error_message_passeord))) {
            return;
        }
        if (mDatabaseHelper.checkCandidate(mTextInputEditTextEmail.getText().toString().trim(),mTextInputEditTextPassword.getText().toString().trim()))
        {
            startService(new Intent(CandidateLoginActivity.this,MyService.class));
            Intent accountsIntent = new Intent(activity, EditDetailsActivity.class);
            accountsIntent.putExtra("EMAIL", mTextInputEditTextEmail.getText().toString().trim());
            startActivity(accountsIntent);
            emptyInputEditText();

        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(mNestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }
    private void emptyInputEditText() {
        mTextInputEditTextEmail.setText(null);
        mTextInputEditTextPassword.setText(null);
    }
    @Override
    public void onBackPressed() {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("Are you want to go back?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog=builder.create();
            alertDialog.setTitle("Alert Dialog");
            alertDialog.show();

    }


    }



