package info.meitime.meitime;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;

public class MyService extends Service {

    public MyService() {

    }
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    @Override
    public void onCreate() {
        super.onCreate();
        mSharedPreferences=getApplicationContext().getSharedPreferences("time",MODE_PRIVATE);
        mEditor=mSharedPreferences.edit();
        Time t=new Time();
        t.setToNow();
        Log.i("meitime","create service at "+t.format2445());
        mEditor.putLong("SCREEN_ON_TIME",t.toMillis(false));
        mEditor.commit();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(screenReceiver,intentFilter);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(screenReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private BroadcastReceiver screenReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Time t=new Time();
            if(intent.getAction().equals(Intent.ACTION_SCREEN_ON))
            {
                t.setToNow();
                Log.i("meitime","screen on at "+t.format2445());
                mEditor.putLong("SCREEN_ON_TIME",t.toMillis(false));
                mEditor.putInt("screenon_frequency",mSharedPreferences.getInt("screenon_frequency",1)+1);
                mEditor.commit();
            }
            else if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
            {
                t.setToNow();
                Log.i("meitime","screen off at "+t.format2445());
                long timepass=t.toMillis(false)-mSharedPreferences.getLong("SCREEN_ON_TIME",0);
                long newtime=mSharedPreferences.getLong("TODAY_TIME_USED",0)+timepass;
                mEditor.putLong("TODAY_TIME_USED",newtime);
                Log.i("meitime","today time used "+newtime);
                mEditor.commit();
            }
        }

    };
}