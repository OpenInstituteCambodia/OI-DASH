
<section ng-app="app" id="widget-grid" class="">
    <div class="row">
        <article class="col-sm-12 col-md-12 col-lg-12" ng-controller="entityAggController">
            <input type="text" ng-init="tableName ='{{$table}}'" ng-model="tableName" hidden="">
            <div class="jarviswidget jarviswidget-color-blueLight" id="wid-id-0" data-widget-sortable="false" data-widget-deletebutton="false" data-widget-editbutton="false" data-widget-custombutton="false">
                <header>
                    <span class="widget-icon"> <i class="fa fa-files-o"></i> </span>
                    <h2 style="font-size: 17px">{{ trans('aggregate_content.form-aggregate-list') }}</h2>
                    <span id="loading" style="display: none;"><i class="fa fa-gear fa-2x fa-spin"></i></span>
                </header>
                <div>
                    <!-- widget edit box -->
                    <div class="jarviswidget-editbox">
                        <!-- This area used as dropdown edit box -->

                    </div>
                    <!-- end widget edit box -->
                    <div class="widget-body no-padding">
                        <div class="smart-form">
                            <fieldset>
                                <header><b>{{ trans('aggregate_content.geography.selecting-geographical-area') }}</b></header>
                                <div class="col-sm-5">
                                    <fieldset>
                                        <div class="row">
                                            <section class="col col-4">
                                                <label class="label">
                                                    {{ trans('aggregate_content.geography.geographical-area') }}
                                                </label>
                                            </section>
                                            <section class="col col-8">
                                                <label class="select">
                                                    <input id="auth_level"  type="hidden" value="{{ Auth::user()->role->level }}" />
                                                    <div ng-model="user_type" style="display: none;"></div>
                                                    {{--<p ng-bind="province_code"></p>--}}

                                                    <label class="select">
                                                        <select ng-model="geography.type" class="form-control" id="geography">
                                                            @foreach($geographical_areas as $geographical_area)
                                                                <option value="{{$geographical_area['name']}}" selected="{{$geographical_area['selected']}}">{{$geographical_area['label']}}</option>
                                                            @endforeach
                                                        </select>
                                                        <i></i>
                                                    </label>
                                                    <i></i>
                                                </label>
                                            </section>
                                        </div>
                                    </fieldset>
                                </div>
                                <div class="col-sm-6" style="border-left: 1px solid black">
                                    <fieldset>
                                        <div class="row" ng-hide="geography.type === 'country'">
                                            <section class="col col-3">
                                                <label class="label">
                                                    {{ trans('aggregate_content.geography.province') }}
                                                </label>
                                            </section>
                                            <section class="col col-1">
                                                <label class="label">
                                                    :
                                                </label>
                                            </section>
                                            <section class="col col-8">
                                                @if($provinces->count() < 2)
                                                    <input id="province_code"  type="hidden" value="{{ $provinces->first()->ProvinceCode }}" />
                                                    <h5>{{ $provinces->first()->ProvinceName }}</h5>
                                                @else
                                                    <label class="select">
                                                        <!-- loadNew function here is used to change district according to the selected province -->
                                                        <select ng-model="geography.province" id="province-filter" class="form-control" onchange="loadNew(this, 'district')">
                                                            <!-- Only Province is passed from controller. Others will be loaded by Javascript below -->
                                                            <option value="">{{ trans('information_content.geography.province_label') }}</option>
                                                            @foreach($provinces as $province)
                                                                <option value="{{$province->ProvinceCode}}">{{$province->ProvinceName}}</option>
                                                            @endforeach
                                                        </select>
                                                        <i></i>
                                                    </label>
                                                @endif
                                            </section>
                                        </div>
                                        <div class="row" ng-show="geography.type === 'commune' || geography.type === 'district'">
                                            <section class="col col-3">
                                                <label class="label">
                                                    {{ trans('aggregate_content.geography.district') }}
                                                </label>
                                            </section>
                                            <section class="col col-1">
                                                <label class="label">
                                                    :
                                                </label>
                                            </section>
                                            <section class="col col-8">
                                                @if($districts->count() < 2)
                                                    <input id="district_code"  type="hidden" value="{{ $districts->first()->DistrictCode }}" />
                                                    <h5>{{ $districts->first()->DistrictName }}</h5>
                                                @else
                                                    <label class="select">
                                                        <select ng-model="geography.district" id="district-filter" class="form-control" onchange="loadNew(this, 'commune')">
                                                            <option value="">{{ trans('information_content.geography.district_label') }}</option>
                                                        </select>
                                                        <i></i>
                                                    </label>
                                                @endif
                                            </section>
                                        </div>
                                        <div class="row" ng-show="geography.type === 'commune'">
                                            <section class="col col-3">
                                                <label class="label">
                                                    {{ trans('aggregate_content.geography.commune') }}
                                                </label>
                                            </section>
                                            <section class="col col-1">
                                                <label class="label">
                                                    :
                                                </label>
                                            </section>
                                            <section class="col col-8">
                                                <label class="select">
                                                    <select ng-model="geography.commune" id="commune-filter" class="form-control" onchange="loadNew(this, 'village')">
                                                        <option value="">{{ trans('aggregate_content.geography.commune_label') }}</option>
                                                    </select>
                                                    <i></i>
                                                </label>
                                            </section>
                                        </div>
                                    </fieldset>
                                </div>
                            </fieldset>
                            <fieldset>
                                <header><b>{{ trans('aggregate_content.aggregate') }}</b></header>
                                <div class="col-sm-5">
                                    <fieldset>
                                        <div class="row">
                                            <section class="col col-4">
                                                <label class="label">
                                                    {{ trans('aggregate_content.geography.geographical-area') }}
                                                </label>
                                            </section>
                                            <section class="col col-8">
                                                <label class="select">
                                                    <select ng-model="geography.aggType" class="form-control">
                                                        @foreach($geographical_areas as $geographical_area)
                                                            <option value="{{$geographical_area['name']}}" selected="{{$geographical_area['selected']}}">{{$geographical_area['label']}}</option>
                                                        @endforeach
                                                    </select>
                                                    <i></i>
                                                </label>
                                            </section>
                                            {{--<p ng-bind="geography.aggType"></p>--}}
                                        </div>
                                    </fieldset>
                                </div>
                            </fieldset>
                            <fieldset>
                                <header><b>{{ trans('aggregate_content.characteristic.selecting-characteristic') }}</b></header>
                                <div class="col-sm-12">
                                    <fieldset>
                                        <div class="row" ng-repeat="option in options">
                                            <section class="col col-1">
                                                <label class="select">
                                                    <select ng-model="option.conjunction" ng-hide="$index === 0" class="form-control">
                                                        <option value="AND">{{ trans('aggregate_content.characteristic.and') }}</option>
                                                        <option value="OR">{{ trans('aggregate_content.characteristic.or') }}</option>
                                                    </select>
                                                </label>
                                            </section>
                                            <section class="col col-3">
                                                <label class="select">
                                                    <select ng-model="option.key.keyValue"  ng-change="loadValue($index)"  class="form-control">
                                                        <option value="">{{ trans('aggregate_content.characteristic.select') }}</option>
                                                        @foreach($fields as $field)
                                                            @if($field->DisplayField!==1)
                                                                <option ng-show="{{$field->EDFSearchType}} !== 0" value="{{$field->EntityDefinedFieldNameInTable}}">{{$field->EntityDefinedFieldListName}}</option>
                                                            @endif
                                                        @endforeach
                                                    </select>
                                                </label>
                                            </section>
                                            <section class="col col-3">
                                                <label class="select">
                                                    <select ng-model="option.condition" class="form-control">
                                                        <option value="">{{ trans('aggregate_content.characteristic.select') }}</option>
                                                        <option ng-repeat="condition in option.conditions" value="<%condition.ConditionSymbol%>"><%condition.ConditionName%></option>
                                                    </select>
                                                </label>
                                            </section>
                                            <section class="col col-3">
                                                <label class="select" ng-show="option.key.edfSearchType === 1">
                                                    @if (session()->get('locale'))
                                                        @if (session()->get('locale') == 'en')
                                                            <select ng-model="option.value" class="form-control">
                                                                <option value="">{{ trans('information_content.characteristic.select') }}</option>
                                                                <option ng-repeat="listValue in option.listValues" value="<%listValue.Value%>"><%listValue.Description%></option>
                                                            </select>
                                                            <i></i>
                                                        @elseif (session()->get('locale') == 'km')
                                                            <select ng-model="option.value" class="form-control">
                                                                <option value="">{{ trans('information_content.characteristic.select') }}</option>
                                                                <option ng-repeat="listValue in option.listValues" value="<%listValue.Value%>"><%listValue.Description_KH%></option>
                                                            </select>
                                                            <i></i>
                                                        @endif
                                                    @else
                                                        <select ng-model="option.value" class="form-control">
                                                            <option value="">{{ trans('information_content.characteristic.select') }}</option>
                                                            <option ng-repeat="listValue in option.listValues" value="<%listValue.Value%>"><%listValue.Description_KH%></option>
                                                        </select>
                                                        <i></i>
                                                    @endif
                                                </label>
                                                <label class="input" ng-show="option.key.edfSearchType === 2">
                                                    <input ng-model="option.value" class="form-control">
                                                </label>
                                            </section>
                                            <section class="col col-1">
                                                <button class="btn btn-sm btn-danger" ng-click="removeOption($index)"><i class="fa fa-trash-o"></i>{{ trans('button.remove') }}</button>
                                            </section>
                                        </div>
                                    </fieldset>
                                </div>
                                <button class="btn btn-sm btn-primary" ng-click="addOption('AND')">{{ trans('button.new-option') }}</button>
                            </fieldset>
                            <footer>
                                <button class="btn btn-primary" ng-click="view()"><i class="fa fa-fw fa-search"></i>{{ trans('button.search') }}</button>
                                <button class="btn btn-danger" type="button" ng-click="reset()"><i class="fa fa-fw fa-refresh"></i>{{ trans('button.reset') }}</button>
                            </footer>
                        </div>
                    </div>
                </div>
            </div><!-- end panel-->
        </article><!-- end controller-->
    </div><!-- end row-->
    <div id="form-result">
    </div>
