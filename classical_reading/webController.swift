//
//  webController.swift
//  classical_reading
//
//  Created by 남기범 on 28/04/2019.
//  Copyright © 2019 남기범. All rights reserved.
//

import UIKit

class webController: UIViewController {
    
    @IBOutlet weak var bookWeb: UIWebView!
    var tableIndex:Int = 0

    override func viewDidLoad() {
        super.viewDidLoad()
        print(tableIndex)
        if tableIndex == 2{
            loadWebpage("https://classic.sejong.ac.kr/info/MAIN_02_02.do")
        }
        else
        {
            loadWebpage("https://classic.sejong.ac.kr/info/MAIN_02_03.do")
        }
        // Do any additional setup after loading the view.
    }
    
    func loadWebpage(_ url: String){
        let myURL = URL(string: url)
        let myRequest  = URLRequest(url: myURL!)
        bookWeb.loadRequest(myRequest)
    }
    
    @IBAction func backSetting(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
    
}
