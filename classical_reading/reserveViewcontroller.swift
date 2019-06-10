//
//  reserveViewcontroller.swift
//  classical_reading
//
//  Created by 남기범 on 28/03/2019.
//  Copyright © 2019 남기범. All rights reserved.
//
var Time = ["10:00","10:20","10:40","11:00","11:20","11:40","14:00","14:20","14:40","15:00","15:20","15:40","16:00","16:20","16:40"]
var bkField = ["서양의역사와사상","동양의역사와사상","동서양의문학","과학사상"]
var bkName = [["플라톤의 국가","정치학","키케로의 의무론","게르마니아","성어거스틴의 고백록","군주론","신기관","리바이어던","통치론","사회계약론","공리주의","존 스튜어트 밀 자유론","체사레 벡카리아의 범죄와 형벌","종교의 자연사","짜라투스트라는이렇게 말했다","프로테스탄티즘의 윤리와 자본주의 정신","미국의민주주의 1", "역사란무엇인가","도덕적인간과비도덕적사회","성과속","소유냐삶이냐","국화와칼","심리학과 종교","슬픈열대","자본주의,사회주의,민주주의","서양미술사","성의역사 1권","감시와 처벌","로마제국쇠망사","역사의 종말","종교의 의미와 목적","낭만적 거짓과 소설적 진실","오래된 미래","위험사회","황금가지","파이돈","예루살렘의 아이히만","호모루덴스"],["성학십도","북학의","조선상고사","삼국유사","논어-슬기바다1","맹자-슬기바다2","대학,중용-슬기바다3","노자의목소리로듣는도덕경","장자","사기열전1","생활의 발견","대동서","간디 자서전","한 젊은 유학자의 초상","묵자","춘추좌전(상)","유배지에서 보낸 편지","산해경","한비자"],["젊은 예술가의 초상","구토","실락원","파우스트","일리아스 오디세이아","원전으로 읽는 변신이야기","인간의 조건","양철북","페스트","카라마조프씨네 형제들","안나카레니나","백년동안의 고독","변신,시골의사","타이스,붉은백합","열하일기","서유견문","파한집","난중일기","천변풍경","위대한 유산 1,2","닥터지바고","변신인형","주홍글씨","걸리버여행기","모비딕","마음","픽션들","농담","아버지와 아들","삼대","적과 흑","대위의 딸"],["프린키피아3(프린키피아:자연과학의 수학적 원리)","종의기원","부분과 전체","과학혁명의 구조","카오스","코스모스","총,균,쇠","엔트로피","객관성의 칼날","같기도 하고 아니 같기도 하고"]]
var ISBN = [["9788930606233","9788991290280","9788930606240","9788908062238","9788951105630","9788972914440","9788935652921","9788949705293","9788972914280","9788901110837","9788970136226","9788930610407","9788964545638","9788957330241","9788937460944","9788931000443","9788935601950", "9788972914297","9788931000023","9788935651733","9788970556826","9788932471389","9788974531881","9788935651740","9788935664153","9788970840659","9788930031363","9788930088572","9788949714318","9788978000468","9788941991120","9788935652860","9788927806615","9788955592115","9788949703879","9788956441160","9788935656615","9788972910251"],["9788970656038","9788932471723","9788937603488","9788935656592","9788970650340","9788970650357","9788970650364","9788971393185","9788932307565","9788937425967","9788931004137","9788932452456","9791186558102","9788982640599","9788970300153","9788974187699","9788936471712","9788937423185","9788935914012"],["9788932911892","9788931001839","9788936603373","9788931006643","9788949704111","8991290035","9788986045536","9788949706481","9788937462672","9788993800593","9788934405184","9788970126937", "9788937460043","9788970962665","9788949706719","9788935912629","9788935911028","9788937426865","9788932015705","9788937462122","9788949714066","9788932015149","9788931004786","9788983920775","9788972883906","9788932317960","9788937462757","9788937484018","9788954613934","9788932015552","9788952237781","9791187192459"],["9788981721312","9788949705132","9788942389056","9788972912569","8980730705","9788983711540","9788970128856","9788986698824","9788955591484","9788972911395"]]

import UIKit
import TextFieldEffects

struct 요일정보추출{
    var 날짜:String
    var 요일:String
}
var 요일정보 = [요일정보추출]()

class reserveViewcontroller: UIViewController,UITextFieldDelegate,UITableViewDelegate,UITableViewDataSource,UIPickerViewDelegate,UIPickerViewDataSource{
    var Date = [String]()//날짜 선택
    var selectTime = ""
    
    ////////////////table View **************************
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return Time.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell")!
        var TimeLabel = cell.viewWithTag(102) as? UILabel
        TimeLabel?.text = Time[indexPath.row]
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        //tableView 선택 동작
        
