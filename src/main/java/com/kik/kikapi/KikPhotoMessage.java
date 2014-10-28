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
     * @param context    non-null Android context
     * @param imageUrl   a valid URL to an image or a data URI representation of an image
     * @param previewUrl a valid URL to an image or a data URI representation of an image to provide a thumbnail preview that will be displayed in your message
     */
    public KikPhotoMessage(Context context, String imageUrl, String previewUrl) throws IllegalArgumentException
    {
        super(context);
        if (imageUrl == null || previewUrl == null) {
            throw new IllegalArgumentException("The imageUrl and previewUrl must be non-null values.");
        }
        _imageUrl = imageUrl;
        _previewUrl = previewUrl;
    }

    /**
     * KikPhotoMessage with a scaled preview and image from drawable resource.
     *
     * @param context  non-null Android context
     * @param drawable a valid drawable that will be used for the image and preview for the photo message in Kik
     */
    public KikPhotoMessage(Context context, Drawable drawable)
    {
        this(context,
                KikImageUtil.generateEncodedUrlForImage(KikImageUtil.generateScaledBitmap(drawable, IMAGE_MAX_SIZE, IMAGE_MAX_SIZE)),
                KikImageUtil.generateEncodedUrlForImage(KikImageUtil.generateScaledBitmap(drawable, PREVIEW_IMAGE_MAX_SIZE, PREVIEW_IMAGE_MAX_SIZE)));
    }

    /**
     * KikPhotoMessage with a scaled preview and image from bitmap resource.
     *
     * @param context non-null Android context
     * @param bitmap  a valid bitmap that will be used for the image and preview for the photo message in Kik
     */
    public KikPhotoMessage(Context context, Bitmap bitmap)
    {
        this(context,
                KikImageUtil.generateEncodedUrlForImage(KikImageUtil.generateScaledBitmap(bitmap, IMAGE_MAX_SIZE, IMAGE_MAX_SIZE)),
                KikImageUtil.generateEncodedUrlForImage(KikImageUtil.generateScaledBitmap(bitmap, PREVIEW_IMAGE_MAX_SIZE, PREVIEW_IMAGE_MAX_SIZE)));
    }

    /**
     * KikPhotoMessage with a scaled preview and image from byte[] resource.
     *
     * @param context non-null Android context
     * @param bytes   a valid byte array representation of an image that will be used for the image and preview for the photo message in Kik
     */
    public KikPhotoMessage(Context context, byte[] bytes)
    {
        this(context,
                KikImageUtil.generateEncodedUrlForImage(KikImageUtil.generateScaledBitmap(bytes, IMAGE_MAX_SIZE, IMAGE_MAX_SIZE)),
                KikImageUtil.generateEncodedUrlForImage(KikImageUtil.generateScaledBitmap(bytes, PREVIEW_IMAGE_MAX_SIZE, PREVIEW_IMAGE_MAX_SIZE)));
    }

    @Override
    protected String getMessageType()
    {
        return "photo";
    }
}
