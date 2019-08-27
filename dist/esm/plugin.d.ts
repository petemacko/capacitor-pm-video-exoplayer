import { IExoplayerPlugin, VideoPlayerOptions, VideoPlayerResult } from "./definitions";
export declare enum AvailableMediaTypes {
    Dash = "dash",
    Hls = "hls",
    SmoothStreaming = "smoothstreaming"
}
export declare class Exoplayer implements IExoplayerPlugin {
    play(options: VideoPlayerOptions): Promise<VideoPlayerResult>;
}
