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

angular.module("WebApp").controller('KindCtrl', ['$scope', '$http', function ($scope, $http) {
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
        $http({
            url: "mvc/dispatch",
            params: {
                controller: "StatisticController",
                method: "queryAllDataByKind",
                orgId: $scope.orgId
            }
        }).then(function (res) {

            $scope.list = [];
            $scope.cacheList = res.data.result;
            $.each($scope.cacheList, function (idx, data) {
                if (data['parentOrgId'] === "520500000000" || data['orgId'] === "520500000000") {
                    var clone;
                    clone = $.extend(true, {}, data);
                    clone['expanded'] = data['orgId'] === "520500000000";
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
                    if ($scope.list[i].parentOrgId === item.orgId) {
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
                if (data["parentOrgId"] === item.orgId) {
                    $scope.list.splice(++current, 0, data);
                }
            });
        }
        item.expanded = !item.expanded;
    };

    $scope.download = function () {
        var url = "mvc/dispatch?";
        url += "controller=ExportController";
        url += "&method=exporAllDataByKind";
        url += "&orgId=" + $scope.orgId;
        window.open(url, "_blank")
    }
}]);
