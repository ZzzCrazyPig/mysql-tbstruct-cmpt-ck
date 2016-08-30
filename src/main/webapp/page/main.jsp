<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-md-12">
		<div class="panel panel-success">
			<div class="panel-heading">版本库信息</div>
			<div class="panel-body">
				<form id="form" class="form-horizontal" role="form">
				<div class="row">
					<div class="col-md-6">
						<h4>生产版本库</h4>
						<div class="form-group">
							<label for="pvHost" class="col-md-2 control-label">host:</label>
							<div class="col-md-5">
								<input class="form-control" id="pvHost" name="pvHost" 
									value="localhost" required data-error="(必填)">
							</div>
							<div class="col-md-3 help-block with-errors"></div>
						</div>
						<div class="form-group">
							<label for="pvPort" class="col-md-2 control-label">port:</label>
							<div class="col-md-3">
								<input class="form-control" id="pvPort" name="pvPort" 
									value="3306" required data-error="(必填)">
							</div>
							<div class="col-md-3 help-block with-errors"></div>
						</div>
						<div class="form-group">
							<label for="pvDb" class="col-md-2 control-label">db:</label>
							<div class="col-md-3">
								<input class="form-control" id="pvDb" name="pvDb" 
									value="db2" required data-error="(必填)">
							</div>
							<div class="col-md-3 help-block with-errors"></div>
						</div>
						<div class="form-group">
							<label for="pvUser" class="col-md-2 control-label">user:</label>
							<div class="col-md-3">
								<input class="form-control" id="pvUser" name="pvUser" 
									value="root" required data-error="(必填)">
							</div>
							<div class="col-md-3 help-block with-errors"></div>
						</div>
						<div class="form-group">
							<label for="pvPwd" class="col-md-2 control-label">password:</label>
							<div class="col-md-5">
								<input type="password" class="form-control" id="pvPwd" name="pvPwd" 
									value="mysql" required data-error="(必填)">
							</div>
							<div class="col-md-3 help-block with-errors"></div>
						</div>
					</div>
					
					<div class="col-md-6">
						<h4>灰度版本库</h4>
						<div class="form-group">
							<label for="gvHost" class="col-md-2 control-label">host:</label>
							<div class="col-md-4">
								<input class="form-control" id="gvHost" name="gvHost" 
									value="localhost" required data-error="(必填)">
							</div>
							<div class="col-md-3 help-block with-errors"></div>
						</div>
						<div class="form-group">
							<label for="gvPort" class="col-md-2 control-label">port:</label>
							<div class="col-md-3">
								<input class="form-control" id="gvPort" name="gvPort" 
									value="3306" required data-error="(必填)">
							</div>
							<div class="col-md-3 help-block with-errors"></div>
						</div>
						<div class="form-group">
							<label for="gvDb" class="col-md-2 control-label">db:</label>
							<div class="col-md-3">
								<input class="form-control" id="gvDb" name="gvDb" 
									value="db1" required data-error="(必填)">
							</div>
							<div class="col-md-3 help-block with-errors"></div>
						</div>
						<div class="form-group">
							<label for="gvUser" class="col-md-2 control-label">user:</label>
							<div class="col-md-3">
								<input class="form-control" id="gvUser" name="gvUser" 
									value="root" required data-error="(必填)">
							</div>
							<div class="col-md-3 help-block with-errors"></div>
						</div>
						<div class="form-group">
							<label for="gvPwd" class="col-md-2 control-label">password:</label>
							<div class="col-md-5">
								<input type="password" class="form-control" id="gvPwd" name="gvPwd" 
									value="mysql" required data-error="(必填)">
							</div>
							<div class="col-md-3 help-block with-errors"></div>
						</div>
					</div>
					
				</div>
					
					<hr />
					<h4>校验表</h4>
					<div class="form-group">
						<label for="tables" class="col-md-1 control-label">tables:</label>
						<div class="col-md-4">
							<input class="form-control" id="tables" name="tables"
								value="hotnews" required data-error="(必填)">
						</div>
						<div class="col-md-3 help-block with-errors"></div>
					</div>
					<p><button type="submit" id="checkCmptBtn" class="btn btn-primary">检查</button></p>
				</form>
			</div><!-- /.panel-body -->
		</div> <!-- /.panel -->
	</div>
	
</div> <!--/.row-->

<div class="row">
	<div class="col-md-12">
		<div id="checkResult"></div>
	</div>
</div>

