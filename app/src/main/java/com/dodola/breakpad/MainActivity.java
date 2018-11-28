package com.dodola.breakpad;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import ru.ivanarh.jndcrash.NDCrash;
import ru.ivanarh.jndcrash.NDCrashError;
import ru.ivanarh.jndcrash.NDCrashUnwinder;

public class MainActivity extends Activity {

  private static boolean test;

  static {
    System.loadLibrary("dcrash");
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final RadioGroup radioGroup = findViewById(R.id.radio_group);
    TextView tv = findViewById(R.id.sample_text);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }
    findViewById(R.id.crash_btn)
        .setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.radio_breakpad) {
                  initBreakpad(getFilesDir().getAbsolutePath());
                } else if (checkedRadioButtonId == R.id.radio_ndcrash) {
                  final String reportPath =
                      getFilesDir().getAbsolutePath() + "/crash.txt"; // Example.
                  final NDCrashError error =
                      NDCrash.initializeInProcess(
                          reportPath, NDCrashUnwinder.libunwind); // 此处可以选择多个处理框架
                }
                crash();
              }
            });
  }

  @Override
  public void onRequestPermissionsResult(
      int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  public native void crash();

  public static native void initBreakpad(String path);
}
