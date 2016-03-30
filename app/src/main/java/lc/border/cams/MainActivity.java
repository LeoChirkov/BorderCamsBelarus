package lc.border.cams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view);

        new Queue().execute();

    }

    class Queue extends AsyncTask<Void, Void, ArrayList<String[]>> {
        ArrayList<String[]> arrayListBY, arrayListLT;

//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mProgressDialog = new ProgressDialog(MainActivity.this);
//            mProgressDialog.setMessage("Loading data...");
//            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            mProgressDialog.setIndeterminate(false);
//            mProgressDialog.show();
//        }

        @Override
        protected ArrayList<String[]> doInBackground(Void... voids) {
            QueueParser queueParser = new QueueParser();
            arrayListBY = queueParser.createArrayList();

            return null;
        }
        @Override
        protected void onPostExecute(ArrayList<String[]> result) {

            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            int width = displaymetrics.widthPixels;
            GridView gridView = (GridView) findViewById(R.id.gridview);
            gridView.setAdapter(new GridAdapter(MainActivity.this, arrayListBY, width));
//            mProgressDialog.dismiss();
        }

    }

    
}
