/*
 *   Copyright (C) 2010-2011 The pffmod Project
 *
 *    pffmod is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, version 3 of the License.
 *
 *    pffmod is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with pffmod.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.pffmod.testcases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.pffmod.demo.views.TestView;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.DropBoxManager;
import android.provider.ContactsContract;

public class ReadContactsTestCase {

    private static final String NAME = "Read Logs";

    private Context mContext;

    private TestView mView;

    private String mSeparator;

    public ReadContactsTestCase(Context context) {
        mContext = context;
    }

    public void prepare(TestView view) {
        mView = view;
        mView.addTestCase(NAME);
        mSeparator = System.getProperty("line.separator"); //$NON-NLS-1$
    }

    public void start(TestView view) {
        try {
            mView.startTest("Read contacts.");
            Uri contactUri = ContactsContract.Contacts.CONTENT_URI;
            String[] PROJECTION = new String[] {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME,
            };

            Cursor contacts = ((Activity)mContext).managedQuery(contactUri, PROJECTION, null, null, null);
            contacts.moveToFirst();
            StringBuilder sb = new StringBuilder();
            do {
                int nameIndex = contacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                sb.append("\n" + contacts.getString(nameIndex));
            } while (contacts.moveToNext());
            mView.setTestState(sb);
        } catch (SecurityException e) {
            e.printStackTrace();
            mView.setTestState(e.getMessage());
        }
    }

    public void end(TestView view) {

    }

}
