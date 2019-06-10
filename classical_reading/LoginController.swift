//
//  LoginController.swift
//  classical_reading
//
//  Created by 남기범 on 25/03/2019.
//  Copyright © 2019 남기범. All rights reserved.
//

import UIKit
import TextFieldEffects
import Alamofire

struct 인증현황{
    let 서양의역사와사상:String
    let 동양의역사와사상:String
    let 동서양의문학:String
    let 과학사상:String
    let 합계:String
}

struct 시험인증현황{
    let 년도학기:String
    let 영역명:String
    let 도서명:String
    let 응시일자:String
    let 점수:String
    let 합격여부:String
}

struct 대체과목현황{
    let 년도학기:String
    let 과목명:String
    let 영역명:String
    let 도서명:String
    let 이수여부:String
}

struct 대회인증현황{
    let 년도학기:String
    let 대회명:String
    let 영역명:String
    let 도서명:String
}

var array_인증현황 = [인증현황]()
var array_시험인증현황 = [시험인증현황]()
var array_대체과목현황 = [대체과목현황]()
var array_대회인증현황 = [대회인증현황]()
var array_예약확인정보 = [예약확인정보]()

struct element_예약:Codable{
    let id:String
    let title:String
    let hakgi:String
    let date:String
    let time:String
    let day:String
}

struct 예약정보:Codable{
    let key:[element_예약]
}

struct 예약확인정보{
    var id:String
    var title:String
    var hakgi:String
    var date:String
    var time:String
    var day:String
}

class LoginController: UIViewController,UITextFieldDelegate {
    
    
    @IBOutlet weak var textField: UITextField!
    @IBOutlet weak var sejongLogo: UIImageView!
    @IBOutlet weak var password: IsaoTextField!
    @IBOutlet weak var btn_box: UIButton!
    
    @IBOutlet weak var indicator: UIActivityIndicatorView!
    
    
    
