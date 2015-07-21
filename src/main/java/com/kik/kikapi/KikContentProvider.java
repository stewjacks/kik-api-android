package com.kik.kikapi;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by gabrielgrant on 15-07-01.
 */
public class KikContentProvider extends ContentProvider
{
    private static String CONTENT_URI_BASE = null;

    /**
     * Initializes KikContentProvider. This must be called before attempting to share videos to Kik.
     * @param authority The globally unique string identifying this content provider's authority. This <b>must</b>
     *            be the same authority string as declared in the Manifest.
     */
    public static void init(String authority)
    {
        CONTENT_URI_BASE = "content://" + authority + "/";
    }

    public static Uri getContentUri(String path)
    {
        if (CONTENT_URI_BASE == null) {
            throw new IllegalArgumentException("KikContentProvider init must be called before using this method");
        }
        return Uri.parse(CONTENT_URI_BASE + Uri.encode(path));
    }

    @Override
    public boolean onCreate()
    {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        return null;
    }

    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        return 0;
    }

    @Override
    public android.os.ParcelFileDescriptor openFile(android.net.Uri uri, java.lang.String mode)
            throws java.io.FileNotFoundException
    {
        File f = new File(uri.getPath());

        if (f.exists()) {
            return (ParcelFileDescriptor.open(f,
                    ParcelFileDescriptor.MODE_READ_WRITE));
        }

        throw new FileNotFoundException(uri.getPath());
    }

}
