/*
	Copyright 2010, 2011 Kwok Ho Yin

   	Licensed under the Apache License, Version 2.0 (the "License");
   	you may not use this file except in compliance with the License.
   	You may obtain a copy of the License at

    	http://www.apache.org/licenses/LICENSE-2.0

   	Unless required by applicable law or agreed to in writing, software
   	distributed under the License is distributed on an "AS IS" BASIS,
   	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   	See the License for the specific language governing permissions and
   	limitations under the License.
 */

package slinfy.android.unitconverter;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class createCurrencyEditDialog extends Dialog implements OnClickListener {
	// about messages
	private static final String str_about[] = {
			"Unit and Currency Converter",
	};

	public createCurrencyEditDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// do not need title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// use about_dialog layout for content
		this.setContentView(R.layout.about_dialog);

		TextView about_text = (TextView) this
				.findViewById(R.id.About_Dialog_Text);

		// show about message
		String html_about = "";
		for (int i = 0; i < str_about.length; i++) {
			html_about = html_about + str_about[i] + "<br />";
		}

		about_text.setText(android.text.Html.fromHtml(html_about));

		Button about_close_btn = (Button) this
				.findViewById(R.id.About_Dialog_CloseButton);

		// create OK button
		about_close_btn.setOnClickListener(this);
	}

	public void onClick(View v) {
		// close button
		this.dismiss();
	}

}
