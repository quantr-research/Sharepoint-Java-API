// License : Apache License Version 2.0  https://www.apache.org/licenses/LICENSE-2.0
package hk.quantr.sharepoint;

import com.peterswing.CommonLib;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONObject;
import org.junit.Test;

/**
 *
 * @author Peter <mcheung63@hotmail.com>
 */
public class TestSPOnline {

	@Test
	public void test1() {
		JPasswordField pwd = new JPasswordField(10);
		int action = JOptionPane.showConfirmDialog(null, pwd, "Please input office365 password", JOptionPane.OK_CANCEL_OPTION);
		String password = new String(pwd.getPassword());
		String domain = "quantr";
		Pair<String, String> token = SPOnline.login("peter@quantr.hk", password, domain);
		if (token != null) {
			String jsonString = SPOnline.post(token, domain, "/_api/contextinfo", null, null);
			System.out.println(CommonLib.prettyFormatJson(jsonString));
			JSONObject json = new JSONObject(jsonString);
			String formDigestValue = json.getJSONObject("d").getJSONObject("GetContextWebInformation").getString("FormDigestValue");
			System.out.println("FormDigestValue=" + formDigestValue);

//			// get all webs
//			jsonString = SPOnline.get(token, domain, "/_api/web/webs");
//			if (jsonString != null) {
//				System.out.println(CommonLib.prettyFormatJson(jsonString));
//			}

			// get all site collections
			jsonString = SPOnline.get(token, domain, "/_api/search/query?querytext='contentclass:sts_site'");
			if (jsonString != null) {
				System.out.println(CommonLib.prettyFormatJson(jsonString));
			}

//			// get all sites
//			jsonString = SPOnline.get(token, domain, "/_api/site");
//			if (jsonString != null) {
//				System.out.println(CommonLib.prettyFormatJson(jsonString));
//			}

//			// add a site
//			jsonString = SPOnline.post(token, domain, "/_api/web/webs/add", "{ 'parameters': { '__metadata': { 'type': 'SP.WebCreationInformation' },\n"
//					+ "    'Title': 'Social Meetup', 'Url': 'social', 'WebTemplate': 'MPS#3',\n"
//					+ "    'UseSamePermissionsAsParentSite': true } }", formDigestValue);
//			if (jsonString != null) {
//				System.out.println(CommonLib.prettyFormatJson(jsonString));
//			}
//
//			// change site description
//			jsonString = SPOnline.post(token, domain, "/social/_api/web", "{ '__metadata': { 'type': 'SP.Web' }, 'Description': 'my testing description',\n"
//					+ "    'EnableMinimalDownload': false }", formDigestValue, true);
//			if (jsonString != null) {
//				System.out.println(CommonLib.prettyFormatJson(jsonString));
//			}
//
//			// delete a site
//			jsonString = SPOnline.delete(token, domain, "/social/_api/web", formDigestValue);
//			if (jsonString != null) {
//				System.out.println(CommonLib.prettyFormatJson(jsonString));
//			}
//
//			// get all lists
//			jsonString = SPOnline.get(token, domain, "/_api/web/lists");
//			if (jsonString != null) {
//				System.out.println(CommonLib.prettyFormatJson(jsonString));
//			}
//
//			// get all lists with tile and guid only
//			jsonString = SPOnline.get(token, domain, "/_api/web/lists?$select=ID,Title");
//			if (jsonString != null) {
//				System.out.println(CommonLib.prettyFormatJson(jsonString));
//			}
//
//			// get all list by specific ID
//			jsonString = SPOnline.get(token, domain, "/_api/web/lists(guid'8f0cd839-88c1-4fea-ae05-19f7df1f2645')");
//			if (jsonString != null) {
//				System.out.println(CommonLib.prettyFormatJson(jsonString));
//			}
//
//			// get all list by specific title
//			jsonString = SPOnline.get(token, domain, "/_api/web/lists/GetByTitle('Workflow%20Tasks')");
//			if (jsonString != null) {
//				System.out.println(CommonLib.prettyFormatJson(jsonString));
//			}
//
//			// create a list called Peter
//			jsonString = SPOnline.post(token, domain, "/_api/web/lists", "{ '__metadata': { 'type': 'SP.List' }, 'AllowContentTypes': true, 'BaseTemplate': 100,\n"
//					+ "    'ContentTypesEnabled': true, 'Description': 'created by SharePoint-Java-API', 'Title': 'Peter' }", formDigestValue);
//			if (jsonString != null) {
//				System.out.println(CommonLib.prettyFormatJson(jsonString));
//			}
//
//			// change list name from Peter to John
//			jsonString = SPOnline.post(token, domain, "/_api/web/lists/GetByTitle('Peter')", "{ '__metadata': { 'type': 'SP.List' }, 'AllowContentTypes': true, 'BaseTemplate': 100,\n"
//					+ "    'ContentTypesEnabled': true, 'Description': 'new description', 'Title': 'John' }", formDigestValue, true);
//			if (jsonString != null) {
//				System.out.println(CommonLib.prettyFormatJson(jsonString));
//			}
//
//			// add column to list John, for FieldTypeKind references to https://msdn.microsoft.com/en-us/library/microsoft.sharepoint.client.fieldtype.aspx
//			jsonString = SPOnline.post(token, domain, "/_api/web/lists/GetByTitle('John')/Fields", "{ '__metadata': { 'type': 'SP.Field' }, 'FieldTypeKind': 11, 'Title': 'my new column'}", formDigestValue);
//			if (jsonString != null) {
//				System.out.println(CommonLib.prettyFormatJson(jsonString));
//			}
//			// insert an item to list John, the list was called Peter by creation, so it is SP.Data.PeterListItem, not SP.Data.JohnListItem
//			jsonString = SPOnline.post(token, domain, "/_api/web/lists/GetByTitle('John')/items", "{ '__metadata': { 'type': 'SP.Data.PeterListItem' },\n"
//					+ "'Title': 'test1', "
//					+ "'my_x0020_new_x0020_column': {'Url': 'http://www.google.com', 'Description': 'Google USA'}}", formDigestValue);
//			if (jsonString != null) {
//				System.out.println(CommonLib.prettyFormatJson(jsonString));
//			}
//
//			// get list items from list John
//			jsonString = SPOnline.get(token, domain, "/_api/web/lists/GetByTitle('John')/items");
//			if (jsonString != null) {
//				System.out.println(CommonLib.prettyFormatJson(jsonString));
//			}
//
//			// delete list john
//			jsonString = SPOnline.delete(token, domain, "/_api/web/lists/GetByTitle('John')", formDigestValue);
//			if (jsonString != null) {
//				System.out.println(CommonLib.prettyFormatJson(jsonString));
//			}
		} else {
			System.err.println("Login failed");
		}
	}
}
