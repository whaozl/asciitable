/* Copyright 2014 Sven van der Meer <vdmeer.sven@mykolab.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.vandermeer.asciitable.v2.core;

import org.apache.commons.lang3.builder.ToStringBuilder;

import de.vandermeer.asciitable.commons.TableException;
import de.vandermeer.asciitable.commons.ObjectToStringStyle;

/**
 * A table row, either a rule or a row with content for columns.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.1.2 build 150812 (12-Aug-15) for Java 1.7
 * @since      v0.0.3
 */
public class V2_TableRow {

	/** Array with content if the row is a content row, null otherwise. */
	Object[] columns;

	/** Array with indicators if the row has to set borders or not. */
	boolean[] borders;

	/** Type of a rule row, will be null for a content row. */
	V2_E_RuleType ruleType;

	/** Style of a rule row, will be null for a content row. */
	V2_E_RuleStyle ruleStyle;

	/**
	 * Returns a new table row of a particular rule type without content.
	 * The rule style is set to normal.
	 * @param type rule type of the row
	 * @param columnCount number of columns for the row
	 */
	public V2_TableRow(V2_E_RuleType type, int columnCount){
		this(type, V2_E_RuleStyle.NORMAL, columnCount);
	}

	/**
	 * Returns a new table row of a particular rule type and style without content.
	 * @param type rule type of the row
	 * @param style rule style
	 * @param columnCount number of columns for the row
	 */
	public V2_TableRow(V2_E_RuleType type, V2_E_RuleStyle style, int columnCount){
		this.ruleType = type;
		this.ruleStyle = style;
		this.columns = null;

		this.borders = new boolean[columnCount+1];
		for(int i=0; i<columnCount+1; i++){
			this.borders[i] = true;
		}

		if(type==null){
			throw new IllegalArgumentException("row type for rule cannot be null");
		}
		if(style==null){
			throw new IllegalArgumentException("row style for rule cannot be null");
		}
	}

	/**
	 * Returns a new content table row
	 * @param cols content in form of columns
	 * @param columnCount column count
	 * @throws TableException if columns and count do not add up
	 */
	public V2_TableRow(Object[] cols, int columnCount) throws TableException {
		if(cols==null){
			throw new TableException("wrong columns argument", "empty column array");
		}
		if(cols.length!=columnCount){
			throw new TableException("wrong columns argument", "tried to add " + cols.length + " columns, expected " + columns + " columns");
		}

		this.ruleType = null;
		this.ruleStyle = null;
		this.columns = cols;

		this.borders = new boolean[cols.length+1];
		for(int i=0; i<columnCount+1; i++){
			this.borders[i] = true;
		}
	}

	/**
	 * Tests if the row is a rule.
	 * @return true if it is a rule, false otherwise
	 */
	public boolean isRule(){
		if(this.ruleType!=null){
			return true;
		}
		return false;
	}

	/**
	 * Tests if the row has content meaning it is not a rule.
	 * @return true if it has content, false otherwise
	 */
	public boolean isContent(){
		return !this.isRule();
	}

	/**
	 * Returns the rule type of the row.
	 * @return rule type, null if not set, i.e. for content rows
	 */
	public V2_E_RuleType getRuleType(){
		return this.ruleType;
	}

	/**
	 * Returns the rule style of the row.
	 * @return rule style, null if not set, i.e. for content rows
	 */
	public V2_E_RuleStyle getRuleStyle(){
		return this.ruleStyle;
	}

	/**
	 * Sets the rule type if the row is a rule row and the new type is not null.
	 * @param type new rule type
	 */
	public void setRuleType(V2_E_RuleType type){
		if(this.isRule() && type!=null){
			this.ruleType = type;
		}
	}

	/**
	 * Sets the rule style if the row is a rule row and the new style is not null.
	 * @param style new rule style
	 */
	public void setRuleStyle(V2_E_RuleStyle style){
		if(this.isRule() && style!=null){
			this.ruleStyle = style;
		}
	}

	/**
	 * Returns a string with debug information.
	 * @param indent number of spaces for indentation, useful for nested operations
	 * @return string with debug information about the table
	 */
	public String toString(int indent){
		ToStringBuilder ret = new ToStringBuilder(this, ObjectToStringStyle.getStyle(indent));

		if(this.isContent()){
			ret.append("row type       ", "content with " + this.columns.length + " column(s)");
			ret.append("columns        ", this.columns, false);
			ret.append("columns        ", this.columns);
		}
		if(this.isRule()){
			ret.append("row type       ", "rule type " + this.ruleType + " style " + this.ruleStyle);
		}
		ret.append("borders        ", this.borders, false);
		ret.append("borders        ", this.borders);

		return ret.toString();
	}

	/**
	 * Returns a string with debug information.
	 * @return string with debug information about the table
	 */
	public String toString(){
		return this.toString(0);
	}

}