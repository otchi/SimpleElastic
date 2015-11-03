package test.com.edifixio.simplElastic.applicatif;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.edifixio.simplElastic.application.elasticResults.Aggregations;
import com.edifixio.simplElastic.application.elasticResults.AggrsReturnObject;
import com.edifixio.simplElastic.application.elasticResults.Bucket;
import com.edifixio.simplElastic.application.elasticResults.FacetableAggr;

public class CopyAndUpdatingTest {
	
	@Test
	public void testBuquets(){
		Bucket bucket=new Bucket(5, new Aggregations());
		Bucket cpBucket=bucket.getCopy();
		Assert.assertEquals(bucket.getIsChecked(), cpBucket.getIsChecked());
		cpBucket.setIsChecked(false);
		Assert.assertNotEquals(bucket.getIsChecked(), cpBucket.getIsChecked());
		bucket.update(cpBucket);
		System.out.println(bucket);
	}
	
	@Test
	public void testAggsFacet(){
		Map<String, Bucket> mapConf=new HashMap<String, Bucket>();
		mapConf.put("japan", new Bucket(5, new Aggregations()));
		FacetableAggr facet=new FacetableAggr(mapConf);
		FacetableAggr cpFacet=facet.getCopy();
		System.out.println("------------------ first ------------------------------------------");
		System.out.println("origin: "+facet);
		System.out.println("copie: "+cpFacet);
		System.out.println("------------------------------------------------------------");
		cpFacet.getBuckets().get("japan").setIsChecked(false);
		System.out.println("-----------------change cpvalue-------------------------------------------");
		System.out.println("origin: "+facet);
		System.out.println("copie: "+cpFacet);
		System.out.println("------------------------------------------------------------");
		facet.update(cpFacet);
		System.out.println("--------------------- update  original using original ---------------------------------------");
		System.out.println("origin: "+facet);
		System.out.println("copie: "+cpFacet);
		System.out.println("------------------------------------------------------------");
	}
	
	@Test
	public void aggsResultObject(){
		Map<String, Bucket> mapConf = new HashMap<String, Bucket>();
		mapConf.put("japan", new Bucket(5, new Aggregations()));
		FacetableAggr facet=new FacetableAggr(mapConf);
		Map<String, FacetableAggr> facets = new HashMap<String, FacetableAggr>();
		facets.put("test", facet);
		AggrsReturnObject aro=new AggrsReturnObject(facets);
		AggrsReturnObject cpAro=aro.getCopy();
		System.out.println("------------------ first ------------------------------------------");
		System.out.println("origin: "+aro);
		System.out.println("copie: "+cpAro);
		System.out.println("------------------------------------------------------------");
		cpAro.getFacets().get("test").getBuckets().get("japan").setIsChecked(false);
		cpAro.getFacets().get("test").getBuckets().get("japan").setCount(null);
		System.out.println("------------------ change cpvalue ------------------------------------------");
		System.out.println("origin: "+aro);
		System.out.println("copie: "+cpAro);
		System.out.println("------------------------------------------------------------");
		aro.update(cpAro);
		System.out.println("------------------ update  original using original ------------------------------------------");
		System.out.println("origin: "+aro);
		System.out.println("copie: "+cpAro);
		System.out.println("------------------------------------------------------------");
		
		
		
		
	}

}
