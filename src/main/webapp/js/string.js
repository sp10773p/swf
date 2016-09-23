/*
 ===============================================================================
 == String관련 공통 함수
 ===============================================================================
 ● gfn_isNull       : 입력값이 null에 해당하는 경우 모두를 한번에 체크한다.
 ● gfn_nvl          : 입력값이 존재하는지를 판단하여
                       존재하는 경우 입력값을 그대로 반환, 그렇지 않으면
                       두 번째 파라미터를 반환하는 함수
 ● gfn_quote        : 입력된 문자열의 양쪽에 쌍따옴표를 붙여 반환
 ● gfn_toString     : 입력값을 String으로 변경한다.
 ● gfn_subStr       : 지정한 위치에서 시작하고 지정한 길이를 갖는 부분 문자열을 반환하는 함수
 ● gfn_length       : 입력값 형태에 따라서 길이 또는 범위를 구하는 함수
 ● gfn_lengthByte   : 문자 전체 길이를 계산 - 문자, 숫자, 특수문자 : 1 로 Count
 ● gfn_lengthUTF8   : 문자 전체 길이를 계산 - UTF-8
 ● gfn_right        : 문자열의 오른쪽부분을 지정한 길이만큼 Return 한다.
 ● gfn_left         : 문자열의 왼쪽부분을 지정한 길이만큼 Return 한다.
 ● gfn_replace      : 입력된 문자열의 일부분을 다른 문자열로 치환하는 함수
 ● gfn_replaceCase  : 문자열을 대소문자 구별없이 치환한다
 ● gfn_numFormat    : 입력된 실수를 문자열 표현법으로 표현하는 함수
 ● gfn_setComma     : 숫자에 ","를 집어넣기
 ● gfn_getFormat    : 문자 포맷 형식 변환 ( 해당 문자는 포맷에서 @ 사용)
 ● gfn_split        : 특정 문자열을 기준으로 전체 문자열을 나누어서 하나의 배열(Array)로 만들어 반납하는 함수
 ● gfn_split2       : 입력된 문자열을 strDelimiter1과 strDelimiter2로 2 번 Parsing 한 2차원 배열을 Return
 ● gfn_indexOf      : 전체 문자열 중 특정 문자열이 포함된 위치를 반납
 ● gfn_pos          : 문자열의 위치를 대소문자 구별하여 찾는다
 ● gfn_posCase      : 문자열의 위치를 대소문자 구별없이 찾는다
 ● gfn_posReverse   : 문자열의 위치를 대소문자 구별하여 거꾸로 찾는다
 ● gfn_mid          : 시작글자와 끝글자에 해당하는 글자의 사이에 있는 가운데 글자를 대소문자를 구별하여 찾는다.
 ● gfn_midCase      : 시작글자와 끝글자에 해당하는 글자의 사이에 있는 가운데 글자를 대소문자를 구별없이 찾는다.
 ● gfn_midArray     :  gfn_mid 함수와 동일하나 Return을 Array로 한다.
 ● gfn_midCaseArray : gfn_midCase 함수와 동일하나 Return을 Array로 한다.
 ● gfn_trim         : 대소문자 구별하여 왼쪽, 오른쪽 문자열 삭제
 ● gfn_lTrim        : 대소문자 구별하여 왼쪽에서 문자열 삭제.
 ● gfn_lTrimCase    : 대소문자 구별없이 왼쪽에서 문자열 삭제.
 ● gfn_rTrim        : 소문자 구별하여 오른쪽에서 문자열 삭제.
 ● gfn_rTrimCase    : 대소문자 구별없이 오른쪽에서 문자열 삭제.
 ● gfn_lPad         : 자리수 만큼 왼쪽에 문자열 추가.
 ● gfn_rPad         : 자리수 만큼 오른쪽에 문자열 추가.
 ● gfn_strCount     : 대소문자 구별하여 문자개수 세기
 ● gfn_strCountCase : 대소문자 구분없이 문자개수 세기
 ● gfn_htmlToChars  : html형식의 문자열에서 태그문자를 특수문자로 변형
 ● gfn_specToChars  : 특수문자가 들어있는 문자열에서 html형식의 문자로 변형
 ● gfn_setArg       : Argument 문자열을 반환 예) key1, value1, key2, value2 ==> key1="value1" key2="value2"
 */

 /**
 * @class 입력값이 null에 해당하는 경우 모두를 한번에 체크한다.
 * @param sValue - 체크할 문자열( 예 : null 또는 undefined 또는 "" 또는 "abc" )
 * @return Boolean sValue이 undefined, null, NaN, "", Array.length = 0인 경우 true
 */
