package com.whcard.rent;

import com.loopj.android.image.SmartImageView;
import com.whcard.main.R;
import com.whcard.main.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

public class HouseImageActivity extends Activity {

	private SmartImageView imgewall_house_image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_house_image);
		
		imgewall_house_image=(SmartImageView) findViewById(R.id.imgewall_house_image);
		
		String imagePath=getIntent().getStringExtra("path");
		imgewall_house_image.setImageUrl(imagePath);
	}
}
