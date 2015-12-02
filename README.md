# Kik API

## Installation

You can install the Kik API using either [Gradle](http://gradle.org) or [Maven](http://maven.apache.org). To include the library,
simply add the following dependency to your project:

### Maven (pom.xml)

```xml
<dependency>
    <groupId>com.kik</groupId>
    <artifactId>kik-api</artifactId>
    <version>1.1-BETA</version>
</dependency>
```

### Gradle (build.gradle)

```groovy
dependencies {
  compile 'com.kik:kik-api:1.1-BETA'
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


#### Video

```java
KikVideoMessage message = new KikVideoMessage(
    getActivity(),
    "http://www.videos.com/video.mp4",
    "http://www.videos.com/video_preview.png");

KikClient.getInstance().sendKikMessage(getActivity(), message);
```

Video files should be encoded using H.264 with baseline profile for video and AAC for audio. The container should be mp4. Video files must be no larger than 15mb and no longer than 2 minutes in duration. The provided preview image should match the first frame of the video and have the same aspect ratio as the video.

If a video is marked to autoplay using the `videoShouldAutoplay` property, it must be no larger than 1mb. Developers are encouraged to test that their video content plays back reliably on a range of iOS and Android devices. 

Alternatively, you can specify a `Uri` object referencing a video on disk. In this case, the video data will be uploaded to and hosted on the Kik servers.

```java
// Create a video message from a video Uri
Uri videoUri = ...
KikVideoMessage message = new KikVideoMessage(
    getActivity(),
    videoUri);
```

If you want your application to send a video that has no URI already referencing it, for example a video file stored in your application's internal storage, you must make use of the `KikContentProvider` by adding the following to your `AndroidManifest.xml`:

```xml
<provider
    android:name="com.kik.kikapi.KikContentProvider"
    android:authorities="com.your.company.name"
    android:exported="true" />
```

and calling `KikContentProvider.init("com.your.company.name")`. Note that the authority declared in the manifest must be globally unique, and it must be the same string passed to `KikContentProvider.init()`. You may then call `KikContentProvider.getContentUri(filepath)` to retrieve a `Uri` referencing the video to be shared.

```java
// Create a video message from a video file
File videoFile = new File(getApplicationContext().getCacheDir(), "video.mp4");
Uri videoUri = KikContentProvider.getContentUri(videoFile.getPath());
KikVideoMessage message = new KikVideoMessage(
    getActivity(),
    videoUri);
```

##### Video Message Attributes

###### Looping
If the `videoShouldLoop` property is set to `true`, the video will loop when played back. In other words, when playback reaches the end of the video it will start again at the beginning. The default value for `videoShouldLoop` is `false`.

###### Autoplay
If the `videoShouldAutoplay` property is set to `true `, the video will start playing automatically when it appears in a chat. The video will be muted by default with an unmute button provided to the user. When the unmute button is tapped, playback will restart from the beginning of the video with audio enabled. If the `videoShouldBeMuted` property is set to `true `, the unmute button will not be shown. If `videoShouldAutoplay` is set to `true ` the attached or linked video data must be less than 1mb. The default value for `videoShouldAutoplay` is `false `.

###### Muted
If the `videoShouldBeMuted` property is set to `true`, the video will be played with the audio track muted. In the case that `videoShouldAutoplay` is set to `true`, no unmute button will be shown. The default value for `videoShouldBeMuted` is `false`.

```java
...
KikVideoMessage message = ...
message.setShouldLoop(true);
message.setShouldAutoplay(true);
message.setShouldBeMuted(true);
```

### Other Message Attributes

#### Forwardable

If you do not want to allow a user to forward a particular content message on to a friend you can set the `forwardable` attribute on a `KikMessage` to `false`. The default value for `forwardable` is `true`.

```java
...
message.setForwardable(false);
```

#### Disallow Saving

If you do not want to allow a user to save a particular photo or video message you can set the `disallowSave` attribute on a `KikMessage` to `true`. The default value for `disallowSave` is `false`.

```java
...
message.setDisallowSave(true);
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