function gfn_isNull (sValue)
{
    if (new String(sValue).valueOf() == "undefined") return true;
    if (sValue == null) return true;
    var v_ChkStr = new String(sValue);

    if (v_ChkStr == null) return true;
    if (v_ChkStr.toString().length == 0 ) return true;

    return false;
}


 /**
 * @class 입력값이 존재하는지를 판단하여
          존재하는 경우 입력값을 그대로 반환, 그렇지 않으면 두 번째 파라미터를 반환하는 함수
 * @param Val - 입력값
 * @param newVal - Null일 경우 대체할 값
 * @return Null을 대치한 값
 */
function gfn_nvl (Val, newVal)
{
    if (gfn_isNull(Val))
    {
        if (gfn_isNull(newVal))
        {
            return "";
        }else{
            return newVal;
        }
    }

/*
	if (gfn_isNull(newVal))
	{
		return "";
	}
*/
	return Val;
}

 /**
 * @class 입력된 문자열의 양쪽에 쌍따옴표를 붙여 반환
 * @param Val - 문자열
 * @return String 양쪽에 쌍따옴표를 붙인 문자열
 */
function gfn_quote ()
{
	var retVal = '""';
	var arrArgument = gfn_quote.arguments;

	if ((arrArgument != null) && (arrArgument.length >= 1) && (!gfn_isNull(arrArgument[0]))) {
		retVal = wrapQuote(new String(arrArgument[0]));
	}

	return retVal;
}

 /**
 * @class 입력값을 String으로 변경
 * @param Val - 문자열
 * @return String 문자열
 */
function gfn_toString (Val)
{
	if (gfn_isNull(Val))
	{
		return new String();
	}
	return new String(Val);
}

 /**
 * @class 지정한 위치에서 시작하고 지정한 길이를 갖는 부분 문자열을 반환하는 함수
 * @param strString - 가운데 부문을 얻어올 원본 문자열
 * @param nIndex    - 얻어올 첫 글자의 Index
 * @param nSize     - Integer 얻어올 글자수 [Default length(해당 개채의 길이)]
 * @return String 문자열
 */
function gfn_subStr ()
{
	var retVal = "";
	var strString = "";
	var nIndex = 0;
	var nSize = 0;
	var arrArgument = gfn_subStr.arguments;

	if (arrArgument.length >= 1)
	{
		strString = arrArgument[0];
	}
	if (arrArgument.length >= 2)
	{
		nIndex = parseInt(arrArgument[1], 10);
	}
	if (arrArgument.length >= 3)
	{
		nSize = parseInt(arrArgument[2], 10);
	}
	else
	{
		nSize = gfn_length(arrArgument[0]);
	}

	if (!gfn_isNull(strString))
	{
		retVal = strString.substr(nIndex, nSize);
	}

	return retVal;
}

 /**
 * @class 입력값 형태에 따라서 길이 또는 범위를 구하는 함수
 * @param Val - 문자열
 * @return Integer Type에 따라 구해진 길이
 */
function gfn_length (Val)
{
	return gfn_toString(Val).length;
}

 /**
 * @class 문자 전체 길이를 계산
          - 문자, 숫자, 특수문자 : 1 로 Count
          - 그외 한글/한자 : 2 로 count 되어 합산
 * @param sVal - 문자열
 * @return Integer Type에 따라 구해진 길이
 */
function gfn_lengthByte (sVal)
{
	var lengthByte = 0;

	if (gfn_isNull(sVal))
	{
		return -1;
	}

	for (var i = 0; i < sVal.length; i++)
	{
		if (sVal.charCodeAt(i) > 127)
		{
			lengthByte += 2;
		}
		else
		{
			lengthByte += 1;
		}
	}

	return lengthByte;
}

function gfn_lengthUTF8(s)
{
    var utf8length = 0;
    for (var n = 0; n < s.length; n++) {
        var c = s.charCodeAt(n);
        if (c < 128) {
            utf8length++;
        } else if((c > 127) && (c < 2048)) {
            utf8length = utf8length+2;
        } else {
            utf8length = utf8length+3;
        }
    }
    return utf8length;
}

 /**
 * @class 문자열의 오른쪽부분을 지정한 길이만큼 Return
 * @param Val - 오른 부분을 얻어올 원본 문자열
 * @param nSize - 얻어올 크기. [Default Value = 0]
 * @return String 문자열
 */
