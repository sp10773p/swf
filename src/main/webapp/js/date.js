/*
 ===============================================================================
 ==  Date 관련 공통 함수
 ===============================================================================
 ● gfn_isDate       : 날짜에 대한 형식 체크
 ● gfn_isDate6      : 날짜 형태의 8자리 입력값 인지 체크하는 함수
 ● gfn_isDate8      : 날짜 형태의 8자리 입력값 인지 체크하는 함수
 ● gfn_today        : 해당 PC의 오늘 날짜를 가져온다.
 ● gfn_todayTime    : 해당 PC의 오늘 날짜+시간를 가져온다.
 ● gfn_getYear      : 해당년도 구하기
 ● gfn_getYearMonth : 해당년월 구하기
 ● gfn_getMonth     : 해당월 구하기 
 ● gfn_getFirstDate    : 해당월의 시작 날짜를 yyyyMMdd형태로 구하기
 ● gfn_getLastDate     : 해당월의 마지막 날짜를 yyyyMMdd형태로 구하기
 ● gfn_getLastDateNum  : 해당월의 마지막 날짜를 숫자로 구하기
 ● gfn_addDate      : 입력된 날자에 OffSet 으로 지정된 만큼의 일을 더한다.
 ● gfn_addMonth     : 입력된 날자에 OffSet 으로 지정된 만큼의 달을 더한다.
 ● gfn_datetime     : MiPlatform에서 사용하던 Datetime형식으로 변환
 ● gfn_makeDate     : 년,월,일 숫자 값을 받아 년월일 string을 리턴한다.
 ● gfn_getDiffDay   : 2개의 날짜간의 Day count
 ● gfn_getDiffMonth : 2개의 날짜간의 Month count 
 ● gfn_getDay       : 입력된 날자로부터 요일을 구함
 ● gfn_getDayName   : 입력된 날자로부터 요일명을 구함
 ● gfn_strToDate    : String 타입의 형식을 날짜형식으로 변환
 ● gfn_date2Str3    : 문자를 날짜로 변환
 ● gfn_dateToStr    : 날짜를 문자로 변환
 ● gfn_isLeapYear   : 윤년여부 확인
 ● gfn_solar2Lunar  : 양력을 음력으로 변환해주는 함수 (처리가능 기간  1841 - 2043년)
 ● gfn_lunar2Solar  : 음력을 양력으로 변환해주는 함수 (처리가능 기간  1841 - 2043년)
 ● gfn_getHolidays  : 양력 nYear에 해당하는 년도의 법정 공휴일(양력) List 모두 구하기
 ● gfn_getOneMonthAfter  : 한달후 날짜 구하는 함수
 */




 /**
 * @class 날짜에 대한 형식 체크
 * @param sDate  - 일자(yyyy-MM-dd 등)
 * @return boolean
 */   
function gfn_isDate (sDate)
{
	var stringWrapper = new String(sDate);
 	stringWrapper = stringWrapper.replace("/","").replace("/","");
 	stringWrapper = stringWrapper.replace("-","").replace("-","");
 	stringWrapper = stringWrapper.replace(".","").replace(".","");
	
	if( stringWrapper.toString().length !== 8 ) return false;
	
	var iMonth  = Math.floor(stringWrapper.slice(4,6), 10);
	var iDate   = Math.floor(stringWrapper.slice(6,8), 10);
		
	if( iMonth < 1 || iMonth > 12 ) return false;	
	if( iDate < 1 || iDate > gfn_getLastDateNum(stringWrapper) ) return false;	
	
	return true;
}

/**
 * @class 6자리 년월 날짜에 대한 형식 체크
 * @param sDate  - 일자(yyyyMM)
 * @return boolean
 */    
function gfn_isDate6 (sDate)
{
	if (gfn_length(sDate) != 6) 
	{
		return false;
	}
	else if (!gfn_isDate(sDate + "01")) 
	{
		return false;
	}
	return true;
}

/**
 * @class 8자리 년월 날짜에 대한 형식 체크
 * @param sDate  - 일자(yyyyMMdd)
 * @return boolean
 */    
function gfn_isDate8 (sDate)
{
	if (gfn_length(sDate) != 8) 
	{
		return false;
	}
	else if (!gfn_isDate(sDate)) 
	{
		return false;
	}
	return true;
}

/**
 * @class 해당 PC의 오늘 날짜를 가져온다.
 * @return string 오늘 날짜
 */  
function gfn_today ()
{
	var strToday = "";
	var objDate = new Date();

	var strToday = objDate.getFullYear().toString();
	strToday += gfn_right("0" + (objDate.getMonth() + 1), 2);
	strToday += gfn_right("0" + objDate.getDate(), 2);

	return strToday;
}

/**
 * @class 해당 PC의 오늘 날짜와 시간를 가져온다.
 * @return string 오늘 날짜+시간
 */   
function gfn_todayTime ()
{
	var strToday = "";
	var objDate = new Date();
	var sToday = objDate.getFullYear().toString();
	sToday += gfn_right("0" + (objDate.getMonth() + 1), 2);
	sToday += gfn_right("0" + objDate.getDate(), 2);
	sToday += gfn_right("0" + objDate.getHours(), 2);
	sToday += gfn_right("0" + objDate.getMinutes(), 2);
	sToday += gfn_right("0" + objDate.getSeconds(), 2);
	// strToday += objDate.getMilliseconds();
	return sToday;
}

/**
 * @class 해당년도 구하기
 * @param sDate  - 일자(yyyyMMdd) 
 * @return string yyyy형태의 년도 ( 예 : "2012" )
 */    
function gfn_getYear (sDate)
{
	if (gfn_isNull(sDate)) 
	{
		sDate = gfn_today();
	}

	return sDate.substr(0, 4);
}

/**
 * @class 해당년월 구하기
 * @param sDate  - 일자(yyyyMMdd) 
 * @return string yyyyMM형태의 년월 ( 예 : "201211" )
 */  
