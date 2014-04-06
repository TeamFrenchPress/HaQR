package com.teamfrenchpress.haqr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.DefaultedHttpContext;
import org.apache.http.protocol.HttpContext;

import android.os.AsyncTask;
import android.util.Log;

public class SignInAsyncTask extends AsyncTask<String, Void, HttpResponse> {
	
	private OnSignInSuccess callSuccess;
	private OnSignInFailure callFailure;
	private CookieStore cookieStore;
	
	public SignInAsyncTask(CookieStore c, OnSignInSuccess s, OnSignInFailure f)
	{
		cookieStore = c;
		callSuccess = s;
		callFailure = f;
	}
	
	protected HttpResponse doInBackground(String... params)
	{
		HttpContext ctx = new BasicHttpContext();
		ctx.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		
		HttpClient h = new DefaultHttpClient();
		HttpPost post = new HttpPost("https://www.hackerleague.org/session");
		
		HttpResponse response = null;
		
		try {
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			for (int i = 0; i < params.length / 2; i++)
				pairs.add(new BasicNameValuePair(params[i * 2], params[i * 2 + 1]));
			
			post.setEntity(new UrlEncodedFormEntity(pairs));
			
			response = h.execute(post, ctx);
		}
		catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    }
		catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
		
		return response;
	}
	
	protected void onPostExecute(HttpResponse r)
	{
		int code = r.getStatusLine().getStatusCode();
		
		if (code == HttpStatus.SC_OK)
			callSuccess.onSignInSuccess(r);
		else
			callFailure.onSignInFailure(r);
	}
}
