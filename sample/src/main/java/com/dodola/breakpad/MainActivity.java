package com.dodola.breakpad;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
  static {
    System.loadLibrary("crash-lib");
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.crash_btn)
        .setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                crash();
              }
            });
  }

  public native void crash();
}
