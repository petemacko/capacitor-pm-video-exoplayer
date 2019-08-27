import { Plugins } from '@capacitor/core';
const { ExoplayerPlugin } = Plugins;
export class Exoplayer {
    play(options) {
        const mt = options.mediaType;
        const mediaType = mt === undefined || !!!mt ? 'hls' : mt + '';
        console.log(mediaType);
        return ExoplayerPlugin.play({ videoUrl: options.videoUrl, mediaType });
    }
}
//# sourceMappingURL=plugin.js.map