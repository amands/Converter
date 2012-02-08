package slinfy.android.unitconverter;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class AboutSlinfy extends Activity {
	 private WebView myWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);
		myWebView = (WebView) this.findViewById(R.id.webView);
		
		myWebView.loadUrl("http://www.slinfy.com/");
	}
	
	
	
	

}
