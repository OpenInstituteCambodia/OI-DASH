<section id="widget-grid" class="">
    <div class="row">
        <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <div class="col-xs-12 col-sm-2 col-md-12-3 col-lg-2">
                <select id="table-value" class="form-control" onchange="loadField()">
                    <option value="">--Table--</option>
                    <?php foreach($tables as $table): ?>
                    <option value="<?php echo e($table->id); ?>"><?php echo e($table->TableName); ?></option>
                    <?php endforeach; ?>
                </select>
                <br>
                <button class="btn btn-primary" onclick='newField()'><i class="fa fa-search-plus"></i> Add New Field</button>
                <span id="spin" class="txt-color-blueDark" href="javascript:void(0);" style="display: none;">
                    <i class="fa fa-gear fa-2x fa-spin"></i>
                </span>
                <br>
                <div class="form-horizontal" id="new-field">

                </div>
            </div>
            <div id="data" class="col-xs-10 col-sm-10 col-md-12-10 col-lg-10">
                <table class="table table-bordered table-responsive">
                    <thead>
                        <tr>
                            <th>Variable(Table)</th>
                            <th>Category</th>
                            <th>Name EN</th>
                            <th>Name KH</th>
                            <th>Variable Type</th>
                            <th>Variable Search Type</th>
                            <th>Use Of Variable</th>
                        </tr>
                    </thead>
                    <tbody id="field">

                    </tbody>
                </table><!--
                <div class="widget-body">
                    <div class="tree">
                        <ul id="field">
                        </ul>
                    </div>

                </div>-->
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
            "<option value='0'>--Choose Variable Type--</option>" +
            "<option value='1'>1-Drop Down</option>" +
            "<option value='2'>2-Input Text OR Number</option>";
    var edfSearchType =
            "<option value='0'>--Choose Variable Search Type--</option>" +
            "<option value='1'>1-Drop Down</option>" +
            "<option value='2'>2-Input Text OR Number</option>";
    var displayField =
            "<option value=''>--Use of Variable--</option>" +
            "<option value='0'>Filter Only</option>" +
            "<option value='1'>Display Only</option>" +
            "<option value='2'>Filter and Display</option>";
    var loadField = function () {
        $("#spin").show();
        $("#field").html("");
        tableID = $("#table-value").val();
        if (tableID.length !== 0) {
            $.ajax({
                url: "<?php echo e(url('system/entity-field')); ?>/" + tableID,
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
                    $("#field").html("");
                    for (i = 0; i < result.length; i++) {
                        $("#field").append(
                                "<tr>" +
                                "<td><span class='label label-danger' onclick='removeField(" + result[i].EntityDefinedFieldListCode + ",this)'><i class='fa fa-lg fa-trash-o'></i></span>" + result[i].EntityDefinedFieldNameInTable + "</td>" +
                                //"<span class='label label-danger' onclick='removeField(" + result[i].EntityDefinedFieldListCode + ",this)'><i class='fa fa-lg fa-trash-o'></i></span>" +                               
                                "<td>" + result[i].EntityDefinedCategoryNameEN + "-" + result[i].EntityDefinedCategoryNameKH + "</td>" +
                                "<td>" + result[i].EntityDefinedFieldListNameEN + "</td>" +
                                "<td>" + result[i].EntityDefinedFieldListNameKH + "</td>" +
                                "<td>" + (result[i].EDFType === 0 ? 'N/A' : result[i].EDFType === 1 ? 'Drop Down' : 'Input Text OR Number') + "</td>" +
                                "<td>" + (result[i].EDFSearchType === 0 ? 'N/A' : result[i].EDFSearchType === 1 ? 'Drop Down' : 'Input Text OR Number') + "</td>" +
                                "<td>" + (result[i].DisplayField === 0 ? 'Filter Only' : result[i].DisplayField === 1 ? 'Display Only' : 'Filter And Display') + "</td>" +
                                "</tr>");
                    }
                    $("#spin").hide();
                }
            });
        }
    };
    var removeNewField = function (obj) {
        $(obj).parent().remove();
    };
    var addNewField = function (data) {

        $("#field").prepend(
                "<tr>" +
                "<td><span class='label label-danger' onclick='removeField(" + data.EntityDefinedFieldListCode + ",this)'><i class='fa fa-lg fa-trash-o'></i></span>" + data.EntityDefinedFieldNameInTable + "</td>" +
                //"<span class='label label-danger' onclick='removeField(" + result[i].EntityDefinedFieldListCode + ",this)'><i class='fa fa-lg fa-trash-o'></i></span>" +                               
                "<td>" + data.EntityDefinedCategoryNameEN + "-" + data.EntityDefinedCategoryNameKH + "</td>" +
                "<td>" + data.EntityDefinedFiledListNameEN + "</td>" +
                "<td>" + data.EntityDefinedFiledListNameKH + "</td>" +
                "<td>" + (data.EDFType === '0' ? 'N/A' : data.EDFType === '1' ? 'Drop Down' : 'Input Text OR Number') + "</td>" +
                "<td>" + (data.EDFSearchType === '0' ? 'N/A' : data.EDFSearchType === '1' ? 'Drop Down' : 'Input Text OR Number') + "</td>" +
                "<td>" + (data.DisplayField === '0' ? 'Filter Only' : data.DisplayField === '1' ? 'Display Only' : 'Filter And Display') + "</td>" +
                "</tr>");

    };
    var saveNewField = function (obj) {
        var fieldInTable = $(obj).siblings(".select-field-table").children("select").val();
        var catCode = $(obj).siblings(".cat").children("select").val();
        var fieldNameLatin = $(obj).siblings(".field-en").children("input").val();
        var fieldNameKh = $(obj).siblings(".field-kh").children("input").val();
        var edfType = $(obj).siblings(".edf-type").children("select").val();
        var edfSearchType = $(obj).siblings(".edf-search-type").children("select").val();
        var displayField = $(obj).siblings(".display-field").children("select").val();
        // loading();
        $(obj).parent().append("<span class=\"label label-info txt-color-blueDark\" href=\"javascript:void(0);\"><i class=\"fa fa-gear fa-2x fa-spin\"></i></span>");
        $.ajax({
            url: "<?php echo e(url('system/entity-field')); ?>",
            type: 'POST',
            data: {
                tableID: tableID,
                fieldInTable: fieldInTable,
                catCode: catCode,
                fieldNameLatin: fieldNameLatin,
                fieldNameKh: fieldNameKh,
                edfType: edfType,
                edfSearchType: edfSearchType,
                displayField: displayField},
            success: function (result) {
                addNewField(result);
                $(obj).parent().remove();
            },
            error: function () {
                alert("Fail to add new field");
            }
        });
    };

    var newField = function () {
        $("#new-field").prepend("<div>"+
                "<div class='form-group col-sm-12 select-field-table'><select class='form-control input-sm'>" + columnOption + "</select></div>" +
                "<div class='form-group col-sm-12 cat'><select class='form-control input-sm'>" + categoryOption + "</select></div>" +
                "<div class='form-group col-sm-12 field-en'><input class='form-control input-sm' type='text' placeholder='Enter Variable Name In English'></div>" +
                "<div class='form-group col-sm-12 field-kh'><input class='form-control input-sm' type='text' placeholder='Enter Variable Name In Khmer'></div>" +
                "<div class='form-group col-sm-12 edf-type'><select class='form-control input-sm'>" + edfType + "</select></div>" +
                "<div class='form-group col-sm-12 edf-search-type'><select class='form-control input-sm'>" + edfSearchType + "</select></div>" +
                "<div class='form-group col-sm-12 display-field'><select class='form-control input-sm'>" + displayField + "</select></div>" +
                "<button class='btn btn-info' onclick='saveNewField(this)'><i class='fa fa-lg fa-save'></i></button>"+
                "<button class='btn btn-danger' onclick='removeNewField(this)'><i class='fa fa-lg fa-trash-o'></i></button>"+
                "</div>");
    };
    var removeField = function (fieldID, obj) {
        if (confirm("Are You sure to delete!")) {
            $("#spin").show();
            $.ajax({
                url: "<?php echo e(url('system/entity-field')); ?>/" + fieldID,
                type: 'DELETE',
                success: function (result) {
                    $(obj).parent().parent().remove();
                    $("#spin").hide();
                    alert("Your variable has been deleted!");
                },
                error: function () {
                    alert("Unable to remove variable!");
                }
            });
        }
    };
    var editField = function (fieldID, obj) {

    };

    var getColumnAndCategory = function (tableID) {
        var results;
        $.ajax({
            url: "<?php echo e(url('system/entity-field/getColumnAndCategoryName')); ?>/" + tableID,
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
         root.console.log("✔ Calendar destroyed");
         } 
         
         For common instances, such as Jarviswidgets, Google maps, and Datatables, are automatically destroyed through the app.js loadURL mechanic
         
         */


    }

    // end destroy

    // run pagefunction on load

    pagefunction();

</script>