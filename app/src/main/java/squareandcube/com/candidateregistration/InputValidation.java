package squareandcube.com.candidateregistration;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by sandeep krishnan on 11-04-2018.
 */

public class InputValidation {

    private Context context;


    public InputValidation(Context context) {
        this.context = context;
    }


    public boolean isInputEditTextFilled(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty()) {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return true;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return false;
    }
    public boolean isEditTextFilled(EditText editText, TextInputLayout textInputLayout, String message) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty()) {
            editText.setError(message);
            hideKeyboardFrom(editText);
            return true;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return false;
    }
    public boolean isInputEditTextMatches(EditText EditText1, EditText EditText2, String message) {
        String value1 = EditText1.getText().toString().trim();
        String value2 = EditText2.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            EditText1.setError(message);
            hideKeyboardFrom(EditText2);
            return false;
        } else {
            EditText1.setEnabled(false);
        }
        return true;
    }
    public boolean isInputEditTextEmail(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }
    public boolean isInputEditTextValid(EditText textInputEditText,String message)
    {
        String value1=textInputEditText.getText().toString().trim();
        if(value1.isEmpty())
        {
            textInputEditText.setError(message);
            return false;
        }
        else{
               textInputEditText.setEnabled(false);
        }
        return true;
    }
    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
