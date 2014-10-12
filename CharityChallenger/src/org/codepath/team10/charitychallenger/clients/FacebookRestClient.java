package org.codepath.team10.charitychallenger.clients;

import java.io.Serializable;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FacebookApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;

public class FacebookRestClient extends OAuthBaseClient implements Serializable {

	private static final long serialVersionUID = 5330469630348849634L;
	
	public static final Class<? extends Api> REST_API_CLASS = FacebookApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "308Y6AhszvPZK1Tg9l7RDXf9Z";       // Change this
	public static final String REST_CONSUMER_SECRET = "xdRYse6QDDbWkE5bFA7xB5XsLwl6DWusTUSytx7TMge4LE1YNS"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://fbclientchallenger"; // Change this (here and in manifest)

	public FacebookRestClient(Context c) {
		super(c, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

}
