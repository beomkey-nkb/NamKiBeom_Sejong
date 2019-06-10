//
//  ViewController.swift
//  classical_reading
//
//  Created by 남기범 on 18/03/2019.
//  Copyright © 2019 남기범. All rights reserved.
//

import UIKit
import Alamofire
import MBCircularProgressBar

var name = "홍길동"
var schoolNumber = "14011113"
var major = "만화애니매이션학과"
var password1 = "1"

struct Form: Codable {
    let status:String
    let 학과:String
    let 학번:String
    let 이름: String
    
    struct 인증현황: Codable{
        let 서양의역사와사상:String
        let 동양의역사와사상:String
        let 동서양의문학:String
        let 과학사상:String
        let 합계:String
        
        enum CodingKeys : String, CodingKey {
            case 서양의역사와사상 = "서양의 역사와 사상"
            case 동양의역사와사상 = "동양의 역사와 사상"
            case 동서양의문학 = "동서양의 문학"
            case 과학사상 = "과학 사상"
            case 합계 = "합계"
        }
        init(from decoder: Decoder) throws {
            let values = try decoder.container(keyedBy: CodingKeys.self)
            서양의역사와사상 = (try? values.decode(String.self, forKey: .서양의역사와사상)) ?? ""
            동양의역사와사상 = (try? values.decode(String.self, forKey: .동양의역사와사상)) ?? ""
            동서양의문학 = (try? values.decode(String.self, forKey: .동서양의문학)) ?? ""
            과학사상 = (try? values.decode(String.self, forKey: .과학사상)) ?? ""
            합계 = (try? values.decode(String.self, forKey: .합계)) ?? ""
        }
    }
    struct 시험인증현황: Codable{
        let 년도학기:String
        let 영역명:String
        let 도서명:String
        let 응시일자:String
        let 점수:String
        let 합격여부:String
        
        enum CodingKeys : String, CodingKey {
            case 년도학기 = "년도/학기"
            case 영역명 = "영역명"
            case 도서명 = "도서명"
            case 응시일자 = "응시일자"
            case 점수 = "점수"
            case 합격여부 = "합격여부"
        }
        init(from decoder: Decoder) throws {
            let values = try decoder.container(keyedBy: CodingKeys.self)
            년도학기 = (try? values.decode(String.self, forKey: .년도학기)) ?? ""
            응시일자 = (try? values.decode(String.self, forKey: .응시일자)) ?? ""
            영역명 = (try? values.decode(String.self, forKey: .영역명)) ?? ""
            도서명 = (try? values.decode(String.self, forKey: .도서명)) ?? ""
            점수 = (try? values.decode(String.self, forKey: .점수)) ?? ""
            합격여부 = (try? values.decode(String.self, forKey: .합격여부)) ?? ""
        }
    }
    
    struct 대체과목현황: Codable{
        let 년도학기:String
        let 과목명:String
        let 영역명:String
        let 도서명:String
        let 이수여부:String
        
        enum CodingKeys : String, CodingKey {
            case 년도학기 = "년도/학기"
            case 과목명 = "과목명"
            case 영역명 = "영역명"
            case 도서명 = "도서명"
            case 이수여부 = "이수여부"
        }
        
        init(from decoder: Decoder) throws {
            let values = try decoder.container(keyedBy: CodingKeys.self)
            년도학기 = (try? values.decode(String.self, forKey: .년도학기)) ?? ""
            과목명 = (try? values.decode(String.self, forKey: .과목명)) ?? ""
            영역명 = (try? values.decode(String.self, forKey: .영역명)) ?? ""
            도서명 = (try? values.decode(String.self, forKey: .도서명)) ?? ""
            이수여부 = (try? values.decode(String.self, forKey: .이수여부)) ?? ""
        }
    }
    struct 대회인증현황: Codable{
        let 년도학기:String
        let 대회명:String
        let 영역명:String
        let 도서명:String
        
        enum CodingKeys : String, CodingKey {
            case 년도학기 = "년도/학기"
            case 대회명 = "대회명"
            case 영역명 = "영역명"
            case 도서명 = "도서명"
        }
        
