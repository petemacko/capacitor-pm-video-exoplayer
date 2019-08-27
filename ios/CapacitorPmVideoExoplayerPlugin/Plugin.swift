import Foundation
import Capacitor
import AVKit

@objc(ExoplayerPlugin)
public class ExoplayerPlugin: CAPPlugin {
    //
    // This code copied from capacitor-video-player, then modified.
    // https://github.com/jepiqueau/capacitor-video-player
    //
    // The ExoplayerPlugin is meant to provide expanded video capabilities for
    // Android devices. The iOS player is provided as a convenience.
    //
    var avPlayer: AVPlayer!
    var currentCall: CAPPluginCall?
    var videoPlayer: AVPlayerViewController!

    override public func load() {
        NotificationCenter.default.addObserver(self, selector: #selector(playerRanToEnd),
                                               name:Notification.Name.AVPlayerItemDidPlayToEndTime, object: nil)

    }

    @objc func play(_ call: CAPPluginCall)  {
        
        guard let urlString = call.getString("videoUrl") else {
            let error = "ExoplayerPlugin: 'videoUrl' must be provided"
            NSLog(error)
            call.reject(error)
            return
        }
        
        guard let url = URL(string: urlString) else {
            let error = "ExoplayerPlugin: could not create a valid URL from 'videoUrl' '\(urlString)'"
            NSLog(error)
            call.reject(error)
            return
        }
        
        self.videoPlayer = AVPlayerViewController()
        DispatchQueue.main.async {
            self.avPlayer = AVPlayer(url: url)
            self.videoPlayer.player = self.avPlayer
            if let vc = self.bridge?.viewController {
                vc.present(self.videoPlayer, animated: true, completion:{
                    self.currentCall = call
                    self.avPlayer.play()
                });
            }
        }
    }

    @objc func playerRanToEnd() {
        if let call = currentCall {
            currentCall = nil;
            self.videoPlayer.dismiss(animated: true,completion: nil)
            call.success([ "result": true])
        }
    }
}