</section> <!-- end section-->
<div id="modal-loading" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content" style="background-color: transparent; box-shadow: none; border: none;">
            <div class="modal-body" style="background-color: transparent; text-align: center;">
                <img src="{{asset('img/loading.gif')}}">
            </div>
        </div>

    </div>
</div>

<script type="text/javascript">
    pageSetUp();
    $(document).ready(function () {
        angular.bootstrap($('#widget-grid'), ["app"]);

        $('#geography').change(function () {
            if(({{ $user_role_level }}) == 3) {
                var p_code = $('#province_code').val();

                var type = 'district';

                jQuery.ajax({
                    url: "{{url('PDCV')}}/" + type + "/" + p_code,
                    type: "GET",
                    dataType: "json",
                    success: function (data) {
//                            alert(data);
                        $("#district-filter").html("<option value=''>{{ trans('information_content.geography.district_label') }}</option>");
                        $.each(data, function (i, value) {
//                                alert(value.DistrictCode);
                            $('#district-filter').append('<option value="' + value.DistrictCode + '">' + value.DistrictName + '</option>');
                        });
                    }
                });
            } else if(({{ $user_role_level }}) > 3) {
                var d_code = $('#district_code').val();

                var type = 'commune';

                jQuery.ajax({
                    url: "{{url('PDCV')}}/" + type + "/" + d_code,
                    type: "GET",
                    dataType: "json",
                    success: function (data) {
//                            alert(data);
                        $("#commune-filter").html("<option value=''>{{ trans('information_content.geography.commune_label') }}</option>");
                        $.each(data, function (i, value) {
//                                alert(value.DistrictCode);
                            $('#commune-filter').append('<option value="' + value.CommuneCode + '">' + value.CommuneName + '</option>');
                        });
                    }
                });
            }
        });

    });
    loadScript("js/plugin/bootstraptree/bootstrap-tree.min.js", function(){

    });
