package info.meitime.meitime;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.i("meitime","Create Service");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("meitime","Start Service");
        return START_STICKY;
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i("meitime","Destroy Service");
    }

        @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
