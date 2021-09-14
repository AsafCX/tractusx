package net.catenax.prs.acceptancetests;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class StepDefinitionsIntegrationTest extends AcceptanceTestsBase {

    @When("^the client calls ([\\w|\\/|?|=|&]+)")
    public void the_client_issues_GET_health(String path) {
        executeGet("http://localhost:8080" + path);
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        HttpStatus currentStatusCode = latestResponse.getResponse().getStatusCode();
        assertThat("status code is incorrect : "+
                latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
    }

    @And("^the client receives body containing (.+)$")
    public void the_client_receives_body_containing(String body) {
        assertThat(latestResponse.getBody(), containsString(body));
    }

}
