/* ***************************************************************************
 * EZ.JWAF/EZ.JCWAP: Easy series Production.
 * Including JWAF(Java-based Web Application Framework)
 * and JCWAP(Java-based Customized Web Application Platform).
 * Copyright (C) 2016-2017 the original author or authors.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of MIT License as published by
 * the Free Software Foundation;
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the MIT License for more details.
 *
 * You should have received a copy of the MIT License along
 * with this library; if not, write to the Free Software Foundation.
 * ***************************************************************************/

angular.module("WebApp").controller('BuildingCrtl', ['$scope', '$http', function ($scope, $http) {
    var strFullPath = window.document.location.href;
    var index = strFullPath.indexOf("?");
    if (index !== -1) {
        var params = strFullPath.substr(index + 1);
        var attr = params.split("&");//参数
        for (var i = 0; i < attr.length; i++) {
            var arr = attr[i].split("=");
            if (arr[0] === "orgId") {
                $scope.orgId = arr[1];
                break;
            }
        }
    }
    if (!$scope.orgId) {
        $scope.orgId = "520500000000";
    }

    $scope.flag = false;
    $scope.headList = [
        {
            DIC_PY:1,
            DIC_NAME:'是否危房',
            COL_SPAN:2,
            isShow:1
        },
        {
            DIC_PY:2,
            DIC_NAME:'房屋用途',
            isShow:1
        },
        {
            DIC_PY:3,
            DIC_NAME:'出租房',
            COL_SPAN:2,
            isShow:1
        }
    ];

    $scope.load = function () {
        $scope.getDicList();
        $scope.getData();
    };

    // 获取所以复选框内容
    $scope.getDicList = function () {
        $http({
            url: "mvc/dispatch",
            params: {
                controller: "building",
                method: "selectDicdataByTypeOrName",
                DIC_TYPE_ID: "1029"
            }
        }).then(function (res) {
            $scope.dicList = [];
            $scope.dicList.push({DIC_NAME:'危房',type:0});
            $scope.dicList.push({DIC_NAME:'非危房',type:0});
            var cacheList = res.data.rows;
            var otherItem = {};
            $.each(cacheList, function (idx, data) {
                if (data.DIC_NAME == '其他'){
                    data.type = 1;
                    otherItem = data;
                }else {
                    data.type = 1;
                    $scope.dicList.push(data);
                }
            });
            $scope.dicList.push(otherItem);
            $scope.dicList.push({DIC_NAME:'出租房',type:2});
            $scope.dicList.push({DIC_NAME:'非出租房',type:2});
            $scope.headList[1].COL_SPAN = cacheList.length;
            // $scope.headListAttr.isUsage.COL_SPAN = cacheList.length;
            // $scope.flag = true;
        });
    };

    //根据复选框状态获取表头信息
    $scope.getTableHead = function () {
        var dicpyElement = $("input[name='buildingCheck']");
        var dicpys = '';
        $.each(dicpyElement,function(i,elem){
            if(i==dicpyElement.length-1){
                dicpys = dicpys + $(elem).val();
            }else{
                dicpys = dicpys + $(elem).val() + ',';
            }
        });
        console.log(dicpys);
        $http({
            url: "mvc/dispatch",
            params: {
                controller: "system",
                method: "selectDicdataByDicpy",
                dicpys: dicpys
            }
        }).then(function (res) {
            $scope.headList = [];
            var cacheList = res.data.rows;
            $.each(cacheList, function (idx, data) {
                $scope.headList.push(data);
            });
        });
    };

    $scope.getData = function () {
        $http({
            url: "mvc/dispatch",
            params: {
                controller: "building",
                method: "selectAllBuildingInfo",
                zzjgdm: $scope.orgId
            }
        }).then(function (res) {

            $scope.list = [];
            $scope.cacheList = res.data.rows;
            $.each($scope.cacheList, function (idx, data) {
                if (data['PARENTORGID'] === $scope.orgId || data['ORGID'] === $scope.orgId) {
                    var clone;
                    clone = $.extend(true, {}, data);
                    clone['expanded'] = data['ORGID'] === $scope.orgId;
                    $scope.list.push(clone);
                }
            });
        });
    };

    $scope.load();

    $scope.expandAndHide = function (idx, item) {
        var fetAllSub = function(item) {
            var result = [];
            if (!item.expanded) {
                result.push(item);
            } else {
                for (var i = 0; i < $scope.list.length; i++) {
                    if ($scope.list[i].PARENTORGID === item.ORGID) {
                        result.push($scope.list[i]);
                        if ($scope.list[i].expanded) {
                            $.each(fetAllSub($scope.list[i]), function(idx, data) {
                                result.push(data);
                            });
                        }
                    }
                }
            }
            return result;
        };
        if (item.expanded) {
            var subs = fetAllSub(item);
            $.each(subs, function (index, data) {
                data.expanded = false;
            });
            $scope.list.splice(idx + 1, subs.length);
        } else {
            var current = idx;
            $.each($scope.cacheList, function (idx, data) {
                if (data["PARENTORGID"] === item.ORGID) {
                    $scope.list.splice(++current, 0, data);
                }
            });
        }
        item.expanded = !item.expanded;
    };

    $scope.download = function () {
        var url = "mvc/dispatch?";
        url += "controller=rktj";
        url += "&method=exportFwtj";
        url += "&zzjgdm=" + $scope.orgId;
        var temp = ["<form method='post' action='"+url+"'>"];
        temp.push("input name='zzjgdm' value='"+$scope.orgId+"'");
        temp.push("</form>");
        $(temp.join("")).appendTo("body").submit().remove();
    }
}]);
