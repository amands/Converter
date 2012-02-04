package kankan.wheel.demo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class SLength extends Activity {
	// Scrolling flag
	private boolean scrolling = false;
	// int Selected = 1;
	EditText etTo, etFrom;
	Map<String, String> googleUnits = new HashMap<String, String>();
	private float result;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		googleUnits.put("Fahrenheit", "F");
		googleUnits.put("Celsius", "C");
		googleUnits.put("Kelvin", "K");

		setContentView(R.layout.cities_layout);

		etFrom = (EditText) findViewById(R.id.edittext_from);
		etTo = (EditText) findViewById(R.id.edittext_to);

		final String cities[][] = new String[][] { new String[] { "Fahrenheit",
				"Celsius", "Kelvin" }, };

		final WheelView city = (WheelView) findViewById(R.id.city);
		city.setVisibleItems(cities[0].length);

		final WheelView country = (WheelView) findViewById(R.id.country);
		updateCities(country, cities, 0);
		updateCities(city, cities, 0);

		country.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!scrolling) {
					updateCities(city, cities, 0);
				}
			}
		});

		city.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!scrolling) {
					System.out
							.println("CitiesActivity.onCreate(...).new OnWheelChangedListener() {...}.onChanged()");
				}
			}
		});

		country.addScrollingListener(new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				scrolling = true;
			}

			public void onScrollingFinished(WheelView wheel) {
				scrolling = false;
				// updateCities(city, cities, country.getCurrentItem());
			}
		});

		System.out.println("sElected ::" + 0);
		country.setCurrentItem(0);

		etFrom.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				String element1 = (String) googleUnits.get(cities[0][country
						.getCurrentItem()]);
				String element2 = (String) googleUnits.get(cities[0][city
						.getCurrentItem()]);
				System.out.println(country.getCurrentItem()
						+ "selected item in else::" + city.getCurrentItem());
				result = Float.valueOf(etFrom.getText().toString())
						.floatValue();
				new GetData().execute(result + element1 + "=?" + element2);
			}
		});
	}

	/**
	 * Updates the city wheel
	 */
	private void updateCities(WheelView city, String cities[][], int index) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				cities[index]);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(cities[index].length / 2);
	}

	public class GetData extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String result = "";
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(
						"http://www.google.com/ig/calculator?q=" + params[0]);
				System.out.println("Result::"
						+ "http://www.google.com/ig/calculator?q=" + params[0]);
				// httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				is.close();

				String output = sb.toString();
				System.out.println("output::" + output);
				JSONObject jobj = new JSONObject(output);
				System.out.println(jobj + "arrlen::" + jobj.length());
				String foo = jobj.get("lhs").toString();
				result = jobj.get("rhs").toString();
				System.out.println(foo + "::" + result + "RESULT : -" + output
						+ "-");
				return result;

			} catch (Exception e) {
				System.out.println("Error parsing data " + e.toString());
			}

			return result;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			etTo.setText(result);

		}
	}
}
