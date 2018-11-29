package com.dodola.breakpad;

import java.io.File;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class MainActivity extends Activity {
  private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 100;

  static {
    System.loadLibrary("crash-lib");
  }

  private File externalReportPath;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(
          this,
          new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
          WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
    } else {
      initExternalReportPath();
    }

    findViewById(R.id.crash_btn)
        .setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  crash();
                  // copy core dump to sdcard
              }
            });
  }

  @Override
  public void onRequestPermissionsResult(
      int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    initExternalReportPath();
  }

  private void initExternalReportPath() {
    externalReportPath = new File(Environment.getExternalStorageDirectory(), "crashDump");
    if (!externalReportPath.exists()) {
      externalReportPath.mkdirs();
    }
    Log.d("xxxxx", "reportPath:" + externalReportPath);
  }

  public native void crash();
}
