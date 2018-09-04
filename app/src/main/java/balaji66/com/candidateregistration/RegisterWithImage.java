package balaji66.com.candidateregistration;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterWithImage extends AppCompatActivity implements View.OnClickListener ,View.OnTouchListener{
    private static final int SELECTED_PICTURE=1;
    private final AppCompatActivity activity=RegisterWithImage.this;
    private ScrollView mScrollView;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mDateOfBirth;
    private EditText mPassword;
    private EditText mReEnterPassword;
    private Button mButtonRegister;
    private TextView mTextViewLoginLink;
    private DbHelper1 mDatabaseHelper;
    private User mUser;
    private CircleImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_with_image);
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
        imageView=(CircleImageView)findViewById(R.id.circularImage);
        int i=R.color.colorPrimary;
        imageView.setBackgroundResource(i);
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
        mDatabaseHelper=new DbHelper1(activity);
        mUser=new User();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case SELECTED_PICTURE:
                if(resultCode==RESULT_OK)
                {
                    Uri uri=data.getData();
                    String[]projection={MediaStore.Images.Media.DATA};
                    Cursor cursor=getContentResolver().query(uri,projection,null,null,null);
                    cursor.moveToFirst();
                    int columnIndex=cursor.getColumnIndex(projection[0]);
                    String filepath=cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap yourSelectedImage= BitmapFactory.decodeFile(filepath);
                    Drawable d=new BitmapDrawable(yourSelectedImage);
                    //imageView.setBackground(d);
                    imageView.setImageDrawable(d);
                }
                else {


                    imageView.setImageResource(R.drawable.ic_profile);
                }

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRegister1:
                postDataToSQLite();
                break;
            case R.id.textViewLoginLink:
                Intent intentLoginLink =new Intent(RegisterWithImage.this,CandidateLoginActivity.class);
                startActivity(intentLoginLink);
                finish();
                break;
            /*case R.id.editTextDateOfBirht:*/
            case R.id.editImage:
                Intent in=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                in.putExtra("crop", "true");
                in.putExtra("outputX", 100);
                in.putExtra("outputY", 100);
                in.putExtra("scale", true);
                in.putExtra("return-data", true);
                startActivityForResult(in,SELECTED_PICTURE);
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
        else if(!repassword.equals(password))
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
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bitmap=imageView.getDrawingCache();
            ByteArrayOutputStream baos =new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte []data=baos.toByteArray();
            mUser.setImage(data);
            mDatabaseHelper.addCandidate(mUser,data);

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
                DatePickerDialog mDatePickerDialog = new DatePickerDialog(RegisterWithImage.this, new DatePickerDialog.OnDateSetListener() {
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
