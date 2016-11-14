package com.tacademy.search.chemi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText mSearchProductEditText;
    private PipedReader mPipedReader;
    private PipedWriter mPipedWriter;

    private Thread workerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPipedReader = new PipedReader();
        mPipedWriter = new PipedWriter();

        try {
            mPipedWriter.connect(mPipedReader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);
        mSearchProductEditText = (EditText) findViewById(R.id.search_product_edit_text);
        mSearchProductEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "beforeTextChanged is triggered.");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "onTextChanged is triggered.");
                Log.d(TAG, charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "afterTextChanged is triggered.");
            }
        });

        workerThread = new Thread(new TextHandlerTask(mPipedReader));
        workerThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (workerThread != null) {
            workerThread.interrupt();
        }
        try {
            mPipedReader.close();
            mPipedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class TextHandlerTask implements Runnable {
        private final PipedReader mPipedReader;

        public TextHandlerTask(PipedReader pipedReader) {
            mPipedReader = pipedReader;
        }

        @Override
        public void run() {
            while (Thread.currentThread().isInterrupted()) {
                try {
                    int i;
                    while ((i = mPipedReader.read()) != -1) {
                        char c = (char) i;
                        Log.d(TAG, "char = " + c);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