    override var preferredStatusBarStyle:UIStatusBarStyle {
        return UIStatusBarStyle.lightContent //상태표시줄 색상 흰색
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        array_예약확인정보.removeAll()
        array_인증현황.removeAll()
        array_대체과목현황.removeAll()
        array_대회인증현황.removeAll()
        array_시험인증현황.removeAll()
        
        indicator.startAnimating()
        textField.delegate = self
        password.delegate = self
        
        NotificationCenter.default.addObserver(self, selector: #selector(self.keyboardWillShow(_:)), name: UIResponder.keyboardWillShowNotification, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(self.keyboardWillHide(_:)), name: UIResponder.keyboardWillHideNotification, object: nil)//키보드 화면 조정
        
        sejongLogo.layer.cornerRadius = sejongLogo.frame.height/2
        sejongLogo.layer.borderWidth = 1
        sejongLogo.layer.borderColor = UIColor.clear.cgColor
        sejongLogo.clipsToBounds = true//이미지 동그랗게
        indicator.stopAnimating()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        array_예약확인정보.removeAll()
        array_인증현황.removeAll()
        array_대체과목현황.removeAll()
        array_대회인증현황.removeAll()
        array_시험인증현황.removeAll()
        if let userID = UserDefaults.standard.string(forKey: "id"){
            array_예약확인정보.removeAll()
            array_인증현황.removeAll()
            array_대체과목현황.removeAll()
            array_대회인증현황.removeAll()
            array_시험인증현황.removeAll()
            
            
            indicator.startAnimating()
            self.textField.text = userID
            self.password.text = UserDefaults.standard.string(forKey: "pw")!
            print(userID)
            schoolNumber = userID
            password1 = self.password.text!
            
            let urlview = "http://15.164.113.118:3000/?status=1&id=\(schoolNumber)&password=\(password1)"
            let urlReserved = "http://15.164.113.118:3000/?status=4&id=\(schoolNumber)"
            
            let req = Alamofire.request(urlview, method: .post, encoding: URLEncoding.methodDependent, headers: [:]).responseString { (response) in
                let datat = response.result.value!.data(using: .utf8)
                
                do {
                    let data = try JSONDecoder().decode(Form.self, from: datat!)
                    if(data.status == "1")
                    {
                        name = data.이름
                        schoolNumber = data.학번
                        major = data.학과
                        
                        var 인증현황_t = 인증현황(서양의역사와사상: data.인증현황[0].서양의역사와사상, 동양의역사와사상: data.인증현황[0].동양의역사와사상, 동서양의문학: data.인증현황[0].동서양의문학, 과학사상: data.인증현황[0].과학사상, 합계: data.인증현황[0].합계)
                        array_인증현황.append(인증현황_t)
                        
                        for i in 0..<data.시험인증현황.count{
                            var 시험인증현황_t = 시험인증현황(년도학기: data.시험인증현황[i].년도학기, 영역명: data.시험인증현황[i].영역명, 도서명: data.시험인증현황[i].도서명, 응시일자: data.시험인증현황[i].응시일자, 점수: data.시험인증현황[i].점수, 합격여부: data.시험인증현황[i].합격여부)
                            
                            array_시험인증현황.append(시험인증현황_t)
                        }
                        print(array_시험인증현황.count)
                        
                        for i in 0..<data.대체과목현황.count{
                            var 대체과목현황_t = 대체과목현황(년도학기: data.대체과목현황[i].년도학기, 과목명: data.대체과목현황[i].과목명, 영역명: data.대체과목현황[i].영역명, 도서명: data.대체과목현황[i].도서명, 이수여부: data.대체과목현황[i].이수여부)
                            
                            array_대체과목현황.append(대체과목현황_t)
                        }
                        
                        for i in 0..<data.대회인증현황.count{
                            var 대회인증현황_t = 대회인증현황(년도학기: data.대회인증현황[i].년도학기, 대회명: data.대회인증현황[i].대회명, 영역명: data.대회인증현황[i].영역명, 도서명: data.대회인증현황[i].도서명)
                            
                            array_대회인증현황.append(대회인증현황_t)
                            
                            self.indicator.stopAnimating()
                            
                            if let xvc = self.storyboard?.instantiateViewController(withIdentifier: "TabBar") as? UITabBarController{
                                
                                self.present(xvc, animated: true, completion: nil)
                                
                            }
                        }
                    }else{
                        self.indicator.stopAnimating()
                        array_예약확인정보.removeAll()
                        array_인증현황.removeAll()
                        array_대체과목현황.removeAll()
                        array_대회인증현황.removeAll()
                        array_시험인증현황.removeAll()
                        let alertController_로그인 = UIAlertController(title: "로그인",message: "정보가 정확하지 않습니다!", preferredStyle: UIAlertController.Style.alert)
                        let cancelButton_로그인 = UIAlertAction(title: "확인", style: UIAlertAction.Style.cancel, handler: nil)
                        alertController_로그인.addAction(cancelButton_로그인)
                        self.present(alertController_로그인,animated: true,completion: nil)
                        
                    }
                    
                } catch {
                    print(error)
                }
            }
            let res = Alamofire.request(urlReserved, method: .post, encoding: URLEncoding.methodDependent, headers: [:]).responseString { (response) in
                
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
                }catch{
                    print(error)
                }
            }
        }
    }
    
    @objc func keyboardWillShow(_ sender:Notification){
        self.view.frame.origin.y = -150
    }
    
