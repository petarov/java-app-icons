Java App Icons
===================

![App Store](https://www.apple.com/v/ios/app-store/d/images/overview/app_store_icon__fngcxe43zo2u_large.jpg)
![Google Play](https://www.gstatic.com/android/market_images/web/play_prism_hlock_2x.png)

Fetch urls or download app icons for any App Store or Play Store app. 

  - Requires `Java 11` or above
  - Zero transitive dependencies
  - Fluent builder interface
  - Supports non-blocking downloads

# Installation

Use Bintray's JCenter maven repository with Gradle.

```groovy
repositories {
    jcenter()
}

dependencies {
    implementation 'net.vexelon:java-app-icons:1.0' 
}
```

Or get the latest stable version into your POM file. 

```xml
<dependency>
  <groupId>net.vexelon</groupId>
  <artifactId>java-app-icons</artifactId>
  <version>1.0</version>
  <type>pom</type>
</dependency>
```

# Usage

## Quick Start

Fetch the Instagram App Store icon urls for the 3 default icon sizes: 60x, 100x and 512x pixels.

```java

var downloader = AppIcons.appstore().build();
downloader.getUrls("389801252").forEach(iconUrl -> System.out.println(iconUrl.getUrl()));

``` 

Do the same thing for the Google Play Instagram app. By default, fetches only a single app icon url of size 512x512.

```java

var downloader = AppIcons.playstore().build();
downloader.getUrls("com.instagram.android").forEach(iconUrl -> {
    System.out.println("URL:" + iconUrl.getUrl());
    System.out.println("Image type: " + iconUrl.getType());
    System.out.println("Image width: " + iconUrl.getWidth());
    System.out.println("Image height: " + iconUrl.getHeight());
});

```

Fetch the icon urls of several App Store apps with a single API call.

```java

appStoreDownloader.getMultiUrls(Set.of("389801252", "310633997")).forEach(
    (k, v) -> v.forEach(icon -> System.out.println(icon)));
playStoreDownloader.getMultiUrls(Set.of("com.instagram.android", "com.zhiliaoapp.musically")).forEach(
    (k, v) -> v.forEach(icon -> System.out.println(icon)));

```

## Specify Icon Sizes

If you only need certain app icon sizes for a given App Store app, then you can specify that in the builder.

```java

var downloader = AppIcons.appstore()
        .size60(false)
        .size100(false)
        .size512(true)
        .build();

```

It works a bit different for Google Play. In this case one may specify an arbitrary list of icon sizes as integer values.

```java

var downloader = AppIcons.playstore()
        .sizes(64, 100, 512) // sizes in pixels
        .build();

```

## Download and Save on Disk

There are use cases where one may wish to save the app icons directly to a path on disk. This can be easily done by using
the `getFiles()` API method where the `path` parameter specifies a folder path. Each icon file name is a `SHA-1` value of 
the corresponding url fetched. 

```java

var path = Path.of("/Users/Profit/Downloads");
var downloader = AppIcons.playstore().build();
downloader.getFiles("com.instagram.android", path).forEach(iconUrl -> {
    System.out.println("Path:" + iconUrl.getPath()); // e.g., 58bd9d753544bfb3364fe8cceda56d799c050ad6.png
    System.out.println("File extension: " + iconUrl.getExtension());
    System.out.println("Image width: " + iconUrl.getWidth());
    System.out.println("Image height: " + iconUrl.getHeight());
});

```

Multiple apps can be specified by using the `getMultiFiles()` API call.

```java

downloader.getMultiFiles(Set.of("com.instagram.android", "com.zhiliaoapp.musically"), path).forEach(iconUrl -> { ... });

```

## Proxy and Network Parameters

Connection timeout, proxy and user agent parameters are supported in both App Store and Google Play builders. Note that
currently only `HTTP/S` proxies are supported.

```java

var downloader = AppIcons.appstore()
        .timeout(60)            // connection timeout in seconds
        .userAgent("My-Fancy-App/1.0")
        .proxy(BuilderConfig.ProxyType.HTTP, "localhost", 1080, "user1", "pass1")
        .skipSSLVerify(false)  // do not verify HTTPS proxy certificate
        .build(); 

```

## Non-Blocking Downloads

Let's think big! Say you need to use this library to download icon files or fetch icon urls in a non-blocking way. Maybe this
matches your server architecture better or allows for better utilization of resources. The async downloader has you covered.

```java

var executorService = Executors.newCachedThreadPool();
var downloader = AppIcons.appstore()
        .country("us")
        .language("en")
        .buildAsync(executorService);

downloader.getUrls("com.instagram.android", new DownloadCallback<>() {
    @Override public void onError(String appId, Throwable t) {
        t.printStackTrace();
    }

    @Override public void onSuccess(String appId, List<IconURL> iconURLS) {
        iconURLS.stream().map(IconURL::toString).forEach(System.out::println);
    }
});

```

You'll need to provide your own `ExecutorService`, something that your server application probably already has instantiated.

The file download API works a bit differently in non-blocking mode. The inner download and save to disk operations are both 
non-blocking, therefore the callback gets notified as soon as a single icon entry gets processed for the apps specified.

```java

downloader.getMultiFiles(Set.of("com.zhiliaoapp.musically", "com.instagram.android"), path, new DownloadCallback<>() {
    @Override public void onError(String appId, Throwable t) {
        t.printStackTrace();
    }

    @Override public void onSuccess(String appId, IconFile file) {
        System.out.println("APP: " + appId + "  " + file);
    }
});

```


# License

[MIT](LICENSE)
