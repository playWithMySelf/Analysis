angular.module("WebApp").controller('PersonCtrl', ['$scope', '$http', function ($scope, $http) {
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

    //获取所以复选框内容
    $scope.getAllCheck = function () {
        $http({
            url: "mvc/dispatch",
            params: {
                controller: "system",
                method: "selectDicdataByTypeOrName",
                DIC_TYPE_ID: "1218"
            }
        }).then(function (res) {
            $scope.headList = [];//全部标题

            var cacheList = res.data.rows;
            $.each(cacheList, function (idx, data) {
                data.isShow = 1;
                $scope.headList.push(data);
            });
        });
    };


    $scope.showModal = function (thisObj) {
        var dicpyElement = $("input[name='personCheck']:checked");
        var checksPY = [];    //选中的复选框拼音数组
        var checksZW = [];    //选中的复选框中文数组
        $.each(dicpyElement,function (i,elem) {
            checksPY.push($(elem).val());
            checksZW.push($(elem).parent().text().trim());
        });
        var item = thisObj.item;
        var values = item.values;

        var data = [];
        $.each(values,function (key,value) {
            var index = checksPY.indexOf(key);
            if(index!=-1){
                var temp = {value:value,name:checksZW[index]};
                data.push(temp);
            }
        });

        $("#myModal").modal('show');

        var dom = document.getElementById("modalContent");
        var myChart = echarts.init(dom);
        var app = {};
        option = null;
        option = {
            title : {
                text: item.ORGNAME,
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: checksZW
            },
            series : [
                {
                    name: '访问来源',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data: data,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        ;
        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }
    };

    $scope.getData = function () {
        $http({
            url: "mvc/dispatch",
            params: {
                controller: "rktj",
                method: "getRktj",
                zzjgdm:  $scope.orgId,
                type: ''
            }
        }).then(function (res) {

            $scope.list = [];
            $scope.cacheList = res.data.rows;
            $.each($scope.cacheList, function (idx, data) {
                var tjvalues = {};
                $.each(data,function(key,value){
                    if(key.indexOf('ZFLX')>=0){
                        tjvalues[key.substring(5)] = value;
                    }
                });
                data['values']=tjvalues;

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
        url += "&method=exportRktj";
        url += "&zzjgdm=" + $scope.orgId;
        var temp = ["<form method='post' action='"+url+"'>"];
        temp.push("input name='zzjgdm' value='"+$scope.orgId+"'");
        temp.push("</form>");
        $(temp.join("")).appendTo("body").submit().remove();
    }

    $scope.heatmap = function () {
        window.location="gzrq.html?zzjgdm="+$scope.orgId;
    }
}]);