</script>

<script>
    var conditions = <?php echo $conditions; ?>;
    var newField = function (obj) {
        var append = $(obj).parent().siblings(".panel-body");
        $(append).html("Hello");
    };

    var loadNew = function (obj, type) {
        var code;
        code = $(obj).val();
        var location = getLocation(type, code);
        if (type === 'district') {
            $("#district-filter").html("<option value=''>{{ trans('information_content.geography.district_label') }}</option>");
            for (i = 0; i < location.length; i++) {
                $("#district-filter").append("<option value='" + location[i].DistrictCode + "'>" + location[i].DistrictName + "</option>");
            }
            $("#commune-filter").html("<option value=''>{{ trans('information_content.geography.commune_label') }}</option>");
            $("#village-filter").html("<option value=''>{{ trans('information_content.geography.village_label') }}</option>");
        } else if (type === 'commune') {
            $("#commune-filter").html("<option value=''>{{ trans('information_content.geography.commune_label') }}</option>");
            for (i = 0; i < location.length; i++) {
                $("#commune-filter").append("<option value='" + location[i].CommuneCode + "'>" + location[i].CommuneName + "</option>");
            }
            $("#village-filter").html("<option value=''>{{ trans('information_content.geography.village_label') }}</option>");
        } else if (type === 'village') {
            $("#village-filter").html("<option value=''>{{ trans('information_content.geography.village_label') }}</option>");
            for (i = 0; i < location.length; i++) {
                $("#village-filter").append("<option value='" + location[i].VillageCode + "'>" + location[i].VillageName + "</option>");
            }
        }
    };
    var getLocation = function (type, code) {
        var results = "";
        if (name === undefined) {
            name = "";
        }
        $.ajax({
            url: "{{url('PDCV')}}/" + type + "/" + code,
            type: "GET",
            async: false,
            success: function (result) {
                results = result;
            }
        });
        return results;
    };
    var pagefunction = function () {

        //console.log("cleared");

        /* // DOM Position key index //

         l - Length changing (dropdown)
         f - Filtering input (search)
         t - The Table! (datatable)
         i - Information (records)
         p - Pagination (paging)
         r - pRocessing
         < and > - div elements
         <"#id" and > - div with an id
         <"class" and > - div with a class
         <"#id.class" and > - div with an id and class

         Also see: http://legacy.datatables.net/usage/features
         */

        /* BASIC ;*/
        var responsiveHelper_datatable_fixed_column = undefined;
        var responsiveHelper_datatable_col_reorder = undefined;
        var breakpointDefinition = {
            tablet: 1024,
            phone: 480
        };
        /* END BASIC */

        /* COLUMN FILTER  */
        var otable = $('#datatable_fixed_column').DataTable({
            //"bFilter": false,
            //"bInfo": false,
            //"bLengthChange": false
            //"bAutoWidth": false,
            //"bPaginate": false,
            //"bStateSave": true // saves sort state using localStorage
            "sDom": "<'dt-toolbar'<'col-xs-12 col-sm-4 hidden-xs'f><'col-sm-4 col-xs-6 hidden-xs'T><'col-sm-4 col-xs-6 hidden-xs'C>r>" +
            "t" +
            "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p>>",
            "oTableTools": {
                "aButtons": [
                    "copy",
                    "xls",
                    "pdf",
                    {
                        "sExtends": "print",
                        "sMessage": "Generated by Open Institute Monitoring System <i>(press Esc to close)</i>"
                    }
                ],
                "sSwfPath": "{{asset('js/plugin/datatables/swf/copy_csv_xls_pdf.swf')}}"
            },
            "iDisplayLength": 20,
            "autoWidth": true,
            "preDrawCallback": function () {
                // Initialize the responsive datatables helper once.
                if (!responsiveHelper_datatable_fixed_column) {
                    responsiveHelper_datatable_fixed_column = new ResponsiveDatatablesHelper($('#datatable_fixed_column'), breakpointDefinition);
                }
            },
            "rowCallback": function (nRow) {
                responsiveHelper_datatable_fixed_column.createExpandIcon(nRow);
            },
            "drawCallback": function (oSettings) {
                responsiveHelper_datatable_fixed_column.respond();
            }

        });
        // custom toolbar

        // Apply the filter
        $("#datatable_fixed_column thead th input[type=text],#datatable_fixed_column thead th select").on('keyup change', function () {

            otable
                    .column($(this).parent().index() + ':visible')
                    .search(this.value)
                    .draw();
        });
        $("#datatable_fixed_column thead th select").bind("DOMSubtreeModified", function () {
            otable
                    .column($(this).parent().index() + ':visible')
                    .search(this.value)
                    .draw();
        });
        /* END COLUMN FILTER */

        /* COLUMN SHOW - HIDE */
        $('#datatable_col_reorder').dataTable({
            "sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6'f><'col-sm-6 col-xs-6 hidden-xs'C>r>" +
            "t" +
            "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p>>",
            "autoWidth": true,
            "preDrawCallback": function () {
                // Initialize the responsive datatables helper once.
                if (!responsiveHelper_datatable_col_reorder) {
                    responsiveHelper_datatable_col_reorder = new ResponsiveDatatablesHelper($('#datatable_col_reorder'), breakpointDefinition);
                }
            },
            "rowCallback": function (nRow) {
                responsiveHelper_datatable_col_reorder.createExpandIcon(nRow);
            },
            "drawCallback": function (oSettings) {
                responsiveHelper_datatable_col_reorder.respond();
            }
        });
        /* END COLUMN SHOW - HIDE */

    };
    var reloadScript = function(){
        loadScript("{{asset('js/plugin/datatables/jquery.dataTables.min.js')}}", function () {
            loadScript("{{asset('js/plugin/datatables/dataTables.colVis.min.js')}}", function () {
                loadScript("{{asset('js/plugin/datatables/dataTables.tableTools.min.js')}}", function () {
                    loadScript("{{asset('js/plugin/datatables/dataTables.bootstrap.min.js')}}", function () {
                        loadScript("{{asset('js/plugin/datatable-responsive/datatables.responsive.min.js')}}", pagefunction);
                    });
                });
            });
        });
    }
</script>
