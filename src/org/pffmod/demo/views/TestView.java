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
package org.pffmod.demo.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.pffmod.demo.R;

public class TestView extends LinearLayout implements View.OnClickListener {

    private final LayoutInflater mInflater;

    private View mCurrentTestcase;

    private View mCurrentTest;

    private LinearLayout mLayout;

    public TestView(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        View v = mInflater.inflate(R.layout.main, null);
        mLayout = (LinearLayout) v.findViewById(R.id.main_layout);
        addView(v);
        v.setVisibility(VISIBLE);
        mLayout.setVisibility(VISIBLE);
    }

    public void addTestCase(CharSequence name) {
        mCurrentTestcase = mInflater.inflate(R.layout.testcase_view, null);
        TextView nameView = (TextView) mCurrentTestcase.findViewById(R.id.testcase_name);
        nameView.setText(name);
        mLayout.addView(mCurrentTestcase);
        nameView.setVisibility(VISIBLE);
    }

    public void startTest(CharSequence name) {
        mCurrentTest = mInflater.inflate(R.layout.test_view, null);
        View testIcon = mCurrentTest.findViewById(R.id.test_icon);
        testIcon.setVisibility(GONE);
        TextView testName = (TextView) mCurrentTest.findViewById(R.id.test_name);
        testName.setText(name);
        LinearLayout ll = (LinearLayout)mCurrentTestcase.findViewById(R.id.tests);
        ll.addView(mCurrentTest);
        ll.setVisibility(VISIBLE);
        testName.setVisibility(VISIBLE);
        mCurrentTest.setVisibility(VISIBLE);
    }

    public void setTestState(CharSequence state) {
        TextView testState = (TextView) mCurrentTest.findViewById(R.id.test_state);
        testState.setVisibility(VISIBLE);
        testState.setText(state);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
    }

    public void removeAllTestCases() {
        mLayout.removeAllViewsInLayout();
    }

}
