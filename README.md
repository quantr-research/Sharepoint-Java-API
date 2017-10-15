# Sharepoint Java API
This library is calling SharePoint restful API https://msdn.microsoft.com/en-us/library/office/dn499819.aspx

## Why make this library
Calling SharePoint restful api using java is not that simple, first need to get the request token, secondly you need to get the rtfa and FedAuth keys. Now if you calling http-get methods, you need to stuck those keys into cookies. If you are calling http-post methods, you need to get the X-RequestDigest key from other request first, so it would be very very trouble and this library handled all these for you

## Who are we
We are quantr development team, we are a sharepoint dev company http://www.quantr.hk

## Example
https://github.com/quantr-research/Sharepoint-Java-API/blob/master/src/test/java/hk/quantr/sharepoint/TestSPOnline.java

## Compile

This library rely on peter-swing library https://github.com/mcheung63/peter-swing

1. git clone https://github.com/mcheung63/peter-swing.git
2. cd peter-swing
3. mvn clean install
4. cd ..
5. cd Sharepoint-Java-API
6. mvn clean package
7. The compiled jar file is in target folder, you can use it in your project now

## Becareful
		
Please encode the parameter yourself:

There is space in the parameter, so dont just pass it -> String jsonString = SPOnline.get(token, serverInfo.domain, serverInfo.path + "/_api/web/lists?$select=ID,Title&$filter=basetype eq 1&$orderby=title");

do:

Encode it : String jsonString = SPOnline.get(token, serverInfo.domain, serverInfo.path + "/_api/web/lists?$select=ID,Title&$filter=" + URLEncoder.encode("basetype eq 1", "utf-8") + "&$orderby=title");