package com.assignment.furniturestore;

import android.content.Context;
import android.content.Intent;

public class Launch {

    public static void openActivity(Context context, Class<?> activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }
}
