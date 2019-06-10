//
//  SearchResultController.swift
//  classical_reading
//
//  Created by 남기범 on 29/04/2019.
//  Copyright © 2019 남기범. All rights reserved.
//

import UIKit
import Kanna
import Alamofire
import SwiftSoup
import UserNotifications
import SwiftyJSON

struct BookInfo{
    var location:String
    var BookNumber:String
    var bookState:String
    var bookLimit:String
}

class SearchResultController: UIViewController,UITableViewDelegate,UITableViewDataSource {

    var bookISBN = ""
    var bookName = ""
    var finalLength:Int = 0
    var arrBookInfo: Array<BookInfo> = Array()
    
    @IBOutlet weak var NameLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        print("\(bookISBN)")
        NameLabel.text = bookName
        // 책 정보 배열 - 내용은 Struct
        
        ////////////url 추출을 위한 파싱
        let url = "http://library.sejong.ac.kr/search/Search.Result.ax?sid=1&q=ISBN%3A\(bookISBN)&eq=&mf=true&qt=ISBN%3D\(bookISBN)&qf=\(bookISBN)&f=&br=&cl=1+2+3+4+5+7+8+9+18+19+20+21+22+23+24+25+26+27+28+29+30+31+32+33+34+35+36+37+61+62+34+35+38+39+11+12+13+63+44+45+51+52+42+43+49+50+40+41+57+58+48+59+61+62&gr=1+2+3+4+5+6+7+8+9+10+12+13+20&rl=&page=&pageSize=10&s=&st=&h=&cr=&py=&subj=&facet=Y&nd=&vid=0&tabID="
        
        var arrHref = [""]
        guard let main = URL(string: url) else{
            print("Error: \(url) doesn't seem to be a valid URL")
            return
        }
        do {
            let lolMain = try String(contentsOf: main, encoding: .utf8)
            //url을 string으로 인코딩
            let firstconfirm:Document = try SwiftSoup.parse(lolMain)
            
            let Data: Element = try firstconfirm.select("#frmResultList > ul > li:nth-child(1) > dl > dd > div > div > div.body > a:nth-child(1)").first()!
            let linkHref: String = try Data.attr("href")
            arrHref = linkHref.components(separatedBy: ["(",")"])
        }catch let error {
            print("Error: \(error)")
        }
        
        print(arrHref[1])
        /////////////////////////////////////////////
        
        let finalURL = "http://library.sejong.ac.kr/search/ItemDetail.axa?sid=1&cid=\(arrHref[1])"
        print(finalURL)
        guard let finalMain = URL(string: finalURL) else{
            print("Error: \(url) doesn't seem to be a valid URL")
            return
        }
        do {
            
            let lolMain1 = try String(contentsOf: finalMain, encoding: .utf8)
            //url을 string으로 인코딩
            let firstconfirm1:Document = try SwiftSoup.parse(lolMain1)
            
            let Data1: Element = try firstconfirm1.select("body > div > table > tbody > tr.lastRecord > td:nth-child(1)").first()!
            var Data1Length = Int(try Data1.text())
            finalLength = Data1Length!
            for i in 1...Data1Length! {
                var bkInfo = BookInfo(location: " ", BookNumber: " ", bookState: " ", bookLimit: " ")
                for j in 3...6{
                    let Data2: Element = try firstconfirm1.select("body > div > table > tbody > tr:nth-child(\(i)) > td:nth-child(\(j))").first()!
                    switch j {
                    case 3:
                        bkInfo.location = try Data2.text()
                        break
                    case 4:
                        bkInfo.BookNumber = try Data2.text()
                        break
                    case 5:
                        bkInfo.bookState = try Data2.text()
                        break
                    case 6:
                        bkInfo.bookLimit = try Data2.text()
                        break
                    default:
                        break
                    }
                }
                arrBookInfo.append(bkInfo)
            }
            
            print(arrBookInfo)
        }catch let error {
            print("Error: \(error)")
        }
    }

    
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return finalLength
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "maincell")!
        let location = cell.viewWithTag(101) as? UILabel
        let bkNumber = cell.viewWithTag(102) as? UILabel
        let bkState = cell.viewWithTag(103) as? UILabel
        let bkLimit = cell.viewWithTag(104) as? UILabel
        
        location?.text = arrBookInfo[indexPath.row].location
        bkNumber?.text = arrBookInfo[indexPath.row].BookNumber
        bkState?.text = arrBookInfo[indexPath.row].bookState
        bkLimit?.text = arrBookInfo[indexPath.row].bookLimit
        
        if bkState?.text == "대출중"{
            bkLimit?.text = arrBookInfo[indexPath.row].bookLimit + "까지"
        }
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print("click")
        let text = "소장위치: \(arrBookInfo[indexPath.row].location), 청구기호: \(arrBookInfo[indexPath.row].BookNumber), 도서상태: \(arrBookInfo[indexPath.row].bookState)"
        let textToShare = [text]
        // 액티비티 뷰 컨트롤러 셋업
        let activityVC = UIActivityViewController(activityItems: textToShare, applicationActivities: nil)
        activityVC.popoverPresentationController?.sourceView = self.view // 아이패드에서도 동작하도록 팝오버로 설정
        // 제외하고 싶은 타입을 설정 (optional)
        activityVC.excludedActivityTypes = [ UIActivity.ActivityType.airDrop ]
        // 현재 뷰에서 present
        self.present(activityVC, animated: true, completion: nil)

        //선택 후에 선택된 색상을 없애줌
        tableView.deselectRow(at: indexPath, animated: true)
    }
    
    @IBAction func SearchBack(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
    
    
    
    
}
