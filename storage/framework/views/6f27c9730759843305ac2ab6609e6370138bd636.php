<!-- row -->
<div class="row">
<!-- NEW WIDGET START -->
    <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
        <!-- end widget -->

        <!-- Widget ID (each widget will need unique ID)-->
        <!-- Widget ID (each widget will need unique ID)-->
        <div class="jarviswidget jarviswidget-color-blueLight" id="wid-id-2" data-widget-editbutton="false">
            <!-- widget options:
            usage: <div class="jarviswidget" id="wid-id-0" data-widget-editbutton="false">

            data-widget-colorbutton="false"
            data-widget-editbutton="false"
            data-widget-togglebutton="false"
            data-widget-deletebutton="false"
            data-widget-fullscreenbutton="false"
            data-widget-custombutton="false"
            data-widget-collapsed="true"
            data-widget-sortable="false"

            -->
            <header style="height: 50px">
                <span class="widget-icon"> <i class="fa fa-users fa-2x" style="padding: 10px 0 0 10px"></i> </span>
                <h1 style="padding-left: 50px">User Management
                    <a href="#users/create" class="pull-right" style="padding-right: 10px ;!important; cursor: pointer;"><i class="fa fa-plus "></i></a>
                </h1>
                <div class="clearfix"></div>
            </header>

            <!-- widget div-->
            <?php echo $__env->yieldContent('content'); ?>
            <!-- end widget div -->
        <!-- end widget -->

    </article>
<!-- WIDGET END -->

</div>
<!-- end row -->
<style>
    tr{
        cursor: pointer;
    }
    tr:hover{
        color: #0066cc;
    }
</style>

<script type="text/javascript">
    pageSetUp();

    $(document).ready(function() {
        // Call function reloadScript()
        reloadScript();

        var pagefunction = function () {
//            console.log("cleared");

            /* BASIC ;*/
            var responsiveHelper_datatable_fixed_column = undefined;
            var responsiveHelper_datatable_col_reorder = undefined;

            var breakpointDefinition = {
                tablet: 1024,
                phone: 480
            };

            var otable = $('#datatable_fixed_column').DataTable({
                "sDom": "<'dt-toolbar'<'col-xs-12 col-sm-4 hidden-xs'f><'col-sm-4 col-xs-6 hidden-xs'T><'col-sm-4 col-xs-6 hidden-xs'C>r>" +
                "t" + "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p>>",
                "oTableTools": {
                    "aButtons": [],
                    "sSwfPath": "<?php echo e(asset('js/plugin/datatables/swf/copy_csv_xls_pdf.swf')); ?>"
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
            $("#datatable_fixed_column thead th input[type=text], #datatable_fixed_column thead th select").on('keyup change', function () {
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

        };

         function reloadScript() {
            loadScript("<?php echo e(asset('js/plugin/datatables/jquery.dataTables.min.js')); ?>", function () {
                loadScript("<?php echo e(asset('js/plugin/datatables/dataTables.colVis.min.js')); ?>", function () {
                    loadScript("<?php echo e(asset('js/plugin/datatables/dataTables.tableTools.min.js')); ?>", function () {
                        loadScript("<?php echo e(asset('js/plugin/datatables/dataTables.bootstrap.min.js')); ?>", function () {
                            loadScript("<?php echo e(asset('js/plugin/datatable-responsive/datatables.responsive.min.js')); ?>", pagefunction);
                        });
                    });
                });
            });
        }

    });

</script>
