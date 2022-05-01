package edu.ucsd.cse110.zooseeker46;

import android.app.Activity;
import android.app.AlertDialog;

import java.util.Optional;

public class Utilities {
    public static void showAlert(Activity activity, String message){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        alertBuilder
                .setTitle("Alert!")
                .setMessage(message)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.cancel();
                })
                .setCancelable(true);

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    //code from  lab 4
    //safely parse number with try catch
    public static Optional<Integer> parseCount(String str) {
        try {
            int exhibitCount = Integer.parseInt(str);
            return Optional.of(exhibitCount);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
