<section id="widget-grid" class="">
    <div class="row">
        <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <div class="col-xs-12 col-sm-3 col-md-12-3 col-lg-3">
                <select id="table-value" onchange="loadField()">
                    <option value="">--Table--</option>
                    @foreach($tables as $table)
                    <option value="{{$table->id}}">{{$table->TableName}}</option>
                    @endforeach
                </select>
            </div>
            <div id="data" class="col-xs-12 col-sm-9 col-md-12-9 col-lg-9">
                <span id="spin" class="txt-color-blueDark" href="javascript:void(0);" style="display: none;">
                    <i class="fa fa-gear fa-2x fa-spin"></i>
                </span>
                <div class="widget-body">
                    <div class="tree">
                        <ul id="field">
                        </ul>
                    </div>

                </div>
            </div>
        </article>
    </div>

</section>
<style>
    a:hover{
        cursor:pointer;
    }
    #field span:hover{
        cursor:pointer;
    }
</style>
<script>
    var columns;
    var categories;
    var tableID;
    var columnOption;
    var categoryOption;
    var edfType = 
            "<option value='0'>0-No Type</option>" +
            "<option value='1'>1-Drop Down</option>" +
            "<option value='2'>2-Input Text</option>";
    var addNew = "<li><a onclick='newField();'><span class='label label-primary'><i class='fa fa-lg fa-plus'></i> Add New </span><a></li>";
    var loadField = function () {
        $("#spin").show();
        $("#field").html("");
        tableID = $("#table-value").val();
        if (tableID.length !== 0) {
            $.ajax({
                url: "{{url('system/entity-field')}}/" + tableID,
                success: function (result) {
                    columnOption = "";
                    categoryOption = "";
                    var ColCat = getColumnAndCategory(tableID);
                    columns = ColCat.columns;
                    categories = ColCat.categories;
                    for (j = 0; j < columns.length; j++) {
                        columnOption += "<option value='" + columns[j].COLUMN_NAME + "'>" + columns[j].COLUMN_NAME + "</option>";
                    }
                    for (j = 0; j < categories.length; j++) {
                        categoryOption += "<option value='" + categories[j].EntityDefinedCategoryCode + "'>" + categories[j].EntityDefinedCategoryName + "</option>";
                    }
                    $("#field").html(addNew);
                    for (i = 0; i < result.length; i++) {
                        $("#field").append(
                                "<li>" +
                                "<span class='label label-primary'><i class='fa fa-lg fa-plus-circle'></i> Field Name In Table : " + result[i].EntityDefinedFieldNameInTable + "</span>" +
                                "<span class='label label-danger' onclick='removeField(" + result[i].EntityDefinedFieldListCode + ",this)'><i class='fa fa-lg fa-trash-o'></i></span>" +
                                "<span class='label label-primary' onclick='editField(" + result[i].EntityDefinedFieldListCode + ",this)'><i class='fa fa-lg fa-pencil'></i></span>" +
                                "<ul>" +
                                "<li>" +
                                "<span class='label label-success'><i class='fa fa-lg fa-minus-circle'></i> Category EN : " + result[i].EntityDefinedCategoryNameEN + "</span>" +
                                "</li>" +
                                "<li>" +
                                "<span class='label label-success'><i class='fa fa-lg fa-minus-circle'></i> Category KH : " + result[i].EntityDefinedCategoryNameKH + "</span>" +
                                "</li>" +
                                "<li>" +
                                "<span class='label label-success'><i class='fa fa-lg fa-minus-circle'></i> Field Name EN : " + result[i].EntityDefinedFieldListNameEN + "</span>" +
                                "</li>" +
                                "<li>" +
                                "<span class='label label-success'><i class='fa fa-lg fa-minus-circle'></i> Field Name KH : " + result[i].EntityDefinedFieldListNameKH + "</span>" +
                                "</li>" +
                                "<li>" +
                                "<span class='label label-success'><i class='fa fa-lg fa-minus-circle'></i> EDF Type : " + result[i].EDFType + "</span>" +
                                "</li>" +
                                "<li>" +
                                "<span class='label label-success'><i class='fa fa-lg fa-minus-circle'></i> EDF Search Type : " + result[i].EDFSearchType + "</span>" +
                                "</li>" +
                                "</ul>" +
                                "</li>");
                    }
                    $("#spin").hide();
                }
            });
        }
    };
    var removeNewField = function (obj) {
        $(obj).parent().parent().parent().remove();
    };
    var addNewField = function (obj, data) {

        $(obj).parent().parent().parent().html(
                "<span class='label label-primary'><i class='fa fa-lg fa-plus-circle'></i> " + data.EntityDefinedFieldNameInTable + "</span><span class='label label-danger' onclick='removeField(" + data.EntityDefinedFieldListCode + ",this)'><i class='fa fa-lg fa-trash-o'></i></span>" +
                "<ul>" +
                "<li>" +
                "<span class='label label-success'><i class='fa fa-lg fa-minus-circle'></i> Category EN: " + data.EntityDefinedCategoryNameEN + "</span>" +
                "</li>" +
                "<li>" +
                "<span class='label label-success'><i class='fa fa-lg fa-minus-circle'></i> Category KH: " + data.EntityDefinedCategoryNameKH + "</span>" +
                "</li>" +
                "<li>" +
                "<span class='label label-success'><i class='fa fa-lg fa-minus-circle'></i> Field Name EN: " + data.EntityDefinedFiledListNameEN + "</span>" +
                "</li>" +
                "<li>" +
                "<span class='label label-success'><i class='fa fa-lg fa-minus-circle'></i> Field Name KH: " + data.EntityDefinedFiledListNameKH + "</span>" +
                "</li>" +
                "</ul>"
                );

    };
    var saveNewField = function (obj) {
        var fieldInTable = $(obj).parent().parent().siblings(".select-field-table").children("select").val();
        var catCode = $(obj).parent().siblings(".cat").children("div").children("select").val();
        var fieldNameLatin = $(obj).parent().siblings(".field-en").children("div").children("input").val();
        var fieldNameKh = $(obj).parent().siblings(".field-kh").children("div").children("input").val();
        var udftype = $(obj).parent().siblings(".field-kh").children("div").children("input").val();
        var udfSearchType = $(obj).parent().siblings(".udf-search-type").children("div").children("input").val();
        // loading();
        $(obj).parent().append("<span class=\"label label-info txt-color-blueDark\" href=\"javascript:void(0);\"><i class=\"fa fa-gear fa-2x fa-spin\"></i></span>");
        $.ajax({
            url: "{{url('system/entity-field')}}",
            type: 'POST',
            data: {
                tableID: tableID,
                fieldInTable: fieldInTable,
                catCode: catCode,
                fieldNameLatin: fieldNameLatin,
                fieldNameKh: fieldNameKh},
            success: function (result) {
                addNewField(obj, result);
            }
        });
    };

    var newField = function () {
        $("#field").append(
                "<li>" +
                "<div class='col-sm-3 select-field-table'><select class='form-control input-sm'>" + columnOption + "</select></div><br>" +
                "<ul>" +
                "<li class='cat'>" +
                "<div class='col-sm-3'><select class='form-control input-sm'>" + categoryOption + "</select></div><br><br>" +
                "</li>" +
                "<li class='field-en'>" +
                "<div class='col-sm-3'><input class='form-control input-sm' type='text' placeholder='Enter Field Name In English'></div><br><br>" +
                "</li>" +
                "<li class='field-kh'>" +
                "<div class='col-sm-3'><input class='form-control input-sm' type='text' placeholder='Enter Field Name In Khmer'></div><br><br>" +
                "</li>" +
                "<li class='edf-type'>" +
                "<div class='col-sm-3'><select class='form-control input-sm'>" + edfType + "</select></div><br><br>" +
                "</li>" +
                "<li class='edf-search-type'>" +
                "<div class='col-sm-3'><select class='form-control input-sm'>" + edfType + "</select></div><br><br>" +
                "</li>" +
                "<li>" +
                "<span class='label label-primary' onclick='saveNewField(this)'><i class='fa fa-lg fa-save'></i></span> <span class='label label-danger' onclick='removeNewField(this)'><i class='fa fa-lg fa-trash-o'></i></span>" +
                "</li>" +
                "</ul>" +
                "</li>");

    };
    var removeField = function (fieldID, obj) {
        if (confirm("Are You sure to delete!")) {
            $(obj).parent().append("<span class=\"label label-info txt-color-blueDark\" href=\"javascript:void(0);\"><i class=\"fa fa-gear fa-2x fa-spin\"></i></span>");
            $.ajax({
                url: "{{url('system/entity-field')}}/" + fieldID,
                type: 'DELETE',
                success: function (result) {
                    $(obj).parent().remove();
                }
            });
        }
    };
    var editField = function (fieldID, obj) {

    };

    var getColumnAndCategory = function (tableID) {
        var results;
        $.ajax({
            url: "{{url('system/entity-field/getColumnAndCategoryName')}}/" + tableID,
            async: false,
            success: function (result) {
                results = result;
            }
        });
        return results;
    };