function gfn_getYearMonth (sDate)
{
	if (gfn_isNull(sDate)) 
	{
		sDate = gfn_today();
	}

	return sDate.substr(0, 6);
}

/**
 * @class 해당월 구하기
 * @param sDate  - 일자(yyyyMMdd) 
 * @return string MM형태의 년월 ( 예 : "11" )
 */   
function gfn_getMonth (sDate)
{
	if (gfn_isNull(sDate)) 
	{
		sDate = gfn_today();
	}

	return sDate.substr(4, 2);
}

/**
 * @class 해당월의 시작 날짜를 yyyyMMdd형태로 구하기
 * @param sDate  - 일자(yyyyMMdd) 
 * @return string yyyyMMdd형태의 시작 날짜 ( 예 : "20121101" )
 */  
function gfn_getFirstDate (sDate)
{
	if (gfn_isNull(sDate)) 
	{
		sDate = gfn_today();
	}

	return sDate.substr(0, 6) + "01";
}

/**
 * @class 해당월의 마지막 날짜를 yyyyMMdd형태로 구하기
 * @param sDate  - 일자(yyyyMMdd) 
 * @return string yyyyMMdd형태의 마지막 날짜 ( 예 : "20121130" )
 */  
function gfn_getLastDate (sDate)
{
	if (gfn_isNull(sDate)) 
	{
		sDate = gfn_today();
	}

	var nLastDate = gfn_getLastDateNum(sDate);

	return sDate.substr(0, 6) + nLastDate.toString();
}

/**
 * @class 해당월의 마지막 날짜를 숫자로 구하기
 * @param sDate  - 일자(yyyyMMdd) 
 * @return integer 마지막 날짜 숫자값 ( 예 : "30" )
 */   
function gfn_getLastDateNum (sDate)
{
	var nMonth,nLastDate;

	if (gfn_isNull(sDate)) 
	{
		return -1;
	}

	nMonth = parseInt(sDate.substr(4, 2), 10);
	if (nMonth == 1 || nMonth == 3 || nMonth == 5 || nMonth == 7 || nMonth == 8 || nMonth == 10 || nMonth == 12) 
	{
		nLastDate = 31;
	}
	else if (nMonth == 2) 
	{
		if (gfn_isLeapYear(sDate) == true) 
		{
			nLastDate = 29;
		}
		else 
		{
			nLastDate = 28;
		}
	}
	else 
	{
		nLastDate = 30;
	}

	return nLastDate;
}

/**
 * @class 입력된 날자에 OffSet 으로 지정된 만큼의 일을 더한다.
 * @param sDate  - 일자(yyyyMMdd) 
 * @param nOffSet  - 날짜로부터 증가 감소값. 지정하지 않으면 Default Value = 1 로 적용
 * @return string Date에 nOffset이 더해진 결과를 'yyyyMMdd'로 표현된 날자.
 */   
function gfn_addDate (sDate, nOffSet)
{
	var nYear = parseInt(sDate.substr(0, 4));
	var nMonth = parseInt(sDate.substr(4, 2));
	var nDate = parseInt(sDate.substr(6, 2)) + nOffSet;

	return gfn_datetime(nYear, nMonth, nDate);
}

/**
 * @class 입력된 날자에 OffSet 으로 지정된 만큼의 달을 더한다.
 * @param sDate  - 일자(yyyyMMdd) 
 * @param nOffSet  - 날짜로부터 증가 감소값. 지정하지 않으면 Default Value = 1 로 적용
 * @return string Date에 nOffset이 더해진 결과를 'yyyyMMdd'로 표현된 날자.
 */ 
function gfn_addMonth (sDate, nOffSet)
{
	var nYear = parseInt(sDate.substr(0, 4));
	var nMonth = parseInt(sDate.substr(4, 2)) + nOffSet;
	var nDate = parseInt(sDate.substr(6, 2));

	return gfn_datetime(nYear, nMonth, nDate);
}

/**
 * @class MiPlatform에서 사용하던 Datetime형식으로 변환 Date Type을 String으로 변환
 * @param nYear  - nYear (년도)
 * @param nMonth - nMonth (월)
 * @param nDate  - nDate (일)
 * @return string 조합한 날짜를 리턴
 */  
function gfn_datetime (nYear, nMonth, nDate)
{
	if (nYear.toString().trim().length >= 5) 
	{
		var sDate = new String(nYear);
		var nYear = sDate.substr(0, 4);
		var nMonth = sDate.substr(4, 2);
		var nDate = ((sDate.substr(6, 2) == "") ? 1 : sDate.substr(6, 2));
		var nHours = ((sDate.substr(8, 2) == "") ? 0 : sDate.substr(8, 2));
		var nMinutes = ((sDate.substr(10, 2) == "") ? 0 : sDate.substr(10, 2));
		var nSeconds = ((sDate.substr(12, 2) == "") ? 0 : sDate.substr(12, 2));

		var objDate = new Date(parseInt(nYear), parseInt(nMonth) - 1, parseInt(nDate), parseInt(nHours), parseInt(nMinutes), parseInt(nSeconds));
	}
	else 
	{
		var objDate = new Date(parseInt(nYear), parseInt(nMonth) - 1, parseInt(((nDate == null) ? 1 : nDate)));
	}

	var strYear = objDate.getYear().toString();
	var strMonth = (objDate.getMonth() + 1).toString();
	var strDate = objDate.getDate().toString();

	if (strMonth.length == 1) 
	{
		strMonth = "0" + strMonth;
	}
	if (strDate.length == 1) 
	{
		strDate = "0" + strDate;
	}

	return strYear + strMonth + strDate;
}

/**
 * @class 년,월,일 숫자 값을 받아 년월일 string을 리턴한다.
 * @param nYear  - 년도
 * @param nMonth - 월
 * @param nDate  - 날짜
 * @return string
 */
