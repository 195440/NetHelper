package example.nethelper;

import java.util.ArrayList;

import example.nethelper.adapter.MainPagerAdapter;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;

public class ViewPagerActivity extends Activity {
	
	private ViewPager vp_main;
	
	private ArrayList<View> viewList;
	
	PagerTabStrip tabStrip = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);
		
		vp_main = (ViewPager) findViewById(R.id.vp_main);
		
		LayoutInflater inflater=getLayoutInflater();
		View leftView = inflater.inflate(R.layout.fragment_left, null);
		View rightView = inflater.inflate(R.layout.fragment_right, null);
		
		viewList = new ArrayList<View>();
		viewList.add(leftView);
		viewList.add(rightView);
		
		tabStrip = (PagerTabStrip) this.findViewById(R.id.tabstrip);
        tabStrip.setDrawFullUnderline(true);
        tabStrip.setBackgroundColor(Color.WHITE);
        tabStrip.setTabIndicatorColor(Color.RED);
        tabStrip.setTextSpacing(100);
		ArrayList<String> titleList = new ArrayList<String>();
		titleList.add("TCP");
		titleList.add("UDP");
		
		MainPagerAdapter pagerAdapter = new MainPagerAdapter(this, viewList, titleList);
		vp_main.setAdapter(pagerAdapter);
		
		vp_main.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				System.out.println(arg0+"=======");
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}

}
