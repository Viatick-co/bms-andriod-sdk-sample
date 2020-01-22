package com.viatick.bmssdkandroidexample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.viatick.bmsandroidsdk.controller.ViaBmsCtrl;
import com.viatick.bmsandroidsdk.helper.BmsEnvironment;
import com.viatick.bmsandroidsdk.model.IBeacon;
import com.viatick.bmsandroidsdk.model.ViaBmsUtil;
import com.viatick.bmsandroidsdk.model.ViaZone;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViaBmsCtrl.ViaBmsCtrlDelegate {
  private final String TAG = "BMS_EXAMPLE";

  private TextView statusText;
  private Button startBtn;
  private Button stopBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    statusText = findViewById(R.id.status);
    startBtn = findViewById(R.id.startBtn);
    stopBtn = findViewById(R.id.stopBtn);

    startBtn.setEnabled(false);
    stopBtn.setEnabled(false);

    Log.d(TAG, "Hello World!");

    List<IBeacon> requestBeacons = new ArrayList<>();
    requestBeacons.add(new IBeacon("F7826DA6-4FA2-4E98-8024-BC5B71E0893E", 40, 50));
    requestBeacons.add(new IBeacon("F7826DA6-4FA2-4E98-8024-BC5B71E0893E", 1, 2));
    requestBeacons.add(new IBeacon("F7826DA6-4FA2-4E98-8024-BC5B71E0893E", 322, 119));

    ViaBmsCtrl.settings(true, true,
      true, ViaBmsUtil.MinisiteViewType.LIST, null,
      true, true,
      true,
      5, 20, requestBeacons, BmsEnvironment.DEV);

    ViaBmsCtrl.setDelegate(this);

    ViaBmsCtrl.initSdk(this, "_tonthdf8aoramakguq7e92bkqtbip8etkeo5vdaojgmnqrbnmnv");

    Log.d(TAG, "Init Called!");
  }

  @Override
  public void sdkInited(boolean inited, List<ViaZone> zones) {
    Log.d(TAG, "Sdk inited " + inited);
    if (inited) {
      ViaBmsCtrl.initCustomer("khoa_android", "+65 88268722", "khoa@viatick.com", zones);
    }
  }

  @Override
  public void customerInited(boolean inited) {
    Log.d(TAG, "Customer Inited " + inited);
    if (inited) {
      statusText.setText("On distance: ");

      startBtn.setEnabled(true);
      stopBtn.setEnabled(true);
    }
  }

  @Override
  public void checkin() {

  }

  @Override
  public void checkout() {

  }

  @Override
  public void onDistanceBeacons(List<IBeacon> beacons) {
    Log.d(TAG, "On distance");
    for (IBeacon beacon :
      beacons) {
      Log.d(TAG, "Beacon " + beacon.getKey() + " " + beacon.getDistance());
    }

    int len = beacons.size();
    statusText.setText("On distance: " + len);
  }

  public void startSDK(View view) {
    boolean sdkInited = ViaBmsCtrl.isSdkInited();
    boolean bmsRunning = ViaBmsCtrl.isBmsRunning();

    if (!bmsRunning && sdkInited) {
      Log.d(TAG, "Bms Starting");
      ViaBmsCtrl.startBmsService();
    }

  }

  public void stopSDK(View view) {
    ViaBmsCtrl.stopBmsService();
  }
}
