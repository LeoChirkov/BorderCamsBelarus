package lc.border.cams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.GridView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity {
    ProgressDialog mProgressDialog;
    private static String FILE_PATH = "";
    static String FILE_LT = "/pkpd.lt";
    static String FILE_BY = "/gpk.by";
    private static String URL_BY = "http://gpk.gov.by/maps/ochered.php";
    private static String URL_LT = "http://www.pkpd.lt/lt/border";
    private static boolean wifiConnected = false;
    private static boolean mobileConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view);

        FILE_PATH = String.valueOf(this.getFilesDir());
        Log.i("FILE_PATH", FILE_PATH);

        new Queue().execute(FILE_PATH);
    }


    class Queue extends AsyncTask<String, Void, Void> {
        ArrayList<String[]> arrayListBorders;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setMessage("Loading data...");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(String... filePath) {
            QueueParser queueParser = new QueueParser();
            Log.i("doInBackground filePath", filePath[0]+FILE_BY);

            String filePathLT = filePath[0]+FILE_LT;
            String filePathBY = filePath[0]+FILE_BY;

            downloadFile(URL_BY, filePathBY);
            downloadFile(URL_LT, filePathLT);

            arrayListBorders = queueParser.createArrayList(filePath[0]);

            return null;
        }
        @Override
        protected void onPostExecute(Void args) {

            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            int width = displaymetrics.widthPixels;
            GridView gridView = (GridView) findViewById(R.id.gridview);
            gridView.setAdapter(new GridAdapter(MainActivity.this, arrayListBorders, width));
            mProgressDialog.dismiss();
        }
    }

    // Download the file
    public String downloadFile(String fromURL, String toFile) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(fromURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            // download the file
            input = connection.getInputStream();
            output = new FileOutputStream(toFile);

            byte data[] = new byte[4096];
            int count;
            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }
}
