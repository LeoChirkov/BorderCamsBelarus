package lc.border.cams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class FullSize extends Activity {
    String URL;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.full_size);

        Intent intent = getIntent();
        URL = intent.getStringExtra("URL");

        ImageView fullSize = (ImageView) findViewById(R.id.full_size_camera);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(URL, fullSize);
    }
}
