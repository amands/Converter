package slinfy.android.unitconverter;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class WheelDemo extends ListActivity {

	private static final int FIND_US = Menu.FIRST;
	private static final int ABOUT_APP = Menu.FIRST + 1;
	private static final int ABOUT_COMPANY = Menu.FIRST + 2;
	private static final int ABOUT_DOWNLOAD = Menu.FIRST + 3;

	private static final int DIALOG_ABOUT = 1;
	private static final int DIALOG_FINDUS = 2;
	private static final int DIALOG_COMPANY = 3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		String path = intent.getStringExtra("com.example.android.apis.Path");

		if (path == null) {
			path = "";
		}

		setListAdapter(new SimpleAdapter(this, getData(path),
				android.R.layout.simple_list_item_1, new String[] { "title" },
				new int[] { android.R.id.text1 }));
	}

	// For Menu

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem menu_item;

		super.onCreateOptionsMenu(menu);
		menu_item = menu.add(0, ABOUT_COMPANY, 0, R.string.szMenu_share);
		menu_item.setIcon(R.drawable.share);
		//
		// menu_item = menu.add(0, ABOUT_APP, 2, R.string.szMenu_Preference);
		// menu_item.setIcon(R.drawable.c_icon);

		menu_item = menu.add(0, FIND_US, 1, R.string.szMenu_About);
		menu_item.setIcon(R.drawable.aboutus);

		menu_item = menu.add(0, ABOUT_DOWNLOAD, 1, R.string.szMenu_download);
		menu_item.setIcon(R.drawable.more);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case ABOUT_DOWNLOAD:
			try {
				// Start the activity
				String url = "http://www.yahoo.com";
				Intent i = new Intent(Intent.ACTION_VIEW);
				Uri u = Uri.parse(url);
				i.setData(u);
				startActivity(i);
			} catch (Exception e) {
				// Raise on activity not found
				Toast toast = Toast.makeText(WheelDemo.this,
						"Browser not found.", Toast.LENGTH_SHORT);
			}
			break;

		case ABOUT_COMPANY:
			showDialog(DIALOG_COMPANY);
			break;

		case ABOUT_APP:
			showDialog(DIALOG_FINDUS);
			break;

		case FIND_US:
			showDialog(DIALOG_ABOUT);
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {

		case DIALOG_ABOUT:
			return new AboutDialog(this);

		case DIALOG_FINDUS:
			return createCurrencyEditDialog(this);

		case DIALOG_COMPANY:
			return new FindUsScreen(this);

		default:
			break;

		}
		return null;

	}

	private Dialog createCurrencyEditDialog(WheelDemo listViewWithImageActivity) {
		return null;
	}

	private Dialog createDIALOG_COMPANYEDIT(WheelDemo listViewWithImageActivity) {
		return null;
	}

	protected List getData(String prefix) {
		List<Map> myData = new ArrayList<Map>();

		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory("kankan.wheel.WHEEL_SAMPLE");

		PackageManager pm = getPackageManager();
		List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

		if (null == list)
			return myData;

		String[] prefixPath;

		if (prefix.equals("")) {
			prefixPath = null;
		} else {
			prefixPath = prefix.split("/");
		}

		int len = list.size();

		Map<String, Boolean> entries = new HashMap<String, Boolean>();

		for (int i = 0; i < len; i++) {
			ResolveInfo info = list.get(i);
			CharSequence labelSeq = info.loadLabel(pm);
			String label = labelSeq != null ? labelSeq.toString()
					: info.activityInfo.name;

			if (prefix.length() == 0 || label.startsWith(prefix)) {

				String[] labelPath = label.split("/");

				String nextLabel = prefixPath == null ? labelPath[0]
						: labelPath[prefixPath.length];

				if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
					addItem(myData,
							nextLabel,
							activityIntent(
									info.activityInfo.applicationInfo.packageName,
									info.activityInfo.name));
				} else {
					if (entries.get(nextLabel) == null) {
						addItem(myData, nextLabel,
								browseIntent(prefix.equals("") ? nextLabel
										: prefix + "/" + nextLabel));
						entries.put(nextLabel, true);
					}
				}
			}
		}

		Collections.sort(myData, sDisplayNameComparator);

		return myData;
	}

	private final static Comparator<Map> sDisplayNameComparator = new Comparator<Map>() {
		private final Collator collator = Collator.getInstance();

		public int compare(Map map1, Map map2) {
			return collator.compare(map1.get("title"), map2.get("title"));
		}
	};

	protected Intent activityIntent(String pkg, String componentName) {
		Intent result = new Intent();
		result.setClassName(pkg, componentName);
		return result;
	}

	protected Intent browseIntent(String path) {
		Intent result = new Intent();
		result.setClass(this, WheelDemo.class);
		result.putExtra("com.example.android.apis.Path", path);
		return result;
	}

	protected void addItem(List<Map> data, String name, Intent intent) {
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("title", name);
		temp.put("intent", intent);
		data.add(temp);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Map map = (Map) l.getItemAtPosition(position);

		Intent intent = (Intent) map.get("intent");
		startActivity(intent);
	}
}