        if let uvc = self.storyboard?.instantiateViewController(withIdentifier: "bookSelect") as? BookselectController{
            if reserveDate.text == ""{
                ////////alert 활용
                let alertController = UIAlertController(title: "날짜 선택",message: "날짜가 선택되지 않았습니다.", preferredStyle: UIAlertController.Style.alert)
                
                let cancelButton = UIAlertAction(title: "취소", style: UIAlertAction.Style.cancel, handler: nil)
                alertController.addAction(cancelButton)
                self.present(alertController,animated: true,completion: nil)
                tableView.deselectRow(at: indexPath, animated: true)
                ////////////////
            }else{
                uvc.selectedTime = Time[indexPath.row]+"부터 20분동안"
                예약시간 = Time[indexPath.row]
                uvc.selectedDay = reserveDate.text!
                tableView.deselectRow(at: indexPath, animated: true)
                self.present(uvc, animated: true, completion: nil)
            }
        }
        
        
        
    }
    
    ////////////////table View **************************
    
    @IBOutlet weak var reserveDate: AkiraTextField!

    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //키보드에 툴바 추가:Done 버튼만
        let keyboardToolbar = UIToolbar()
        keyboardToolbar.sizeToFit()
        let flexibleSpace = UIBarButtonItem(barButtonSystemItem: UIBarButtonItem.SystemItem.flexibleSpace, target: nil, action: nil)
        let doneButton = UIBarButtonItem(barButtonSystemItem: UIBarButtonItem.SystemItem.done, target: self, action: #selector(self.doneClicked))
        keyboardToolbar.setItems([flexibleSpace,doneButton], animated: false)
        
        reserveDate.delegate = self
        reserveDate.inputAccessoryView = keyboardToolbar
        //////////////////////////////////////////////
        
        //키보드 화면 조정
        NotificationCenter.default.addObserver(self, selector: #selector(self.keyboardWillShow(_:)), name: UIResponder.keyboardWillShowNotification, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(self.keyboardWillHide(_:)), name: UIResponder.keyboardWillHideNotification, object: nil)
        //////////////////////////////////////////////
        
        //Date 배열에 오늘 날짜 부터 한달 뒤의 날짜까지 String 형식으로 저장
        let cal = Calendar(identifier: .gregorian)
        
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd"
        
        var today = NSDate()
        var comps = cal.dateComponents([.weekday], from: today as Date)
        //요일을 추출하기 위한 수단, 1~7까지 숫자로 표시
        
        if comps.weekday! != 7 && comps.weekday! != 1 {
            //주말을 제외시켜서 저장
            Date.append(formatter.string(from: today as Date))
            
        }
        
        for i in 1...29{
            today = NSDate(timeIntervalSinceNow: TimeInterval(i*86400))//오늘 기준으로 30일 뒤
            comps = cal.dateComponents([.weekday], from: today as Date)
            
            if comps.weekday! != 7 && comps.weekday! != 1 {
                //주말 제외
                Date.append(formatter.string(from: today as Date))
                //요일정보 추출
                if comps.weekday! == 2{
                    let trash_요일정보 = 요일정보추출(날짜: formatter.string(from: today as Date), 요일: "월")
                    요일정보.append(trash_요일정보)
                }
                else if comps.weekday! == 3{
                    let trash_요일정보 = 요일정보추출(날짜: formatter.string(from: today as Date), 요일: "화")
                    요일정보.append(trash_요일정보)
                }
                else if comps.weekday! == 4{
                    let trash_요일정보 = 요일정보추출(날짜: formatter.string(from: today as Date), 요일: "수")
                    요일정보.append(trash_요일정보)
                }
                else if comps.weekday! == 5{
                    let trash_요일정보 = 요일정보추출(날짜: formatter.string(from: today as Date), 요일: "목")
                    요일정보.append(trash_요일정보)
                }
                else if comps.weekday! == 6{
                    let trash_요일정보 = 요일정보추출(날짜: formatter.string(from: today as Date), 요일: "금")
                    요일정보.append(trash_요일정보)
                }
            }
        }
        print(Date)
        
        //키보드 대신 pickerView 추가(date에 대한 pickerView)
        var bkFieldPickerView = UIPickerView()
        bkFieldPickerView.delegate = self
        bkFieldPickerView.tag = 1
        reserveDate.inputView = bkFieldPickerView
        
//        //키보드 대신 pickerView 추가
//        var bkFieldPickerView = UIPickerView()
//        bkFieldPickerView.delegate = self
//        bkFieldPickerView.tag = 1
//        bookField.inputView = bkFieldPickerView
//
//        var bkNamePickerView = UIPickerView()
//        bkNamePickerView.delegate = self
//        bkNamePickerView.tag = 2
//        bookName.inputView = bkNamePickerView
    }
    
    @objc func keyboardWillShow(_ sender:Notification){
        self.view.frame.origin.y = -150
    }
    
    @objc func keyboardWillHide(_ sender:Notification){
        self.view.frame.origin.y = 0
    }
    
    
    ////////////////////////date에 관한 pickerView 함수들
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if pickerView.tag == 1{
            return Date.count
        }
        return 0
    }
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if pickerView.tag == 1{
            return Date[row]
        }
        return nil
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if pickerView.tag == 1{
            reserveDate.text = Date[row]
        }
    }
    ///////////////////////////////////
    @objc func doneClicked(){
        view.endEditing(true)
    }
}