function gfn_right (Val, nSize)
{
	var nStart = gfn_toString(Val).length;
	var nEnd = Number(nStart) - Number(nSize);
	var rtnVal = Val.substring(nStart, nEnd);

	return rtnVal;
}

 /**
 * @class 문자열의 왼쪽부분을 지정한 길이만큼 Return
 * @param Val - 왼쪽 부분을 얻어올 원본 문자열
 * @param nSize - 얻어올 크기. [Default Value = 0]
 * @return String 문자열
 */
function gfn_left (Val, nSize)
{
	return gfn_toString(Val).substr(0, nSize);
}

 /**
 * @class 입력된 문자열의 일부분을 다른 문자열로 치환하는 함수
 * @param Val - 원본 문자열
 * @param strOld - 원본 문자열에서 찾을 문자열
 * @param strNew - 새로 바꿀 문자열
 * @return String 문자열
 */
function gfn_replace (Val, strOld, strNew)
{
	var varRtnValue = "";
	var arrArgument = gfn_replace.arguments;

	if (arrArgument.length < 3) {
		varRtnValue = arrArgument[0];
	} else {
		if (gfn_isNull(arrArgument[0])) {
			return varRtnValue;
		}
		varRtnValue = arrArgument[0].toString().replace(arrArgument[1], arrArgument[2]);
	}

	return varRtnValue;
}

 /**
 * @class 문자열을 대소문자 구별없이 치환
 * @param sOrg - 원래문자열
 * @param sRepFrom - 치환할 문자열
 * @param sRepTo - 치환될 문자열
 * @return String 문자열
 */
function gfn_replaceCase (sOrg, sRepFrom, sRepTo)
{
	var pos,nStart = 0,sRet = "";

	if (gfn_isNull(sOrg))
	{
		return "";
	}
	if (gfn_isNull(sRepFrom))
	{
		return sOrg;
	}
	if (gfn_isNull(sRepTo))
	{
		return sOrg;
	}

	while (1)
	{
		pos = gfn_posCase(sOrg, sRepFrom, nStart);
		if (pos < 0)
		{
			sRet += sOrg.substr(nStart);
			break;
		}
		else
		{
			sRet += sOrg.substr(nStart, pos - nStart);
			sRet += sRepTo;
			nStart = pos + sRepFrom.length;
		}
	}
	return sRet;
}

 /**
 * @class 입력된 문자를 정부부분에 "," 를 집어넣기
 * @param strNumber - 문자열로 출력할 문자
 * @return String 정수부분에 3자리마다 ',' 가 삽입된 문자열
 */
function gfn_setComma (strNumber)
{
	var strNumber = strNumber.toString();
	var grouping = "\\3";
	var thousands_sep = ",";
	if( thousands_sep.length > 0 )
	{
		var	dec_buf = strNumber.split(""),
			dec_size = strNumber.length,
			out_size = (thousands_sep.length + 1) * strNumber.length,
			out_buf = [],
			cur_group = 0, d_size = dec_size,
			endpos = out_size,
			groupingIdx = 0,
			g;

		grouping = grouping.split("");

		while (grouping[groupingIdx] && d_size > 0 )
		{
			g = grouping[groupingIdx];
			if ( g == "\\" )
			{
				groupingIdx++;
				g = parseInt(grouping[groupingIdx]);
			}
			if (g > 0 )
			{
				cur_group = g;
				while (g-- > 0 && d_size > 0)
					out_buf[--endpos] = dec_buf[--d_size];
				if (d_size > 0)
					out_buf[--endpos] = thousands_sep;
			}
			else if (g == 0 && d_size > cur_group)
			{
				g = cur_group;
				while (g-- > 0)
					out_buf[--endpos] = dec_buf[--d_size];
				if (d_size > 0)
					out_buf[--endpos] = thousands_sep;
			}
			else if (g == 0 && d_size <= cur_group && d_size > 0 )
			{
				g = d_size;
				while (g-- > 0)
					out_buf[--endpos] = dec_buf[--d_size];
			}
			else
			{
				break;
			}
		}
		return out_buf.slice(endpos,out_size + endpos).join("");
	}
	return strNumber;
}

 /**
 * @class 문자 포맷 형식 변환 ( 해당 문자는 포맷에서 @ 사용)
 * @param Val - 문자열
 * @param strMask - 문자열 포맷, (@:문자, 포맷스트링("-", ",", ".")등
 * @return String 포맷된 문자
 */
