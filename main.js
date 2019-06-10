var childProcess = require('child_process');
var phantomjs = require('phantomjs');
var cheerio = require('cheerio');
var binPath = phantomjs.path;
var path = require('path');
var express = require('express');
var http = require('http');
var url = require('url');
var mysql      = require('mysql');

var con = mysql.createConnection({
  host     : 'localhost',//접속할 데이터베이스 주소  이거 외부접근 해보는중인데 너무 빡셈
  user     : 'root',//사용자 이름
  password : '',//호스트 비밀번호
  database : 'o69'// 내가 설정한 데이터베이스 이름
});//이거 원래는 보안상으로 이렇게하면안댐 현재는 내 컴퓨터랑 맞추는 중

con.connect();// db랑 연결성공해버림



var app = express();
app.set('port', process.env.PORT || 3000);//서버 3000번 포트로 열기, express 모듈 사용

app.use(function(req, res, next){
    if(req.url == '/'){
      url = '/index.html';
    }
    if(req.url == '/favicon.ico'){
      return res.writeHead(404);
    }
    
    console.log('미들웨어실행');
    
    var _url = req.url;
    var querydata = require('url').parse(_url,true).query;
    
    var status = querydata.status;
    var id = querydata.id;
    var password = querydata.password;
    var hakbun = querydata.hakbun;
    var title = querydata.title;
    var hakgi = querydata.hakgi;
    var date = querydata.date;
    var time = querydata.time;
    var day = querydata.day;
	
    console.log(id);
    console.log(password);

   if(status == 1){
    var childArgs = [
    path.join(__dirname, 'phantom_test.js'),
        id,
        password
        
    ];
    
    console.log(id);
    console.log(password);
        
    childProcess.execFile(binPath, childArgs, function(err, stdout, stderr) {
        console.log('success');
        var parse1 = []; //인증현황
        var parse2 = []; //시험대체
        var parse3 = []; //과목대체
        var parse4 = []; //대회인증
        var parsing1 = []; //데이터빈칸제거
        var parsing2 = [];
        var parsing3 = [];
        var parsing4 = [];
        var $ = cheerio.load(stdout);
        
        var loginerr = $('.subTit').text();
        var parslog =  loginerr.replace(/\s/gi,"");
        
       
        
        var idenbook = [];
     
        // 영역별 인증현황
        for(var i=0; i<5; i++){
         parse1[i] = $('.listA tbody tr td').next().slice(0).eq(i+5).text();
        parsing1[i] =   parse1[i].replace(/\s/gi,"");
        }
        
        
            idenbook[0]= {
            "서양의 역사와 사상" : parsing1[0],
            "동양의 역사와 사상" : parsing1[1],
            "동서양의 문학" : parsing1[2],
            "과학 사상" : parsing1[3],
            "합계" : parsing1[4]
        };

        //시험 인증 현황 
        var parse2count = $('.listA tbody').eq(1).children().length;
        
        var examstatus = [];
        
        for(var i=0; i<parse2count; i++){
            for(var j=0; j<6; j++){
            
                parse2[j] = $('.listA tbody').eq(1).children().eq(i).children().eq(j).text();
                parsing2[j] = parse2[j].replace(/\s/gi,"");
            }
            
            examstatus[i] = {
            "년도/학기" : parsing2[0],
            "영역명" : parsing2[1],
            "도서명" : parsing2[2],
            "응시일자" : parsing2[3],
            "점수" : parsing2[4],
            "합격여부" : parsing2[5]
            }

        }
        
      
        
        
        //과목 대체 인증
         var parse3count = $('.listA tbody').eq(2).children().length;
        
        var alterstatus = [];
        
        for(var i=0; i<parse3count; i++){
            for(var j=0; j<5; j++){
                
                parse3[j] = $('.listA tbody').eq(2).children().eq(i).children().eq(j).text();
                parsing3[j] = parse3[j].replace(/\s/gi,"");
            
            }
            
            alterstatus[i] = {
            "년도/학기" : parsing3[0],
            "과목명" : parsing3[1],
            "영역명" : parsing3[2],
            "도서명" : parsing3[3],
            "이수여부" : parsing3[4]
            }
            
        }
        
        
        
        //대회 인증 현황
        
        var parse4count = $('.listA tbody').eq(3).children().length;
        
        var leaguestatus = [];
       
     
        for(var i=0; i<parse4count; i++){
            for(var j=0; j<4; j++){
            
                parse4[j] = $('.listA tbody').eq(3).children().eq(i).children().eq(j).text();
                parsing4[j] = parse4[j].replace(/\s/gi,"");
            }
            
            leaguestatus[i] = {
            "년도/학기" : parsing4[0],
            "대회명" : parsing4[1],
            "영역명" : parsing4[2],
            "도서명" : parsing4[3]
            }
           
        }
      
        
        //학과
        var hakgua = $('.tblA li').eq(0).children().children().eq(1).text();
        //학번
        var hakbun =$('.tblA li').eq(1).children().children().eq(1).text();
    
        //이름
        var name =$('.tblA li').eq(2).children().children().eq(1).text();
        
        var parsinginfo = {
            status : "1",
            "학과" : hakgua,
            "학번" : hakbun,
            "이름" : name,
            "인증현황" : idenbook,
            "시험인증현황" : examstatus,
            "대체과목현황" : alterstatus,
            "대회인증현황" : leaguestatus
            
        };
       
             if( parslog != "나의인증현황") //로그인실패시
        {   
            examstatus[0] = {
                "년도/학기" :"",
            "영역명" : "",
            "도서명" : "",
            "응시일자" : "",
            "점수" : "",
            "합격여부" : ""
            }
            
            alterstatus[0] = {
            "년도/학기" : "",
            "과목명" : "",
            "영역명" : "",
            "도서명" : "",
            "이수여부" : ""
            }
            
             leaguestatus[0] = {
            "년도/학기" :"",
            "대회명" : "",
            "영역명" : "",
            "도서명" : ""
            }
            
            
            var loginerror = {
                status : "0",
                "학과" : hakgua,
                "학번" : hakbun,
                "이름" : name,
                "인증현황" : idenbook,
                "시험인증현황" : examstatus,
                "대체과목현황" : alterstatus,
                "대회인증현황" : leaguestatus
            };
            
            
            
            console.log("로그인실패");
            res.send(loginerror);
           
        }
             
             
            if( parslog == "나의인증현황")
            res.send(parsinginfo);
       
         
        
      
    });
    }
     if(status == 2) //예약신청 값으로 [학번(id),책제목(title),학기(hakgi),날짜(date),시간(time)] 보내줘야함
	{
        if(req.url == '/')
		{
			url = '/index.html';
    	}
		if(req.url == '/favicon.ico')
		{
			return res.writeHead(404);
		}
		
				var adr = id;
			    var sql = 'SELECT day FROM Cap WHERE id = ?' ;
		
				con.query(sql, [adr], function (err, result)
				{
					
 					if (err)
					{
						console.log(err);
					}
					else if(result != '')//예약할려는데 이새끼가 예약정보가 있어
					{
						console.log(result);
						var data123 = {
						 	
						 	id: "0"
						 	
						}
						 
						res.send(data123);
						
						console.log('정보있음ㅋ');//여기서 정보가있으니까 예약신청이 안된다는식의 에러창 띄워야함
					}
					else 
					{
						var insert = 'INSERT INTO Cap VALUES(?,?,?,?,?,?)' ;
		
						var data = [id,title,hakgi,date,time,day];
  
						con.query(insert,data,function(error,result,fields)
								  {
										if(error)
										{
											
												console.log(error);
										
										}
										else
										{
											
											console.log('예약정보가 없어서 예약정보 저장완료');
											var data123 = {
						 	
												id: "1"

						 	
						}
						 
						res.send(data123);
										}
									});
					
					}
				});
        
    }
    
    if(status == 3)//예약취소 이것도 학번 보내줘야함
	{
        var deletesql = 'DELETE FROM Cap WHERE id = ?' ;//이부분을 건드려야 값을 계속 넣을 수 있음
		var data = id;
			con.query (deletesql,data,function(error,result,fields)
					   {
							if(error)
								{
									console.log(error);
								}
							else
								{
									console.log('삭제완료');
									var data123 = {
						 	
													id: "1"
						 	
													}
						 
						res.send(data123);
								}
						});
		
		
        
    }
	
	if(status == 4) //리프레쉬 했을경우 셀렉트로 보여줌 값으로 [학번(id)] 보내야함
	{
		
		var adr = id;
			    var sql = 'SELECT * FROM Cap WHERE id = ?';
				con.query(sql, [adr], function (err, result)
				{
					
 				if (err) console.log(err);
					
					else if(result != '')//값이있어
					{
						console.log('값 띄워줘');
  						console.log(result);
						res.send(result);//json형식
						
					}
					else 
					{
						var ssd = [];
						console.log('값이 없음 리프레쉬 결과');
						var data123= {
						 	
						 	id: "1",
						 	title: "",
							hakgi: "",
							date: "",
							time: "",
							day: "",
						}
						
						ssd[0]=data123;
						
						res.send(ssd);
					}
						
				});
		
		
				
	}
	
})

http.createServer(app).listen(app.get('port'), function(){//서버 생성
   console.log('express server start : '+app.get('port'));
});
