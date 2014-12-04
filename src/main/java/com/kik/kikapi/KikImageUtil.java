package com.kik.kikapi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by costa on 2014-09-03.
 */
public class KikImageUtil
{
    /**
     * Generates a Base64 encoded data URI from the provided Drawable
     *
     * @param drawable a valid BirmapDrawable to be encoded
     * @return String representation of a Base64 encoded data URI
     */
    public static String generateEncodedUrlForImage(Drawable drawable)
    {
        if (drawable != null && drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            return generateEncodedUrlForImage(bitmap);
        }
        else {
            return null;
        }
    }

    /**
     * Generates a base64 encoded data URI from the provided Bitmap
     *
     * @param bitmap a Bitmap to be encoded and compressed
     * @return String representation of a Base64 encoded data URI
     */
    public static String generateEncodedUrlForImage(Bitmap bitmap)
    {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
            byte[] bitMapData = stream.toByteArray();
            return generateEncodedUrlForImage(bitMapData);
        }
        else {
            return null;
        }
    }

    /**
     * Generates a base64 encoded data URI from the provided byte array
     *
     * @param bitMapData a byte array representation of a jpg image
     * @return String representation of a Base64 encoded data URI
     */
    public static String generateEncodedUrlForImage(byte[] bitMapData)
    {
        if (bitMapData != null) {
            try {
                return "data:image/jpg;base64," + Base64.encodeToString(bitMapData, Base64.NO_WRAP);
            }
            catch (AssertionError assertionError) {
                return null;
            }
        }
        else {
            return null;
        }
    }

    /**
     * Generates a bitmap scaled to provided width and height
     *
     * @param drawable a valid BitmapDrawable to be scaled
     * @param width the new Bitmap's desired width
     * @param height the new Bitmap's desired height
     * @return A Bitmap scaled to the provided height and width
     */
    public static Bitmap generateScaledBitmap(Drawable drawable, int width, int height)
    {
        if (drawable != null && drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            return generateScaledBitmap(bitmap, width, height);
        }
        else {
            return null;
        }
    }

    /**
     * Generates a bitmap scaled to provided width and height
     *
     * @param bitmap a Bitmap to be scaled
     * @param width the new Bitmap's desired width
     * @param height the new Bitmap's desired height
     * @return A Bitmap scaled to the provided height and width
     */
    public static Bitmap generateScaledBitmap(Bitmap bitmap, int width, int height)
    {
        if (bitmap != null) {
            try {
                return Bitmap.createScaledBitmap(bitmap, width, height, true);
            }
            catch (IllegalArgumentException illegalArgs) {
                return null;
            }
        }
        else {
            return null;
        }
    }

    /**
     * Generates a bitmap scaled to provided width and height
     *
     * @param bitMapData a byte array representation of an image to be scaled
     * @param width the new Bitmap's desired width
     * @param height the new Bitmap's desired height
     * @return A Bitmap scaled to the provided height and width
     */
    public static Bitmap generateScaledBitmap(byte[] bitMapData, int width, int height)
    {
        if (bitMapData != null) {
            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bitMapData, 0, bitMapData.length);
                return generateScaledBitmap(bitmap, width, height);
            }
            catch (ArrayIndexOutOfBoundsException indexOutOfBounds) {
                return null;
            }
            catch (IllegalArgumentException illegalArgs) {
                return null;
            }
        }
        else {
            return null;
        }
    }
}
