package com.edifixio.simplElastic.application.elasticResults;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Hits {
	
	private static final String HITS="hits";
	
	private MetaHits mss;
	private List<Hit> hits;

	/****************************************************************************/


	
	public Hits(MetaHits mss, List<Hit> hits) {
		super();
		this.mss = mss;
		this.hits = hits;
	}
	
	

	public List<Hit> getHits() {
		return hits;
	}

	public MetaHits getMss() {
		return mss;
	}
	
	/**************************************************************************************/
	public static Hits getHits(JsonObject jsonObject){
		List<Hit> sources=new LinkedList<Hit>();
		Iterator<JsonElement> jaIter=jsonObject.get(HITS)
												.getAsJsonArray()
												.iterator();
		while(jaIter.hasNext()){
			sources.add(
					Hit.getSource(
							jaIter.next().getAsJsonObject()));
		}
	
		
		return new Hits(null, sources);
		
	}



	@Override
	public String toString() {
		return "Hits [mss=" + mss + ", hits=" + hits + "]";
	}


	/***************************************************************************************/
	
	
	

}
