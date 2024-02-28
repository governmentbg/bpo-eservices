package eu.ohim.sp.core.rules.drools;


import org.drools.KnowledgeBase;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class RulesMapTest {
	private static final String ANY_MODULE = "ANY_MODULE";
	private static final String ANY_LIST = "ANY_LIST";

	private RulesMap rulesMap = new RulesMap();

	@Test
	public void should_add_knowledge_base_to_map() {
		// given
		Map<String, KnowledgeBase> knowledgeBaseMap = new HashMap<String, KnowledgeBase>();
		knowledgeBaseMap.put(ANY_MODULE, mock(KnowledgeBase.class));

		// when
		rulesMap.addMap(ANY_MODULE, knowledgeBaseMap);

		// then
		assertThat(rulesMap.getRuleFilesCount(ANY_MODULE), is(knowledgeBaseMap.size()));
	}

	@Test(expected = IllegalStateException.class)
	public void should_throw_exception_when_module_not_found() {
		rulesMap.getKnowledgeBase("some module", "some list");
	}

	@Test(expected = IllegalStateException.class)
	public void should_throw_exception_when_list_not_found() {
		// given
		Map<String, KnowledgeBase> knowledgeBaseMap = new HashMap<String, KnowledgeBase>();
		knowledgeBaseMap.put(ANY_MODULE, mock(KnowledgeBase.class));
		rulesMap.addMap(ANY_MODULE, knowledgeBaseMap);

		// when
		rulesMap.getKnowledgeBase(ANY_LIST, "some list");

		// then
		// exception is thrown
	}

	@Test
	public void should_return_knowledge_base() {
		// given
		final String module = "module";
		final String list = "list";
		Map<String, KnowledgeBase> knowledgeBaseMap = new HashMap<String, KnowledgeBase>();

		KnowledgeBase sampleKnowledgeBase = mock(KnowledgeBase.class);

		knowledgeBaseMap.put(list, sampleKnowledgeBase);
		rulesMap.addMap(module, knowledgeBaseMap);

		// when
		KnowledgeBase knowledgeBase = rulesMap.getKnowledgeBase(module, list);

		// then
		assertThat(knowledgeBase, is(sampleKnowledgeBase));
	}
}
