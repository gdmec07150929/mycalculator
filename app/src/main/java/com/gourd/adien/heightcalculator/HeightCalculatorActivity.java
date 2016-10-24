package com.gourd.adien.heightcalculator;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class HeightCalculatorActivity extends AppCompatActivity {
    private Button calculatorButton;//计算按钮
    private EditText weightEditText;//体重输入框
    private RadioButton manRadioButton;//男性选择框
    private RadioButton womanRadioButton;//女性选择框
    private TextView resultTextView;//显示结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height_calculator);

        calculatorButton = (Button) findViewById(R.id.calculator);
        weightEditText = (EditText) findViewById(R.id.weight);
        manRadioButton = (RadioButton) findViewById(R.id.man);
        womanRadioButton = (RadioButton) findViewById(R.id.woman);
        resultTextView = (TextView) findViewById(R.id.result);

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerEvent();//注册事件
    }

    private void registerEvent() {
        //注册按钮事件
        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断是否已填写体重数据
                if (!weightEditText.getText().toString().trim().equals("")) {//trim把空格压掉
                    //判断是否已选择性别
                    if (manRadioButton.isChecked() || womanRadioButton.isChecked()) {
                        Double weight = Double.parseDouble(weightEditText.getText().toString());//大double
                        StringBuffer sb = new StringBuffer();
                        sb.append("--------评估结果--------\n");
                        if (manRadioButton.isChecked()) {
                            sb.append("男性标准身高：");
                            //执行运算
                            double result = evaluateHeight(weight, "男");
                            sb.append((int) result + "(厘米)");
                        }
                        if (womanRadioButton.isChecked()) {
                            sb.append("女性标准身高：");
                            //执行运算
                            double result = evaluateHeight(weight, "女");
                            sb.append((int) result + "(厘米)");
                        }
                        //输出页面结果
                        resultTextView.setText(sb.toString());
                    } else {
                        showMessage("请选择性别");
                    }
                } else {
                    showMessage("请选择体重");
                }
            }
        });
    }

    private double evaluateHeight(Double weight, String sex) {
        double height = 0;
        if (sex.equals( "男")) {
            height = 170 - (62 - weight) / 0.6;
        } else if(sex.equals("女")) {
            height = 158 - (52 - weight) / 0.5;
        }
        return height;
    }


    private void showMessage(String message){
        //提示框
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("系统信息");
        alert.setMessage(message);
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(Menu.NONE,1,Menu.NONE,"退出");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 1://退出
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}