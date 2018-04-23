angular.module("WebApp").controller('DepartmentCtrl', ['$scope', '$http', function ($scope, $http) {
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

    $scope.load = function () {
        $scope.getAllCheck();
        $scope.getData();
    };

    //重点人员类型和单位类别复选框状态
    $scope.showZDDWLX = 1;
    $scope.showDWLB = 1;

    //获取所以复选框内容
    $scope.getAllCheck = function () {
        $http({
            url: "mvc/dispatch",
            params: {
                controller: "system",
                method: "selectDicdataByTypeOrName",
                DIC_TYPE_ID: "1206", //重点单位类型
                DIC_LEVEL: "1"  //级别
            }
        }).then(function (res) {
            $scope.zddwlxList = [];
            var cacheList = res.data.rows;
            $.each(cacheList, function (idx, data) {
                $scope.zddwlxList.push(data);
            });
        });
        $http({
            url: "mvc/dispatch",
            params: {
                controller: "system",
                method: "selectDicdataByTypeOrName",
                DIC_TYPE_ID: "1199", //单位类别
                DIC_LEVEL: "1"  //级别
            }
        }).then(function (res) {
            $scope.dwlbList = [];
            var cacheList = res.data.rows;
            $.each(cacheList, function (idx, data) {
                $scope.dwlbList.push(data);
            });
        });
    };

    /*$scope.bindEvent = function () {
        var dicpyElement = $("input[name='departmentCheck']");
        var dicpys = '';
        $.each(dicpyElement,function (i,elem) {
            var checkObj = $(elem);
            checkObj.click(function(){
                var value = checkObj.val();
                if(checkObj.is(":checked")){
                    $("."+value).show();
                }else{
                    $("."+value).hide();
                }
            });
        });
    };*/

    //获取数据
    $scope.getData = function () {
        $http({
            url: "mvc/dispatch",
            params: {
                controller: "rktj",
                method: "getDwtj",
                zzjgdm: $scope.orgId,
                type: ''
            }
        }).then(function (res) {

            $scope.list = [];
            $scope.cacheList = res.data.rows;
            $.each($scope.cacheList, function (idx, data) {
                var zddwlxValues = {};
                var dwlbdmValues = {};
                $.each(data,function(key,value){
                    if(key.indexOf('ZDDWLX')>=0){
                        zddwlxValues[key.substring(7)] = value;
                    }
                    if(key.indexOf('DWLBDM')>=0){
                        dwlbdmValues[key.substring(7)] = value;
                    }
                });
                data['zddwlxValues']=zddwlxValues;
                data['dwlbdmValues']=dwlbdmValues;

                if (data['PARENTORGID'] === $scope.orgId) {
                    var clone;
                    clone = $.extend(true, {}, data);
                    clone['expanded'] = data['ORGID'] === $scope.orgId;
                    $scope.list.push(clone);
                }else if(data['ORGID'] === $scope.orgId) {
                    var clone;
                    clone = $.extend(true, {}, data);
                    clone['expanded'] = data['ORGID'] === $scope.orgId;
                    $scope.list.unshift(clone);
                }
            });
            console.log($scope.list);
            /*$scope.bindEvent();*/

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
        url += "&method=exportDwtj";
        url += "&zzjgdm=" + $scope.orgId;
        var temp = ["<form method='post' action='"+url+"'>"];
        temp.push("input name='zzjgdm' value='"+$scope.orgId+"'");
        temp.push("</form>");
        $(temp.join("")).appendTo("body").submit().remove();
    }
}]);
