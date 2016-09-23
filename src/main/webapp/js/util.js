/*
 ===============================================================================
 == String관련 공통 함수
 ===============================================================================
 ● gfn_getTextSize 	: TextSize 생성 반환
 ● gfn_subStrByte 		: 바이트 단위의 문자열 얻기
 ● gfn_isEmail 		: 입력값이 e-mail Type인지 체크하는 함수
 */


 /**
 * @class TextSize 생성 반환
 * @param sText - 문자열
 * @param objFont - Font Object
 * @param iLimitWidth - Font width 값
 * @param sCommand -
 * @return boolean
 */
function gfn_getTextSize(sText, objFont, iLimitWidth, sCommand)
{
	var objPainter = this.canvas.getPainter();
	if(gfn_isNull(objPainter)==false)
	{
		var objTextSize = objPainter.getTextSize(sText, objFont);
		return objTextSize;
	}
	else
	{
		return false;
	}
}


 /**
 * @class 바이트 단위의 문자열 얻기
 * @param sVal - 원본 문자열
 * @param nStartByte - 시작 바이트
 * @param nEndByte - 종료 바이트
 * @return string
 */
function gfn_subStrByte(sVal, nStartByte, nEndByte)
{
    var sOrgStr = sVal.toString();
    var nLen = -1;
    var sRtnStr = "";

    for (var i=0; i<sOrgStr.length; i++) {
		if (sOrgStr.charCodeAt(i) > 127) {
			nLen += 2;
		} else {
			nLen += 1;
		}
		if (nLen >= nStartByte && nLen <= nEndByte) {
			sRtnStr += String.fromCharCode(sOrgStr.charCodeAt(i));
		}
    }

    return sRtnStr;
}


 /**
 * @class 입력값이 e-mail Type인지 체크하는 함수
 * @param sValue - 값
 * @return boolean
 */
function gfn_isEmail (sValue)
{
	if(!sValue) return false;

	var email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
	if(!email_regex.test(sValue)){
		return false;
	}else{
		return true;
	}
}

