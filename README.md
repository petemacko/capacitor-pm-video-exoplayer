# capacitor-pm-video-exoplayer
Capacitor plugin that plays HLS video on Android via the ExoPlayer library and on iOS via AVPlayer (standard AVKit). 

```
import {Exoplayer} from 'capacitor-pm-video-exoplayer';

export class ThankDawgItsNotCordovaAnyLongerPage implements OnInit { 

    playSomeVideo() {
    
        const videoUrl = 'https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8';
        
        new Exoplayer().play({videoUrl, mediaType: 'hls'}).then().catch(reason => {
            this.appBackbone.logError(`Switching to web HLS video player because: ${JSON.stringify(reason)}`);
            this.showVideoUsingFallbackWebViewer(videoUrl);
        });
    
    }

    showVideoUsingFallbackWebViewer(videoUrl: string) {
        // Eww... hls.js stuff 
    }

}
```
