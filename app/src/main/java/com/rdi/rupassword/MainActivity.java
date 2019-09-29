package com.rdi.rupassword;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText sourseEditeText;
    private TextView resultTextView;
    private TextView generateTextView;
    private TextView qualityTextView;
    private ImageView qualityClipView;
    private TextView lengthPasswordTextView;
    private String[] russians;
    private String[] latin;
    private PasswordsHelper passwordsHelper;
    private View copyButton;
    private View generateButton;
    private View copyGeneratedPasswordButton;
    private SeekBar lengthPasswordSeekBar;
    private CompoundButton checkCaps;
    private CompoundButton checkDigits;
    private CompoundButton checkSymbols;

    private int extraLengthPasswordFromSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = findViewById(R.id.text_result);
        copyButton = findViewById(R.id.button_copy_password);
        sourseEditeText = findViewById(R.id.edit_source);
        qualityTextView = findViewById(R.id.quality_text_view);
        qualityClipView = findViewById(R.id.quality_clip_view);
        Drawable clipDrawable = qualityClipView.getDrawable();
        clipDrawable.setLevel(2500);

        lengthPasswordSeekBar = findViewById(R.id.length_password_SeekBar);
        lengthPasswordTextView = findViewById(R.id.length_password_TextView);


        checkCaps = findViewById(R.id.check_caps);
        checkDigits = findViewById(R.id.check_digits);
        checkSymbols = findViewById(R.id.check_symbols);
        generateTextView = findViewById(R.id.text_generated);
        copyGeneratedPasswordButton = findViewById(R.id.button_copy_generated_password);
        generateButton = findViewById(R.id.button_generate_password);

        russians = getResources().getStringArray(R.array.russian);
        latin = getResources().getStringArray(R.array.latin);

        passwordsHelper = new PasswordsHelper(russians, latin);


        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                if (clipboardManager != null) {
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(getString(R.string.clipboard_title), resultTextView.getText().toString()));
                }
                Toast.makeText(MainActivity.this, R.string.copy, Toast.LENGTH_SHORT).show();
            }
        });

        copyGeneratedPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                if (clipboardManager != null) {
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(getString(R.string.clipboard_title), generateTextView.getText().toString()));
                }
                Toast.makeText(MainActivity.this, R.string.copy, Toast.LENGTH_SHORT).show();
            }
        });


        lengthPasswordSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                extraLengthPasswordFromSeekBar = i;
                lengthPasswordTextView.setText(
                        "Длина: 8 + " +
                                getResources().getQuantityString(R.plurals.symbols_count, i, i)
                                + " = " +
                                getResources().getQuantityString(R.plurals.symbols_count, i + 8, i + 8)
                );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        sourseEditeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                resultTextView.setText(passwordsHelper.convert(charSequence));
                copyButton.setEnabled(charSequence.length() > 0);
                String textForQualityTextView;
                int intForQualityClipView;
                switch (charSequence.length()) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        intForQualityClipView = 2500;
                        textForQualityTextView = "Ужасный";
                        break;
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        intForQualityClipView = 5000;
                        textForQualityTextView = "Плохой";
                        break;
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                        intForQualityClipView = 7500;
                        textForQualityTextView = "Хороший";
                        break;
                    default:
                        intForQualityClipView = 10000;
                        textForQualityTextView = "Отличный";
                }
                qualityTextView.setText(textForQualityTextView);
                qualityClipView.setImageLevel(intForQualityClipView);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateTextView.setText(generatPassword());
            }
        });
    }

    private String generatPassword() {
        StringBuffer generatedPassword = new StringBuffer();
        boolean userCaps = checkCaps.isChecked();
        boolean userDigits = checkDigits.isChecked();
        boolean userSymbols = checkSymbols.isChecked();
        generatedPassword.append("aaaaa" +
                (userCaps ? "B" : "b") +
                (userDigits ? "1" : "c") +
                (userSymbols ? "@" : "d"));
        for (int i = 0; i < extraLengthPasswordFromSeekBar; i++) {
            generatedPassword.append("f");
        }
        return String.valueOf(generatedPassword);
    }
}
