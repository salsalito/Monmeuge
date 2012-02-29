package fr.activity.meuge;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter  {
	 private static Context mContext;
	 private boolean tableau;
	 int mGalleryItemBackground;
	    // references to our images
	    private Integer[] mThumbIds = {
	            R.drawable.ic_launcher, R.drawable.ic_launcher,
	            R.drawable.ic_launcher, R.drawable.ic_launcher,
	            R.drawable.ic_launcher, R.drawable.ic_launcher,
	            R.drawable.ic_launcher, R.drawable.ic_launcher,
	            R.drawable.ic_launcher, R.drawable.ic_launcher,
	            R.drawable.ic_launcher, R.drawable.ic_launcher
	    };
	    // references to our images
	    private Integer[] mThumbIds2 = {
	            R.drawable.robot, R.drawable.robot,
	            R.drawable.robot, R.drawable.robot,
	            R.drawable.robot, R.drawable.robot,
	            R.drawable.robot, R.drawable.robot,
	            R.drawable.robot, R.drawable.robot,
	            R.drawable.robot, R.drawable.robot
	    };

	 public ImageAdapter(Context c, boolean table) {
	        
		 	mContext = c;
		 	tableau = table;
//	        mContext.obtainStyledAttributes((R.styleable.GalleryTheme_android_galleryItemBackground);
//	        TypedArray attr = mContext.obtainStyledAttributes(R.styleable.GalleryTheme_android_galleryItemBackground);
//	        mGalleryItemBackground = attr.getResourceId(
//	                R.styleable.GalleryTheme_android_galleryItemBackground, 0);
//	        attr.recycle();
	        
	    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 12;

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
    private Bitmap downloadImage(String myURL) {

    	Bitmap bitmap = null;

    	try {

    	URL urlImage = new URL(myURL);

    	HttpURLConnection connection = (HttpURLConnection) urlImage.openConnection();

    	InputStream inputStream = connection.getInputStream();

    	bitmap = BitmapFactory.decodeStream(inputStream);

   // 	image.setImageBitmap(bitmap);

    	} catch (MalformedURLException e) {

    		Log.e ("Image","Erreur sur URL l 'image" );

    	} catch (IOException e) {

    	Log.e ("Image","Erreur sur l 'image" );

    	}
    	return bitmap;
    	}
    public View getViewMichel(int position, View convertMich, ViewGroup parent) {

		ImageView imageView = new ImageView(mContext);
        if (convertMich == null) {  // if it's not recycled, initialize some attributes
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(4, 4, 4, 4);
            convertMich = (View) imageView;
        } 
        imageView = (ImageView) convertMich;
        return imageView;
    	
    }
	@Override
	public View getView(int position, View convertMich, ViewGroup parent) {
		
		ImageView viewSet = (ImageView) getViewMichel(position, convertMich, parent);
    	if (!tableau)
    		viewSet.setImageResource(mThumbIds[position]);
        else 
        	viewSet.setImageResource(mThumbIds2[position]);
                //imageView.setImageBitmap(downloadImage("http://173.194.67.94/intl/en_com/images/srpr/logo1w.png"));
        return viewSet;

	}

}
