package kr.pe.sdh.common.util;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import kr.pe.sdh.common.view.SearchEntry;

import java.util.List;

/**
 * Created by seongdonghun on 2016. 9. 23..
 */
public class UIComponentsUtils {
    public static final String LF = "\r\n";
    private static String dateFormat = "yy-mm-dd";

    public static String getBindScript(SearchEntry searchEntry){
        String type = searchEntry.getType();

        StringBuffer scriptBuffer = new StringBuffer();

        // 날짜
        if(type.indexOf("date") > -1){
            scriptBuffer = getDateTypeScript(searchEntry);
            scriptBuffer.append(getDateDefaultDateSetting(searchEntry));
        // 연도
        }else if(type.indexOf("year") > -1){

        // 월
        }else if(type.indexOf("month") > -1){

        // autocomplete
        }else if(type.equals("autocomplete")){
            scriptBuffer = getAutocompleteTypeScript(searchEntry);
        }

        return scriptBuffer.toString();
    }

    private static StringBuffer getAutocompleteTypeScript(SearchEntry searchEntry) {
        StringBuffer scriptBuffer = new StringBuffer();
        scriptBuffer
                .append("$('#"+searchEntry.getId()+"')").append(LF)
                .append(".on( \"keydown\", function( event ) {")
                .append("\tif ( event.keyCode === $.ui.keyCode.TAB &&")
                .append("\t\t$( this ).autocomplete( \"instance\" ).menu.active ) {")
                .append("\t\tevent.preventDefault();")
                .append("\t}")
                .append("})")

                .append(".autocomplete({").append(LF)
                .append("\tsource: function( request, response ) {").append(LF)
                .append("\t\t$.ajax( {").append(LF)
                .append("\t\t\turl: \"commonCode.do\",").append(LF)
                .append("\t\t\tdataType: \"json\",").append(LF)
                .append("\t\t\tdata: {").append(LF)
                .append("\t\t\t\tterm: request.term,").append(LF)
                .append("\t\t\t\tselectQKey: 'selectQKey'").append(LF)
                .append("\t\t\t},").append(LF)
                .append("\t\t\tsuccess: function( data ) {").append(LF)
                .append("\t\t\t\tresponse( eval(data['data']) );").append(LF)
                .append("\t\t\t},").append(LF)
                .append("\t\t\terror : function(xhr, status, error) {").append(LF)
                .append("\t\t\t\talert('error : ' + status +'/'+error);").append(LF)
                .append("\t\t\t}").append(LF)
                .append("\t\t})").append(LF)
                .append("\t},").append(LF)

                .append("\tsearch: function() {").append(LF)
                .append("\t\tvar term = this.value.split( /,\\s*/ ).pop();").append(LF)
                .append("\t\tif ( term.length < 1 ) {").append(LF)
                .append("\t\t\treturn false;").append(LF)
                .append("\t\t}").append(LF)
                .append("\t},").append(LF)

                .append("\tfocus: function() { return false; },").append(LF)

                .append("\tselect: function( event, ui ) {").append(LF)
                .append("\t\tvar terms = this.value.split( /,\\s*/ );").append(LF)
                .append("\t\tterms.pop();").append(LF)
                .append("\t\tterms.push( ui.item.value );").append(LF)
                .append("\t\tterms.push( \"\" );").append(LF)
                .append("\t\tthis.value = terms.join( \", \" );").append(LF)
                .append("\t\treturn false;").append(LF)
                .append("\t}").append(LF)

                .append("});").append(LF);

        return scriptBuffer;
    }

    private static String getDateDefaultDateSetting(SearchEntry searchEntry) {
        String id = searchEntry.getId();
        String type = searchEntry.getType();

        String defaultDate = null;
        if(type.equals("duedate")){
            if(id.startsWith("from_")){
                defaultDate = searchEntry.getFromDate();
            }else{
                defaultDate = searchEntry.getToDate();
            }

        }else{
            defaultDate = searchEntry.getDefaultDate();
        }

        if(StringUtils.isNotEmpty(defaultDate)){
            return "$('#" + id + "').datepicker('setDate', '" + defaultDate + "');" + LF;
        }else{
            return "";
        }
    }