<textarea id="checkResultTemplate" style="display:none">
<!-- <![CDATA[ -->
{#template MAIN}
<div class="panel panel-success">
	<div class="panel-heading">兼容性校验结果</div>
	<div class="panel-body">
	<table class="table table-bordered">
	<tr>
		<th>序号</th>
		<th>灰度版本库信息</th>
		<th>表</th>
		<th>兼容性</th>
		<th>详情</th>
	</tr>
	{#foreach $T as item}
	<tr>
		<td>#{$T.item$index}</td>
		<td>{$T.item.gvConnInfo.host}:{$T.item.gvConnInfo.port}/{$T.item.gvConnInfo.db}</td>
		<td>{$T.item.table}</td>
		<td>
		{#if $T.item.compatible}
			<span><img src="assets/img/correct_alt.png" width="16" height="16"></span>
		{#else}
			<span><img src="assets/img/error_alt.png" width="16" height="16"></span>
		{#/if}
		</td>
		<td>
		{#if $T.item.compatible && !$T.item.diff}
			无
		{#else}
			{#include SUB root=$T.item}
			<!-- <a href="javascript:void(0)" onclick="showCheckResultDetail({#var $T.item})">显示</a> -->
		{#/if}
		</td>
	</tr>
	{#/for}
	</table>
	</div>
</div>
{#/template MAIN}

{#template SUB}
{#if $T.newItems.length > 0}
<h4>[新增列]</h4>
<ul>
	{#foreach $T.newItems as newItem}
	<li>
	<p>
	{$T.newItem.columnName} {$T.newItem.columnType} default {$T.newItem.columnDefault}
	</p>
	</li>
	{#/for}
</ul>
{#/if}
{#if $T.deletedItems.length > 0}
<h4>[缺失列]</h4>
<ul>
	{#foreach $T.deletedItems as delItem}
		<li><p>{$T.delItem.columnName} {$T.delItem.columnType} default {$T.delItem.columnDefault}</p></li>
	{#/for}
</ul>
{#/if}
{#if $T.conflictItems.length > 0}
<h4 class="text-focus">[不兼容]</h4>
<ul>
	{#foreach $T.conflictItems as cItem}
	<li><p class="text-focus">列 [{$T.cItem.gvColInfo.columnName}] 的数据类型{$T.cItem.gvColInfo.columnType}与生产版本库该列数据类型{$T.cItem.pvColInfo.columnType}不兼容</p></li>
	{#/for}
</ul>
{#/if}
{#/template SUB}


<!-- ]]> -->
</textarea>

<textarea id="checkResultDetailTemplate" style="display:none">

{#if $T.newItems.length > 0}
<h4>[新增列]</h4>
<ul>
	{#foreach $T.newItems as newItem}
	<li>
	<p>
	{$T.newItem.columnName} {$T.newItem.columnType} default {$T.newItem.columnDefault}
	</p>
	</li>
	{#/for}
</ul>
{#/if}
{#if $T.deletedItems.length > 0}
<h4>[缺失列]</h4>
<ul>
	{#foreach $T.deletedItems as delItem}
		<li><p>{$T.delItem.columnName} {$T.delItem.columnType} default {$T.delItem.columnDefault}</p></li>
	{#/for}
</ul>
{#/if}
{#if $T.conflictItems.length > 0}
<h4>[不兼容]</h4>
<ul>
	{#foreach $T.conflictItems as cItem}
	<li><p class="text-focus">列 [{$T.cItem.gvColInfo.columnName}] 的数据类型{$T.cItem.gvColInfo.columnType}与生产版本库该列数据类型{$T.cItem.pvColInfo.columnType}不兼容</p></li>
	{#/for}
</ul>
{#/if}
</textarea>

<script>
	$(document).ready(function() {
		
		$('#form').validator();
		
		$('#checkCmptBtn').click(function(e){
			
			e.preventDefault();
			
			var form = $('#form');
		    var meta = form.serializeObject();
			$.ajax({
				contentType: 'application/json;charset=UTF-8',
				url: 'checkCmpt',
				type: 'POST',
				data: JSON.stringify(meta),
				success: function(result, status, xhr) {
					if(result.success) {
						// console.log(result.rows);
						renderResult(result.rows);
					} else {
						showError('执行校验异常', result.errMsg);
					}
				},
				error: function(xhr, errMsg, e){  
					showError("Ajax请求异常!", errMsg);
				} 
			});
		});
		
	});
	
var showError = function(title, message) {
	$.toaster({ settings : {
		timeout: 5000
	} });
	$.toaster({ priority : 'danger', title : title, message : message});
};

var renderResult = function(rows) {
	$('#checkResult').setTemplateElement('checkResultTemplate');
	$('#checkResult').processTemplate(rows);
};

var showCheckResultDetail = function(data) {
	BootstrapDialog.show({
		title: '详细',
		message: function(dialog){
			var $message = $('<div id="checkResultDetail"></div>');
			$message.setTemplateElement('checkResultDetailTemplate');
			$message.processTemplate(data);
			return $message;
		},
		closeByBackdrop: false,
        closeByKeyboard: false,
        type: BootstrapDialog.TYPE_WARNING
	});
};

(function($){
	$.fn.serializeObject = function()
	{
	   var o = {};
	   var a = this.serializeArray();
	   $.each(a, function() {
	       if (o[this.name]) {
	           if (!o[this.name].push) {
	               o[this.name] = [o[this.name]];
	           }
	           o[this.name].push(this.value || '');
	       } else {
	           o[this.name] = this.value || '';
	       }
	   });
	   return o;
	};
})(jQuery);

</script>