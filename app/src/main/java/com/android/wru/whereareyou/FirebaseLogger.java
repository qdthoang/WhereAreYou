package com.android.wru.whereareyou;

import com.android.wru.whereareyou.common.LogEntry;
import com.google.firebase.database.DatabaseReference;

/*
 * FirebaseLogger pushes user event logs to a specified path.
 * A backend Servlet instance listens to
 * the same key and keeps track of event logs.
 */
public class FirebaseLogger {
    private DatabaseReference logRef;
    private String tag;

    public FirebaseLogger(DatabaseReference firebase, String path) {
        logRef = firebase.child(path);
    }

    public void log(String tag, String message) {
        LogEntry entry = new LogEntry(tag, message);
        logRef.push().setValue(entry);
    }

}
