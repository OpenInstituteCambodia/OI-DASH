<section id="widget-grid" class="">
    <?php
    $i = 0;
    $edf = [];
    ?>
    <?php foreach($rows as $row): ?>
    <?php
    $edf[$i] = [];
    ?>
    <div class="col-sm-5">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <?php echo e($table->TableNameEN); ?>

            </div>
            <div class="panel-body">
                <div class="form-horizontal" role="form">
                    <div class="col-sm-6">
                        <?php foreach($colHeaders as $header): ?>
                        <div class="form-group">
                            <label for="field" style="font-weight: bold;"><?php echo e($header->EntityDefinedFieldListName); ?></label>
                        </div>
                        <?php endforeach; ?>
                    </div>
                    <div class="col-sm-6">
                        <?php foreach($row as $col): ?>
                        <?php
                        $edf[$i][] = $col;
                        ?>
                        <div class="form-group">
                            <label for="field">:<?php echo e($col); ?></label>
                        </div>
                        <?php endforeach; ?>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <?php
    $i++;
    ?>
    <?php endforeach; ?>
    <div class="col-sm-2">
        <div class="panel panel-primary">
            <div class="panel-heading">
                Variance
            </div>
        </div>
    </div>
</section>