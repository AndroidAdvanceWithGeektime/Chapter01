package com.dodola.breakpad;

import java.io.File;

import android.app.Application;
import android.content.Context;

import com.sample.breakpad.BreakpadInit;

public class SampleApplication extends Application {
    public static File breakPadPath;
    @Override
    protected void attachBaseContext(final Context base) {
        super.attachBaseContext(base);
        breakPadPath = new File(getFilesDir(), "crashDump");
        BreakpadInit.initBreakpad(breakPadPath.getAbsolutePath());
    }
}
