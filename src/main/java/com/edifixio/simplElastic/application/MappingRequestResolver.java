package com.edifixio.simplElastic.application;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.edifixio.jsonFastBuild.selector.JsonHandleUtil;
import com.edifixio.simplElastic.utils.Duo;
import com.edifixio.simplElastic.utils.EntryImp;
import com.edifixio.simplElastic.utils.IntegerDuo;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

/***
 * 
 * @author amine
 * class of variable resolving
 * for the solution template variables query
 * 
 */
public class MappingRequestResolver {
	//variable that maps a variable with the name (in the corresponding class) and position !!!! not sur
	private Map<String, Entry<String, IntegerDuo>> corresp;
	private Map<String, List<String>> varInSameField;
	// json template 
	private final JsonObject jsonObject;
	/**
	 * construction with a template to process
	 * @param jsonObject : template
	 */

	public MappingRequestResolver(JsonObject jsonObject) {
		super();
		this.jsonObject = jsonObject;
		corresp = new HashMap<String, Map.Entry<String, IntegerDuo>>();
		varInSameField = new HashMap<String, List<String>>();

	}

	/************************************************************************************************/

	public JsonObject getJsonObject() {
		return jsonObject;
	}

	/**********************************************************************************************/
	public Map<String, Entry<String, IntegerDuo>> getCorresp() {
		return corresp;
	}

	/***********************************************************************************************/
	public Map<String, List<String>> getVarInSameField() {
		return varInSameField;
	}

	/*************************************************************************************************/
	public void parsing() {
		parser(jsonObject, "");

	}

	/**************************************************************************************************/
	/**
	 *  Delegation method of treatment according to the type of element JSON
	 *  
	 * @param jsonElement : element to be inspected to search a template variables
	 * @param path : path in JSON tree
	 */
	private  final void parser(final JsonElement jsonElement, final String path) {

		if (jsonElement.isJsonPrimitive()) {
			JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();

			if (jsonPrimitive.isString()) {
				stringParser(jsonPrimitive.getAsString(), path);
				return;
			}
			return;
		}

		if (jsonElement.isJsonObject()) {
			parseInJsonObject(jsonElement.getAsJsonObject(), path);
			return;
		}

		if (jsonElement.isJsonArray()) {
			parseInJsonArray(jsonElement.getAsJsonArray(), path);
			return;
		}

	}

	/************************************************************************************************/
	/**
	 * 
	 * @param jsonObject : object JSON
	 * @param path : path in JSON tree
	 */
	private final void parseInJsonObject(final JsonObject jsonObject, String path) {
		Iterator<Entry<String, JsonElement>> jsoIter = jsonObject.entrySet().iterator();
		if (!path.equals(""))
			path = path + "::";

		for(Entry<String, JsonElement> entry : jsonObject.entrySet() ) {
			entry = jsoIter.next();
			parser(entry.getValue(), path + entry.getKey());
		}
	}

	/**********************************************************************************************/
	/**
	 * 
	 * @param jsonArray :  JSON array
	 * @param path  : path in JSON tree
	 */
	private final void parseInJsonArray(final JsonArray jsonArray, String path) {
		if (!path.equals(""))
			path = path + "::";
		for (int i = 0; i < jsonArray.size(); i++) {
			parser(jsonArray.get(i), path + i);
		}
	}

	/*******************************************************************************************/
	/**
	 * method to search a  location of template variables in a JSON literal (String) 
	 * then then index positions to be processed later 
	 * @param field :  field contain  one of template  variables
	 * @param path :  path of location of the variable JSON template in tree 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public final void stringParser(final String field,final  String path) {

		int[][] index = indexer(field);
		if (index == null)
			return;

		for (int i = 0; i < index.length; i++) {

			String varName = field.substring(index[i][0] + 2, index[i][1] - 1);
			if (varName.length() == 0) {
				System.out.println("MappingRequestResolver ~ exception 98");
				return;
			}

			if (corresp.containsKey(varName)) {
				System.out.println("MappingRequestResolver ~ exception 103");
				return;
			}

			//System.out.println(varName+"//"+index[i][0]+"//"+index[i][1]);
			
			corresp.put(varName, new EntryImp(path, new IntegerDuo(index[i][0], index[i][1])));
			if (varInSameField.containsKey(path)) {
				varInSameField.get(path).add(varName);
			} else {
				List<String> list = new LinkedList<String>();
				list.add(varName);
				varInSameField.put(path, list);
			}
		}
	}

	/**************************************************************************************/
	/**
	 * 
	 * @param field : field that could contain a template variable 
	 * @return array that contain the begin and end index position of template variables in a specific field
	 *  
	 */
	public int[][] indexer(String field) {

		String[] strs = field.split("\\$");
		if (strs == null)
			return null;
		// System.out.println(strs.length);
		if (strs.length < 2)
			return null;

		int lastVariableIndex = -1, beginPos = 0, endPos = 0, indexOfVar = 0, j = 0;
		int[][] index = new int[strs.length - 1][2];
		String var;

		for (int i = 0; i < strs.length; i++) {
			// System.out.println("-"+strs[i]+"-");
			beginPos = strs[i].indexOf('{');
			endPos = strs[i].indexOf('}') + 1;
			// System.out.println(beginPos+"-|"+endPos);
			if (beginPos != 0 || endPos < 2)
				continue;
			// System.out.println(beginPos+"!-!"+endPos);
			var = "$" + strs[i].substring(beginPos, endPos);
			indexOfVar = field.indexOf(var);
			// System.out.println(indexOfVar);
			if (indexOfVar <= lastVariableIndex) {

				//System.out.println("\n duplicated variable");
				//System.out.println("_______________________");
				return null;
			}
			// System.out.println(indexOfVar+"--|"+lastVariableIndex);
			lastVariableIndex = indexOfVar;
			index[j][0] = field.indexOf(var);
			index[j][1] = index[j][0] + var.length();
			//System.out.println("--------------vars----------------------");
			//System.out.println(field.substring(index[j][0], index[j][1]));
			//System.out.println("------------------------------------");
			j++;
		}
		if (j == 0)
			return null;
		//System.out.println("----------index--------------------------");
		 //System.out.println(corresp);
		return index;
	}

