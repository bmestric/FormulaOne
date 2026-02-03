package hr.bmestric.formulaone.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import hr.bmestric.formulaone.domain.model.Meeting
import hr.bmestric.formulaone.domain.model.Session

private const val DATABASE_NAME = "formulaone.db"
private const val DATABASE_VERSION = 1
private const val SESSIONS_TABLE = "sessions"
private const val MEETINGS_TABLE = "meetings"
private val CREATE_SESSIONS_TABLE = """
    CREATE TABLE $SESSIONS_TABLE (
        ${Session::sessionKey.name} INTEGER PRIMARY KEY,
        ${Session::sessionType.name} TEXT NOT NULL,
        ${Session::name.name} TEXT NOT NULL,
        ${Session::location.name} TEXT NOT NULL,
        ${Session::country.name} TEXT NOT NULL,
        ${Session::circuit.name} TEXT NOT NULL,
        ${Session::dateStart.name} TEXT NOT NULL,
        ${Session::dateEnd.name} TEXT NOT NULL,
        ${Session::year.name} INTEGER NOT NULL,
        ${Session::meetingKey.name} INTEGER NOT NULL
    )
""".trimIndent()
private val CREATE_MEETINGS_TABLE = """
    CREATE TABLE $MEETINGS_TABLE (
        ${Meeting::meetingKey.name} INTEGER PRIMARY KEY,
        ${Meeting::circuitInfoUrl.name} TEXT NOT NULL,
        ${Meeting::circuitImage.name} TEXT NOT NULL
    )
""".trimIndent()
private const val DROP_SESSIONS_TABLE = "DROP TABLE IF EXISTS $SESSIONS_TABLE"
private const val DROP_MEETINGS_TABLE = "DROP TABLE IF EXISTS $MEETINGS_TABLE"
class DBRepository(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION),
    Repository {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_SESSIONS_TABLE)
        db?.execSQL(CREATE_MEETINGS_TABLE)

        Log.d("DB", "Database CREATED!")
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL(DROP_SESSIONS_TABLE)
        db?.execSQL(DROP_MEETINGS_TABLE)
        onCreate(db)
    }

    override fun insertSession(values: ContentValues?): Long = insertTable(SESSIONS_TABLE, values)

    override fun querySessions(
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? = queryTable(SESSIONS_TABLE, projection, selection, selectionArgs, sortOrder)

    override fun updateSession(
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int = updateTable(SESSIONS_TABLE, values, selection, selectionArgs)

    override fun deleteSession(
        selection: String?,
        selectionArgs: Array<String>?
    ): Int = deleteTable(SESSIONS_TABLE, selection, selectionArgs)

    override fun insertMeeting(values: ContentValues?): Long = insertTable(MEETINGS_TABLE, values)

    override fun queryMeetings(
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? = queryTable(MEETINGS_TABLE, projection, selection, selectionArgs, sortOrder)

    override fun updateMeeting(
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int = updateTable(MEETINGS_TABLE, values, selection, selectionArgs)

    override fun deleteMeeting(
        selection: String?,
        selectionArgs: Array<String>?
    ): Int = deleteTable(MEETINGS_TABLE, selection, selectionArgs)
}