    private static StringBuffer getDateTypeScript(SearchEntry searchEntry) {
        String id = searchEntry.getId();
        String type = searchEntry.getType();

        StringBuffer scriptBuffer = new StringBuffer();
        scriptBuffer.append("$('#").append(id).append("').datepicker({").append(LF);

        String[] monthNames = {"1","2","3","4","5","6","7","8","9","10","11","12"};
        String[] dayNames   = {"일","월","화","수","목","금","토"};

        scriptBuffer
                .append(creatJqueryOption("showOn"            , "button")) // both
                .append(creatJqueryOption("buttonImage"       , "images/common/calendar.png"))
                .append(creatJqueryOption("buttonImageOnly"   , true))
                .append(creatJqueryOption("changeYear"        , true))
                .append(creatJqueryOption("changeMonth"       , true))
                .append(creatJqueryOption("showButtonPanel"   , true))
                .append(creatJqueryOption("showMonthAfterYear", true))
                .append(creatJqueryOption("yearSuffix"        , "/"))
                .append(creatJqueryOption("closeText"         , "Close"))
                .append(creatJqueryOption("monthNames"        , monthNames))
                .append(creatJqueryOption("monthNamesShort"   , monthNames))
                .append(creatJqueryOption("dayNames"          , dayNames))
                .append(creatJqueryOption("dayNamesShort"     , dayNames))
                .append(creatJqueryOption("dayNamesMin"       , dayNames));

        // defaultDate Setting
        if(type.equals("duedate")){
            if(id.startsWith("from_")){
                if(StringUtils.isNotEmpty(searchEntry.getFromDate())) {
                    scriptBuffer.append(creatJqueryOption("defaultDate", searchEntry.getFromDate()));

                }
            }else{
                if(StringUtils.isNotEmpty(searchEntry.getToDate())) {
                    scriptBuffer.append(creatJqueryOption("defaultDate", searchEntry.getToDate()));

                }
            }
        }else {
            if(StringUtils.isNotEmpty(searchEntry.getDefaultDate())){
                scriptBuffer.append(creatJqueryOption("defaultDate", searchEntry.getDefaultDate()));
            }
        }

        scriptBuffer.append(creatJqueryOption("dateFormat", dateFormat, false));
        scriptBuffer.append("})").append(LF);

        // duedate 일때 기간 validation 추가
        if(type.equals("duedate")){
            scriptBuffer = new StringBuffer("var " + id + "=" + scriptBuffer.toString());
            scriptBuffer.append(".on(\"change\", function(){").append(LF);

            if(id.startsWith("from_")){
                scriptBuffer.append("\tto").append(id.replaceAll("from", "")).append(".datepicker(\"option\", \"minDate\", gfn_getDate(this));").append(LF);

            }else{
                scriptBuffer.append("\tfrom").append(id.replaceAll("to", "")).append(".datepicker(\"option\", \"maxDate\", gfn_getDate(this));").append(LF);

            }
            scriptBuffer.append("})");

        }

        scriptBuffer.append(";").append(LF);

        return scriptBuffer;
    }

    private static String createFunction(String[] params, String fnContent){
        StringBuffer fn = new StringBuffer();
        fn.append("function(");
        for (String param : params){
            fn.append(param).append(",");
        }
        fn.deleteCharAt(fn.length()-1);
        fn.append(") {").append(LF);
        fn.append("\t").append(fnContent).append(LF);
        fn.append("},").append(LF);

        return fn.toString();
    }

    private static String creatJqueryOption(String key, Object value) {
        return creatJqueryOption(key, value, true);
    }
    private static String creatJqueryOption(String key, Object value, boolean isComma) {
        String result = new String();
        if(value instanceof String){
            result = "'" + String.valueOf(value) + "'";

        }else{
            if(value instanceof String[] || value instanceof List){
                result = StringUtils.arrayToString(value);
            }else{
                result = String.valueOf(value);
            }
        }

        return "\t" + key + ":" + result + (isComma ? "," : "") + LF;
    }
}
