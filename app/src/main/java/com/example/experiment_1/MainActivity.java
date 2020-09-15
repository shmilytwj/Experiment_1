package com.example.experiment_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    //使用栈存储数字和运算符
    Stack<String> num = new Stack<>();
    Stack<String> operate = new Stack<>();
    final int NumID[] = {R.id.Numbuer00, R.id.Numbuer0, R.id.Numbuer1, R.id.Numbuer2, R.id.Numbuer3
            , R.id.Numbuer4, R.id.Numbuer5, R.id.Numbuer6, R.id.Numbuer7, R.id.Numbuer8, R.id.Numbuer9};
    final int OpID[] = {R.id.Add, R.id.Sub, R.id.Multiplication, R.id.Division,
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
                    //判断前一个符号是否是数字
                          if( i-1>=0 &&str[i-1] <= '9' && str[i-1] >= '0'){
                              num.push(num.pop()+String.valueOf(str[i]));
                          }
                          //判断前一个符号是否是小数点
                          else if(i-1>=0 && str[i-1]=='.'){
                              num.push(num.pop()+String.valueOf(str[i]));
                          }
                          else
                              num.push(String.valueOf(str[i]));
                }
                else if(str[i]=='.'){//判断是否是小数点
                    num.push(num.pop()+String.valueOf(str[i]));

                }
                else if(str[i]=='%'){//判断是否为百分号
                    String a=num.pop();
                    double b=Double.parseDouble(a)/100;
                    num.push(String.valueOf(b));
                }
                else if (str[i] == '+' | str[i] == '-' | str[i] == 'x' | str[i] == '÷' | str[i] == '(' | str[i] == ')'| str[i]=='#') {
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
                            if(compare(operate.peek(), String.valueOf(str[i]))=="=")//脱括号
                                operate.pop();
                            else if(str[i]=='#') {
                                while (operate.peek() != "#") {
                                    op = operate.pop();
                                    b = Double.parseDouble(num.pop());
                                    a = Double.parseDouble(num.pop());
                                    Result = Calculator(a, op, b);
                                    num.push(String.valueOf(Result));
                                }
                            }
                            else
                                operate.push(String.valueOf(str[i]));
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
    //计算
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

    public void ConversiononClick(View view) {
        TextView text = findViewById(R.id.DisplayScreen);
        int num = (int) Double.parseDouble(text.getText().toString());
        double a;
        double n;
        switch (view.getId()) {
//            case R.id.Conversion2:
//                text.setText(Integer.toBinaryString(num));
//                break;
//            case R.id.Conversion8:
//
//                text.setText(Integer.toOctalString(num));
//                break;
//            case R.id.Conversion16:
//               text.setText(Integer.toHexString(num));
//                break;
//            case R.id.Conversion210:
//                text.setText(Integer.valueOf(String.valueOf(num),2).toString());
//                break;
//            case R.id.Conversion810:
//                text.setText(Integer.valueOf(String.valueOf(num),8).toString());
//                break;
//            case R.id.Conversion1610:
//                text.setText(Integer.valueOf(String.valueOf(num),16).toString());
//                break;
            case R.id.cos:
                n=Math.cos(Math.toRadians(num));
                text.setText(String.valueOf(n));
                break;
            case R.id.sin:
                n=Math.sin(Math.toRadians(num));
                text.setText(String.valueOf(n));
                break;
            case R.id.tan:
                n=Math.tan(Math.toRadians(num));
                text.setText(String.valueOf(n));
                break;
            case R.id.Length:
                text.setText(String.valueOf(Double.parseDouble(text.getText().toString())/100));
                break;
            case R.id.Volume:
                text.setText(String.valueOf(Double.parseDouble(text.getText().toString())/1000000));
                break;
        }
    }

    public void ConversionNonClick(final View view) {
        final TextView text=findViewById(R.id.DisplayScreen);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View viewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.numdialog, null, false);
        builder.setTitle("进制转换").setView(viewDialog).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText a = viewDialog.findViewById(R.id.A);
                EditText b = viewDialog.findViewById(R.id.B);
                EditText num=viewDialog.findViewById(R.id.Num);
                int n=Integer.valueOf(num.getText().toString(),Integer.valueOf(a.getText().toString()));
                String result="";
                switch(b.getText().toString()){
                    case "2":
                        result=Integer.toBinaryString(n);
                        text.setText(result);
                        break;
                    case "8":
                        result=Integer.toBinaryString(n);
                        text.setText(result);
                        break;
                    case "10":
                        result=String.valueOf(n);
                        text.setText(result);
                        break;
                    case "16":
                        result=Integer.toHexString(n);
                        text.setText(result);
                        break;
                }

            }
        });
        builder.create().show();
    }

    public void ConversionLonClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View viewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.lengthdialog, null, false);
        final TextView text=findViewById(R.id.DisplayScreen);
        builder.setTitle("长度转换").setView(viewDialog).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText l1 = viewDialog.findViewById(R.id.L1);
                EditText l2 = viewDialog.findViewById(R.id.L2);
                EditText num=(EditText) viewDialog.findViewById(R.id.Num1);
                double n=0;
                if(l1.getText().toString().equals("cm")){
                    if(l2.getText().toString().equals("dm")){
                        n=Double.parseDouble(num.getText().toString())/10;
                        text.setText(String.valueOf(n));
                    }
                    else if(l2.getText().toString().equals("m")){
                        n=Double.parseDouble(num.getText().toString())/100;
                        text.setText(String.valueOf(n));
                    }
                }
                else if(l1.getText().toString().equals("dm")){
                    if(l2.getText().toString().equals("cm")){
                        n=Double.parseDouble(num.getText().toString())*10;
                        text.setText(String.valueOf(n));
                    }
                    else if(l2.getText().toString().equals("m")){
                        n=Double.parseDouble(num.getText().toString())/10;
                        text.setText(String.valueOf(n));
                    }
                }
                else if(l1.getText().toString().equals("m")){
                    if(l2.getText().toString().equals("dm")){
                        n=Double.parseDouble(num.getText().toString())*10;
                        text.setText(String.valueOf(n));
                    }
                    else if(l2.getText().toString().equals("cm")){
                        n=Double.parseDouble(num.getText().toString())*100;
                        text.setText(String.valueOf(n));
                    }
                }

            }
        });
        builder.create().show();
    }

    public void ConversionVonClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View viewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.volumedialog, null, false);
        final TextView text=findViewById(R.id.DisplayScreen);
        builder.setTitle("体积转换").setView(viewDialog).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText v1 = viewDialog.findViewById(R.id.V1);
                EditText v2 = viewDialog.findViewById(R.id.V2);
                EditText num=viewDialog.findViewById(R.id.Num2);
                double n=0;
                if(v1.getText().toString().equals("cm")){
                    if(v2.getText().toString().equals("dm")){
                        n=Double.parseDouble(num.getText().toString())/1000;
                        text.setText(String.valueOf(n));
                    }
                    else if(v2.getText().toString().equals("m")){
                        n=Double.parseDouble(num.getText().toString())/1000000;
                        text.setText(String.valueOf(n));
                    }
                }
                else if(v1.getText().toString().equals("dm")){
                    if(v2.getText().toString().equals("cm")){
                        n=Double.parseDouble(num.getText().toString())*1000;
                        text.setText(String.valueOf(n));
                    }
                    else if(v2.getText().toString().equals("m")){
                        n=Double.parseDouble(num.getText().toString())/1000;
                        text.setText(String.valueOf(n));
                    }
                }
                else if(v1.getText().toString().equals("m")){
                    if(v2.getText().toString().equals("dm")){
                        n=Double.parseDouble(num.getText().toString())*1000;
                        text.setText(String.valueOf(n));
                    }
                    else if(v2.getText().toString().equals("cm")){
                        n=Double.parseDouble(num.getText().toString())*1000000;
                        text.setText(String.valueOf(n));
                    }
                }
            }
        });
        builder.create().show();
    }
}


