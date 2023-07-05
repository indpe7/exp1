package com.example.shiyan1;

import static com.example.shiyan1.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    Button btn_0,btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9;//数字按键
    Button btn_cheng,btn_chu,btn_clear,btn_delete,btn_deng,btn_zhengfu,btn_kaifang,btn_jia,btn_jian,btn_dian;//符号按键
    TextView text_input,text_output;//文本框
    int clr;//清除位
    String biaozhi;//标志位
    String input;
    String output;
    String abd;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实例化对象
        input="";
        output="";
        clr=0;
        biaozhi="";
        abd="";
        btn_0=(Button) findViewById(R.id.ca_0);
        btn_1=(Button) findViewById(R.id.ca_1);
        btn_2=(Button) findViewById(R.id.ca_2);
        btn_3=(Button) findViewById(R.id.ca_3);
        btn_4=(Button) findViewById(R.id.ca_4);
        btn_5=(Button) findViewById(R.id.ca_5);
        btn_6=(Button) findViewById(R.id.ca_6);
        btn_7=(Button) findViewById(R.id.ca_7);
        btn_8=(Button) findViewById(R.id.ca_8);
        btn_9=(Button) findViewById(R.id.ca_9);
        btn_jia=(Button) findViewById(R.id.ca_jia);
        btn_jian=(Button) findViewById(R.id.ca_jian);
        btn_cheng=(Button) findViewById(R.id.ca_cheng);
        btn_chu=(Button) findViewById(R.id.ca_chu);
        btn_clear=(Button) findViewById(R.id.ca_clear);
        btn_delete=(Button) findViewById(R.id.ca_delete);
        btn_deng=(Button) findViewById(R.id.ca_deng);
        btn_zhengfu=(Button) findViewById(R.id.ca_zhengfu);
        btn_kaifang=(Button) findViewById(R.id.ca_kaifang);
        btn_dian=(Button) findViewById(R.id.ca_dian);
        text_input=(TextView) findViewById(R.id.ca_input);
        text_output=(TextView) findViewById(R.id.ca_output);
        //设置点击事件
        btn_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biaozhi+=0;
                input+=0;
                text_input.setText(input);
            }
        });
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biaozhi+=0;
                input+=1;
                text_input.setText(input);
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biaozhi+=0;
                input+=2;
                text_input.setText(input);
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biaozhi+=0;
                input+=3;
                text_input.setText(input);
            }
        });
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biaozhi+=0;
                input+=4;
                text_input.setText(input);
            }
        });
        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biaozhi+=0;
                input+=5;
                text_input.setText(input);
            }
        });
        btn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biaozhi+=0;
                input+=6;
                text_input.setText(input);
            }
        });
        btn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biaozhi+=0;
                input+=7;
                text_input.setText(input);
            }
        });
        btn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biaozhi+=0;
                input+=8;
                text_input.setText(input);
            }
        });
        btn_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biaozhi+=0;
                input+=9;
                text_input.setText(input);
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.length()==0){
                    return;
                }
                input=input.substring(0,input.length()-1);
                biaozhi=biaozhi.substring(0,biaozhi.length()-1);
                text_input.setText(input);
            }
        });
        btn_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input.length()>0 && biaozhi.charAt(biaozhi.length()-1)=='1'){
                    input=input.substring(0,input.length()-1);
                    biaozhi=biaozhi.substring(0,biaozhi.length()-1);
                }else if (input.length()>0 && biaozhi.charAt(biaozhi.length()-1)=='2'){
                    return;
                }
                input+="+";
                biaozhi+=1;
                text_input.setText(input);
            }
        });
        btn_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input.length()>0 && biaozhi.charAt(biaozhi.length()-1)=='1'){
                    input=input.substring(0,input.length()-1);
                    biaozhi=biaozhi.substring(0,biaozhi.length()-1);
                }else if (input.length()>0 && biaozhi.charAt(biaozhi.length()-1)=='2'){
                    return;
                }
                input+="-";
                biaozhi+=1;
                text_input.setText(input);
            }
        });
        btn_cheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input.length()>0 && biaozhi.charAt(biaozhi.length()-1)=='1'){
                    input=input.substring(0,input.length()-1);
                    biaozhi=biaozhi.substring(0,biaozhi.length()-1);
                }else if (input.length()>0 && biaozhi.charAt(biaozhi.length()-1)=='2'){
                    return;
                }
                input+="*";
                biaozhi+=1;
                text_input.setText(input);
            }
        });
        btn_chu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input.length()>0 && biaozhi.charAt(biaozhi.length()-1)=='1'){
                    input=input.substring(0,input.length()-1);
                    biaozhi=biaozhi.substring(0,biaozhi.length()-1);
                }
                else if (input.length()>0 && biaozhi.charAt(biaozhi.length()-1)=='2'){
                    return;
                }
                input+="/";
                biaozhi+=1;
                text_input.setText(input);
            }
        });
        btn_kaifang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input.length()>0 && biaozhi.charAt(biaozhi.length()-1)=='4'){
                    input=input.substring(0,input.length()-1);
                    biaozhi=biaozhi.substring(0,biaozhi.length()-1);
                }
                else if (input.length()>0 && biaozhi.charAt(biaozhi.length()-1)=='0'){
                    return;
                }else if (input.length()>0 && biaozhi.charAt(biaozhi.length()-1)=='2'){
                    return;
                }
                input+="√";
                biaozhi+="4";
                text_input.setText(input);
            }
        });
        btn_zhengfu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int j=0;
                int b=0;
                if (input.length()==0){
                    return;
                }
                else if (input.length()>0 && biaozhi.charAt(biaozhi.length()-1)=='2'){
                    return;
                }
                //负数变正数
                //从最后一个数字向前查找，若发现符号或者正负号，则判定为一个数字
                for (int i=input.length()-1;i>=0;i--){
                    if(biaozhi.charAt(i)=='1' || biaozhi.charAt(i)=='3'||biaozhi.charAt(i)=='4') {
                        if(biaozhi.charAt(i)=='3'){
                            b=1;
                        }
                        j = i;
                        break;
                    }
                }
                String q="";
                String g="";
                if (b==0) {
                    if(j==0){
                        if (biaozhi.charAt(0)=='4'){
                            q=input.substring(0,1);
                            q+="-";
                            q+=input.substring(j+1);
                            g=biaozhi.substring(0,1);
                            g+="3";
                            g+=biaozhi.substring(j+1);
                        }
                        else {
                            g += "3";
                            g += biaozhi;
                            q += "-";
                            q += input;
                        }
                    }
                    else {
                        q = input.substring(0, j+1);
                        q += "-";
                        q += input.substring(j + 1);
                        g=biaozhi.substring(0,j+1);
                        g+="3";
                        g+=biaozhi.substring(j+1);
                    }
                }
                else{
                    if(j==0){
                        if (biaozhi.charAt(0)=='4'){
                            q=input.substring(0,1);
                            q+="-";
                            q+=input.substring(j+1);
                            g=biaozhi.substring(0,1);
                            g+="3";
                            g+=biaozhi.substring(j+1);
                        }
                        else {
                            q = input.substring(j + 1);
                            g = biaozhi.substring(j + 1);
                        }
                    }
                    else {
                        q = input.substring(0, j);
                        q += input.substring(j + 1);
                        g=biaozhi.substring(0,j);
                        g+=biaozhi.substring(j+1);
                    }
                }
                biaozhi=g;
                input=q;
                //text_output.setText(biaozhi);
                text_input.setText(input);
            }
        });
        btn_dian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean k=true;
                for(int i=input.length()-1;i>=0;i--){
                    if (biaozhi.charAt(i)=='1'){
                        break;
                    }else if(biaozhi.charAt(i)=='2'){
                        k=false;
                        break;
                    }
                }
                if(k) {
                    input += ".";
                    biaozhi += "2";
                    text_input.setText(input);
                }
            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                output="";
                input="";
                biaozhi="";
                text_output.setText(output);
                text_input.setText(input);
            }
        });
        btn_deng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.length()==0){
                    return;
                }
                if (IsOp(input.charAt(0))){
                    input=output+input;
                    Log.e("MainActivity",input);
                }
                if (IsOp(input.charAt(0)) && input.length()==1){
                    output="error";
                }
                if(input.length()==1 && input.charAt(0)=='.'){
                    output="error";
                }
                else {
                    output = operation(input);
                }
                text_output.setText(output);
                input="";
                biaozhi="";
                text_input.setText(input);
            }
        });
    }
    boolean IsOp(char a){
        return a=='+' || a=='-' ||a=='*' ||a=='/' || a=='√';
    }
    String operation(String input){
        String res="";
        Stack<Character> stack=new Stack<>();
        Stack<Double> numStack=new Stack<>();
        boolean IsMinus=false;//是否为负数
        boolean K=false;//是否开方
        for (int i=0;i<input.length();){
            String str1="";
            if (input.charAt(i)=='√'){
                K=true;
                i++;
                continue;
            }
            if (input.charAt(i)=='.'){
                if (i>0 && !isNumber(input.charAt(i-1))){
                    str1+="0";
                }
            }
            //判断操作符
            //判断负数
            if(input.charAt(i) == '-' && (i + 1) < input.length() && isNumber(input.charAt(i + 1))){
                if(i == 0 || isOperator(input.charAt(i - 1))){
                    IsMinus = true;
                    i++;
                    continue;
                }
            }
            else if(input.charAt(i) == '-' && (i + 1) < input.length() && input.charAt(i + 1)=='.'){
                if(i == 0 || isOperator(input.charAt(i - 1))){
                    IsMinus = true;
                    i++;
                    continue;
                }
            }
            //是数字的一部分
            while(i<input.length()&& isNumberOrDot(input.charAt(i))){
                str1+=input.charAt(i++);
            }
            //数字处理
            if (!str1.equals("")) {
                res+=str1;
                if(checkNumber(str1)) {
                    double a = new Double(str1);
                    if (IsMinus && K){
                        return"K_error";
                    }
                    if (IsMinus) {
                        a = -a;
                        IsMinus = false;
                    }
                    if (K){
                        a=Math.sqrt(a);
                        K=false;
                    }
                    numStack.push(a);
                }
                else{
                    return "小数错误";
                }
            }//其他字符处理
            else{
                if (stack.isEmpty()){
                    stack.push(input.charAt(i));
                }
                else{
                    if(operatorPrecedent(input.charAt(i))>operatorPrecedent(stack.peek())){
                        stack.push(input.charAt(i));
                    }
                    else{
                        while(!stack.isEmpty()&&operatorPrecedent(input.charAt(i))<=operatorPrecedent(stack.peek())){
                                char c=stack.pop();
                                res+=c;
                                try {
                                    calculate(numStack, c);
                                }catch(Exception e){
                                    return "err";
                                }
                        }
                        stack.push(input.charAt(i));
                    }
                }
                i++;
            }
        }
        while(!stack.isEmpty()){
            char c=stack.pop();
            res+=c;
            try {
                calculate(numStack, c);
            }catch (Exception e){
                return "err";
            }
        }
        double a=numStack.pop();
        return String.valueOf(a);
    }
    boolean isNumber(char c){
        return c == '0' || c == '1' || c == '2' || c == '3' || c == '4'
                || c == '5' || c == '6' || c == '7' || c == '8' || c == '9';
    }
    boolean isNumberOrDot(char c){
        return isNumber(c) || c == '.';
    }
    boolean isOperator(char ch){
        if(ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch =='√')
            return true;
        return false;
    }

    boolean checkNumber(String str){
        int dotCount = 0;
        for(int i = 0 ; i < str.length(); i++){
            if(str.charAt(i) == '.')
                dotCount++;
        }
        if(dotCount > 1){
            return false;
        }
        return true;
    }
    int operatorPrecedent(char c){
        if(c == '*' || c == '/')
            return 2;
        if(c == '+' || c == '-')
            return 1;
        return -1;
    }
    public void  calculate(Stack<Double> numberStack , char ch){
        double num2 = numberStack.pop();
        double num1 = numberStack.pop();
        double res = 0;
        switch (ch){
            case '+': res = num1+num2;
                break;
            case '-': res = num1-num2;
                break;
            case '*': res = num1*num2;
                break;
            case '/':
                if(num2==0) throw new ArithmeticException();
                res = num1/num2;//注意相除产生无线小数的情况，需要指明精度，以及舍入模式
                break;
        }
        numberStack.push(res);
    }
}