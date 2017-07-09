package oob.cityworld.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

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

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void checkAndLoadUrlImage(String url, Activity activity, ImageView imageView, String messageEmpty) {
        if (url.isEmpty()) {
            Utils.showToast(activity, messageEmpty);
        } else if (url.contains("http://") || url.contains("https://")){
            Picasso.with(activity).load(url).fit().into(imageView);
        } else {
            Utils.showToast(activity, messageEmpty);
        }
    }
}
