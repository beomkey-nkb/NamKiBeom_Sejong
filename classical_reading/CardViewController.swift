//
//  CardViewController.swift
//  classical_reading
//
//  Created by 남기범 on 28/04/2019.
//  Copyright © 2019 남기범. All rights reserved.
//

import UIKit
import TextFieldEffects
import Alamofire

struct 예약응답결과처리:Codable{
    let id:String
}

class CardViewController: UIViewController,UITextFieldDelegate,UIPickerViewDelegate,UIPickerViewDataSource {

    @IBOutlet weak var handleArea: UIView!
    
    @IBOutlet weak var domain: AkiraTextField!
    @IBOutlet weak var bookName: AkiraTextField!
    
    var sDay = ""
    var sTime = ""
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //키보드에 툴바 추가:Done 버튼만
        let keyboardToolbar = UIToolbar()
        keyboardToolbar.sizeToFit()
        let flexibleSpace = UIBarButtonItem(barButtonSystemItem: UIBarButtonItem.SystemItem.flexibleSpace, target: nil, action: nil)
        let doneButton = UIBarButtonItem(barButtonSystemItem: UIBarButtonItem.SystemItem.done, target: self, action: #selector(self.doneClicked))
        keyboardToolbar.setItems([flexibleSpace,doneButton], animated: false)
        
        domain.delegate = self
        domain.inputAccessoryView = keyboardToolbar
        bookName.delegate = self
        bookName.inputAccessoryView = keyboardToolbar
        //////////////////////////////////////////////
        
        //키보드 대신 pickerView 추가
        var bkFieldPickerView = UIPickerView()
        bkFieldPickerView.delegate = self
        bkFieldPickerView.tag = 1
        domain.inputView = bkFieldPickerView
        
        var bkNamePickerView = UIPickerView()
        bkNamePickerView.delegate = self
        bkNamePickerView.tag = 2
        bookName.inputView = bkNamePickerView
    }

    
    @IBAction func reserveButton(_ sender: Any) {
        if domain.text == "" || bookName.text == ""{
            ////////alert 활용
            let alertController = UIAlertController(title: "선택",message: "선택을 완료하지 않았습니다.", preferredStyle: UIAlertController.Style.alert)
            
            let cancelButton = UIAlertAction(title: "취소", style: UIAlertAction.Style.cancel, handler: nil)
            alertController.addAction(cancelButton)
            self.present(alertController,animated: true,completion: nil)
            ////////////////
        }else{
            ////////alert 활용
            //서버통신 코드
            var day_요일 = ""
            for i in 0..<요일정보.count{
                if 요일정보[i].날짜 == 예약일자{
                    day_요일 = 요일정보[i].요일
                    print(1)
                }
            }
            let bookname_String = self.bookName.text!
            let url_status2 = "http://15.164.113.118:3000/?status=2&id=\(schoolNumber)&title=\(bookname_String)&hakgi=2019-1학기&date=\(예약일자)&time=\(예약시간)&day=\(day_요일)"
            
            let encoded = url_status2.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
            let final_url = URL(string: encoded)!
            
            let rek = Alamofire.request(final_url, method: .post, encoding: URLEncoding.methodDependent, headers: [:]).responseString { (response) in
                
                let datat = response.result.value!.data(using: .utf8)
                
                do {
                    let data = try JSONDecoder().decode(예약응답결과처리.self, from: datat!)
                    print(data.id)
                    
                    if data.id == "0"{
                        let alertController_실패 = UIAlertController(title: "예약",message: "예약이 이미 존재합니다.", preferredStyle: UIAlertController.Style.alert)
                        let cancelButton_실패 = UIAlertAction(title: "확인", style: UIAlertAction.Style.cancel){
                            (action) in
                            self.presentingViewController?.dismiss(animated: true, completion: nil)
                        }
                        alertController_실패.addAction(cancelButton_실패)
                        self.present(alertController_실패,animated: true,completion: nil)
                    }
                    else{
                        let alertController_성공 = UIAlertController(title: "예약",message: "예약이 완료되었습니다!", preferredStyle: UIAlertController.Style.alert)
                        let cancelButton_성공 = UIAlertAction(title: "확인", style: UIAlertAction.Style.cancel){
                            (action) in
                            self.presentingViewController?.dismiss(animated: true, completion: nil)
                        }
                        alertController_성공.addAction(cancelButton_성공)
                        self.present(alertController_성공,animated: true,completion: nil)
                    }
                }
                catch{
                    print(error)
                }
            }
            ////////////////
            
            
        }
    }
    
    @IBAction func cancelButton(_ sender: Any) {
        self.presentingViewController?.dismiss(animated: true, completion: nil)
    }
    
    //////////////done 버튼 엑션
    
    @objc func doneClicked(){
        view.endEditing(true)
    }
    ///////////////////////////////
    
    
    
    //////////////pickerView 함수
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if pickerView.tag == 1{
            return bkField.count
        }
        if pickerView.tag == 2{
            if domain.text != ""{
                if domain.text == "서양의역사와사상"{
                    return bkName[0].count
                }
                if domain.text == "동양의역사와사상"{
                    return bkName[1].count
                }
                if domain.text == "동서양의문학"{
                    return bkName[2].count
                }
                if domain.text == "과학사상"{
                    return bkName[3].count
                }
            }
        }
        return 0
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if pickerView.tag == 1{
            return bkField[row]
        }
        if pickerView.tag == 2{
            if domain.text != ""{
                if domain.text == "서양의역사와사상"{
                    return bkName[0][row]
                }
                if domain.text == "동양의역사와사상"{
                    return bkName[1][row]
                }
                if domain.text == "동서양의문학"{
                    return bkName[2][row]
                }
                if domain.text == "과학사상"{
                    return bkName[3][row]
                }
            }
        }
        return nil
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if pickerView.tag == 1{
            domain.text = bkField[row]
            bookName.text = ""
        }
        if pickerView.tag == 2{
            if domain.text != ""{
                if domain.text == "서양의역사와사상"{
                    bookName.text = bkName[0][row]
                }
                if domain.text == "동양의역사와사상"{
                    bookName.text = bkName[1][row]
                }
                if domain.text == "동서양의문학"{
                    bookName.text = bkName[2][row]
                }
                if domain.text == "과학사상"{
                    bookName.text = bkName[3][row]
                }
            }
        }
    }
    ///////////////////////////
    
}
