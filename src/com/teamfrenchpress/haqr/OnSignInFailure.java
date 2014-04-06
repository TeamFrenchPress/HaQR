package com.teamfrenchpress.haqr;

import org.apache.http.HttpResponse;

public interface OnSignInFailure {
	public void onSignInFailure(HttpResponse r);
}
