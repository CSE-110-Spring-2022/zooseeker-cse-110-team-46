package edu.ucsd.cse110.zooseeker46;

import android.app.Activity;
import android.app.AlertDialog;

public class Utilities {
    public static void showAlert(Activity activity, String message){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        alertBuilder
                .setTitle("Alert")
                .setMessage(message)
                .setPositiveButton("Replan", (dialog, id) -> {
                    dialog.cancel();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }
}
