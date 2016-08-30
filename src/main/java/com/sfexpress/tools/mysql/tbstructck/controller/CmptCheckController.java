package com.sfexpress.tools.mysql.tbstructck.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sfexpress.tools.mysql.tbstructck.checker.ColInfoCmptChecker;
import com.sfexpress.tools.mysql.tbstructck.entity.AppResult;
import com.sfexpress.tools.mysql.tbstructck.entity.CmptCheckResult;
import com.sfexpress.tools.mysql.tbstructck.entity.ConnInfo;
import com.sfexpress.tools.mysql.tbstructck.entity.ConnInfoBuilder;
import com.sfexpress.tools.mysql.tbstructck.entity.Meta;

@Controller
public class CmptCheckController {
	
	private ColInfoCmptChecker colInfoCmptChecker = new ColInfoCmptChecker();
	
	@RequestMapping(value = "/checkCmpt", method = RequestMethod.POST)
	@ResponseBody
	public AppResult<CmptCheckResult> check(@RequestBody Meta meta) {
		
		ConnInfoBuilder connInfoBuilder = new ConnInfoBuilder();
		ConnInfo pvConnInfo = connInfoBuilder.host(meta.getPvHost())
												.port(meta.getPvPort())
												.db(meta.getPvDb())
												.user(meta.getPvUser())
												.password(meta.getPvPwd())
												.build();
		ConnInfo gvConnInfo = connInfoBuilder.host(meta.getGvHost())
												.port(meta.getGvPort())
												.db(meta.getGvDb())
												.user(meta.getGvUser())
												.password(meta.getGvPwd())
												.build();
		String[] tables = StringUtils.split(meta.getTables(), ",");
		for(int i = 0; i < tables.length; i++) {
			tables[i] = StringUtils.trim(tables[i]);
		}
		AppResult<CmptCheckResult> result = new AppResult<CmptCheckResult>();
		try {
			List<CmptCheckResult> rows = colInfoCmptChecker.check(pvConnInfo, gvConnInfo, tables);
			result.setSuccess(true);
			result.setRows(rows);
		} catch(Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrMsg(e.getMessage());
		}
		return result;
	}

}
