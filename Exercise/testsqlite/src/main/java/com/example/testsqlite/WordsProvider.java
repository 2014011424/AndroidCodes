package com.example.testsqlite;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class WordsProvider extends ContentProvider {

    private static final int MULTIPLE_WORDS = 1;
    private static final int SINGLE_WORD = 2;

    private WordsDBHelper dbHelper = null;
    private SQLiteDatabase db = null;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        uriMatcher.addURI(Words.AUTHORITY, Words.Word.PATH_MULTIPLE, MULTIPLE_WORDS);
        uriMatcher.addURI(Words.AUTHORITY, Words.Word.PATH_SINGLE, SINGLE_WORD);
    }

    public WordsProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int count = 0;
        switch (uriMatcher.match(uri)){
            case MULTIPLE_WORDS:
                count = db.delete(Words.Word.TABLE_NAME, selection, selectionArgs);
                break;
            case SINGLE_WORD:
                String whereArgs = Words.Word._ID + "=" + uri.getPathSegments().get(1);
                count = db.delete(Words.Word.TABLE_NAME, whereArgs, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unkonwn Uri:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch(uriMatcher.match(uri)){
            case MULTIPLE_WORDS:
                return Words.Word.MINE_TYPE_MULTIPLE;
            case SINGLE_WORD:
                return Words.Word.MINE_TYPE_SINGLE;
            default:
                throw new IllegalArgumentException("Unkonwn Uri:" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        long id = 0;
        id = db.insert(Words.Word.TABLE_NAME, null, values);
        if(id > 0){
            //在多路径uri后添加插入的id作为单路径uri
            Uri newUri = ContentUris.withAppendedId(Words.Word.CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(newUri,null);
            return newUri;
        }
        throw new SQLException("Fail to insert row into " + uri);
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbHelper = new WordsDBHelper(this.getContext());
        db = dbHelper.getReadableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(Words.Word.TABLE_NAME);
        switch (uriMatcher.match(uri)){
            case MULTIPLE_WORDS:
                return db.query(Words.Word.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
            case SINGLE_WORD:
                qb.appendWhere(Words.Word._ID + "=" + uri.getPathSegments().get(1));
                return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            default:
                throw new IllegalArgumentException("Unkonwn Uri:" + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        int count = 0;
        switch(uriMatcher.match(uri)){
            case MULTIPLE_WORDS:
                count = db.update(Words.Word.TABLE_NAME, values, selection, selectionArgs);
                break;
            case SINGLE_WORD:
                String whereArgs = Words.Word._ID + "=" + uri.getPathSegments().get(1);
                count = db.update(Words.Word.TABLE_NAME, values, whereArgs, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unkonwn Uri:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }
}
