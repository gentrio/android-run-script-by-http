package com.gentrio.runscriptdemo;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.gentrio.runscript.Script;
import com.gentrio.runscript.ScriptClass;
import com.gentrio.runscript.ScriptClassContext;


/**
 * author: gentrio
 * created on: 2019-07-16
 */
@ScriptClass
public class ScriptTest extends ScriptClassContext {

    @Script
    public void run() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(application, "Hello World", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
