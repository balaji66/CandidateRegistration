package balaji66.com.candidateregistration;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager  {
    Context context;

    public PrefManager(Context context) {
        this.context = context;
    }
    public void saveLoginDetails(String email, String password)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("CandidateLoginDetails",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Email",email);
        editor.putString("Password",password);
        editor.apply();
    }
    public String getEmailId()
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("CandidateLoginDetails",Context.MODE_PRIVATE);
        return sharedPreferences.getString("Email","");
    }
    public boolean isUserLogOut()
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("CandidateLoginDetails",Context.MODE_PRIVATE);
        boolean isEmailEmpty=sharedPreferences.getString("Email","").isEmpty();
        boolean isPasswordEmpty=sharedPreferences.getString("Password","").isEmpty();
        return isEmailEmpty||isPasswordEmpty;
    }
    public void clear()
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("CandidateLoginDetails",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove("Emaill");
        editor.remove("Password");
        editor.apply();
    }
}
