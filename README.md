# Kik API

## Installation

You can install the Kik API using either [Gradle](http://gradle.org) or [Maven](http://maven.apache.org). To include the library,
simply add the following dependency to your project:

### Maven (pom.xml)

```xml
<dependency>
    <groupId>com.kik</groupId>
    <artifactId>kik-api</artifactId>
    <version>1.0.2</version>
</dependency>
```

### Gradle (build.gradle)

```groovy
dependencies {
  compile 'com.kik:kik-api:1.0.2'
}
```

## Usage

### Opening a Kik User Profile

```java
KikClient.getInstance().openProfileForKikUsername(getActivity(), "kikteam");
```

### Sending a Kik Content Message

Two layout styles are available for your content messages: article and photo. You can specify the layout style that
best suits your content when composing the content message to send.

To send a content message using the API you must construct a `KikMessage` object with the parameters of your message
and send it using the `sendKikMessage(...)` method on `KikClient`. The examples in the following sections will show you how
to construct and send article and photo content messages.

#### Article

```java
KikArticleMessage message = new KikArticleMessage(
    getActivity(),
    "Title of your Article",
    "Text of your Article",
    "http://www.yourcontent.com/thecontent",
    null);

KikClient.getInstance().sendKikMessage(getActivity(), message);
```

- One of either ```title``` or ```text``` must be specified
- The ```contentUrl``` must be specified. This is the target URL that will be opened when the user taps on the message in Kik
- ```previewUrl``` is an optional image URL that can be specified to provide a thumbnail preview that will be displayed in your message

#### Photo

```java
KikPhotoMessage message = new KikPhotoMessage(
    getActivity(),
    "http://www.images.com/image.png",
    "http://www.images.com/image_preview.png");

KikClient.getInstance().sendKikMessage(getActivity(), message);
```

When sending an image URL, you can choose to specify a base64-encoded Data URI in place of a standard web URL. In this case, the data will be uploaded
and downloaded from the Kik servers instead of directly accessing the image via the provided web URL.

Alternatively, you can specify a `Bitmap`, `byte[]` or `Drawable` as the photo to send.

```java
// Create a photo message from a Bitmap object
Bitmap image = ...;
KikPhotoMessage message = new KikPhotoMessage(getActivity(), bitmap);
```

### Other Message Attributes

#### Forwardable

If you do not want to allow a user to forward a particular content message on to a friend you can set the `forwardable` attribute on a `KikMessage` to `false`. The default value for `forwardable` is `true`.

```java
...
message.forwardable = false;
```

#### Fallback URLs

With any content message, you can specify fallback URLs. When the user opens your content message, Kik will check your fallback URLs to see which URL is supported on the current platform and display a link to the user which will allow them to navigate to that URL.

A fallback URL can be any type of URL whether it is a link to a website (ie. `http://kik.com`) or a specialized scheme for a native app (ie. `kik://users/kikteam/profile`).

If your app supports both Android and iOS, you can add a link to the native scheme which would open your app on each respective platform. Also, if your app is not installed, Kik will detect that your native scheme is not handled. If this is the case, the next fallback URL in order will be presented to the user. This is an opportunity to add another fallback URL linking to the AppStore with an ITMS link or the Google Play Store with a web link.

```java
public KikMessage addFallbackURL(String fallbackURL, KikMessagePlatform platform)
```

##### Example

```java
...
// launch the app first if available
message.addFallbackUrl("test-iphone://api/launch", KikMessagePlatform.KIK_MESSAGE_PLATFORM_IPHONE);
message.addFallbackUrl("test-android://api/launch", KikMessagePlatform.KIK_MESSAGE_PLATFORM_ANDROID);

// fallback to launching the app store on the given platform
message.addFallbackUrl("itms-apps://itunes.apple.com/ca/app/kik/id357218860?mt=8", KikMessagePlatform.KIK_MESSAGE_PLATFORM_IPHONE);
message.addFallbackUrl("https://play.google.com/store/apps/details?id=kik.android&hl=en", KikMessagePlatform.KIK_MESSAGE_PLATFORM_ANDROID);
```

### Returning to Kik After Opening Content

When the user taps your content from within Kik and launches your app, they will want to return to Kik to continue their chat. From within your app, use the `backToKik(...)` method to return the user to their active chat once they have viewed the content in your app.

```java
KikClient.getInstance().backToKik(getActivity(0));
```

## Author

Kik Interactive

## License

Use of the Kik API is subject to the Terms & Conditions and the Acceptable Use Policy. See TERMS.md for details.

The source for KikAPI is available under the Apache 2.0 license. See the LICENSE.md file for details.

