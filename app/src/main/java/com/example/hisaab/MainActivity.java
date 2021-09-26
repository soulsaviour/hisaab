package com.example.hisaab;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView iv_mic;
    TextView tv_speech_to_text;
    TableLayout table;
    Random random;
    String saveData = " ";
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_speech_to_text = findViewById(R.id.tv_speech_to_text);


        Hawk.init(MainActivity.this).build();
        if (Hawk.contains("hasan")) {
            ArrayList<String> data = Hawk.get("hasan");
            for (int i =0; i< data.size();i++){
                saveData = saveData + data.get(i) + "\n\n";
            }
            tv_speech_to_text.setText(saveData);
        }

        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        iv_mic = findViewById(R.id.iv_mic);
        random = new Random();
        TableLayout table = new TableLayout(this);
        iv_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                }
                catch (Exception e) {
                    Toast
                            .makeText(MainActivity.this, " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                if (Hawk.contains("hasan")) {
                    ArrayList<String> a = Hawk.get("hasan");
                    a.add(result.get(0));
                    Hawk.put("hasan", a);
                    tv_speech_to_text.setText(tv_speech_to_text.getText() + "\n\n" + result.get(0));
                } else {
                    ArrayList<String> a = new ArrayList<>();
                    a.add(result.get(0));
                    Hawk.put("hasan", a);
                    tv_speech_to_text.setText(result.get(0));
                }
            }
        }
    }
        public void onClick(View view) {
            table.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            table.setShrinkAllColumns(true);
            table.setStretchAllColumns(true);
            for (int i=0; i < 2; i++) {
                TableRow row = new TableRow(MainActivity.this);
                for (int j=0; j < 100; j++) {
                    int value = random.nextInt(100) + 1;
                    TextView tv = new TextView(MainActivity.this);
                    tv.setText(String.valueOf(value));
                    row.addView(tv);
                }
                table.addView(row);
            }
        }
}