	/***************************************************************************************************/
	/**
	 * search a template variable  and replace with value
	 * @param var : name of a template variable 
	 * @param value : value to assign to the template variable
	 */
	public void varResolver(final String var,final String value) {
		//System.out.println(corresp);
		Entry<String, IntegerDuo> info = corresp.get(var);
		
		if (info == null) {
			System.out.println("MappingRequestResolver ~ exception 162 "+var+"---->"+value);
			return;
		}
		
		Duo<Integer, Integer> val = info.getValue();
		String path = info.getKey();
		Integer infoValueFirst = val.getFirst(), infoValueSeconde = val.getSeconde();
		JsonParser jp = new JsonParser();
		// System.out.println(info.getKey()+"-->"+jsonObject);
		String field = JsonHandleUtil.seletor(path, jsonObject).getAsString();
		StringBuilder fieldStrBuild;
		Integer lenghtDifference;
		JsonElement parent;
		List<String> SameField;

		if (field == null) {
			System.out.println("MappingRequestResolver ~ exception 173");
			return;
		}

		fieldStrBuild = new StringBuilder(field);
		lenghtDifference = (-(infoValueSeconde - infoValueFirst - value.length()));
		// System.out.println(lenghtDifference+"---->"+info.getKey()+"--->"+var);
		
		
		/********************************************************/
		SameField = varInSameField.get(path);
		IntegerDuo thisFieldduo=corresp.get(var).getValue();
		for (String str : SameField) {
			IntegerDuo duo = corresp.get(str).getValue();
			if(thisFieldduo.getFirst()<duo.getFirst())
					duo.incrementAll(lenghtDifference);
		}
		SameField.remove(var);
		/*********************************************************/
		System.out.println(corresp);
		//System.out.println(infoValueFirst+"//"+infoValueSeconde+"//"+val);
		if(infoValueFirst<0 || infoValueSeconde<0 ) {
			System.out.println("MappingRequestResolver ~ 132 exception");
			return;
		}// to retest later
		fieldStrBuild.replace(infoValueFirst, infoValueSeconde, value);

		int indexOfPrefix = info.getKey().lastIndexOf("::");

		if (indexOfPrefix < 0)
			indexOfPrefix = 0;
		//System.out.println(corresp);
		parent = JsonHandleUtil.seletor(info.getKey().substring(0, indexOfPrefix), jsonObject);

		if (indexOfPrefix != 0)
			indexOfPrefix += 2;
		if (parent.isJsonArray()) {
			JsonArray ja = parent.getAsJsonArray();
			JsonHandleUtil.replaceInJsonArray(ja, Integer.parseInt(info.getKey().substring(indexOfPrefix)),
					jp.parse("[\"" + fieldStrBuild.toString() + "\"]").getAsJsonArray().get(0));
		}
		if (parent.isJsonObject()) {

			JsonObject jo = parent.getAsJsonObject();
			String elementName = info.getKey().substring(indexOfPrefix);

			jo.remove(elementName);
			jo.add(elementName, // like that because it cause error in parsing
								// JSON if
					// you pass a only string with space , same case for object
					jp.parse("[\"" + fieldStrBuild.toString() + "\"]").getAsJsonArray().get(0));
		}
		corresp.remove(var);
	}

	@Override
	public String toString() {
		return "MappingRequestResolver [corresp=" + corresp + ", varInSameField=" + varInSameField + ", jsonObject="
				+ jsonObject + "]";
	}

	
	
	/*********************************************************************/
	
	

}
