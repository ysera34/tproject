package com.tacademy.test.chemitest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private WebView webView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        imageView = (ImageView) findViewById(R.id.image_view);

        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
//        webView.setInitialScale(50);
//        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        url = "http://gongle.inframincer.org/index.php/archives/8";
//        webView.loadUrl(url);

        new WebViewTask().execute(url);

    }

    private class WebViewTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            String url = strings[0];
            String html = null;

            try {
                Document doc = Jsoup.connect(url).get();
                Elements ele = doc.select("div.entry-content");
                html = ele.toString();
                Log.d("html", html);
            } catch (IOException e) {
                e.printStackTrace();
            }
                return html;
        }

        @Override
        protected void onPostExecute(String html) {
            super.onPostExecute(html);
            String mine = "text/html; charset=UTF-8";
//            String encoding = "utf-8";
//            webView.loadData(html, mine, null);
//            webView.loadDataWithBaseURL("http://gongle.inframincer.org", html, mine, null, null);

            html = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" + html;
            webView.loadDataWithBaseURL("file:///android_asset/", html, mine, null, null);
        }
    }

    /*
        // Printing HTML Document
        webView = new WebView(getApplicationContext());
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view,
                                                    String url)
            {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                createWebPrintJob(view);
                myWebView = null;
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view,
                                                    String url)
            {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                createWebPrintJob(view);
                myWebView = null;
            }
        });

    }

    private void doWebViewPrint() {
        // Create a WebView object specifically for printing
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "page finished loading " + url);
                createWebPrintJob(view);
                mWebView = null;
            }
        });

        // Generate an HTML document on the fly:
        String htmlDocument = "<html><body><h1>Test Content</h1><p>Testing, " +
                "testing, testing...</p></body></html>";
        webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);

        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
        mWebView = webView;
    }

    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter("MyDocument");

        String jobName = getString(R.string.app_name) + " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }

    */
}
