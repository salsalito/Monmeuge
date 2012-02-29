package fr.activity.meuge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.restlet.engine.adapter.HttpResponse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class ImageGrid extends Activity {
	public GridView mGrid;
	private boolean mBusy = true;
	ArrayList<Integer> item_ids = new ArrayList<Integer>();
	final Handler handler = new Handler();
	protected static final int SUB_ACTIVITY_REQUEST_CODE = 100;
	private boolean myClick;
	private Timer timer;
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.grid);
	        mGrid = (GridView) findViewById(R.id.myGrid);
	        startGallery();
	 }

	/**
	 * 
	 */
	private void startGallery() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
		@Override
		public void run() {
			if (myClick)
				timer.cancel();
			else
				handler.post(ImageGridThread(timer));
		}
		}, 3000, 3000); //delay, //periode
	}

	 private void demarreGrid(Context myClass)
	{
			mBusy = !mBusy;
			mGrid.setAdapter(new ImageAdapter(myClass, mBusy));
	        mGrid.setOnItemClickListener(clickListener());
	}
	/**
	 * @return
	 */
	private Runnable ImageGridThread(Timer time) {
		return new Runnable() {
			  @Override
			  public void run() {
			       Toast.makeText(getApplicationContext(), String.valueOf(System.currentTimeMillis()), Toast.LENGTH_SHORT).show();
			       demarreGrid(ImageGrid.this);
			    	handler.removeCallbacks(this);
			  }
			  
			};
	}

	
	 /**
	 * @return
	 */
	private OnItemClickListener clickListener() {
		return new OnItemClickListener()
		{
		    public void onItemClick(AdapterView parent,
		    View v, int position, long id)
		    {
		    	final Bundle bundle = new Bundle();
		    	String []tt = new  String[1];
		    	myClick = true;
		    	Toast.makeText(getApplicationContext(), "Position " + (position + 1), Toast.LENGTH_SHORT).show();
		    	tt[0] = String.valueOf(position + 1);
		    	Intent intent = new Intent(ImageGrid.this,ImageFull.class);
		        bundle.putStringArray("Infos", tt);
		        intent.putExtras(bundle);
		        startActivityForResult(intent, SUB_ACTIVITY_REQUEST_CODE);
		   }
		};
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("CheckStartActivity","onActivityResult and resultCode = "+resultCode);
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==SUB_ACTIVITY_REQUEST_CODE){
            Toast.makeText(this, "Redemarrage gallerie", Toast.LENGTH_LONG).show();
            startGallery();
            myClick = false;
        }
        else{
            Toast.makeText(this, "Michel Fail", Toast.LENGTH_LONG).show();
        }
    }

}
