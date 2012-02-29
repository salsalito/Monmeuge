package fr.activity.meuge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ImageFull extends Activity  implements OnClickListener {
	private Button exit; 
	@Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.image);
	        Bundle extras = getIntent().getExtras();
	        String []tt = new String[1];
	        tt = (String []) extras.get("Infos");
	        exit = (Button)findViewById(R.id.buttonBack);
	        exit.setText("Position : " +tt[0]);
	        exit.setOnClickListener(this);
	 }
	@Override
	public void onClick(View view) {
		switch (view.getId())
       {
			case R.id.buttonBack: setResult(100,new Intent());
								  finish();  break;
       }
	}
}