function gfn_makeDate (nYear, nMonth, nDate)
{
	if (gfn_isNull(nYear) || gfn_isNull(nMonth) || gfn_isNull(nDate)) 
	{
		return "";
	}

	var objDate = new Date(nYear, nMonth - 1, nDate);

	var sYear = objDate.getFullYear().toString();
	var sMonth = gfn_right("0" + (objDate.getMonth() + 1), 2);
	var sDate = gfn_right("0" + objDate.getDate(), 2);

	return sYear + sMonth + sDate;
}

/**
 * @class 2개의 날짜간의 Day count
 * @param sFdate - 시작일자
 * @param sTdate - 종료일자
 * @return string 양 일자간의 Day count
 */ 
function gfn_getDiffDay (sFdate, sTdate)
{
	sFdate = new String(sFdate);
	sFdate = sFdate.split(" ").join("").split("-").join("").split("/").join("");
	sTdate = new String(sTdate);
	sTdate = sTdate.split(" ").join("").split("-").join("").split("/").join("");

	sFdate = gfn_left(sFdate, 8);
	sTdate = gfn_left(sTdate, 8);

	if (sFdate.length != 8 || sTdate.length != 8
		 || isNumeric(sFdate) == false || isNumeric(sTdate) == false
		 || gfn_getDay(sFdate) == -1 || gfn_getDay(sTdate) == -1) 
	{
		return null;
	}

	var nDiffDate = gfn_strToDate(sTdate) - gfn_strToDate(sFdate);
	var nDay = 1000 * 60 * 60 * 24;

	nDiffDate = parseInt(nDiffDate / nDay);
	if (nDiffDate < 0) 
	{
		nDiffDate = nDiffDate - 1;
	}
	else 
	{
		nDiffDate = nDiffDate + 1;
	}

	return nDiffDate;
}

/**
 * @class 두 월간의 차이 월수 계산
 * @param sFdate - 시작일자
 * @param sTdate - 종료일자
 * @return Integer 숫자 형태의 차이월수(sStartDate, sEndDate의 일은 포함하지 않고 계산)
 */  
function gfn_getDiffMonth (sStartDate, sEndDate)
{
	var nStartMon,nEndMon;

	if (gfn_isNull(sStartDate) || gfn_isNull(sEndDate)) 
	{
		return NaN;
	}

	nStartMon = parseInt(sStartDate.substr(0, 4), 10) * 12 + parseInt(sStartDate.substr(4, 2), 10);
	nEndMon = parseInt(sEndDate.substr(0, 4), 10) * 12 + parseInt(sEndDate.substr(4, 2), 10);

	return (nEndMon - nStartMon);
}

/**
 * @class 입력된 날자로부터 요일을 구함
 * @param sDate  - 일자(yyyyMMdd)
 * @return Integer 요일에 따른 숫자
 */   
function gfn_getDay (sDate)
{
	var objDate = gfn_strToDate(sDate);
	return objDate.getDay();
}

/**
 * @class 입력된 날자로부터 요일명을 구함
 * @param sDate  - 일자(yyyyMMdd)
 * @return string 요일명
 */    
function gfn_getDayName (sDate)
{
	var objDayName = new Array("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT");
	var objDate = gfn_getDay(sDate);

	return objDayName[objDate];
}

/**
 * @class String 타입의 형식을 날짜형식으로 변환
 * @param sDate  - 일자(yyyyMMdd)
 * @return Date 날자
 */   
function gfn_strToDate (sDate)
{
	var nYear = parseInt(sDate.substr(0, 4));
	var nMonth = parseInt(sDate.substr(4, 2)) - 1;
	var nDate = parseInt(sDate.substr(6, 2));

	return new Date(nYear, nMonth, nDate);
}

 /**
 * @class 문자를 날짜로 변환
 * @param sDate  date object
 * @return string (yyyy-MM-dd)
 */   
function gfn_date2Str3(inDate)
{
  return inDate.substr(0, 4)+"-"+inDate.substr(4, 2)+"-"+inDate.substr(6, 2);
}

/**
 * @class 날짜를 문자로 변환
 * @param inDate  date object
 * @return string 
 */   
function gfn_dateToStr(inDate)
{
  return (new Date(inDate)).getFullYear()
		   + (((new Date(inDate)).getMonth() + 1) + "").padLeft(2, '0')
		   + (((new Date(inDate)).getDate()) + "").padLeft(2, '0');
}

/**
 * @class 윤년여부 확인
 * @param sDate  - 일자(yyyyMMdd)
 * @return boolean
 */ 
function gfn_isLeapYear (sDate)
{
	var ret;
	var nY;

	if (gfn_isNull(sDate)) 
	{
		return false;
	}

	nY = parseInt(sDate.substring(0, 4), 10);

	if ((nY % 4) == 0) 
	{
		if ((nY % 100) != 0 || (nY % 400) == 0) 
		{
			ret = true;
		}
		else 
		{
			ret = false;
		}
	}
	else 
	{
		ret = false;
	}

	return ret;
}

 /**
 * @class 양력을 음력으로 변환해주는 함수 (처리가능 기간  1841 - 2043년)
 * @param sDate  - 일자(yyyyMMdd)
 * @return string 음력날자 Flag(1 Byte) + (yyyyMMdd형태의 음력일자)
                  ( Flag : 평달 = "0", 윤달 = "1" ) 
 */  
