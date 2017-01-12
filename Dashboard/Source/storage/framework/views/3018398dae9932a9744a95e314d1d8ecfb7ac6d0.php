<!-- InformationController@compareEDF -->
<section id="widget-grid" class="">
  <?php
    $i = 0;
    $edf = [];
  ?>
  <?php foreach($rows as $row): ?>
    <?php $edf[$i] = []; ?>

    <div class="col-sm-7" style="margin-bottom: 45px">
      <div class="panel panel-primary">
        <div class="panel-heading">
          <?php if(session()->get('locale')): ?>
            <?php if(session()->get('locale') == 'en'): ?>
              <?php echo e($table->TableNameEN); ?>

            <?php elseif(session()->get('locale') == 'km'): ?>
              <?php echo e($table->TableNameKH); ?>

            <?php endif; ?>
          <?php else: ?>
            <?php echo e($table->TableNameKH); ?>

          <?php endif; ?>
        </div>
        <div class="panel-body" style="padding: 15px 15px 0 15px">
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
                <?php $edf[$i][] = $col; ?>
                <div class="form-group">
                  <label for="field">: <?php echo e($col); ?></label>
                </div>
              <?php endforeach; ?>
            </div>
          </div>
        </div>
      </div>
    </div>
    <?php $i++; ?>
  <?php endforeach; ?>

  <?php if(count($edf) > 1): ?>
    <div class="col-sm-5">
      <div class="panel panel-primary">
        <div class="panel-heading">
          <?php echo e(trans('information_content.variance')); ?>

        </div>
        <div class="panel-body">
          <div class="form-horizontal" role="form">
            <div class="col-sm-12">
              <?php
                for ($i = 0; $i < count($edf[0]); $i++) {
                  $display = "-";
                  if (is_numeric($edf[0][$i])) {
                      $variance=$edf[0][$i]==0? 1:(float) $edf[1][$i] / (float)$edf[0][$i];
                      $k1 = 0.05;
                      $k2 = 0.15;
                      $k3 = 0.25;
                      if ($variance < 1 - $k3) {
                          $display = "<i class='fa fa-arrow-down'></i><i class='fa fa-arrow-down'></i><i class='fa fa-arrow-down'></i>";
                      } else if ($variance < 1 - $k2) {
                          $display = "<i class='fa fa-arrow-down'></i><i class='fa fa-arrow-down'></i>";
                      } else if ($variance < 1 - $k1) {
                          $display = "<i class='fa fa-arrow-down'></i>";
                      } else if ($variance > 1 + $k3) {
                          $display = "<i class='fa fa-arrow-up'></i><i class='fa fa-arrow-up'></i><i class='fa fa-arrow-up'></i>";
                      } else if ($variance > 1 + $k2) {
                          $display = "<i class='fa fa-arrow-up'></i><i class='fa fa-arrow-up'></i>";
                      } else if ($variance > 1 + $k1) {
                          $display = "<i class='fa fa-arrow-up'></i>";
                      }
                  }
                  echo "<div class='form-group'>
                          <label for='var'>$display</label>
                        </div>";
                }
              ?>
            </div>
          </div>
        </div>
      </div>
    </div>
  <?php endif; ?>

    
</section>
