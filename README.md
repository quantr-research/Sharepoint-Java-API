# Sharepoint Java API
This library is calling SharePoint restful API https://msdn.microsoft.com/en-us/library/office/dn499819.aspx

# Why make this library
Calling SharePoint restful api using java is not that simple, first need to get the request token, secondly you need to get the rtfa and FedAuth keys. Now if you calling http-get methods, you need to stuck those keys into cookies. If you are calling http-post methods, you need to get the X-RequestDigest key from other request first, so it would be very very trouble and this library handled all these for you

# Who are we
We are quantr development team, we are a sharepoint dev company http://www.quantr.hk

# Example
https://github.com/quantr-research/Sharepoint-Java-API/blob/master/src/test/java/hk/quantr/sharepoint/TestSPOnline.java
