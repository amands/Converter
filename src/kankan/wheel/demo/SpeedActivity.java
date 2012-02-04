// 5 Currency

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

public class SpeedActivity extends Activity {
    // Scrolling flag
    private boolean scrolling = false;
    int Selected = 1;
    EditText etTo, etFrom;
    Map<String, String> googleUnits = new HashMap<String, String>();
    private float result;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        googleUnits.put("Fahrenheit", "F");
		googleUnits.put("Celsius", "C");
		googleUnits.put("Kelvin", "K");
		googleUnits.put("Km/h", "Km/h");
		googleUnits.put("m/s", "m/s");
		googleUnits.put("cm/s", "cm/s");
		googleUnits.put("Hours", "hours");
		googleUnits.put("Minutes", "minutes");
		googleUnits.put("Seconds", "seconds");

		googleUnits.put("Euro", "EUR");
		googleUnits.put("US dollar","US");
		googleUnits.put("Japanese yen","JPY");
		googleUnits.put("Bulgarian lev", "BGN");
		googleUnits.put("Czech koruna", "CZK");
		googleUnits.put("Danish krone", "DKK");
		googleUnits.put("Estonian kroon", "EEK");
		googleUnits.put("GBP", "Pound sterling");
		googleUnits.put("Hungarian forint", "HUF");
		googleUnits.put("Lithuanian litas","LTL");
		googleUnits.put("Latvian lats", "LVL");
		googleUnits.put("Polish zloty", "PLN");
		googleUnits.put("New Romanian leu","RON");
		googleUnits.put("Swedish krona","SEK");
		googleUnits.put("Swiss franc", "CHF");
		googleUnits.put("Norwegian krone","NOK");
		googleUnits.put("Croatian kuna", "HRK");
		googleUnits.put("Russian rouble","RUB");
		googleUnits.put("Turkish lira","TRY");
		googleUnits.put("Australian dollar", "AUD");
		googleUnits.put("Brasilian real", "BRL");
		googleUnits.put("CAD", "Canadian dollar");
		googleUnits.put("Chinese yuan renminbi","CNY");
		googleUnits.put("Hong Kong dollar","HKD");
		googleUnits.put("Indonesian rupiah", "IDR");
		googleUnits.put("Indian rupee","INR");
		googleUnits.put("South Korean won","KRW");
		googleUnits.put("Mexican peso","MXN");
		googleUnits.put("Malaysian ringgit", "MYR");
		googleUnits.put("New Zealand dollar","NZD");
		googleUnits.put("Philippine peso", "PHP");
		googleUnits.put("Singapore dollar", "SGD");
		googleUnits.put("Thai baht","THB");
		googleUnits.put("South African rand","ZAR");
        
//		Warehouse collector	= (Warehouse)getApplicationContext();
		
		Selected = 5;
        setContentView(R.layout.cities_layout);
        
        etFrom	=	(EditText)findViewById(R.id.edittext_from);
        etTo	=	(EditText)findViewById(R.id.edittext_to);
        
        final String cities[][] = new String[][] {
        		new String[] {""},
        		new String[] {"Fahrenheit", "Celcius", "Kelvin"},
        		new String[] {"km/h", "m/s", "cm/s"},
        		new String[] {"Hours", "Minutes", "Seconds"},
        		new String[] {"Kilometer", "Meter", "Centimeter", "France"},
        		new String[] {"EUR", "USD", "JPY", "BGN", "CZK", "DKK", "EEK", "GBP", "HUF", "LTL", "LVL", "PLN", "RON", "SEK", "CHF", "NOK", "HRK", "RUB", "TRY", "AUD", "BRL", "CAD", "CNY", "HKD", "IDR", "INR", "KRW", "MXN", "MYR", "NZD", "PHP", "SGD", "THB", "ZAR"}
        		};
        
        final WheelView city = (WheelView) findViewById(R.id.city);
        city.setVisibleItems(cities[Selected].length);
        
