package com.dodola.breakpad;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import ru.ivanarh.jndcrash.NDCrash;
import ru.ivanarh.jndcrash.NDCrashError;
import ru.ivanarh.jndcrash.NDCrashUnwinder;

public class MainActivity extends Activity {
  private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 100;

  static {
    System.loadLibrary("dcrash");
  }

  private String reportPath;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TextView tv = findViewById(R.id.sample_text);

    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(
          this,
          new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
          WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
    } else {
      initReportPath();
    }

    final Spinner spinner = findViewById(R.id.spinner);
    ArrayAdapter<CharSequence> adapter =
        ArrayAdapter.createFromResource(
            this, R.array.crash_array, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
    spinner.setSelection(0);

    findViewById(R.id.crash_btn)
        .setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                int selectedItemPosition = spinner.getSelectedItemPosition();
                String selectItem = (String) spinner.getSelectedItem();
                if (selectedItemPosition == 0) {
                  initBreakpad(reportPath);
                  crash();
                } else {

                  NDCrashUnwinder unwinder = NDCrashUnwinder.libunwind;
                  switch (selectedItemPosition) {
                    case 1:
                      unwinder = NDCrashUnwinder.libcorkscrew;
                      break;
                    case 2:
                      unwinder = NDCrashUnwinder.libunwind;
                      break;
                    case 3:
                      unwinder = NDCrashUnwinder.libunwindstack;
                      break;
                    case 4:
                      unwinder = NDCrashUnwinder.cxxabi;
                      break;
                    case 5:
                      unwinder = NDCrashUnwinder.stackscan;
                      break;
                    default:
                      break;
                  }
                  if (TextUtils.isEmpty(reportPath)) {
                    reportPath = getFilesDir().getAbsolutePath();
                  }
                  final NDCrashError error =
                      NDCrash.initializeInProcess(
                          reportPath + "/crash_" + selectItem + ".txt", unwinder); // 此处可以选择多个处理框架
                  if (error == NDCrashError.ok) {
                    crash();
                  } else {
                    Toast.makeText(view.getContext(), "初始化失败 " + error, Toast.LENGTH_SHORT).show();
                  }
                }
              }
            });
  }

  @Override
  public void onRequestPermissionsResult(
      int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    initReportPath();
  }

  private void initReportPath() {
    File crashDumpDir = new File(Environment.getExternalStorageDirectory(), "crashDump");
    if (!crashDumpDir.exists()) {
      crashDumpDir.mkdirs();
    }
    reportPath = crashDumpDir.getAbsolutePath();
    Log.d("xxxxx", "reportPath:" + reportPath);
  }

  public native void crash();

  public static native void initBreakpad(String path);
}
