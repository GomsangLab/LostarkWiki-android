package wiki.lostark.app.utils;

import android.content.Context;
import android.content.Intent;

import wiki.lostark.app.R;

public class ShareUtils {
    public static void shareText(Context context, String text){
        String shareBody = text;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "Select"));
    }
}
