package backend.stepdefs;

import backend.repositories.DjRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class MyStepdefs {

    DjRepository djRepository;

    @Given("I have no DJ in the database")
    public void iHaveNoDJInTheDatabase(){
    }
    @Then("Add DJ to database")
    public void addDJToDatabase() {
    }
}
