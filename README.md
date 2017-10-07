# Sharepoint Java API
This library is calling SharePoint restful API https://msdn.microsoft.com/en-us/library/office/dn499819.aspx

# Example

```
		String password = "your password";
		String domain = "quantr";   //  http://quantr.sharepoint.com
		Pair<String, String> token = SPOnline.login("peter@quantr.hk", password, domain);
		if (token != null) {
			String json = SPOnline.web(token, domain);
			System.out.println(CommonLib.prettyFormatJson(json));
		}
```