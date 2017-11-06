package olog.dev.leeto.app;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;

@GlideModule
public final class AppGlideModule extends com.bumptech.glide.module.AppGlideModule{

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setLogLevel(Log.ERROR)
                .build(context);
    }


    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
