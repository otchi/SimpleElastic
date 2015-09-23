package com.edifixio.amine.application.elasticResults;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SetSources {
	
	private static final String HITS="hits";
	
	private MetaSetSource mss;
	private List<Source> sources;

	/****************************************************************************/


	
	public SetSources(MetaSetSource mss, List<Source> sources) {
		super();
		this.mss = mss;
		this.sources = sources;
	}
	
	

	public List<Source> getSources() {
		return sources;
	}

	public MetaSetSource getMss() {
		return mss;
	}
	
	/**************************************************************************************/
	public static SetSources getSetSources(JsonObject jsonObject){
		List<Source> sources=new LinkedList<Source>();
		Iterator<JsonElement> jaIter=jsonObject.get(HITS)
												.getAsJsonArray()
												.iterator();
		while(jaIter.hasNext()){
			sources.add(
					Source.getSource(
							jaIter.next().getAsJsonObject()));
		}
	
		
		return new SetSources(null, sources);
		
	}

	@Override
	public String toString() {
		return "SetSources [mss=" + mss + ", sources=" + sources + "]";
	}

	/***************************************************************************************/
	
	
	

}
