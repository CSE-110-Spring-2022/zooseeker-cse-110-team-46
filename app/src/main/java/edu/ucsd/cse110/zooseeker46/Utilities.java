package edu.ucsd.cse110.zooseeker46;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import edu.ucsd.cse110.zooseeker46.directions.DirectionsActivity;
import edu.ucsd.cse110.zooseeker46.tracking.RecalculateDirections;

public class Utilities {
    public static void showAlert(Activity activity, String message){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        alertBuilder
                .setTitle("Alert")
                .setMessage(message)
                .setPositiveButton("Replan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RecalculateDirections r = new RecalculateDirections();
                        r.newFinalPath();
                        r.newListIDs();
                        activity.finish();
                    }
                } )
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }
}
