# Driver app

### Add google-services.json to your project

### Set app content
 **Set app name** :
File name : `edelivery -> src -> main -> res -> values ->strings.xml`

File name : `build.gradle (app)`In `defaultConfig`
1. change applicationId
2. versionCode
3. versionName
4. FACEBOOK_APP_ID *if required
5. FB_LOGIN_PROTOCOL_SCHEME *if required
6. GOOGLE_ANDROID_API_KEY

 For places api we are using GOOGLE_ANDROID_API_KEY
 For other google api, we are using google key which will get from api response which is set in the admin panel.
 API name : “get_provider_setting_detail”
 Method name : `getAPIKeys`
        -`parseContent.parseProviderSettingDetail`

Ex:`preferenceHelper.putGoogleAutoCompleteKey(adminSettings.getAndroidProviderAppGoogleKey());`

**Set server URL for API and Image(Assets)**
1) BASE_URL = “http://192.168.0.165:8001/”


### Change app launcher icon and notification icon
File name: `edelivery -> src -> main -> res ->Image asset`

### Change Splash screen 



### Set provider path url for save captured image in internal storage 
File name :`provider_path.xml`
 
Ex : path="Android/data/your applicationId/files/Pictures"



### Change include subdomain 
File name : `network_security_config.xml`

Ex : `<domain includeSubdomains="true">192.168.0.165</domain> (From BASE_URL)`




### Change Theme color
File name : `eber -> src -> main -> res ->values->colors.xml`
`Change this color:`
```java 
<color name="color_app_theme_dark">#fb0000</color> 
<color name="color_app_theme_light">#fb0000</color> 
<color name="color_app_status_bar">#fb0000</color>
<color name="color_app_button">#fb0000</color> 
<color name="color_app_button_tint">#fb0000</color>
<color name="color_app_red_path">#fb0000</color>
<color name="color_app_button_dark">#fb0000</color>

```


### Change font 
Add your font file in `edelivery -> src -> main ->assets ->fonts`
Change in your all custom view (button, text, Edittext etc)
 
EX : 
```java 
private boolean setCustomFont(Context ctx, String asset) {
   try {
       if (typeface == null) {
           // Log.i(TAG, "asset:: " + "fonts/" + asset);
           typeface = Typeface.createFromAsset(ctx.getAssets(),
                   "fonts/Roboto_Regular_0.ttf");
      }

   } catch (Exception e) {
       AppLog.handleException(TAG, e);
       // Log.e(TAG, "Could not get typeface: " + e.getMessage());
       return false;
   }

   setTypeface(typeface);
   return true;
}

```



### Build project
 
**Check build variants (check which have BASE_URL)**

1. Select target device
2. Run project 

# AkiliAndroidDriverApp
