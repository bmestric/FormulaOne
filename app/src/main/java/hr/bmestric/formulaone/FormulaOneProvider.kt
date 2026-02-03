package hr.bmestric.formulaone

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.core.net.toUri
import hr.bmestric.formulaone.dao.Repository
import hr.bmestric.formulaone.dao.getRepository
import hr.bmestric.formulaone.domain.model.Meeting
import hr.bmestric.formulaone.domain.model.Session

private const val AUTHORITY = "hr.bmestric.formulaone.provider"
private const val PATH_SESSIONS = "sessions"
private const val PATH_MEETINGS = "meetings"
val SESSIONS_CONTENT_URI = "content://$AUTHORITY/sessions".toUri()
val MEETINGS_CONTENT_URI = "content://$AUTHORITY/meetings".toUri()
private const val SESSIONS = 1
private const val SESSION_ID = 2
private const val MEETINGS = 3
private const val MEETING_ID = 4

private val URI_MATCHER = with(UriMatcher(UriMatcher.NO_MATCH)) {
    addURI(AUTHORITY, PATH_SESSIONS, SESSIONS)
    addURI(AUTHORITY, "$PATH_SESSIONS/#", SESSION_ID)
    addURI(AUTHORITY, PATH_MEETINGS, MEETINGS)
    addURI(AUTHORITY, "$PATH_MEETINGS/#", MEETING_ID)
    this
}


class FormulaOneProvider : ContentProvider() {
    private lateinit var repository: Repository

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return when (URI_MATCHER.match(uri)) {
            SESSIONS -> {
                 repository.deleteSession(selection,selectionArgs)
            }
            SESSION_ID -> {
                val id = uri.lastPathSegment ?: return 0
                repository.deleteSession("${Session::sessionKey.name}=?", arrayOf(id))
            }
           MEETINGS -> {
               repository.deleteMeeting(selection, selectionArgs)
           }
            MEETING_ID -> {
                val id = uri.lastPathSegment ?: return 0
                repository.deleteMeeting("${Meeting::meetingKey.name}=?", arrayOf(id))
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? = null


    override fun insert(
        uri: Uri,
        values: ContentValues?
    ): Uri? {
        val id = when(URI_MATCHER.match(uri)) {
        SESSIONS -> repository.insertSession(values)
        MEETINGS -> repository.insertMeeting(values)
        else -> throw IllegalArgumentException("Unknown URI: $uri")
    }
    return when (URI_MATCHER.match(uri)) {
        SESSIONS -> ContentUris.withAppendedId(SESSIONS_CONTENT_URI, id)
        MEETINGS -> ContentUris.withAppendedId(MEETINGS_CONTENT_URI, id)
        else -> throw IllegalArgumentException("Unknown URI: $uri")
    }
}

    override fun onCreate(): Boolean {
        repository = getRepository(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
    return when (URI_MATCHER.match(uri)) {
        SESSIONS -> {
            repository.querySessions(projection, selection, selectionArgs, sortOrder)
        }
        SESSION_ID -> {
            val id = uri.lastPathSegment ?: return null
            repository.querySessions(projection, "${Session::sessionKey.name}=?", arrayOf(id), sortOrder)
        }
        MEETINGS -> {
            repository.queryMeetings(projection, selection, selectionArgs, sortOrder)
        }
        MEETING_ID -> {
            val id = uri.lastPathSegment ?: return null
            repository.queryMeetings(projection, "${Meeting::meetingKey.name}=?", arrayOf(id), sortOrder)
        }
        else -> throw IllegalArgumentException("Unknown URI: $uri")
    }
}


    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
       return when (URI_MATCHER.match(uri)) {
            SESSIONS -> {
                repository.updateSession(values,selection,selectionArgs)
            }
            SESSION_ID -> {
                val id = uri.lastPathSegment ?: return 0
                repository.updateSession(values,"${Session::sessionKey.name}=?", arrayOf(id))
            }
            MEETINGS -> {
                repository.updateMeeting(values,selection,selectionArgs)
            }
            MEETING_ID -> {
                val id = uri.lastPathSegment ?: return 0
                repository.updateMeeting(values,"${Meeting::meetingKey.name}=?", arrayOf(id))
            }
           else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }
}