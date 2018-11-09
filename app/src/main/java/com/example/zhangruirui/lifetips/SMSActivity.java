package com.example.zhangruirui.lifetips;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zhangruirui.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfcon
 * Blog：http://blog.csdn.net/u011489043
 * Date：11/09/18
 */
public class SMSActivity extends AppCompatActivity {

  @BindView(R.id.sender)
  TextView mFromNumber;

  @BindView(R.id.content)
  TextView mFromContent;

  @BindView(R.id.to)
  EditText mToNumber;

  @BindView(R.id.msg_input)
  EditText mToContent;

  private MessageReceiver mMessageReceiver;

  private SendStatusReceiver mSendStatusReceiver;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sms);
    ButterKnife.bind(this);
    final IntentFilter receiverFilter = new IntentFilter();
    final IntentFilter sendFilter = new IntentFilter();
    receiverFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
    sendFilter.addAction("SENT_SMS_ACTION");
    mMessageReceiver = new MessageReceiver();
    mSendStatusReceiver = new SendStatusReceiver();
    registerReceiver(mMessageReceiver, receiverFilter);
    registerReceiver(mSendStatusReceiver, sendFilter);
  }

  @OnClick(R.id.send)
  public void onClick() {
    final SmsManager smsManager = SmsManager.getDefault();
    final Intent sentIntent = new Intent("SENT_SMS_ACTION");
    final PendingIntent pi = PendingIntent.getBroadcast(SMSActivity.this,
        0, sentIntent, 0);
    String destinationAddress = mToNumber.getText().toString();
    String text = mToContent.getText().toString();
    if (TextUtils.isEmpty(destinationAddress)) {
      ToastUtil.showToast(SMSActivity.this, "Phone Number is Empty!");
    } else if (TextUtils.isEmpty(text)) {
      ToastUtil.showToast(SMSActivity.this, "Content is Empty!");
    } else {
      smsManager.sendTextMessage(destinationAddress, null,
          text, pi, null);
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unregisterReceiver(mMessageReceiver);
    unregisterReceiver(mSendStatusReceiver);
  }

  class MessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      Bundle bundle = intent.getExtras();
      Object[] pdus = new Object[0];
      if (bundle != null) {
        pdus = (Object[]) bundle.get("pdus");
      }
      SmsMessage[] messages = pdus != null ? new SmsMessage[pdus.length] : new SmsMessage[0];
      for (int i = 0; i < messages.length; i++) {
        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
      }
      String address = messages[0].getOriginatingAddress();
      StringBuilder fullMessage = new StringBuilder();
      for (SmsMessage message : messages) {
        fullMessage.append(message.getMessageBody());
      }
      mFromNumber.setText(address);
      mFromContent.setText(fullMessage.toString());
    }
  }

  class SendStatusReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      if (getResultCode() == RESULT_OK) {
        ToastUtil.showToast(SMSActivity.this, "Send succeeded");
      } else {
        ToastUtil.showToast(SMSActivity.this, "Send failed");
      }
    }
  }
}
