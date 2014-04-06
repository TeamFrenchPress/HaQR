package com.teamfrenchpress.haqr;

//import java.net.HttpCookie;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

public class ListActivity extends ActionBarActivity implements OnFavoriteSuccess, OnFavoriteFailure {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		String val = getIntent().getExtras().getString("login-cookies");
		CookieStore cookieStore = new BasicCookieStore();
		cookieStore.addCookie(new BasicClientCookie("remember_token", val));

		/*BasicClientCookie cookie;
		for (int i = 0; i < cookieStrs.length; i++)
		{
			String[] split = cookieStrs[i].split("=");
			if (split.length == 2)
				cookie = new BasicClientCookie(split[0], split[1]);
			else
				cookie = new BasicClientCookie(split[0], null);
			
			cookie.setDomain(".hackerleague.org");
			cookieStore.addCookie(cookie);
		}*/
		
		/*HttpCookie c;
		for (int i = 0; i < cookieStrs.length; i++)
		{
			c = new HttpCookie(cookieStrs[i]);
			cookieStore.addCookie(c);
		}*/
		
		new FavoriteAsyncTask(cookieStore, this, this).execute("y-hack-at-yale", "WorkIt");
	}

	public void onFavoriteSuccess(HttpResponse r)
	{
		Toast.makeText(this, "FAVORITED!", Toast.LENGTH_LONG).show();
	}
	
	public void onFavoriteFailure(HttpResponse r)
	{
		Toast.makeText(this, "NOT TODAY", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_list, container,
					false);
			return rootView;
		}
	}

}
