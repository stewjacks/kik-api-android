package com.kik.kikapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by costa on 2014-08-29.
 */
public class KikPhotoMessage extends KikMessage
{
    /**
     * A KikPhotoMessage requires an image URL and preview URL. Use KikImageUtil.createImageURL to
     * ensure a valid image and preview URL are provided.
     *
     * @param context
     * @param imageURL A valid URL to an image or a data uri representation of an image
     * @param previewURL A valid URL to an image or a data uri representation of an image
     */
    public KikPhotoMessage(Context context, String imageURL, String previewURL)
    {
        super(context);
        if (imageURL == null || previewURL == null) {
            throw new IllegalArgumentException("The imageURL and previewURL must both be non-null values.");
        }
        _imageUrl = imageURL;
        _previewUrl = previewURL;
    }

    public KikPhotoMessage(Context context, Drawable drawable)
    {
        this(context, KikImageUtil.generateEncodedURLForImage(drawable), KikImageUtil.generateEncodedURLForImage(drawable));
    }

    public KikPhotoMessage(Context context, Bitmap bitmap)
    {
        this(context, KikImageUtil.generateEncodedURLForImage(bitmap), KikImageUtil.generateEncodedURLForImage(bitmap));
    }

    public KikPhotoMessage(Context context, byte[] bytes)
    {
        this(context, KikImageUtil.generateEncodedURLForImage(bytes), KikImageUtil.generateEncodedURLForImage(bytes));
    }

    @Override
    protected String getMessageType()
    {
        return "photo";
    }
}
