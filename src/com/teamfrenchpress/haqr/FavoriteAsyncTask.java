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

public class FavoriteAsyncTask extends AsyncTask<String, Void, HttpResponse> {
	
	private CookieStore cookieStore;
	private OnFavoriteSuccess callSuccess;
	private OnFavoriteFailure callFailure;
	
	public FavoriteAsyncTask(CookieStore c, OnFavoriteSuccess s, OnFavoriteFailure f)
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
		HttpPost post = new HttpPost("https://www.hackerleague.org/api/v1/hackathons/" + params[0] + "/hacks/" + params[1] + "/favorites");
		
		HttpResponse response = null;
		
		try {
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
			callSuccess.onFavoriteSuccess(r);
		else
			callFailure.onFavoriteFailure(r);
	}
}
