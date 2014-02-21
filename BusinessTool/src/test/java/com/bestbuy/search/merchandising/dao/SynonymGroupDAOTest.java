package com.bestbuy.search.merchandising.dao;

/**
 * @author Kalaiselvi.Jaganathan
 * 
 * SynonymGroupTest
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.search.merchandising.domain.Synonym;
import com.bestbuy.search.merchandising.domain.SynonymTerm;
import com.bestbuy.search.merchandising.domain.SynonymTermPK;
import com.bestbuy.search.merchandising.domain.SynonymType;
import com.bestbuy.search.merchandising.service.SynonymTypeService;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
//@Transactional
//@Configurable
public class SynonymGroupDAOTest {

	SynonymGroupDAO synonymGroupDao;
	StatusDAO statusDAO;
	SynonymTypeService synonymTypeService;
	
	@SuppressWarnings("unchecked")
	@Before
	public void init() {
		synonymGroupDao = new SynonymGroupDAO();
		statusDAO = new StatusDAO();
		synonymTypeService = new SynonymTypeService();
	 }	
	

	@Ignore
	@Test
	public void testRetrieveAll() throws Exception {
		assertTrue(synonymGroupDao.retrieveAll().size() > 0 );	
	}


	@Ignore
	@Test
	public void testSave()
	{
		try{
			Synonym synonymGroup = new Synonym();
			synonymGroup.setPrimaryTerm("Keyboard"); 
			synonymGroup.setDirectionality("1");
			synonymGroup.setExactMatch("true");
			Long synonymListType=1133827231862L;
			SynonymType type = synonymTypeService.retrieveById(synonymListType);
			synonymGroup.setSynListId(type);
			assertEquals(20,(synonymGroupDao.retrieveAll()).size());
			Synonym xsynonymGroup = synonymGroupDao.save(synonymGroup);

			String synonymTerms="abc,def,ghk";
			List<SynonymTerm> synonymGroupTerms = new ArrayList<SynonymTerm>();
			String terms[] = synonymTerms.split(",");
			for(int i = 0;i< terms.length ; i++){
				SynonymTerm groupTerm=new SynonymTerm();
				SynonymTermPK groupTermPK = new SynonymTermPK();
				groupTermPK.setSynonym(xsynonymGroup);
				groupTermPK.setSynTerm(terms[i]);
				groupTerm.setSynonymTerms(groupTermPK);
				synonymGroupTerms.add(groupTerm);
			}
			//saving the synonymGroup Terms
			

			//Asset synonymAssets = new Asset();
			//synonymAssets.setAssetsId(xsynonymGroup.getId());
			//synonymAssets.setAssetType(3L);
			//assetsDAO.save(synonymAssets);
			//synonymAssets.setAssetEditedBy("A922998");
			assertEquals(17,(synonymGroupDao.retrieveAll()).size());
			//assertEquals(17,(assetsDAO.retrieveAll()).size());
			//assertEquals(22,(synonymGroupTermDao.retrieveAll()).size());
			assertNotNull(xsynonymGroup);

		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**Test Update Synonyms.
	 */
	@Ignore
	@Test
	public void testeditSynonym()
	{
		try{
			Synonym synonym = new Synonym();
			synonym.setId(3l);
			synonym.setPrimaryTerm("chanchal"); 
			synonym.setDirectionality("1");
			synonym.setExactMatch("true");
			Long synonymListType=1147192016834L;
			SynonymType type = synonymTypeService.retrieveById(synonymListType);
			synonym.setSynListId(type);
			String synonymTerms="abc,def,ghk";
			List<SynonymTerm> synonymGroupTerms = new ArrayList<SynonymTerm>();
			String terms[] = synonymTerms.split(",");
			for(int i = 0;i< terms.length ; i++){
				SynonymTerm groupTerm=new SynonymTerm();
				SynonymTermPK groupTermPK = new SynonymTermPK();
				groupTermPK.setSynonym(synonym);
				groupTermPK.setSynTerm(terms[i]);
				groupTerm.setSynonymTerms(groupTermPK);
				synonymGroupTerms.add(groupTerm);
			}
			//updating the synonymGroup Terms
			//Asset synonymAssets = new Asset();
			//synonymAssets.setAssetsId(3l);
			//synonymAssets.setAssetType(3L);
			//assetsDAO.update(synonymAssets);
			Synonym updatedGroup = synonymGroupDao.update(synonym);
			assertNotNull(updatedGroup);
			assertTrue(updatedGroup.getPrimaryTerm() == "chanchal");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
