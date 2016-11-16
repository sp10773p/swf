/**
 * Created by sdh on 2016-10-06.
 */
function gfn_getRowData(gridId, rowId){
    return $('#' + gridId).jqGrid('getRowData', rowId);
}