function gfn_getFormat (Val, strMask)
{
	var strRetVal = "";
	var sUnit;

	if (gfn_isNull(Val))
	{
		return "";
	}

	Val = gfn_toString(Val);

	for (var i = 0; i < strMask.length; i++)
	{
		sUnit = strMask.substr(i, 1);

		if (sUnit == "@")
		{
			strRetVal += Val.substr(0, 1);
			Val = Val.substr(1);
		}
		else
		{
			strRetVal += sUnit;
		}
	}
	return strRetVal;
}

 /**
 * @class 특정 문자열을 기준으로 전체 문자열을 나누어서 하나의 배열(Array)로 만들어 반납하는 함수
 * @param strString - 원본 문자열
 * @param strChar - 나눌 기준이 되는 문자
 * @return Array 1 차원 배열
 */
function gfn_split()
{
	var rtnArr = new Array();
	var arrArgument = gfn_split.arguments;

	if (arrArgument.length < 1) {
	} else if (arrArgument.length < 2) {
		if (!gfn_isNull(arrArgument[0])) {
			rtnArr[0] = arrArgument[0];
		}
	} else if (arrArgument.length == 2) {
		if (!gfn_isNull(arrArgument[0])) {
			rtnArr = arrArgument[0].toString().split(arrArgument[1]);
		}
	}

	return rtnArr;
}

 /**
 * @class 입력된 문자열을 strDelimiter1과 strDelimiter2로 2 번 Parsing 한 2차원 배열을 Return
 * @param Val - 원본 문자열
 * @param strDelimiter1 - 첫번째로 잘라낼 구분 문자열
 * @param strDelimiter2 - 두번째로 잘라낼 구분 문자열
 * @return Array 2 차원 배열
 */
function gfn_split2 (Val, strDelimiter1, strDelimiter2)
{
	var split2Arr = new Array();

	Val = gfn_toString(Val);

	var splitArr = Val.split(strDelimiter1);
	for (var i = 0; i < splitArr.length; i++)
	{
		split2Arr.getSetter(i).set(splitArr[i].split(strDelimiter2));
	}
	return split2Arr;
}

 /**
 * @class 전체 문자열 중 특정 문자열이 포함된 위치를 반납
 * @param Val    - 원본 문자열
 * @param strOld - 검사할 문자열
 * @param index  - 시작순서
 * @return Integer 문자열이 포함된 위치의 index값
 */
function gfn_indexOf (Val, strOld, index)
{
	if (gfn_isNull(index))
	{
		index = 0;
	}
	return gfn_toString(Val).indexOf(strOld, index);
}

 /**
 * @class 문자열의 위치를 대소문자 구별하여 찾는다
 * @param sOrg   - 원래 문자열
 * @param sFind  - 찾고자 하는 문자열
 * @param nStart - 검색 시작위치 (옵션 : Default=0)
 * @return Integer 찾고자 하는 문자열의 시작위치
 */
function gfn_pos (sOrg, sFind, nStart)
{
	if (gfn_isNull(sOrg) || gfn_isNull(sFind))
	{
		return -1;
	}
	if (gfn_isNull(nStart))
	{
		nStart = 0;
	}

	return sOrg.indexOf(sFind, nStart);
}

 /**
 * @class 문자열의 위치를 대소문자 구별없이 찾는다
 * @param sOrg   - 원래 문자열
 * @param sFind  - 찾고자 하는 문자열
 * @param nStart - 검색 시작위치 (옵션 : Default=0)
 * @return Integer 찾고자 하는 문자열의 시작위치
 */
function gfn_posCase (sOrg, sFind, nStart)
{
	if (gfn_isNull(sOrg) || gfn_isNull(sFind))
	{
		return -1;
	}
	if (gfn_isNull(nStart))
	{
		nStart = 0;
	}

	return sOrg.toLowerCase().indexOf(sFind.toLowerCase(), nStart);
}

 /**
 * @class 문자열의 위치를 대소문자 구별하여 거꾸로 찾는다
 * @param sOrg   - 원래 문자열
 * @param sFind  - 찾고자 하는 문자열( 예 : "bb" )
 * @param nStart - 검색 시작위치 (옵션 : Default=문자열의 끝 )
 * @return Integer 찾고자 하는 문자열의 시작위치
 */
