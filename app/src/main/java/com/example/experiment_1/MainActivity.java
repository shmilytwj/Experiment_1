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
    Stack<Double> num = new Stack<>();
    Stack<String> operate=new Stack<>();
    final int NumID[]={R.id.Numbuer00,R.id.Numbuer0,R.id.Numbuer1,R.id.Numbuer2,R.id.Numbuer3
            ,R.id.Numbuer4, R.id.Numbuer5,R.id.Numbuer6,R.id.Numbuer7,R.id.Numbuer8,R.id.Numbuer9};
    final int OpID[]={R.id.Add,R.id.Sub,R.id.Multiplication,R.id.Division, R.id.Square,
            R.id.Point,R.id.Prencent,R.id.LeftBracket,R.id.RightBracket};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {

    int id=view.getId();
    TextView text=(TextView)findViewById(R.id.DisplayScreen);
    Button bt=(Button)findViewById(id);
    for(int i=0;i<NumID.length;i++){
        if (id == NumID[i]){
            text.setText(text.getText().toString()+bt.getText().toString());
           }
        }
    for(int i=0;i<OpID.length;i++){
        if(id==OpID[i]){
            text.setText(text.getText().toString()+bt.getText().toString());
        }
        }
    if(id==R.id.Reset)
        text.setText("");

    if (id==R.id.Delete){
        String string=text.getText().toString();
        char [] str = string.toCharArray();
        string="";
        for(int i=0;i<str.length-1;i++)
            string=string+str[i];
        text.setText(string);
    }
    }

    public void AnsweronClick(View view) {
        TextView text=(TextView) findViewById(R.id.DisplayScreen);
        String string=text.getText().toString();
        //将显示屏中的表达式进行划分:数字和运算符
        char [] str = string.toCharArray();
        for(int i=0;i<str.length;i++){
            if(str[i] <='9' && str[i]>='0'){
                num.push(Double.parseDouble(String.valueOf(str[i])));
            }
            else if (str[i]=='+'| str[i]=='-' |str[i]=='x' |str[i]=='÷'| str[i]=='('|str[i]==')') {
                if (operate.empty() && i!=str.length-1)
                    operate.push(String.valueOf(str[i]));
                else {
                    String op = operate.pop();
                    //运算符的优先级判断
                    if (compare(op, String.valueOf(str[i]))) {
                        operate.push(op);
                        operate.push(String.valueOf(str[i]));
                    } else {
                        Double num1 = num.pop();
                        Double num2 = num.pop();

                        Double Result = Calculator(num1, op, num2);
                        num.push(Result);
                        operate.push(String.valueOf(str[i]));
                    }
                }
            }
            else{

                }
        }
    }

    private Double Calculator(Double pop, String op, Double pop1) {
            return 0.0;
    }

    private boolean compare(String op, String valueOf) {
            return true;
    }
}