function gfn_solar2Lunar (sDate)
{
	var sMd = "31,0,31,30,31,30,31,31,30,31,30,31";
	var aMd = new Array();

	var aBaseInfo = new Array();
	var aDt = new Array();
	var td;
	var td1;
	var td2;
	var mm,m1,m2;
	var nLy,nLm,nLd;
	var sLyoon;
	if (gfn_isNull(sDate)) 
	{
		return "";
	}

	var sY = parseInt(sDate.substr(0, 4), 10);
	var sM = parseInt(sDate.substr(4, 2), 10);
	var sD = parseInt(sDate.substr(6, 2), 10);
	if (sY < 1841 || sY > 2043) 
	{
		return "";
	}

	aBaseInfo = _solarBase();
	aMd = sMd.split(",");
	if (gfn_isLeapYear(sDate) == true) 
	{
		aMd[1] = 29;
	}
	else 
	{
		aMd[1] = 28;
	}

	td1 = 672069; //672069 = 1840 * 365 + 1840/4 - 1840/100 + 1840/400 + 23  //1840년까지 날수

	// 1841년부터 작년까지의 날수
	td2 = (sY - 1) * 365 + parseInt((sY - 1) / 4) - parseInt((sY - 1) / 100) + parseInt((sY - 1) / 400);

	// 전월까지의 날수를 더함
	for (var i = 0; i <= sM - 2; i++) 
	{
		td2 = td2 + parseInt(aMd[i]);
	}

	// 현재일까지의 날수를 더함
	td2 = td2 + sD;

	// 양력현재일과 음력 1840년까지의 날수의 차이
	td = td2 - td1 + 1;

	// 1841년부터 음력날수를 계산
	for (var i = 0; i <= sY - 1841; i++) 
	{
		aDt[i] = 0;
		for( j = 0 ; j <= 11 ; j++ )
		{
			switch( parseInt(aBaseInfo[i*12 + j]) )
			{
				case 1 : mm = 29;
						break;
				case 2 : mm = 30;
						break;				
				case 3 : mm = 58;	// 29 + 29
						break;				
				case 4 : mm = 59;	// 29 + 30
						break;				
				case 5 : mm = 59;	// 30 + 29
						break;				
				case 6 : mm = 60;	// 30 + 30
						break;				
			}
			aDt[i] = aDt[i] + mm;
		}
	}

	// 1840년 이후의 년도를 계산 - 현재까지의 일수에서 위에서 계산된 1841년부터의 매년 음력일수를 빼가면수 년도를 계산
	nLy = 0;
	do {
		td = td - aDt[nLy];
		nLy = nLy + 1;
	} while (td > aDt[nLy]);

	nLm = 0;
	sLyoon = "0"; //현재월이 윤달임을 표시할 변수 - 기본값 평달
	do {
		if (parseInt(aBaseInfo[nLy * 12 + nLm]) <= 2) 
		{
			mm = parseInt(aBaseInfo[nLy * 12 + nLm]) + 28;
			if (td > mm) 
			{
				td = td - mm;
				nLm = nLm + 1;
			}
			else 
			{
				break;
			}
		}
		else 
		{
			switch (parseInt(aBaseInfo[nLy * 12 + nLm])) 
			{
				case 3:
					m1 = 29;
					m2 = 29;
					break;
				case 4:
					m1 = 29;
					m2 = 30;
					break;
				case 5:
					m1 = 30;
					m2 = 29;
					break;
				case 6:
					m1 = 30;
					m2 = 30;
					break;
			}

			if (td > m1) 
			{
				td = td - m1;
				if (td > m2) 
				{
					td = td - m2;
					nLm = nLm + 1;
				}
				else 
				{
					sLyoon = "1";
				}
			}
			else 
			{
				break;
			}
		}
	} while (1);

	nLy = nLy + 1841;
	nLm = nLm + 1;
	nLd = td;

	return sLyoon + nLy + gfn_right("0" + nLm, 2) + gfn_right("0" + nLd, 2);
}

 /**
 * @class 음력을 양력으로 변환해주는 함수 (처리가능 기간  1841 - 2043년)
 * @param sDate  - 일자(yyyyMMdd)
 * @return string yyyyMMdd형태의 양력일자
 */  
