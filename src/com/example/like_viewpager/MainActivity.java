package com.example.like_viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.like_viewpager.MyScrollView.PageChangeListener;

public class MainActivity extends Activity {
	private int[] ids = new int[] { R.drawable.a1, R.drawable.a2,
			R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6 };
	private MyScrollView myScrollView;
	private RadioGroup radioGroup;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		myScrollView = (MyScrollView) findViewById(R.id.myscrollview);

		 View testView = getLayoutInflater().inflate(R.layout.test, null);
		 listView = (ListView) testView.findViewById(R.id.listview);
		//listView = new ListView(this);
		listView.setAdapter(new LiatAdapter());
		for (int i = 0; i < ids.length; i++) {
			if (i == 2) {
				// myScrollView.addView(testView);
				myScrollView.addView(testView);
				ImageView imageView = new ImageView(this);
				imageView.setBackgroundResource(ids[i]);
				myScrollView.addView(imageView);
			} else {
				ImageView imageView = new ImageView(this);
				imageView.setBackgroundResource(ids[i]);
				myScrollView.addView(imageView);
			}
//			ImageView imageView = new ImageView(this);
//			imageView.setBackgroundResource(ids[i]);
//			myScrollView.addView(imageView);
		}
		for (int i = 0; i < myScrollView.getChildCount(); i++) {
			RadioButton radioButton = new RadioButton(this);
			radioButton.setId(i);
			radioGroup.addView(radioButton);
			if (i == 0) {
				radioButton.setChecked(true);
			}
		}
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				System.out.println("checkedId:" + checkedId);
				myScrollView.radioFlushView(checkedId);
			}
		});
		myScrollView.setPageChangeListener(new PageChangeListener() {

			@Override
			public void changeRadio(int count) {
				// TODO Auto-generated method stub
				((RadioButton) radioGroup.getChildAt(count)).setChecked(true);
			}
		});

	}

	private class LiatAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 50;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Holder holder = null;
			if (convertView != null) {
				holder = (Holder) convertView.getTag();
			} else {
				holder = new Holder();
				convertView = View.inflate(MainActivity.this,
						R.layout.list_item, null);
				holder.textView = (TextView) convertView
						.findViewById(R.id.textview);
				convertView.setTag(holder);
			}
			return convertView;
		}

		private class Holder {
			private TextView textView;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
