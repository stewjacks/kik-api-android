package com.kik.kikapi;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

/**
 * Created by costa on 2014-09-03.
 */
public class KikImageUtil
{

    // TODO: add resizing?
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

    public static String generateEncodedURLForImage(Bitmap bitmap)
    {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitMapData = stream.toByteArray();
            return generateEncodedURLForImage(bitMapData);
        }
        else {
            return null;
        }
    }

    public static String generateEncodedURLForImage(byte[] bitMapData)
    {
        if (bitMapData != null) {
            return "data:image/png;base64," + Base64.encodeToString(bitMapData, Base64.URL_SAFE);
        }
        else {
            return null;
        }
    }
}
