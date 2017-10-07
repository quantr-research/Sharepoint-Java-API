# Sharepoint Java API
This library is calling SharePoint restful API https://msdn.microsoft.com/en-us/library/office/dn499819.aspx

# Why make this library
Calling SharePoint restful api using java is not that simple, first need to get the request token, secondly you need to get the rtfa and FedAuth keys. Now if you calling http-get methods, you need to stuck those keys into cookies. If you are calling http-post methods, you need to get the X-RequestDigest key from other request first, so it would be very very trouble and this library handled all these for you

# Who are we
We are quantr development team, we are a sharepoint dev company http://www.quantr.hk

# Example
```
JPasswordField pwd = new JPasswordField(10);
int action = JOptionPane.showConfirmDialog(null, pwd, "Please input office365 password", JOptionPane.OK_CANCEL_OPTION);
String password = new String(pwd.getPassword());
String domain = "quantr";
Pair < String, String > token = SPOnline.login("peter@quantr.hk", password, domain);
if (token != null) {
    String jsonString = SPOnline.post(token, domain, "/_api/contextinfo", null, null);
    System.out.println(CommonLib.prettyFormatJson(jsonString));
    JSONObject json = new JSONObject(jsonString);
    String formDigestValue = json.getJSONObject("d").getJSONObject("GetContextWebInformation").getString("FormDigestValue");
    System.out.println("FormDigestValue=" + formDigestValue);

    // get all sites
    jsonString = SPOnline.get(token, domain, "/_api/web");
    System.out.println(CommonLib.prettyFormatJson(jsonString));

    // add a site
    jsonString = SPOnline.post(token, domain, "/_api/web/webs/add", "{ 'parameters': { '__metadata': { 'type': 'SP.WebCreationInformation' },\n" +
        "    'Title': 'Social Meetup', 'Url': 'social', 'WebTemplate': 'MPS#3',\n" +
        "    'UseSamePermissionsAsParentSite': true } }", formDigestValue);
    System.out.println(CommonLib.prettyFormatJson(jsonString));
} else {
    System.err.println("Login failed");
}
```