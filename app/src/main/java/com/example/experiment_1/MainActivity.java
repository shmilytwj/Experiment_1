package com.example.experiment_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    //使用栈存储数字和运算符
    Stack<String> num = new Stack<>();
    Stack<String> operate = new Stack<>();
    final int NumID[] = {R.id.Numbuer00, R.id.Numbuer0, R.id.Numbuer1, R.id.Numbuer2, R.id.Numbuer3
            , R.id.Numbuer4, R.id.Numbuer5, R.id.Numbuer6, R.id.Numbuer7, R.id.Numbuer8, R.id.Numbuer9};
    final int OpID[] = {R.id.Add, R.id.Sub, R.id.Multiplication, R.id.Division, R.id.Square,
            R.id.Point, R.id.Prencent, R.id.LeftBracket, R.id.RightBracket};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {

        int id = view.getId();
        TextView text = (TextView) findViewById(R.id.DisplayScreen);
        Button bt = (Button) findViewById(id);
        for (int i = 0; i < NumID.length; i++) {
            if (id == NumID[i]) {
                text.setText(text.getText().toString() + bt.getText().toString());
            }
        }
        for (int i = 0; i < OpID.length; i++) {
            if (id == OpID[i]) {
                text.setText(text.getText().toString() + bt.getText().toString());
            }
        }
        if (id == R.id.Reset)
            text.setText("");

        if (id == R.id.Delete) {
            String string = text.getText().toString();
            char[] str = string.toCharArray();
            string = "";
            for (int i = 0; i < str.length - 1; i++)
                string = string + str[i];
            text.setText(string);
        }
    }

    public void AnsweronClick(View view) {
        double Result = 0;
        TextView text = (TextView) findViewById(R.id.DisplayScreen);
        String string = text.getText().toString()+"#";
        //将显示屏中的表达式进行划分:数字和运算符
        char[] str = string.toCharArray();
        operate.push("#");
            for (int i = 0; i < str.length; i++) {
                if (str[i] <= '9' && str[i] >= '0') {
                          if(i-1>=0 && str[i-1] <= '9' && str[i-1] >= '0' ){//判断前一个符号是否是数字
                              num.push(num.pop()+String.valueOf(str[i]));
                          }
                          else
                              num.push(String.valueOf(str[i]));
                } else if (str[i] == '+' | str[i] == '-' | str[i] == 'x' | str[i] == '÷' | str[i] == '(' | str[i] == ')'| str[i]=='#') {
                    //运算符的优先级判断
                    switch (compare(operate.peek(), String.valueOf(str[i]))) {
                        case "<":
                            operate.push(String.valueOf(str[i]));
                            break;
                        case ">":
                            String op = operate.pop();
                            double b = Double.parseDouble(num.pop());
                            double a = Double.parseDouble(num.pop());
                            Result = Calculator(a, op, b);
                            num.push(String.valueOf(Result));
                            if(compare(operate.peek(), String.valueOf(str[i]))!="=")
                                operate.push(String.valueOf(str[i]));
                            else
                                operate.pop();
                            break;
                    }
                }
            }
        text.setText(num.peek());
    }


    //判断符号优先级
    private String compare(String op1, String op2) {
        String op="";
        switch (op1){
            case "+":
            case "-":
                switch (op2){
                    case "+":
                    case "-":
                    case "#":
                    case ")":
                        op=">";
                        break;
                    case "(":
                    case "x":
                    case "÷":
                        op="<";
                        break;
                }
                break;
            case "x":
            case "÷":
                switch (op2){
                    case "x":
                    case "÷":
                    case ")":
                    case "#":
                    case "+":
                    case "-":
                        op=">";
                        break;
                    case "(":
                        op="<";
                        break;
                }
                break;
            case "(":
                switch(op2){
                    case ")":
                        op="=";
                        break;
                    default:
                        op="<";
                        break;
                }
                break;
            case ")":
                op=">";
                break;
            case "#":
                op="<";
                break;

        }
        return op;
    }

    private double Calculator(double a, String op, double b) {
        double result =0;
        switch (op){
            case "+":
                result=a+b;
                break;
            case "-":
                result=a-b;
                break;
            case "÷":
                result=a/b;
                break;
            case "x":
                result=a*b;
                break;
        }

        return result;
    }

}


