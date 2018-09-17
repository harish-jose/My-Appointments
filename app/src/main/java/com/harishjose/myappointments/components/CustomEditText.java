package com.harishjose.myappointments.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.harishjose.myappointments.R;

/**
 * Created by harish.jose on 17-09-2018.
 */
public class CustomEditText extends LinearLayout {
    private Context context;
    private TextInputLayout inputLayout;
    private EditText etField;
    private ImageView ivErrorIndication;
    private static int id = 1000;
    boolean isRequired;
    private OnTextChangedCallback textChangeCallback;
    private Handler handler = new Handler();
    private Runnable runnable;

    public CustomEditText(Context context) {
        this(context, null);
    }

    public CustomEditText(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.Custom_EditText_Options, 0, 0);
        String fieldHint = a.getString(R.styleable.Custom_EditText_Options_field_hint);
        isRequired = a.getBoolean(R.styleable.Custom_EditText_Options_field_required, false);
        String fieldValue = a.getString(R.styleable.Custom_EditText_Options_field_text);
        int imeOption = a.getInt(R.styleable.Custom_EditText_Options_field_ime_option,0);
        int inputType = a.getInt(R.styleable.Custom_EditText_Options_field_input_type,0);
        int fieldMaxLength = a.getInt(R.styleable.Custom_EditText_Options_field_max_length, -1);
        boolean isEnabled = a.getBoolean(R.styleable.Custom_EditText_Options_enabled, true);
        a.recycle();
        setOrientation(LinearLayout.HORIZONTAL);
        //setBackgroundColor(Color.WHITE);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_edittext, this, true);

        //Binding view elements
        inputLayout = findViewById(R.id.inputLayout);
        etField= findViewById(R.id.etField);
        etField.setId(getId() + id);
        ivErrorIndication = findViewById(R.id.ivErrorIndication);

        //initializing values
        inputLayout.setHint(fieldHint);
        etField.setText(fieldValue);
        if(fieldMaxLength != -1) {
            InputFilter[] filters = new InputFilter[1];
            filters[0] = new InputFilter.LengthFilter(fieldMaxLength);
            etField.setFilters(filters);
        }
        etField.setSelection(etField.getText().length());
        ivErrorIndication.setVisibility(GONE);

        //ime option settings
        switch (imeOption){
            case 1:
                etField.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                break;
            default:
                etField.setImeOptions(EditorInfo.IME_ACTION_DONE);
                break;
        }

        //input type settings
        switch (inputType){
            case 1:
                etField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                break;
            default:
                etField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                break;
        }

        if(!isEnabled) {
            etField.setEnabled(false);
        }

        //Listeners
        etField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateField();
                if(textChangeCallback != null){
                    textChangeCallback.onTextChange(etField.getText().toString().trim());
                }
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 500);
            }
        });

    }

    /**
     * Function which will validate the data in field and set error icon if has error.
     */
    private void validateField(){
        if(isRequired && etField.getText().toString().trim().isEmpty()) {
            ivErrorIndication.setVisibility(VISIBLE);
        } else {
            ivErrorIndication.setVisibility(GONE);
        }
    }

    public void setEnabled(boolean enabled) {
        etField.setEnabled(enabled);
    }

    /**
     * set text to field
     * @param text
     */
    public void setText(String text) {
        etField.setText(text);
    }

    /**
     * set text to field
     * @param text
     */
    public void setText(String text,boolean isIgnoreValue) {
        etField.setText(text);
    }

    /**
     * Fer text from field
     * @return
     */
    public String getText() {
        return etField.getText().toString().trim();
    }

    /**
     * Function which return the edit text instance.
     * @return
     */
    public EditText getEdiText(){
        return etField;
    }


    /**
     * Function which will check filed have valid data
     * @return
     */
    public boolean isHaveValidData(){
        if(ivErrorIndication.getVisibility() == VISIBLE){
            return false;
        } else {
            return true;
        }
    }

    /**
     * set text change listener callback
     * @param callback
     */
    public void setTextChangeListener(OnTextChangedCallback callback){
        textChangeCallback = callback;
    }


    /**
     * Text change callback interface.
     */
    public interface OnTextChangedCallback{
        void onTextChange(String text);
    }

}

