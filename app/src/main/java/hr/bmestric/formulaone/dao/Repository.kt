package hr.bmestric.formulaone.dao

import android.content.ContentValues
import android.database.Cursor

interface Repository {
    fun insertSession(
        values: ContentValues?
    ): Long
    fun querySessions(
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor?

    fun updateSession(
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int

    fun deleteSession(
        selection: String?,
        selectionArgs: Array<String>?
    ): Int
    fun insertMeeting(
        values: ContentValues?
    ): Long
    fun queryMeetings(
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor?
    fun updateMeeting(
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int
    fun deleteMeeting(
        selection: String?,
        selectionArgs: Array<String>?
    ): Int
}