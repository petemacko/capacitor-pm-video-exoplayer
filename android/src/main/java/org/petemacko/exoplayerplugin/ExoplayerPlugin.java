package org.petemacko.exoplayerplugin;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

@NativePlugin(requestCodes = {ExoplayerPlugin.Exoplayer.Video})
public class ExoplayerPlugin extends Plugin {

    public enum MediaTypes {
        Unknown,
        Hls,
        Dash,
        SmoothStreaming
    }

    private static final String TAG = "ExoplayerPlugin";

    @PluginMethod()
    public void play(PluginCall call) {
        String url = call.getString("videoUrl");
        if (url == null) {
            call.reject("'videoUrl' must be provided");
            return;
        }

        String mediaTypeString = call.getString("mediaType");
        if (mediaTypeString == null) {
            mediaTypeString = "hls";
        }

        mediaTypeString = mediaTypeString.toLowerCase();

        MediaTypes mediaType = MediaTypes.Unknown;
        switch (mediaTypeString) {
            case "hls":
                mediaType = MediaTypes.Hls;
                break;
            case "dash":
                mediaType = MediaTypes.Dash;
                break;
            case "smoothstreaming":
                mediaType = MediaTypes.SmoothStreaming;
                break;
        }

        if(mediaType == MediaTypes.Unknown) {
            call.reject("Media type '"+ mediaTypeString +"' is not supported. Supported media types are: hls, dash, smoothstreaming.");
            return;
        }

        Uri uri = Uri.parse(url);
        Intent intent = new Intent(getActivity(), ExoplayerActivity.class);
        intent.putExtra("videoUri", uri);
        intent.putExtra("mediaType", mediaType);

        startActivityForResult(call, intent, ExoplayerPlugin.Exoplayer.Video);
        saveCall(call);
    }

    @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        super.handleOnActivityResult(requestCode, resultCode, data);
        PluginCall call = getSavedCall();
        if (call == null) {
            return;
        }
        JSObject j = new JSObject();
        if (requestCode == ExoplayerPlugin.Exoplayer.Video) {
            if (data == null) {
                j.put("result", false);
            } else {
                j.put("result", data.getBooleanExtra("result", false));
            }
            call.resolve(j);
            return;
        }
        call.reject("rejected plugin result");
    }

    public interface Exoplayer {
        int Video = 12112;
    }
}
