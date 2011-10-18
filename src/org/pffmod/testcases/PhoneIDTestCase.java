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

import android.content.Context;
import android.telephony.TelephonyManager;

public class PhoneIDTestCase {

    private static final String NAME = "Read Phone state and Identity.";
    private Context mContext;
    private TelephonyManager mTel;
    private TestView mView;

    public PhoneIDTestCase(Context context) {
        mContext = context;
    }
    
    public void prepare(TestView view) {
        mView = view; 
        mView.addTestCase(NAME);
        try {
            mTel = (TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void start(TestView view) {
        try {
            mView.startTest("getSubscriberId()");
            mView.setTestState(mTel.getSubscriberId());
        }
        catch (SecurityException e) {
            mView.setTestState(e.getMessage());
        }
        try {
            mView.startTest("getDeviceId()");
            mView.setTestState(mTel.getDeviceId());
        }
        catch (SecurityException e) {
            mView.setTestState(e.getMessage());
        }
        try {
            mView.startTest("getDeviceSoftwareVersion()");
            mView.setTestState(mTel.getDeviceSoftwareVersion());
        }
        catch (SecurityException e) {
            mView.setTestState(e.getMessage());
        }
        try {
            mView.startTest("getLine1Number()");
            mView.setTestState(mTel.getLine1Number());
        }
        catch (SecurityException e) {
            mView.setTestState(e.getMessage());
        }
        try {
            mView.startTest("getSimSerialNumber()");
            mView.setTestState(mTel.getSimSerialNumber());
        }
        catch (SecurityException e) {
            mView.setTestState(e.getMessage());
        }
        try {
            mView.startTest("getVoiceMailAlphaTag()");
            mView.setTestState(mTel.getVoiceMailAlphaTag());
        }
        catch (SecurityException e) {
            mView.setTestState(e.getMessage());
        }
        try {
            mView.startTest("getVoiceMailNumber()");
            mView.setTestState(mTel.getVoiceMailNumber());
        }
        catch (SecurityException e) {
            mView.setTestState(e.getMessage());
        }
    }

    public void end(TestView view) {
        
    }
}
