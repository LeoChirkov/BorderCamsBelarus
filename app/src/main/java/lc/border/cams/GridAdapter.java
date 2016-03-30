package lc.border.cams;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private int screenWidth, screenHeight;
    LayoutInflater inflater;

    ArrayList<String[]> borders;

    public GridAdapter(Context c, ArrayList<String[]> arrayList, int width) {
        inflater = LayoutInflater.from(c);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(c));
        mContext = c;
        borders = arrayList;
        screenWidth = width/2;
        screenHeight = (int) (screenWidth/1.33);

        Log.i("SCREEN SIZE", String.valueOf(screenWidth)+" "+String.valueOf(screenHeight));
    }


    @Override
    public int getCount() {
        return borders.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView borderName, carQueue, truckQueue, time;
        ImageView camera;
        FrameLayout frameLayout;

        inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.grid_item, viewGroup, false);
        final String[] border = borders.get(i);


        borderName = (TextView) myView.findViewById(R.id.border_name);
        borderName.setText(border[0]);

        carQueue = (TextView) myView.findViewById(R.id.queue_car);
        carQueue.setText(border[2]);

        truckQueue = (TextView) myView.findViewById(R.id.queue_truck);
        truckQueue.setText(border[3]);

        time = (TextView) myView.findViewById(R.id.time);
        time.setText(border[4]);

        camera = (ImageView) myView.findViewById(R.id.camera);

        camera.getLayoutParams().height = screenHeight;
        camera.requestLayout();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(border[1], camera);

        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FullSize.class);
                intent.putExtra("URL", border[1]);
                mContext.startActivity(intent);
            }
        });

        return myView;
    }
}