//    for (i = 0; i < result.length; i++) {
//        $("#field").append(
//                "<li>" +
//                "<span class='label label-primary'><i class='fa fa-lg fa-plus-circle'></i>" + result[i].EntityDefinedFiledListName + "</span><span class='label label-danger'><i class='fa fa-lg fa-trash-o'></i></span><span class='label label-primary'><i class='fa fa-lg fa-save'></i></span>" +
//                "<ul>" +
//                "<li>" +
//                "<lable class='label label-primary'>Categroy:</lable>" +
//                "<div class='col-sm-3'><input class='form-control input-sm' type='text' value='" + result[i].EntityDefinedFieldListCategory + "'></div>" +
//                "</li><br>" +
//                "<li>" +
//                "<lable class='label label-primary'>Field Name In Table:</lable>" +
//                "<div class='col-sm-3'><select id='select-" + i + "' class='form-control input-sm'></select></div>" +
//                "</li>" +
//                "</ul>" +
//                "</li>");
//        $("#select-" + i).html("");
//        for (j = 0; j < columns.length; j++) {
//            $("#select-" + i).append("<option value='" + columns[j].COLUMN_NAME + "'>" + columns[j].COLUMN_NAME + "</option>");
//        }
//    }
</script>

<script type="text/javascript">
    /* DO NOT REMOVE : GLOBAL FUNCTIONS!
     *
     * pageSetUp(); WILL CALL THE FOLLOWING FUNCTIONS
     *
     * // activate tooltips
     * $("[rel=tooltip]").tooltip();
     *
     * // activate popovers
     * $("[rel=popover]").popover();
     *
     * // activate popovers with hover states
     * $("[rel=popover-hover]").popover({ trigger: "hover" });
     *
     * // activate inline charts
     * runAllCharts();
     *
     * // setup widgets
     * setup_widgets_desktop();
     *
     * // run form elements
     * runAllForms();
     *
     ********************************
     *
     * pageSetUp() is needed whenever you load a page.
     * It initializes and checks for all basic elements of the page
     * and makes rendering easier.
     *
     */

    pageSetUp();
    // PAGE RELATED SCRIPTS
    // pagefunction

    var pagefunction = function () {

        loadScript("js/plugin/bootstraptree/bootstrap-tree.min.js");
    };
    // end pagefunction

    // destroy generated instances 
    // pagedestroy is called automatically before loading a new page
    // only usable in AJAX version!

    var pagedestroy = function () {

        /*
         Example below:
         
         $("#calednar").fullCalendar( 'destroy' );
         if (debugState){
         root.console.log("? Calendar destroyed");
         } 
         
         For common instances, such as Jarviswidgets, Google maps, and Datatables, are automatically destroyed through the app.js loadURL mechanic
         
         */


    }

    // end destroy

    // run pagefunction on load

    pagefunction();

</script>