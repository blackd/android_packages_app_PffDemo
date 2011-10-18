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

import org.pffmod.demo.views.TestView;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.Browser;
import android.telephony.TelephonyManager;

public class BrowserBookmarksTestCase {

    private static final String NAME = "Read Browser Bookmarks.";
    private Context mContext;
    private TestView mView;

    public BrowserBookmarksTestCase(Context context) {
        mContext = context;
    }
    
    public void prepare(TestView view) {
        mView = view; 
        mView.addTestCase(NAME);
    }

    public void start(TestView view) {
        try {
            mView.startTest("Bookmarks");
            
            String[] projection = new String[] {
                    Browser.BookmarkColumns.TITLE
                    , Browser.BookmarkColumns.URL
                };
                Cursor mCur = ((Activity)mContext).managedQuery(android.provider.Browser.BOOKMARKS_URI,
                    projection, null, null, null
                    );
                mCur.moveToFirst();
                int titleIdx = mCur.getColumnIndex(Browser.BookmarkColumns.TITLE);
                int urlIdx = mCur.getColumnIndex(Browser.BookmarkColumns.URL);
                StringBuilder sb = new StringBuilder();
                while (mCur.isAfterLast() == false) {
                    sb.append("\n" + mCur.getString(titleIdx));
                    sb.append("\n" + mCur.getString(urlIdx));
                    mCur.moveToNext();
                }            
            mView.setTestState(sb);
        }
        catch (SecurityException e) {
            e.printStackTrace();
            mView.setTestState(e.getMessage());
        }
    }

    public void end(TestView view) {
        
    }
}
