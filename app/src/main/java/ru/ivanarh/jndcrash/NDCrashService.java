package ru.ivanarh.jndcrash;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.CallSuper;

/**
 * Service for out-of-process crash handling daemon. Should be run from a separate process.
 */
public class NDCrashService extends Service implements NDCrash.OnCrashCallback
{
    /**
     * Log tag.
     */
    private static final String TAG = "JNDCRASH";

    /**
     * Indicates if a daemon was started.
     */
    private static boolean mDaemonStarted = false;

    /**
     * Key for report file in arguments.
     */
    public static final String EXTRA_REPORT_FILE = "report_file";

    /**
     * Key for unwinder in arguments. Ordinal value is saved as integer.
     */
    public static final String EXTRA_UNWINDER = "unwinder";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!mDaemonStarted && intent != null) {
            mDaemonStarted = true;
            final NDCrashUnwinder unwinder = NDCrashUnwinder.values()[intent.getIntExtra(EXTRA_UNWINDER, NDCrashUnwinder.libunwind.ordinal())];
            final String reportPath = intent.getStringExtra(EXTRA_REPORT_FILE);
            final NDCrashError initResult = NDCrash.startOutOfProcessDaemon(this, reportPath, unwinder, this);
            if (initResult != NDCrashError.ok) {
                Log.e(TAG, "Couldn't start NDCrash out-of-process daemon with unwinder: " + unwinder + ", error: " + initResult);
            } else {
                Log.i(TAG, "Out-of-process unwinding daemon is started with unwinder: " + unwinder + " report path: " +
                        (reportPath != null ? reportPath : "null"));
                onDaemonStart(unwinder, reportPath, initResult);
            }
        }
        return Service.START_STICKY;
    }

    @Override @CallSuper
    public void onDestroy() {
        if (mDaemonStarted) {
            mDaemonStarted = false;
            final boolean stoppedSuccessfully = NDCrash.stopOutOfProcessDaemon();
            Log.i(TAG, "Out-of-process daemon " + (stoppedSuccessfully ? "is successfully stopped." : "failed to stop."));
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Service doesn't support to be bound.
        return null;
    }

    @Override
    public void onCrash(String reportPath) {
    }

    /**
     * Called on daemon start attempt, both on success and failed.
     *
     * @param unwinder Unwinder that is used.
     * @param reportPath Path to crash report file.
     * @param result Start result.
     */
    protected void onDaemonStart(NDCrashUnwinder unwinder, String reportPath, NDCrashError result) {
    }
}