function gfn_posReverse (sOrg, sFind, nStart)
{
	var pos;

	if (gfn_isNull(sOrg) || gfn_isNull(sFind))
	{
		return -1;
	}
	if (gfn_isNull(nStart))
	{
		nStart = sOrg.length - 1;
	}

	for (pos = nStart; pos >= 0; pos--)
	{
		if (sOrg.substr(pos, sFind.length) == sFind)
		{
			break;
		}
	}

	return pos;
}

 /**
 * @class 시작글자와 끝글자에 해당하는 글자의 사이에 있는 가운데 글자를 대소문자를 구별하여 찾는다.
          ( 예 : aaBBbbccdd에서 bb, dd사이의 글자 cc를 찾는다 )
 * @param sOrg   - 원래 문자열
 * @param sStart - 찾고자 하는 시작 문자열(옵션 : Default = "")
 * @param sEnd   - 찾고자 하는 끝 문자열 (옵션 : Default = "")
 * @param nStart - 검색 시작위치 (옵션 : Default=0)
 * @return String 가운데 글자
 */
function gfn_mid (sOrg, sStart, sEnd, nStart)
{
	var pos_start,pos_end,ret_str;

	if (gfn_isNull(sOrg))
	{
		return "";
	}
	if (gfn_isNull(sStart))
	{
		sStart = "";
	}
	if (gfn_isNull(sEnd))
	{
		sEnd = "";
	}
	if (gfn_isNull(nStart))
	{
		nStart = 0;
	}

	if (sStart == "")
	{
		pos_start = nStart;
	}
	else
	{
		pos_start = gfn_pos(sOrg, sStart, nStart);
		if (pos_start < 0)
		{
			return "";
		}
	}
	if (sEnd == "")
	{
		pos_end = sOrg.length;
	}
	else
	{
		pos_end = gfn_pos(sOrg, sEnd, pos_start + sStart.length, nStart);
		if (pos_end < 0)
		{
			return "";
		}
	}

	return sOrg.substring(pos_start + sStart.length, pos_end);
}

 /**
 * @class 시작글자와 끝글자에 해당하는 글자의 사이에 있는 가운데 글자를 대소문자 구별없이 찾는다.
          ( 예 : aaBBbbccdd에서 bb, dd사이의 글자 cc를 찾는다 )
 * @param sOrg   - 원래 문자열
 * @param sStart - 찾고자 하는 시작 문자열(옵션 : Default = "")
 * @param sEnd   - 찾고자 하는 끝 문자열 (옵션 : Default = "")
 * @param nStart - 검색 시작위치 (옵션 : Default=0)
 * @return String 가운데 글자
 */
function gfn_midCase (sOrg, sStart, sEnd, nStart)
{
	var pos_start,pos_end,ret_str;

	if (gfn_isNull(sOrg))
	{
		return "";
	}
	if (gfn_isNull(sStart))
	{
		sStart = "";
	}
	if (gfn_isNull(sEnd))
	{
		sEnd = "";
	}
	if (gfn_isNull(nStart))
	{
		nStart = 0;
	}

	if (sStart == "")
	{
		pos_start = nStart;
	}
	else
	{
		pos_start = gfn_posCase(sOrg, sStart, nStart);
		if (pos_start < 0)
		{
			return "";
		}
	}
	if (sEnd == "")
	{
		pos_end = sOrg.length;
	}
	else
	{
		pos_end = gfn_posCase(sOrg, sEnd, pos_start + sStart.length, nStart);
		if (pos_end < 0)
		{
			return "";
		}
	}

	return sOrg.substring(pos_start + sStart.length, pos_end);
}

 /**
 * @class 시작글자와 끝글자에 해당하는 글자의 사이에 있는 가운데 글자를 대소문자를 구별하여 찾는다.
           gfn_mid()함수와 동일하나 Return을 Array로 한다.
 * @param sOrg   - 원래 문자열
 * @param sStart - 찾고자 하는 시작 문자열(옵션 : Default = "")
 * @param sEnd   - 찾고자 하는 끝 문자열 (옵션 : Default = "")
 * @param nStart - 검색 시작위치 (옵션 : Default=0)
 * @return Array  Array[0] = 가운데 글자
                  Array[1] = sStart의 위치
                  Array[2] = sEnd의 위치
 */
