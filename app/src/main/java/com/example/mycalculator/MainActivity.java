package com.example.mycalculator;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MaterialButton clearBtn, percBtn, add7, add8, add9, add4,
    add5, add6, add3, add2, add1, add00, add0, addDecimal, resBtn;



    MaterialButton deleteBtn, divBtn, multiplyBtn, plusBtn, minusBtn;

    ImageButton optionsMenu, history;

    TextView editTextInput, editTextOutput;
    ScrollView scrollInput;

    ArrayList<history_info> arrHistory = new ArrayList<>();

    int flag1 = 0, flag2 = 0, flag3 = 0;
    String finalResult;
    String inputData = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);


        setContentView(R.layout.activitymain);

        View main = findViewById(R.id.main);

        int id = getResources().getIdentifier("config_showNavigationBar","bool","android");
        boolean result = id > 0 && getResources().getBoolean(id);

        if(result) {
            ViewGroup.MarginLayoutParams paramsMain = (ViewGroup.MarginLayoutParams) main.getLayoutParams();
            paramsMain.bottomMargin = 100;
        }

        init();

        loadFrag(new buttonFragment(),0);

        optionsMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.options_drop_down,popupMenu.getMenu());
                popupMenu.show();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flag2 == 1)
                {
                    loadFrag(new buttonFragment(),1);
                    flag2 = 0;
                    return;
                }

                loadFrag(new historyFragment(arrHistory),1);
                flag2 = 1;
            }
        });
    }

    public void onClick(View v)
    {
        MaterialButton button = (MaterialButton) v;
        String btnText = button.getText().toString();
        String inputText = editTextInput.getText().toString() + btnText;

        scrollInput.smoothScrollTo(0,editTextInput.getBottom());


        if(btnText.equals("+") || btnText.equals("-") || btnText.equals("÷") || btnText.equals("×") || btnText.equals("%"))
            flag1 = 1;

        if(btnText.equals("÷")) btnText = "/";
        if(btnText.equals("×")) btnText = "*";
        if(btnText.equals("%")) btnText = "/100*";

        if(btnText.equals("AC"))
        {
            editTextInput.setText("");
            editTextOutput.setText("");
            inputData = "";
            inputText = "";
            flag1 = 0;
            return;
        }

        else if(btnText.equals("="))
        {
            if(flag1 == 1) arrHistory.add(new history_info(editTextInput.getText().toString(),editTextOutput.getText().toString()));
            editTextInput.setText(editTextOutput.getText());
            inputData = editTextOutput.getText().toString();
            editTextOutput.setText("");
            flag1 = 0;

            return;
        }

        else if(btnText.equals("C") && inputData.isEmpty() || btnText.equals("C") && inputData.length() == 1)
        {
            editTextInput.setText("");
            editTextOutput.setText("");
            return;
        }

        else if(btnText.equals("C") && !inputData.isEmpty())
        {

            inputData = inputData.substring(0,inputData.length()-1);

            inputText = inputText.substring(0,inputText.length()-2);

            if(inputData.endsWith("/100*"))
            {
                inputData = inputData.replace("/100*","");
            }

            if(inputData.endsWith("/100"))
            {
                inputData = inputData.replace("/100","");
            }

            String res = getResult(inputData);

            if(flag1 == 1)
            {
                editTextInput.setText(inputText);
                editTextOutput.setText(res);
            }

            else
            {
                editTextInput.setText(inputText);
                editTextOutput.setText("");
            }

            return;
        }

        else {
            inputData = inputData + btnText;
        }

        Log.d("String Check","string is :" + inputData);


        editTextInput.setText(inputText);

        if(flag1 == 1)
        {
            finalResult = getResult(inputData);

            if(!finalResult.equals("Error"))
            {
                Log.d("Result val","String is : " + finalResult);
                editTextOutput.setText(finalResult);
            }
        }

        else
        {
            editTextOutput.setText("");
        }

    }

    String getResult(String dataToCalculate)
    {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();

            String finalRes = dataToCalculate;

            if(finalRes.endsWith("+") || finalRes.endsWith("/") || finalRes.endsWith("*")
                    || finalRes.endsWith("-") || finalRes.endsWith("%") )
            {
                finalRes = finalRes.substring(0,finalRes.length()-1);
            }

            finalRes = context.evaluateString(scriptable,finalRes,"JavaScript",1,null).toString();

            Log.d("resCheck",finalRes);

            if(finalRes.endsWith("E14")) flag3 = 1;

            if(flag3 == 0)
            {
                finalRes = foo(Double.parseDouble(finalRes));
            }

            if(finalRes.endsWith(".000000"))
            {
                finalRes = finalRes.replace(".000000","");
            }



            return finalRes;

        }catch (Exception e)
        {
            return "Error";
        }
    }

    public String foo(double value) //Got here 6.743240136E7 or something..
    {
        DecimalFormat formatter;

        if(value - (int)value > 0.0)
            formatter = new DecimalFormat("0.000000"); //Here you can also deal with rounding if you wish..
        else
            formatter = new DecimalFormat("0");

        return formatter.format(value);
    }

    private void init()
    {

        clearBtn = findViewById(R.id.clearBtn);
        percBtn = findViewById(R.id.percBtn);
        deleteBtn = findViewById(R.id.backspaceBtn);
        divBtn = findViewById(R.id.divBtn);
        multiplyBtn = findViewById(R.id.multiplyBtn);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        add1 = findViewById(R.id.insert1Btn);
        add2 = findViewById(R.id.insert2Btn);
        add3 = findViewById(R.id.insert3Btn);
        add4 = findViewById(R.id.insert4Btn);
        add5 = findViewById(R.id.insert5Btn);
        add6 = findViewById(R.id.insert6Btn);
        add7 = findViewById(R.id.insert7btn);
        add8 = findViewById(R.id.insert8Btn);
        add9 = findViewById(R.id.insert9Btn);
        add00 = findViewById(R.id.insertDouble0);
        add0 = findViewById(R.id.insert0Btn);
        addDecimal = findViewById(R.id.insertDecimalBtn);
        editTextInput = findViewById(R.id.edtTxtInput);
        editTextOutput = findViewById(R.id.edtTxtOutput);
        history = findViewById(R.id.historyBtn);
        optionsMenu = findViewById(R.id.menuBtn);
        scrollInput = findViewById(R.id.scrollInput);
    }

    private void loadFrag(Fragment fragment,int flag)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if(flag == 0)
        {
            ft.add(R.id.frameLayout1,fragment);
        }

        else {
            ft.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_from_right);
            ft.replace(R.id.frameLayout1, fragment);
        }

        ft.commit();
    }




}