        final WheelView country = (WheelView) findViewById(R.id.country);
        updateCities(country, cities, Selected);
        
//        country.setVisibleItems(cities[Selected].length);
//        country.setViewAdapter(new CountryAdapter(this));

        country.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
			    if (!scrolling) {
			        updateCities(city, cities, Selected);
			    }
			}
		});
       
        city.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
			    if (!scrolling) {
			        System.out.println("CitiesActivity.onCreate(...).new OnWheelChangedListener() {...}.onChanged()");
			    }
			}
		});
        
        country.addScrollingListener( new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
                scrolling = true;
            }
            public void onScrollingFinished(WheelView wheel) {
                scrolling = false;
//                updateCities(city, cities, country.getCurrentItem());
            }
        });

        System.out.println("sElected ::"+Selected);
        country.setCurrentItem(Selected);
        
        etFrom.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,int count) {
				
				String element1 = (cities[Selected][country.getCurrentItem()]);
				String element2 = (cities[Selected][city.getCurrentItem()]);
				System.out.println(country.getCurrentItem()+"selected item in else::"+city.getCurrentItem());
				result = Float.valueOf(etFrom.getText().toString()).floatValue();
				new GetData().execute(result+element1+"=?"+element2);
				
//				et_to.setText("" + );
			}
		});
    }
    
    /**
     * Updates the city wheel
     */
    private void updateCities(WheelView city, String cities[][], int index) {
        ArrayWheelAdapter<String> adapter =
            new ArrayWheelAdapter<String>(this, cities[index]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(cities[index].length / 2);        
    }
    
    /**
     * Adapter for countries
     */
//    private class CountryAdapter extends AbstractWheelTextAdapter {
//        // Countries names
//        private String countries[][] =new String[][] {
//        		new String[] {""},
//        		new String[] {"Fahrenheit", "Celcius", "Kelvin"},
//        		new String[] {"km/h", "m/s", "cm/s"},
//        		new String[] {"Hours", "Minutes", "Seconds"},
//        		new String[] {"Kilometer", "Meter", "Centimeter", "France"},
//        		new String[] {"EUR", "USD", "JPY", "BGN", "CZK", "DKK", "EEK", "GBP", "HUF", "LTL", "LVL", "PLN", "RON", "SEK", "CHF", "NOK", "HRK", "RUB", "TRY", "AUD", "BRL", "CAD", "CNY", "HKD", "IDR", "INR", "KRW", "MXN", "MYR", "NZD", "PHP", "SGD", "THB", "ZAR"}
//        		};
//        // Countries flags
////        private int flags[] =
////            new int[] {R.drawable.usa, R.drawable.canada, R.drawable.ukraine, R.drawable.france};
//        
//        /**
//         * Constructor
//         */
//        protected CountryAdapter(Context context) {
//            super(context, R.layout.country_layout, NO_RESOURCE);
//            
//            setItemTextResource(R.id.country_name);
//        }
//
////        @Override
////        public View getItem(int index, View cachedView, ViewGroup parent) {
////            View view = super.getItem(index, cachedView, parent);
////            ImageView img = (ImageView) view.findViewById(R.id.flag);
////            img.setImageResource(flags[index]);
////            return view;
////        }
//        
//        @Override
//        public int getItemsCount() {
//            return countries[Selected].length;
//        }
//        
//        @Override
//        protected CharSequence getItemText(int index) {
//            return countries[Selected][index];
//        }
//    }
    
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
				 HttpPost httppost = new
				 HttpPost("http://www.google.com/ig/calculator?q="+params[0]);
				 System.out.println("Result::"+"http://www.google.com/ig/calculator?q="+params[0]);
//				 httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				 HttpResponse response = httpclient.execute(httppost);
				 HttpEntity entity = response.getEntity();
				 InputStream is = entity.getContent();
				
				 BufferedReader reader = new BufferedReader(new
				 InputStreamReader(is, "iso-8859-1"), 8);
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
				System.out.println(foo + "::" + result + "RESULT : -" + output + "-");
				return result;
				
	 		} catch (Exception e) {
				System.out.println("Error parsing data "+ e.toString());
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
