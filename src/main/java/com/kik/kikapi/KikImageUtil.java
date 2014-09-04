package com.kik.kikapi;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

/**
 * Created by costa on 2014-09-03.
 */
public class KikImageUtil
{
    /**
     * 
     * @param drawable
     * @return
     */
    public static String generateEncodedURLForImage(Drawable drawable)
    {
        if (drawable != null && drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            return generateEncodedURLForImage(bitmap);
        }
        else {
            return null;
        }
    }

    /**
     * 
     * @param bitmap
     * @return
     */
    public static String generateEncodedURLForImage(Bitmap bitmap)
    {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
            byte[] bitMapData = stream.toByteArray();
            return generateEncodedURLForImage(bitMapData);
        }
        else {
            return null;
        }
    }

    /**
     * 
     * @param bitMapData
     * @return
     */
    public static String generateEncodedURLForImage(byte[] bitMapData)
    {
        if (bitMapData != null) {
            try {
                return "data:image/jpg;base64," + Base64.encodeToString(bitMapData, Base64.URL_SAFE);
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
     * @param drawable
     * @param width
     * @param height
     * @return
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
     * @param bitmap
     * @param width
     * @param height
     * @return
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
     * @param bitMapData
     * @param width
     * @param height
     * @return
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
