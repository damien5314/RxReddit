package rxreddit.api

import org.junit.*
import org.junit.Assert.assertNotNull
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import rxreddit.test.getRedditService

@RunWith(MockitoJUnitRunner::class)
class RedditServiceTests {

  companion object {
    @BeforeClass @JvmStatic
    fun setUpClass() {
    }

    @AfterClass @JvmStatic
    fun tearDownClass() {
    }
  }

  @Before
  fun setUp() {
  }

  @After
  fun tearDown() {
  }

  @Test
  fun test_loadLinks() {
    val service = getRedditService()
    val observable = service.loadLinks("", "", "", "", "")
    assertNotNull(observable)
  }

}
