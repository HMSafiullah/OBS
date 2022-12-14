package com.mr.oldbookstore.Utils;

import android.content.Context;
import android.content.Intent;

public class Utils {

    public static void intentWithClear(Context fromActivity, Class toActivity) {
        Intent i = new Intent(fromActivity, toActivity);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        fromActivity.startActivity(i);
    }
}
