import UIKit
import React

@objc(KundliViewManager)
class KundliViewManager: RCTViewManager {

  override func view() -> UIView! {
    return KundliView()
  }

  override static func requiresMainQueueSetup() -> Bool {
    return true
  }

 
}