function gfn_lunar2Solar (sDate)
{
	var sMd = "31,0,31,30,31,30,31,31,30,31,30,31";
	var aMd = new Array();	
	var aBaseInfo = new Array();	
	
	var nLy, nLm, nLd, sLflag;		// 전해온 음력 인자값을 저장할 년, 월, 일, 윤달여부 임시변수
	var nSy, nSm, nSd;				// 계산된 양력 년, 월, 일을 저장할 변수
	var y1, m1, i, j, y2, y3;	// 임시변수	
	var leap;

	if( gfn_isNull(sDate) )			return "";
	if( sDate.length != 9 )		return "";
	
	sLflag = sDate.substr(0,1);
	nLy = parseInt(sDate.substr(1,4), 10);
	nLm = parseInt(sDate.substr(5,2), 10);
	nLd = parseInt(sDate.substr(7,2), 10);
	if( nLy < 1841 || nLy > 2043 )			return "";
	if( sLflag != "0" && sLflag != "1" )	return "";
		
	aBaseInfo = _solarBase();
	aMd = sMd.split(",");
	if( gfn_isLeapYear(sDate.substr(1,8)) == true )					
		aMd[1] = 29;
	else
		aMd[1] = 28;	
		
	y1 = nLy - 1841;
	m1 = nLm - 1;
	leap = 0;
	if( parseInt(aBaseInfo[y1*12 + m1]) > 2 )
		leap = gfn_isLeapYear(nLy+"0101");
	
	if( leap == 1 )
	{
		switch( parseInt(aBaseInfo[y1*12 + m1]) )
		{
			case 3 : mm = 29;
					break;
			case 4 : mm = 30;
					break;			
			case 5 : mm = 29;
					break;			
			case 6 : mm = 30;
					break;
		}
	}
	else
	{
		switch( parseInt(aBaseInfo[y1*12 + m1]) )
		{
			case 1 : mm = 29;
					break;			
			case 2 : mm = 30;
					break;			
			case 3 : mm = 29;
					break;			
			case 4 : mm = 29;
					break;			
			case 5 : mm = 30;
					break;			
			case 6 : mm = 30;
					break;			
		}
	}

	td = 0;
	for( i = 0 ; i <= y1 - 1 ; i++ )
	{
		for( j = 0 ; j <= 11 ; j++ )
		{
			switch( parseInt(aBaseInfo[i*12 + j]) )
			{
				case 1 : td = td + 29;
						break;
				case 2 : td = td + 30;
						break;				
				case 3 : td = td + 58;
						break;				
				case 4 : td = td + 59;
						break;				
				case 5 : td = td + 59;
						break;				
				case 6 : td = td + 60;
						break;				
			}
		}
	}

	for( j = 0 ; j <= m1 - 1 ; j++ )
	{
		switch( parseInt(aBaseInfo[y1*12 + j]) )
		{
			case 1 : td = td + 29;
					break;			
			case 2 : td = td + 30;
					break;						
			case 3 : td = td + 58;
					break;						
			case 4 : td = td + 59;
					break;						
			case 5 : td = td + 59;
					break;						
			case 6 : td = td + 60;
					break;						
		}
	}

	if( leap == 1 )
	{
		switch( parseInt(aBaseInfo[y1*12 + m1]) )
		{
			case 3 : mm = 29;
					break;						
			case 4 : mm = 29;
					break;						
			case 5 : mm = 30;
					break;						
			case 6 : mm = 30;
					break;						
		}
	}
	
	td = td + nLd + 22;
	
	if( sLflag == "1" )
	{
		switch( parseInt(aBaseInfo[y1*12 + m1]) )
		{
			case 3 : td = td + 29;
					break;						
			case 4 : td = td + 30;
					break;						
			case 5 : td = td + 29;
					break;						
			case 6 : td = td + 30;
					break;						
		}
	}
	
	y1 = 1840;
	do
	{
		y1 = y1 + 1;
		leap = gfn_isLeapYear(y1+"0101");

		if( leap == 1 )
			y2 = 366;
		else
			y2 = 365;

		if( td <= y2 )
			break;
			
		td = td - y2;
	}
	while(1);

	nSy = y1;
	aMd[1] = y2 - 337;
	m1 = 0;
	do
	{
		m1 = m1 + 1;
		if( td <= parseInt(aMd[m1-1]) )
			break;
		td = td - parseInt(aMd[m1-1]);
	}
	while(1);
	
	nSm = m1;
	nSd = td;
	y3 = nSy;
	td = y3 * 365 + parseInt(y3/4) - parseInt(y3/100) + parseInt(y3/400);
	for( i = 0 ; i <= nSm - 1 ; i++ )
		td = td + parseInt(aMd[i]);

	td = td + nSd;

	return y3 + gfn_right("0" + nSm, 2)+gfn_right("0" + nSd, 2);
}

 /**
 * @class 양력 nYear에 해당하는 년도의 법정 공휴일(양력) List 모두 구하기
 * @param nYear  - 년도
 * @return Array 공휴일 List Array ==> 각 Array[i]="yyyyMMdd공휴일명" 으로 return
 */  
function gfn_getHolidays (nYear)
{
	var nYear;
	var aHoliday = new Array();

	if (gfn_isNull(nYear)) 
	{
		return aHoliday;
	}

	// ///// 음력 체크
	// 구정
	aHoliday[0] = gfn_lunar2Solar("0" + (nYear - 1) + "1230") + "설날";
	aHoliday[1] = gfn_addDate(aHoliday[0], 1) + "설날";
	aHoliday[2] = gfn_addDate(aHoliday[1], 1) + "설날";
	// 석가탄신일
	aHoliday[3] = gfn_lunar2Solar("0" + nYear + "0408") + "석가탄신일";
	// 추석
	aHoliday[4] = gfn_lunar2Solar("0" + nYear + "0814") + "추석";
	aHoliday[5] = gfn_addDate(aHoliday[4], 1) + "추석";
	aHoliday[6] = gfn_addDate(aHoliday[5], 1) + "추석";

	// ///// 양력 체크
	aHoliday[7] = nYear + "0101" + "신정";
	aHoliday[8] = nYear + "0301" + "삼일절";
	aHoliday[9] = nYear + "0505" + "어린이날";
	aHoliday[10] = nYear + "0606" + "현충일";
	aHoliday[11] = nYear + "0815" + "광복절";
	aHoliday[12] = nYear + "1225" + "성탄절";

	return aHoliday.sort();
}


/*******************************************************************************
 * 함 수 명 	: gfn_validFromTo
 * 함수설명 	: From ~ To 형식의 날짜 형식의 유효성 검사를 해주는 함수
 * 입    력 	: sDate : 시작일(yyyymmdd)
				  eDate : 마지막일(yyyymmdd)
 * 결    과 	: boolean
******************************************************************************/
function gfn_validFromTo(sDate, eDate) 
{
    if(!gfn_isDate8(sDate) || !gfn_isDate8(eDate)){
		gfn_alert("msg.err.date.diff.valid");
		return false;
	}
	
	if(gfn_getDiffDay(sDate, eDate) < 0){
		gfn_alert("msg.err.validator.compdate", sDate+"^"+eDate);
		return false;
	}
	
	return true;
}


/*******************************************************************************
 * 함 수 명 	: gfn_validFromTo
 * 함수설명 	: From ~ To 형식의 날짜 형식의 유효성 검사를 해주는 함수
 * 입    력 	: sDate : 시작일(yyyymmdd)
				  eDate : 마지막일(yyyymmdd)
 * 결    과 	: boolean
******************************************************************************/
function gfn_validFromTo6(sDate, eDate) 
{
    if(!gfn_isDate6(sDate) || !gfn_isDate6(eDate)){
		gfn_alert("msg.err.date.diff.valid");
		return false;
	}
	
	if(Number(sDate) >  Number(eDate)){
		gfn_alert("msg.err.validator.compdate", sDate+"^"+eDate);
		return false;
	}
	
	return true;
}