function gfn_midArray (sOrg, sStart, sEnd, nStart)
{
	var pos_start,pos_end,ret_str;
	var arr = new Array("", -1, -1);

	if (gfn_isNull(sOrg))
	{
		return "";
	}
	if (gfn_isNull(sStart))
	{
		sStart = "";
	}
	if (gfn_isNull(sEnd))
	{
		sEnd = "";
	}
	if (gfn_isNull(nStart))
	{
		nStart = 0;
	}

	if (sStart == "")
	{
		pos_start = nStart;
	}
	else
	{
		pos_start = gfn_pos(sOrg, sStart, nStart);
		if (pos_start < 0)
		{
			return arr;
		}
	}
	if (sEnd == "")
	{
		pos_end = sOrg.length;
	}
	else
	{
		pos_end = gfn_pos(sOrg, sEnd, pos_start + sStart.length, nStart);
		if (pos_end < 0)
		{
			return arr;
		}
	}

	arr[0] = sOrg.substring(pos_start + sStart.length, pos_end);
	arr[1] = pos_start;
	arr[2] = pos_end;
	return arr;
}

 /**
 * @class 시작글자와 끝글자에 해당하는 글자의 사이에 있는 가운데 글자를 대소문자를 구별없이 찾는다.
           gfn_midCase()함수와 동일하나 Return을 Array로 한다.
 * @param sOrg   - 원래 문자열
 * @param sStart - 찾고자 하는 시작 문자열(옵션 : Default = "")
 * @param sEnd   - 찾고자 하는 끝 문자열 (옵션 : Default = "")
 * @param nStart - 검색 시작위치 (옵션 : Default=0)
 * @return Array  Array[0] = 가운데 글자
                  Array[1] = sStart의 위치
                  Array[2] = sEnd의 위치
 */
function gfn_midCaseArray (sOrg, sStart, sEnd, nStart)
{
	var pos_start,pos_end,ret_str;
	var arr = new Array("", -1, -1);

	if (gfn_isNull(sOrg))
	{
		return "";
	}
	if (gfn_isNull(sStart))
	{
		sStart = "";
	}
	if (gfn_isNull(sEnd))
	{
		sEnd = "";
	}
	if (gfn_isNull(nStart))
	{
		nStart = 0;
	}

	if (sStart == "")
	{
		pos_start = nStart;
	}
	else
	{
		pos_start = gfn_posCase(sOrg, sStart, nStart);
		if (pos_start < 0)
		{
			return arr;
		}
	}
	if (sEnd == "")
	{
		pos_end = sOrg.length;
	}
	else
	{
		pos_end = gfn_posCase(sOrg, sEnd, pos_start + sStart.length, nStart);
		if (pos_end < 0)
		{
			return arr;
		}
	}

	arr[0] = sOrg.substring(pos_start + sStart.length, pos_end);
	arr[1] = pos_start;
	arr[2] = pos_end;
	return arr;
}

 /**
 * @class 대소문자 구별하여 왼쪽, 오른쪽 문자열 삭제
 * @param sOrg - 원래 문자열
 * @param sTrim - Trim할 문자열(옵션 : Default=" ")
 * @return String Trim된 문자열
 */
function gfn_trim (sOrg, sTrim)
{
    var vTrim = sTrim;
	var rtnVal = "";
	if (gfn_isNull(vTrim))
	{
		vTrim = " ";
	}
	rtnVal = gfn_rTrim(sOrg, vTrim);
	rtnVal = gfn_lTrim(rtnVal, vTrim);
	
	return rtnVal;
}

 /**
 * @class 대소문자 구별하여 왼쪽에서 문자열 삭제
 * @param sOrg - 원래 문자열
 * @param sTrim - Trim할 문자열(옵션 : Default=" ")
 * @return String Trim된 문자열
 */
function gfn_lTrim (sOrg, sTrim)
{
	var chk,pos;

	sOrg = gfn_toString(sOrg);

	if (gfn_isNull(sOrg))
	{
		return "";
	}
	if (gfn_isNull(sTrim))
	{
		sTrim = " ";
	}

	for (pos = 0; pos < sOrg.length; pos += sTrim.length)
	{
		if (sOrg.substr(pos, sTrim.length) != sTrim)
		{
			break;
		}
	}

	return sOrg.substr(pos);
}

 /**
 * @class 대소문자 구별없이 왼쪽에서 문자열 삭제
 * @param sOrg - 원래 문자열
 * @param sTrim - Trim할 문자열(옵션 : Default=" ")
 * @return String Trim된 문자열
 */
function gfn_lTrimCase (sOrg, sTrim)
{
	var pos;

	if (gfn_isNull(sOrg))
	{
		return "";
	}
	if (gfn_isNull(sTrim))
	{
		sTrim = " ";
	}

	for (pos = 0; pos < sOrg.length; pos += sTrim.length)
	{
		if (sOrg.toLowerCase().substr(pos, sTrim.length) != sTrim.toLowerCase())
		{
			break;
		}
	}

	return sOrg.substr(pos);
}

 /**
 * @class 대소문자 구별하여 오른쪽에서 문자열 삭제
 * @param sOrg - 원래 문자열
 * @param sTrim - Trim할 문자열(옵션 : Default=" ")
 * @return String Trim된 문자열
 */
