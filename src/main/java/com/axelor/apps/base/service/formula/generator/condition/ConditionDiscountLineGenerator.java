/**
 * Copyright (c) 2012-2013 Axelor. All Rights Reserved.
 *
 * The contents of this file are subject to the Common Public
 * Attribution License Version 1.0 (the “License”); you may not use
 * this file except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://license.axelor.com/.
 *
 * The License is based on the Mozilla Public License Version 1.1 but
 * Sections 14 and 15 have been added to cover use of software over a
 * computer network and provide for limited attribution for the
 * Original Developer. In addition, Exhibit A has been modified to be
 * consistent with Exhibit B.
 *
 * Software distributed under the License is distributed on an “AS IS”
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is part of "Axelor Business Suite", developed by
 * Axelor exclusively.
 *
 * The Original Developer is the Initial Developer. The Initial Developer of
 * the Original Code is Axelor.
 *
 * All portions of the code written by Axelor are
 * Copyright (c) 2012-2013 Axelor. All Rights Reserved.
 */
package com.axelor.apps.base.service.formula.generator.condition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.axelor.apps.base.service.formula.generator.AbstractConditionGenerator;
import com.axelor.apps.account.db.DiscountEngineLine;

public class ConditionDiscountLineGenerator extends AbstractConditionGenerator {
	
	@Inject
	public ConditionDiscountLineGenerator() { super(); }
	
	@Override
	protected Map<String, Object> bind() {
	
		Map<String, Object> bind = new HashMap<String, Object>();
		bind.put("klassImport",new String[]{ 
				"com.axelor.apps.account.db.InvoiceLine",
				"com.axelor.apps.account.db.DiscountEngineLine",
				"com.axelor.apps.tool.date.DateTool"
		});
		bind.put("klassName","ConditionDiscountLine");
		bind.put("runnableParameters", new String[]{ 
				"DiscountEngineLine",
				"InvoiceLine"
		});
		
		Map<String,String> conditions = new HashMap<String, String>();
		Map<String, List<String>> exceptionConditions = new HashMap<String, List<String>>();

		for (DiscountEngineLine discountEngineLine : DiscountEngineLine.all().fetch()){
			if ( discountEngineLine.getCode() != null && !discountEngineLine.getCode().isEmpty() && discountEngineLine.getFinalCondition() != null && !discountEngineLine.getFinalCondition().isEmpty()){
				conditions.put(discountEngineLine.getCode().toLowerCase(), discountEngineLine.getFinalCondition());
				List<String> exceptions = new ArrayList<String>();
				exceptions.add(String.format("msg += \"Problème sur la condition de la remise %s\"", discountEngineLine.getCode()));
				exceptionConditions.put(discountEngineLine.getCode().toLowerCase(), exceptions);
			}
		}

		bind.put("conditions",conditions);
		bind.put("exceptionConditions", exceptionConditions);

		return bind;
	}
	
}
