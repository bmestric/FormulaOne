package hr.bmestric.formulaone.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper

fun SQLiteOpenHelper.insertTable(table: String, values: ContentValues?): Long =
    writableDatabase.insert(table, null, values)

fun SQLiteOpenHelper.deleteTable(table: String, selection: String?, selectionArgs: Array<String>?): Int =
    writableDatabase.delete(table, selection, selectionArgs)

fun SQLiteOpenHelper.updateTable(
    table: String,
    values: ContentValues?,
    selection: String?,
    selectionArgs: Array<String>?
): Int = writableDatabase.update(table, values, selection, selectionArgs)

fun SQLiteOpenHelper.queryTable(
    table: String,
    projection: Array<String>?,
    selection: String?,
    selectionArgs: Array<String>?,
    sortOrder: String?
): Cursor? = readableDatabase.query(table, projection, selection, selectionArgs, null, null, sortOrder)