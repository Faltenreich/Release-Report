-keepattributes EnclosingMethod
-keep class com.faltenreich.release.data.model.** { *; }

##---------------Begin: Navigation Components ----------
-keep class androidx.navigation.** { *; }
##---------------End: Navigation Components ----------

##---------------Begin: Glide ----------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
##---------------End: Glide ----------