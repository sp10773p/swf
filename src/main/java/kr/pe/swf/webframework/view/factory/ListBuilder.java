package kr.pe.swf.webframework.view.factory;

import kr.pe.swf.webframework.util.StringUtils;
import kr.pe.swf.webframework.view.ListsFactory;
import kr.pe.swf.webframework.view.entry.EventEntry;
import org.apache.velocity.app.VelocityEngine;
import org.json.simple.JSONObject;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by sdh on 2016-10-15.
 */
public class ListBuilder {
	private String gridType;
	private ListsFactory listsFactory;
	private VelocityEngine velocityEngine;

	public ListBuilder(String gridType, ListsFactory listsFactory, VelocityEngine velocityEngine) {
		this.gridType = gridType;
		this.listsFactory = listsFactory;
		this.velocityEngine = velocityEngine;
	}

	public String getGridType() {
		return gridType;
	}

	public void setGridType(String gridType) {
		this.gridType = gridType;
	}

	public String createBindGrid(int count, int index, String targetListQKey, String url, String layout, Map param){
		if(StringUtils.isEmpty(layout)){
			layout = "DEFAULT_GRID";
		}

		String gridVm = this.gridType + "/ " + layout + ".vm";

		Map map = this.listsFactory.toMap();

		StringBuffer buffer = new StringBuffer();
		// 리스트가 2개이상이고 다음 타겟 리스트가 있을때 타겟리스트 조회 이벤트를 설정
		if(count > (index + 1)){
			String targetId = "LISTS" + (index + 2);

			buffer.append("onSelectRow: function(rowId, status, e){\r\n");
			buffer.append("\t\tvar server = new serverUtil(null, '").append(targetId).append("','").append(targetListQKey).append("','").append(""); // todo 잊어먹음
			buffer.append("\t\tserver.setParam('rowKey', rowId);\r\n");
			buffer.append("\t\tserver.loadGrid();\r\n");
			buffer.append("\t},\r\n");
		}

		//사용자 이벤트
		List<EventEntry> eventEntries = this.listsFactory.getEventEntries();
		for(EventEntry eventEntry : eventEntries){
			String ev = eventEntry.getName();
			String fn = eventEntry.getFnName();

			if(StringUtils.isNotEmpty(ev) && StringUtils.isNotEmpty(fn)){
				buffer.append(ev).append(": ").append(fn).append(",\r\n");
			}
		}

		map.put("defaultEvent", buffer.toString());

		buffer = new StringBuffer();

		// 화면로드시 그리드 조회
		if(this.listsFactory.isFirstLoad()){
			JSONObject jsonObject = new JSONObject();
			jsonObject.putAll(param);
			jsonObject.put("selectQKey", this.listsFactory.getqKey());

			String test = "encodeURIComponent(('" + jsonObject.toJSONString() + "').split( \"null\").join(''))";
			buffer.append("url : '").append((StringUtils.isEmpty(this.listsFactory.getUrl()) ? "commonGridSelectList.do" : this.listsFactory.getUrl())).append("\r\n");
			buffer.append("datatype: 'json',").append("\r\n");
			buffer.append("mtype: 'post',").append("\r\n");
			buffer.append("postData: {formData: ").append(test).append("}, \r\n");

		}else{
			buffer.append("datatype: 'local', ").append("\r\n");
		}

		map.put("initOption", buffer.toString());

		int rows = (StringUtils.isEmpty(listsFactory.getRowNum()) ? 10 : Integer.parseInt(listsFactory.getRowNum()));
		map.put("height", (rows == 10 ? "250" : "550"));

		StringWriter writer = new StringWriter();
		VelocityEngineUtils.mergeTemplate(this.velocityEngine, gridVm, "UTF-8", map, writer);

		return writer.toString();
	}

}