function gfn_rTrim (sOrg, sTrim)
{
	var pos,nStart;

	sOrg = gfn_toString(sOrg);

	if (gfn_isNull(sOrg))
	{
		return "";
	}
	if (gfn_isNull(sTrim))
	{
		sTrim = " ";
	}

	for (pos = sOrg.length - sTrim.length; pos >= 0; pos -= sTrim.length)
	{
		if (sOrg.substr(pos, sTrim.length) != sTrim)
		{
			break;
		}
	}

	return sOrg.substr(0, pos + sTrim.length);
}

 /**
 * @class 대소문자 구별없이 오른쪽에서 문자열 삭제
 * @param sOrg - 원래 문자열
 * @param sTrim - Trim할 문자열(옵션 : Default=" ")
 * @return String Trim된 문자열
 */
function gfn_rTrimCase (sOrg, sTrim)
{
	var pos,nStart;

	if (gfn_isNull(sOrg))
	{
		return "";
	}
	if (gfn_isNull(sTrim))
	{
		sTrim = " ";
	}

	for (pos = sOrg.length - sTrim.length; pos >= 0; pos -= sTrim.length)
	{
		if (sOrg.toLowerCase().substr(pos, sTrim.length) != sTrim.toLowerCase())
		{
			break;
		}
	}

	return sOrg.substr(0, pos + sTrim.length);
}

 /**
 * @class 자리수 만큼 왼쪽에 문자열 추가
 * @param sOrg - 원래 문자열
 * @param sPad - Pad할 문자열
 * @param nCnt - 자리수
 * @return String Pad된 문자열
 */
function gfn_lPad (sOrg, sPad, nCnt)
{
	var i,sRet = "";
	var nLength;

	if (gfn_isNull(sOrg))
	{
		return "";
	}
	if (gfn_isNull(sPad))
	{
		sPad = " ";
	}
	if (gfn_isNull(nCnt))
	{
		nCnt = 1;
	}

	for (i = 0; i < nCnt; i++)
	{
		sRet += sPad;
	}
	sRet += sOrg;

	nLength = gfn_length(sOrg) > nCnt ? gfn_length(sOrg) : nCnt;

	return gfn_right(sRet,nLength);
}

 /**
 * @class 자리수 만큼 오른쪽에 문자열 추가
 * @param sOrg - 원래 문자열
 * @param sPad - Pad할 문자열
 * @param nCnt - 자리수
 * @return String Pad된 문자열
 */
function gfn_rPad (sOrg, sPad, nCnt)
{
	var i,sRet = "";
	var nLength;

	if (gfn_isNull(sOrg))
	{
		return "";
	}
	if (gfn_isNull(sPad))
	{
		sPad = " ";
	}
	if (gfn_isNull(nCnt))
	{
		nCnt = 1;
	}

	sRet += sOrg;
	for (i = 0; i < nCnt; i++)
	{
		sRet += sPad;
	}

	nLength = gfn_length(sOrg) > nCnt ? gfn_length(sOrg) : nCnt;

	return gfn_left(sRet,nLength);
}

 /**
 * @class 대소문자 구별하여 문자개수 세기
 * @param sOrg - 원래 문자열
 * @param sCnt - 개수를 셀 문자열
 * @return Integer 문자개수
 */
function gfn_strCount (sOrg, sCnt)
{
	var i,sRet = "";
	var nCnt = 0;

	if (gfn_isNull(sOrg) || gfn_isNull(sCnt))
	{
		return -1;
	}

	for (i = 0; i < sOrg.length; i += sCnt.length)
	{
		if (sOrg.substr(i, sCnt.length) == sCnt)
		{
			nCnt++;
		}
	}

	return nCnt;
}

 /**
 * @class 대소문자 구분없이 문자개수 세기
 * @param sOrg - 원래 문자열
 * @param sCnt - 개수를 셀 문자열
 * @return Integer 문자개수
 */
