package com.kik.kikapi;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

/**
 * Created by costa on 2014-08-29.
 */
public abstract class KikMessage
{
    public static enum KikMessagePlatform {
        KIK_MESSAGE_PLATFORM_GENERIC(""),
        KIK_MESSAGE_PLATFORM_IPHONE("iphone"),
        KIK_MESSAGE_PLATFORM_ANDROID("android"),
        KIK_MESSAGE_PLATFORM_CARDS("cards");

        private String _value;

        private KikMessagePlatform(String value)
        {
            this._value = value;
        }
    }

    private static final String KIK_MESSENGER_API_SEND_URL = "kik-share://kik.com/send/";

    protected String _appName; // Required
    protected String _appPkg; // Required
    protected String _title;
    protected String _text;
    protected boolean _forwardable = true;
    protected ArrayList<String> _URLs = new ArrayList<String>();
    protected String _imageUrl;
    protected String _previewUrl;
    protected String _iconUrl;
    protected Context _context;

    /**
     * Set whether the KikMessage is forwardable. Default value is true.
     *
     * @param isForwardable <code>false</code> to disable the ability to forward the content message
     * @return              The current instance of KikMessage with the forwardable property set
     */
    public KikMessage setForwardable(boolean isForwardable)
    {
        _forwardable = isForwardable;
        return this;
    }

    protected KikMessage(Context context) throws IllegalArgumentException
    {
        if (_context == null) {
            throw new IllegalArgumentException("Context can't be null when creating a KikMessage");
        }

        _context = context;
        _appPkg = _context.getPackageName();

        final PackageManager pm = _context.getPackageManager();
        ApplicationInfo ai;
        try {
            ai = pm.getApplicationInfo(_appPkg, 0);
        }
        catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }
        _appName = (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
        Drawable icon = pm.getApplicationIcon(ai);
        _iconUrl = KikImageUtil.generateEncodedURLForImage(icon);
    }

    /**
     * Add a fallback url for a specified platform that will be displayed to the user upon opening the KikMessage
     *
     * @param fallbackURL a valid url to be displayed to the user
     * @param platform    the platform which this fallbackURL should target
     * @return            The current instance of KikMessage with the provided fallbackURL attached
     */
    public KikMessage addFallbackURL(String fallbackURL, KikMessagePlatform platform)
    {
        try {
            new URL(fallbackURL);
        }
        catch (MalformedURLException e) {
            // do nothing?
        }
        // Only insert the platform if it is defined
        String fallback = (platform != null && !"".equals(platform._value)) ? platform._value + "," : "";
        fallback += fallbackURL;
        _URLs.add(fallback);
        return this;
    }

    /**
     * @return A valid link representation of the KikMessage
     */
    protected String linkRepresentation()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(KIK_MESSENGER_API_SEND_URL);
        builder.append(getMessageType()).append("?");

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        BasicNameValuePair appNamePair = new BasicNameValuePair("app_name", _appName);
        nameValuePairs.add(appNamePair);
        BasicNameValuePair appPkgPair = new BasicNameValuePair("app_pkg", _appPkg);
        nameValuePairs.add(appPkgPair);
        if (isValueSet(_title)) {
            BasicNameValuePair titlePair = new BasicNameValuePair("title", _title);
            nameValuePairs.add(titlePair);
        }

        if (isValueSet(_text)) {
            BasicNameValuePair textPair = new BasicNameValuePair("text", _text);
            nameValuePairs.add(textPair);
        }

        BasicNameValuePair forwardablePair = new BasicNameValuePair("forwardable", _forwardable ? "1" : "0");
        nameValuePairs.add(forwardablePair);

        for (String fallbackUrl : _URLs) {
            BasicNameValuePair urlPair = new BasicNameValuePair("url", fallbackUrl);
            nameValuePairs.add(urlPair);
        }

        if (isValueSet(_imageUrl)) {
            BasicNameValuePair imagePair = new BasicNameValuePair("image_url", _imageUrl);
            nameValuePairs.add(imagePair);
        }

        if (isValueSet(_previewUrl)) {
            BasicNameValuePair previewPair = new BasicNameValuePair("preview_url", _previewUrl);
            nameValuePairs.add(previewPair);
        }

        BasicNameValuePair iconPair = new BasicNameValuePair("icon_url", _iconUrl);
        nameValuePairs.add(iconPair);

        BasicNameValuePair nativePair = new BasicNameValuePair("isNative", "1");
        nameValuePairs.add(nativePair);

        BasicNameValuePair referrerPair = new BasicNameValuePair("referer", _appPkg);
        nameValuePairs.add(referrerPair);

        String httpParams = URLEncodedUtils.format(nameValuePairs, HTTP.UTF_8);
        builder.append(httpParams);
        return builder.toString();
    }

    private boolean isValueSet(String value)
    {
        return value != null && !"".equals(value);
    }

    protected abstract String getMessageType();
}
