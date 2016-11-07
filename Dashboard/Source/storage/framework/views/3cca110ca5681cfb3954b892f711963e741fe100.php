<!-- row -->
<div class="row">
  <!-- NEW WIDGET START -->
  <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
    <!-- end widget -->

    <!-- Widget ID (each widget will need unique ID)-->
    <div class="jarviswidget jarviswidget-color-greenLight" id="wid-id-1" data-widget-sortable="false" data-widget-togglebutton="false" data-widget-deletebutton="false" data-widget-editbutton="false">
      <header>
          <span class="widget-icon"> <i class="fa fa-table"></i> </span>
          <h2><?php echo e(trans('aggregate_content.result.aggregate-result')); ?></h2>
      </header>

      <!-- widget div-->
      <div>
        <!-- widget edit box -->
        <div class="jarviswidget-editbox">
            <!-- This area used as dropdown edit box -->

        </div>
        <!-- end widget edit box -->

        <!-- widget content -->
        <div class="widget-body no-padding">
            <!--<h2>Nestable List #1</h2>-->

            <div class="dd col-sm-12" id="nestable">
                <?php if($gp_type === 'country'): ?>
                    <ol class="dd-list">
                        <li class="dd-item">
                            <div class="dd-handle">
                              <?php echo e(trans('aggregate_content.geography.country')); ?>

                              <em class="badge pull-right bg-color-purple">
                                  <?php echo e($country->NumberOfEntity); ?>

                              </em>
                            </div>

                            <?php if(strcmp($gp_aggType, 'country') !== 0): ?>
                              <?php if(session()->get('locale')): ?>
                                <?php if(session()->get('locale') == 'en'): ?>
                                  <ol class="dd-list">
                                    <?php foreach($country->provinces as $province): ?>
                                      <li class="dd-item">
                                        <h4><b><?php echo e(trans('aggregate_content.geography.province')); ?></b></h4>
                                      </li>
                                      <li class="dd-item">
                                        <div class="dd-handle">
                                          <?php echo e($province->ProvinceName); ?>

                                          <em class="badge pull-right bg-color-purple">
                                              <?php echo e($province->NumberOfEntity); ?>

                                          </em>
                                        </div>
                                        <?php if(strcmp($gp_aggType, 'province') === 0): ?>
                                          <?php continue; ?>
                                        <?php endif; ?>
                                        <ol class="dd-list">
                                            <?php foreach($province->districts as $district): ?>
                                            <li class="dd-item">
                                                <h4><b><?php echo e(trans('aggregate_content.geography.district')); ?></b></h4>
                                            </li>
                                            <li class="dd-item">
                                                <div class="dd-handle">
                                                    <?php echo e($district->DistrictName); ?>

                                                    <em class="badge pull-right bg-color-purple">
                                                        <?php echo e($district->NumberOfEntity); ?>

                                                    </em>
                                                </div>
                                                <?php if(strcmp($gp_aggType, 'district') === 0): ?>
                                                <?php continue; ?>
                                                <?php endif; ?>
                                                <ol class="dd-list">
                                                    <li class="dd-item">
                                                        <h4><b><?php echo e(trans('aggregate_content.geography.commune')); ?></b></h4>
                                                    </li>
                                                    <?php foreach($district->communes as $commune): ?>
                                                    <li class="dd-item">
                                                        <div class="dd-handle">
                                                            <?php echo e($commune->CommuneName); ?>

                                                            <em class="badge pull-right bg-color-purple">
                                                                <?php echo e($commune->NumberOfEntity); ?>

                                                            </em>
                                                        </div>
                                                    </li>
                                                    <?php endforeach; ?>
                                                </ol>
                                            </li>
                                            <?php endforeach; ?>
                                        </ol>
                                      </li>
                                    <?php endforeach; ?>
                                  </ol>
                                <?php elseif(session()->get('locale') == 'km'): ?>
                                  <ol class="dd-list">
                                    <?php foreach($country->provinces as $province): ?>
                                      <li class="dd-item">
                                        <h4><b><?php echo e(trans('aggregate_content.geography.province')); ?></b></h4>
                                      </li>
                                      <li class="dd-item">
                                        <div class="dd-handle">
                                          <?php echo e($province->ProvinceKhmerName); ?>

                                          <em class="badge pull-right bg-color-purple">
                                              <?php echo e($province->NumberOfEntity); ?>

                                          </em>
                                        </div>
                                        <?php if(strcmp($gp_aggType, 'province') === 0): ?>
                                          <?php continue; ?>
                                        <?php endif; ?>
                                        <ol class="dd-list">
                                            <?php foreach($province->districts as $district): ?>
                                            <li class="dd-item">
                                                <h4><b><?php echo e(trans('aggregate_content.geography.district')); ?></b></h4>
                                            </li>
                                            <li class="dd-item">
                                                <div class="dd-handle">
                                                    <?php echo e($district->DistrictKhmerName); ?>

                                                    <em class="badge pull-right bg-color-purple">
                                                        <?php echo e($district->NumberOfEntity); ?>

                                                    </em>
                                                </div>
                                                <?php if(strcmp($gp_aggType, 'district') === 0): ?>
                                                <?php continue; ?>
                                                <?php endif; ?>
                                                <ol class="dd-list">
                                                    <li class="dd-item">
                                                        <h4><b><?php echo e(trans('aggregate_content.geography.commune')); ?></b></h4>
                                                    </li>
                                                    <?php foreach($district->communes as $commune): ?>
                                                    <li class="dd-item">
                                                        <div class="dd-handle">
                                                            <?php echo e($commune->CommuneKhmerName); ?>

                                                            <em class="badge pull-right bg-color-purple">
                                                                <?php echo e($commune->NumberOfEntity); ?>

                                                            </em>
                                                        </div>
                                                    </li>
                                                    <?php endforeach; ?>
                                                </ol>
                                            </li>
                                            <?php endforeach; ?>
                                        </ol>
                                      </li>
                                    <?php endforeach; ?>
                                  </ol>
                                <?php endif; ?>
                              <?php else: ?>
                                <ol class="dd-list">
                                  <?php foreach($country->provinces as $province): ?>
                                    <li class="dd-item">
                                      <h4><b><?php echo e(trans('aggregate_content.geography.province')); ?></b></h4>
                                    </li>
                                    <li class="dd-item">
                                      <div class="dd-handle">
                                        <?php echo e($province->ProvinceKhmerName); ?>

                                        <em class="badge pull-right bg-color-purple">
                                            <?php echo e($province->NumberOfEntity); ?>

                                        </em>
                                      </div>
                                      <?php if(strcmp($gp_aggType, 'province') === 0): ?>
                                        <?php continue; ?>
                                      <?php endif; ?>
                                      <ol class="dd-list">
                                          <?php foreach($province->districts as $district): ?>
                                          <li class="dd-item">
                                              <h4><b><?php echo e(trans('aggregate_content.geography.district')); ?></b></h4>
                                          </li>
                                          <li class="dd-item">
                                              <div class="dd-handle">
                                                  <?php echo e($district->DistrictKhmerName); ?>

                                                  <em class="badge pull-right bg-color-purple">
                                                      <?php echo e($district->NumberOfEntity); ?>

                                                  </em>
                                              </div>
                                              <?php if(strcmp($gp_aggType, 'district') === 0): ?>
                                              <?php continue; ?>
                                              <?php endif; ?>
                                              <ol class="dd-list">
                                                  <li class="dd-item">
                                                      <h4><b><?php echo e(trans('aggregate_content.geography.commune')); ?></b></h4>
                                                  </li>
                                                  <?php foreach($district->communes as $commune): ?>
                                                  <li class="dd-item">
                                                      <div class="dd-handle">
                                                          <?php echo e($commune->CommuneKhmerName); ?>

                                                          <em class="badge pull-right bg-color-purple">
                                                              <?php echo e($commune->NumberOfEntity); ?>

                                                          </em>
                                                      </div>
                                                  </li>
                                                  <?php endforeach; ?>
                                              </ol>
                                          </li>
                                          <?php endforeach; ?>
                                      </ol>
                                    </li>
                                  <?php endforeach; ?>
                                </ol>
                              <?php endif; ?>
                            <?php endif; ?>
                        </li>
                    </ol>
                <?php else: ?>
                    <ol class="dd-list">
                        <li class="dd-item">
                            <div class="dd-handle">
                                <?php echo e(trans('aggregate_content.geography.country')); ?>

                                <em class="badge pull-right bg-color-purple">
                                    <?php echo e($country->NumberOfEntity); ?>

                                </em>
                            </div>

                            <?php if(strcmp($gp_aggType, 'country') !== 0): ?>
                                <?php if(session()->get('locale')): ?>
                                    <?php if(session()->get('locale') == 'en'): ?>
                                        <ol class="dd-list">
                                            <?php foreach($country->provinces as $province): ?>
                                                <li class="dd-item">
                                                    <h4><b><?php echo e(trans('aggregate_content.geography.province')); ?></b></h4>
                                                </li>
                                                <li class="dd-item">
                                                    <div class="dd-handle">
                                                        <?php echo e($province->ProvinceName); ?>

                                                        <em class="badge pull-right bg-color-purple">
                                                            <?php echo e($province->NumberOfEntity); ?>

                                                        </em>
                                                    </div>
                                                    <?php if(strcmp($gp_aggType, 'province') === 0): ?>
                                                        <?php continue; ?>
                                                    <?php endif; ?>
                                                    <ol class="dd-list">
                                                        <?php foreach($province->districts as $district): ?>
                                                            <li class="dd-item">
                                                                <h4><b><?php echo e(trans('aggregate_content.geography.district')); ?></b></h4>
                                                            </li>
                                                            <li class="dd-item">
                                                                <div class="dd-handle">
                                                                    <?php echo e($district->DistrictName); ?>

                                                                    <em class="badge pull-right bg-color-purple">
                                                                        <?php echo e($district->NumberOfEntity); ?>

                                                                    </em>
                                                                </div>
                                                                <?php if(strcmp($gp_aggType, 'district') === 0): ?>
                                                                    <?php continue; ?>
                                                                <?php endif; ?>
                                                                <ol class="dd-list">
                                                                    <li class="dd-item">
                                                                        <h4><b><?php echo e(trans('aggregate_content.geography.commune')); ?></b></h4>
                                                                    </li>
                                                                    <?php foreach($district->communes as $commune): ?>
                                                                        <li class="dd-item">
                                                                            <div class="dd-handle">
                                                                                <?php echo e($commune->CommuneName); ?>

                                                                                <em class="badge pull-right bg-color-purple">
                                                                                    <?php echo e($commune->NumberOfEntity); ?>

                                                                                </em>
                                                                            </div>
                                                                        </li>
                                                                    <?php endforeach; ?>
                                                                </ol>
                                                            </li>
                                                        <?php endforeach; ?>
                                                    </ol>
                                                </li>
                                            <?php endforeach; ?>
                                        </ol>
                                    <?php elseif(session()->get('locale') == 'km'): ?>
                                        <ol class="dd-list">
                                            <?php foreach($country->provinces as $province): ?>
                                                <li class="dd-item">
                                                    <h4><b><?php echo e(trans('aggregate_content.geography.province')); ?></b></h4>
                                                </li>
                                                <li class="dd-item">
                                                    <div class="dd-handle">
                                                        <?php echo e($province->ProvinceKhmerName); ?>

                                                        <em class="badge pull-right bg-color-purple">
                                                            <?php echo e($province->NumberOfEntity); ?>

                                                        </em>
                                                    </div>
                                                    <?php if(strcmp($gp_aggType, 'province') === 0): ?>
                                                        <?php continue; ?>
                                                    <?php endif; ?>
                                                    <ol class="dd-list">
                                                        <?php foreach($province->districts as $district): ?>
                                                            <li class="dd-item">
                                                                <h4><b><?php echo e(trans('aggregate_content.geography.district')); ?></b></h4>
                                                            </li>
                                                            <li class="dd-item">
                                                                <div class="dd-handle">
                                                                    <?php echo e($district->DistrictKhmerName); ?>

                                                                    <em class="badge pull-right bg-color-purple">
                                                                        <?php echo e($district->NumberOfEntity); ?>

                                                                    </em>
                                                                </div>
                                                                <?php if(strcmp($gp_aggType, 'district') === 0): ?>
                                                                    <?php continue; ?>
                                                                <?php endif; ?>
                                                                <ol class="dd-list">
                                                                    <li class="dd-item">
                                                                        <h4><b><?php echo e(trans('aggregate_content.geography.commune')); ?></b></h4>
                                                                    </li>
                                                                    <?php foreach($district->communes as $commune): ?>
                                                                        <li class="dd-item">
                                                                            <div class="dd-handle">
                                                                                <?php echo e($commune->CommuneKhmerName); ?>

                                                                                <em class="badge pull-right bg-color-purple">
                                                                                    <?php echo e($commune->NumberOfEntity); ?>

                                                                                </em>
                                                                            </div>
                                                                        </li>
                                                                    <?php endforeach; ?>
                                                                </ol>
                                                            </li>
                                                        <?php endforeach; ?>
                                                    </ol>
                                                </li>
                                            <?php endforeach; ?>
                                        </ol>
                                    <?php endif; ?>
                                <?php else: ?>
                                    <ol class="dd-list">
                                        <?php foreach($country->provinces as $province): ?>
                                            <li class="dd-item">
                                                <h4><b><?php echo e(trans('aggregate_content.geography.province')); ?></b></h4>
                                            </li>
                                            <li class="dd-item">
                                                <div class="dd-handle">
                                                    <?php echo e($province->ProvinceKhmerName); ?>

                                                    <em class="badge pull-right bg-color-purple">
                                                        <?php echo e($province->NumberOfEntity); ?>

                                                    </em>
                                                </div>
                                                <?php if(strcmp($gp_aggType, 'province') === 0): ?>
                                                    <?php continue; ?>
                                                <?php endif; ?>
                                                <ol class="dd-list">
                                                    <?php foreach($province->districts as $district): ?>
                                                        <li class="dd-item">
                                                            <h4><b><?php echo e(trans('aggregate_content.geography.district')); ?></b></h4>
                                                        </li>
                                                        <li class="dd-item">
                                                            <div class="dd-handle">
                                                                <?php echo e($district->DistrictKhmerName); ?>

                                                                <em class="badge pull-right bg-color-purple">
                                                                    <?php echo e($district->NumberOfEntity); ?>

                                                                </em>
                                                            </div>
                                                            <?php if(strcmp($gp_aggType, 'district') === 0): ?>
                                                                <?php continue; ?>
                                                            <?php endif; ?>
                                                            <ol class="dd-list">
                                                                <li class="dd-item">
                                                                    <h4><b><?php echo e(trans('aggregate_content.geography.commune')); ?></b></h4>
                                                                </li>
                                                                <?php foreach($district->communes as $commune): ?>
                                                                    <li class="dd-item">
                                                                        <div class="dd-handle">
                                                                            <?php echo e($commune->CommuneKhmerName); ?>

                                                                            <em class="badge pull-right bg-color-purple">
                                                                                <?php echo e($commune->NumberOfEntity); ?>

                                                                            </em>
                                                                        </div>
                                                                    </li>
                                                                <?php endforeach; ?>
                                                            </ol>
                                                        </li>
                                                    <?php endforeach; ?>
                                                </ol>
                                            </li>
                                        <?php endforeach; ?>
                                    </ol>
                                <?php endif; ?>
                            <?php endif; ?>
                        </li>
                    </ol>
                <?php endif; ?>
            </div>
        </div>
        <!-- end widget content -->

      </div>
      <!-- end widget div -->

    </div>
    <!-- end widget -->

  </article>
  <!-- WIDGET END -->

</div>
<!-- end widget grid -->

<script type="text/javascript">
    pageSetUp();
</script>
