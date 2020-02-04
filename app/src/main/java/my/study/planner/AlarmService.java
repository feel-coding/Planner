package my.study.planner;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.ConditionVariable;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class AlarmService extends Service {
    private static int NOTIFY_ID = R.layout.notify_row;
    private NotificationManager notificationManager;
    private ConditionVariable condition;
    private ArrayList<String> spellList;
    private Random random;
    private Cursor wordCursor;
    private Intent intent;
    private PendingIntent contentIntent = null;
    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        spellList = new ArrayList<>();
        random = new Random();
        intent = new Intent(this, MainActivity.class);
        Thread notifyThread = new Thread(null, task, "Notify Service");
        condition = new ConditionVariable(false);
        notifyThread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notificationManager.cancelAll();
        condition.open();
    }
    private Runnable task = new Runnable() {
        @Override
        public void run() {
            while(true) {
                if(!condition.block(50000))
                    showNotification();
            }
            //condition.block(5000);
            //AlarmService.this.stopSelf();
        }
    };
    public void showNotification(){
        Notification notification = new Notification(R.drawable.chorong, "sldkjfslkfj", System.currentTimeMillis());
        if(contentIntent != null) contentIntent.cancel();
        contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
        notificationManager.notify(NOTIFY_ID, notification);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    private final IBinder mBinder = new Binder(){
        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }
    };
}
