# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

####基础混淆####
#指定代码的压缩级别
-optimizationpasses 5

# 混淆时不使用大小写混合，混淆后的类名为小写
# windows大小写不敏感，linux下可以不用添加
-dontusemixedcaseclassnames

# 指定不去忽略非公共的库的类
# 默认跳过，有些情况下编写的代码与类库中的类在同一个包下，并且持有包中内容的引用，此时就需要加入此条声明
-dontskipnonpubliclibraryclasses

# 指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers

# 不做预检验，preverify是proguard的四个步骤之一
# Android不需要preverify，去掉这一步可以加快混淆速度
-dontpreverify

# 混淆后生成映射文件
# 包含有类名->混淆后类名的映射关系
# 然后使用printmapping指定映射文件的名称
-verbose
-printmapping priguardMapping.txt

# 指定混淆时采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不改变
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# 保护代码中的Annotation不被混淆
# 这在JSON实体映射时非常重要，比如fastJson
-keepattributes *Annotation*

# 避免混淆泛型
# 这在JSON实体映射时非常重要，比如fastJson
-keepattributes Signature

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable


####基础混淆 完成####

####针对应用混淆####

# 保留所有的本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留了继承自Activity、Application这些类的子类
# 因为这些子类有可能被外部调用
# 比如第一行就保证了所有Activity的子类不要被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService

# 如果有引用android-support-v4.jar包，可以添加下面这行
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.app.Fragment

# 保留Activity中的方法参数是view的方法，
# 从而我们在layout里面编写onClick就不会影响
-keepclassmembers class * extends android.app.Activity {
    public void * (android.view.View);
}

# 枚举类不能被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保留自定义控件(继承自View)不能被混淆
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(***);
    *** get* ();
}

# 保留Parcelable序列化的类不能被混淆
-keep class * implements android.os.Parcelable{
    public static final android.os.Parcelable$Creator *;
}

# 保留Serializable 序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
   static final long serialVersionUID;
   private static final java.io.ObjectStreamField[] serialPersistentFields;
   !static !transient <fields>;
   private void writeObject(java.io.ObjectOutputStream);
   private void readObject(java.io.ObjectInputStream);
   java.lang.Object writeReplace();
   java.lang.Object readResolve();
}
# 对R文件下的所有类及其方法，都不能被混淆
 -keepclassmembers class **.R$* {
     *;
 }

 #对于带有回调函数onXXEvent的，不能混淆
 -keepclassmembers class * {
     void *(**On*Event);
 }

#对实体类不进行混淆
#-keep class com.clxchina.chelixiang.Model.** {
-keep class zjl.com.oa.Response.**{
    public void set*(***);
    public *** get*();
    public *** is*();
}

#对内嵌类不进行混淆
-keep class zjl.com.oa.Adapter.*$* {
    *;
}

#-keep class com.clxchina.chelixiang.lecheng.**{
#    *;
#}

#对WebView不混淆
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String);
}
#对JavaScript不进行混淆
-keepclassmembers class com.clxchina.chelixiang.jzh.JZHSignCard$InJavaScriptLocalObj {
    <methods>;
}
-keepclassmembers class com.clxchina.chelixiang.jzh.WebViewActivity$InJavaScriptLocalObj {
    <methods>;
}

-dontwarn

####针对应用混淆 完成####

####混淆引用的第三方库和SDK####

#不对阿里云SDK进行混淆
-keep class com.alivc.player.**{*;}
-keep class com.aliyun.vodplayer.**{*;}
-keep class com.aliyun.vodplayerview.**{*;}


#不对ShareSDK进行混淆
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}

#不对OcrSDK进行混淆
-keep class com.baidu.ocr.sdk.**{*;}
-keep class com.baidu.idl.**{*;}
-dontwarn com.baidu.ocr.**
-dontwarn com.baidu.idl.**

#不对banner进行混淆
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
# banner 的混淆代码
-keep class com.youth.banner.** {
     *;
}
# fastjson 的混淆代码
-keepattributes Signature
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.*{
    *;
}
-keep class com.***.utils.*{
    *;
}
-keep class com.***.Utils.*{
    *;
}

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

#okhttputils 的混淆代码
-dontwarn com.zhy.http.**
-keep class com.zhy.http.**{*;}
#okhttp 的混淆代码
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
#okio 的混淆代码
-dontwarn okio.**
-keep class okio.**{*;}
#facebook 的混淆代码
-dontwarn com.facebook.**
-keep class com.facebook.**{*;}

-keepclassmembers class com.lechange.common.play.PlayListenerAdapter {
    void onPlayerResult(String , type);
    void onResolutionChanged(int , height);
 }

-dontwarn com.jaeger.**
-keep class com.jaeger.**{
    *;
 }

-dontwarn com.kyleduo.**
-keep class com.kyleduo.**{
    *;
 }

-dontwarn info.hoang8f.**
-keep class info.hoang8f.**{
    *;
 }
-dontwarn uk.co.senab..**
-keep class uk.co.senab..**{
    *;
 }

-dontwarn org.apache.commons.**
-keep class org.apache.commons.**{
    *;
 }

-dontwarn com.google.zxing.**
-keep class com.google.zxing.**{
    *;
 }

-dontwarn com.uuch.adlibrary.**
-keep class com.uuch.adlibrary.**{
    *;
 }

-dontwarn com.allenliu.**
-keep class com.allenliu.**{
    *;
 }

-dontwarn android.support.multidex.**
-keep class android.support.multidex.**{
    *;
 }

-dontwarn cn.jiguan.**
-keep class cn.jiguan.**{
    *;
 }
-dontwarn com.lechange.**
-keep class com.lechange.**{
    *;
 }
 #butterknife
 -keep class butterknife.** { *; }
 -dontwarn butterknife.internal.**
 -keep class **$$ViewBinder { *; }
 -keepclasseswithmembernames class * {
     @butterknife.* <fields>;
 }
 -keepclasseswithmembernames class * {
     @butterknife.* <methods>;
 }

# Retrofit
-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions

# okhttp
-dontwarn okio.**

# Gson
-keep class zjl.com.oa.Response.**{*;}
-keep class zjl.com.oa.Base.**{*;}

#rxjava
-dontwarn rx.**
-keep class rx.** { *; }

-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#GSYVideoPlayer
-keep class com.shuyu.gsyvideoplayer.video.** { *; }
-dontwarn com.shuyu.gsyvideoplayer.video.**
-keep class com.shuyu.gsyvideoplayer.video.base.** { *; }
-dontwarn com.shuyu.gsyvideoplayer.video.base.**
-keep class com.shuyu.gsyvideoplayer.utils.** { *; }
-dontwarn com.shuyu.gsyvideoplayer.utils.**
-keep class tv.danmaku.ijk.** { *; }
-dontwarn tv.danmaku.ijk.**

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#Mob ShareSDK
-keep class cn.sharesdk.**{*;}
	-keep class com.sina.**{*;}
	-keep class **.R$* {*;}
	-keep class **.R{*;}
	-keep class com.mob.**{*;}
	-dontwarn com.mob.**
	-dontwarn cn.sharesdk.**
	-dontwarn **.R$*

####混淆引用的第三方库和SDK 完成####
