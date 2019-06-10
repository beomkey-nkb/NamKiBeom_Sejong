//
//  ChildViewController.swift
//  classical_reading
//
//  Created by 남기범 on 24/05/2019.
//  Copyright © 2019 남기범. All rights reserved.
//

import UIKit
import XLPagerTabStrip

let 서양랭킹책이름 = ["플라톤의 국가","정치학","키케로의 의무론","게르마니아","성어거스틴의 고백록"]
let 서양통과율 = ["78%","72%","65%","58%","55%"]
let 서양책이미지 = [UIImage(named: "서양1"),UIImage(named: "서양2"),UIImage(named: "서양3"),UIImage(named: "서양4"),UIImage(named: "서양5")]
let 동양랭킹책이름 = ["성학십도","북학의","조선상고사","삼국유사","논어-슬기바다1"]
let 동양통과율 = ["82%","78%","72%","69%","57%"]
let 동양책이미지 = [UIImage(named: "동양1"),UIImage(named: "동양2"),UIImage(named: "동양3"),UIImage(named: "동양4"),UIImage(named: "동양5")]
let 동서양랭킹책이름 = ["젊은 예술가의 초상","구토","실락원","파우스트","일리아스/오디세이아"]
let 동서양통과율 = ["87%","79%","75%","72%","69%"]
let 동서양책이미지 = [UIImage(named: "동서양1"),UIImage(named: "동서양2"),UIImage(named: "동서양3"),UIImage(named: "동서양4"),UIImage(named: "동서양5")]
let 과학랭킹책이름 = ["프린키피아3","종의 기원","부분과 전체","과학혁명의 구조","카오스"]
let 과학통과율 = ["81%","75%","71%","68%","52%"]
let 과학책이미지 = [UIImage(named: "과학1"),UIImage(named: "과학2"),UIImage(named: "과학3"),UIImage(named: "과학4"),UIImage(named: "과학5")]


class ChildViewController: UIViewController, IndicatorInfoProvider, UITableViewDelegate, UITableViewDataSource {
    var childNumber = ""
    
    func indicatorInfo(for pagerTabStripController: PagerTabStripViewController) -> IndicatorInfo {
        return IndicatorInfo(title: "\(childNumber)")
    }
    

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 5
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell123")!
        
        var images = cell.viewWithTag(1) as? UIImageView
        var 책이름들 = cell.viewWithTag(2) as? UILabel
        var 통과율들 = cell.viewWithTag(3) as? UILabel
        var 순위 = cell.viewWithTag(4) as? UILabel
        
        if childNumber == "서양 역사"{
            images?.image = 서양책이미지[indexPath.row]
            책이름들?.text = 서양랭킹책이름[indexPath.row]
            통과율들?.text = 서양통과율[indexPath.row]
            순위?.text = "TOP" + "\(indexPath.row+1)"
        }
        else if childNumber == "동양 역사"
        {
            images?.image = 동양책이미지[indexPath.row]
            책이름들?.text = 동양랭킹책이름[indexPath.row]
            통과율들?.text = 동양통과율[indexPath.row]
            순위?.text = "TOP" + "\(indexPath.row+1)"
        }
        else if childNumber == "동서양 문학"
        {
            images?.image = 동서양책이미지[indexPath.row]
            책이름들?.text = 동서양랭킹책이름[indexPath.row]
            통과율들?.text = 동서양통과율[indexPath.row]
            순위?.text = "TOP" + "\(indexPath.row+1)"
        }
        else
        {
            images?.image = 과학책이미지[indexPath.row]
            책이름들?.text = 과학랭킹책이름[indexPath.row]
            통과율들?.text = 과학통과율[indexPath.row]
            순위?.text = "TOP" + "\(indexPath.row+1)"
        }
        return cell
    }
    
}
