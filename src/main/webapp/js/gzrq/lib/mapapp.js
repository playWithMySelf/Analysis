var map = null;
var zzjgdm = "";
var layers_ = {};
	function initMap(id,callback) {//初始化
	var mapObj = map = new EzMap(id);
	map.set("initFlag",true);
	map.on("postrender",function(){
		map.set("initFlag",false);
	});
	map.showSimpleZoomControl();
	map.addMapEventListener(Ez.Event.MAP_PAN, function(ev) {
	    
	 });


		var layers = map.global_.getLayersInfo();
		for (var o = 0; o < layers.length; o++) {
			var t = layers[o];
			layers_[t.title] = t.layer
		}
};
initMap("map");
function switchMapServerMap(i){
	$(".mapTd1").css({"background": "#fff"});
	$(".mapTd2").css({"background": "#fff"});
	$("#map_"+i).css({"background": "#e2effc"});
	swap(i);
};

function swap(e){
		var o = map;
		if (!goog.isDef(e)) {
			return
		}
		var t = getCurrentTileLayer()
			, r = t.get("ezname");
		if (e === r) {
			return
		}
		var i = layers_
			, n = i[e];
		var a = o.getView().getProjection();
		var l, s;
		if (n instanceof Ez.Layer.Group) {
			l = n.getLayers().getArray()[0].getSource().getProjection();
			s = n.getLayers().getArray()[0].getResolutions()
		} else {
			s = n.getResolutions();
			l = n.getSource().getProjection()
		}
		var g = o.getView().getCenter()
			, p = o.getView().getZoom();
		var u = o.getView()
			, c = u.getState();
		var d = ol.proj.transform(g, a, l);
		var y = {
			center: d,
			projection: l,
			resolutions: s,
			zoom: p
		};
		o.removeLayer(t);
		o.addLayer(n)
}
function getCurrentTileLayer(){
	var e = map.tileLayerGroup_.getLayers();
	return e.item(e.getLength() - 1)
}
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 匹配目标参数
	var result = window.location.search.substr(1).match(reg); // 对querystring匹配目标参数
	if (result != null) {
		return decodeURIComponent(result[2]);
	} else {
		return null;
	}
}
zzjgdm = getQueryString("zzjgdm");
$.ajax({
	url:"mvc/focuscrowed/selectBjzbzByZzjgdm",
	data:{zzjgdm:zzjgdm},
	type:"get",
	dataType:"json",
	success:function(data){
		if(data.code == "EC00"){
			if(data.rows.length > 0){
				if(data.rows[0].bjzbz != ""){
					var polyline = new Polyline(data.rows[0].bjzbz, {
						strokeColor:"#1d3957",
						strokeWidth:1,
						strokeOpacity:1
					});
					map.addOverlay(polyline);
					map.centerAtMBR(polyline.getMBR());
				}
			}

		}
	}
});