package balaji66.com.candidateregistration;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditDetailsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    private final AppCompatActivity activity=EditDetailsActivity.this;

    EditText textViewId;
EditText textViewEmail;
EditText textViewFirstName;
EditText textViewLastName;
EditText textViewDateOfBirth;
EditText textViewPassword1;
private DbHelper1 databaseHelper;
private ToggleButton toggleButton;
private Button buttonEdit;
private Button buttonUpdate;
private User user;
    private DatePickerDialog mDatePickerDialog;
    private Calendar cal;
    InputValidation inputValidation;
    private CircleImageView imageView;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggleButton=(ToggleButton)findViewById(R.id.toggle);
       String s= toggleButton.getTextOff().toString();
       String s2= toggleButton.getTextOn().toString();
       toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked)
               {
                   startService(new Intent(EditDetailsActivity.this,MyService.class));
               }
               else
               {
                   stopService(new Intent(EditDetailsActivity.this,MyService.class));
               }
           }
       });
         user =new User();
        initViews();
        getAllCandidate();
        initListeners();
    }
public void initObjects()
{
    inputValidation=new InputValidation(activity);
}
        void initViews() {
    imageView=(CircleImageView)findViewById(R.id.circularImage);
    buttonUpdate =(Button)findViewById(R.id.buttonUpdate);
    buttonEdit=(Button)findViewById(R.id.buttonEdit);
    textViewId=(EditText)findViewById(R.id.id);
    textViewEmail=(EditText)findViewById(R.id.textViewDetailsEmail);
    textViewFirstName=(EditText)findViewById(R.id.textViewDetailsFName);
    textViewLastName=(EditText)findViewById(R.id.textViewDetailsLName);
    textViewPassword1=(EditText)findViewById(R.id.textViewDetailsPassword);
    textViewDateOfBirth=(EditText)findViewById(R.id.textViewDetailsDateOfBirth);
    }
    void initListeners() {
        buttonUpdate.setOnClickListener(this);
        buttonEdit.setOnClickListener(this);
        textViewDateOfBirth.setOnClickListener(this);
    }
        @Override
        public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
   }
        alertDialogOnBackPressed();
    }
        public void alertDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Are you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stopService(new Intent(EditDetailsActivity.this,MyService.class));
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
        public void editButton () {
                textViewFirstName.setEnabled(true);
                textViewLastName.setEnabled(true);
                //textViewEmail.setEnabled(true);
                textViewPassword1.setEnabled(true);
                textViewDateOfBirth.setEnabled(true);
            }
        public  void prepareForEdit() {
    buttonEdit.setVisibility(View.GONE);
    buttonUpdate.setVisibility(View.VISIBLE);
}
        public void prepareForUpdate() {
    buttonEdit.setVisibility(View.VISIBLE);
    buttonUpdate.setVisibility(View.GONE);
}
        public void updateButton() {
                String fname=textViewFirstName.getText().toString();
                String lname=textViewLastName.getText().toString();
                String password=textViewPassword1.getText().toString();
                String dateofbirth=textViewDateOfBirth.getText().toString();
                if(fname.equals(""))
                {
                    textViewFirstName.setError("first name mandatory");
                }
                else if (lname.equals(""))
                {
                    textViewLastName.setError("last name mandatory");
                }
                else if(password.equals(""))
                {
                    textViewPassword1.setError("password should not empty");
                }
                else
                {
                    user.setId(textViewId.getId());
                    user.setFirstname(textViewFirstName.getText().toString().trim());
                    user.setLastname(textViewLastName.getText().toString().trim());
                    user.setDateofbirth(textViewDateOfBirth.getText().toString().trim());
                    user.setEmail(textViewEmail.getText().toString().trim());
                    user.setPassword(textViewPassword1.getText().toString().trim());
                    imageView.setDrawingCacheEnabled(true);
                    imageView.buildDrawingCache();
                    Bitmap bitmap=imageView.getDrawingCache();
                    ByteArrayOutputStream baos =new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                    byte []data=baos.toByteArray();

                    databaseHelper.updateCandidate(user,data);
                    Toast.makeText(EditDetailsActivity.this, "updated successfully", Toast.LENGTH_LONG).show();
                    enableFalse();
                    buttonUpdate.setVisibility(View.GONE);
                    buttonEdit.setVisibility(View.VISIBLE);

                }

            }
        public void enableFalse() {
    textViewFirstName.setEnabled(false);
    textViewLastName.setEnabled(false);
    textViewEmail.setEnabled(false);
    textViewPassword1.setEnabled(false);
    textViewDateOfBirth.setEnabled(false);
    //buttonUpdate.setText("Edit");
}
        public void getAllCandidate(){
        String s=getIntent().getStringExtra("EMAIL");
        databaseHelper=new DbHelper1(this);
        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from stu WHERE candidate_email = '"+s+"'",null);
        cursor.moveToFirst();
        textViewId.setText(cursor.getString(0));
        textViewFirstName.setText(cursor.getString(1));
        textViewLastName.setText(cursor.getString(2));
        textViewDateOfBirth.setText(cursor.getString(3));
        textViewEmail.setText(cursor.getString(4));
        textViewPassword1.setText(cursor.getString(5));
        byte b[]=cursor.getBlob(6);
        Bitmap bp= BitmapFactory.decodeByteArray(b, 0, b.length);
        imageView.setImageBitmap(bp);
    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_details, menu);
        return true;
    }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        int id1 = item.getItemId();

        if(id1==R.id.logOut)
        {
            alertDialog();
            return true;
        }
        return true;
    }
        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_audio) {
            Intent intentAudioRecord =new Intent(EditDetailsActivity.this,RecordAudioActivity.class);
            startActivity(intentAudioRecord);
        } else if (id == R.id.nav_qrCode) {
            Intent intentQrCode =new Intent(EditDetailsActivity.this,QrScanActivity.class);
            startActivity(intentQrCode);
        } else if (id == R.id.nav_call) {
            Intent intentCall = new Intent(Intent.ACTION_DIAL);
            startActivity(intentCall);

        } else if (id == R.id.nav_message) {
            Intent intentMessage=new Intent(Intent.ACTION_MAIN);
            intentMessage.addCategory(Intent.CATEGORY_APP_MESSAGING);
            startActivity(intentMessage);
        } else if (id == R.id.nav_mail) {
            Intent intentMail = new Intent(Intent.ACTION_VIEW);
            intentMail.setData(Uri.parse("http://www.gmail.com"));
            startActivity(intentMail);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
        @Override
        public void onClick(View v) {
        switch (v.getId())
            {
                case R.id.buttonEdit:
                    prepareForEdit();
                    editButton();
                    break;
                case R.id.buttonUpdate:
                    //prepareForUpdate();
                    updateButton();
                    break;
                case R.id.textViewDetailsDateOfBirth:
                    cal=Calendar.getInstance();
                    int day=cal.get(Calendar.DAY_OF_MONTH);
                    int month=cal.get(Calendar.MONTH);
                    int year=cal.get(Calendar.YEAR);
                    mDatePickerDialog=new DatePickerDialog(EditDetailsActivity.this, new DatePickerDialog.OnDateSetListener()
                    {
                        @Override
                        public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay)
                        {
                            textViewDateOfBirth.setText(mDay + "/" + mMonth + "/" + mYear);
                        }
                    },day,month,year);
                    mDatePickerDialog.show();
            }
        }
        public void alertDialogOnBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Are you want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stopService(new Intent(EditDetailsActivity.this,MyService.class));
                        finish();
                        finishAffinity();
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
