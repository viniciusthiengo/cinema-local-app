package br.com.thiengo.cinemalocalapp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.calldorado.Calldorado;

/**
 * Created by viniciusthiengo on 21/03/17.
 */

public class DynamicReEngagementReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("log", "onReceive()");

        if( intent.getAction().equalsIgnoreCase("com.calldorado.android.intent.DYNAMIC_RE_ENGAGEMENT_SHOWN") ){
            String fieldName = intent.getExtras().getString("reEngagementName");

            Log.i("log", "fieldName: "+fieldName);
            Calldorado.ReEngagementField field = new Calldorado
                .ReEngagementField(
                    fieldName,
                    "https://play.google.com/store/apps/details?id=br.thiengocalopsita&hl=pt_BR",
                    "Thiengo Calopsita 2" );
            Calldorado.setupDynamicReEngagementField(context, field);
        }
    }
}
