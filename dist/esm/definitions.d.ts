declare module "@capacitor/core" {
    interface PluginRegistry {
        Exoplayer: IExoplayerPlugin;
    }
    enum MediaTypes {
        MediaTypes
    }
}
export interface VideoPlayerOptions {
    videoUrl: string;
    mediaType?: string;
}
export interface VideoPlayerResult {
    result?: boolean;
}
export interface IExoplayerPlugin {
    play(options: VideoPlayerOptions): Promise<VideoPlayerResult>;
}
