phantom.casperPath = './node_modules/casperjs';
phantom.injectJs('./node_modules/casperjs/bin/bootstrap.js');
 
var fs = require('fs')
var system = require('system');
 
var casper = require('casper').create({
    pageSettings: {
        loadImages: true,//The script is much faster when this field is set to false
        loadPlugins: false
    }
});

var id1 = system.args[1];
var password1 = system.args[2];

casper.start().thenOpen("https://portal.sejong.ac.kr/jsp/login/loginSSL.jsp?rtUrl=classic.sejong.ac.kr/ssoLogin.do", function() {
});
 
casper.then(function () {
    casper.fill("body > form", {
        id: id1 ,
        password: password1
    }, false);
});

casper.then(function(){
    this.evaluate(function(){
            document.querySelector('button[onclick="beforeSubmit();"]').click()
    });
});
casper.thenOpen("http://classic.sejong.ac.kr/userCertStatus.do?menuInfoId=MAIN_02_05", function() {
});
 
//페이지 저장하기
casper.then(function(){
    console.log(this.getHTML());
});
 
 
casper.run();