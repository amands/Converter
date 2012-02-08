
package slinfy.android.unitconverter;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FindApp extends Dialog implements OnClickListener {
	// about messages
	private static final String str_about[] = {
			"<b>Currency Converter</b>",
			"Copyright, 2011 ",
			"Licensed under the Solitaire Infosys, Version 2.0",
		
			
	};

	public FindApp(Context context) {
		super(context); 
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// do not need title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// use about_dialog layout for content
		this.setContentView(R.layout.find_app);
	 	
		TextView about_text = (TextView) this.findViewById(R.id.About_Dialog_Text);
		
		// show about message
    	String html_about = "";
    	for(int i=0; i<str_about.length; i++) {
    		html_about = html_about + str_about[i] + "<br />";
    	}
    	
    	about_text.setText(android.text.Html.fromHtml(html_about));
    	
    	Button about_close_btn = (Button) this.findViewById(R.id.About_Dialog_CloseButton);
    	
    	// create OK button
    	about_close_btn.setOnClickListener(this);
	}

	public void onClick(View v) {
		// close button
		this.dismiss();
	}

}
