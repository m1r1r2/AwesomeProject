import UIKit

@objc(KundliView)
class KundliView: UIView {
    
    private var housesData: [[String: Any]] = []
    private var lagnaSign: Int = 1
    
    @objc var data: NSDictionary = [:] {
        didSet {
            // 1. Get Ascendant from root
            self.lagnaSign = data["ascendant"] as? Int ?? 1
            
            // 2. Parse houses array
            guard let houses = data["houses"] as? NSArray else {
                self.housesData = []
                return
            }
            
            var parsed: [[String: Any]] = []
            for item in houses {
                guard let dict = item as? NSDictionary else { continue }
                let planet = dict["planet"] as? String ?? ""
                let degree = dict["degree"] as? String ?? ""
                
                // Handle house as Number or String
                var house = 1
                if let num = dict["house"] as? NSNumber {
                    house = num.intValue
                } else if let str = dict["house"] as? String {
                    house = Int(str) ?? 1
                }
                
                parsed.append([
                    "planet": planet,
                    "degree": degree,
                    "house": house
                ])
            }
            self.housesData = parsed
            
            DispatchQueue.main.async {
                self.setNeedsDisplay()
            }
        }
    }
    
    override func draw(_ rect: CGRect) {
        guard let ctx = UIGraphicsGetCurrentContext() else { return }
        
        // --- Dimensions ---
        let w = bounds.width
        let h = bounds.height
        let s = min(w, h) * 0.98 // Slight padding to avoid edge clipping
        
        let left = (w - s) / 2
        let top = (h - s) / 2
        let right = left + s
        let bottom = top + s
        let midX = left + s / 2
        let midY = top + s / 2
        
        // --- 1. Background & Lines ---
        UIColor.white.setFill()
        ctx.fill(rect)
        
        let linePath = UIBezierPath()
        linePath.lineWidth = 1.5
        UIColor.black.setStroke()
        
        // Outer square
        linePath.append(UIBezierPath(rect: CGRect(x: left, y: top, width: s, height: s)))
        
        // Diagonals
        linePath.move(to: CGPoint(x: left, y: top))
        linePath.addLine(to: CGPoint(x: right, y: bottom))
        
        linePath.move(to: CGPoint(x: right, y: top))
        linePath.addLine(to: CGPoint(x: left, y: bottom))
        
        // Inner Diamond
        linePath.move(to: CGPoint(x: midX, y: top))
        linePath.addLine(to: CGPoint(x: right, y: midY))
        linePath.addLine(to: CGPoint(x: midX, y: bottom))
        linePath.addLine(to: CGPoint(x: left, y: midY))
        linePath.close()
        
        linePath.stroke()
        
        // --- 2. Define Centers (Same as Android logic) ---
        let centers: [CGPoint] = [
            CGPoint(x: midX, y: top + s * 0.25),             // 1.      
            CGPoint(x: left + s * 0.25, y: top + s * 0.12),  // 2
            CGPoint(x: left + s * 0.10, y: top + s * 0.25),  // 3
            CGPoint(x: left + s * 0.25, y: midY),             // 4
            CGPoint(x: left + s * 0.08, y: top + s * 0.80),  // 5
            CGPoint(x: left + s * 0.25, y: top + s * 0.90),  // 6
            CGPoint(x: midX, y: top + s * 0.75),             // 7
            CGPoint(x: right - s * 0.25, y: top + s * 0.88), // 8
            CGPoint(x: right - s * 0.12, y: top + s * 0.75), // 9
            CGPoint(x: right - s * 0.25, y: midY),            // 10
            CGPoint(x: right - s * 0.12, y: top + s * 0.25), // 11
            CGPoint(x: right - s * 0.25, y: top + s * 0.12)  // 12

        ]
        
        // Group planets by house
        let groups = Dictionary(grouping: housesData, by: { $0["house"] as? Int ?? 1 })
        
        // --- 3. Draw Numbers & Planets ---
        for i in 1...12 {
            let center = centers[i - 1]
            
            // Calculate Sign Number
            var signToDraw = (lagnaSign + i - 1)
            while signToDraw > 12 { signToDraw -= 12 }
            
            // Sign Number Text
            let isDiamond = (i == 1 || i == 4 || i == 7 || i == 10)
            let signOffset = isDiamond ? s * 0.10 : s * 0.07
            
            let signAttr: [NSAttributedString.Key: Any] = [
                .font: UIFont.boldSystemFont(ofSize: s * 0.030),
                .foregroundColor: UIColor.darkGray
            ]
            
            let signStr = "\(signToDraw)"
            let signSize = signStr.size(withAttributes: signAttr)
            signStr.draw(at: CGPoint(x: center.x - signSize.width / 2, y: center.y - signOffset), withAttributes: signAttr)
            
            // Planet Text
            if let planets = groups[i] {
                let planetFont = UIFont.boldSystemFont(ofSize: s * 0.030)
                let planetAttr: [NSAttributedString.Key: Any] = [
                    .font: planetFont,
                    .foregroundColor: UIColor.black
                ]
                
                let lineHeight = planetFont.lineHeight * 0.95
                var currentY = center.y - (CGFloat(planets.count) * lineHeight / 2) + (s * 0.015)
                
                for p in planets {
                    let text = "\(p["planet"] ?? "") \(p["degree"] ?? "")"
                    let textSize = text.size(withAttributes: planetAttr)
                    text.draw(at: CGPoint(x: center.x - textSize.width / 2, y: currentY), withAttributes: planetAttr)
                    currentY += lineHeight
                }
            }
        }
    }
}

