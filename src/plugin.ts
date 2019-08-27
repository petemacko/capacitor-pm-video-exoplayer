import {Plugins} from '@capacitor/core';

import {IExoplayerPlugin, VideoPlayerOptions, VideoPlayerResult} from "./definitions";

const {ExoplayerPlugin} = Plugins;

export declare enum AvailableMediaTypes {
    Dash = "dash",
    Hls = "hls",
    SmoothStreaming = "smoothstreaming"
}

export class Exoplayer implements IExoplayerPlugin {
    play(options: VideoPlayerOptions): Promise<VideoPlayerResult> {
        const mt = options.mediaType;
        const mediaType = mt === undefined || !!!mt ? 'hls' : mt + '';
        console.log(mediaType);
        return ExoplayerPlugin.play({videoUrl: options.videoUrl, mediaType});
    }
}
