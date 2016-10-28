var entity = angular.module("entity", []);

entity.controller("entityInfoController", function ($scope, $http) {
//    $scope.categories = [
//        {name: 'abc',processing:0,;
//            options: [{conjunction :'and',key: {udfType: 1, keyValue: ''}, condition: '>', value: 456}]
//        }
//    ];
    $scope.selections = [];
    $scope.geography = {};
    $scope.geography.type = 'country';
    $scope.categories = [];
    $scope.options = [];
    $scope.categories.push();
    
    $scope.addCategory = function() {};

    // Options for Selecting Characteristic
    // Add a new row option
    $scope.addOption = function (conjunction) {
        $scope.options.push({conjunction: conjunction, key: {udfType: 1, keyValue: ''}, conditions: [], condition: '', listValues: [], value: ''});
    };

    $scope.removeOption = function (optionIndex) {
        $scope.options.splice(optionIndex, 1);
    };

    // Add values and conditions according to First option dropdown
    // This will call function in InformationController@showFieldListValue
    $scope.loadValue = function (optionid) {
        $scope.options[optionid].listValues = [];
        $http({
            method: 'GET',
            url: "monitor/entity-info-field/" + $scope.options[optionid].key.keyValue
        }).then(function (response) {
            if (response.data.values.length === 0) {
                $scope.options[optionid].key.edfSearchType = 2;
            } else {
                $scope.options[optionid].key.edfSearchType = 1;
            }
            $scope.options[optionid].listValues = response.data.values;
            $scope.options[optionid].conditions = response.data.conditions;
        }, function (response) {});
    };
    
    $scope.view = function () {
        //make the categories
        var conditions = [];
        for (i = 0; i < $scope.options.length; i++) {
            conditions.push({conjunction: $scope.options[i].conjunction,
                keyValue: $scope.options[i].key.keyValue,
                condition: $scope.options[i].condition,
                value: $scope.options[i].value});
        }
        var checkboxes = document.getElementsByClassName("selections");
        var selections = [];
        // loop over them all
        for (var i = 0; i < checkboxes.length; i++) {
            // And stick the checked ones onto an array...
            if (checkboxes[i].checked) {
                selections.push(checkboxes[i].value);
            }
        }
        $('#modal-loading').modal('show');
        $http({
            method: 'GET',
            url: "monitor/entity-info",
            params: {
                gp_type: $scope.geography.type,
                gp_province: $scope.geography.province,
                gp_district: $scope.geography.district,
                gp_commune: $scope.geography.commune,
                gp_village: $scope.geography.village,
                table_name: $scope.tableName,
                data: JSON.stringify(conditions),
                selections: JSON.stringify(selections)
            },
            headers: {
                // Header for sending file
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).then(function (response) {
            document.getElementById("form-result").innerHTML = response.data;
            $('#modal-loading').modal('hide');
            reloadScript();
        }, function () {
            $('#modal-loading').modal('hide');
            alert("Error! Please check again!");
        });
    };
    
    $scope.reset = function () {
        $scope.selections = [];
        $scope.geography = {};
        $scope.geography.type = 'country';
        for (i = 0; i < $scope.categories.length; i++) {
            $scope.categories[i].options = [];
            $scope.categories[i].selectedField = true;
        }
    }; 
});

// For Aggregate
entity.controller("entityAggController", function ($scope, $http) {
    $scope.selections = [];
    $scope.geography = {};
    $scope.geography.type = 'country';
    $scope.geography.aggType='country';
    $scope.categories = [];
    $scope.options = [];
    $scope.categories.push();

    $scope.addCategory = function () {};

    $scope.addOption = function (conjunction) {
        $scope.options.push({conjunction: conjunction, key: {udfType: 1, keyValue: ''}, conditions: [], condition: '', listValues: [], value: ''});
    };

    $scope.removeOption = function (optionIndex) {
        $scope.options.splice(optionIndex, 1);
    };

    $scope.loadValue = function (optionid) {
        $scope.options[optionid].listValues = [];
        $http({
            method: 'GET',
            url: "monitor/entity-info-field/" + $scope.options[optionid].key.keyValue
        }).then(function (response) {
            if (response.data.values.length === 0) {
                $scope.options[optionid].key.edfSearchType = 2;
            } else {
                $scope.options[optionid].key.edfSearchType = 1;
            }
            $scope.options[optionid].listValues = response.data.values;
            $scope.options[optionid].conditions = response.data.conditions;
        }, function (response) {

        });
    };

    $scope.view = function () {
        //make the categori
        var conditions = [];
        for (i = 0; i < $scope.options.length; i++) {
            conditions.push({conjunction: $scope.options[i].conjunction,
                keyValue: $scope.options[i].key.keyValue,
                condition: $scope.options[i].condition,
                value: $scope.options[i].value});
        }
        $('#modal-loading').modal('show');
        $http({
            method: 'GET',
            url: "monitor/entity-agg",
            params: {
                gp_type: $scope.geography.type,
                gp_aggType:$scope.geography.aggType,
                gp_province: $scope.geography.province,
                gp_district: $scope.geography.district,
                gp_commune: $scope.geography.commune,
                gp_village: $scope.geography.village,
                table_name: $scope.tableName,
                data: JSON.stringify(conditions)
            },
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).then(function (response) {
            document.getElementById("form-result").innerHTML = response.data;
            $('#modal-loading').modal('hide');
            reloadScript();
        }, function () {
            $('#modal-loading').modal('hide');
            alert("Error! Please check again in Aggregate!");
        });
    };

    $scope.reset = function () {
        $scope.selections = [];
        $scope.geography = {};
        $scope.geography.type = 'country';
        gp_aggType: $scope.geography.aggType = 'country';
        for (i = 0; i < $scope.categories.length; i++) {
            $scope.categories[i].options = [];
            $scope.categories[i].selectedField = true;
        }
    };
});


