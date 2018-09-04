package squareandcube.com.candidateregistration;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class CandidateRegistrationActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener {

private final AppCompatActivity activity=CandidateRegistrationActivity.this;
    private ScrollView mScrollView;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mDateOfBirth;
    private EditText mPassword;
    private EditText mReEnterPassword;
    private Button mButtonRegister;
    private TextView mTextViewLoginLink;
    private DatabaseHelper mDatabaseHelper;
    private User mUser;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_registration);
        getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();

       }
private void initViews()
{
    TextInputLayout textInputLayoutFirstName = (TextInputLayout) findViewById(R.id.textInputLayoutFirstName);
    TextInputLayout textInputLayoutLastName = (TextInputLayout) findViewById(R.id.textInputLayoutLastName);
    TextInputLayout textInputLayoutEmailId = (TextInputLayout) findViewById(R.id.textInputLayoutEmailId);
    TextInputLayout textInputLayoutDateOfBirth = (TextInputLayout) findViewById(R.id.textInputLayoutDateOfBirth);
    TextInputLayout textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
    TextInputLayout textInputLayoutReEnterPassword = (TextInputLayout) findViewById(R.id.textInputLayoutReEnterPassword);
    mScrollView =(ScrollView)findViewById(R.id.scrollView);
    mFirstName =(EditText)findViewById(R.id.editTextFirstName);
    mLastName=(EditText)findViewById(R.id.editTextLastName);
    mEmail=(EditText)findViewById(R.id.editTextEmailId);
    mDateOfBirth=(EditText)findViewById(R.id.editTextDateOfBirht);
    mPassword=(EditText)findViewById(R.id.editTextPassword);
    mReEnterPassword=(EditText)findViewById(R.id.editTextReEnterPassword);
    mButtonRegister=(Button)findViewById(R.id.buttonRegister1);
    mTextViewLoginLink=(TextView)findViewById(R.id.textViewLoginLink);
}
private void initListeners()
{
    mButtonRegister.setOnClickListener(this);
    mTextViewLoginLink.setOnClickListener(this);
    /*mDateOfBirth.setOnClickListener(this);*/
}

private void initObjects()
{
    InputValidation inputValidation = new InputValidation(activity);
    mDatabaseHelper=new DatabaseHelper(activity);
    mUser=new User();
}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRegister1:
                postDataToSQLite();
                break;

            case R.id.textViewLoginLink:
                Intent intentLoginLink =new Intent(CandidateRegistrationActivity.this,CandidateLoginActivity.class);
                startActivity(intentLoginLink);
                finish();
                break;
            /*case R.id.editTextDateOfBirht:*/

        }

    }

    private void postDataToSQLite()
    {
        String fname=mFirstName.getText().toString();
        String lname=mLastName.getText().toString();
        String email=mEmail.getText().toString();
        String password=mPassword.getText().toString();
        String date=mDateOfBirth.getText().toString();
        String repassword=mReEnterPassword.getText().toString();
        boolean b=mDateOfBirth.isClickable();
        if(fname.equals(""))
        {
            mFirstName.setError("first name is mandatory");
        }
        else if(lname.equals(""))
        {
            mLastName.setError("last name is mandatory");
        }
        else if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mEmail.setError("enter correct email id pattern");
        }
        else if(date.equals(""))
        {
            mDateOfBirth.setError("enter correct date");
        }
        else if(password.equals(""))
        {
            mPassword.setError("enter password");
        }
        else if(repassword.equals(password))
        {
            mReEnterPassword.setError("password and confirm password should match");
        }
       /* if(inputValidation.isEditTextFilled(mFirstName,textInputLayoutFirstName,"enter First Name")){
            return;
        }
        if(inputValidation.isEditTextFilled(mLastName,textInputLayoutLastName,"enter Last Name")){
            return;
        }
        if(inputValidation.isEditTextFilled(mEmail,textInputLayoutEmailId,"enter email")){
            return;
        }
        if(inputValidation.isEditTextFilled(mDateOfBirth,textInputLayoutDateOfBirth,"enter date of Birth")){
            return;
        }
        if(inputValidation.isEditTextFilled(mPassword,textInputLayoutPassword,"enter password")){
            return;
        }
        if(inputValidation.isEditTextFilled(mPassword,textInputLayoutReEnterPassword,"enter confirm password")){
            return;
        }
        if(!inputValidation.isInputEditTextMatches(mPassword,mReEnterPassword,"password and confirm password should match"))
        {
            return;
        }*/
        else if (!mDatabaseHelper.checkCandidate(mEmail.getText().toString().trim())) {

            mUser.setFirstname(mFirstName.getText().toString().trim());
            mUser.setLastname(mLastName.getText().toString().trim());
            mUser.setDateofbirth(mDateOfBirth.getText().toString().trim());
            mUser.setEmail(mEmail.getText().toString().trim());
            mUser.setPassword(mPassword.getText().toString().trim());

            mDatabaseHelper.addCandidate(mUser);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(mScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyEditText();


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(mScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
    }
    private void emptyEditText()
    {
        mFirstName.setText(null);
        mLastName.setText(null);
        mEmail.setText(null);
        mDateOfBirth.setText(null);
        mPassword.setText(null);
        mReEnterPassword.setText(null);
    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        final int DRAWABLE_RIGHT = 2;
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (motionEvent.getRawX() >= (mDateOfBirth.getRight() - mDateOfBirth.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                DatePickerDialog mDatePickerDialog = new DatePickerDialog(CandidateRegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        mDateOfBirth.setText(mDay + "/" + mMonth + "/" + mYear);
                    }
                }, day, month, year);
                mDatePickerDialog.show();
                // String s=mDateOfBirth.getText().toString();


            }
        }
        return false;
    }
}
