//
//  viewCustom.swift
//  classical_reading
//
//  Created by 남기범 on 18/03/2019.
//  Copyright © 2019 남기범. All rights reserved.
//

import Foundation
import UIKit

class ViewCustom:UIView{
    required init(coder aDecoder: NSCoder){
        super.init(coder: aDecoder)!
        
        self.layer.cornerRadius = 3
    }
}
class ButtonCustom:UIButton{
    required init(coder aDecoder: NSCoder){
        super.init(coder: aDecoder)!
        
        self.layer.cornerRadius = 3
    }
}

class shadowView:UIView{
    required init(coder aDecoder: NSCoder){
        super.init(coder: aDecoder)!
        self.layer.cornerRadius = 3
        self.layer.shadowColor = UIColor.black.cgColor
        self.layer.masksToBounds = false
        self.layer.shadowOffset = CGSize(width: 0, height: 4)
        self.layer.shadowRadius = 5
        self.layer.shadowOpacity = 0.2
    }
}

class shadowView1:UIView{
    required init(coder aDecoder: NSCoder){
        super.init(coder: aDecoder)!
        self.layer.cornerRadius = 20
        self.layer.shadowColor = UIColor.black.cgColor
        self.layer.masksToBounds = false
        self.layer.shadowOffset = CGSize(width: 0, height: 7)
        self.layer.shadowRadius = 10
        self.layer.shadowOpacity = 0.5
    }
}

class ButtonCircle:UIButton{
    required init(coder aDecoder: NSCoder){
        super.init(coder: aDecoder)!
        self.layer.cornerRadius = self.frame.height/2
        self.layer.borderWidth = 1
        self.layer.borderColor = UIColor.clear.cgColor
        self.clipsToBounds = true
        
    }
}

class imageViewCircle:UIImageView{
    required init(coder aDecoder: NSCoder){
        super.init(coder: aDecoder)!
        self.layer.cornerRadius = self.frame.height/2
        self.layer.borderWidth = 1
        self.layer.borderColor = UIColor.clear.cgColor
        self.clipsToBounds = true
    }
}

