
  Pod::Spec.new do |s|
    s.name = 'CapacitorPmVideoExoplayer'
    s.version = '0.0.1'
    s.summary = 'Capacitor plugin for playing videos via ExoPlayer on iOS and Android. Since this is a podspec, guess which platform this is for.'
    s.license = 'MIT'
    s.homepage = 'https://github.com/petemacko/capacitor-pm-video-exoplayer'
    s.author = 'Pete Macko'
    s.source = { :git => 'https://github.com/petemacko/capacitor-pm-video-exoplayer', :tag => s.version.to_s }
    s.source_files = 'ios/CapacitorPmVideoExoplayerPlugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '11.0'
    s.dependency 'Capacitor'
  end