    @objc func keyboardWillHide(_ sender:Notification){
        self.view.frame.origin.y = 0
    }
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        
        textField.resignFirstResponder()
        password.resignFirstResponder()
        return true
    }
    
    @IBAction func autoLoginAction(_ sender: Any) {
        // auto login 선택 여부
        if (btn_box.isSelected == true)
        {
            btn_box.isSelected = false;
        }
        else
        {
            btn_box.isSelected = true;
        }
    }
    
    
    @IBAction func login_Action(_ sender: Any) {
        array_예약확인정보.removeAll()
        array_인증현황.removeAll()
        array_대체과목현황.removeAll()
        array_대회인증현황.removeAll()
        array_시험인증현황.removeAll()
        if btn_box.isSelected == true{
            
            print("auto login")
            schoolNumber = self.textField.text!
            password1 = self.password.text!
            UserDefaults.standard.set(self.textField.text, forKey: "id")
            UserDefaults.standard.set(self.password.text, forKey: "pw")
            print(password1)
            print(schoolNumber)
            //201ehd1603gh
            indicator.startAnimating()
            let urlview = "http://15.164.113.118:3000/?status=1&id=\(schoolNumber)&password=\(password1)"
            let urlReserved = "http://15.164.113.118:3000/?status=4&id=\(schoolNumber)"
        
            let req = Alamofire.request(urlview, method: .post, encoding: URLEncoding.methodDependent, headers: [:]).responseString { (response) in
                let datat = response.result.value!.data(using: .utf8)
                do {
                    let data = try JSONDecoder().decode(Form.self, from: datat!)
                    if(data.status == "1")
                    {
                        name = data.이름
                        schoolNumber = data.학번
                        major = data.학과
                        
                        var 인증현황_t = 인증현황(서양의역사와사상: data.인증현황[0].서양의역사와사상, 동양의역사와사상: data.인증현황[0].동양의역사와사상, 동서양의문학: data.인증현황[0].동서양의문학, 과학사상: data.인증현황[0].과학사상, 합계: data.인증현황[0].합계)
                        array_인증현황.append(인증현황_t)
                        
                        for i in 0..<data.시험인증현황.count{
                            var 시험인증현황_t = 시험인증현황(년도학기: data.시험인증현황[i].년도학기, 영역명: data.시험인증현황[i].영역명, 도서명: data.시험인증현황[i].도서명, 응시일자: data.시험인증현황[i].응시일자, 점수: data.시험인증현황[i].점수, 합격여부: data.시험인증현황[i].합격여부)
                            
                            array_시험인증현황.append(시험인증현황_t)
                        }
                        print(array_시험인증현황.count)
                        
                        for i in 0..<data.대체과목현황.count{
                            var 대체과목현황_t = 대체과목현황(년도학기: data.대체과목현황[i].년도학기, 과목명: data.대체과목현황[i].과목명, 영역명: data.대체과목현황[i].영역명, 도서명: data.대체과목현황[i].도서명, 이수여부: data.대체과목현황[i].이수여부)
                            
                            array_대체과목현황.append(대체과목현황_t)
                        }
                        
                        for i in 0..<data.대회인증현황.count{
                            var 대회인증현황_t = 대회인증현황(년도학기: data.대회인증현황[i].년도학기, 대회명: data.대회인증현황[i].대회명, 영역명: data.대회인증현황[i].영역명, 도서명: data.대회인증현황[i].도서명)
                            
                            array_대회인증현황.append(대회인증현황_t)
                            
                            self.indicator.stopAnimating()
                            
                            if let xvc = self.storyboard?.instantiateViewController(withIdentifier: "TabBar") as? UITabBarController{
                                
                                self.present(xvc, animated: true, completion: nil)
                                
                            }
                        }
                    }else{
                        self.indicator.stopAnimating()
                        array_예약확인정보.removeAll()
                        array_인증현황.removeAll()
                        array_대체과목현황.removeAll()
                        array_대회인증현황.removeAll()
                        array_시험인증현황.removeAll()
                        let alertController_로그인 = UIAlertController(title: "로그인",message: "정보가 정확하지 않습니다!", preferredStyle: UIAlertController.Style.alert)
                        let cancelButton_로그인 = UIAlertAction(title: "확인", style: UIAlertAction.Style.cancel, handler: nil)
                        alertController_로그인.addAction(cancelButton_로그인)
                        self.present(alertController_로그인,animated: true,completion: nil)
                    }
                    
                } catch {
                    print(error)
                }
            }
            let res = Alamofire.request(urlReserved, method: .post, encoding: URLEncoding.methodDependent, headers: [:]).responseString { (response) in
                
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
                }catch{
                    print(error)
                }
            }
        }
        else{
            password1 = self.password.text!
            schoolNumber = self.textField.text!
            
            indicator.startAnimating()
            array_예약확인정보.removeAll()
            array_인증현황.removeAll()
            array_대체과목현황.removeAll()
            array_대회인증현황.removeAll()
            array_시험인증현황.removeAll()
            let urlview = "http://15.164.113.118:3000/?status=1&id=\(schoolNumber)&password=\(password1)"
            let urlReserved = "http://15.164.113.118:3000/?status=4&id=\(schoolNumber)"
            
            let req = Alamofire.request(urlview, method: .post, encoding: URLEncoding.methodDependent, headers: [:]).responseString { (response) in
                
                let datat = response.result.value!.data(using: .utf8)
                
                do {
                    let data = try JSONDecoder().decode(Form.self, from: datat!)
                    
                    if(data.status == "1")
                    {
                        name = data.이름
                        schoolNumber = data.학번
                        major = data.학과
                        
                        var 인증현황_t = 인증현황(서양의역사와사상: data.인증현황[0].서양의역사와사상, 동양의역사와사상: data.인증현황[0].동양의역사와사상, 동서양의문학: data.인증현황[0].동서양의문학, 과학사상: data.인증현황[0].과학사상, 합계: data.인증현황[0].합계)
                        array_인증현황.append(인증현황_t)
                        
                        for i in 0..<data.시험인증현황.count{
                            var 시험인증현황_t = 시험인증현황(년도학기: data.시험인증현황[i].년도학기, 영역명: data.시험인증현황[i].영역명, 도서명: data.시험인증현황[i].도서명, 응시일자: data.시험인증현황[i].응시일자, 점수: data.시험인증현황[i].점수, 합격여부: data.시험인증현황[i].합격여부)
                            
                            array_시험인증현황.append(시험인증현황_t)
                        }
                        print(array_시험인증현황.count)
                        for i in 0..<data.대체과목현황.count{
                            var 대체과목현황_t = 대체과목현황(년도학기: data.대체과목현황[i].년도학기, 과목명: data.대체과목현황[i].과목명, 영역명: data.대체과목현황[i].영역명, 도서명: data.대체과목현황[i].도서명, 이수여부: data.대체과목현황[i].이수여부)
                            
                            array_대체과목현황.append(대체과목현황_t)
                        }
                        
                        for i in 0..<data.대회인증현황.count{
                            var 대회인증현황_t = 대회인증현황(년도학기: data.대회인증현황[i].년도학기, 대회명: data.대회인증현황[i].대회명, 영역명: data.대회인증현황[i].영역명, 도서명: data.대회인증현황[i].도서명)
                            
                            array_대회인증현황.append(대회인증현황_t)
                            
                            self.indicator.stopAnimating()
                            
                            if let xvc = self.storyboard?.instantiateViewController(withIdentifier: "TabBar") as? UITabBarController{
                                
                                self.present(xvc, animated: true, completion: nil)
                                
                            }
                        }
                    }else{
                        self.indicator.stopAnimating()
                        array_예약확인정보.removeAll()
                        array_인증현황.removeAll()
                        array_대체과목현황.removeAll()
                        array_대회인증현황.removeAll()
                        array_시험인증현황.removeAll()
                        let alertController_로그인 = UIAlertController(title: "로그인",message: "정보가 정확하지 않습니다!", preferredStyle: UIAlertController.Style.alert)
                        let cancelButton_로그인 = UIAlertAction(title: "확인", style: UIAlertAction.Style.cancel, handler: nil)
                        alertController_로그인.addAction(cancelButton_로그인)
                        self.present(alertController_로그인,animated: true,completion: nil)
                    }
                    
                } catch {
                    print(error)
                }
            }
            let res = Alamofire.request(urlReserved, method: .post, encoding: URLEncoding.methodDependent, headers: [:]).responseString { (response) in
                
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
                }catch{
                    print(error)
                }
            }
        }
    }

}
