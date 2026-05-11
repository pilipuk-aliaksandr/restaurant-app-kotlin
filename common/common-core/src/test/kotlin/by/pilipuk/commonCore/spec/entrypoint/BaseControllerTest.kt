package by.pilipuk.commonCore.spec.entrypoint

import by.pilipuk.commonCore.environment.service.TruncateDBTablesTestService
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import tools.jackson.databind.ObjectMapper
import kotlin.test.DefaultAsserter.fail

@SpringBootTest
@AutoConfigureMockMvc
class BaseControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var truncateDBTablesTestService: TruncateDBTablesTestService

    @BeforeEach
    fun setup(): Unit {
        truncateDBTablesTestService.truncateAllTables()
    }

    fun toJson(obj: Any): String =
        objectMapper.writeValueAsString(obj)

    protected fun performPostRequest(url: String, postDto: Any, expectedEntity: Any) {
        runCatching {
            mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(postDto))
            ).andExpectAll(
                status().isCreated,
                content().contentType(MediaType.APPLICATION_JSON),
                content().json(toJson(expectedEntity))
            )
        }.onFailure { e ->
            fail("Error executing POST request to $url: ${e.message}")
        }
    }

    protected fun performPostRequest(url: String, postDto: Any) {
        runCatching {
            mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(postDto))
            ).andExpect(status().isCreated)
        }.onFailure { e ->
            fail("Error executing POST request to $url: ${e.message}")
        }
    }

    protected fun performGetRequest(url: String, urlVariables: Any, expectedDto: Any) {
        runCatching {
            mockMvc.perform(
                MockMvcRequestBuilders.get(url, urlVariables)
            ).andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                content().json(toJson(expectedDto))
            )
        }.onFailure { e ->
            fail("Error executing GET request with path variable: ${e.message}")
        }
    }

    protected fun performPostSearchRequest(url: String, requestBody: Any, expectedDto: Any) {
        runCatching {
            mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(requestBody))
            ).andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                content().json(toJson(expectedDto))
            )
        }.onFailure { e ->
            fail("Error executing POST search request: ${e.message}")
        }
    }

    protected fun performAuthRequest(url: String, authRequest: Any, expectedResponse: Any) {
        runCatching {
            mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJson(authRequest))
            ).andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                content().json(toJson(expectedResponse))
            )
        }.onFailure { e ->
            fail("Error executing Auth POST request to $url: ${e.message}")
        }
    }
}