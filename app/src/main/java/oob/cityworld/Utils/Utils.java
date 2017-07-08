package oob.cityworld.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class Utils {

    public static void showAlertForRemovingCity(Context context,
                                                String title,
                                                String message,
                                                String positiveActionText,
                                                DialogInterface.OnClickListener positiveCallback,
                                                String negativeActionText,
                                                DialogInterface.OnClickListener negativeCallback) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveActionText, positiveCallback)
                .setNegativeButton(negativeActionText, negativeCallback)
                .show();
    }
}
