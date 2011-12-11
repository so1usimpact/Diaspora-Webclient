package com.voidcode.diasporawebclient;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ShareActivity extends MainActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo m3G = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mWifi.isConnected() || m3G.isConnected()) 
        {
        	if(!this.main_domain.equals(""))
        	{	
        		// load: open new messages
        		mWeb.loadUrl("https://"+main_domain+"/status_messages/new");
        		
        		//when you are on eg your default browser and choose 'share with', 
        		//and then choose 'Diaspora-Webclient' it goto here 
        		Intent intent = getIntent();
        		Bundle extras = intent.getExtras();
        		String action = intent.getAction();
        		if (Intent.ACTION_SEND.equals(action)) //if user has 
        		{  
        			if (extras.containsKey(Intent.EXTRA_TEXT) && extras.containsKey(Intent.EXTRA_SUBJECT)) 
        			{
		        		final String pagesUrl = (String) extras.get(Intent.EXTRA_TEXT);//get url on the site user will share
		        		final String titleUrl = (String) extras.get(Intent.EXTRA_SUBJECT);//get the url�s title
		        		mWeb.setWebViewClient(new WebViewClient() 
		        		{
				        	public void onPageFinished(WebView view, String url) 
				        	{
				        		//TODO
				        		//user has to touch the 'textarea' before bookmarklink is paste
				        		//this have to be intent
				
				        		//inject share pageurl into 'textarea' via javascript
				        	    mWeb.loadUrl("javascript:(function() { " + 
					        	    			//make more space to user-message
					        	    			"document.getElementsByTagName('textarea')[0].style.height='110px'; "+
					        	                //inject formate bookmark
					        	    			"document.getElementsByTagName('textarea')[0].innerHTML = '["+titleUrl+"]("+pagesUrl+") #bookmark '; " +  
					        	            "})()");  
				        		if(mProgress.isShowing())
				        		{
				        			mProgress.dismiss();
				        			
				        		}
				        	}
				        });	
				    }
        		}
        	}
        }
    }
	//Screen orientation crashes app fix
	//http://jamesgiang.wordpress.com/2010/06/05/screen-orientation-crashes-my-app/
	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
		super.onConfigurationChanged(newConfig);
	} 
}
