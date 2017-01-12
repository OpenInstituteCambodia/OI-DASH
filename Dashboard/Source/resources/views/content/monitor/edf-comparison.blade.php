<!-- InformationController@compareEDF -->
<section id="widget-grid" class="">
    <button class="btn btn-primary" onclick="print()">{{ trans('button.print') }}</button></p>

    <?php
        $i = 0;
        $edf = [];
    ?>

    <div id="mainEntityCompared" class="col-md-12" style="padding: 0;">
        @if(count($rows) < 2)
            @foreach($rows as $row)
                <?php $edf[$i] = []; ?>
                <div class="col-sm-8" style="margin-bottom: 45px">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            @if (session()->get('locale'))
                                @if (session()->get('locale') == 'en')
                                    {{$table->TableNameEN}}
                                @elseif (session()->get('locale') == 'km')
                                    {{$table->TableNameKH}}
                                @endif
                            @else
                                {{$table->TableNameKH}}
                            @endif
                        </div>
                        <div class="panel-body" style="padding: 15px 15px 0 15px">
                            <div class="form-horizontal" role="form">
                                <div class="col-sm-6">
                                    @foreach($colHeaders as $header)
                                        <div class="form-group">
                                            <label for="field" style="font-weight: bold;">{{$header->EntityDefinedFieldListName}}</label>
                                        </div>
                                    @endforeach
                                </div>
                                <div class="col-sm-6">
                                    @foreach($row as $col)
                                        <?php $edf[$i][] = $col; ?>
                                        <div class="form-group">
                                            <label for="field">: {{$col}}</label>
                                        </div>
                                    @endforeach
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <?php $i++; ?>
            @endforeach
        @else
            @foreach($rows as $row)
                <?php $edf[$i] = []; ?>
                <div id="entityCompared" class="col-sm-5 school-compare">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            @if (session()->get('locale'))
                                @if (session()->get('locale') == 'en')
                                    {{$table->TableNameEN}}
                                @elseif (session()->get('locale') == 'km')
                                    {{$table->TableNameKH}}
                                @endif
                            @else
                                {{$table->TableNameKH}}
                            @endif
                        </div>
                        <div class="panel-body" style="padding: 15px 5px 0 5px;">
                            <div class="form-horizontal" role="form">
                                <div class="col-sm-6">
                                    @foreach($colHeaders as $header)
                                        <div class="form-group">
                                            <label id="header-label" for="field"
                                                   style="font-weight: bold; font-size: 12px">{{$header->EntityDefinedFieldListName}}</label>
                                        </div>
                                    @endforeach
                                </div>
                                <div class="col-sm-6" style="padding-left: 0; padding-right: 0">
                                    @foreach($row as $col)
                                        <?php $edf[$i][] = $col; ?>
                                        <div class="form-group">
                                            <label for="field">: {{$col}}</label>
                                        </div>
                                    @endforeach
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <?php $i++; ?>
            @endforeach

            @if(count($edf) > 1)
                <div id="varianceCol" class="col-sm-1 variance">
                    <div class="panel panel-primary">
                        <div class="panel-heading" style="padding: 10px 0; text-align: center;">
                            {{ trans('information_content.variance') }}
                        </div>
                        <div class="panel-body" style="padding-bottom: 0; padding-right: 0; padding-left: 0; text-align: center;">
                            <div class="form-horizontal" role="form">
                                <div class="col-sm-12">
                                    <?php
                                    for ($i = 0; $i < count($edf[0]); $i++) {
                                        $display = "-";
                                        if (is_numeric($edf[0][$i])) {
                                            $variance = $edf[0][$i] == 0 ? 1 : (float)$edf[1][$i] / (float)$edf[0][$i];
                                            $k1 = 0.05;
                                            $k2 = 0.15;
                                            $k3 = 0.25;
                                            if ($variance < 1 - $k3) {
                                                $display = "<i id='pointer' class='fa fa-arrow-down'></i><i class='fa fa-arrow-down'></i><i class='fa fa-arrow-down'></i>";
                                            } else if ($variance < 1 - $k2) {
                                                $display = "<i id='pointer' class='fa fa-arrow-down'></i><i class='fa fa-arrow-down'></i>";
                                            } else if ($variance < 1 - $k1) {
                                                $display = "<i id='pointer' class='fa fa-arrow-down'></i>";
                                            } else if ($variance > 1 + $k3) {
                                                $display = "<i id='pointer' class='fa fa-arrow-up'></i><i class='fa fa-arrow-up'></i><i class='fa fa-arrow-up'></i>";
                                            } else if ($variance > 1 + $k2) {
                                                $display = "<i id='pointer' class='fa fa-arrow-up'></i><i class='fa fa-arrow-up'></i>";
                                            } else if ($variance > 1 + $k1) {
                                                $display = "<i id='pointer' class='fa fa-arrow-up'></i>";
                                            }
                                        }
                                        echo "<div class='form-group'><label for='field'>$display</label></div>";
                                    }
                                    ?>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            @endif
        @endif
    </div>
</section>

<script type="text/javascript">
    function addCss(myForm, fileName) {
        var link = document.createElement('link');

        link.type = 'text/css';
        link.rel = 'stylesheet';
        link.href = fileName;

        myForm.document.head.appendChild(link);
    }

    function print() {
        var printForm = window.open('', '_blank');

        // var font_awesome_css = document.createElement('font_awesome_css');
        // font_awesome_css = 'text/css';
        // font_awesome_css.rel = 'stylesheet';
        // font_awesome_css.href = '<?php echo asset("css/font-awesome.min.css") ?>';
        //
        // var bootstrap_css = document.createElement('bootstrap_css');
        // bootstrap_css = 'text/css';
        // bootstrap_css.rel = 'stylesheet';
        // bootstrap_css.href = '<?php echo asset("css/bootstrap.min.css") ?>';
        //
        // printForm.document.head.appendChild(font_awesome_css);
        // printForm.document.head.appendChild(bootstrap_css);

        var css = '#header-label { font-size:13px !important;} #entityCompared { width:490px; } #pointer { padding-bottom:5px }',
        style = document.createElement('style');

        style.type = 'text/css';
        if (style.styleSheet){
          style.styleSheet.cssText = css;
        } else {
          style.appendChild(document.createTextNode(css));
        }

        printForm.document.head.appendChild(style);

        addCss(printForm, '<?php echo asset("css/font-awesome.min.css") ?>');

        addCss(printForm, '<?php echo asset("css/bootstrap.min.css") ?>');

        addCss(printForm, '<?php echo asset("css/custom_style.css") ?>');

        var schoolHTML = document.getElementById("mainEntityCompared").innerHTML;

        // if (!varianceHTML) {
        //     var varianceHTML = document.getElementById("varianceCol").innerHTML;
        // }

        var $title = '<h3 style="text-align:center;font-size:25px;margin-bottom:25px">{{ trans('information_content.report-compare-title') }}</h3>\n';
        $title += schoolHTML;
        printForm.document.body.innerHTML = $title;

        setTimeout(function () {
            // Automatic Open Print Diaglog
            printForm.print();

            // Close Windows after print completed
            // printForm.close();
        }, 300);


        // printForm.close();
    }
</script>

