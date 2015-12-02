package com.kik.kikapi;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.List;

/**
 * Created by costa on 2014-08-29.
 */
public class KikClient
{
    private static final String KIK_MESSENGER_PLAY_STORE_URL = "market://details?id=kik.android";
    private static final String KIK_MESSENGER_PLAY_STORE_WEB_URL = "market://details?id=kik.android";
    private static final String KIK_MESSENGER_API_BACK_URL = "kik-share://kik.com/back";
    private static final String KIK_MESSENGER_API_PROFILE_URL = "kik-share://kik.com/u/";
    private static final KikClient _kikClient = new KikClient();

    private KikClient()
    {
    }

    public static KikClient getInstance()
    {
        return _kikClient;
    }

    /**
     * Kik API call to launch or return back to Kik
     *
     * @param context a valid Android context
     */
    public void backToKik(Context context)
    {
        openKikUrl(context, KIK_MESSENGER_API_BACK_URL);
    }

    /**
     * Kik API call to open a specified users profile
     *
     * @param context   a valid Android context
     * @param username  the Kik username of the profile you wish to open
     */
    public void openProfileForKikUsername(Context context, String username)
    {
        openKikUrl(context, KIK_MESSENGER_API_PROFILE_URL + username);
    }

    /**
     * Kik API call to send a constructed KikMessage
     *
     * @param context a valid Android context
     * @param message the constructed KikMessage you wish to send through Kik
     */
    public void sendKikMessage(Context context, KikMessage message)
    {
        openKikUrl(context, message.linkRepresentation());
    }

    private void openKikUrl(Context context, String url)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(url));

        if (canLaunchIntent(context, intent)) {
            context.startActivity(intent);
        }
        else {
            showKikInPlayStore(context);
        }
    }

    private boolean canLaunchIntent(Context context, Intent intent)
    {
        PackageManager manager = context.getPackageManager();
        List<ResolveInfo> infos = manager.queryIntentActivities(intent, 0);
        return infos.size() > 0;
    }

    private void showKikInPlayStore(Context context)
    {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(KIK_MESSENGER_PLAY_STORE_URL)));
        }
        catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(KIK_MESSENGER_PLAY_STORE_WEB_URL)));
        }
    }
}
