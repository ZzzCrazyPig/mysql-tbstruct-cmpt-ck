<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="row">
	<div class="col-md-12">
		<div class="panel panel-success">
			<div class="panel-heading">版本库信息</div>
			<div class="panel-body">
				<h4>生产版本库</h4>
				<form id="form" class="form-horizontal" role="form">
					<div class="form-group">
						<label for="pvHost" class="col-md-1 control-label">host:</label>
						<div class="col-md-3">
							<input class="form-control" id="pvHost" name="pvHost" required data-error="(必填)">
						</div>
						<div class="col-md-3 help-block with-errors"></div>
					</div>
					<div class="form-group">
						<label for="pvPort" class="col-md-1 control-label">port:</label>
						<div class="col-md-2">
							<input class="form-control" id="pvPort" name="pvPort" required data-error="(必填)">
						</div>
						<div class="col-md-3 help-block with-errors"></div>
					</div>
					<div class="form-group">
						<label for="pvDb" class="col-md-1 control-label">db:</label>
						<div class="col-md-2">
							<input class="form-control" id="pvDb" name="pvDb" required data-error="(必填)">
						</div>
						<div class="col-md-3 help-block with-errors"></div>
					</div>
					<div class="form-group">
						<label for="pvUser" class="col-md-1 control-label">user:</label>
						<div class="col-md-3">
							<input class="form-control" id="pvUser" name="pvUser" required data-error="(必填)">
						</div>
						<div class="col-md-3 help-block with-errors"></div>
					</div>
					<div class="form-group">
						<label for="pvPwd" class="col-md-1 control-label">password:</label>
						<div class="col-md-3">
							<input class="form-control" id="pvPwd" name="pvPwd" required data-error="(必填)">
						</div>
						<div class="col-md-3 help-block with-errors"></div>
						</div>
					<hr />
					<h4>灰度版本库</h4>
					<div class="form-group">
						<label for="gvHost" class="col-md-1 control-label">host:</label>
						<div class="col-md-3">
							<input class="form-control" id="gvHost" name="gvHost" required data-error="(必填)">
						</div>
						<div class="col-md-3 help-block with-errors"></div>
					</div>
					<div class="form-group">
						<label for="gvPort" class="col-md-1 control-label">port:</label>
						<div class="col-md-2">
							<input class="form-control" id="gvPort" name="gvPort" required data-error="(必填)">
						</div>
						<div class="col-md-3 help-block with-errors"></div>
					</div>
					<div class="form-group">
						<label for="gvDb" class="col-md-1 control-label">db:</label>
						<div class="col-md-2">
							<input class="form-control" id="gvDb" name="gvDb" required data-error="(必填)">
						</div>
						<div class="col-md-3 help-block with-errors"></div>
					</div>
					<div class="form-group">
						<label for="gvUser" class="col-md-1 control-label">user:</label>
						<div class="col-md-3">
							<input class="form-control" id="gvUser" name="gvUser" required data-error="(必填)">
						</div>
						<div class="col-md-3 help-block with-errors"></div>
					</div>
					<div class="form-group">
						<label for="gvPwd" class="col-md-1 control-label">password:</label>
						<div class="col-md-3">
							<input class="form-control" id="gvPwd" name="gvPwd" required data-error="(必填)">
						</div>
						<div class="col-md-3 help-block with-errors"></div>
					</div>
					
					<hr />
					<h4>校验表</h4>
					<div class="form-group">
						<label for="tables" class="col-md-1 control-label">tables:</label>
						<div class="col-md-4">
							<input class="form-control" id="tables" name="tables" required data-error="(必填)">
						</div>
						<div class="col-md-3 help-block with-errors"></div>
					</div>
					<p><button type="submit" id="checkCmptBtn" class="btn btn-primary">检查</button></p>
				</form>
			</div>
		</div>
	</div>
	
</div> <!--/.row-->

<script>
	$(document).ready(function() {
		
		$('#form').validator().on('submit', function(e){
			if (e.isDefaultPrevented()) {
			    // handle the invalid form...
			    console.info('handle the invalid form...');
			} else {
			    // everything looks good!
			    console.info('everything looks good!');
			    var form = $('#form');
			    var meta = form.serializeObject();
				console.info(meta);
				$.ajax({
					contentType: 'application/json;charset=UTF-8',
					url: 'checkCmpt',
					type: 'POST',
					data: JSON.stringify(meta),
					success: function(result, status, xhr) {
						if(result.success) {
							console.log(result.rows);
						} else {
							showError('执行校验异常', result.errMsg);
						}
					},
					error: function(xhr, errMsg, e){  
						showError("Ajax请求异常!", errMsg);
					} 
				});
			}
		});
		
	});
	
var showError = function(title, message) {
	$.toaster({ settings : {
		timeout: 5000
	} });
	$.toaster({ priority : 'danger', title : title, message : message});
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