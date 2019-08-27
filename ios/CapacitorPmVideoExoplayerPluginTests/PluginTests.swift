import XCTest
import Capacitor
@testable import CapacitorPmVideoExoplayer

class PluginTests: XCTestCase {
    
    override func setUp() {
        super.setUp()
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }
    
    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        super.tearDown()
    }
    
    func testForEcho() {
        let value = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"
        let plugin = ExoplayerPlugin()
        
        let call = CAPPluginCall(callbackId: "test", options: [
            "videoUrl": value
        ], success: { (result, call) in            
        }, error: { (err) in
            XCTFail("Error shouldn't have been called")
        })
        
        plugin.play(call!)
    }
}
