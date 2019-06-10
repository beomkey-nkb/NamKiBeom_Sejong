//
//  demoViewController.swift
//  classical_reading
//
//  Created by 남기범 on 24/05/2019.
//  Copyright © 2019 남기범. All rights reserved.
//

import UIKit
import XLPagerTabStrip

class demoViewController: ButtonBarPagerTabStripViewController {

    override func viewDidLoad() {
        edgesForExtendedLayout = []
        configureButtonBar()
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    override func viewControllers(for pagerTabStripController: PagerTabStripViewController) -> [UIViewController] {
        let child1 = UIStoryboard.init(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "ChildViewController") as! ChildViewController
        child1.childNumber = "서양 역사"
        let child2 = UIStoryboard.init(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "ChildViewController") as! ChildViewController
        child2.childNumber = "동양 역사"
        let child3 = UIStoryboard.init(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "ChildViewController") as! ChildViewController
        child3.childNumber = "동서양 문학"
        let child4 = UIStoryboard.init(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "ChildViewController") as! ChildViewController
        child4.childNumber = "과학 사상"
        
        return [child1, child2, child3, child4]
    }
    
    func configureButtonBar() {
        // Sets the background colour of the pager strip and the pager strip item
        settings.style.buttonBarBackgroundColor = .white
        settings.style.buttonBarItemBackgroundColor = .white
        
        // Sets the pager strip item font and font color
        settings.style.buttonBarItemFont = UIFont(name: "Helvetica", size: 16.0)!
        settings.style.buttonBarItemTitleColor = .gray
        
        // Sets the pager strip item offsets
        settings.style.buttonBarMinimumLineSpacing = 0
        settings.style.buttonBarItemsShouldFillAvailableWidth = true
        settings.style.buttonBarLeftContentInset = 0
        settings.style.buttonBarRightContentInset = 0
        
        // Sets the height and colour of the slider bar of the selected pager tab
        settings.style.selectedBarHeight = 3.0
        settings.style.selectedBarBackgroundColor = UIColor(netHex: 0xA73A3C)
        
        
        // Changing item text color on swipe
        changeCurrentIndexProgressive = { [weak self] (oldCell: ButtonBarViewCell?, newCell: ButtonBarViewCell?, progressPercentage: CGFloat, changeCurrentIndex: Bool, animated: Bool) -> Void in
            guard changeCurrentIndex == true else { return }
            oldCell?.label.textColor = .gray
            newCell?.label.textColor = UIColor(netHex: 0xA73A3C)
        }
    }
}


