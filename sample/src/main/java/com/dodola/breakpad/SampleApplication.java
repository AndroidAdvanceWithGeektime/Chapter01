package com.dodola.breakpad;

import android.app.Application;
import android.content.Context;

import com.sample.breakpad.BreakpadInit;

import java.io.File;

public class SampleApplication extends Application {
  public static File breakPadPath;

  @Override
  protected void attachBaseContext(final Context base) {
    super.attachBaseContext(base);
    breakPadPath = new File(getFilesDir(), "crashDump");
    if (!breakPadPath.exists()) {
      breakPadPath.mkdirs();
    }
    BreakpadInit.initBreakpad(breakPadPath.getAbsolutePath());
  }
}
