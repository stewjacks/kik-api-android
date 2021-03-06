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
     * @param context    non-null Android context
     * @param title      the title of the KikMessage
     * @param text       the description text for the KikMessage
     * @param contentUrl a valid URL that will be loaded when the user opens the KikMessage
     * @param previewUrl a valid URL to an image or a data URI representation of an image to provide a thumbnail preview that will be displayed in your message
     *
     * @throws java.lang.IllegalArgumentException if both of the provided title and text, or the context are null
     */
    public KikArticleMessage(Context context, String title, String text, String contentUrl, String previewUrl) throws IllegalArgumentException
    {
        super(context);
        if (title == null && text == null) {
            throw new IllegalArgumentException("Title or text must be non-null.");
        }
        _title = title;
        _text = text;
        _previewUrl = previewUrl;
        addFallbackUrl(contentUrl, null);
    }

    @Override
    protected String getMessageType()
    {
        return "article";
    }
}
