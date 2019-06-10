//
//  BookselectController.swift
//  classical_reading
//
//  Created by 남기범 on 28/04/2019.
//  Copyright © 2019 남기범. All rights reserved.
//

import UIKit
import TextFieldEffects

var 예약일자 = ""
var 예약시간 = ""

class BookselectController: UIViewController{
    
    var selectedDay = ""
    var selectedTime = ""
    
    enum CardState {
        case expanded
        case collapsed
    }

    @IBOutlet weak var userName: UILabel!
    @IBOutlet weak var IDNumber: UILabel!
    @IBOutlet weak var Department: UILabel!
    @IBOutlet weak var reserveDate: UILabel!
    @IBOutlet weak var reserveTime: UILabel!
    
    ///////////////////////// CardView 세팅
    var cardViewController:CardViewController!
    var visualEffectView:UIVisualEffectView!
    
    let cardHeight:CGFloat = 600
    let cardHandleAreaHeight:CGFloat = 100
    
    var cardVisible = false
    var nextState:CardState {
        return cardVisible ? .collapsed : .expanded
    }
    
    var runningAnimations = [UIViewPropertyAnimator]()
    var animationProgressWhenInterrupted:CGFloat = 0
    /////////////////////////
    
    override func viewDidLoad() {
        super.viewDidLoad()
        userName.text = name
        IDNumber.text = schoolNumber
        Department.text = major
        reserveDate.text = selectedDay
        reserveTime.text = selectedTime
        예약일자 = selectedDay
        setupCard()
    }
    
    func setupCard(){
        visualEffectView = UIVisualEffectView()
        visualEffectView.frame = self.view.frame
        self.view.addSubview(visualEffectView)
        
        cardViewController = CardViewController(nibName:"CardViewController", bundle: nil)
        self.addChild(cardViewController)
        self.view.addSubview(cardViewController.view)
        
        cardViewController.view.frame = CGRect(x: 0, y: self.view.frame.height - cardHandleAreaHeight, width: self.view.bounds.width, height: cardHeight)
        
        cardViewController.view.clipsToBounds = true
        
        let tapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(BookselectController.handleCardTap(recognzier:)))
        let panGestureRecognizer = UIPanGestureRecognizer(target: self, action: #selector(BookselectController.handleCardPan(recognizer:)))
        
        cardViewController.handleArea.addGestureRecognizer(tapGestureRecognizer)
        cardViewController.handleArea.addGestureRecognizer(panGestureRecognizer)
    }
    

    @objc
    func handleCardTap(recognzier:UITapGestureRecognizer){
        switch recognzier.state{
        case .ended:
            animateTransitionIfNeeded(state: nextState, duration: 0.9)
        default:
            break
        }
    }
    @objc
    func handleCardPan(recognizer:UIPanGestureRecognizer){
        switch recognizer.state {
        case .began:
            startInteractiveTransition(state: nextState, duration: 0.9)
        case .changed:
            let translation = recognizer.translation(in: self.cardViewController.handleArea)
            var fractionComplete = translation.y / cardHeight
            fractionComplete = cardVisible ? fractionComplete : -fractionComplete
            updateInteractiveTransition(fractionCompleted: fractionComplete)
        case .ended:
            continueInteractiveTransition()
        default:
            break
        }
        
    }
    
    func animateTransitionIfNeeded(state:CardState, duration:TimeInterval){
        if runningAnimations.isEmpty{
            let frameAnimator = UIViewPropertyAnimator(duration: duration, dampingRatio: 1){
                switch state{
                case .expanded:
                    self.cardViewController.view.frame.origin.y = self.view.frame.height - self.cardHeight
                case .collapsed:
                    self.cardViewController.view.frame.origin.y = self.view.frame.height - self.cardHandleAreaHeight
                }
            }
            
            frameAnimator.addCompletion{ _ in
                self.cardVisible = !self.cardVisible
                self.runningAnimations.removeAll()
            }
            
            frameAnimator.startAnimation()
            runningAnimations.append(frameAnimator)
            
            let cornerRadiusAnimator = UIViewPropertyAnimator(duration: duration, curve: .linear)
            {
                switch state{
                case .expanded:
                    self.cardViewController.view.layer.cornerRadius = 12
                case .collapsed:
                    self.cardViewController.view.layer.cornerRadius = 12
                }
                
            }
            
            cornerRadiusAnimator.startAnimation()
            runningAnimations.append(cornerRadiusAnimator)
            
            let blurAnimator = UIViewPropertyAnimator(duration: duration, dampingRatio: 1)
            {
                switch state {
                case .expanded:
                    self.visualEffectView.effect = UIBlurEffect(style: .dark)
                case .collapsed:
                    self.visualEffectView.effect = nil
                }
            }
            
            blurAnimator.startAnimation()
            runningAnimations.append(blurAnimator)
        }
    }
    
    func startInteractiveTransition(state:CardState, duration:TimeInterval){
        if runningAnimations.isEmpty{
            animateTransitionIfNeeded(state: state, duration: duration)
        }
        for animator in runningAnimations {
            animator.pauseAnimation()
            animationProgressWhenInterrupted = animator.fractionComplete
        }
    }
    
    func updateInteractiveTransition(fractionCompleted:CGFloat){
        for animator in runningAnimations{
            animator.fractionComplete = fractionCompleted + animationProgressWhenInterrupted
        }
    }
    
    func continueInteractiveTransition(){
        for animator in runningAnimations{
            animator.continueAnimation(withTimingParameters: nil, durationFactor: 0)
        }
    }
    

    
}
