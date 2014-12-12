package com.mobica.mawa.fiszki.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.mobica.mawa.fiszki.R;

/**
 * Created by mawa on 2014-11-23.
 */
public class AlertHelper {


    public static void showError(Context context, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.error))
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showInfo(Context context, String title, String message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
