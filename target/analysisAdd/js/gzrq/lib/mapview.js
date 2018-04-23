var mapview = {
    zoomLevel : null,
    init:function(){
        var source = new ol.source.Vector({
            features: []
        });
        mapview.heatmap = new ol.layer.Heatmap({
            source: source,
            blur: 10,
            radius: 10
        });
        var o = map.getVectorLayers();
        var t = o.getLayers();
        t.push(mapview.heatmap);
        mapview.zoomLevel = map.getZoom();
        map.addMapEventListener(Ez.Event.MAP_ZOOMCHANGE, function(ev) {
            var zoom = map.getZoom();
            if(zoom > 16){
                if(mapview.zoomLevel <= 16){
                    mapview.clear();
                    mapview.switchShow();
                }
            }else{
                if(mapview.zoomLevel > 16){
                    mapview.clear();
                    mapview.switchShow();
                }
            }

            mapview.zoomLevel = zoom;
        });
    },
    showData:[],
    showMarkers:[],
    clear:function(){
        var source = mapview.heatmap.getSource();
        source.clear();
        mapview.clearMarkers();
    },
    requestHeatData:function(gzrqlx){
        mapview.showData = [];
        var source = mapview.heatmap.getSource();
        source.clear();
        mapview.clearMarkers();
        $.ajax({
            url:"mvc/focuscrowed/selectFocuscrowedLocation",
            data:{gzrqlx:gzrqlx, zzjgdm:zzjgdm},
            type:"get",
            dataType:"json",
            success:function(data){
                if(data.code == "EC00"){
                    mapview.showData = data.rows;
                    mapview.switchShow();
                }
            }
        });
    },
    switchShow:function(){
        var rows = mapview.showData;
        var zoom = map.getZoom();
        if(zoom > 16){
            mapview.addMarkers(rows);
        }else{
            mapview.addHeatData(rows);
        }

    },
    addHeatData:function(rows){
        var source = mapview.heatmap.getSource();
        for(var i = 0, row; row = rows[i]; i++){
            if(row.JZD_ZBX && row.JZD_ZBY && row.JZD_ZBX != "" && row.JZD_ZBY != ""){
                var coordinates = [row.JZD_ZBX, row.JZD_ZBY];
                var feature = new ol.Feature(new ol.geom.Point(coordinates));
                source.addFeature(feature);
            }
        }
    },
    addMarkers:function(rows){
        for(var i = 0, row; row = rows[i]; i++){
            mapview.addMarker(row);
        }
    },
    addMarker:function(row){
        if(row.JZD_ZBX && row.JZD_ZBY && row.JZD_ZBX != "" && row.JZD_ZBY != ""){
            var uIcon = new EzIcon({src:"js/gzrq/images/syfw_blue.png", size:[26, 35], anchor:[0.5, 1],
                anchorXUnits:"fraction", anchorYUnits:"fraction", scale:1, opacity:1});
            var point = new EzCoord(row.JZD_ZBX, row.JZD_ZBY);
            var title = new EzTitle(row.XM);
            var marker = new EzMarker(point, uIcon, title);

            map.addOverlay(marker);

            mapview.showMarkers.push(marker);
        }
    },
    clearMarkers:function(){
        for(var i = 0, marker; marker = mapview.showMarkers[i]; i++){
            map.removeOverlay(marker);
        }
        mapview.showMarkers = [];
    }
};

mapview.init();

$.ajax({
    url:"mvc/focuscrowed/selectGzrqLx",
    data:{},
    type:"get",
    dataType:"json",
    success:function(data){
        if(data.code == "EC00"){
            if(data.rows.length > 0){
                var rows = data.rows;
                var strhtml = '<select value="'+rows[0].DIC_CODE+'" class="span3" tabindex="'+rows[0].DIC_CODE+'" name="herolist">';
                for(var i = 0, row; row = rows[i]; i++){
                    strhtml += '<option value="'+row.DIC_CODE+'">'+row.DIC_NAME+'</option>';
                }
                strhtml+='</select>';
                $("#gzrqlxCon").html(strhtml);
                mapview.requestHeatData(rows[0].DIC_CODE);
                $("select").dropkick({change:function($select, value){
                    mapview.requestHeatData($select);
                }});
            }

        }
    }
});