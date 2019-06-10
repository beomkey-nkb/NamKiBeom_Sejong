//
//  SettingController.swift
//  classical_reading
//
//  Created by 남기범 on 27/04/2019.
//  Copyright © 2019 남기범. All rights reserved.
//

import UIKit

class SettingController: UIViewController,UITableViewDelegate,UITableViewDataSource {
    @IBOutlet weak var tableView: UITableView!
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 5
    }
    
    //여러가지 셀을 사용하는 방법
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        if indexPath.row == 0{
            let cell = tableView.dequeueReusableCell(withIdentifier: "cell1")!
            let setting_이름 = cell.viewWithTag(1) as? UILabel
            
            setting_이름!.text = name + "님의 프로필"
            return cell
        }
        else if indexPath.row == 1{
            let cell = tableView.dequeueReusableCell(withIdentifier: "cell2")!
            return cell
        }
        else if indexPath.row == 2{
            let cell = tableView.dequeueReusableCell(withIdentifier: "cell3")!
            return cell
        }
        else if indexPath.row == 3{
            let cell = tableView.dequeueReusableCell(withIdentifier: "cell4")!
            return cell
        }
        else{
            let cell = tableView.dequeueReusableCell(withIdentifier: "cell5")!
            return cell
        }
        
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath.row == 0 {
            return 157
        }
        else{
            return 50
        }//셀 마다 높이 다르게
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if indexPath.row == 2 || indexPath.row == 3
        {
            if let rvc = self.storyboard?.instantiateViewController(withIdentifier: "webController") as? webController {
                rvc.tableIndex = indexPath.row
                tableView.deselectRow(at: indexPath, animated: true)                
                self.present(rvc, animated: true, completion: nil)
            }
        }
        else if indexPath.row == 4 {
            tableView.deselectRow(at: indexPath, animated: true)
            UserDefaults.standard.removeObject(forKey: "id")
            UserDefaults.standard.removeObject(forKey: "pw")
            dismiss(animated: true, completion: nil)
        }
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
    }
   
}
