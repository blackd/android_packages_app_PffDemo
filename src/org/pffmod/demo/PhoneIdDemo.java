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
package org.pffmod.demo;

import org.pffmod.demo.views.TestView;
import org.pffmod.testcases.BrowserBookmarksTestCase;
import org.pffmod.testcases.PhoneIDTestCase;
import org.pffmod.testcases.ReadContactsTestCase;
import org.pffmod.testcases.ReadLogsTestCase;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class PhoneIdDemo extends Activity {
    private TestView mTestView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTestView = new TestView(this);
        mTestView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        setContentView((View)mTestView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTestView.removeAllTestCases();
        final PhoneIDTestCase pt = new PhoneIDTestCase(this);
        this.runOnUiThread(new Runnable() {
            
            @Override
            public void run() {
                try {
                    pt.prepare(mTestView);
                    pt.start(mTestView);
                    pt.end(mTestView);
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
        final ReadContactsTestCase rct = new ReadContactsTestCase(this);
        this.runOnUiThread(new Runnable() {
            
            @Override
            public void run() {
                try {
                    rct.prepare(mTestView);
                    rct.start(mTestView);
                    rct.end(mTestView);
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });

        final BrowserBookmarksTestCase rlt = new BrowserBookmarksTestCase(this);
        this.runOnUiThread(new Runnable() {
            
            @Override
            public void run() {
                try {
                    rlt.prepare(mTestView);
                    rlt.start(mTestView);
                    rlt.end(mTestView);
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
}