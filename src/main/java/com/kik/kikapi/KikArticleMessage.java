package com.kik.kikapi;

import android.content.Context;

/**
 * Created by costa on 2014-08-29.
 */
public class KikArticleMessage extends KikMessage
{

    /**
     * One of title or text is required for creating a KikArticleMessage.
     *
     * @param title The title of the KikMessage
     * @param text The description text for the KikMessage
     * @param contentURL A valid URL that will be loaded when the user opens the KikMessage
     * @param previewURL
     */
    public KikArticleMessage(Context context, String title, String text, String contentURL, String previewURL)
    {
        super(context);
        if (title == null && text == null) {
            throw new IllegalArgumentException("Title or text must be non-null.");
        }
        _title = title;
        _text = text;
        _previewUrl = previewURL;
        addFallbackURL(contentURL, null);
    }

    @Override
    protected String getMessageType()
    {
        return "article";
    }
}
