package com.example.testsqlite;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by LiDafei on 2017/9/4.
 */

public class Words {

    public static final String AUTHORITY = "com.example.wordsprovider";

    public Words(){

    }

    public static abstract class Word implements BaseColumns{
        public static final String TABLE_NAME = "words";
        public static final String COLUMN_NAME_WORD = "word";
        public static final String COLUMN_NAME_MEANING = "meaning";
        public static final String COLUMN_NAME_SAMPLE = "sample";

        //定义MIME类型
        public static final String MIME_DIR_PREFIX = "vnd.android.cursor.dir";//用于多行
        public static final String MIME_ITEM_PREFIX = "vnd.android.cursor.item";//用于单行
        public static final String MIME_ITEM = "vnd.example.word";

        public static final String MINE_TYPE_SINGLE = MIME_ITEM_PREFIX + "/" + MIME_ITEM;
        public static final String MINE_TYPE_MULTIPLE = MIME_DIR_PREFIX + "/" + MIME_ITEM;


        public static final String PATH_SINGLE = "word/#";
        public static final String PATH_MULTIPLE = "word";

        //定义ContentURI
        public static final String CONTENT_URI_STRING = "content://" + AUTHORITY +
                "/" + PATH_MULTIPLE;
        public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING);
    }
}
