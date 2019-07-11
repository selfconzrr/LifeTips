package com.example.zhangruirui.lifetips;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/05/18
 */
public class CalculatorActivity extends BaseActivity implements View.OnClickListener {

  public static final int ADD = 1; // 加
  public static final int MINUS = 2; // 减
  public static final int MULTI = 3; // 乘
  public static final int DIV = 4; // 除
  public static final int ONE_IN_X = 5; // 倒数
  public static final int CONVERSE = 6; // 相反数
  public static final int EQUAL = 7;

  private boolean mLastInputIsOp;
  private double mAnswer;
  private int mLastOp;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_calculator);

    View num0Button = this.findViewById(R.id.num_0);
    num0Button.setOnClickListener(this);
    View num1Button = this.findViewById(R.id.num_1);
    num1Button.setOnClickListener(this);
    View num2Button = this.findViewById(R.id.num_2);
    num2Button.setOnClickListener(this);
    View num3Button = this.findViewById(R.id.num_3);
    num3Button.setOnClickListener(this);
    View num4Button = this.findViewById(R.id.num_4);
    num4Button.setOnClickListener(this);
    View num5Button = this.findViewById(R.id.num_5);
    num5Button.setOnClickListener(this);
    View num6Button = this.findViewById(R.id.num_6);
    num6Button.setOnClickListener(this);
    View num7Button = this.findViewById(R.id.num_7);
    num7Button.setOnClickListener(this);
    View num8Button = this.findViewById(R.id.num_8);
    num8Button.setOnClickListener(this);
    View num9Button = this.findViewById(R.id.num_9);
    num9Button.setOnClickListener(this);

    View opAddButton = this.findViewById(R.id.op_add);
    opAddButton.setOnClickListener(this);
    View opMinusButton = this.findViewById(R.id.op_minus);
    opMinusButton.setOnClickListener(this);
    View opMultiButton = this.findViewById(R.id.op_multi);
    opMultiButton.setOnClickListener(this);
    View opDivButton = this.findViewById(R.id.op_div);
    opDivButton.setOnClickListener(this);

    View opDotButton = this.findViewById(R.id.op_dot);
    opDotButton.setOnClickListener(this);
    View opEqualButton = this.findViewById(R.id.op_equal);
    opEqualButton.setOnClickListener(this);
    View opOneInXButton = this.findViewById(R.id.op_one_in_x);
    opOneInXButton.setOnClickListener(this);
    View opConverseButton = this.findViewById(R.id.op_converse);
    opConverseButton.setOnClickListener(this);

    View opClearButton = this.findViewById(R.id.op_clear);
    opClearButton.setOnClickListener(this);
    View opExitButton = this.findViewById(R.id.op_exit);
    opExitButton.setOnClickListener(this);

    TextView resultView = this.findViewById(R.id.result);
    resultView.setText("0");
    mLastInputIsOp = true;
    mAnswer = 0;
    mLastOp = ADD;
  }

  @Override
  public void onClick(View v) {
    TextView resultView = this.findViewById(R.id.result);
    StringBuffer currentText = new StringBuffer(resultView.getText());

    switch (v.getId()) {
      case R.id.num_0:
        if (mLastInputIsOp || currentText.toString().equals("0")) {
            resultView.setText("0");
        } else {
            resultView.setText(currentText.append('0'));
        }
        mLastInputIsOp = false;
        break;
      case R.id.num_1:
        if (mLastInputIsOp || currentText.toString().equals("0")) {
            resultView.setText("1");
        } else {
            resultView.setText(currentText.append('1'));
        }
        mLastInputIsOp = false;
        break;
      case R.id.num_2:
        if (mLastInputIsOp || currentText.toString().equals("0")) {
            resultView.setText("2");
        } else {
            resultView.setText(currentText.append('2'));
        }
        mLastInputIsOp = false;
        break;
      case R.id.num_3:
        if (mLastInputIsOp || currentText.toString().equals("0")) {
            resultView.setText("3");
        } else {
            resultView.setText(currentText.append('3'));
        }
        mLastInputIsOp = false;
        break;
      case R.id.num_4:
        if (mLastInputIsOp || currentText.toString().equals("0")) {
            resultView.setText("4");
        } else {
            resultView.setText(currentText.append('4'));
        }
        mLastInputIsOp = false;
        break;
      case R.id.num_5:
        if (mLastInputIsOp || currentText.toString().equals("0")) {
            resultView.setText("5");
        } else {
            resultView.setText(currentText.append('5'));
        }
        mLastInputIsOp = false;
        break;
      case R.id.num_6:
        if (mLastInputIsOp || currentText.toString().equals("0")) {
            resultView.setText("6");
        } else {
            resultView.setText(currentText.append('6'));
        }
        mLastInputIsOp = false;
        break;
      case R.id.num_7:
        if (mLastInputIsOp || currentText.toString().equals("0")) {
            resultView.setText("7");
        } else {
            resultView.setText(currentText.append('7'));
        }
        mLastInputIsOp = false;
        break;
      case R.id.num_8:
        if (mLastInputIsOp || currentText.toString().equals("0")) {
            resultView.setText("8");
        } else {
            resultView.setText(currentText.append('8'));
        }
        mLastInputIsOp = false;
        break;
      case R.id.num_9:
        if (mLastInputIsOp || currentText.toString().equals("0")) {
            resultView.setText("9");
        } else {
            resultView.setText(currentText.append('9'));
        }
        mLastInputIsOp = false;
        break;
      case R.id.op_add:
        if (mLastOp == ADD) {
            mAnswer += Double.parseDouble(currentText.toString());
        } else if (mLastOp == MINUS) {
            mAnswer -= Double.parseDouble(currentText.toString());
        } else if (mLastOp == MULTI) {
            mAnswer *= Double.parseDouble(currentText.toString());
        } else if (mLastOp == DIV) {
            mAnswer /= Double.parseDouble(currentText.toString());
        }
        if (String.valueOf(mAnswer).endsWith(".0")) {
          int length = String.valueOf(mAnswer).length();
          resultView.setText(String.valueOf(mAnswer).substring(0, length - 2));
        } else {
          resultView.setText(String.valueOf(mAnswer));
        }
        mLastOp = ADD;
        mLastInputIsOp = true;
        break;
      case R.id.op_minus:
        if (mLastOp == ADD) {
            mAnswer += Double.parseDouble(currentText.toString());
        } else if (mLastOp == MINUS) {
            mAnswer -= Double.parseDouble(currentText.toString());
        } else if (mLastOp == MULTI) {
            mAnswer *= Double.parseDouble(currentText.toString());
        } else if (mLastOp == DIV) {
            mAnswer /= Double.parseDouble(currentText.toString());
        }
        if (String.valueOf(mAnswer).endsWith(".0")) {
          int length = String.valueOf(mAnswer).length();
          resultView.setText(String.valueOf(mAnswer).substring(0, length - 2));
        } else {
          resultView.setText(String.valueOf(mAnswer));
        }
        mLastOp = MINUS;
        mLastInputIsOp = true;
        break;
      case R.id.op_multi:
        if (mLastOp == ADD) {
            mAnswer += Double.parseDouble(currentText.toString());
        } else if (mLastOp == MINUS) {
            mAnswer -= Double.parseDouble(currentText.toString());
        } else if (mLastOp == MULTI) {
            mAnswer *= Double.parseDouble(currentText.toString());
        } else if (mLastOp == DIV) {
            mAnswer /= Double.parseDouble(currentText.toString());
        }
        if (String.valueOf(mAnswer).endsWith(".0")) {
          int length = String.valueOf(mAnswer).length();
          resultView.setText(String.valueOf(mAnswer).substring(0, length - 2));
        } else {
          resultView.setText(String.valueOf(mAnswer));
        }
        mLastOp = MULTI;
        mLastInputIsOp = true;
        break;
      case R.id.op_div:
        if (mLastOp == ADD) {
            mAnswer += Double.parseDouble(currentText.toString());
        } else if (mLastOp == MINUS) {
            mAnswer -= Double.parseDouble(currentText.toString());
        } else if (mLastOp == MULTI) {
            mAnswer *= Double.parseDouble(currentText.toString());
        } else if (mLastOp == DIV) {
            mAnswer /= Double.parseDouble(currentText.toString());
        }
        if (String.valueOf(mAnswer).endsWith(".0")) {
          int length = String.valueOf(mAnswer).length();
          resultView.setText(String.valueOf(mAnswer).substring(0, length - 2));
        } else {
          resultView.setText(String.valueOf(mAnswer));
        }
        mLastOp = DIV;
        mLastInputIsOp = true;
        break;
      case R.id.op_dot:
        if (mLastInputIsOp) {
            resultView.setText("0");
        } else {
            resultView.setText(currentText.append('.'));
        }
        mLastInputIsOp = false;
        break;
      case R.id.op_equal:
        if (mLastOp == ADD) {
            mAnswer += Double.parseDouble(currentText.toString());
        } else if (mLastOp == MINUS) {
            mAnswer -= Double.parseDouble(currentText.toString());
        } else if (mLastOp == MULTI) {
            mAnswer *= Double.parseDouble(currentText.toString());
        } else if (mLastOp == DIV) {
            mAnswer /= Double.parseDouble(currentText.toString());
        }
        if (String.valueOf(mAnswer).endsWith(".0")) {
          int length = String.valueOf(mAnswer).length();
          resultView.setText(String.valueOf(mAnswer).substring(0, length - 2));
        } else {
          resultView.setText(String.valueOf(mAnswer));
        }
        mLastOp = EQUAL;
        mLastInputIsOp = false;
        break;
      case R.id.op_one_in_x:
        if (mLastInputIsOp || currentText.toString().equals("0")) {
            resultView.setText("0");
        } else {
          if (mLastOp == EQUAL) {
            mAnswer = 1 / Double.parseDouble(currentText.toString());
            if (String.valueOf(mAnswer).endsWith(".0")) {
              int length = String.valueOf(mAnswer).length();
              resultView.setText(String.valueOf(mAnswer).substring(0, length - 2));
            } else {
              resultView.setText(String.valueOf(mAnswer));
            }
          } else {
              resultView.setText(String.valueOf(1 / Double.parseDouble(currentText.toString())));
          }
        }
        mLastInputIsOp = false;
        break;
      case R.id.op_converse:
        if (mLastInputIsOp || currentText.toString().equals("0")) {
            resultView.setText("0");
        } else {
          if (mLastOp == EQUAL) {
            mAnswer = 0 - Double.parseDouble(currentText.toString());
            if (String.valueOf(mAnswer).endsWith(".0")) {
              int length = String.valueOf(mAnswer).length();
              resultView.setText(String.valueOf(mAnswer).substring(0, length - 2));
            } else {
              resultView.setText(String.valueOf(mAnswer));
            }
          } else {
              resultView.setText(String.valueOf(0 - Double.parseDouble(currentText.toString())));
          }
        }
        mLastInputIsOp = false;
        break;
      case R.id.op_clear:
        mLastInputIsOp = true;
        mAnswer = 0;
        mLastOp = ADD;
        resultView.setText("0");
        break;
      case R.id.op_exit:
        Intent intent = new Intent(CalculatorActivity.this, MainActivity.class);
        startActivity(intent);
        break;
    }

  }
}
