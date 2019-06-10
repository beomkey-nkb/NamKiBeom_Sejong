//
//  infoTableViewController.swift
//  classical_reading
//
//  Created by 남기범 on 13/05/2019.
//  Copyright © 2019 남기범. All rights reserved.
//

import UIKit

class infoTableViewController: UITableViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }

    // MARK: - Table view data source

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        print(3 + array_시험인증현황.count + array_대회인증현황.count + array_대체과목현황.count)
        print(array_시험인증현황.count)
        return 3 + array_시험인증현황.count + array_대회인증현황.count + array_대체과목현황.count
    }
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath.row == 0 {
            return 89
        }
        else if indexPath.row < 3 {
            return 102
        }
        else if indexPath.row < 4 {
            return 89
        }
        else if indexPath.row < 9 {
            return 102
        }
        else if indexPath.row < 10 {
            return 89
        }
        else{
            return 102
        }
    }


    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.row == 0{
            let cell = tableView.dequeueReusableCell(withIdentifier: "cell1")!
            return cell
        }
        else if indexPath.row <= array_시험인증현황.count {
            let cell = tableView.dequeueReusableCell(withIdentifier: "cell2")!
            let imageBox = cell.viewWithTag(1) as? UIImageView
            let bookNameLabel = cell.viewWithTag(2) as? UILabel
            let 점수 = cell.viewWithTag(3) as? UILabel
            let 영역 = cell.viewWithTag(4) as? UILabel
            let 여부 = cell.viewWithTag(5) as? UILabel
            let 학기 = cell.viewWithTag(6) as? UILabel
            let 날짜 = cell.viewWithTag(7) as? UILabel
            
            if array_시험인증현황[indexPath.row-1].영역명 == "서양의역사와사상"{
                imageBox?.backgroundColor = UIColor(netHex: 0xD783FF)
            }
            else if array_시험인증현황[indexPath.row-1].영역명 == "동양의역사와사상"{
                imageBox?.backgroundColor = UIColor(netHex: 0xFCD401)
            }
            else if array_시험인증현황[indexPath.row-1].영역명 == "동서양의문학"{
                imageBox?.backgroundColor = UIColor(netHex: 0x14B4BE)
            }
            else{
                imageBox?.backgroundColor = UIColor(netHex: 0x238316)
            }
            
            bookNameLabel!.text = array_시험인증현황[indexPath.row-1].도서명
            점수!.text = array_시험인증현황[indexPath.row-1].점수 + ":"
            영역!.text = array_시험인증현황[indexPath.row-1].영역명
            여부!.text = array_시험인증현황[indexPath.row-1].합격여부
            학기!.text = array_시험인증현황[indexPath.row-1].년도학기
            날짜!.text = array_시험인증현황[indexPath.row-1].응시일자
            
            if 학기!.text == "검색된결과가없습니다."{
                bookNameLabel!.text = "검색된결과가없습니다."
                점수!.isHidden = true
                여부!.isHidden = true
                영역!.isHidden = true
                날짜!.isHidden = true
                imageBox!.isHidden = true
                학기!.isHidden = true
            }
            return cell
        }
        else if indexPath.row < array_시험인증현황.count + 2 {
            let cell = tableView.dequeueReusableCell(withIdentifier: "cell3")!
            return cell
        }
        else if indexPath.row < array_시험인증현황.count + 2 + array_대체과목현황.count{
            let cell = tableView.dequeueReusableCell(withIdentifier: "cell4")!
            let imageBox = cell.viewWithTag(1) as? UIImageView
            let bookNameLabel = cell.viewWithTag(2) as? UILabel
            let 영역 = cell.viewWithTag(3) as? UILabel
            let 이수 = cell.viewWithTag(4) as? UILabel
            let 학기 = cell.viewWithTag(5) as? UILabel
            let 과목 = cell.viewWithTag(6) as? UILabel

            if array_대체과목현황[indexPath.row - (array_시험인증현황.count + 2)].영역명 == "서양의역사와사상"{
                imageBox?.backgroundColor = UIColor(netHex: 0xD783FF)
            }
            else if array_대체과목현황[indexPath.row - (array_시험인증현황.count + 2)].영역명 == "동양의역사와사상"{
                imageBox?.backgroundColor = UIColor(netHex: 0xFCD401)
            }
            else if array_대체과목현황[indexPath.row - (array_시험인증현황.count + 2)].영역명 == "동서양의문학"{
                imageBox?.backgroundColor = UIColor(netHex: 0x14B4BE)
            }
            else{
                imageBox?.backgroundColor = UIColor(netHex: 0x238316)
            }
            
            bookNameLabel!.text = array_대체과목현황[indexPath.row - (array_시험인증현황.count + 2)].도서명
            과목!.text = array_대체과목현황[indexPath.row - (array_시험인증현황.count + 2)].과목명
            영역!.text = array_대체과목현황[indexPath.row - (array_시험인증현황.count + 2)].영역명
            이수!.text = array_대체과목현황[indexPath.row - (array_시험인증현황.count + 2)].이수여부
            학기!.text = array_대체과목현황[indexPath.row - (array_시험인증현황.count + 2)].년도학기
            
            if 학기!.text == "검색된결과가없습니다."{
                bookNameLabel!.text = "검색된결과가없습니다."
                과목!.isHidden = true
                이수!.isHidden = true
                영역!.isHidden = true
                imageBox!.isHidden = true
                학기!.isHidden = true
            }
            return cell
        }
        else if indexPath.row == array_시험인증현황.count + 2 + array_대체과목현황.count {
            let cell = tableView.dequeueReusableCell(withIdentifier: "cell5")!
            return cell
        }
        else{
            let cell = tableView.dequeueReusableCell(withIdentifier: "cell6")!
            
            let imageBox = cell.viewWithTag(1) as? UIImageView
            let 대회명 = cell.viewWithTag(2) as? UILabel
            let 영역 = cell.viewWithTag(3) as? UILabel
            let 인증 = cell.viewWithTag(4) as? UILabel
            let 학기 = cell.viewWithTag(5) as? UILabel
            
            if array_대체과목현황[indexPath.row - (array_시험인증현황.count + 2 + array_대체과목현황.count + 1)].영역명 == "서양의역사와사상"{
                imageBox?.backgroundColor = UIColor(netHex: 0xD783FF)
            }
            else if array_대체과목현황[indexPath.row - (array_시험인증현황.count + 2 + array_대체과목현황.count + 1)].영역명 == "동양의역사와사상"{
                imageBox?.backgroundColor = UIColor(netHex: 0xFCD401)
            }
            else if array_대체과목현황[indexPath.row - (array_시험인증현황.count + 2 + array_대체과목현황.count + 1)].영역명 == "동서양의문학"{
                imageBox?.backgroundColor = UIColor(netHex: 0x14B4BE)
            }
            else{
                imageBox?.backgroundColor = UIColor(netHex: 0x238316)
            }
            
            대회명!.text = array_대회인증현황[indexPath.row - (array_시험인증현황.count + 2 + array_대체과목현황.count + 1)].대회명
            print(array_대회인증현황)
            영역!.text = array_대회인증현황[indexPath.row - (array_시험인증현황.count + 2 + array_대체과목현황.count + 1)].영역명
            학기!.text = array_대회인증현황[indexPath.row - (array_시험인증현황.count + 2 + array_대체과목현황.count + 1)].년도학기
            
            
            if 학기!.text == "검색된결과가없습니다."{
                대회명!.text = "검색된결과가없습니다."
                영역!.isHidden = true
                인증!.isHidden = true
                imageBox!.isHidden = true
                학기!.isHidden = true
            }
            return cell
        }
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
    }
    
    @IBAction func dismisAction(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
    

}
extension UIColor {
    convenience init(red: Int, green: Int, blue: Int) {
        assert(red >= 0 && red <= 255, "Invalid red component")
        assert(green >= 0 && green <= 255, "Invalid green component")
        assert(blue >= 0 && blue <= 255, "Invalid blue component")
        
        self.init(red: CGFloat(red) / 255.0, green: CGFloat(green) / 255.0, blue: CGFloat(blue) / 255.0, alpha: 1.0)
    }
    
    convenience init(netHex:Int) {
        self.init(red:(netHex >> 16) & 0xff, green:(netHex >> 8) & 0xff, blue:netHex & 0xff)
    }
}
