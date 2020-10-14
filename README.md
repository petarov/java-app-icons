Java App Icons
===================

![build](https://github.com/petarov/java-app-icons/workflows/CI%20Build/Latest)/badge.svg)
[![JitPack](https://jitpack.io/v/petarov/java-app-icons.svg)](https://jitpack.io/#petarov/java-app-icons)
[![BinTray](https://api.bintray.com/packages/petarov/java-libs/java-app-icons/images/download.svg) ](https://bintray.com/petarov/java-libs/java-app-icons/_latestVersion)

![App Store](https://www.apple.com/v/ios/app-store/d/images/overview/app_store_icon__fngcxe43zo2u_large.jpg)
![Google Play](https://www.gstatic.com/android/market_images/web/play_prism_hlock_2x.png)


Fetch urls or download app icons for any App Store or Play Store app. 

  - Requires `Java 11` or above
  - Zero transitive dependencies
  - Fluent builder interface
  - Supports non-blocking downloads
  
  
See [java-app-icons-bin](https://github.com/petarov/java-app-icons-bin) for an executable CLI.

# Installation

Use with Gradle:

```gradle
repositories {
    jcenter()
}

dependencies {
    implementation 'net.vexelon:java-app-icons:1.2'
}
```

Use with Maven:

```xml
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
    <groupId>com.github.petarov</groupId>
    <artifactId>java-app-icons</artifactId>
    <version>1.2</version>
</dependency>
```

For more info see the [JitPack](https://jitpack.io/#petarov/java-app-icons) or [JCenter](https://bintray.com/petarov/java-libs/java-app-icons) setup pages.

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
    System.out.println("Image type: " + iconUrl.getType()); // PNG
    System.out.println("Image width: " + iconUrl.getWidth()); // 512
    System.out.println("Image height: " + iconUrl.getHeight()); // 512
});

```

Fetch the icon urls of several App Store apps with a single API call.

```java

appStoreDownloader.getMultiUrls(Set.of("389801252", "310633997")).forEach(
    (k, v) -> v.forEach(iconUrl -> System.out.println(iconUrl)));
playStoreDownloader.getMultiUrls(Set.of("com.instagram.android", "com.zhiliaoapp.musically")).forEach(
    (k, v) -> v.forEach(iconUrl -> System.out.println(iconUrl)));

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
the `getFiles()` API method where the `path` parameter specifies a folder path. By default, each icon file name is a 
`SHA-1` value of the corresponding url fetched.

```java

var path = Path.of("/Users/Profit/Downloads");
var downloader = AppIcons.playstore().build();
downloader.getFiles("com.instagram.android", path).forEach(iconFile -> {
    System.out.println("Path:" + iconFile.getPath()); // e.g., 58bd9d753544bfb3364fe8cceda56d799c050ad6.png
    System.out.println("File extension: " + iconFile.getExtension());
    System.out.println("Image width: " + iconFile.getWidth());
    System.out.println("Image height: " + iconFile.getHeight());
});

```

Multiple apps can be specified by using the `getMultiFiles()` API call.

```java

downloader.getMultiFiles(Set.of("com.instagram.android", "com.zhiliaoapp.musically"), path).forEach(iconUrl -> { ... });

```

The naming of the files can be specified using `namingStrategy` in the builder. Instead of using `SHA-1` values, this
strategy will use a concatenation of the app id and icon image size.

```java

AppIcons.playstore().namingStrategy(BuilderConfig.NamingStrategy.APPID_AND_SIZE).build();
// com.instagram.android-512x.png
AppIcons.appstore().namingStrategy(BuilderConfig.NamingStrategy.APPID_AND_SIZE).build();
// 389801252-512x.jpg

```

Using a custom naming strategy is also possible.

```java

AppIcons.appstore().namingStrategy(((appId, iconURL) -> {
  return appId + "." + iconURL.getType().toLowerCase();
})).build();

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

Let's say you need to use this library to download icon files or fetch icon urls in a non-blocking way. Maybe this
matches your server architecture better or allows for better utilization of resources. The non-blocking downloader has you covered.

```java

var executorService = Executors.newCachedThreadPool();
var downloader = AppIcons.appstore()
        .country("us")
        .language("en")
        .buildNio(executorService);

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
There are different strategies about what [type of ExecutorService](https://dzone.com/articles/java-executor-service-types) 
to use depending on the particular use case.

The file download API works a bit differently in non-blocking mode. The inner download and save to disk operations are both 
non-blocking, therefore the callback gets notified as soon as a single icon entry gets processed for the apps specified.

```java

downloader.getMultiFiles(Set.of("com.zhiliaoapp.musically", "com.instagram.android"), path, new DownloadCallback<>() {
    @Override public void onError(String appId, Throwable t) {
        t.printStackTrace();
    }

    @Override public void onSuccess(String appId, IconFile iconFile) {
        System.out.println("App=" + appId + " File=" + iconFile);
    }
});

```


# License

[MIT](LICENSE)
