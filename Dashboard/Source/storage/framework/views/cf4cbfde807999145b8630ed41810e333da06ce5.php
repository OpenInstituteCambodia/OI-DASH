<section id="widget-grid" class="">
    <div class="row">
        <article class="col-sm-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Form
                </div>
                <div class="panel-body">
                    <form class="excel-upload smart-form" method="post" enctype="multipart/form-data" action='system/edf-import/process'>
                        <fieldset>	
                            <section>
                                <label class="label">File input</label>
                                <label for="file" class="col-sm-11 input input-file state-success">
                                    <div class="button"><input type="file" name="file" onchange="this.parentNode.nextSibling.value = this.value">Browse</div><input type="text" readonly>
                                </label>
                                <div id="loading" style="display:none" class="col-sm-1"><i class="fa fa-2x fa-fw fa-lg fa-spin fa-gear"></i></div>
                            </section>
                        </fieldset>
                        <footer>
                            <button type="submit" class="btn btn-primary" onclick="process()">Submit</button>
                        </footer>
                    </form>
                </div>
            </div>
        </article>
    </div>
</section>

<script type="text/javascript">
    pageSetUp();

    var pageFunction = function () {

    };

    var process = function () {
        $("#loading").show();
        $(".excel-upload").ajaxForm({
            success: function (result) {
                $("#loading").hide();
                alert("Import Success");
            },
            error: function (result) {
                $("#loading").hide();
                alert("Fail to import");
            }
        });
    };
    loadScript('js/libs/jquery-form-3.51.js', pageFunction);

    $(document).ready(function () {

    });

</script>
