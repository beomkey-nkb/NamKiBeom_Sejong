//
//  NotiController.swift
//  
//
//  Created by 남기범 on 27/04/2019.
//

import UIKit
import UserNotifications
import Alamofire

class NotiController: UIViewController {
    
    let calendar = Calendar.current
    
    var dayString = ""
    var timeString = ""
    var bookName = ""
    var allTime = ""
    
    @IBOutlet weak var day: UILabel!
    @IBOutlet weak var timeLabel: UILabel!
    @IBOutlet weak var bookNameLabel: UILabel!
    
    
    @IBOutlet weak var NodataLabel: UILabel!
    @IBOutlet weak var reservedTimeLabel: UILabel!
    @IBOutlet weak var reservedTime: UILabel!
    @IBOutlet weak var reservedCencelButton: ButtonCircle!
    
    

    override func viewDidLoad() {
        super.viewDidLoad()

        UNUserNotificationCenter.current().requestAuthorization(options: [.alert,.sound,.badge], completionHandler: {didAllow, Error in
            print(didAllow)
        })
        
        allTime = dayString + " " + timeString
        print(allTime)
        day.text = dayString
        timeLabel.text = timeString + "부터 20분동안"
        bookNameLabel.text = bookName
        
        if let allT = UserDefaults.standard.string(forKey: "allTime"){
            reservedTime.text = allTime
            reservedTime.isHidden = false
            reservedTimeLabel.isHidden = false
            reservedCencelButton.isHidden = false
            NodataLabel.isHidden = true
        }
        else{
            reservedTime.isHidden = true
            reservedTimeLabel.isHidden = true
            reservedCencelButton.isHidden = true
        }
    }
    
    @IBAction func backButton(_ sender: Any) {
        
        self.presentingViewController?.dismiss(animated: true, completion: nil)
    }
    
    @IBAction func reserveCancel(_ sender: Any) {
        //서버 통신
        let url_status3 = "http://15.164.113.118:3000/?status=3&id=\(schoolNumber)"
        
        let rea = Alamofire.request(url_status3, method: .post, encoding: URLEncoding.methodDependent, headers: [:]).responseString { (response) in
            print(response.result.value!)
            let datat = response.result.value!.data(using: .utf8)
            
            do {
                let data = try JSONDecoder().decode(예약응답결과처리.self, from: datat!)
                
                if data.id == "1"{
                    let alertController_3 = UIAlertController(title: "예약",message: "예약취소가 완료되었습니다!", preferredStyle: UIAlertController.Style.alert)
                    let cancelButton_3 = UIAlertAction(title: "확인", style: UIAlertAction.Style.cancel){
                        (action) in
                        self.presentingViewController?.dismiss(animated: true, completion: nil)
                    }
                    alertController_3.addAction(cancelButton_3)
                    self.present(alertController_3,animated: true,completion: nil)
                }
            }
            catch{
                print(error)
            }
        }
    }
    
    @IBAction func timeReserve(_ sender: Any) {
        
        print("ok")
        
        if NodataLabel.isHidden == false{
            ////////alert 활용
            let alertController = UIAlertController(title: "시험 1시간 전 알림",message: "알림설정이 완료되었습니다.", preferredStyle: UIAlertController.Style.alert)
            
            let cancelButton = UIAlertAction(title: "완료", style: UIAlertAction.Style.cancel, handler: nil)
            alertController.addAction(cancelButton)
            self.present(alertController,animated: true,completion: nil)
            ////////////////
            
            //////////////시간 예약
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm"
            dateFormatter.timeZone = NSTimeZone(name:  "KST") as TimeZone? //시간 기준 한국으로 변경
            let EndtimeDate = dateFormatter.date(from:allTime)
            
            let beforeHour = EndtimeDate! - 3600
            var components1 = self.calendar.dateComponents([.year, .month, .day, .hour, .minute],from: beforeHour)
            
            let dateFormatter1 = DateFormatter()
            dateFormatter1.dateFormat = "yyyy-MM-dd HH:mm"
            
            let dateString = dateFormatter1.string(from: beforeHour)
            
            
            let content = UNMutableNotificationContent()
            content.title = "세종대 고전독서인증"
            content.body = "시험 1시간 전입니다!"
            content.sound = UNNotificationSound.default
            
            let trigger = UNCalendarNotificationTrigger(dateMatching: components1, repeats: false)
            let request = UNNotificationRequest(identifier: "testIdentfier", content: content, trigger: trigger)
            UNUserNotificationCenter.current().add(request, withCompletionHandler: nil)
            ///////////////////
            
            reservedTime.text = dateString
            reservedTime.isHidden = false
            reservedTimeLabel.isHidden = false
            reservedCencelButton.isHidden = false
            NodataLabel.isHidden = true
            
            UserDefaults.standard.set(allTime, forKey: "allTime")
        }
        else{
            ////////alert 활용
            let alertController = UIAlertController(title: "알림설정",message: "설정이 이미 완료되었습니다!", preferredStyle: UIAlertController.Style.alert)
            
            let cancelButton = UIAlertAction(title: "취소", style: UIAlertAction.Style.cancel, handler: nil)
            alertController.addAction(cancelButton)
            
            self.present(alertController,animated: true,completion: nil)
            //////////////
        }
    }
    
    @IBAction func reserveTimeCencel(_ sender: Any) {
        
        UserDefaults.standard.removeObject(forKey: "allTime")
        UNUserNotificationCenter.current().removeAllPendingNotificationRequests()
        reservedTime.isHidden = true
        reservedTimeLabel.isHidden = true
        reservedCencelButton.isHidden = true
        NodataLabel.isHidden = false
    }
    
}
