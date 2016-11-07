<section id="widget-grid" class="">
    <form class="excel-export smart-form" method="post" enctype="multipart/form-data" action='system/edf-export/process'>
        <input type="hidden" name="_token" value="<?php echo e(csrf_token()); ?>">
        <fieldset>	
            <section class="col col-4">
                <label class="select">
                    <select class="form-control" name="table-name">
                        <option value="">--Select--</option>
                        <?php foreach($tables as $table): ?>
                        <option value="<?php echo e($table->id); ?>"><?php echo e($table->TableNameEN); ?></option>
                        <?php endforeach; ?>
                    </select>
                </label>
            </section>
            <div id="loading" style="display:none" class="col-sm-1"><i class="fa fa-2x fa-fw fa-lg fa-spin fa-gear"></i></div>
        </fieldset>
        <footer>
            <button type="submit" class="btn btn-primary">Submit</button>
        </footer>
    </form>

</section>

<script type="text/javascript">

    pageSetUp();

    var pageFunction = function () {

    };

//    var process = function () {
//        $("#loading").show();
//        $(".excel-export").ajaxForm({
//            success: function (result) {
//                $("#loading").hide();
//                window.location = result;
//            },
//            error: function (result) {
//                $("#loading").hide();
//                alert("Fail to export file");
//            }
//        });
//    };
    loadScript('js/libs/jquery-form-3.51.js', pageFunction);

    $(document).ready(function () {

    });

</script>