/*******************************************************************************
 * 함 수 명 	: gfn_getOneMonthAfter
 * 함수설명 	: 한달후 날짜 구하는 함수.
 * 입    력 	: strDate(yyyyMMdd형태의 From 일자) ( 예 : "20121122" )
 * 결    과 	: string
******************************************************************************/
function gfn_getOneMonthAfter(strDate) 
{
    if (strDate) 
    {
        var date = gfn_strToDate(strDate);
        var d = (new Date(date)).addMonth(1);
    } else {
		var d = (new Date).addMonth(1);
    }

	var s = (new Date(d)).getFullYear()
		   + (((new Date(d)).getMonth() + 1) + "").padLeft(2, '0')
		   + (((new Date(d)).getDate()) + "").padLeft(2, '0');

	return (s);
}

 /**
 * @class 각 월별 음력 기준 정보를 처리하는 함수(처리가능 기간  1841 - 2043년) 단, 내부에서 사용하는 함수임
 * @return String 음력 기준정보
 */ 
function _solarBase ()
{
	var kk;

	// 1841
	kk = "1,2,4,1,1,2,1,2,1,2,2,1,";
	kk += "2,2,1,2,1,1,2,1,2,1,2,1,";
	kk += "2,2,2,1,2,1,4,1,2,1,2,1,";
	kk += "2,2,1,2,1,2,1,2,1,2,1,2,";
	kk += "1,2,1,2,2,1,2,1,2,1,2,1,";
	kk += "2,1,2,1,5,2,1,2,2,1,2,1,";
	kk += "2,1,1,2,1,2,1,2,2,2,1,2,";
	kk += "1,2,1,1,2,1,2,1,2,2,2,1,";
	kk += "2,1,2,3,2,1,2,1,2,1,2,2,";
	kk += "2,1,2,1,1,2,1,1,2,2,1,2,";
	// 1851
	kk += "2,2,1,2,1,1,2,1,2,1,5,2,";
	kk += "2,1,2,2,1,1,2,1,2,1,1,2,";
	kk += "2,1,2,2,1,2,1,2,1,2,1,2,";
	kk += "1,2,1,2,1,2,5,2,1,2,1,2,";
	kk += "1,1,2,1,2,2,1,2,2,1,2,1,";
	kk += "2,1,1,2,1,2,1,2,2,2,1,2,";
	kk += "1,2,1,1,5,2,1,2,1,2,2,2,";
	kk += "1,2,1,1,2,1,1,2,2,1,2,2,";
	kk += "2,1,2,1,1,2,1,1,2,1,2,2,";
	kk += "2,1,6,1,1,2,1,1,2,1,2,2,";
	// 1861
	kk += "1,2,2,1,2,1,2,1,2,1,1,2,";
	kk += "2,1,2,1,2,2,1,2,2,3,1,2,";
	kk += "1,2,2,1,2,1,2,2,1,2,1,2,";
	kk += "1,1,2,1,2,1,2,2,1,2,2,1,";
	kk += "2,1,1,2,4,1,2,2,1,2,2,1,";
	kk += "2,1,1,2,1,1,2,2,1,2,2,2,";
	kk += "1,2,1,1,2,1,1,2,1,2,2,2,";
	kk += "1,2,2,3,2,1,1,2,1,2,2,1,";
	kk += "2,2,2,1,1,2,1,1,2,1,2,1,";
	kk += "2,2,2,1,2,1,2,1,1,5,2,1,";
	// 1871
	kk += "2,2,1,2,2,1,2,1,2,1,1,2,";
	kk += "1,2,1,2,2,1,2,1,2,2,1,2,";
	kk += "1,1,2,1,2,4,2,1,2,2,1,2,";
	kk += "1,1,2,1,2,1,2,1,2,2,2,1,";
	kk += "2,1,1,2,1,1,2,1,2,2,2,1,";
	kk += "2,2,1,1,5,1,2,1,2,2,1,2,";
	kk += "2,2,1,1,2,1,1,2,1,2,1,2,";
	kk += "2,2,1,2,1,2,1,1,2,1,2,1,";
	kk += "2,2,4,2,1,2,1,1,2,1,2,1,";
	kk += "2,1,2,2,1,2,2,1,2,1,1,2,";
	// 1881
	kk += "1,2,1,2,1,2,5,2,2,1,2,1,";
	kk += "1,2,1,2,1,2,1,2,2,1,2,2,";
	kk += "1,1,2,1,1,2,1,2,2,2,1,2,";
	kk += "2,1,1,2,3,2,1,2,2,1,2,2,";
	kk += "2,1,1,2,1,1,2,1,2,1,2,2,";
	kk += "2,1,2,1,2,1,1,2,1,2,1,2,";
	kk += "2,2,1,5,2,1,1,2,1,2,1,2,";
	kk += "2,1,2,2,1,2,1,1,2,1,2,1,";
	kk += "2,1,2,2,1,2,1,2,1,2,1,2,";
	kk += "1,5,2,1,2,2,1,2,1,2,1,2,";
	// 1891
	kk += "1,2,1,2,1,2,1,2,2,1,2,2,";
	kk += "1,1,2,1,1,5,2,2,1,2,2,2,";
	kk += "1,1,2,1,1,2,1,2,1,2,2,2,";
	kk += "1,2,1,2,1,1,2,1,2,1,2,2,";
	kk += "2,1,2,1,5,1,2,1,2,1,2,1,";
	kk += "2,2,2,1,2,1,1,2,1,2,1,2,";
	kk += "1,2,2,1,2,1,2,1,2,1,2,1,";
	kk += "2,1,5,2,2,1,2,1,2,1,2,1,";
	kk += "2,1,2,1,2,1,2,2,1,2,1,2,";
	kk += "1,2,1,1,2,1,2,5,2,2,1,2,";
	// 1901
	kk += "1,2,1,1,2,1,2,1,2,2,2,1,";
	kk += "2,1,2,1,1,2,1,2,1,2,2,2,";
	kk += "1,2,1,2,3,2,1,1,2,2,1,2,";
	kk += "2,2,1,2,1,1,2,1,1,2,2,1,";
	kk += "2,2,1,2,2,1,1,2,1,2,1,2,";
	kk += "1,2,2,4,1,2,1,2,1,2,1,2,";
	kk += "1,2,1,2,1,2,2,1,2,1,2,1,";
	kk += "2,1,1,2,2,1,2,1,2,2,1,2,";
	kk += "1,5,1,2,1,2,1,2,2,2,1,2,";
	kk += "1,2,1,1,2,1,2,1,2,2,2,1,";
	// 1911
	kk += "2,1,2,1,1,5,1,2,2,1,2,2,";
	kk += "2,1,2,1,1,2,1,1,2,2,1,2,";
	kk += "2,2,1,2,1,1,2,1,1,2,1,2,";
	kk += "2,2,1,2,5,1,2,1,2,1,1,2,";
	kk += "2,1,2,2,1,2,1,2,1,2,1,2,";
	kk += "1,2,1,2,1,2,2,1,2,1,2,1,";
	kk += "2,3,2,1,2,2,1,2,2,1,2,1,";
	kk += "2,1,1,2,1,2,1,2,2,2,1,2,";
	kk += "1,2,1,1,2,1,5,2,2,1,2,2,";
	kk += "1,2,1,1,2,1,1,2,2,1,2,2,";
	// 1921
	kk += "2,1,2,1,1,2,1,1,2,1,2,2,";
	kk += "2,1,2,2,3,2,1,1,2,1,2,2,";
	kk += "1,2,2,1,2,1,2,1,2,1,1,2,";
	kk += "2,1,2,1,2,2,1,2,1,2,1,1,";
	kk += "2,1,2,5,2,1,2,2,1,2,1,2,";
	kk += "1,1,2,1,2,1,2,2,1,2,2,1,";
	kk += "2,1,1,2,1,2,1,2,2,1,2,2,";
	kk += "1,5,1,2,1,1,2,2,1,2,2,2,";
	kk += "1,2,1,1,2,1,1,2,1,2,2,2,";
	kk += "1,2,2,1,1,5,1,2,1,2,2,1,";
	// 1931
	kk += "2,2,2,1,1,2,1,1,2,1,2,1,";
	kk += "2,2,2,1,2,1,2,1,1,2,1,2,";
	kk += "1,2,2,1,6,1,2,1,2,1,1,2,";
	kk += "1,2,1,2,2,1,2,2,1,2,1,2,";
	kk += "1,1,2,1,2,1,2,2,1,2,2,1,";
	kk += "2,1,4,1,2,1,2,1,2,2,2,1,";
	kk += "2,1,1,2,1,1,2,1,2,2,2,1,";
	kk += "2,2,1,1,2,1,4,1,2,2,1,2,";
	kk += "2,2,1,1,2,1,1,2,1,2,1,2,";
	kk += "2,2,1,2,1,2,1,1,2,1,2,1,";
	// 1941
	kk += "2,2,1,2,2,4,1,1,2,1,2,1,";
	kk += "2,1,2,2,1,2,2,1,2,1,1,2,";
	kk += "1,2,1,2,1,2,2,1,2,2,1,2,";
	kk += "1,1,2,4,1,2,1,2,2,1,2,2,";
	kk += "1,1,2,1,1,2,1,2,2,2,1,2,";
	kk += "2,1,1,2,1,1,2,1,2,2,1,2,";
	kk += "2,5,1,2,1,1,2,1,2,1,2,2,";
	kk += "2,1,2,1,2,1,1,2,1,2,1,2,";
	kk += "2,2,1,2,1,2,3,2,1,2,1,2,";
	kk += "2,1,2,2,1,2,1,1,2,1,2,1,";
	// 1951
	kk += "2,1,2,2,1,2,1,2,1,2,1,2,";
	kk += "1,2,1,2,4,2,1,2,1,2,1,2,";
	kk += "1,2,1,1,2,2,1,2,2,1,2,2,";
	kk += "1,1,2,1,1,2,1,2,2,1,2,2,";
	kk += "2,1,4,1,1,2,1,2,1,2,2,2,";
	kk += "1,2,1,2,1,1,2,1,2,1,2,2,";
	kk += "2,1,2,1,2,1,1,5,2,1,2,2,";
	kk += "1,2,2,1,2,1,1,2,1,2,1,2,";
	kk += "1,2,2,1,2,1,2,1,2,1,2,1,";
	kk += "2,1,2,1,2,5,2,1,2,1,2,1,";
	// 1961
	kk += "2,1,2,1,2,1,2,2,1,2,1,2,";
	kk += "1,2,1,1,2,1,2,2,1,2,2,1,";
	kk += "2,1,2,3,2,1,2,1,2,2,2,1,";
	kk += "2,1,2,1,1,2,1,2,1,2,2,2,";
	kk += "1,2,1,2,1,1,2,1,1,2,2,1,";
	kk += "2,2,5,2,1,1,2,1,1,2,2,1,";
	kk += "2,2,1,2,2,1,1,2,1,2,1,2,";
	kk += "1,2,2,1,2,1,5,2,1,2,1,2,";
	kk += "1,2,1,2,1,2,2,1,2,1,2,1,";
	kk += "2,1,1,2,2,1,2,1,2,2,1,2,";
	// 1971
	kk += "1,2,1,1,5,2,1,2,2,2,1,2,";
	kk += "1,2,1,1,2,1,2,1,2,2,2,1,";
	kk += "2,1,2,1,1,2,1,1,2,2,2,1,";
	kk += "2,2,1,5,1,2,1,1,2,2,1,2,";
	kk += "2,2,1,2,1,1,2,1,1,2,1,2,";
	kk += "2,2,1,2,1,2,1,5,2,1,1,2,";
	kk += "2,1,2,2,1,2,1,2,1,2,1,1,";
	kk += "2,2,1,2,1,2,2,1,2,1,2,1,";
	kk += "2,1,1,2,1,6,1,2,2,1,2,1,";
	kk += "2,1,1,2,1,2,1,2,2,1,2,2,";
	// 1981
	kk += "1,2,1,1,2,1,1,2,2,1,2,2,";
	kk += "2,1,2,3,2,1,1,2,2,1,2,2,";
	kk += "2,1,2,1,1,2,1,1,2,1,2,2,";
	kk += "2,1,2,2,1,1,2,1,1,5,2,2,";
	kk += "1,2,2,1,2,1,2,1,1,2,1,2,";
	kk += "1,2,2,1,2,2,1,2,1,2,1,1,";
	kk += "2,1,2,2,1,5,2,2,1,2,1,2,";
	kk += "1,1,2,1,2,1,2,2,1,2,2,1,";
	kk += "2,1,1,2,1,2,1,2,2,1,2,2,";
	kk += "1,2,1,1,5,1,2,1,2,2,2,2,";
	// 1991
	kk += "1,2,1,1,2,1,1,2,1,2,2,2,";
	kk += "1,2,2,1,1,2,1,1,2,1,2,2,";
	kk += "1,2,5,2,1,2,1,1,2,1,2,1,";
	kk += "2,2,2,1,2,1,2,1,1,2,1,2,";
	kk += "1,2,2,1,2,2,1,5,2,1,1,2,";
	kk += "1,2,1,2,2,1,2,1,2,2,1,2,";
	kk += "1,1,2,1,2,1,2,2,1,2,2,1,";
	kk += "2,1,1,2,3,2,2,1,2,2,2,1,";
	kk += "2,1,1,2,1,1,2,1,2,2,2,1,";
	kk += "2,2,1,1,2,1,1,2,1,2,2,1,";
	// 2001
	kk += "2,2,2,3,2,1,1,2,1,2,1,2,";
	kk += "2,2,1,2,1,2,1,1,2,1,2,1,";
	kk += "2,2,1,2,2,1,2,1,1,2,1,2,";
	kk += "1,5,2,2,1,2,1,2,2,1,1,2,";
	kk += "1,2,1,2,1,2,2,1,2,2,1,2,";
	kk += "1,1,2,1,2,1,5,2,2,1,2,2,";
	kk += "1,1,2,1,1,2,1,2,2,2,1,2,";
	kk += "2,1,1,2,1,1,2,1,2,2,1,2,";
	kk += "2,2,1,1,5,1,2,1,2,1,2,2,";
	kk += "2,1,2,1,2,1,1,2,1,2,1,2,";
	// 2011
	kk += "2,1,2,2,1,2,1,1,2,1,2,1,";
	kk += "2,1,6,2,1,2,1,1,2,1,2,1,";
	kk += "2,1,2,2,1,2,1,2,1,2,1,2,";
	kk += "1,2,1,2,1,2,1,2,5,2,1,2,";
	kk += "1,2,1,1,2,1,2,2,2,1,2,2,";
	kk += "1,1,2,1,1,2,1,2,2,1,2,2,";
	kk += "2,1,1,2,3,2,1,2,1,2,2,2,";
	kk += "1,2,1,2,1,1,2,1,2,1,2,2,";
	kk += "2,1,2,1,2,1,1,2,1,2,1,2,";
	kk += "2,1,2,5,2,1,1,2,1,2,1,2,";
	// 2021
	kk += "1,2,2,1,2,1,2,1,2,1,2,1,";
	kk += "2,1,2,1,2,2,1,2,1,2,1,2,";
	kk += "1,5,2,1,2,1,2,2,1,2,1,2,";
	kk += "1,2,1,1,2,1,2,2,1,2,2,1,";
	kk += "2,1,2,1,1,5,2,1,2,2,2,1,";
	kk += "2,1,2,1,1,2,1,2,1,2,2,2,";
	kk += "1,2,1,2,1,1,2,1,1,2,2,2,";
	kk += "1,2,2,1,5,1,2,1,1,2,2,1,";
	kk += "2,2,1,2,2,1,1,2,1,1,2,2,";
	kk += "1,2,1,2,2,1,2,1,2,1,2,1,";
	// 2031
	kk += "2,1,5,2,1,2,2,1,2,1,2,1,";
	kk += "2,1,1,2,1,2,2,1,2,2,1,2,";
	kk += "1,2,1,1,2,1,5,2,2,2,1,2,";
	kk += "1,2,1,1,2,1,2,1,2,2,2,1,";
	kk += "2,1,2,1,1,2,1,1,2,2,1,2,";
	kk += "2,2,1,2,1,4,1,1,2,1,2,2,";
	kk += "2,2,1,2,1,1,2,1,1,2,1,2,";
	kk += "2,2,1,2,1,2,1,2,1,1,2,1,";
	kk += "2,2,1,2,5,2,1,2,1,2,1,1,";
	kk += "2,1,2,2,1,2,2,1,2,1,2,1,";
	// 2041
	kk += "2,1,1,2,1,2,2,1,2,2,1,2,";
	kk += "1,5,1,2,1,2,1,2,2,2,1,2,";
	kk += "1,2,1,1,2,1,1,2,2,1,2,2";

	var arr = new Array();
	arr = kk.split(",");

	return arr;
}

function gfn_getDate(element){
	var date;
	try {
		date = $.datepicker.parseDate( "yy-mmd-dd", element.value );
	} catch( error ) {
		alert("error : " +error);
		date = null;
	}
alert("18 : " + date);
	return date;
}
