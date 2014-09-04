package com.kik.kikapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by costa on 2014-08-29.
 */
public class KikPhotoMessage extends KikMessage
{
    private static int PREVIEW_IMAGE_MAX_SIZE = 400;
    private static int IMAGE_MAX_SIZE = 960;

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

    /**
     * KikPhotoMessage with a scaled preview and image from drawable resource.
     * @param context
     * @param drawable
     */
    public KikPhotoMessage(Context context, Drawable drawable)
    {
        this(context,
                KikImageUtil.generateEncodedURLForImage(KikImageUtil.generateScaledBitmap(drawable, IMAGE_MAX_SIZE, IMAGE_MAX_SIZE)),
                KikImageUtil.generateEncodedURLForImage(KikImageUtil.generateScaledBitmap(drawable, PREVIEW_IMAGE_MAX_SIZE, PREVIEW_IMAGE_MAX_SIZE)));
    }

    /**
     * KikPhotoMessage with a scaled preview and image from bitmap resource.
     * @param context
     * @param bitmap
     */
    public KikPhotoMessage(Context context, Bitmap bitmap)
    {
        this(context,
                KikImageUtil.generateEncodedURLForImage(KikImageUtil.generateScaledBitmap(bitmap, IMAGE_MAX_SIZE, IMAGE_MAX_SIZE)),
                KikImageUtil.generateEncodedURLForImage(KikImageUtil.generateScaledBitmap(bitmap, PREVIEW_IMAGE_MAX_SIZE, PREVIEW_IMAGE_MAX_SIZE)));
    }

    /**
     * KikPhotoMessage with a scaled preview and image from byte[] resource.
     * @param context
     * @param bytes
     */
    public KikPhotoMessage(Context context, byte[] bytes)
    {
        this(context,
                KikImageUtil.generateEncodedURLForImage(KikImageUtil.generateScaledBitmap(bytes, IMAGE_MAX_SIZE, IMAGE_MAX_SIZE)),
                KikImageUtil.generateEncodedURLForImage(KikImageUtil.generateScaledBitmap(bytes, PREVIEW_IMAGE_MAX_SIZE, PREVIEW_IMAGE_MAX_SIZE)));
    }

    @Override
    protected String getMessageType()
    {
        return "photo";
    }
}
