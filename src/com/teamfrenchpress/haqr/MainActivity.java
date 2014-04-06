package com.teamfrenchpress.haqr;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		CookieSyncManager.createInstance(getApplicationContext());
		CookieSyncManager.getInstance().startSync();
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
	
	@Override
	protected void onPause()
	{
		CookieSyncManager.getInstance().stopSync();
	}
	
	@Override
	protected void onResume()
	{
		CookieSyncManager.getInstance().startSync();
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements OnSignInSuccess, OnSignInFailure {

		private CookieStore cookies = new BasicCookieStore();
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			final EditText email = (EditText)rootView.findViewById(R.id.signinemail);
			final EditText passwd = (EditText)rootView.findViewById(R.id.signinpassword);
			
			final OnSignInSuccess callS = this;
			final OnSignInFailure callF = this;
			
			final Button signinBtn = (Button)rootView.findViewById(R.id.signinbutton);
			signinBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					new SignInAsyncTask(cookies, callS, callF).execute("session[email]", email.getText().toString(), "session[password]", passwd.getText().toString(), "commit", "Sign In");
				}
			});
			 
			return rootView;
		}
		
		public void onSignInSuccess(HttpResponse r)
		{
			//Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_SHORT).show();
			
			Intent intent = new Intent(getActivity(), ListActivity.class);
			getActivity().startActivity(intent);
		}
		
		public void onSignInFailure(HttpResponse r)
		{
			Toast.makeText(getActivity(), r.getStatusLine().toString(), Toast.LENGTH_SHORT).show();
		}
	}

}
