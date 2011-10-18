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

import android.content.Context;
import android.os.AsyncTask;
import android.os.DropBoxManager;

public class ReadLogsTestCase {

    private static final String NAME = "Read Logs";
    private Context mContext;
    private DropBoxManager mDropBox;
    private TestView mView;
    private CollectLogTask mCollectLogTask;
    private String mSeparator;

    public ReadLogsTestCase(Context context) {
        mContext = context;
    }
    
    public void prepare(TestView view) {
        mView = view; 
        mView.addTestCase(NAME);
        mSeparator = System.getProperty("line.separator"); //$NON-NLS-1$
        try {
            mDropBox = (DropBoxManager)mContext.getSystemService(Context.DROPBOX_SERVICE);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void start(TestView view) {
        try {
            mView.startTest("DropBoxManager.getNextEntry");
            DropBoxManager.Entry e = mDropBox.getNextEntry(null, 0);
            if (e != null) {
                String tag = e.getTag();
                String text = e.getText(20);
                mView.setTestState(tag + " -> " + text);
            }
            else {
                mView.setTestState("null");
            }
        }
        catch (SecurityException e) {
            e.printStackTrace();
            mView.setTestState(e.getMessage());
        }
        try {
            mView.startTest("execute logcat");
            collectAndSendLog();
        }
        catch (SecurityException e) {
            e.printStackTrace();
            mView.setTestState(e.getMessage());
        }
        
    }

    public void end(TestView view) {
        
    }
    
    
    void collectAndSendLog() {
        /*Usage: logcat [options] [filterspecs]
        options include:
          -s              Set default filter to silent.
                          Like specifying filterspec '*:s'
          -f <filename>   Log to file. Default to stdout
          -r [<kbytes>]   Rotate log every kbytes. (16 if unspecified). Requires -f
          -n <count>      Sets max number of rotated logs to <count>, default 4
          -v <format>     Sets the log print format, where <format> is one of:

                          brief process tag thread raw time threadtime long

          -c              clear (flush) the entire log and exit
          -d              dump the log and then exit (don't block)
          -g              get the size of the log's ring buffer and exit
          -b <buffer>     request alternate ring buffer
                          ('main' (default), 'radio', 'events')
          -B              output the log in binary
        filterspecs are a series of
          <tag>[:priority]

        where <tag> is a log component tag (or * for all) and priority is:
          V    Verbose
          D    Debug
          I    Info
          W    Warn
          E    Error
          F    Fatal
          S    Silent (supress all output)

        '*' means '*:d' and <tag> by itself means <tag>:v

        If not specified on the commandline, filterspec is set from ANDROID_LOG_TAGS.
        If no filterspec is found, filter defaults to '*:I'

        If not specified with -v, format is set from ANDROID_PRINTF_LOG
        or defaults to "brief"*/

        ArrayList<String> list = new ArrayList<String>();
        
        mCollectLogTask = (CollectLogTask) new CollectLogTask().execute(list);
    }
    
    private class CollectLogTask extends AsyncTask<ArrayList<String>, Void, StringBuilder>{
        

        @Override
        protected StringBuilder doInBackground(ArrayList<String>... params){
            final StringBuilder log = new StringBuilder();
            try{
                ArrayList<String> commandLine = new ArrayList<String>();
                commandLine.add("logcat");//$NON-NLS-1$
                commandLine.add("-d");//$NON-NLS-1$
                ArrayList<String> arguments = ((params != null) && (params.length > 0)) ? params[0] : null;
                if (null != arguments){
                    commandLine.addAll(arguments);
                }
                
                Process process = Runtime.getRuntime().exec (commandLine.toArray(new String[0]));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                
                String line;
                while ((line = bufferedReader.readLine()) != null){ 
                    log.append(line);
                    log.append(mSeparator); 
                }
            } 
            catch (IOException e){
                mView.setTestState(e.getMessage());
                e.printStackTrace();
            } 

            return log;
        }

        @Override
        protected void onPostExecute(StringBuilder log) {
            System.out.println(log);
        }
    }
    
    
}