function gfn_strCountCase (sOrg, sCnt)
{
	var i,sRet = "";
	var nCnt = 0;

	if (gfn_isNull(sOrg) || gfn_isNull(sCnt))
	{
		return -1;
	}

	for (i = 0; i < sOrg.length; i += sCnt.length)
	{
		if (sOrg.toLowerCase().substr(i, sCnt.length) == sCnt.toLowerCase())
		{
			nCnt++;
		}
	}

	return nCnt;
}

 /**
 * @class html형식의 문자열에서 태그문자를 특수문자로 변형
 * @param str - html형식 문자열
 * @return String 변형문자열
 */
function gfn_htmlToChars (str)
{
	str = "" + str;
	if (gfn_isNull(str))
	{
		return "";
	}
	str = gfn_replace(str, "\&", '&amp;');
	str = gfn_replace(str, "\'", '&apos;');
	str = gfn_replace(str, "\"", '&quot;');
	str = gfn_replace(str, "\'", '&#39;');
	str = gfn_replace(str, "<", '&lt;');
	str = gfn_replace(str, ">", '&gt;');
	return str;
}

 /**
 * @class 특수문자가 들어있는 문자열에서 html형식의 문자로 변형
 * @param str - 특수문자 형식 문자열
 * @return String html형식의 문자
 */
function gfn_specToChars (str)
{
	str = "" + str;
	if (gfn_isNull(str))
	{
		return "";
	}
	str = gfn_replace(str, "\&amp;", '&');
	str = gfn_replace(str, "\&quot;", '"');
	str = gfn_replace(str, "\&#39;", '\'');
	str = gfn_replace(str, "\&lt;", '<');
	str = gfn_replace(str, "\&gt;", '>');
	return str;
}

 /**
 * sArgument = gfn_setArg('CORP_CD'  ,gfn_quote(gds_corp.getColumn(0,"CODE")));
 * sArgument = gfn_setArg('PLANT_CD' ,gfn_quote(gds_plant.getColumn(0,"CODE")) ,sArgument);
 *
 * @class Argument 문자열을 반환 예) key1, value1, key2, value2 ==> key1="value1" key2="value2"
 * @param key 키
 * @param key 값
 * @param key 추가해야할 arg
 * @param quote 유무 default true
 * @return String html형식의 문자
 */
function gfn_setArg(k ,v, arg, qt)
{
    qt = gfn_isNull(qt)? true:qt;

    var s = "";

    if(qt){
        s = gfn_nvl(arg) + ' ' + gfn_nvl(k) + '=' + gfn_quote(gfn_nvl(v));
    }else{
        s = gfn_nvl(arg) + ' ' + gfn_nvl(k) + '=' + gfn_nvl(v);
    }

    return s;
}

 /**
 * gfn_singleQuote('1,2,3,4,A,B,C,D,E'); --> return '1','2','3','4','A','B','C','D','E'
 *
 * @class 콤마로 구분된 문자열을 singleQuote 하여 리턴한다.
 * @param s 콤마로 구분된 문자열 또는 문자
 * @return singleQuote된 문자열
 */
function gfn_singleQuote(s)
{
	if(gfn_isNull(s)) return '';
	if(gfn_nvl(s) == '') return '';

	var tmpStr = s.split(",").join("','");
	tmpStr = "'"+tmpStr+"'";
	return tmpStr;
}


function gfn_replaceQuote(s, r)
{
	if(gfn_isNull(s)) return '';
	if(gfn_nvl(s) == '') return '';
	
	var tmpStr = "";
	var retStr = "";
	
	s = gfn_trim(s);
	s = s.replace(/'\s+,\s+'/g, "','").replace(/'\s+,/g, "',").replace(/,\s+'/g, ",'");
		
	if(s.indexOf("'") == 0 && s.lastIndexOf("'") == (s.length-1)){
		var tmpStr = s;
		
		if(s.indexOf("','") > 0){
			tmpStr = s.split("','").join(",");
		}

		if(tmpStr.indexOf("'") == 0 && tmpStr.lastIndexOf("'") == (tmpStr.length-1)){
			tmpStr = tmpStr.substring(1, tmpStr.length-1).replace("'", r);
			retStr = gfn_singleQuote(tmpStr);
		
		}else{
			retStr = gfn_singleQuote(tmpStr).substring(1, tmpStr.length-1);
		}
		
		
	}else{
		if(tmpStr.indexOf("'") == 0 && tmpStr.lastIndexOf("'") == (tmpStr.length-1)){
			tmpStr = tmpStr.substring(1, tmpStr.length-1);
			
			tmpStr = tmpStr.replace("'", r);
			retStr = gfn_singleQuote(tmpStr);
		}else{
			retStr = s.replace("'", r);
		}
	}
	
	return retStr;
}