        init(from decoder: Decoder) throws {
            let values = try decoder.container(keyedBy: CodingKeys.self)
            년도학기 = (try? values.decode(String.self, forKey: .년도학기)) ?? ""
            대회명 = (try? values.decode(String.self, forKey: .대회명)) ?? ""
            영역명 = (try? values.decode(String.self, forKey: .영역명)) ?? ""
            도서명 = (try? values.decode(String.self, forKey: .도서명)) ?? ""
        }
    }
    let 인증현황: [인증현황]
    let 시험인증현황: [시험인증현황]
    let 대체과목현황: [대체과목현황]
    let 대회인증현황: [대회인증현황]
}

struct Sample2: Codable {
    var list: [String]
    
    init(from decoder: Decoder) throws {
        list = try decoder.singleValueContainer().decode([String].self)
    }
}

struct Sample1: Codable {
    var a: String
    var b: String
}

class ViewController: UIViewController,UITableViewDelegate,UITableViewDataSource {
    //refresh 변수 설정 및 TableView 아울렛 변수 설정
    var refreshControl = UIRefreshControl()
    @IBOutlet weak var mainTableView: UITableView!
    
    /////////////////////////
    var reserveTime = "20:19"
    var reservedDate = "2019-05-09"
    var reservedBookName = "총,균,쇠"
    var dataLive = "1"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //서버 연동 코드
        // refresh 초기 설정
        self.refreshControl.attributedTitle = NSAttributedString(string: "Pull to refresh")
        self.refreshControl.addTarget(self, action: #selector(reloadTable), for: UIControl.Event.valueChanged)
        self.mainTableView?.addSubview(refreshControl)
        
        //UIView에 클릭 이벤트를 삽입하는 과정
//        let gesture = UITapGestureRecognizer(target: self, action:#selector(self.goPageReserved(_:)))
//        self.reservedView.addGestureRecognizer(gesture)
    }
    
//    //UIView를 클릭시에 다른 뷰컨트롤러로 화면 전환하기 위한 함수
//    @objc func goPageReserved(_ sender:UITapGestureRecognizer)
//    {
//        //화면 전환
//        let uvc = self.storyboard!.instantiateViewController(withIdentifier: "reserved")
//        uvc.modalTransitionStyle = UIModalTransitionStyle.coverVertical
//        self.present(uvc, animated: true, completion: nil)
//    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 4
    }
    
    //여러가지 셀을 사용하는 방법
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        if indexPath.row == 0{
            let cell = tableView.dequeueReusableCell(withIdentifier: "MainUser")!
            //서버 연동 코드
            let userNameLabel = cell.viewWithTag(1) as? UILabel
            userNameLabel!.text = name + "님, 안녕하세요"
            //finish
            return cell
        }
        else if indexPath.row == 1{
            let cell = tableView.dequeueReusableCell(withIdentifier: "totalbook")!
            //서버 연동 코드
            let percent = cell.viewWithTag(1) as? MBCircularProgressBarView
            let allpassbook = cell.viewWithTag(2) as? UILabel
            
            allpassbook!.text = array_인증현황[0].합계.trimmingCharacters(in: ["권"]) + "/10권"
            percent!.value = CGFloat(Int(array_인증현황[0].합계.trimmingCharacters(in: ["권"]))!*10)
            return cell
        }
        else if indexPath.row == 2{
            let cell = tableView.dequeueReusableCell(withIdentifier: "totalbook2")!
            //서버 연동 코드
            let leftbook = cell.viewWithTag(1) as? UILabel
            let righthistorybook = cell.viewWithTag(2) as? UILabel
            let leftrightbook = cell.viewWithTag(3) as? UILabel
            let siencebook = cell.viewWithTag(4) as? UILabel
            
            leftbook!.text = array_인증현황[0].서양의역사와사상.trimmingCharacters(in: ["권"]) + "/4권"
            righthistorybook!.text = array_인증현황[0].동양의역사와사상.trimmingCharacters(in: ["권"]) + "/2권"
            leftrightbook!.text = array_인증현황[0].동서양의문학.trimmingCharacters(in: ["권"]) + "/3권"
            siencebook!.text = array_인증현황[0].과학사상.trimmingCharacters(in: ["권"]) + "/1권"
            
            return cell
        }
        else{
            let cell = tableView.dequeueReusableCell(withIdentifier: "reserveDisplay")!
            let timeLabel = cell.viewWithTag(105) as! UILabel
            let reservedDateLabel = cell.viewWithTag(106) as! UILabel
            let reservedBookNameLabel = cell.viewWithTag(107) as! UILabel
            let stateLabel = cell.viewWithTag(108) as! UILabel
            let ImageNext = cell.viewWithTag(109) as! UIImageView
            let NotDataLabel = cell.viewWithTag(110) as! UILabel
            
            //dataLive가 1이 아닐 때 데이터 로드
            if array_예약확인정보[0].id != "1"{
                timeLabel.text = array_예약확인정보[0].time + "부터 20분동안"
                reservedDateLabel.text = array_예약확인정보[0].date
                reservedBookNameLabel.text = "도서명: "+array_예약확인정보[0].title
                NotDataLabel.isHidden = true
                timeLabel.isHidden = false
                reservedDateLabel.isHidden = false
                reservedBookNameLabel.isHidden = false
                stateLabel.isHidden = false
                ImageNext.isHidden = false
                return cell
            }
            else{//0일 경우 데이터 로드 안함
                timeLabel.isHidden = true
                reservedDateLabel.isHidden = true
                reservedBookNameLabel.isHidden = true
                stateLabel.isHidden = true
                ImageNext.isHidden = true
                NotDataLabel.isHidden = false
                return cell
            }
        }
        
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath.row == 0 {
            return 122
        }
        else if indexPath.row == 1 {
            return 195
        }
        else if indexPath.row == 2 {
            return 235
        }
        else{
            return 76
        }//셀 마다 높이 다르게
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if indexPath.row == 1 {
//            if let avc = self.storyboard?.instantiateViewController(withIdentifier: "infoTable") as? infoTableViewController{
//                self.present(avc, animated: true, completion: nil)
//            }
        }
        else if indexPath.row == 3 {
            //dataLive가 1일 때만 선택헤서 화면전환 가능
            if array_예약확인정보[0].id != "1"{
                tableView.deselectRow(at: indexPath, animated: true)
                if let avc = self.storyboard?.instantiateViewController(withIdentifier: "reserved") as? NotiController{
                    avc.dayString = array_예약확인정보[0].date
                    avc.timeString = array_예약확인정보[0].time
                    avc.bookName = array_예약확인정보[0].title
                    self.present(avc, animated: true, completion: nil)
                }
            }
            else{
                
            }
        }
    }
    ////////밑으로 제스쳐 취했을 때 데이터 리로드 하는 함수
    @objc func reloadTable(){
        let re_urlReserved = "http://15.164.113.118:3000/?status=4&id=\(schoolNumber)"
        array_예약확인정보.removeAll()
        let rep = Alamofire.request(re_urlReserved, method: .post, encoding: URLEncoding.methodDependent, headers: [:]).responseString { (response) in
            
            let 예약파싱전 = """
            {
            "key": \(response.result.value!)
            }
            """
            let data_예약 = 예약파싱전.data(using: .utf8)
            
            do{
                let data_예약정보 = try JSONDecoder().decode(예약정보.self, from: data_예약!)
                var 예약확인_data = 예약확인정보(id: data_예약정보.key[0].id, title: data_예약정보.key[0].title, hakgi: data_예약정보.key[0].hakgi, date: data_예약정보.key[0].date, time: data_예약정보.key[0].time, day: data_예약정보.key[0].day)
                
                array_예약확인정보.append(예약확인_data)
                
                self.mainTableView.reloadData()
                self.refreshControl.endRefreshing()
            }catch{
                print(error)
            }
        }
    }
    //////////////////////////
}

